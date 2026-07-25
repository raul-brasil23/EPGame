package Screens;

import Entities.Player;
import Managers.CollisionManager;
import Managers.EnemyManager;
import Managers.LevelManager;
import Managers.PowerUpManager;
import Managers.ProjectileManager;
import Managers.ScreenManager;
import Utils.GameLib;
import Utils.State;

public class PlayingScreen implements Screen {
    
    private ScreenManager screenManager;
    private EnemyManager enemyManager;
    private ProjectileManager projectileManager;
    private CollisionManager collisionManager;
    private PowerUpManager powerUpManager;
    private LevelManager levelManager;
    private Player player;

    public PlayingScreen(ScreenManager screenManager, long currentTime) {
        this.screenManager = screenManager;
        this.levelManager = new LevelManager("Levels/level_config.txt", currentTime);
        this.player = new Player(GameLib.WIDTH / 2, GameLib.HEIGHT * 0.90, currentTime, this.levelManager.getStartHP());
        this.enemyManager = new EnemyManager();
        this.projectileManager = new ProjectileManager();
        this.collisionManager = new CollisionManager();
        this.powerUpManager = new PowerUpManager();
    }

    // Organiza a ordem com a qual todos os elementos são atualizados em cada frame do jogo
    @Override
    public void update(long currentTime, long delta) {
        this.player.tryToShoot(currentTime, this.player, this.projectileManager);
        this.player.update(currentTime, delta);

        if (this.player.getState() == State.INACTIVE) {
            this.screenManager.setScreen(new GameOverScreen(this.screenManager));
            return; 
        }

        this.levelManager.update(currentTime, this.enemyManager, this.powerUpManager);
        this.enemyManager.update(currentTime, delta, this.player, this.projectileManager);
        this.projectileManager.update(currentTime, delta);
        this.powerUpManager.update(currentTime, delta);
        this.collisionManager.checkCollisions(this.player, this.enemyManager, this.projectileManager, this.powerUpManager, currentTime);

        if (this.levelManager.isVictory()) {
            this.screenManager.setScreen(new VictoryScreen(this.screenManager));
        }
    }

    @Override
    public void draw(long currentTime) {
        this.player.draw(currentTime);
        this.enemyManager.draw(currentTime);
        this.projectileManager.draw(currentTime);
        this.powerUpManager.draw(currentTime);
        this.levelManager.drawLevelText(currentTime); 
    }
}