package Managers;

import Entities.Player;
import Entities.Enemy;
import Entities.PlayerProjectile;
import Entities.EnemyProjectile;
import Entities.PowerUp;
import Utils.State;

public class CollisionManager {

    public void checkCollisions(Player player, EnemyManager enemyManager, ProjectileManager projectileManager, PowerUpManager powerUpManager, long currentTime) {
        
        if (player.getState() == State.ACTIVE) {
            if (!player.isInvulnerable(currentTime)) {
                for (EnemyProjectile ep : projectileManager.getEnemyProjectiles()) {
                    if (ep.getState() == State.ACTIVE) {
                        double dist = calculateDistance(player.getX(), player.getY(), ep.getX(), ep.getY());
                        if (dist < (player.getRadius() + ep.getRadius()) * 0.8) {
                            player.takeDamage(currentTime); 
                            ep.setState(State.INACTIVE);
                            break; 
                        }
                    }
                }
            }

            if (!player.isInvulnerable(currentTime)) {
                for (Enemy enemy : enemyManager.getEnemies()) {
                    if (enemy.getState() == State.ACTIVE) {
                        double dist = calculateDistance(player.getX(), player.getY(), enemy.getX(), enemy.getY());
                        if (dist < (player.getRadius() + enemy.getRadius()) * 0.8) {
                            player.takeDamage(currentTime);
                            enemy.takeDamage(1, currentTime);
                            break; 
                        }
                    }
                }
            }
        }

        for (PlayerProjectile pp : projectileManager.getPlayerProjectiles()) {
            if (pp.getState() == State.ACTIVE) {
                for (Enemy enemy : enemyManager.getEnemies()) {
                    if (enemy.getState() == State.ACTIVE) {
                        double dist = calculateDistance(pp.getX(), pp.getY(), enemy.getX(), enemy.getY());
                        
                        if (dist < enemy.getRadius()) {
                            enemy.takeDamage(1, currentTime);
                            pp.setState(State.INACTIVE);
                        }
                    }
                }
            }
        }

        if (player.getState() == State.ACTIVE) {
            for (PowerUp powerUp : powerUpManager.getPowerUps()) {
                if (powerUp.getState() == State.ACTIVE) {
                    double dist = calculateDistance(player.getX(), player.getY(), powerUp.getX(), powerUp.getY());
                    
                    if (dist < (player.getRadius() + powerUp.getRadius()) * 0.8) {
                        // POLIMORFISMO PURO APLICADO AO POWER-UP!
                        powerUp.applyEffect(player);
                        powerUp.setState(State.INACTIVE);
                    }
                }
            }
        }
    }

    private double calculateDistance(double x1, double y1, double x2, double y2) {
        double dx = x1 - x2;
        double dy = y1 - y2;
        return Math.sqrt(dx * dx + dy * dy);
    }
}