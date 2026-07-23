package Entities;

import Utils.State;

public abstract class Boss extends Enemy {
    protected int hp;
    
    public Boss(State state, double x, double y, double v, double angle, double rv, double radius, int hp) {
        super(state, x, y, v, angle, rv, radius); 
        this.hp = hp;
    }
    
    public void takeDamage(int damage) { 
        this.hp -= damage; 
    }

    // Assinatura corrigida para bater com a classe Entity
    @Override
    public abstract void update(long currentTime, long delta);

    public int getHp() { return hp; }
    public void setHp(int hp) { this.hp = hp; }
}