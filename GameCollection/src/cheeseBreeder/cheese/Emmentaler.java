package cheeseBreeder.cheese;

import java.awt.Color;

public class Emmentaler extends Cheese
{
	public Emmentaler(int x, int y)
	{
		super(x, y);
		
		this.name = "Emmentaler";
		
		this.core_color = new Color(245, 190, 80);
		this.rind_color = new Color(230, 150, 20);
		this.hole_color = new Color(230, 160, 30);
		
		this.rind_size = 10;
		
		this.hole_count_min = 4;
		this.hole_count_max = 10;
		this.hole_size_min = 5;
		this.hole_size_max = Cheese.size / 5;
		this.generateHoles();
	}
}