package Behaviors;

import Entities.Entity;

public class CircularMovement implements MovementBehavior {
    private double v;
    private double angle;
    private double rv;

    public CircularMovement(double v, double angle, double rv) {
        this.v = v;
        this.angle = angle;
        this.rv = rv;
    }

    @Override
    public void move(Entity entity, long delta) {
        entity.setX(entity.getX() + (this.v * Math.cos(this.angle) * delta));
        entity.setY(entity.getY() + (this.v * Math.sin(this.angle) * delta * (-1.0)));
        this.angle += this.rv * delta;
    }

    public double getAngle() { return angle; }
}