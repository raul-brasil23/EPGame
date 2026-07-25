package Entities;

import Utils.GameLib;
import Utils.State;
import java.awt.Color;

public class TripleShotPowerUp extends PowerUp {

    public TripleShotPowerUp(double x, double y) {
        super(x, y, 0.10, 10.0); 
    }
    
    @Override
    public void applyEffect(Player player, long currentTime) {
        player.getPowerUpController().activateTripleShot(currentTime);
    }

    @Override
    public void draw(long currentTime) {
        if (this.state == State.ACTIVE) {
            if (shouldBlink(currentTime)) {
                GameLib.setColor(Color.MAGENTA);
                GameLib.drawStar(this.X, this.Y, this.radius);
            } else {
                GameLib.setColor(Color.MAGENTA.darker());
                GameLib.drawStar(this.X, this.Y, this.radius);
            }
        }
    }
}