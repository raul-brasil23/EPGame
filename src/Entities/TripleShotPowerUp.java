package Entities;

import Utils.GameLib;
import Utils.State;
import java.awt.Color;

public class TripleShotPowerUp extends PowerUp {

    public TripleShotPowerUp(double x, double y) {
        // Mesma velocidade de queda e mesmo tamanho da bolinha verde
        super(x, y, 0.10, 10.0); 
    }

    @Override
    public void draw(long currentTime) {
        if (this.state == State.ACTIVE) {
            if (shouldBlink(currentTime)) {
                GameLib.setColor(Color.ORANGE);
                GameLib.drawCircle(this.X, this.Y, this.radius);
            } else {
                GameLib.setColor(Color.ORANGE.darker());
                GameLib.drawCircle(this.X, this.Y, this.radius);
            }
        }
    }
}