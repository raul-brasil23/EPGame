
public class Player extends Entity {
	private double explosion_start = 0;			// instante do início da explosão
	private double explosion_end = 0;			// instante do final da explosão
	private long nextShot;					// instante a partir do qual pode haver um próximo tiro
	
	public Player (State state, double x, double y, double vx, double vy, long nextShot, double radius) {
		super (state, x, y, vx, vy, 12.0);
		this.nextShot = nextShot;
	}
	
	public void moveLeft (double delta) {
		X -= delta * VX;
	}
	
	public void moveRight (double delta) {
		X += delta * VX;
	}

	public void moveUp (double delta) {
		Y -= delta * VY;
	}
	
	public void moveDown (double delta) {
		Y += delta * VY;
	}
	
	public void shoot () {
		// ATIRA
		nextShot = 0;
	}
	
	public void explode (double currentTime) {
		explosion_start = currentTime;
		this.state = State.EXPLODING;
	}

	public double getExplosion_start() {
		return explosion_start;
	}

	public void setExplosion_start(double explosion_start) {
		this.explosion_start = explosion_start;
	}

	public double getExplosion_end() {
		return explosion_end;
	}

	public void setExplosion_end(double explosion_end) {
		this.explosion_end = explosion_end;
	}

	public long getNextShot() {
		return nextShot;
	}

	public void setNextShot(long nextShot) {
		this.nextShot = nextShot;
	}
}
