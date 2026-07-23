package Entities;

import Utils.State;
import Utils.GameLib;

import java.awt.Color;

public class PlayerProjectile extends Projectile {
	
	public PlayerProjectile (double x, double y) {
		super (State.ACTIVE, x, y, 0.0, -1.0, 0.0);
	}
	
	@Override
	public void update(long currentTime, long delta) {
		// Chama o movimento matemático que agora está na superclasse
		super.update(currentTime, delta);
		
		if (this.state == State.ACTIVE) {
			// Verifica apenas se o projétil saiu da tela (passou do topo)
			if (this.Y < 0) {
				this.state = State.INACTIVE;
			}
		}
	}

	@Override
	public void draw(long currentTime) {
		if (this.state == State.ACTIVE) {
			GameLib.setColor(Color.GREEN);
			GameLib.drawLine(this.X, this.Y - 5, this.X, this.Y + 5);
			GameLib.drawLine(this.X - 1, this.Y - 3, this.X - 1, this.Y + 3);
			GameLib.drawLine(this.X + 1, this.Y - 3, this.X + 1, this.Y + 3);
		}
	}
}