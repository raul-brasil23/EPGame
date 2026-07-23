package Entities;

import Utils.GameLib;
import Utils.State;

public abstract class PowerUp extends Entity {

    public PowerUp(double x, double y, double vy, double radius) {
        // Chamando o construtor correto de Entity: (estado, X, Y, VX, VY, raio)
        // Como o drop cai reto para baixo, o VX é 0.0
        super(State.ACTIVE, x, y, 0.0, vy, radius); 
    }

    @Override
    public void update(long currentTime, long delta) {
        if (this.state == State.ACTIVE) {
            // A bolinha desce constantemente usando o VY que foi passado para a classe mãe
            this.Y += this.VY * delta; 
            
            // Se passar muito do limite inferior da tela, desativamos para economizar memória
            if (this.Y > GameLib.HEIGHT + 50) {
                this.state = State.INACTIVE;
            }
        }
    }
    
    // Método auxiliar para o piscar "não frenético"
    protected boolean shouldBlink(long currentTime) {
        return ((currentTime / 300) % 2 == 0);
    }
}