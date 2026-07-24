package Behaviors;

import Entities.Entity;
import Utils.GameLib;

public class DiamondMovement implements MovementBehavior {
    private double v, angle, rv;
    private boolean readyToShoot = false;

    public DiamondMovement(double v, double angle, double rv) {
        this.v = v; this.angle = angle; this.rv = rv;
    }

    @Override
    public void move(Entity entity, long delta) {
        double previousY = entity.getY();
        entity.setX(entity.getX() + (this.v * Math.cos(this.angle) * delta));
        entity.setY(entity.getY() + (this.v * Math.sin(this.angle) * delta * (-1.0)));
        this.angle += this.rv * delta;
        
        double threshold = GameLib.HEIGHT * 0.30;
        if (previousY < threshold && entity.getY() >= threshold) {
            if (entity.getX() < GameLib.WIDTH / 2) this.rv = 0.003;
            else this.rv = -0.003;
        }
        
        if (this.rv > 0 && Math.abs(this.angle - 3 * Math.PI) < 0.05) {
            this.rv = 0.0;
            this.angle = 3 * Math.PI;
            this.readyToShoot = true;
        }
        if (this.rv < 0 && Math.abs(this.angle) < 0.05) {
            this.rv = 0.0;
            this.angle = 0.0;
            this.readyToShoot = true;
        }
    }
    
    public boolean isReadyToShoot() { return readyToShoot; }
    public void resetShot() { readyToShoot = false; }
}