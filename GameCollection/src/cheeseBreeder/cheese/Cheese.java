package cheeseBreeder.cheese;

import java.awt.Color;

public abstract class Cheese 
{
	protected String name;
	
	protected Color rind_color, core_color, hole_color;
	
	protected int rind_size, hole_count;
	
	protected Hole[] holes;
	
	public class Hole 
	{
		// x and y are relative to the x and y coordinates of the cheese the hole belongs to
		public int x, y;
		public int size;
		
		Hole(int x, int y, int size)
		{
			this.x = x;
			this.y = y;
			this.size = size;
		}
	}
	
	public int x, y;
	public static final int size = 150;
	
	Cheese(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	public String getName() {return name;}
	
	public int getRindSize() {return rind_size;}
	
	public Color getRindColor() {return rind_color;}
	
	public Color getCoreColor() {return core_color;}
	
	public Color getHoleColor() {return hole_color;}
	
	public int getHoleCount() {return hole_count;}
	
	public Hole[] getHoles() {return holes.clone();}
}
