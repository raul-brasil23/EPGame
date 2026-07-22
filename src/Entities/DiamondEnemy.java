
public class DiamondEnemy extends Enemy {
	private double spawnX;						// coordenada x do próximo inimigo tipo 2 a aparecer
	private int enemyCount;						// contagem de inimigos tipo 2 (usada na "formação de voo")
	
	public DiamondEnemy (State state, double x, double y, double v, double angle, double rv, double spawnX, int count) {
		super (state, x, y, v, angle, rv, 12.0);
		this.spawnX = spawnX;
		this.enemyCount = count;
	}
}