package Entities;

import Behaviors.MovementBehavior;
import Managers.ProjectileManager;
import Utils.State;

// Classe base para todas as naves do jogo
public abstract class Ship extends Entity {
    
    protected double explosion_start;
	protected double explosion_end;
    protected MovementBehavior movementBehavior;

    public Ship (State state, double x, double y, double radius, MovementBehavior movement) {
		super(state, x, y, radius);
        this.explosion_start = 0;
        this.explosion_end = 0;
        this.movementBehavior = movement;
	}

    // Lógica de atualização da nave
    @Override
    public final void update(long currentTime, long delta) {
        updateState(currentTime);
        
        if (this.state == State.ACTIVE) {
            updateMovement(delta);
            performExtraActions(currentTime, delta);
        }
    }

    // Desativa a nave quando a animação de explosão termina
    protected void updateState(long currentTime) {
        if (this.state == State.EXPLODING && currentTime > this.explosion_end) {
            this.state = State.INACTIVE; 
        }
    }

    protected void updateMovement(long delta) {
        if (this.movementBehavior != null) {
            this.movementBehavior.move(this, delta);
        }
    }

    public void explode (long currentTime, long duration) {
        this.state = State.EXPLODING;
        this.explosion_start = currentTime;
        this.explosion_end = currentTime + duration;
    }

    protected abstract void performExtraActions(long currentTime, long delta);
    public abstract void tryToShoot(long currentTime, Player player, ProjectileManager projManager);
}