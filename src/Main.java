import Entities.Player;
import Managers.BackgroundManager;
import Managers.CollisionManager;
import Managers.EnemyManager;
import Managers.LevelManager;
import Managers.PowerUpManager;
import Managers.ProjectileManager;
import Utils.GameLib;
import Utils.State;

public class Main {
	
	public static void busyWait(long time){
		while(System.currentTimeMillis() < time) Thread.yield();
	}
	
	public static void main(String [] args){

		boolean running = true;
		long delta;
		long currentTime = System.currentTimeMillis();

		BackgroundManager backgroundManager = new BackgroundManager();
		EnemyManager enemyManager = new EnemyManager();
		ProjectileManager projectileManager = new ProjectileManager();
		CollisionManager collisionManager = new CollisionManager();
		PowerUpManager powerUpManager = new PowerUpManager();
		LevelManager levelManager = new LevelManager("Levels/level_config.txt", currentTime);
		Player player = new Player(GameLib.WIDTH / 2, GameLib.HEIGHT * 0.90, currentTime, levelManager.getStartHP());
						
		GameLib.initGraphics();
		
		while(running){
		
			delta = System.currentTimeMillis() - currentTime;
			currentTime = System.currentTimeMillis();
			
			// 1. Verificando entrada do usuário 
			// A Movimentação agora está sendo chamada implicitamente dentro de player.update(), 
            // mas como a base já separa teclado, você pode manter essa arquitetura focada no OOP
			player.update(currentTime, delta); 
			player.tryToShoot(currentTime, player, projectileManager); // Parâmetro injetado pro Player também!
			
			if(GameLib.iskeyPressed(GameLib.KEY_ESCAPE)) {
				System.out.println("Já tá desistindo? :(");
				running = false;
			}
			
			// 2. Atualizações de estados e Colisões 
			backgroundManager.update(delta);

			if (player.getState() == State.INACTIVE) {
				System.out.println("GAME OVER! A nave foi destruída.");
				running = false;
			}

			levelManager.update(currentTime, enemyManager, projectileManager, powerUpManager);
			enemyManager.update(currentTime, delta, player, projectileManager);
			projectileManager.update(currentTime, delta);
			powerUpManager.update(currentTime, delta);
			collisionManager.checkCollisions(player, enemyManager, projectileManager, powerUpManager, currentTime);

			// 3. Desenho da cena 
			backgroundManager.draw();
			player.draw(currentTime);
			enemyManager.draw(currentTime);
			projectileManager.draw(currentTime);
			powerUpManager.draw(currentTime);
			
			GameLib.display();
			
			busyWait(currentTime + 3);
		}
		
		System.exit(0);
	}
}