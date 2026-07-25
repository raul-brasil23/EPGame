package Entities;

import Utils.GameLib;
import java.awt.Color;

public class PlayerPowerUpController {
    
    private boolean hasTripleShot;
    private long tripleShotEndTime;
    private boolean hasShield;
    private int shieldHp;
    private final int SHIELD_MAX_HP = 3;

    public PlayerPowerUpController() {
        this.hasTripleShot = false;
        this.tripleShotEndTime = 0;
        this.hasShield = false;
        this.shieldHp = 0;
    }

    // Cuida do desligamento do triple shot caso o tempo de duração tenha se esgotado
    public void update(long currentTime) {
        if (this.hasTripleShot && currentTime > this.tripleShotEndTime) {
            this.hasTripleShot = false;
        }
    }

    public void activateShield() {
        this.hasShield = true;
        this.shieldHp = SHIELD_MAX_HP;
    }
    
    // O timer é resetado caso o player pegue outro item, ao invés de somar
    public void activateTripleShot(long currentTime) { 
        this.hasTripleShot = true; 
        this.tripleShotEndTime = currentTime + 10000; 
    }

    // Desenha barra de duração do tempo do powerup triple shoot
    public void drawTripleShotBar(double playerX, double playerY, double playerRadius, long currentTime) {
        if (this.hasTripleShot && currentTime <= this.tripleShotEndTime) {
            double remaining = (this.tripleShotEndTime - currentTime) / 10000.0;
            
            if (remaining < 0) {
                remaining = 0;
            }
            
            double barWidth = 40.0;
            double barHeight = 5.0;
            double currentWidth = barWidth * remaining;
            double drawY = playerY + playerRadius + 15;
            
            GameLib.setColor(Color.MAGENTA);
            GameLib.fillRect(playerX, drawY, barWidth + 2, barHeight + 2);
            GameLib.setColor(Color.BLACK);
            GameLib.fillRect(playerX, drawY, barWidth, barHeight);
            
            double cx = playerX - (barWidth / 2.0) + (currentWidth / 2.0);
            GameLib.setColor(Color.MAGENTA);
            GameLib.fillRect(cx, drawY, currentWidth, barHeight);
        }
    }

    public boolean takeShieldDamage() {
        if (this.hasShield) {
            this.shieldHp -= 1;
            
            if (this.shieldHp <= 0) {
                this.hasShield = false;
            }
            
            return true; 
        }
        
        return false; 
    }

    public void drawShield(double x, double y, double radius) {
        if (this.hasShield) {
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

    public boolean hasTripleShot() { return hasTripleShot; }
    public boolean hasShield() { return hasShield; }
    public int getShieldHp() { return shieldHp; } 
    public long getTripleShotEndTime() { return tripleShotEndTime; }
}