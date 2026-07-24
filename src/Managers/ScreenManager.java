package Managers;

import Screens.Screen;

public class ScreenManager {
    private Screen currentScreen;

    public void setScreen(Screen screen) {
        this.currentScreen = screen;
    }

    public void update(long currentTime, long delta) {
        if (currentScreen != null) {
            currentScreen.update(currentTime, delta);
        }
    }

    public void draw(long currentTime) {
        if (currentScreen != null) {
            currentScreen.draw(currentTime);
        }
    }
}