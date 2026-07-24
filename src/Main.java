import Managers.BackgroundManager;
import Managers.ScreenManager;
import Screens.StartScreen;
import Utils.GameLib;

public class Main {
	
	public static void busyWait(long time){
		while(System.currentTimeMillis() < time) Thread.yield();
	}
	
	public static void main(String [] args){

		boolean running = true;
		long delta;
		long currentTime = System.currentTimeMillis();

		// Apenas os Managers Globais ficam aqui!
		BackgroundManager backgroundManager = new BackgroundManager();
		ScreenManager screenManager = new ScreenManager();
						
		GameLib.initGraphics();
		
		// Injeta a tela inicial no gerenciador
		screenManager.setScreen(new StartScreen(screenManager));
		
		while(running){
			delta = System.currentTimeMillis() - currentTime;
			currentTime = System.currentTimeMillis();
			
			// A checagem do ESC fica global para sair do jogo de qualquer tela
			if(GameLib.iskeyPressed(GameLib.KEY_ESCAPE)) {
				running = false;
			}
			
			// O fundo rola independentemente de estar na tela inicial, morrendo ou jogando
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