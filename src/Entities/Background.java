package Entities;

import java.util.ArrayList;
import java.util.List;
import Utils.GameLib;
import java.awt.Color;

public class Background {
	private List<Star> stars;
	
	// O construtor já cria todas as estrelas e guarda na lista
	public Background(int amountOfStars, double speed, Color color, int size) {
		this.stars = new ArrayList<>();
		
		for(int i = 0; i < amountOfStars; i++) {
			double startX = Math.random() * GameLib.WIDTH;
			double startY = Math.random() * GameLib.HEIGHT;
			
			this.stars.add(new Star(startX, startY, speed, color, size));
		}
	}
	
	// Manda todas as estrelas da lista se atualizarem
	public void update(long delta) {
		for(Star star : this.stars) {
			star.update(delta);
		}
	}
	
	// Manda todas as estrelas da lista se desenharem
	public void draw() {
		for(Star star : this.stars) {
			star.draw();
		}
	}
}