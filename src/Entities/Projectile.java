package Entities;

import Utils.State;

public abstract class Projectile extends Entity{
	
	public Projectile (State state, double x, double y, double vx, double vy, double radius) {
		super (state, x, y, vx, vy, radius);
	}
}