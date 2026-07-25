package Behaviors;

import Entities.Entity;
import Utils.GameLib;

public class DiamondMovement implements MovementBehavior {
    
    private double v;
    private double angle;
    private double rv;
    private boolean readyToShoot;

    public DiamondMovement(double v, double angle, double rv) {
        this.v = v; 
        this.angle = angle; 
        this.rv = rv;
        this.readyToShoot = false;
    }

    // Realiza uma movimentação angular e altera a rotação ao atingir 30% da altura da tela
    @Override
    public void move(Entity entity, long delta) {
        double previousY = entity.getY();
        
        entity.setX(entity.getX() + (this.v * Math.cos(this.angle) * delta));
        entity.setY(entity.getY() + (this.v * Math.sin(this.angle) * delta * (-1.0)));
        this.angle += this.rv * delta;
        
        double threshold = GameLib.HEIGHT * 0.30;
        
        // Verifica se cruzou a linha de limite para iniciar a curva baseada no lado da tela
        if (previousY < threshold && entity.getY() >= threshold) {
            if (entity.getX() < GameLib.WIDTH / 2) {
                this.rv = 0.003;
            } else {
                this.rv = -0.003;
            }
        }
        
        // Trava a rotação quando o ângulo alvo é atingido e prepara o tiro
        if (this.rv > 0 && Math.abs(this.angle - 3 * Math.PI) < 0.05) {
            this.rv = 0.0;
            this.angle = 3 * Math.PI;
            this.readyToShoot = true;
        }
        
        if (this.rv < 0 && Math.abs(this.angle) < 0.05) {
            this.rv = 0.0;
            this.angle = 0.0;
            this.readyToShoot = true;
        }
    }
    
    public boolean isReadyToShoot() { return readyToShoot; }
    public void resetShot() { this.readyToShoot = false; }
}