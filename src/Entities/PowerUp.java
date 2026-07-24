package Entities;

import Utils.GameLib;
import Utils.State;

public abstract class PowerUp extends Entity {
    protected double VY;

    public PowerUp(double x, double y, double vy, double radius) {
        super(State.ACTIVE, x, y, radius); 
        this.VY = vy;
    }

    @Override
    public void update(long currentTime, long delta) {
        if (this.state == State.ACTIVE) {
            this.Y += this.VY * delta; 
            
            if (this.Y > GameLib.HEIGHT + 50) {
                this.state = State.INACTIVE;
            }
        }
    }

    public abstract void applyEffect(Player player);
    
    protected boolean shouldBlink(long currentTime) {
        return ((currentTime / 300) % 2 == 0);
    }
}