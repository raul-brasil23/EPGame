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

    // Adiciona um novo power-up na tela (será chamado pelo Spawner)
    public void addPowerUp(PowerUp powerUp) {
        this.powerUps.add(powerUp);
    }

    // Atualiza a posição de todos os power-ups
    public void update(long currentTime, long delta) {
        Iterator<PowerUp> iterator = powerUps.iterator();
        
        while (iterator.hasNext()) {
            PowerUp powerUp = iterator.next();
            powerUp.update(currentTime, delta);
            
            // Se o power-up ficou inativo (foi pego pelo player ou saiu da tela), remove da lista
            if (powerUp.getState() == State.INACTIVE) {
                iterator.remove();
            }
        }
    }

    // Desenha todos os power-ups ativos
    public void draw(long currentTime) {
        for (PowerUp powerUp : powerUps) {
            powerUp.draw(currentTime);
        }
    }

    // Retorna a lista para o CollisionManager poder verificar as colisões
    public List<PowerUp> getPowerUps() {
        return this.powerUps;
    }
}