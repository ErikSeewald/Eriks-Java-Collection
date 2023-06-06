package cheeseBreeder.cheese;

import java.awt.Color;

public class Gouda extends Cheese 
{

	public Gouda(int x, int y) 
	{
		super(x, y);
		
		this.name = "Gouda";
		
		this.core_color = new Color(240, 190, 45);
		this.rind_color = new Color(230, 160, 40);
		this.hole_color = new Color(225, 170, 30);
		
		this.rind_size = 5;
		
		this.hole_count_min = 1;
		this.hole_count_max = 6;
		this.hole_size_min = 5;
		this.hole_size_max = Cheese.size / 15;
		this.generateHoles();
	}

}
