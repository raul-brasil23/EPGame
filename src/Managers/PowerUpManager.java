package Managers;

import Entities.PowerUp;
import Utils.State;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PowerUpManager {
    
    private List<PowerUp> powerUps;

    public PowerUpManager() {
        this.powerUps = new ArrayList<>();
    }

    public void addPowerUp(PowerUp powerUp) {
        this.powerUps.add(powerUp);
    }

    // Usa um Iterator para atualizar e remover da lista os itens coletados ou que saíram da tela
    public void update(long currentTime, long delta) {
        Iterator<PowerUp> iterator = this.powerUps.iterator();
        
        while (iterator.hasNext()) {
            PowerUp powerUp = iterator.next();
            powerUp.update(currentTime, delta);
            
            if (powerUp.getState() == State.INACTIVE) {
                iterator.remove();
            }
        }
    }

    public void draw(long currentTime) {
        for (PowerUp powerUp : this.powerUps) {
            powerUp.draw(currentTime);
        }
    }

    public List<PowerUp> getPowerUps() { return powerUps; }
}