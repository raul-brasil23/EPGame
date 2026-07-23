package Entities;

import Utils.GameLib;
import Utils.State;

import java.awt.Color;


public class Player extends Ship {
	private int hp;
	private long nextShot;					// instante a partir do qual pode haver um próximo tiro
	private long invulnerableUntil = 0;
	
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

	public boolean isShooting(long currentTime) {
		if (this.state == State.ACTIVE && GameLib.iskeyPressed(GameLib.KEY_CONTROL)) {
			if (currentTime > this.nextShot) {
				this.nextShot = currentTime + 100; // Tempo de recarga (100ms do código original)
				return true;
			}
		}
		return false;
	}

	public void takeDamage(long currentTime) {
    // Só toma dano se estiver ativo E se a imunidade já tiver acabado
		if (this.state == State.ACTIVE && currentTime > this.invulnerableUntil) {
			this.hp -= 1;
			
			// Dá 1 segundo (1000 milissegundos) de invulnerabilidade
			this.invulnerableUntil = currentTime + 1000;
			
			if (this.hp <= 0) {
				this.explode(currentTime, 2000);
			}
		}
	}

	public boolean isInvulnerable(long currentTime) {
		return currentTime < this.invulnerableUntil;
	}

	@Override
	public void update(long currentTime, long delta) {
		if (this.state == State.EXPLODING) {
			if (currentTime > this.explosion_end) {
				// Em vez de voltar para ACTIVE, mudamos para INACTIVE definitivamente
				this.state = State.INACTIVE;
			}
		}
		if (this.state == State.ACTIVE) {
			// Mantendo as travas de tela do seu código original
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
			// Se estiver invulnerável, faz a nave piscar
			if (currentTime < this.invulnerableUntil) {
				// Matemática simples para desenhar a nave sim, nave não (piscar)
				if ((currentTime / 100) % 2 == 0) {
					GameLib.setColor(Color.WHITE);
					GameLib.drawPlayer(this.X, this.Y, this.radius + 2);

					GameLib.setColor(Color.BLUE);
					GameLib.drawPlayer(this.X, this.Y, this.radius);
				}
			} else {
				// Desenho normal quando não está imune
				GameLib.setColor(Color.WHITE);
				GameLib.drawPlayer(this.X, this.Y, this.radius + 2);

				GameLib.setColor(Color.BLUE);
				GameLib.drawPlayer(this.X, this.Y, this.radius);
			}
		}
	}

	public int getHp() { return hp; }
    public void setHp(int hp) { this.hp = hp; }

	public long getNextShot() { return nextShot; }
	public void setNextShot(long nextShot) { this.nextShot = nextShot; }
}
