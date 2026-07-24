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
    }

    public void resetPhase() {
        this.enemies.clear();
        this.activeBoss = null;
        this.bossDefeated = false;
    }

    public void spawnEntity(Spawner spawn) {
        String entityType = spawn.getEntity();
        switch (entityType) {
            case "INIMIGO": createRegularEnemy(spawn); break;
            case "CHEFE": createBoss(spawn); break; 
        }
    }

    private void createRegularEnemy(Spawner spawn) {
        double v = 0.2;
        double angle = (3 * Math.PI) / 2;
        double rv = 0.0;
        
        switch (spawn.getType()) {
            case 1: enemies.add(new CircleEnemy(spawn.getX(), spawn.getY(), v, angle, rv, 500)); break;
            case 2: enemies.add(new DiamondEnemy(spawn.getX(), spawn.getY(), v, angle, rv)); break;
        }
    }

    private void createBoss(Spawner spawn) {
        int hpDoChefe = spawn.getHp(); 
        switch (spawn.getType()) {
            case 1:
                activeBoss = new StaticBoss(State.ACTIVE, spawn.getX(), spawn.getY(), hpDoChefe);
                enemies.add(activeBoss);
                break;
            case 2:
                activeBoss = new MovingBoss(State.ACTIVE, spawn.getX(), spawn.getY(), hpDoChefe);
                enemies.add(activeBoss);
                break;
        }
    }

    public void update(long currentTime, long delta, Player player, ProjectileManager projManager) {
        for (Enemy enemy : enemies) {
            enemy.update(currentTime, delta);
            enemy.tryToShoot(currentTime, player, projManager);
        }

        if (activeBoss != null && activeBoss.getState() == State.INACTIVE) {
            bossDefeated = true;
        }

        enemies.removeIf(enemy -> enemy.getState() == State.INACTIVE);
    }

    public void draw(long currentTime) {
        for (Enemy enemy : enemies) enemy.draw(currentTime);
    }

    public List<Enemy> getEnemies() { return enemies; }
    public boolean isBossDefeated() { return bossDefeated; }
}