
public abstract class Entity {
	protected State state;
	protected double X;
	protected double Y;
	protected double VX;
	protected double VY;
	protected double radius;
	
	public Entity (State state, double x, double y, double vx, double vy, double radius) {
		this.state = state;
		this.X = x;
		this.Y = y;
		this.VX = vx;
		this.VY = vy;
		this.radius = radius;
	}
	
	public abstract void update ();

	public State getState() { return state; }
	public void setState(State state) { this.state = state; }

	public double getX() { return X; }
	public void setX(double x) { X = x; }

	public double getY() { return Y; }
	public void setY(double y) { Y = y; }

	public double getVX() { return VX; }
	public void setVX(double vX) { VX = vX; }

	public double getVY() { return VY; }
	public void setVY(double vY) { VY = vY; }

	public double getRadius() { return radius; }
	public void setRadius(double radius) { this.radius = radius; }
}