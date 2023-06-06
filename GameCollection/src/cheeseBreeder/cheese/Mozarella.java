package cheeseBreeder.cheese;

import java.awt.Color;

public class Mozarella extends Cheese
{
	public Mozarella(int x, int y) 
	{
		super(x, y);
		
		this.name = "Mozarella";
		
		this.core_color = new Color(245, 235, 215);
		this.rind_color = core_color;
		this.hole_color = core_color;
		
		this.rind_size = 0;
		
		this.hole_count_min = 0;
		this.hole_count_max = 0;
		this.hole_size_min = 0;
		this.hole_size_max = 0;
		this.generateHoles();
	}
}
