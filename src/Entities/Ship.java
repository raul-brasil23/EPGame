package Entities;

import Behaviors.MovementBehavior;
import Managers.ProjectileManager;
import Utils.State;

public abstract class Ship extends Entity {
    protected double explosion_start = 0;			// instante do início da explosão
	protected double explosion_end = 0;			// instante do final da explosão
    protected MovementBehavior movementBehavior;

    public Ship (State state, double x, double y, double radius, MovementBehavior movement) {
		super (state, x, y, radius);
        this.movementBehavior = movement;
	}

    @Override
    public void update(long currentTime, long delta) {
        updateState(currentTime);
        
        if (this.state == State.ACTIVE) {
            updateMovement(delta);
            performExtraActions(currentTime, delta); // Para piscar, atirar, etc.
        }
    }

    // 1. Método focado apenas em gerenciar a vida/explosão
    protected void updateState(long currentTime) {
        if (this.state == State.EXPLODING && currentTime > this.explosion_end) {
            this.state = State.INACTIVE; // O Player vai sobrescrever isso para voltar a ser ACTIVE
        }
    }

    // 2. Método focado apenas em andar
    protected void updateMovement(long delta) {
        if (this.movementBehavior != null) {
            this.movementBehavior.move(this, delta);
        }
    }

    protected abstract void performExtraActions(long currentTime, long delta);

    public abstract void tryToShoot(long currentTime, Player player, ProjectileManager projManager);

    public void explode (long currentTime, long duration) {
        this.state = State.EXPLODING;
        this.explosion_start = currentTime;
        this.explosion_end = currentTime + duration;
    }
}
