package Screens;

import Managers.ScreenManager;
import Utils.GameLib;
import java.awt.Color;

public class GameOverScreen implements Screen {
    
    private ScreenManager screenManager;

    public GameOverScreen(ScreenManager screenManager) {
        this.screenManager = screenManager;
    }

    // Reinicia completamente o jogo caso o jogador queira jogar novamente
    @Override
    public void update(long currentTime, long delta) {
        if (GameLib.iskeyPressed(GameLib.KEY_SPACE)) {
            this.screenManager.setScreen(new PlayingScreen(this.screenManager, currentTime));
        }
    }

    @Override
    public void draw(long currentTime) {
        GameLib.setColor(Color.RED);
        GameLib.drawTextCentered("GAME OVER", GameLib.WIDTH / 2.0, GameLib.HEIGHT / 2.0 - 20, 40);
        
        GameLib.setColor(Color.WHITE);
        GameLib.drawTextCentered("Press SPACE to Retry", GameLib.WIDTH / 2.0, GameLib.HEIGHT / 2.0 + 20, 16);

        GameLib.setColor(Color.WHITE);
        GameLib.drawTextCentered("Press ESC to Exit", GameLib.WIDTH / 2.0, GameLib.HEIGHT / 2.0 + 40, 16);
    }
}