package Managers;

import Entities.Enemy;
import Entities.CircleEnemy;
import Entities.DiamondEnemy;
import Entities.Player;
import Utils.GameLib;
import Utils.State;

import java.util.ArrayList;
import java.util.List;

public class EnemyManager {
    private List<Enemy> enemies;
    private long nextEnemy1;
    private long nextEnemy2;
    private double enemy2_spawnX;
    private int enemy2_count;

    public EnemyManager(long currentTime) {
        this.enemies = new ArrayList<>();
        this.nextEnemy1 = currentTime + 2000;
        this.nextEnemy2 = currentTime + 7000;
        this.enemy2_spawnX = GameLib.WIDTH * 0.20;
        this.enemy2_count = 0;
    }

    // Agora o ProjectileManager entra como parâmetro para criar os tiros
    public void update(long currentTime, long delta, Player player, ProjectileManager projManager) {
        
        if (currentTime > nextEnemy1) {
            double x = Math.random() * (GameLib.WIDTH - 20.0) + 10.0;
            double v = 0.20 + Math.random() * 0.15;
            enemies.add(new CircleEnemy(x, -10.0, v, (3 * Math.PI) / 2, 0.0, currentTime + 500));
            this.nextEnemy1 = currentTime + 500;
        }

        if (currentTime > nextEnemy2) {
            enemies.add(new DiamondEnemy(this.enemy2_spawnX, -10.0, 0.42, (3 * Math.PI) / 2, 0.0));
            this.enemy2_count++;
            
            if (this.enemy2_count < 10) {
                this.nextEnemy2 = currentTime + 120;
            } else {
                this.enemy2_count = 0;
                this.enemy2_spawnX = Math.random() > 0.5 ? GameLib.WIDTH * 0.2 : GameLib.WIDTH * 0.8;
                this.nextEnemy2 = (long) (currentTime + 3000 + Math.random() * 3000);
            }
        }

        for (Enemy enemy : enemies) {
            enemy.update(currentTime, delta);

            if (enemy instanceof CircleEnemy) {
                CircleEnemy ce = (CircleEnemy) enemy;
                if (ce.canShoot(currentTime, player.getY())) {
                    projManager.spawnCircleEnemyProjectile(ce);
                }
            } else if (enemy instanceof DiamondEnemy) {
                DiamondEnemy de = (DiamondEnemy) enemy;
                if (de.canShoot()) {
                    projManager.spawnDiamondEnemyProjectiles(de);
                }
            }
        }

        enemies.removeIf(enemy -> enemy.getState() == State.INACTIVE);
    }

    public void draw(long currentTime) {
        for (Enemy enemy : enemies) enemy.draw(currentTime);
    }

    public List<Enemy> getEnemies() { return enemies; }
}