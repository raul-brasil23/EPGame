package Managers;

import Entities.Player;
import Entities.Enemy;
import Entities.Boss; 
import Entities.PlayerProjectile;
import Entities.EnemyProjectile;
import Utils.State;

public class CollisionManager {

    public void checkCollisions(Player player, EnemyManager enemyManager, ProjectileManager projectileManager, long currentTime) {
        
        // 1. Colisões do Player contra os inimigos e tiros inimigos
        if (player.getState() == State.ACTIVE) {
            
            // Só verifica colisões se o jogador NÃO estiver invulnerável
            if (!player.isInvulnerable(currentTime)) {
                
                // Player vs Tiros Inimigos
                for (EnemyProjectile ep : projectileManager.getEnemyProjectiles()) {
                    if (ep.getState() == State.ACTIVE) {
                        double dist = calculateDistance(player.getX(), player.getY(), ep.getX(), ep.getY());
                        if (dist < (player.getRadius() + ep.getRadius()) * 0.8) {
                            
                            player.takeDamage(currentTime); 
                            ep.setState(State.INACTIVE);
                            break; // Sai do loop para garantir que tome apenas 1 tiro por vez
                        }
                    }
                }
            }

            // Verifica de novo, pois o jogador pode ter tomado um tiro e ficado invulnerável no bloco acima
            if (!player.isInvulnerable(currentTime)) {
                
                // Player vs Inimigos (Navinhas e Chefes)
                for (Enemy enemy : enemyManager.getEnemies()) {
                    if (enemy.getState() == State.ACTIVE) {
                        double dist = calculateDistance(player.getX(), player.getY(), enemy.getX(), enemy.getY());
                        if (dist < (player.getRadius() + enemy.getRadius()) * 0.8) {
                            
                            // O Player toma dano e ATIVA SUA INVULNERABILIDADE
                            player.takeDamage(currentTime);
                            
                            // Lógica de dano para o inimigo (Agora restrita a 1 vez por colisão/segundo)
                            if (enemy instanceof Boss) {
                                Boss boss = (Boss) enemy;
                                boss.takeDamage(1);
                                if (boss.getHp() <= 0) boss.explode(currentTime, 500);
                            } else {
                                enemy.explode(currentTime, 500);
                            }
                            
                            break; // Sai do loop para não dar dano em dois inimigos no mesmo frame
                        }
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
                        
                        if (dist < enemy.getRadius()) {
                            
                            // Lógica do chefe tomar dano
                            if (enemy instanceof Boss) {
                                Boss boss = (Boss) enemy;
                                boss.takeDamage(1); 
                                
                                if (boss.getHp() <= 0) {
                                    boss.explode(currentTime, 500);
                                }
                            } else {
                                // Inimigo comum explode com 1 tiro
                                enemy.explode(currentTime, 500);
                            }
                            
                            pp.setState(State.INACTIVE);
                        }
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