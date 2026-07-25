package Managers;

import Entities.Background;
import Utils.GameLib;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class BackgroundManager {
    
    private List<Background> background1;
    private List<Background> background2;

    public BackgroundManager() {
        this.background1 = new ArrayList<>();
        this.background2 = new ArrayList<>();

        // Cria o plano de fundo mais próximo (estrelas maiores e mais rápidas para dar sensação de profundidade)
        for(int i = 0; i < 20; i++){
            double x = Math.random() * GameLib.WIDTH;
            double y = Math.random() * GameLib.HEIGHT;
            
            this.background1.add(new Background(x, y, 0.070, Color.GRAY, 3));
        }

        // Cria o plano de fundo distante (estrelas menores e mais lentas)
        for(int i = 0; i < 50; i++){
            double x = Math.random() * GameLib.WIDTH;
            double y = Math.random() * GameLib.HEIGHT;
            
            this.background2.add(new Background(x, y, 0.045, Color.DARK_GRAY, 2));
        }
    }

    public void update(long delta) {
        for (Background star : this.background2) {
            star.update(delta);
        }
        
        for (Background star : this.background1) {
            star.update(delta);
        }
    }

    public void draw() {
        for (Background star : this.background2) {
            star.draw();
        }
        
        for (Background star : this.background1) {
            star.draw();
        }
    }
}