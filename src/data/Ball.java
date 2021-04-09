package data;

/**
 * Classe representant un balle
 */
public class Ball {
    private TypeBall type;
    private int number;
    private Shape shape;
    private double vx;
    private double vy;

    public Ball(TypeBall type, int number, Shape shape) {
        this.type = type;
        this.number = number;
        this.shape = shape;
        this.vx = 0;
        this.vy = 0;
    }

    public Shape getShape() {
        return this.shape;
    }

    public int getNumber() {
        return this.number;
    }

    public TypeBall getType() {
        return this.type;
    }

    public double getVx() {
        return vx;
    }

    public void setVx(double vx) {
        this.vx = vx;
    }

    public double getVy() {
        return vy;
    }

    public void setVy(double vy) {
        this.vy = vy;
    }

}
