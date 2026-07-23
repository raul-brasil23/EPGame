import Entities.Player;
import Managers.BackgroundManager;
import Managers.CollisionManager;
import Managers.EnemyManager;
import Managers.LevelManager;
import Managers.ProjectileManager;
import Utils.GameLib;

/***********************************************************************/
/*                                                                     */
/* Para jogar:                                                         */
/*                                                                     */
/*    - cima, baixo, esquerda, direita: movimentação do player.        */
/*    - control: disparo de projéteis.                                 */
/*    - ESC: para sair do jogo.                                        */
/*                                                                     */
/***********************************************************************/

public class Main {
	
	/* Espera, sem fazer nada, até que o instante de tempo atual seja */
	/* maior ou igual ao instante especificado no parâmetro "time.    */
	public static void busyWait(long time){
		while(System.currentTimeMillis() < time) Thread.yield();
	}
	
	/* Método principal */
	public static void main(String [] args){

		/* Indica que o jogo está em execução */
		boolean running = true;

		/* variáveis usadas no controle de tempo efetuado no main loop */
		long delta;
		long currentTime = System.currentTimeMillis();

		/* Inicialização dos Gerenciadores (Managers) e do Player */
		Player player = new Player(GameLib.WIDTH / 2, GameLib.HEIGHT * 0.90, currentTime);
		BackgroundManager backgroundManager = new BackgroundManager();
		EnemyManager enemyManager = new EnemyManager(currentTime);
		ProjectileManager projectileManager = new ProjectileManager();
		CollisionManager collisionManager = new CollisionManager();
		LevelManager levelManager = new LevelManager("Levels/level_config.txt", currentTime);
						
		/* iniciando interface gráfica */
		GameLib.initGraphics();
		//GameLib.initGraphics_SAFE_MODE();
		
		while(running){
		
			delta = System.currentTimeMillis() - currentTime;
			currentTime = System.currentTimeMillis();
			
			/********************************************/
			/* 1. Verificando entrada do usuário        */
			/********************************************/
			
			player.move(delta);
			
			if(player.isShooting(currentTime)) {
				projectileManager.spawnPlayerProjectile(player);
			}
			
			if(GameLib.iskeyPressed(GameLib.KEY_ESCAPE)) {
				running = false;
			}
			
			/********************************************/
			/* 2. Atualizações de estados e Colisões    */
			/********************************************/
			
			backgroundManager.update(delta);
			player.update(currentTime, delta);

			levelManager.update(currentTime, enemyManager);

			enemyManager.update(currentTime, delta, player, projectileManager);
			projectileManager.update(currentTime, delta);
			
			collisionManager.checkCollisions(player, enemyManager, projectileManager, currentTime);

			/********************************************/
			/* 3. Desenho da cena                       */
			/********************************************/
			
			backgroundManager.draw();
			player.draw(currentTime);
			enemyManager.draw(currentTime);
			projectileManager.draw(currentTime);
			
			/* chamada a display() da classe GameLib atualiza o desenho exibido pela interface do jogo. */
			GameLib.display();
			
			/* faz uma pausa de modo que cada execução do laço do main loop demore aproximadamente 3 ms. */
			busyWait(currentTime + 3);
		}
		
		System.exit(0);
	}
}