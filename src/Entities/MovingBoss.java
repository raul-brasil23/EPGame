package Entities;

import Behaviors.DiagonalBounceMovement;
import Managers.ProjectileManager;
import Utils.State;
import Utils.GameLib;

public class MovingBoss extends Boss {

    public MovingBoss(State state, double x, double y, int hp) {
        super(state, x, y, 35.0, new DiagonalBounceMovement(), 300, hp);
        this.lastShootTime = System.currentTimeMillis();
    }

    @Override
    protected void performExtraActions(long currentTime, long delta) {}

    @Override
    public void tryToShoot(long currentTime, Player player, ProjectileManager projManager) {
        if (this.state == State.ACTIVE && currentTime - lastShootTime >= shootInterval) {
            double speed = 0.50; 
            double startX = this.getX();
            double startY = this.getY() + this.getRadius(); 

            double sweepAngle = (Math.PI / 2) + Math.sin(currentTime * 0.003) * (Math.PI / 3);
            double[] angles = { sweepAngle - 0.2, sweepAngle + 0.2 };

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
            GameLib.setColor(java.awt.Color.MAGENTA);
            GameLib.drawCircle(this.getX(), this.getY(), this.getRadius());
            
            if (currentTime % 200 < 100) {
                GameLib.setColor(java.awt.Color.WHITE);
                GameLib.drawCircle(this.getX(), this.getY(), this.getRadius() + 5);
            }
        }
    }
}