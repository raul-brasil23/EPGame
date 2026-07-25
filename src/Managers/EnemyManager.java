package Managers;

import Entities.*;
import Utils.Spawner;
import Utils.State;
import java.util.ArrayList;
import java.util.List;

public class EnemyManager {
    
    private List<Enemy> enemies;
    private Boss activeBoss; 
    private boolean bossDefeated;

    public EnemyManager() {
        this.enemies = new ArrayList<>();
        this.bossDefeated = false;
        this.activeBoss = null;
    }

    public void resetPhase() {
        this.enemies.clear();
        this.activeBoss = null;
        this.bossDefeated = false;
    }

    // Lê o arquivo de levels e instancia o objeto correspondente no jogo
    public void spawnEntity(Spawner spawn) {
        String entityType = spawn.getEntity();
        
        switch (entityType) {
            case "INIMIGO": 
                createRegularEnemy(spawn); 
                break;
            case "CHEFE": 
                createBoss(spawn); 
                break; 
        }
    }

    private void createRegularEnemy(Spawner spawn) {
        double angle = (3 * Math.PI) / 2;
        double rv = 0.0;
        
        switch (spawn.getType()) {
            case 1: 
                this.enemies.add(new CircleEnemy(spawn.getX(), spawn.getY(), 0.2, angle, rv, 500)); 
                break;
            case 2: 
                this.enemies.add(new DiamondEnemy(spawn.getX(), spawn.getY(), 0.42, angle, rv)); 
                break;
        }
    }

    private void createBoss(Spawner spawn) {
        int hpDoChefe = spawn.getHp(); 
        
        switch (spawn.getType()) {
            case 1:
                this.activeBoss = new StaticBoss(State.ACTIVE, spawn.getX(), spawn.getY(), hpDoChefe);
                this.enemies.add(this.activeBoss);
                break;
            case 2:
                this.activeBoss = new MovingBoss(State.ACTIVE, spawn.getX(), spawn.getY(), hpDoChefe);
                this.enemies.add(this.activeBoss);
                break;
        }
    }

    public void update(long currentTime, long delta, Player player, ProjectileManager projManager) {
        for (Enemy enemy : this.enemies) {
            enemy.update(currentTime, delta);
            enemy.tryToShoot(currentTime, player, projManager);
        }

        // Marca a fase como concluída assim que a explosão do boss termina
        if (this.activeBoss != null && this.activeBoss.getState() == State.INACTIVE) {
            this.bossDefeated = true;
        }

        // Remove inimigos mortos da lista para evitar memory leak
        this.enemies.removeIf(enemy -> enemy.getState() == State.INACTIVE);
    }

    public void draw(long currentTime) {
        for (Enemy enemy : this.enemies) {
            enemy.draw(currentTime);
        }
    }

    public List<Enemy> getEnemies() { return enemies; }
    public boolean isBossDefeated() { return bossDefeated; }
}