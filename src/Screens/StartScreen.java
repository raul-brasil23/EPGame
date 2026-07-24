package Screens;

import Managers.ScreenManager;
import Utils.GameLib;
import java.awt.Color;

public class StartScreen implements Screen {
    private ScreenManager screenManager;

    public StartScreen(ScreenManager screenManager) {
        this.screenManager = screenManager;
    }

    @Override
    public void update(long currentTime, long delta) {
        if (GameLib.iskeyPressed(GameLib.KEY_SPACE) || GameLib.iskeyPressed(GameLib.KEY_ENTER)) {
            // Passa para a tela do jogo!
            screenManager.setScreen(new PlayingScreen(screenManager, currentTime));
        }
    }

    @Override
    public void draw(long currentTime) {
        GameLib.setColor(Color.YELLOW);
        GameLib.drawTextCentered("PRESS SPACE TO PLAY", GameLib.WIDTH / 2.0, GameLib.HEIGHT / 2.0, 24);
    }
}