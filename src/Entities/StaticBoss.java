package Entities;

import Behaviors.OscillatingMovement;
import Managers.ProjectileManager;
import Utils.State;
import Utils.GameLib;

public class StaticBoss extends Boss {

    public StaticBoss(State state, double x, double y, int hp) {
        super(state, x, y, 40.0, new OscillatingMovement(), 200, hp); 
        this.lastShootTime = System.currentTimeMillis();
    }

    @Override
    protected void performExtraActions(long currentTime, long delta) {}

    @Override
    public void tryToShoot(long currentTime, Player player, ProjectileManager projManager) {
        if (this.state == State.ACTIVE && (currentTime - lastShootTime >= shootInterval)) {
            double speed = 0.40; 
            double startX = this.getX();
            double startY = this.getY() + this.getRadius(); 
            
            double[] angles = { 
                Math.PI/2 - Math.PI/6, Math.PI/2 - Math.PI/12, Math.PI/2, Math.PI/2 + Math.PI/12, Math.PI/2 + Math.PI/6   
            };

            for (double angle : angles) {
                double projectileVx = Math.cos(angle) * speed;
                double projectileVy = Math.sin(angle) * speed;
                projManager.getEnemyProjectiles().add(new EnemyProjectile(startX, startY, projectileVx, projectileVy));
            }
            lastShootTime = currentTime;
        }
    }

    @Override
    public void draw(long currentTime) {
        if (this.state == State.EXPLODING) {
            double alpha = (double) (currentTime - (deadTime > 0 ? deadTime : this.explosion_start)) / 500.0;
            if (alpha > 1.0) alpha = 1.0; 
            GameLib.drawExplosion(this.getX(), this.getY(), alpha);
        } 
        else if (this.state == State.ACTIVE) {
            GameLib.setColor(java.awt.Color.RED);
            GameLib.drawCircle(this.getX(), this.getY(), this.getRadius());
        
            double pulse = Math.sin(currentTime * 0.005) * 10; 
            GameLib.setColor(java.awt.Color.YELLOW);
            GameLib.drawCircle(this.getX(), this.getY(), (this.getRadius() / 2) + pulse);
        }
    }
}