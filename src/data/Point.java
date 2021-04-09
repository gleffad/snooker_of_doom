package data;

/*
 * Represent un point dans un espace en 2D
 */
public class Point {
	private double x;
	private double y;

	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}
	
	/**
	 * Calcul la distance entre ce point et un autre
	 * 
	 * @param p : Point
	 * @return double
	 */
	public double dist(Point p) {
		return Math.sqrt((this.x- p.getX())*(this.x- p.getX()) + (this.y- p.getY())*(this.y- p.getY()));
	}
}
