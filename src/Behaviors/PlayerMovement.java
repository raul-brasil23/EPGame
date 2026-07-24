package Behaviors;

import Entities.Entity;
import Utils.GameLib;

public class PlayerMovement implements MovementBehavior {
    private double vx = 0.25;
    private double vy = 0.25;

    @Override
    public void move(Entity entity, long delta) {
        if(GameLib.iskeyPressed(GameLib.KEY_UP))    entity.setY(entity.getY() - delta * vy);
        if(GameLib.iskeyPressed(GameLib.KEY_DOWN))  entity.setY(entity.getY() + delta * vy);
        if(GameLib.iskeyPressed(GameLib.KEY_LEFT))  entity.setX(entity.getX() - delta * vx);
        if(GameLib.iskeyPressed(GameLib.KEY_RIGHT)) entity.setX(entity.getX() + delta * vx);
    }
}