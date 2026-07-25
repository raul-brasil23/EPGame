package Screens;

// Interface base para as telas do jogo
public interface Screen {
    void update(long currentTime, long delta);
    void draw(long currentTime);
}