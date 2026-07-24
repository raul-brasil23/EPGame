package Entities;

import Behaviors.MovementBehavior;
import Utils.State;

public abstract class Enemy extends Ship{
	protected long lastShootTime = 0;
    protected long shootInterval;
	
	public Enemy (State state, double x, double y, double radius, MovementBehavior movement, long shootInterval) {
		super (state, x, y, radius, movement);
		this.shootInterval = shootInterval;
	}

	public void takeDamage(int damage, long currentTime) {
		// Inimigos comuns morrem com qualquer dano (1 hit kill)
		if (this.state != State.EXPLODING) {
			this.explode(currentTime, 500);
		}
	}
}