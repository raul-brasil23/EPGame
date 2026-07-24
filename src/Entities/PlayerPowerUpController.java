package Entities;
import Utils.GameLib;
import java.awt.Color;

public class PlayerPowerUpController {
    private boolean hasTripleShot = false;
    private long tripleShotEndTime = 0; // Temporizador

    private boolean hasShield = false;
    private int shieldHp = 0;
    private final int SHIELD_MAX_HP = 3;

    public void activateShield() {
        this.hasShield = true;
        this.shieldHp = SHIELD_MAX_HP;
    }
    public void activateTripleShot(long currentTime) { 
        this.hasTripleShot = true; 
        this.tripleShotEndTime = currentTime + 10000; // Dura 10 segundos!
    }

    // Método que será chamado a cada frame para desligar o poder se o tempo acabar
    public void update(long currentTime) {
        if (this.hasTripleShot && currentTime > this.tripleShotEndTime) {
            this.hasTripleShot = false;
        }
    }

    public boolean hasTripleShot() { return hasTripleShot; }
    public boolean hasShield() { return hasShield; }
    public int getShieldHp() { return shieldHp; } // Getter criado para o Player poder desenhar as barrinhas

    // Preparando o terreno para a sua barra visual futura
    public long getTripleShotEndTime() { return tripleShotEndTime; }
    
    // Desenha a barra decrescente do tiro triplo acompanhando a nave
    public void drawTripleShotBar(double playerX, double playerY, double playerRadius, long currentTime) {
        if (hasTripleShot && currentTime <= tripleShotEndTime) {
            double remaining = (tripleShotEndTime - currentTime) / 10000.0;
            if (remaining < 0) remaining = 0;
            
            double barWidth = 40.0;
            double barHeight = 5.0;
            double currentWidth = barWidth * remaining;
            double drawY = playerY + playerRadius + 15;
            
            // Contorno e fundo vazio
            GameLib.setColor(Color.MAGENTA);
            GameLib.fillRect(playerX, drawY, barWidth + 2, barHeight + 2);
            GameLib.setColor(Color.BLACK);
            GameLib.fillRect(playerX, drawY, barWidth, barHeight);
            
            // Preenchimento decaindo (ancorado na esquerda usando o cx)
            double cx = playerX - (barWidth / 2.0) + (currentWidth / 2.0);
            GameLib.setColor(Color.MAGENTA);
            GameLib.fillRect(cx, drawY, currentWidth, barHeight);
        }
    }

    public boolean takeShieldDamage() {
        if (hasShield) {
            shieldHp--;
            if (shieldHp <= 0) hasShield = false;
            return true; // Dano foi absorvido
        }
        return false; // Não tem escudo, player toma dano
    }

    public void drawShield(double x, double y, double radius) {
        if (hasShield) {
            GameLib.setColor(Color.GREEN);
            double shieldRadius = radius + 15;
            for (int i = 0; i < 360; i += 20) {
                double angle1 = Math.toRadians(i);
                double angle2 = Math.toRadians(i + 20);
                
                double x1 = x + Math.cos(angle1) * shieldRadius;
                double y1 = y + Math.sin(angle1) * shieldRadius;
                double x2 = x + Math.cos(angle2) * shieldRadius;
                double y2 = y + Math.sin(angle2) * shieldRadius;
                
                GameLib.drawLine(x1, y1, x2, y2);
            }
        }
    }
}