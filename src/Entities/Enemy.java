
public abstract class Enemy extends Entity{
	private double V;					// velocidades
	private double angle;				// ângulos (indicam direção do movimento)
	private double RV;					// velocidades de rotação
	private double explosion_start = 0;			// instantes dos inícios das explosões
	private double explosion_end = 0;			// instantes dos finais da explosões
	// nextEnemy
	
	public Enemy (State state, double x, double y, double v, double angle, double rv, double radius) {
		super (state, x, y, radius);
		this.V = v;
		this.angle = angle;
		this.RV = rv;
	}
}
