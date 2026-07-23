package Entities;

import Utils.State;
import Utils.GameLib;
import Managers.ProjectileManager;

public class MovingBoss extends Boss {
    private long lastShootTime;
    private long shootInterval = 300; 
    
    private ProjectileManager projectileManager; 
    private double vx = 0.20; // Velocidade horizontal que usaremos para ir de um lado pro outro

    public MovingBoss(State state, double x, double y, int hp, ProjectileManager projectileManager) {
        super(state, x, y, 0.08, Math.PI / 2, 0.0, 35.0, hp);
        this.lastShootTime = System.currentTimeMillis();
        this.projectileManager = projectileManager;
    }

    @Override
    public void update(long currentTime, long delta) {
        // 1. TRATA O ESTADO DE MORTE PRIMEIRO
        // Se estiver explodindo, checa se a explosão já acabou para inativar o chefe
        if (this.state == State.EXPLODING) {
            if (currentTime > this.explosion_end) {
                this.state = State.INACTIVE; // É isso aqui que avisa o LevelManager para passar de fase
            }
            return; // Retorna para ele NÃO mover e NÃO atirar enquanto explode
        }

        // 2. SÓ FAZ A LÓGICA DO CHEFE SE ELE ESTIVER VIVO
        if (this.state == State.ACTIVE) {
            
            // Movimento Vertical: Desce até Y = 150
            if (this.getY() < 150) {
                this.setY(this.getY() + (this.getV() * delta));
            } 
            // Movimento Horizontal: Quando termina de descer, começa a ir para os lados
            else {
                this.setX(this.getX() + (this.vx * delta));

                // Bateu na borda direita da tela? Inverte a velocidade para negativo (vai pra esquerda)
                if (this.getX() >= GameLib.WIDTH - this.getRadius()) {
                    this.vx = -Math.abs(this.vx);
                }
                // Bateu na borda esquerda da tela? Inverte a velocidade para positivo (vai pra direita)
                else if (this.getX() <= this.getRadius()) {
                    this.vx = Math.abs(this.vx);
                }
            }

            // Lógica de Tiro
            if (currentTime - lastShootTime >= shootInterval) {
                shoot(currentTime); 
                lastShootTime = currentTime;
            }
        }
    }

    @Override
    public void draw(long currentTime) {
        // Se estiver explodindo, desenha a animação de explosão em vez do chefe
        if (this.state == State.EXPLODING) {
            double alpha = (double) (currentTime - this.explosion_start) / (this.explosion_end - this.explosion_start);
            GameLib.drawExplosion(this.getX(), this.getY(), alpha);
        } 
        // Só desenha o chefe normal se ele estiver ativo
        else if (this.state == State.ACTIVE) {
            GameLib.setColor(java.awt.Color.MAGENTA);
            GameLib.drawCircle(this.getX(), this.getY(), this.getRadius());
            
            if (currentTime % 200 < 100) {
                GameLib.setColor(java.awt.Color.WHITE);
                GameLib.drawCircle(this.getX(), this.getY(), this.getRadius() + 5);
            }
        }
    }

    private void shoot(long currentTime) {
        double speed = 0.50; 
        double startX = this.getX();
        double startY = this.getY() + this.getRadius(); 

        double sweepAngle = (Math.PI / 2) + Math.sin(currentTime * 0.003) * (Math.PI / 3);

        double[] angles = { sweepAngle - 0.2, sweepAngle + 0.2 };

        for (double angle : angles) {
            double projectileVx = Math.cos(angle) * speed;
            double projectileVy = Math.sin(angle) * speed;
            
            Entities.EnemyProjectile tiro = new Entities.EnemyProjectile(startX, startY, projectileVx, projectileVy);
            this.projectileManager.getEnemyProjectiles().add(tiro);
        }
    }
}