package cheeseBreeder.cheese;

import java.awt.Color;

public class BlueCheese extends Cheese
{
	public BlueCheese(int x, int y)
	{
		super(x, y);
		
		this.name = "Blue Cheese";
		
		this.core_color = new Color(235, 195, 105);
		this.rind_color = new Color(225, 215, 195);
		this.hole_color = new Color(150, 170, 160);
		
		this.rind_size = 7;
		
		this.hole_count_min = 8;
		this.hole_count_max = 20;
		this.hole_size_min = 5;
		this.hole_size_max = Cheese.size / 10;
		this.generateHoles();
	}
}
