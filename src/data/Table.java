package data;

import java.util.ArrayList;

/**
 * Classes representant un table de jeux
 */
public class Table {

	private Shape shape;
	private ArrayList<Ball> balls;
	private ArrayList<Circle> trous;
	private int border;

	public Table(Shape shape, Mod mod, int border) {
		this.shape = shape;
		this.border = border;
		this.balls = initBalls(mod);
		this.trous = makeTrou(mod);
	}

	/**
	 * Permet de cree les trous de la table
	 * 
	 * @param mod : Mod
	 * @return ArrayList<Circle>
	 */
	private ArrayList<Circle> makeTrou(Mod mod) {
		ArrayList<Circle> trous = new ArrayList<Circle>();
		if (mod.equals(Mod.STANDARD)) {
			Square s = (Square) this.shape;
			trous.add(new Circle(new Point(border * 2 / 3, border * 2 / 3), border));
			trous.add(new Circle(new Point(border * 2 / 3, border * 4 / 3 + s.getHeight()), border));
			trous.add(new Circle(new Point(s.getWidth() + border * 4 / 3, border * 2 / 3), border));
			trous.add(new Circle(new Point(s.getWidth() + border * 4 / 3, border * 4 / 3 + s.getHeight()), border));
			trous.add(new Circle(new Point((s.getWidth() + 2 * border) / 2, border * 2 / 3), border));
			trous.add(new Circle(new Point((s.getWidth() + 2 * border) / 2, border * 4 / 3 + s.getHeight()), border));
		}

		if(mod.equals(Mod.CIRCLE)) {
			Circle c = (Circle) this.shape;
			trous.add(new Circle(new Point(border * 2/3, c.getRadius() + border), border));
			trous.add(new Circle(new Point(border * 4/3 + c.getRadius()*2, c.getRadius() + border), border));
			trous.add(new Circle(new Point(c.getRadius()/2 + border, c.getRadius()/6), 20));
			trous.add(new Circle(new Point(c.getRadius()/2 + border, (c.getRadius()*2 + 2*border) - (c.getRadius()/6)), 20));
			trous.add(new Circle(new Point(c.getRadius()/2 + border + c.getRadius(), c.getRadius()/6), 20));
			trous.add(new Circle(new Point(c.getRadius()/2 + border + c.getRadius(), (c.getRadius()*2 + 2*border) - (c.getRadius()/6)), 20));
		}
		return trous;
	}

	/**
	 * Retoune la balle blanche
	 * @return Ball
	 */
	public Ball getWhiteBall() {
		for(Ball b: this.balls) {
			if(b.getType().equals(TypeBall.BLANCHE)) return b;
		}
		return null;
	}

	/**
	 * Permet de placer les balles sur la table
	 * @param mod : Mod
	 * @return ArrayList<Ball>
	 */
	private ArrayList<Ball> initBalls(Mod mod) {
		ArrayList<Ball> balls = new ArrayList<Ball>();
		if (mod.equals(Mod.STANDARD)) {
			Square s = (Square) this.shape;
			double cx = (s.getWidth() + 2 * border) / 2;
			double cy = (s.getHeight() + 2 * border) / 2;
			double r = 10;
			balls.add(new Ball(TypeBall.BLANCHE, 0, new Circle(new Point(cx + 150, cy), r)));
			balls.add(new Ball(TypeBall.PLEINE, 1, new Circle(new Point(cx + r*2 + 4, cy), r)));
			balls.add(new Ball(TypeBall.PLEINE, 2, new Circle(new Point(cx, cy + r + 4), r)));
			balls.add(new Ball(TypeBall.PLEINE, 3, new Circle(new Point(cx - r*2 - 4, cy + 2*r + 4), r)));
			balls.add(new Ball(TypeBall.PLEINE, 4, new Circle(new Point(cx - r*4 - 8, cy + 3*r + 8), r)));
			balls.add(new Ball(TypeBall.PLEINE, 5, new Circle(new Point(cx - r*6 - 12, cy - 4*r - 8), r)));
			balls.add(new Ball(TypeBall.PLEINE, 6, new Circle(new Point(cx - r*6 - 12, cy + 2*r + 4), r)));
			balls.add(new Ball(TypeBall.PLEINE, 7, new Circle(new Point(cx - r*4 - 8, cy - r - 4), r)));
			balls.add(new Ball(TypeBall.NOIR, 8, new Circle(new Point(cx - r*2 - 4, cy), r)));
			balls.add(new Ball(TypeBall.RAYEE, 9, new Circle(new Point(cx, cy - r - 4), r)));
			balls.add(new Ball(TypeBall.RAYEE, 10, new Circle(new Point(cx - r*2 - 4, cy - 2*r - 4), r)));
			balls.add(new Ball(TypeBall.RAYEE, 11, new Circle(new Point(cx - r*4 - 8, cy - 3*r - 8), r)));
			balls.add(new Ball(TypeBall.RAYEE, 12, new Circle(new Point(cx - r*6 - 12, cy + 4*r + 12), r)));
			balls.add(new Ball(TypeBall.RAYEE, 13, new Circle(new Point(cx - r*6 - 12, cy - 2*r - 4), r)));
			balls.add(new Ball(TypeBall.RAYEE, 14, new Circle(new Point(cx - r*4 - 8, cy + r + 4), r)));
			balls.add(new Ball(TypeBall.RAYEE, 15, new Circle(new Point(cx - r*6 - 12, cy), r)));
		}

		if(mod.equals(Mod.CIRCLE)) {
			Circle c = (Circle) this.shape;
			double cx = c.getRadius() + border;
			double cy = c.getRadius() + border;
			double r = 10;
			balls.add(new Ball(TypeBall.BLANCHE, 0, new Circle(new Point(cx + 150, cy), r)));
			balls.add(new Ball(TypeBall.PLEINE, 1, new Circle(new Point(cx + r*2 + 4, cy), r)));
			balls.add(new Ball(TypeBall.PLEINE, 2, new Circle(new Point(cx, cy + r + 4), r)));
			balls.add(new Ball(TypeBall.PLEINE, 3, new Circle(new Point(cx - r*2 - 4, cy + 2*r + 4), r)));
			balls.add(new Ball(TypeBall.PLEINE, 4, new Circle(new Point(cx - r*4 - 8, cy + 3*r + 8), r)));
			balls.add(new Ball(TypeBall.PLEINE, 5, new Circle(new Point(cx - r*6 - 12, cy - 4*r - 8), r)));
			balls.add(new Ball(TypeBall.PLEINE, 6, new Circle(new Point(cx - r*6 - 12, cy + 2*r + 4), r)));
			balls.add(new Ball(TypeBall.PLEINE, 7, new Circle(new Point(cx - r*4 - 8, cy - r - 4), r)));
			balls.add(new Ball(TypeBall.NOIR, 8, new Circle(new Point(cx - r*2 - 4, cy), r)));
			balls.add(new Ball(TypeBall.RAYEE, 9, new Circle(new Point(cx, cy - r - 4), r)));
			balls.add(new Ball(TypeBall.RAYEE, 10, new Circle(new Point(cx - r*2 - 4, cy - 2*r - 4), r)));
			balls.add(new Ball(TypeBall.RAYEE, 11, new Circle(new Point(cx - r*4 - 8, cy - 3*r - 8), r)));
			balls.add(new Ball(TypeBall.RAYEE, 12, new Circle(new Point(cx - r*6 - 12, cy + 4*r + 12), r)));
			balls.add(new Ball(TypeBall.RAYEE, 13, new Circle(new Point(cx - r*6 - 12, cy - 2*r - 4), r)));
			balls.add(new Ball(TypeBall.RAYEE, 14, new Circle(new Point(cx - r*4 - 8, cy + r + 4), r)));
			balls.add(new Ball(TypeBall.RAYEE, 15, new Circle(new Point(cx - r*6 - 12, cy), r)));
		}
		return balls;
	}

	public Shape getShape() {
		return this.shape;
	}

	public int getBorder() {
		return this.border;
	}

	public ArrayList<Circle> getTrous() {
		return this.trous;
	}
	
	public ArrayList<Ball> getBalls() {
		return this.balls;
	}
	
	public boolean removeBall(Ball b) {
		return this.balls.remove(b);
	}
}
