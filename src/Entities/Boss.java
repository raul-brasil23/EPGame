package Entities;

import Behaviors.MovementBehavior;
import Utils.State;

public abstract class Boss extends Enemy {
    protected int hp;
    protected long deadTime = 0; 
    
    public Boss(State state, double x, double y, double radius, MovementBehavior movement, long shootInterval, int hp) {
        super(state, x, y, radius, movement, shootInterval); 
        this.hp = hp;
    }
    
    @Override
    protected void updateState(long currentTime) {
        if (this.state == State.EXPLODING) {
            if (deadTime == 0) deadTime = currentTime; 
            
            if (currentTime > deadTime + 500 || currentTime > this.explosion_end) {
                this.state = State.INACTIVE; 
            }
        }
    }

    @Override
    public void takeDamage(int damage, long currentTime) { 
        this.hp -= damage; 
        if (this.hp <= 0 && this.state != State.EXPLODING) {
            this.explode(currentTime, 500);
        }
    }
}