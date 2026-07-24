package Entities;

import Utils.State;

public abstract class Entity {
	protected State state;
	protected double X;
	protected double Y;
	protected double radius;
	
	public Entity (State state, double x, double y, double radius) {
		this.state = state;
		this.X = x;
		this.Y = y;
		this.radius = radius;
	}
	
	public abstract void update (long currentTime, long delta);
	public abstract void draw (long currentTime);

	public State getState() { return state; }
	public void setState(State state) { this.state = state; }

	public double getX() { return X; }
	public void setX(double x) { X = x; }

	public double getY() { return Y; }
	public void setY(double y) { Y = y; }

	public double getRadius() { return radius; }
	public void setRadius(double radius) { this.radius = radius; }
}