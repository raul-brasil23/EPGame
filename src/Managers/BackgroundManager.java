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

        // Estrelas próximas (cinza claro, mais rápidas, maiores)
        for(int i = 0; i < 20; i++){
            double x = Math.random() * GameLib.WIDTH;
            double y = Math.random() * GameLib.HEIGHT;
            background1.add(new Background(x, y, 0.070, Color.GRAY, 3));
        }

        // Estrelas distantes (cinza escuro, mais lentas, menores)
        for(int i = 0; i < 50; i++){
            double x = Math.random() * GameLib.WIDTH;
            double y = Math.random() * GameLib.HEIGHT;
            background2.add(new Background(x, y, 0.045, Color.DARK_GRAY, 2));
        }
    }

    public void update(long delta) {
        for (Background star : background2) star.update(delta);
        for (Background star : background1) star.update(delta);
    }

    public void draw() {
        for (Background star : background2) star.draw();
        for (Background star : background1) star.draw();
    }
}