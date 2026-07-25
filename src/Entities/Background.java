package Entities;

import Utils.GameLib;
import java.awt.Color;

public class Background {
	
	private double X;
	private double Y;
	private double speed;
	private Color color;
	private int size;
	
	public Background(double x, double y, double speed, Color color, int size) {
		this.X = x;
		this.Y = y;
		this.speed = speed;
		this.color = color;
		this.size = size;
	}
	
    // Move o fundo e cria a ilusão de um loop infinito
	public void update(long delta) {
		this.Y += this.speed * delta;
		
		if (this.Y > GameLib.HEIGHT) {
			this.Y %= GameLib.HEIGHT; 
		}
	}
	
	public void draw() {
		GameLib.setColor(this.color);
		GameLib.fillRect(this.X, this.Y, this.size, this.size);
	}
}