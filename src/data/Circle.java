package data;

/**
 * Classe representant un cercle
 */
public class Circle implements Shape {
	private TypeShape type;
	private Point center;
	private double radius;
	
	public Circle(Point center, double radius) {
		this.center = center;
		this.radius = radius;
		this.type = TypeShape.CIRCLE;
	}

	@Override
	public TypeShape getType() {
		return this.type;
	}

	public Point getCenter() {
		return center;
	}

	public void setCenter(Point center) {
		this.center = center;
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}
}
