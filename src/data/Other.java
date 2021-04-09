package data;

import java.util.ArrayList;

/*
 * Classe representant une forme quelconque
 */
public class Other implements Shape{
	private ArrayList<Point> points;
	private TypeShape type;
	
	public Other(ArrayList<Point> points) {
		this.points = points;
		this.type = TypeShape.OTHER;
	}

	@Override
	public TypeShape getType() {
		return type;
	}
}
