package Entities;

import Utils.State;
import Utils.GameLib;

import java.awt.Color;

public class EnemyProjectile extends Projectile {
	
	public EnemyProjectile (double x, double y, double vx, double vy) {
		super (State.ACTIVE, x, y, vx, vy, 2.0);
	}


	@Override
	public void update(long currentTime, long delta) {
		if (this.state == State.ACTIVE) {
			// Movimentação
			this.X += this.VX * delta;
			this.Y += this.VY * delta;
			
			// Verificando se o projétil saiu da tela (passou do chão)
			if (this.Y > GameLib.HEIGHT) {
				this.state = State.INACTIVE;
			}
		}
	}

	@Override
	public void draw(long currentTime) {
		if (this.state == State.ACTIVE) {
			GameLib.setColor(Color.RED);
			GameLib.drawCircle(this.X, this.Y, this.radius);
		}
	}
}