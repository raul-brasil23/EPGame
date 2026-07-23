package Managers;

import Entities.Enemy;
import Entities.CircleEnemy;
import Entities.DiamondEnemy;
import Entities.Player;
import Utils.Spawner;
import Utils.State;

import java.util.ArrayList;
import java.util.List;

public class EnemyManager {
    private List<Enemy> enemies;

    public EnemyManager(long currentTime) {
        this.enemies = new ArrayList<>();
    }

    public void spawnEntity(Spawner spawn) {
        String entity = spawn.getEntity();
        int type = spawn.getType();
        double x = spawn.getX();
        double y = spawn.getY();

        double v = 0.2; // v: Velocidade de movimentação padrão
        double angle = (3 * Math.PI) / 2; // angle: Ângulo em radianos (apontando para baixo)
        double rv = 0.0; // rv: Velocidade de rotação
        long nextShot = System.currentTimeMillis() + 500; // nextShot: Tempo para atirar (meio segundo após nascer)

        if (entity.equals("INIMIGO")) {
            // Verifica qual é o design do inimigo (1 ou 2, conforme PDF)
            if (type == 1) {
                // Exemplo: cria o inimigo básico que atira reto
                enemies.add(new CircleEnemy(x, y, v, angle, rv, nextShot)); 
            } else if (type == 2) {
                // Exemplo: cria o inimigo que atira na diagonal
                enemies.add(new DiamondEnemy(x, y, v, angle, rv)); 
            }
        } 
        // else if (entidade.equals("CHEFE")) {
        //     int hpChefe = spawn.getHp();
        //     if (tipo == 1) {
        //         enemies.add(new BossType1(x, y, hpChefe));
        //     }
        // }
    }

    // Agora o ProjectileManager entra como parâmetro para criar os tiros
    public void update(long currentTime, long delta, Player player, ProjectileManager projManager) {

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