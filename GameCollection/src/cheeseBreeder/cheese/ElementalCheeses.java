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
			
			this.coreColor = new Color(235, 195, 105);
			this.rindColor = new Color(225, 215, 195);
			this.holeColor = new Color(150, 170, 160);
			
			this.rindSize = 7;
			
			this.holeCountMin = 8;
			this.holeCountMax = 20;
			this.holeSizeMin = 5;
			this.holeSizeMax = Cheese.size / 10;
			this.generateHoles();
		}
	}
	
	public static class Fraise extends Cheese //French for strawberry
	{
		public Fraise(int x, int y)
		{
			super(x, y);
			
			this.name = "Fraise";
			
			this.coreColor = new Color(145, 50, 45);
			this.rindColor = new Color(45, 125, 55);
			this.holeColor = new Color(65, 55, 50);
			
			this.rindSize = 7;
			
			this.holeCountMin = 7;
			this.holeCountMax = 21;
			this.holeSizeMin = 5;
			this.holeSizeMax = Cheese.size / 14;
			this.generateHoles();
		}
	}
	
	public static class Brownie extends Cheese
	{
		public Brownie(int x, int y) 
		{
			super(x, y);
			
			this.name = "Brownie";
			
			this.coreColor = new Color(150, 90, 40);
			this.rindColor = new Color(80, 40, 20);
			this.holeColor = rindColor;
			
			this.rindSize = 6;
			
			this.holeCountMin = 2;
			this.holeCountMax = 12;
			this.holeSizeMin = 3;
			this.holeSizeMax = Cheese.size / 12;
			this.generateHoles();
		}
	}
	
	public static class Camembert extends Cheese
	{
		public Camembert(int x, int y) 
		{
			super(x, y);
			
			this.name = "Camembert";
			
			this.coreColor = new Color(255, 215, 170);
			this.rindColor = new Color(245, 235, 215);
			this.holeColor = coreColor;
			
			this.rindSize = 20;
			
			this.holeCountMin = 0;
			this.holeCountMax = 0;
			this.holeSizeMin = 0;
			this.holeSizeMax = 0;
			this.generateHoles();
		}
		
	}
	
	public static class Coffee extends Cheese
	{
		public Coffee(int x, int y) 
		{
			super(x, y);
			
			this.name = "Coffee";
			
			this.coreColor = new Color(100, 75, 50);
			this.rindColor = new Color(160, 140, 115);
			this.holeColor = new Color(130, 105, 80);;
			
			this.rindSize = 13;
			
			this.holeCountMin = 4;
			this.holeCountMax = 11;
			this.holeSizeMin = 3;
			this.holeSizeMax = Cheese.size / 13;
			this.generateHoles();
		}
		
	}
	
	public static class Cheddar extends Cheese
	{
		public Cheddar(int x, int y) 
		{
			super(x, y);
			
			this.name = "Cheddar";
			
			this.coreColor = new Color(255, 150, 40);
			this.rindColor = new Color(255, 100, 0);
			this.holeColor = coreColor;
			
			this.rindSize = 3;
			
			this.holeCountMin = 0;
			this.holeCountMax = 0;
			this.holeSizeMin = 0;
			this.holeSizeMax = 0;
			this.generateHoles();
		}
	}
	
	public static class CheeseCoal extends Cheese
	{
		public CheeseCoal(int x, int y) 
		{
			super(x, y);
			
			this.name = "Cheese Coal";
			
			this.coreColor = new Color(15, 25, 45);
			this.rindColor = coreColor;
			this.holeColor = new Color(25, 55, 65);
			
			this.rindSize = 0;
			
			this.holeCountMin = 0;
			this.holeCountMax = 10;
			this.holeSizeMin = 3;
			this.holeSizeMax = Cheese.size / 15;
			this.generateHoles();
		}
	}
	
	public static class Emmentaler extends Cheese
	{
		public Emmentaler(int x, int y)
		{
			super(x, y);
			
			this.name = "Emmentaler";
			
			this.coreColor = new Color(245, 190, 80);
			this.rindColor = new Color(230, 150, 20);
			this.holeColor = new Color(230, 160, 30);
			
			this.rindSize = 10;
			
			this.holeCountMin = 4;
			this.holeCountMax = 10;
			this.holeSizeMin = 5;
			this.holeSizeMax = Cheese.size / 5;
			this.generateHoles();
		}
	}
	
	public static class Gouda extends Cheese 
	{
		public Gouda(int x, int y) 
		{
			super(x, y);
			
			this.name = "Gouda";
			
			this.coreColor = new Color(240, 190, 45);
			this.rindColor = new Color(230, 160, 40);
			this.holeColor = new Color(225, 170, 30);
			
			this.rindSize = 5;
			
			this.holeCountMin = 1;
			this.holeCountMax = 6;
			this.holeSizeMin = 5;
			this.holeSizeMax = Cheese.size / 15;
			this.generateHoles();
		}
	}
	
	public static class Mozarella extends Cheese
	{
		public Mozarella(int x, int y) 
		{
			super(x, y);
			
			this.name = "Mozarella";
			
			this.coreColor = new Color(245, 235, 215);
			this.rindColor = coreColor;
			this.holeColor = coreColor;
			
			this.rindSize = 0;
			
			this.holeCountMin = 0;
			this.holeCountMax = 0;
			this.holeSizeMin = 0;
			this.holeSizeMax = 0;
			this.generateHoles();
		}
	}
	
	public static class PinkCheese extends Cheese
	{
		public PinkCheese(int x, int y)
		{
			super(x, y);
			
			this.name = "Pink Cheese";
			
			this.coreColor = new Color(225, 120, 225);
			this.rindColor = new Color(190, 15, 210);
			this.holeColor = new Color(90, 215, 120);
			
			this.rindSize = 17;
			
			this.holeCountMin = 3;
			this.holeCountMax = 30;
			this.holeSizeMin = 3;
			this.holeSizeMax = Cheese.size / 9;
			this.generateHoles();
		}
	}
	
	public static class Pommier extends Cheese
	{
		public Pommier(int x, int y) // French for apple tree
		{
			super(x, y);
			
			this.name = "Pommier";
			
			this.coreColor = new Color(90, 215, 120);
			this.rindColor = new Color(20, 150, 90);
			this.holeColor = new Color(255, 120, 120);
			
			this.rindSize = 4;
			
			this.holeCountMin = 5;
			this.holeCountMax = 23;
			this.holeSizeMin = 4;
			this.holeSizeMax = Cheese.size / 8;
			this.generateHoles();
		}
	}
}
