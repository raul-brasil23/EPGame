package Managers;

import Screens.Screen;

// Classe gerenciadora de telas
public class ScreenManager {
    
    private Screen currentScreen;

    public void setScreen(Screen screen) {
        this.currentScreen = screen;
    }

    public void update(long currentTime, long delta) {
        if (this.currentScreen != null) {
            this.currentScreen.update(currentTime, delta);
        }
    }

    public void draw(long currentTime) {
        if (this.currentScreen != null) {
            this.currentScreen.draw(currentTime);
        }
    }
}