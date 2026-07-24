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
        this.player = new Player(GameLib.WIDTH / 2, GameLib.HEIGHT * 0.90, currentTime, levelManager.getStartHP());
        this.enemyManager = new EnemyManager();
        this.projectileManager = new ProjectileManager();
        this.collisionManager = new CollisionManager();
        this.powerUpManager = new PowerUpManager();
    }

    @Override
    public void update(long currentTime, long delta) {
        // A movimentação agora roda automaticamente dentro do player.update()!
        player.tryToShoot(currentTime, player, projectileManager);
        player.update(currentTime, delta);

        if (player.getState() == State.INACTIVE) {
            screenManager.setScreen(new GameOverScreen(screenManager));
            return; // Sai do método para não continuar atualizando a lógica morta
        }

        // Assinatura limpa: o LevelManager não precisa saber sobre os projéteis
        levelManager.update(currentTime, enemyManager, powerUpManager);
        enemyManager.update(currentTime, delta, player, projectileManager);
        projectileManager.update(currentTime, delta);
        powerUpManager.update(currentTime, delta);
        collisionManager.checkCollisions(player, enemyManager, projectileManager, powerUpManager, currentTime);

        if (levelManager.isVictory()) {
            screenManager.setScreen(new VictoryScreen(screenManager));
        }
    }

    @Override
    public void draw(long currentTime) {
        player.draw(currentTime);
        enemyManager.draw(currentTime);
        projectileManager.draw(currentTime);
        powerUpManager.draw(currentTime);
        levelManager.drawLevelText(currentTime); // Fade out do "LEVEL X"
    }
}