
public abstract class Projectile extends Entity{
	
	public Projectile (State state, double x, double y, double vx, double vy) {
		super (state, x, y, vx, vy, 0.0);
	}
}