package cheeseBreeder.cheese;

import java.awt.Color;

public class Emmentaler extends Cheese
{
	public Emmentaler(int x, int y)
	{
		super(x, y);
		
		this.name = "Emmentaler";
		
		this.core_color = new Color(250, 190, 45);
		this.rind_color = new Color(250, 150, 40);
		this.hole_color = new Color(250, 180, 40);
		
		this.rind_size = 8;
	}
}
