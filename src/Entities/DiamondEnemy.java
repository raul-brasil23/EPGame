package Entities;

import Behaviors.DiamondMovement;
import Managers.ProjectileManager;
import Utils.GameLib;
import Utils.State;
import java.awt.Color;

public class DiamondEnemy extends Enemy {
	
	public DiamondEnemy (double x, double y, double v, double angle, double rv) {
		super (State.ACTIVE, x, y, 12.0, new DiamondMovement(v, angle, rv), 0);
	}

	@Override
	protected void performExtraActions(long currentTime, long delta) {
		if (this.X < -10 || this.X > GameLib.WIDTH + 10) {
			this.state = State.INACTIVE;
		}
	}

	@Override
	public void tryToShoot(long currentTime, Player player, ProjectileManager projManager) {
		DiamondMovement move = (DiamondMovement) this.movementBehavior;
		
		if (move.isReadyToShoot()) {
			double[] angles = { Math.PI/2 + Math.PI/8, Math.PI/2, Math.PI/2 - Math.PI/8 };
			for (double angle : angles) {
				double a = angle + Math.random() * Math.PI/6 - Math.PI/12;
				double vx = Math.cos(a) * 0.30;
				double vy = Math.sin(a) * 0.30;
				projManager.getEnemyProjectiles().add(new EnemyProjectile(this.getX(), this.getY(), vx, vy));
			}
			move.resetShot(); 
		}
	}

	@Override
	public void draw(long currentTime) {
		if (this.state == State.EXPLODING) {
			double alpha = (double) (currentTime - this.explosion_start) / (this.explosion_end - this.explosion_start);
			GameLib.drawExplosion(this.X, this.Y, alpha);
		} else if (this.state == State.ACTIVE) {
			GameLib.setColor(Color.MAGENTA);
			GameLib.drawDiamond(this.X, this.Y, this.radius);
		}
	}
}