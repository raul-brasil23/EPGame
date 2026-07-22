package Entities;

import Utils.GameLib;
import Utils.State;

import java.awt.Color;


public class CircleEnemy extends Enemy {
	private long nextShot;				// instantes do próximo tiro
	
	public CircleEnemy (double x, double y, double v, double angle, double rv, long nextShot) {
		super (State.ACTIVE, x, y, v, angle, rv, 9.0);
		this.nextShot = nextShot;
	}

	@Override
	public void update(long currentTime, long delta) {
		if (this.state == State.EXPLODING) {
			if (currentTime > this.explosion_end) {
				this.state = State.INACTIVE; // Diferente do Player, o inimigo morre de vez
			}
		}

		if (this.state == State.ACTIVE) {
			// Verifica se saiu da tela por baixo
			if (this.Y > GameLib.HEIGHT + 10) {
				this.state = State.INACTIVE;
			} else {
				// Fórmulas trigonométricas de movimentação do código original
				this.X += this.V * Math.cos(this.angle) * delta;
				this.Y += this.V * Math.sin(this.angle) * delta * (-1.0);
				this.angle += this.RV * delta;
			}
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

	public boolean canShoot(long currentTime, double playerY) {
		if (this.state == State.ACTIVE && currentTime > this.nextShot && this.Y < playerY) {
			this.nextShot = (long) (currentTime + 200 + Math.random() * 500);
			return true;
		}
		return false;
	}
}