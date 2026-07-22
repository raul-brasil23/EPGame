package Entities;

import Utils.State;

public abstract class Enemy extends Ship{
	protected double V;					// velocidades
	protected double angle;				// ângulos (indicam direção do movimento)
	protected double RV;					// velocidades de rotação
	
	public Enemy (State state, double x, double y, double v, double angle, double rv, double radius) {
		super (state, x, y, 0.0, 0.0, radius);
		this.V = v;
		this.angle = angle;
		this.RV = rv;
	}

	public double getV() { return V; }
	public void setV(double v) { this.V = v; }

	public double getAngle() { return angle; }
	public void setAngle(double angle) { this.angle = angle; }

	public double getRV() { return RV; }
	public void setRV(double rv) { this.RV = rv; }
}