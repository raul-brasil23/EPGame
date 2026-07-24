package Managers;

import Entities.PlayerProjectile;
import Entities.EnemyProjectile;
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

    public void update(long currentTime, long delta) {
        for (PlayerProjectile p : playerProjectiles) p.update(currentTime, delta);
        for (EnemyProjectile e : enemyProjectiles) e.update(currentTime, delta);

        playerProjectiles.removeIf(p -> p.getState() == State.INACTIVE);
        enemyProjectiles.removeIf(e -> e.getState() == State.INACTIVE);
    }

    public void draw(long currentTime) {
        for (PlayerProjectile p : playerProjectiles) p.draw(currentTime);
        for (EnemyProjectile e : enemyProjectiles) e.draw(currentTime);
    }

    public List<PlayerProjectile> getPlayerProjectiles() { return playerProjectiles; }
    public List<EnemyProjectile> getEnemyProjectiles() { return enemyProjectiles; }
}