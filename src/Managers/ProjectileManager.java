package Managers;

import Entities.Player;
import Entities.PlayerProjectile;
import Entities.EnemyProjectile;
import Entities.CircleEnemy;
import Entities.DiamondEnemy;
import Utils.State;

import java.util.ArrayList;
import java.util.List;

public class ProjectileManager {
    private List<PlayerProjectile> playerProjectiles;
    private List<EnemyProjectile> enemyProjectiles;

    public ProjectileManager() {
        this.playerProjectiles = new ArrayList<>();
        this.enemyProjectiles = new ArrayList<>();
    }

    // Cria o tiro do jogador
    public void spawnPlayerProjectile(Player player) {
        double x = player.getX();
        double y = player.getY() - 2 * player.getRadius();
        playerProjectiles.add(new PlayerProjectile(x, y));
    }

    // Cria o tiro do Inimigo 1 (um único tiro)
    public void spawnCircleEnemyProjectile(CircleEnemy enemy) {
        double vx = Math.cos(enemy.getAngle()) * 0.45;
        double vy = Math.sin(enemy.getAngle()) * 0.45 * (-1.0);
        enemyProjectiles.add(new EnemyProjectile(enemy.getX(), enemy.getY(), vx, vy));
    }

    // Cria os tiros do Inimigo 2 (três tiros em ângulos diferentes)
    public void spawnDiamondEnemyProjectiles(DiamondEnemy enemy) {
        double[] angles = { Math.PI/2 + Math.PI/8, Math.PI/2, Math.PI/2 - Math.PI/8 };
        
        for (double angle : angles) {
            double a = angle + Math.random() * Math.PI/6 - Math.PI/12;
            double vx = Math.cos(a) * 0.30;
            double vy = Math.sin(a) * 0.30;
            enemyProjectiles.add(new EnemyProjectile(enemy.getX(), enemy.getY(), vx, vy));
        }
    }

    public void update(long currentTime, long delta) {
        for (PlayerProjectile p : playerProjectiles) p.update(currentTime, delta);
        for (EnemyProjectile e : enemyProjectiles) e.update(currentTime, delta);

        // Remove os projéteis inativos para não vazar memória
        playerProjectiles.removeIf(p -> p.getState() == State.INACTIVE);
        enemyProjectiles.removeIf(e -> e.getState() == State.INACTIVE);
    }

    public void draw(long currentTime) {
        for (PlayerProjectile p : playerProjectiles) p.draw(currentTime);
        for (EnemyProjectile e : enemyProjectiles) e.draw(currentTime);
    }

    // Getters para o CollisionManager
    public List<PlayerProjectile> getPlayerProjectiles() { return playerProjectiles; }
    public List<EnemyProjectile> getEnemyProjectiles() { return enemyProjectiles; }
}