package Entities;

import Behaviors.MovementBehavior;
import Utils.State;
import Utils.GameLib;
import java.awt.Color;

// O Chefe É-UM Inimigo
public abstract class Boss extends Enemy {
    
    protected int hp;
    protected int maxHp;
    protected String name;
    protected long deadTime; 
    
    public Boss(State state, double x, double y, double radius, MovementBehavior movement, long shootInterval, int hp, String name) {
        super(state, x, y, radius, movement, shootInterval); 
        this.hp = hp;
        this.maxHp = hp;
        this.name = name;
        this.deadTime = 0;
    }
    
    @Override
    protected void updateState(long currentTime) {
        if (this.state == State.EXPLODING) {
            if (this.deadTime == 0) {
                this.deadTime = currentTime; 
            }
            
            if (currentTime > this.deadTime + 500 || currentTime > this.explosion_end) {
                this.state = State.INACTIVE; 
            }
        }
    }

    @Override
    public void takeDamage(int damage, long currentTime) { 
        this.hp -= damage; 
        
        if (this.hp <= 0 && this.state != State.EXPLODING) {
            this.explode(currentTime, 500);
        }
    }

    // Desenha uma barra de vida, no topo da tela, com o nome dentro
    public void drawBossUI() {
        if (this.state == State.ACTIVE || this.state == State.EXPLODING) {
            double barWidth = 300.0;
            double barHeight = 26.0;
            double bx = GameLib.WIDTH / 2.0;
            double by = 40.0;

            GameLib.setColor(Color.RED.darker().darker());
            GameLib.fillRect(bx, by, barWidth + 4, barHeight + 4);

            GameLib.setColor(Color.DARK_GRAY);
            GameLib.fillRect(bx, by, barWidth, barHeight);

            if (this.hp > 0) {
                double hpPercentage = (double) this.hp / this.maxHp;
                double currentWidth = barWidth * hpPercentage;
                GameLib.setColor(Color.RED);
                
                double cx = bx - (barWidth / 2.0) + (currentWidth / 2.0);
                GameLib.fillRect(cx, by, currentWidth, barHeight);
            }

            GameLib.setColor(Color.WHITE);
            GameLib.drawTextCentered(this.name, bx, by + 6, 16);
        }
    }
}