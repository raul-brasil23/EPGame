package Entities;

import Utils.GameLib;
import Utils.State;
import java.awt.Color;

public class ShieldPowerUp extends PowerUp {

    public ShieldPowerUp(double x, double y) {
        super(x, y, 0.10, 10.0); 
    }
    
    @Override
    public void applyEffect(Player player, long currentTime) {
        player.getPowerUpController().activateShield();
    }

    @Override
    public void draw(long currentTime) {
        if (this.state == State.ACTIVE) {
            if (shouldBlink(currentTime)) {
                GameLib.setColor(Color.GREEN);
                GameLib.drawStar(this.X, this.Y, this.radius);
            } else {
                GameLib.setColor(Color.GREEN.darker());
                GameLib.drawStar(this.X, this.Y, this.radius);
            }
        }
    }
}