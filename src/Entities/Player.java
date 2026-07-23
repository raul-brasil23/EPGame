package Entities;

import Utils.GameLib;
import Utils.State;

import java.awt.Color;

public class Player extends Ship {
    private int hp;
    private long nextShot;                  
    private long invulnerableUntil = 0;

    // TIRO TRIPLO E ESCUDO
    private boolean hasTripleShot = false;
    private boolean hasShield = false;
    private int shieldHp = 0;
    private final int SHIELD_MAX_HP = 5;
    
    public Player (double x, double y, long currentTime, int hp) {
        super (State.ACTIVE, x, y, 0.25, 0.25, 12.0);
        this.nextShot = currentTime;
        this.hp = hp;
    }

    public void move (long delta) {
        if(this.state == State.ACTIVE) {
            if(GameLib.iskeyPressed(GameLib.KEY_UP))    this.Y -= delta * this.VY;
            if(GameLib.iskeyPressed(GameLib.KEY_DOWN))  this.Y += delta * this.VY;
            if(GameLib.iskeyPressed(GameLib.KEY_LEFT))  this.X -= delta * this.VX;
            if(GameLib.iskeyPressed(GameLib.KEY_RIGHT)) this.X += delta * this.VX;
        }
    }

    public boolean isShooting(long currentTime) {
        if (this.state == State.ACTIVE && GameLib.iskeyPressed(GameLib.KEY_CONTROL)) {
            if (currentTime > this.nextShot) {
                this.nextShot = currentTime + 100; 
                return true;
            }
        }
        return false;
    }

    public void takeDamage(long currentTime) {
        if (this.state == State.ACTIVE && currentTime > this.invulnerableUntil) {
            
            if (this.hasShield) {
                this.shieldHp -= 1;
                System.out.println("Escudo absorveu dano! HP do escudo: " + this.shieldHp);
                if (this.shieldHp <= 0) {
                    this.hasShield = false; 
                }
            } else {
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

    public void activateShield() {
        this.hasShield = true;
        this.shieldHp = SHIELD_MAX_HP; 
    }

    public void activateTripleShot() {
        this.hasTripleShot = true;
    }
    
    public boolean hasTripleShot() {
        return this.hasTripleShot;
    }

    @Override
    public void update(long currentTime, long delta) {
        if (this.state == State.EXPLODING) {
            if (currentTime > this.explosion_end) {
                this.state = State.INACTIVE;
            }
        }
        if (this.state == State.ACTIVE) {
            if (this.X < 0.0) this.X = 0.0;
            if (this.X >= GameLib.WIDTH) this.X = GameLib.WIDTH - 1;
            if (this.Y < 25.0) this.Y = 25.0;
            if (this.Y >= GameLib.HEIGHT) this.Y = GameLib.HEIGHT - 1;
        }
    }

    @Override
    public void draw(long currentTime) {
        if (this.state == State.EXPLODING) {
            double alpha = (double) (currentTime - this.explosion_start) / (this.explosion_end - this.explosion_start);
            GameLib.drawExplosion(this.X, this.Y, alpha);
        } 
        else if (this.state == State.ACTIVE) {
            
            if (this.hasTripleShot) {
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

            if (this.hasShield) {
                GameLib.setColor(Color.GREEN);
                double shieldRadius = this.radius + 15;
                for (int i = 0; i < 360; i += 20) {
                    double angle1 = Math.toRadians(i);
                    double angle2 = Math.toRadians(i + 20);
                    
                    double x1 = this.X + Math.cos(angle1) * shieldRadius;
                    double y1 = this.Y + Math.sin(angle1) * shieldRadius;
                    double x2 = this.X + Math.cos(angle2) * shieldRadius;
                    double y2 = this.Y + Math.sin(angle2) * shieldRadius;
                    
                    GameLib.drawLine(x1, y1, x2, y2);
                }
            }
        }
    }

    public int getHp() { return hp; }
    public void setHp(int hp) { this.hp = hp; }

    public long getNextShot() { return nextShot; }
    public void setNextShot(long nextShot) { this.nextShot = nextShot; }
}