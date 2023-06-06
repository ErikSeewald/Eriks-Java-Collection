package cheeseBreeder.cheese;

import java.awt.Color;

public class Camembert extends Cheese
{
	public Camembert(int x, int y) 
	{
		super(x, y);
		
		this.name = "Camembert";
		
		this.core_color = new Color(255, 215, 170);
		this.rind_color = new Color(245, 235, 215);
		this.hole_color = core_color;
		
		this.rind_size = 20;
		
		this.hole_count_min = 0;
		this.hole_count_max = 0;
		this.hole_size_min = 0;
		this.hole_size_max = 0;
		this.generateHoles();
	}
	
}
