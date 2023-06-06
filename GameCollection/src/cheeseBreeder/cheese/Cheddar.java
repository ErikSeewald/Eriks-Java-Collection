package cheeseBreeder.cheese;

import java.awt.Color;

public class Cheddar extends Cheese
{
	public Cheddar(int x, int y) 
	{
		super(x, y);
		
		this.name = "Cheddar";
		
		this.core_color = new Color(255, 150, 40);
		this.rind_color = new Color(255, 100, 0);
		this.hole_color = core_color;
		
		this.rind_size = 3;
		
		this.hole_count_min = 0;
		this.hole_count_max = 0;
		this.hole_size_min = 0;
		this.hole_size_max = 0;
		this.generateHoles();
	}
}
