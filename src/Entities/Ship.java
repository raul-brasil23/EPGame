package Entities;

import Utils.State;

public abstract class Ship extends Entity {
    protected double explosion_start = 0;			// instante do início da explosão
	protected double explosion_end = 0;			// instante do final da explosão

    public Ship (State state, double x, double y, double vx, double vy, double radius) {
		super (state, x, y, vx, vy, radius);
	}

    public void explode (long currentTime, long duration) {
        this.state = State.EXPLODING;
        this.explosion_start = currentTime;
        this.explosion_end = currentTime + duration;
    }

    public double getExplosion_start() { return explosion_start; }
	public void setExplosion_start(double explosion_start) { this.explosion_start = explosion_start; }

	public double getExplosion_end() { return explosion_end; }
	public void setExplosion_end(double explosion_end) { this.explosion_end = explosion_end; }
}
