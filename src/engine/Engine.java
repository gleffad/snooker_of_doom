/* ******************************************************
 * Simulator alpha - Composants logiciels 2015.
 * Copyright (C) 2015 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: engine/Engine.java 2015-03-09 buixuan.
 * ******************************************************/
package engine;

import data.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import specifications.EngineService;
import specifications.DataService;
import specifications.RequireDataService;
import java.io.File;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import tools.HardCodedParameters;

/**
 * Classe representant le moteur du jeu
 */
public class Engine implements EngineService, RequireDataService {
    private Timer engineClock;
    private DataService data;

    private final double friction = HardCodedParameters.friction;
    private final int engineTimeout = HardCodedParameters.engineTimeout;
    private final double vMax = HardCodedParameters.maxSpeed;
    private final double strikeCoeff = HardCodedParameters.strikeCoeff;

    private boolean aJoue = false;

    private Media sound = new Media(new File("./ressources/choc-boules.mp3").toURI().toString());
    private MediaPlayer mediaPlayer = new MediaPlayer(sound);

    public Engine() {
    }

    @Override
    public void bindDataService(DataService service) {
        data = service;
    }

    @Override
    public void init() {
        engineClock = new Timer();
    }

    @Override
    public void start() {
        engineClock.schedule(new TimerTask() {
            public void run() {
               if(data.getWinner() == null) {
            	   ArrayList<Ball> balls = data.getTable().getBalls();
                   ArrayList<Circle> trous = data.getTrou();
                   updateSpeedBalls(balls);
                   updatePositionBalls(balls);
                   if (detectCollition(balls)) {
                       mediaPlayer.play();
                       mediaPlayer.seek(Duration.ZERO);
                   }
                   updateCollision(balls, trous);

                   if (aJoue && boulesImmobiles(balls)) {
                       data.switchPlayer();
                       aJoue = false;
                   }
               }
            }
        }, 0, engineTimeout);
    }
    
    /**
     * Permet d'appliquer une friction pour reduire la vitesse des balles
     * @param balls : ArrayList<Ball>
     */
    private void updateSpeedBalls(ArrayList<Ball> balls) {
        for (Ball b : balls) {
            if ((b.getVx() > -0.1) && (b.getVx() < 0.1)) {
                b.setVx(0);
            } else {
                b.setVx(b.getVx() * friction);
            }

            if ((b.getVy() > -0.1) && (b.getVy() < 0.1)) {
                b.setVy(0);
            } else {
                b.setVy(b.getVy() * friction);
            }
        }
    }
    
    /*
     * Permet de gerer les collisions avec le bord de la table
     * @param balls : ArrayList<Ball>
     */
    private void updatePositionBalls(ArrayList<Ball> balls) {
        if (data.getMod().equals(Mod.STANDARD)) {

            Square table = (Square) data.getTable().getShape();

            for (Ball b : balls) {
                double px = ((Circle) b.getShape()).getCenter().getX();
                double py = ((Circle) b.getShape()).getCenter().getY();

                Circle c = (Circle) b.getShape();

                if (c.getCenter().getX() < c.getRadius() + data.getTable().getBorder()
                        || c.getCenter().getX() > c.getRadius() + table.getWidth()) {
                    b.setVx(-b.getVx());
                }

                if (c.getCenter().getY() < c.getRadius() + data.getTable().getBorder()
                        || c.getCenter().getY() > c.getRadius() + table.getHeight()) {
                    b.setVy(-b.getVy());
                }

                ((Circle) b.getShape()).setCenter(new Point(px + b.getVx(), py + b.getVy()));
            }
        } else if (data.getMod().equals(Mod.CIRCLE)) {
            Circle table = (Circle) data.getTable().getShape();
            for (Ball b : balls) {
                double px = ((Circle) b.getShape()).getCenter().getX();
                double py = ((Circle) b.getShape()).getCenter().getY();

                Circle c = (Circle) b.getShape();

                if (table.getCenter().dist(c.getCenter()) + c.getRadius() >= table.getRadius()) {
                    double x1 = c.getCenter().getX();
                    double y1 = c.getCenter().getY();
                    double r1 = c.getRadius();
                    double x2 = table.getCenter().getX();
                    double y2 = table.getCenter().getY();
                    double r2 = table.getRadius();
                    var d = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
                    var nx = (x2 - x1) / (r1 + r2);
                    var ny = (y2 - y1) / (r1 + r2);
                    var gx = -ny;
                    var gy = nx;

                    var v1n = nx * b.getVx() + ny * b.getVy();
                    var v1g = gx * b.getVx() + gy * b.getVy();

                    b.setVx(nx + gx * v1g);
                    b.setVy(ny + gy * v1g);

                    ((Circle) b.getShape()).setCenter(new Point(x1 + (r1) * (x2 - x1) / d, y1 + (r1) * (y2 - y1) / d));
                }

                ((Circle) b.getShape()).setCenter(new Point(px + b.getVx(), py + b.getVy()));
            }
        }
    }
    
    /**
     * Permet de gere la collission et le rebond d'une liste ded balle
     * @param balls : ArrayList<Ball>
     * @return boolean
     */
    private boolean detectCollition(ArrayList<Ball> balls) {
        for (int i = 0; i < balls.size() - 1; i++) {
            for (int j = i + 1; j < balls.size(); j++) {
                Circle c1 = (Circle) balls.get(i).getShape();
                Circle c2 = (Circle) balls.get(j).getShape();

                if (c1.getCenter().dist(c2.getCenter()) <= c1.getRadius() + c2.getRadius()) {
                    double x1 = c1.getCenter().getX();
                    double y1 = c1.getCenter().getY();
                    double r1 = c1.getRadius();
                    double x2 = c2.getCenter().getX();
                    double y2 = c2.getCenter().getY();
                    double r2 = c2.getRadius();
                    double d = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
                    double nx = (x2 - x1) / (r1 + r2);
                    double ny = (y2 - y1) / (r1 + r2);
                    double gx = -ny;
                    double gy = nx;

                    double v1n = nx * balls.get(i).getVx() + ny * balls.get(i).getVy();
                    double v1g = gx * balls.get(i).getVx() + gy * balls.get(i).getVy();
                    double v2n = nx * balls.get(j).getVx() + ny * balls.get(j).getVy();
                    double v2g = gx * balls.get(j).getVx() + gy * balls.get(j).getVy();

                    balls.get(i).setVx(nx * v2n + gx * v1g);
                    balls.get(i).setVy(ny * v2n + gy * v1g);
                    balls.get(j).setVx(nx * v1n + gx * v2g);
                    balls.get(j).setVy(ny * v1n + gy * v2g);

                    c2.setCenter(new Point(x1 + (r1 + r2) * (x2 - x1) / d, y1 + (r1 + r2) * (y2 - y1) / d));

                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * Si une des boules touche un des trous alors on applique un traitement en fonction de la balle
     * @param balls : ArrayList<Ball>
     * @param trous : ArrayList<Circle>
     */
    private void updateCollision(ArrayList<Ball> balls, ArrayList<Circle> trous) {
    	Ball ball2remove = detectHitTrou(balls, trous);
        if (data.getMod().equals(Mod.STANDARD)) {
            if (ball2remove != null) {
                double w = ((Square) data.getTable().getShape()).getWidth();
                double h = ((Square) data.getTable().getShape()).getHeight();

                if (ball2remove.getType().equals(TypeBall.BLANCHE) && balls.size() > 0) {
                    ball2remove.setVx(0);
                    ball2remove.setVy(0);
                    ((Circle) ball2remove.getShape()).setCenter(new Point(w / 2 + 150, h / 2 + data.getTable().getBorder()));

                    data.getCurrentPlayer().setMalus(10);
                } else if (ball2remove.getType().equals(TypeBall.NOIR) && balls.size() > 0) {
                    data.getCurrentPlayer().addBall(ball2remove);
                    data.getTable().removeBall(ball2remove);

                    if(balls.size() > 2){
                        data.getCurrentPlayer().resetScore();
                    }
                    else{
                        data.getCurrentPlayer().setBonus(15);
                    }
                } else {
                    if (data.getTable().removeBall(ball2remove)) { // une boule de couleur vient de sortir
                        data.getCurrentPlayer().addBall(ball2remove);
                    }
                }
            }
        } else if (data.getMod().equals(Mod.CIRCLE)) {
            if (ball2remove != null) {
                Point c = ((Circle) data.getTable().getShape()).getCenter();

                if (ball2remove.getType().equals(TypeBall.BLANCHE) && balls.size() > 1) {
                    ball2remove.setVx(0);
                    ball2remove.setVy(0);
                    ((Circle) ball2remove.getShape()).setCenter(new Point(c.getX() + 150, c.getY()));

                    data.getCurrentPlayer().setMalus(10);
                } else if (ball2remove.getType().equals(TypeBall.NOIR) && balls.size() > 2) {
                    data.getCurrentPlayer().addBall(ball2remove);
                    data.getTable().removeBall(ball2remove);

                    if(balls.size() > 2){
                        data.getCurrentPlayer().resetScore();
                    }
                    else{
                        data.getCurrentPlayer().setBonus(20);
                    }
                } else {
                    if (data.getTable().removeBall(ball2remove)) {
                        data.getCurrentPlayer().addBall(ball2remove);
                    }
                }
            }
        }
    }
    
    /**
     * Permet de savoir si tous les balles sont arrete
     * @param balls : ArrayList<Ball>
     * @return boolean
     */
    private boolean boulesImmobiles(ArrayList<Ball> balls) {
        for (Ball b : balls) {
            if (b.getVx() != 0 || b.getVy() != 0) return false;
        }
        return true;
    }

    /**
     * Retourne la balle qui touche un trou
     * @param balls : ArrayList<Ball>
     * @param trous : ArrayList<Circle>
     * @return Ball
     */
    private Ball detectHitTrou(ArrayList<Ball> balls, ArrayList<Circle> trous) {
        for (Ball b : balls) {
            Circle cb = (Circle) b.getShape();
            for (Circle t : trous) {
                if (cb.getCenter().dist(t.getCenter()) <= cb.getRadius() + t.getRadius()) {
                    return b;
                }
            }
        }
        return null;
    }

    @Override
    /* Tire la balle blanche en fonction de la position en x et y du curseur
     * @param x : double
     * @param y : double 
     */
    public void strike(double x, double y) {
        Ball b = data.getTable().getWhiteBall();
        if (b == null)
            return;

        if (!boulesImmobiles(data.getTable().getBalls())) return;

        double bx = ((Circle) b.getShape()).getCenter().getX();
        double by = ((Circle) b.getShape()).getCenter().getY();

        double vx = (x - bx) * strikeCoeff;
        double vy = (y - by) * strikeCoeff;

        while (Math.abs(vx) > vMax || Math.abs(vy) > vMax) {
            vx *= 0.9;
            vy *= 0.9;
        }

        b.setVx(vx);
        b.setVy(vy);

        aJoue = true;
    }
}
