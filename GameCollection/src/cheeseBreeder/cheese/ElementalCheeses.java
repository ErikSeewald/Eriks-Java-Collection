package cheeseBreeder.cheese;

import java.awt.Color;

public class ElementalCheeses 
{
	public static class BlueCheese extends Cheese
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
	
	public static class Brownie extends Cheese
	{
		public Brownie(int x, int y) 
		{
			super(x, y);
			
			this.name = "Brownie";
			
			this.core_color = new Color(150, 90, 40);
			this.rind_color = new Color(80, 40, 20);
			this.hole_color = rind_color;
			
			this.rind_size = 6;
			
			this.hole_count_min = 2;
			this.hole_count_max = 12;
			this.hole_size_min = 3;
			this.hole_size_max = Cheese.size / 12;
			this.generateHoles();
		}
	}
	
	public static class Camembert extends Cheese
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
	
	public static class Cheddar extends Cheese
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
	
	public static class Emmentaler extends Cheese
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
	
	public static class Gouda extends Cheese 
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
	
	public static class Mozarella extends Cheese
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
	
	public static class PinkCheese extends Cheese
	{
		public PinkCheese(int x, int y)
		{
			super(x, y);
			
			this.name = "Pink Cheese";
			
			this.core_color = new Color(225, 120, 225);
			this.rind_color = new Color(190, 15, 210);
			this.hole_color = new Color(90, 215, 120);
			
			this.rind_size = 17;
			
			this.hole_count_min = 3;
			this.hole_count_max = 30;
			this.hole_size_min = 3;
			this.hole_size_max = Cheese.size / 9;
			this.generateHoles();
		}
	}
	
	public static class Pommier extends Cheese
	{
		public Pommier(int x, int y) // French for apple tree
		{
			super(x, y);
			
			this.name = "Pommier";
			
			this.core_color = new Color(90, 215, 120);
			this.rind_color = new Color(20, 150, 90);
			this.hole_color = new Color(255, 120, 120);
			
			this.rind_size = 4;
			
			this.hole_count_min = 5;
			this.hole_count_max = 23;
			this.hole_size_min = 4;
			this.hole_size_max = Cheese.size / 8;
			this.generateHoles();
		}
	}
}
