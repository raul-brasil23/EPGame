package Entities;

import Utils.State;
import Utils.GameLib;
import Managers.ProjectileManager; // Importação necessária

public class StaticBoss extends Boss {
    private long lastShootTime;
    private long shootInterval = 200;
    
    // Guarda a referência do gerenciador de tiros
    private ProjectileManager projectileManager; 

    // Adicione o parâmetro int hp na assinatura do construtor
    public StaticBoss(State state, double x, double y, int hp, ProjectileManager projectileManager) {
        // Passe o 'hp' recebido para o super
        super(state, x, y, 0.05, Math.PI / 2, 0.0, 40.0, hp); 
        this.lastShootTime = System.currentTimeMillis();
        this.projectileManager = projectileManager;
    }

    @Override
    public void update(long currentTime, long delta) {
        if (this.state == State.EXPLODING) {
            if (currentTime > this.explosion_end) {
                this.state = State.INACTIVE;
            }
            return;
        }
        if (this.state == State.ACTIVE) {

            // Lógica de Movimento: Entra na tela e para no Y = 100
            if (this.getY() < 100) {
                // Continua descendo baseando-se no delta para movimento fluido
                this.setY(this.getY() + (this.getV() * delta));
            } else {
                // Chegou no ponto ideal, fica completamente parado
                this.setV(0); 
            }

            // Lógica de Tiro Intenso
            if (currentTime - lastShootTime >= shootInterval) {
                shoot(); // Agora utiliza o atributo da classe
                lastShootTime = currentTime;
            }
        }
    }

    @Override
    public void draw(long currentTime) {
        if (this.state == State.EXPLODING) {
            double alpha = (double) (currentTime - this.explosion_start) / (this.explosion_end - this.explosion_start);
            GameLib.drawExplosion(this.getX(), this.getY(), alpha);
        } 
        else if (this.state == State.ACTIVE) {
            // Corpo principal do chefe (Vermelho)
            GameLib.setColor(java.awt.Color.RED);
            GameLib.drawCircle(this.getX(), this.getY(), this.getRadius());
        
            // Efeito visual com currentTime: O núcleo pulsa (aumenta e diminui)
            double pulse = Math.sin(currentTime * 0.005) * 10; 
        
            GameLib.setColor(java.awt.Color.YELLOW);
            GameLib.drawCircle(this.getX(), this.getY(), (this.getRadius() / 2) + pulse);
        }
    }

    // Removido o parâmetro; utiliza o this.projectileManager
    private void shoot() {
        double speed = 0.40; 
        
        double startX = this.getX();
        double startY = this.getY() + this.getRadius(); 

        double[] angles = { 
            Math.PI/2 - Math.PI/6,  
            Math.PI/2 - Math.PI/12, 
            Math.PI/2,              
            Math.PI/2 + Math.PI/12, 
            Math.PI/2 + Math.PI/6   
        };

        for (double angle : angles) {
            double vx = Math.cos(angle) * speed;
            double vy = Math.sin(angle) * speed;
            
            Entities.EnemyProjectile tiro = new Entities.EnemyProjectile(startX, startY, vx, vy);
            
            this.projectileManager.getEnemyProjectiles().add(tiro);
        }
    }
}