package Entities;

import Utils.State;
import Utils.GameLib;
import Managers.ProjectileManager;

public class StaticBoss extends Boss {
    private long lastShootTime;
    private long shootInterval = 200;
    private ProjectileManager projectileManager; 

    // Variáveis para a nova movimentação
    private double initialX = -1;
    private double vx = 0.10; // Velocidade do movimento horizontal
    
    // Trava de segurança para garantir que a fase passe
    private long deadTime = 0; 

    public StaticBoss(State state, double x, double y, int hp, ProjectileManager projectileManager) {
        super(state, x, y, 0.05, Math.PI / 2, 0.0, 40.0, hp); 
        this.lastShootTime = System.currentTimeMillis();
        this.projectileManager = projectileManager;
    }

    @Override
    public void update(long currentTime, long delta) {
        
        // --- LÓGICA DE MORTE CORRIGIDA ---
        if (this.state == State.EXPLODING) {
            if (currentTime > this.explosion_end) {
                this.state = State.INACTIVE;
            }
            return;
        }

        if (this.state == State.ACTIVE) {
            
            // Lógica de Movimento: Desce até Y = 100
            if (this.getY() < 100) {
                this.setY(this.getY() + (this.getV() * delta));
            } else {
                // Chegou na altura certa, marca o eixo X e começa o vai e vem
                if (initialX == -1) initialX = this.getX(); 
                
                this.setX(this.getX() + (this.vx * delta));
                
                // Inverte a direção se andar 100 pixels para algum dos lados
                if (this.getX() >= initialX + 100) this.vx = -Math.abs(this.vx);
                else if (this.getX() <= initialX - 100) this.vx = Math.abs(this.vx);
            }

            if (currentTime - lastShootTime >= shootInterval) {
                shoot(); 
                lastShootTime = currentTime;
            }
        }
    }

    @Override
    public void draw(long currentTime) {
        if (this.state == State.EXPLODING) {
            // Desenha a explosão, garantindo que o deadTime previna loops infinitos
            double alpha = (double) (currentTime - (deadTime > 0 ? deadTime : this.explosion_start)) / 500.0;
            if (alpha > 1.0) alpha = 1.0; // Impede que o alpha quebre a renderização
            GameLib.drawExplosion(this.getX(), this.getY(), alpha);
        } 
        else if (this.state == State.ACTIVE) {
            GameLib.setColor(java.awt.Color.RED);
            GameLib.drawCircle(this.getX(), this.getY(), this.getRadius());
        
            double pulse = Math.sin(currentTime * 0.005) * 10; 
            GameLib.setColor(java.awt.Color.YELLOW);
            GameLib.drawCircle(this.getX(), this.getY(), (this.getRadius() / 2) + pulse);
        }
    }

    private void shoot() {
        double speed = 0.40; 
        double startX = this.getX();
        double startY = this.getY() + this.getRadius(); 

        double[] angles = { 
            Math.PI/2 - Math.PI/6, Math.PI/2 - Math.PI/12, Math.PI/2, Math.PI/2 + Math.PI/12, Math.PI/2 + Math.PI/6   
        };

        for (double angle : angles) {
            double projectileVx = Math.cos(angle) * speed;
            double projectileVy = Math.sin(angle) * speed;
            this.projectileManager.getEnemyProjectiles().add(new Entities.EnemyProjectile(startX, startY, projectileVx, projectileVy));
        }
    }
}