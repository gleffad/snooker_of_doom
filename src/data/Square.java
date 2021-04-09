package data;

/*
 * Classe representant un carre
 */
public class Square implements Shape {
	private TypeShape type;
	
	private Point pos;
	private double width;
	private double height;
	
	public Square(Point pos, double width, double height) {
		this.pos = pos;
		this.width = width;
		this.height = height;
		this.type = TypeShape.SQUARE;
	}

	@Override
	public TypeShape getType() {
		return this.type;
	}

	public Point getPos() {
		return pos;
	}

	public void setPos(Point pos) {
		this.pos = pos;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}
}
