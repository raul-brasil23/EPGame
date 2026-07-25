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
        for (PlayerProjectile p : this.playerProjectiles) {
            p.update(currentTime, delta);
        }
        
        for (EnemyProjectile e : this.enemyProjectiles) {
            e.update(currentTime, delta);
        }

        // Limpa todos os tiros que saíram da tela ou colidiram
        this.playerProjectiles.removeIf(p -> p.getState() == State.INACTIVE);
        this.enemyProjectiles.removeIf(e -> e.getState() == State.INACTIVE);
    }

    public void draw(long currentTime) {
        for (PlayerProjectile p : this.playerProjectiles) {
            p.draw(currentTime);
        }
        
        for (EnemyProjectile e : this.enemyProjectiles) {
            e.draw(currentTime);
        }
    }

    public List<PlayerProjectile> getPlayerProjectiles() { return playerProjectiles; }
    public List<EnemyProjectile> getEnemyProjectiles() { return enemyProjectiles; }
}