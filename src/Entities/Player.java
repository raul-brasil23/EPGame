package Entities;

import Behaviors.PlayerMovement;
import Managers.ProjectileManager;
import Utils.GameLib;
import Utils.State;
import java.awt.Color;

public class Player extends Ship {
    private int hp;
    private long invulnerableUntil = 0;
    private PlayerPowerUpController powerUpController;
    private long lastShootTime;
    
    public Player (double x, double y, long currentTime, int hp) {
        super(State.ACTIVE, x, y, 12.0, new PlayerMovement());
        this.hp = hp;
        this.lastShootTime = currentTime;
        this.powerUpController = new PlayerPowerUpController();
    }

    @Override
    protected void performExtraActions(long currentTime, long delta) {
        if (this.X < 0.0) this.X = 0.0;
        if (this.X >= GameLib.WIDTH) this.X = GameLib.WIDTH - 1;
        if (this.Y < 25.0) this.Y = 25.0;
        if (this.Y >= GameLib.HEIGHT) this.Y = GameLib.HEIGHT - 1;
    }

    @Override
    public void tryToShoot(long currentTime, Player player, ProjectileManager projManager) {
        if (GameLib.iskeyPressed(GameLib.KEY_CONTROL) && currentTime > this.lastShootTime) {
            this.lastShootTime = currentTime + 100; 
            
            double xCenter = this.getX();
            double yCenter = this.getY() - 2 * this.getRadius();
            
            if (this.powerUpController.hasTripleShot()) {
                projManager.getPlayerProjectiles().add(new PlayerProjectile(xCenter, yCenter));
                projManager.getPlayerProjectiles().add(new PlayerProjectile(this.getX() - 20, this.getY() + 10 - this.getRadius()));
                projManager.getPlayerProjectiles().add(new PlayerProjectile(this.getX() + 20, this.getY() + 10 - this.getRadius()));
            } else {
                projManager.getPlayerProjectiles().add(new PlayerProjectile(xCenter, yCenter));
            }
        }
    }

    public void takeDamage(long currentTime) {
        if (this.state == State.ACTIVE && currentTime > this.invulnerableUntil) {
            if (!this.powerUpController.takeShieldDamage()) {
                this.hp -= 1;
            }
            this.invulnerableUntil = currentTime + 1000;
            if (this.hp <= 0) {
                this.explode(currentTime, 2000);
            }
        }
    }

    public boolean isInvulnerable(long currentTime) {
        return currentTime < this.invulnerableUntil;
    }

    @Override
    public void draw(long currentTime) {
        if (this.state == State.EXPLODING) {
            double alpha = (double) (currentTime - this.explosion_start) / (this.explosion_end - this.explosion_start);
            GameLib.drawExplosion(this.X, this.Y, alpha);
        } 
        else if (this.state == State.ACTIVE) {
            if (this.powerUpController.hasTripleShot()) {
                GameLib.setColor(Color.WHITE); 
                GameLib.drawPlayer(this.X - 20, this.Y + 10, (this.radius / 2) + 2);
                GameLib.drawPlayer(this.X + 20, this.Y + 10, (this.radius / 2) + 2);
                
                GameLib.setColor(Color.ORANGE); 
                GameLib.drawPlayer(this.X - 20, this.Y + 10, this.radius / 2);
                GameLib.drawPlayer(this.X + 20, this.Y + 10, this.radius / 2);
            }

            boolean isBlinking = (currentTime < this.invulnerableUntil) && ((currentTime / 100) % 2 == 0);
            boolean isImmune = (currentTime < this.invulnerableUntil);

            if (!isImmune || isBlinking) {
                GameLib.setColor(Color.WHITE);
                GameLib.drawPlayer(this.X, this.Y, this.radius + 2);
                GameLib.setColor(Color.BLUE);
                GameLib.drawPlayer(this.X, this.Y, this.radius);
            }

            this.powerUpController.drawShield(this.X, this.Y, this.radius);
        }
    }

    public int getHp() { return hp; }
    public PlayerPowerUpController getPowerUpController() { return this.powerUpController; }
}