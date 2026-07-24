package Behaviors;

import Entities.Entity;

public interface MovementBehavior {
    void move(Entity entity, long delta);
}