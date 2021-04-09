package data;

import java.util.ArrayList;

/*
 * Classe representant un joueur
 */
public class Player {
	private String name;
	private ArrayList<Ball> myBalls;

	private int score;
	private int iterator = 0;

	public Player(String name) {
		this.name = name;
		myBalls = new ArrayList<Ball>();
		this.score = 0;
	}

	public ArrayList<Ball> getMyBalls(){
		return myBalls;
	}
	
	public void addBall(Ball ball) {
		myBalls.add(ball);
	}
	
	/*
	 * Donne le score du joueur
	 * 
	 * @return int
	 */
	public int getScore() {
		while(this.iterator < myBalls.size()) {
			Ball b = getMyBalls().get(this.iterator);

			switch (b.getType()) {
			case RAYEE:
				this.score += 5;
				break;
			case PLEINE:
				this.score += 10;
				break;
			default:
				break;
			}

			this.iterator++;
		}
		return this.score;
	}

	public void setMalus(int malus){
		this.score -= malus;
	}

	public void setBonus(int bonus) {
		this.score += bonus;
	}

	public void resetScore(){
		if(this.score > 0){
			this.score = 0;
		}
	}
	
	public String getName() {
		return name;
	}
	
}
