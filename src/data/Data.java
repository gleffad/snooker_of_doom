package data;

import java.util.ArrayList;
import specifications.DataService;

/**
 * Represente l'etat du jeu
 */
public class Data implements DataService {
	
	private final String nameP1 = "Player 1";
	private final String nameP2 = "Player 2";
	
	private Player p1, p2, currentPlayer, winner;
	private Table table;
	private Mod mod;

	public Data(Mod m) {
		this.p1 = new Player(nameP1);
		this.p2 = new Player(nameP2);
		currentPlayer = p1;
		this.mod = m;
		
		Shape shapeTable = setShape(mod);
		this.table = new Table(shapeTable, mod, 20);
	}

	/**
	 * Cree un table en fonction du mode
	 * @param mod : Mod
	 * @return Shape
	 */
	private Shape setShape(Mod mod) {
		switch (mod) {
			case STANDARD:
				return new Square(new Point(0, 0), 500, 300);
			case CIRCLE:
				return new Circle(new Point(320, 320), 300);
			default:
				return null;
		}
	}
	
	public Table getTable() {
		return this.table;
	}
	
	public ArrayList<Circle> getTrou() {
		return this.table.getTrous();
	}
	
	public Mod getMod() {
		return this.mod;
	}
	
	public Player getPlayer1() {
		return p1;
	}
	
	public Player getPlayer2() {
		return p2;
	}
	
	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	/**
	 * Retourne le gagnant si il existe
	 * @return Player
	 */
	public Player getWinner(){
		
		if(table.getBalls().size() < 2 && table.getBalls().get(0).getType() == TypeBall.BLANCHE){
			if(getPlayer1().getScore() > getPlayer2().getScore()){
				return getPlayer1();
			} else {
				return getPlayer2();
			}
        } else {
        	return null;
        }
		
	}
	
	/**
	 * Permet de changer de joueur courant
	 */
	public void switchPlayer() {
		if ( currentPlayer == p1 ) {
			currentPlayer = p2;
		} else {
			currentPlayer = p1;
		}
	}
}
