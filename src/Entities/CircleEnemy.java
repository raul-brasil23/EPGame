package Entities;

import Behaviors.CircularMovement;
import Managers.ProjectileManager;
import Utils.GameLib;
import Utils.State;
import java.awt.Color;

public class CircleEnemy extends Enemy {
	
	public CircleEnemy (double x, double y, double v, double angle, double rv, long shootInterval) {
		super (State.ACTIVE, x, y, 9.0, new CircularMovement(v, angle, rv), shootInterval);
	}

	@Override
	protected void performExtraActions(long currentTime, long delta) {
		if (this.Y > GameLib.HEIGHT + 10) {
			this.state = State.INACTIVE;
		}
	}

	@Override
	public void tryToShoot(long currentTime, Player player, ProjectileManager projManager) {
		if (this.state == State.ACTIVE && currentTime > this.lastShootTime && this.Y < player.getY()) {
			CircularMovement move = (CircularMovement) this.movementBehavior;
			
			double vx = Math.cos(move.getAngle()) * 0.45;
			double vy = Math.sin(move.getAngle()) * 0.45 * (-1.0);
			projManager.getEnemyProjectiles().add(new EnemyProjectile(this.getX(), this.getY(), vx, vy));
			
			this.lastShootTime = currentTime + this.shootInterval;
			this.shootInterval = (long) (200 + Math.random() * 500);
		}
	}

	@Override
	public void draw(long currentTime) {
		if (this.state == State.EXPLODING) {
			double alpha = (double) (currentTime - this.explosion_start) / (this.explosion_end - this.explosion_start);
			GameLib.drawExplosion(this.X, this.Y, alpha);
		} else if (this.state == State.ACTIVE) {
			GameLib.setColor(Color.CYAN);
			GameLib.drawCircle(this.X, this.Y, this.radius);
		}
	}
}