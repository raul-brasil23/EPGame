package Entities;

import Utils.State;
import Utils.GameLib;
import Managers.ProjectileManager;

public class MovingBoss extends Boss {
    private long lastShootTime;
    private long shootInterval = 300; 
    private ProjectileManager projectileManager; 
    
    // Velocidades para o estilo "DVD Screensaver"
    private double vx = 0.20; 
    private double vy = 0.20; 
    
    // Trava de segurança para garantir que a fase passe
    private long deadTime = 0; 

    public MovingBoss(State state, double x, double y, int hp, ProjectileManager projectileManager) {
        super(state, x, y, 0.08, Math.PI / 2, 0.0, 35.0, hp);
        this.lastShootTime = System.currentTimeMillis();
        this.projectileManager = projectileManager;
    }

    @Override
    public void update(long currentTime, long delta) {
        
        if (this.state == State.EXPLODING) {
            if (deadTime == 0) deadTime = currentTime; 
            
            if (currentTime > deadTime + 500 || currentTime > this.explosion_end) {
                this.state = State.INACTIVE; 
            }
            return; 
        }

        if (this.state == State.ACTIVE) {
            
            // Aplica o movimento nos dois eixos (Diagonal)
            this.setX(this.getX() + (this.vx * delta));
            this.setY(this.getY() + (this.vy * delta));

            // Bateu nas bordas laterais? Inverte X
            if (this.getX() >= GameLib.WIDTH - this.getRadius()) {
                this.vx = -Math.abs(this.vx);
                this.setX(GameLib.WIDTH - this.getRadius());
            } else if (this.getX() <= this.getRadius()) {
                this.vx = Math.abs(this.vx);
                this.setX(this.getRadius());
            }

            // Bateu nas bordas superior/inferior? Inverte Y
            if (this.getY() >= (GameLib.HEIGHT) - this.getRadius()) {
                this.vy = -Math.abs(this.vy);
                this.setY((GameLib.HEIGHT) - this.getRadius());
            } else if (this.getY() <= 25 + this.getRadius()) {
                this.vy = Math.abs(this.vy);
                this.setY(25 + this.getRadius());
            }

            if (currentTime - lastShootTime >= shootInterval) {
                shoot(currentTime); 
                lastShootTime = currentTime;
            }
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

    private void shoot(long currentTime) {
        double speed = 0.50; 
        double startX = this.getX();
        double startY = this.getY() + this.getRadius(); 

        double sweepAngle = (Math.PI / 2) + Math.sin(currentTime * 0.003) * (Math.PI / 3);
        double[] angles = { sweepAngle - 0.2, sweepAngle + 0.2 };

        for (double angle : angles) {
            double projectileVx = Math.cos(angle) * speed;
            double projectileVy = Math.sin(angle) * speed;
            this.projectileManager.getEnemyProjectiles().add(new Entities.EnemyProjectile(startX, startY, projectileVx, projectileVy));
        }
    }
}