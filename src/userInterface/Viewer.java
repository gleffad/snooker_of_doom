/* ******************************************************
 * Simulator alpha - Composants logiciels 2015.
 * Copyright (C) 2015 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: userInterface/Viewer.java 2015-03-09 buixuan.
 * ******************************************************/
package userInterface;

import specifications.ViewerService;
import specifications.ReadService;
import specifications.RequireReadService;
import specifications.StartEngineService;
import specifications.RequireStartEngineService;

import java.io.File;

import data.Ball;
import data.Circle;
import data.Player;
import data.Square;
import data.TypeBall;
import data.TypeShape;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class Viewer implements ViewerService, RequireReadService, RequireStartEngineService {
    private ReadService data;
    private StartEngineService startEngine;

    private double cursorX;
    private double cursorY;
    private boolean mousePressed;

    public Viewer() {
    }

    @Override
    public void bindReadService(ReadService service) {
        data = service;
    }

    @Override
    public void bindStartEngineService(StartEngineService service) {
        startEngine = service;
    }

    @Override
    public void init() {
        cursorX = -1;
        cursorY = -1;
        mousePressed = false;
    }

    @Override
    public void startViewer() {
        startEngine.start();
    }

    @Override
    public Group getPanel() {
        int border = data.getTable().getBorder();
        Group panel = new Group();

        data.Shape shapeTable = data.getTable().getShape();
        javafx.scene.shape.Shape table;

        // --- Affiche la table si rectangle --- //
        if (shapeTable.getType().equals(TypeShape.SQUARE)) {
            Square s = (Square) shapeTable;

            table = new Rectangle(s.getPos().getX() + border, s.getPos().getY() + border, s.getWidth(), s.getHeight());
            table.setFill(Color.GREEN);

            Rectangle borderTable = new Rectangle(s.getPos().getX(), s.getPos().getY(), s.getWidth() + (2 * border),
                    s.getHeight() + (2 * border));
            borderTable.setFill(Color.MAROON);

            panel.getChildren().add(borderTable);
            panel.getChildren().add(table);

            for (data.Circle t : data.getTrou()) {
                panel.getChildren()
                        .add(new javafx.scene.shape.Circle(t.getCenter().getX(), t.getCenter().getY(), t.getRadius()));
            }
        }

        // ---Affiche la table si cercle --- //
        if (shapeTable.getType().equals(TypeShape.CIRCLE)){
            Circle c = (Circle) shapeTable;

            table = new javafx.scene.shape.Circle(c.getCenter().getX(), c.getCenter().getY(), c.getRadius());
            table.setFill(Color.GREEN);

            javafx.scene.shape.Circle borderTable = new javafx.scene.shape.Circle(c.getCenter().getX(), c.getCenter().getY(), c.getRadius() + border);
            borderTable.setFill(Color.MAROON);

            panel.getChildren().add(borderTable);
            panel.getChildren().add(table);

            for (data.Circle t : data.getTrou()) {
                panel.getChildren().add(new javafx.scene.shape.Circle(t.getCenter().getX(), t.getCenter().getY(), t.getRadius()));
            }
        }

        displayBalls(panel);
        displayScore(600, 50, panel, data.getPlayer1());
        displayScore(600, 150, panel, data.getPlayer2());
        
        // --- Affiche le gagnant --- //
        if(data.getWinner() != null) {
        	if(shapeTable.getType().equals(TypeShape.SQUARE)) {
        		Square s = (Square) shapeTable;
        		displayWinner(s.getWidth()/2 - 50, s.getHeight()/2 + 2*border, panel, data.getWinner());
        	}
        	if(shapeTable.getType().equals(TypeShape.CIRCLE)) {
        		Circle c = (Circle) shapeTable;
        		displayWinner(c.getRadius() - 50, c.getRadius() + 2*border, panel, data.getWinner());
        	}
        }

        return panel;
    }
    
    /**
     * Affiche le gagnant de la partie a une position donnee
     * 
     * @param x : double
     * @param y : double
     * @param panel : Group
     * @param winner : Player
     */
    public void displayWinner(double x, double y, Group panel, Player winner) {
    	Text winnerLabel = new Text(x, y, winner.getName());
    	 winnerLabel.setFont(Font.loadFont(new File("./ressources/doom.ttf").toURI().toString(), 50));
    	 panel.getChildren().add(winnerLabel);
    }

    /**
     * Affiche le score du player
     * 
     * @param x : double
     * @param y : double
     * @param panel : Group
     * @param player: Player
     */
    private void displayScore(int x, int y, Group panel, Player player) {
        Text score = new Text(x, y, player.getName() + " : " + String.valueOf(player.getScore()));
        score.setFont(Font.loadFont(new File("./ressources/doom.ttf").toURI().toString(), 40));
        if (data.getCurrentPlayer() == player) {
            score.setFill(Color.RED);
        }
        panel.getChildren().add(score);
    }
    
    /**
     * Affiche les balles
     * 
     * @param panel : Group
     */
    private void displayBalls(Group panel) {
        for (Ball b : data.getTable().getBalls()) {
            if (b.getShape().getType().equals(TypeShape.CIRCLE)) {
                Circle c = (Circle) b.getShape();

                javafx.scene.shape.Circle ball = new javafx.scene.shape.Circle(c.getCenter().getX(),
                c.getCenter().getY(), c.getRadius());
                ball.setFill(getBallColor(b));
                panel.getChildren().add(ball);

                javafx.scene.shape.Circle insideBall = new javafx.scene.shape.Circle(c.getCenter().getX(),
                c.getCenter().getY(), c.getRadius() / 2);
                insideBall.setFill(Color.WHITE);
                panel.getChildren().add(insideBall);

                if (b.getType().equals(TypeBall.RAYEE)) {
                    Rectangle rayBall = new Rectangle(c.getCenter().getX() - 15, c.getCenter().getY() - 5, 30, 10);
                    rayBall.setFill(Color.WHITE);
                    Shape bb = Shape.subtract(ball, rayBall);
                    bb.setFill(Color.WHITE);
                    panel.getChildren().add(bb);
                }

                if (b.getNumber() != 0) {
                    Text number = new Text(c.getCenter().getX() - 2, c.getCenter().getY() + 3,
                    String.valueOf(b.getNumber()));
                    number.setFill(Color.BLACK);
                    number.setFont(Font.font("verdana", FontWeight.LIGHT, FontPosture.REGULAR, 7));
                    panel.getChildren().add(number);
                }

                if (cursorX != -1 && cursorY != -1 && b.getType().equals(TypeBall.BLANCHE)) {
                    Circle whiteBall = (Circle) b.getShape();
                    Line line = new Line(whiteBall.getCenter().getX(), whiteBall.getCenter().getY(), cursorX, cursorY);
                    line.setStrokeWidth(1);
                    line.setStroke(Color.WHITE);
                    line.getStrokeDashArray().addAll(2.0, 21.0);
                    panel.getChildren().add(line);
                }
            }
        }
    }

    /**
     * Donne la couleur de la balle
     * 
     * @param b : Ball
     * @return Color
     */
    private Color getBallColor(Ball b) {
        switch (b.getNumber()) {
            case 0:
                return Color.WHITE;
            case 1:
                return Color.YELLOW;
            case 2:
                return Color.BLUE;
            case 3:
                return Color.RED;
            case 4:
                return Color.PURPLE;
            case 5:
                return Color.ORANGE;
            case 6:
                return Color.DARKGREEN;
            case 7:
                return Color.DARKRED;
            case 8:
                return Color.BLACK;
            case 9:
                return Color.YELLOW;
            case 10:
                return Color.BLUE;
            case 11:
                return Color.RED;
            case 12:
                return Color.PURPLE;
            case 13:
                return Color.ORANGE;
            case 14:
                return Color.DARKGREEN;
            case 15:
                return Color.DARKRED;
            default:
                return null;
        }
    }

    @Override
    /**
     * Change la position du curseur au clic     
     *      
     * @param x : double
     * @param y : double
     */
    public void setCursorPressed(double x, double y) {
        if (mousePressed) {
            startEngine.strike(x, y);
            this.mousePressed = false;
            this.cursorX = -1;
            this.cursorY = -1;
        } else {
            this.cursorX = x;
            this.cursorY = y;
            this.mousePressed = true;
        }
    }

    @Override
    /**
     * Change la position du curseur au mouvement      
     *      
     * @param x : double
     * @param y : double
     */
    public void setCursorMoved(double x, double y) {
        if (mousePressed) {
            this.cursorX = x;
            this.cursorY = y;
        }
    }
}
