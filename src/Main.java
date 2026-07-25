import Managers.BackgroundManager;
import Managers.ScreenManager;
import Screens.StartScreen;
import Utils.GameLib;

public class Main {
	
    // Pequena pausa na execução do jogo para manter o frame rate constante
	public static void busyWait(long time){
		while(System.currentTimeMillis() < time) Thread.yield();
	}
	
	public static void main(String [] args){

		boolean running = true;
		long delta;
		long currentTime = System.currentTimeMillis();

		BackgroundManager backgroundManager = new BackgroundManager();
		ScreenManager screenManager = new ScreenManager();
						
		GameLib.initGraphics();
		
		screenManager.setScreen(new StartScreen(screenManager));
		
		while(running){
			delta = System.currentTimeMillis() - currentTime;
			currentTime = System.currentTimeMillis();
			
			if(GameLib.iskeyPressed(GameLib.KEY_ESCAPE)) {
				running = false;
			}
			
			backgroundManager.update(delta);
			screenManager.update(currentTime, delta);

			backgroundManager.draw();
			screenManager.draw(currentTime);
			
			GameLib.display();
			busyWait(currentTime + 3);
		}
		
		System.exit(0);
	}
}