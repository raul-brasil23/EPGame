package Screens;

public interface Screen {
    void update(long currentTime, long delta);
    void draw(long currentTime);
}