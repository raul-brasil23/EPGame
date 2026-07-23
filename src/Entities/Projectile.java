package Entities;

import Utils.State;

public abstract class Projectile extends Entity{
	
	public Projectile (State state, double x, double y, double vx, double vy, double radius) {
		super (state, x, y, vx, vy, radius);
	}

	@Override
	public void update(long currentTime, long delta) {
		if (this.state == State.ACTIVE) {
			this.X += this.VX * delta;
			this.Y += this.VY * delta;
		}
	}
}