package Entities;
import Utils.GameLib;
import java.awt.Color;

public class PlayerPowerUpController {
    private boolean hasTripleShot = false;
    private boolean hasShield = false;
    private int shieldHp = 0;
    private final int SHIELD_MAX_HP = 5;

    public void activateShield() {
        this.hasShield = true;
        this.shieldHp = SHIELD_MAX_HP;
    }
    public void activateTripleShot() { this.hasTripleShot = true; }
    public boolean hasTripleShot() { return hasTripleShot; }
    public boolean hasShield() { return hasShield; }

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