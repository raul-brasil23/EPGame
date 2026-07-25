package Behaviors;

import Entities.Entity;

// Define o contrato base para movimentação
public interface MovementBehavior {
    void move(Entity entity, long delta);
}