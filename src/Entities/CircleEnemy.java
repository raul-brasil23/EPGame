
public class CircleEnemy extends Enemy {
	private long nextShot;				// instantes do próximo tiro
	
	public CircleEnemy (double x, double y, double v, double angle, double rv, long nextShot) {
		super (x, y, v, angle, rv, 9.0);
		this.nextShot = nextShot;
	}
}