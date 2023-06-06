package cheeseBreeder.cheese;

import java.awt.Color;
import java.util.Random;

public abstract class Cheese 
{
	public int x, y;
	public static final int size = 150;
	
	protected String name;
	
	//CORE
	protected Color core_color;
	
	//RIND
	protected Color rind_color;
	protected int rind_size;
	
	//HOLES
	protected Color hole_color;
	protected int hole_count, hole_count_min, hole_count_max;
	protected int hole_size_min, hole_size_max;
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
	
	private Random random;
	
	Cheese(int x, int y)
	{
		this.x = x;
		this.y = y;
		
		random = new Random();
	}
	
	protected void generateHoles()
	{	
		if (this.hole_count_max < 1) {return;}
		
		this.hole_count = random.nextInt(hole_count_max - hole_count_min) + hole_count_min;
		holes = new Hole[hole_count];
		
		for (int i = 0; i < this.hole_count; i++)
		{
			// create a new whole with x and y positions relative to this.x and this.y and size relative to Cheese.size
			holes[i] = new Hole
			(
					(int) (Cheese.size * (random.nextFloat(0.6f) + 0.1f)), 
					(int) (Cheese.size * (random.nextFloat(0.6f) + 0.1f)), 
					random.nextInt(hole_size_max - hole_size_min) + hole_size_min
			);
		}
	}
	
	public String getName() {return name;}
	
	public int getRindSize() {return rind_size;}
	
	public Color getRindColor() {return rind_color;}
	
	public Color getCoreColor() {return core_color;}
	
	public Color getHoleColor() {return hole_color;}
	
	public int getHoleCount() {return hole_count;}
	
	public Hole[] getHoles() 
	{
		if (this.holes == null) {return new Hole[] {};}
		return holes.clone();
	}
}
