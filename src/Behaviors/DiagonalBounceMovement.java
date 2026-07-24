package Behaviors;

import Entities.Entity;
import Utils.GameLib;

public class DiagonalBounceMovement implements MovementBehavior {
    private double vx = 0.20;
    private double vy = 0.20;

    @Override
    public void move(Entity entity, long delta) {
        entity.setX(entity.getX() + (this.vx * delta));
        entity.setY(entity.getY() + (this.vy * delta));

        if (entity.getX() >= GameLib.WIDTH - entity.getRadius()) {
            this.vx = -Math.abs(this.vx);
            entity.setX(GameLib.WIDTH - entity.getRadius());
        } else if (entity.getX() <= entity.getRadius()) {
            this.vx = Math.abs(this.vx);
            entity.setX(entity.getRadius());
        }

        if (entity.getY() >= (GameLib.HEIGHT) - entity.getRadius()) {
            this.vy = -Math.abs(this.vy);
            entity.setY((GameLib.HEIGHT) - entity.getRadius());
        } else if (entity.getY() <= 25 + entity.getRadius()) {
            this.vy = Math.abs(this.vy);
            entity.setY(25 + entity.getRadius());
        }
    }
}