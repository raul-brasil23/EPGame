package Managers;

import Entities.Player;
import Entities.Enemy;
import Entities.PlayerProjectile;
import Entities.EnemyProjectile;
import Utils.State;

public class CollisionManager {

    public void checkCollisions(Player player, EnemyManager enemyManager, ProjectileManager projectileManager, long currentTime) {
        
        // 1. Colisões do Player contra os inimigos e tiros inimigos
        if (player.getState() == State.ACTIVE) {
            
            // Player vs Tiros Inimigos
            for (EnemyProjectile ep : projectileManager.getEnemyProjectiles()) {
                if (ep.getState() == State.ACTIVE) {
                    double dist = calculateDistance(player.getX(), player.getY(), ep.getX(), ep.getY());
                    if (dist < (player.getRadius() + ep.getRadius()) * 0.8) {
                        player.explode(currentTime, 2000);
                        ep.setState(State.INACTIVE);
                    }
                }
            }

            // Player vs Inimigos (Navinhas)
            for (Enemy enemy : enemyManager.getEnemies()) {
                if (enemy.getState() == State.ACTIVE) {
                    double dist = calculateDistance(player.getX(), player.getY(), enemy.getX(), enemy.getY());
                    if (dist < (player.getRadius() + enemy.getRadius()) * 0.8) {
                        player.explode(currentTime, 2000);
                        enemy.explode(currentTime, 500);
                    }
                }
            }
        }

        // 2. Colisões dos tiros do Player contra os Inimigos
        for (PlayerProjectile pp : projectileManager.getPlayerProjectiles()) {
            if (pp.getState() == State.ACTIVE) {
                for (Enemy enemy : enemyManager.getEnemies()) {
                    if (enemy.getState() == State.ACTIVE) {
                        double dist = calculateDistance(pp.getX(), pp.getY(), enemy.getX(), enemy.getY());
                        // Diferente do player, a colisão aqui usa apenas o raio do inimigo
                        if (dist < enemy.getRadius()) {
                            enemy.explode(currentTime, 500);
                            pp.setState(State.INACTIVE);
                        }
                    }
                }
            }
        }
    }

    // Método auxiliar para não repetir o cálculo da distância euclidiana toda hora
    private double calculateDistance(double x1, double y1, double x2, double y2) {
        double dx = x1 - x2;
        double dy = y1 - y2;
        return Math.sqrt(dx * dx + dy * dy);
    }
}