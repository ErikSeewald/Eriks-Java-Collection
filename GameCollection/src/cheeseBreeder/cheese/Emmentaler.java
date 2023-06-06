package cheeseBreeder.cheese;

import java.awt.Color;
import java.util.Random;

public class Emmentaler extends Cheese
{
	private Random random;
	
	public Emmentaler(int x, int y)
	{
		super(x, y);
		
		this.name = "Emmentaler";
		
		this.core_color = new Color(250, 190, 45);
		this.rind_color = new Color(250, 150, 40);
		this.hole_color = new Color(235, 160, 30);
		
		this.rind_size = 10;
		
		random = new Random();
		this.generateHoles();
	}
	
	private void generateHoles()
	{
		this.hole_count = random.nextInt(5) + 3;
		holes = new Hole[hole_count];
		
		for (int i = 0; i < this.hole_count; i++)
		{
			// create a new whole with x and y positions relative to this.x and this.y and size relative to Cheese.size
			holes[i] = new Hole
			(
					(int) (Cheese.size * (random.nextFloat(0.6f) + 0.1f)), 
					(int) (Cheese.size * (random.nextFloat(0.6f) + 0.1f)), 
					random.nextInt(Cheese.size / 5) + 5
			);
		}
	}
}