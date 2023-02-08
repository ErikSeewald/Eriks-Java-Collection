package particles;

import java.awt.Color;
import java.util.Random;

public class Particle 
{
	int X, Y;
	int sizeX, sizeY;
	Color color;

	Particle(int X, int Y, int sizeX, int sizeY)
	{this.X = X; this.Y = Y; this.sizeX = sizeX; this.sizeY = sizeY;}
	
	public void move(Random random)
	{
		switch (random.nextInt(4))
		{
			case 0: this.X+=random.nextInt(2); break;
			case 1: this.X-=random.nextInt(2); break;
			case 2: this.Y+=random.nextInt(2); break;
			case 3: this.Y-=random.nextInt(2); break;
		}
	}
	
	public void push(int x, int y, int size, int amount)
	{
		if (this.X > x-size*3 && this.X < x+size && this.Y > y-size*3 && this.Y < y+size)	
		{	
			this.X+=amount;
			this.Y+=amount;	
		}
	}
	
	public void setColor(Color color)
	{
		int center = ParticlesPanel.PANEL_SIZE / 2;
		
		double distFromCenter = Math.abs(this.X-center) + Math.abs(this.Y-center);
		int R = (int) Math.abs(color.getRed()-(distFromCenter / 5));
		int G = (int) Math.abs(color.getGreen()-(distFromCenter / 5));
		int B = (int) Math.abs(color.getBlue()-(distFromCenter / 5));
		this.color = new Color(R,G,B);
	}
}