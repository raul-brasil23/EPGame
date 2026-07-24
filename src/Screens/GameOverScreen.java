package Screens;

import Managers.ScreenManager;
import Utils.GameLib;
import java.awt.Color;

public class GameOverScreen implements Screen {
    private ScreenManager screenManager;

    public GameOverScreen(ScreenManager screenManager) {
        this.screenManager = screenManager;
    }

    @Override
    public void update(long currentTime, long delta) {
        if (GameLib.iskeyPressed(GameLib.KEY_SPACE) || GameLib.iskeyPressed(GameLib.KEY_ENTER)) {
            // Cria um novo jogo passando a hora atual
            screenManager.setScreen(new PlayingScreen(screenManager, currentTime));
        }
    }

    @Override
    public void draw(long currentTime) {
        GameLib.setColor(Color.RED);
        GameLib.drawTextCentered("GAME OVER", GameLib.WIDTH / 2.0, GameLib.HEIGHT / 2.0 - 20, 40);
        
        GameLib.setColor(Color.WHITE);
        GameLib.drawTextCentered("Press ESC to exit or press SPACE to try again", GameLib.WIDTH / 2.0, GameLib.HEIGHT / 2.0 + 20, 16);
    }
}