package Entities;

import Utils.GameLib;
import Utils.State;

import java.awt.Color;


public class Player extends Ship {
	private int hp;
	private long nextShot;					// instante a partir do qual pode haver um próximo tiro
	
	public Player (double x, double y, long currentTime, int hp) {
		super (State.ACTIVE, x, y, 0.25, 0.25, 12.0);
		this.nextShot = currentTime;
		this.hp = hp;
	}

	public void move (long delta) {
		if(this.state == State.ACTIVE) {
			if(GameLib.iskeyPressed(GameLib.KEY_UP))    this.Y -= delta * this.VY;
			if(GameLib.iskeyPressed(GameLib.KEY_DOWN))  this.Y += delta * this.VY;
			if(GameLib.iskeyPressed(GameLib.KEY_LEFT))  this.X -= delta * this.VX;
			if(GameLib.iskeyPressed(GameLib.KEY_RIGHT)) this.X += delta * this.VX;
		}
	}

	@Override
	public void update(long currentTime, long delta) {
		if (this.state == State.EXPLODING) {
			if (currentTime > this.explosion_end) {
				this.state = State.ACTIVE;
			}
		}
		if (this.state == State.ACTIVE) {
			if (this.X < 0.0) this.X = 0.0;
			if (this.X >= GameLib.WIDTH) this.X = GameLib.WIDTH - 1;
			if (this.Y < 25.0) this.Y = 25.0;
			if (this.Y >= GameLib.HEIGHT) this.Y = GameLib.HEIGHT - 1;
		}
	}

	@Override
	public void draw(long currentTime) {
		if (this.state == State.EXPLODING) {
			double alpha = (double) (currentTime - this.explosion_start) / (this.explosion_end - this.explosion_start);
			GameLib.drawExplosion(this.X, this.Y, alpha);
		} 
		else if (this.state == State.ACTIVE) {
			GameLib.setColor(Color.BLUE);
			GameLib.drawPlayer(this.X, this.Y, this.radius);
		}
	}
	
	public boolean isShooting(long currentTime) {
		if (this.state == State.ACTIVE && GameLib.iskeyPressed(GameLib.KEY_CONTROL)) {
			if (currentTime > this.nextShot) {
				this.nextShot = currentTime + 100; // Tempo de recarga (100ms do código original)
				return true;
			}
		}
		return false;
	}

	public int getHp() { return hp; }
    public void setHp(int hp) { this.hp = hp; }

	public long getNextShot() { return nextShot; }
	public void setNextShot(long nextShot) { this.nextShot = nextShot; }
}
