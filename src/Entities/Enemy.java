package Entities;

import Behaviors.MovementBehavior;
import Utils.State;

public abstract class Enemy extends Ship {
    
	protected long lastShootTime;
    protected long shootInterval;
	
	public Enemy (State state, double x, double y, double radius, MovementBehavior movement, long shootInterval) {
		super(state, x, y, radius, movement);
		this.lastShootTime = 0;
		this.shootInterval = shootInterval;
	}

    // Inimigos comuns sempre são destruídos com apenas 1 tiro
	public void takeDamage(int damage, long currentTime) {
		if (this.state != State.EXPLODING) {
			this.explode(currentTime, 500);
		}
	}
}