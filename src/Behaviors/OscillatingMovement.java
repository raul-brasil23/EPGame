package Behaviors;

import Entities.Entity;

public class OscillatingMovement implements MovementBehavior {
    private double initialX = -1;
    private double vx = 0.10;
    private double v = 0.05; 

    @Override
    public void move(Entity entity, long delta) {
        if (entity.getY() < 100) {
            entity.setY(entity.getY() + (this.v * delta));
        } else {
            if (initialX == -1) initialX = entity.getX(); 
            
            entity.setX(entity.getX() + (this.vx * delta));
            
            if (entity.getX() >= initialX + 100) this.vx = -Math.abs(this.vx);
            else if (entity.getX() <= initialX - 100) this.vx = Math.abs(this.vx);
        }
    }
}