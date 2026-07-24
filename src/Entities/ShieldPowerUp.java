package Entities;

import Utils.GameLib;
import Utils.State;
import java.awt.Color;

public class ShieldPowerUp extends PowerUp {

    public ShieldPowerUp(double x, double y) {
        // x e y vêm do txt. 
        // 0.10 é a velocidade de queda, 10.0 é o raio da bolinha
        super(x, y, 0.10, 10.0); 
    }

    @Override
    public void applyEffect(Player player) {
        player.getPowerUpController().activateShield();
    }

    @Override
    public void draw(long currentTime) {
        if (this.state == State.ACTIVE) {
            if (shouldBlink(currentTime)) {
                GameLib.setColor(Color.GREEN);
                GameLib.drawCircle(this.X, this.Y, this.radius);
            } else {
                // Quando não está piscando claro, desenha um tom mais escuro
                // para dar aquele efeito de pulsar suave
                GameLib.setColor(Color.GREEN.darker());
                GameLib.drawCircle(this.X, this.Y, this.radius);
            }
        }
    }
}