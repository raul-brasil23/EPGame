package Entities;

import Utils.GameLib;
import Utils.State;

import java.awt.Color;

public class DiamondEnemy extends Enemy {
	private boolean canShoot;
	
	public DiamondEnemy (double x, double y, double v, double angle, double rv) {
		super (State.ACTIVE, x, y, v, angle, rv, 12.0);
		this.canShoot = false;
	}

	@Override
	public void update(long currentTime, long delta) {
		if (this.state == State.EXPLODING) {
			if (currentTime > this.explosion_end) {
				this.state = State.INACTIVE;
			}
		}

		if (this.state == State.ACTIVE) {
			// Inativa se sair da tela pelas laterais
			if (this.X < -10 || this.X > GameLib.WIDTH + 10) {
				this.state = State.INACTIVE;
			} else {
				double previousY = this.Y;
										
				this.X += this.V * Math.cos(this.angle) * delta;
				this.Y += this.V * Math.sin(this.angle) * delta * (-1.0);
				this.angle += this.RV * delta;
				
				// Lógica original de mudança de direção ao chegar em 30% da tela
				double threshold = GameLib.HEIGHT * 0.30;
				if (previousY < threshold && this.Y >= threshold) {
					if (this.X < GameLib.WIDTH / 2) this.RV = 0.003;
					else this.RV = -0.003;
				}
				
				// Trava o ângulo e prepara para atirar
				if (this.RV > 0 && Math.abs(this.angle - 3 * Math.PI) < 0.05) {
					this.RV = 0.0;
					this.angle = 3 * Math.PI;
					this.canShoot = true;
				}
				if (this.RV < 0 && Math.abs(this.angle) < 0.05) {
					this.RV = 0.0;
					this.angle = 0.0;
					this.canShoot = true;
				}
			}
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

	public boolean isReadyToShoot() { 
    	return this.canShoot; 
	}
	
	public void resetShot() { 
    	this.canShoot = false; 
	}
}