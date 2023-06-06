package cheeseBreeder.cheese;

import java.awt.Color;
import java.util.ArrayList;

public abstract class Cheese 
{
	protected String name;
	
	protected Color rind_color, core_color, hole_color;
	
	protected int rind_size, hole_count;
	
	protected ArrayList<Hole> holes;
	
	public class Hole 
	{
		int x, y;
		int size;
		
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
		
		holes = new ArrayList<>();
	}
	
	public String getName() {return name;}
	
	public int getRindSize() {return rind_size;}
	
	public Color getRindColor() {return rind_color;}
	
	public Color getCoreColor() {return core_color;}
	
	public Color getHoleColor() {return hole_color;}
	
	public int getHoleCount() {return hole_count;}
}
