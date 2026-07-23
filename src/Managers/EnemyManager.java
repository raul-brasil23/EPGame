package Managers;

import Entities.*;
import Utils.Spawner;
import Utils.State;

import java.util.ArrayList;
import java.util.List;

public class EnemyManager {
    private List<Enemy> enemies;
    private Boss activeBoss; // Guarda a referência do chefe da fase atual
    private boolean bossDefeated;

    public EnemyManager() {
        this.enemies = new ArrayList<>();
        this.bossDefeated = false;
    }

    // Método chamado quando mudamos de fase para limpar a tela
    public void resetPhase() {
        this.enemies.clear();
        this.activeBoss = null;
        this.bossDefeated = false;
    }

    // Criação modularizada usando switch para melhorar a legibilidade
    public void spawnEntity(Spawner spawn, ProjectileManager projManager) {
        String entityType = spawn.getEntity();
        
        switch (entityType) {
            case "INIMIGO":
                createRegularEnemy(spawn);
                break;
            case "CHEFE":
                createBoss(spawn, projManager);
                break;
            case "POWERUP":
                // Ignorado por enquanto, o sistema está pronto para recebê-lo
                break;
        }
    }

    private void createRegularEnemy(Spawner spawn) {
        double v = 0.2;
        double angle = (3 * Math.PI) / 2;
        double rv = 0.0;
        long nextShot = System.currentTimeMillis() + 500;

        switch (spawn.getType()) {
            case 1:
                enemies.add(new CircleEnemy(spawn.getX(), spawn.getY(), v, angle, rv, nextShot));
                break;
            case 2:
                enemies.add(new DiamondEnemy(spawn.getX(), spawn.getY(), v, angle, rv));
                break;
        }
    }

    private void createBoss(Spawner spawn, ProjectileManager projManager) {
        // Pegamos o HP que foi lido do arquivo txt pelo Spawner
        int hpDoChefe = spawn.getHp(); 

        switch (spawn.getType()) {
            case 1:
                activeBoss = new StaticBoss(State.ACTIVE, spawn.getX(), spawn.getY(), hpDoChefe, projManager);
                enemies.add(activeBoss);
                break;
            case 2:
                activeBoss = new MovingBoss(State.ACTIVE, spawn.getX(), spawn.getY(), hpDoChefe, projManager);
                enemies.add(activeBoss);
                break;
        }
    }

    public void update(long currentTime, long delta, Player player, ProjectileManager projManager) {
        for (Enemy enemy : enemies) {
            enemy.update(currentTime, delta);

            // Delegação de tiros dos inimigos comuns
            if (enemy instanceof CircleEnemy) {
                CircleEnemy ce = (CircleEnemy) enemy;
                if (ce.canShoot(currentTime, player.getY())) {
                    projManager.spawnCircleEnemyProjectile(ce);
                }
            } else if (enemy instanceof DiamondEnemy) {
                DiamondEnemy de = (DiamondEnemy) enemy;
                if (de.isReadyToShoot()) {
                    projManager.spawnDiamondEnemyProjectiles(de);
                    de.resetShot();
                }
            }
        }

        // Verifica se o chefe morreu (estado Inativo indica que a explosão terminou)
        if (activeBoss != null && activeBoss.getState() == State.INACTIVE) {
            bossDefeated = true;
        }

        // Remove da lista todos os inimigos destruídos/fora da tela
        enemies.removeIf(enemy -> enemy.getState() == State.INACTIVE);
    }

    public void draw(long currentTime) {
        for (Enemy enemy : enemies) enemy.draw(currentTime);
    }

    public List<Enemy> getEnemies() { return enemies; }
    
    // Método para o LevelManager saber a hora de avançar a fase
    public boolean isBossDefeated() { return bossDefeated; }
}