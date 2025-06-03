package cheeseBreeder.cheese;

import java.awt.Color;
import java.util.Random;

public abstract class Cheese 
{
	public int x, y;
	public static final int size = 150;
	
	protected String name;
	
	//CORE
	protected Color coreColor;
	
	//RIND
	protected Color rindColor;
	protected int rindSize;
	
	//HOLES
	protected Color holeColor;
	protected int holeCount, holeCountMin, holeCountMax;
	protected int holeSizeMin, holeSizeMax;
	protected Hole[] holes;
	private boolean holesCreated;
	
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
	private boolean selected;
	
	protected Cheese(int x, int y)
	{
		this.x = x;
		this.y = y;
		this.holesCreated = false;
		
		random = new Random();
	}
	
	public boolean isSelected() {return selected;}
	
	public void setSelected(boolean state) {selected = state;}
	
	public void generateHoles()
	{	
		if (this.holeCountMax < 1) {this.holesCreated = true; return;}
		
		this.holeCount = random.nextInt(holeCountMax - holeCountMin) + holeCountMin;
		holes = new Hole[holeCount];
		
		for (int i = 0; i < this.holeCount; i++)
		{
			// create a new whole with x and y positions relative to this.x and this.y and size relative to Cheese.size
			holes[i] = new Hole
			(
					(int) (Cheese.size * (random.nextFloat(0.6f) + 0.1f)), 
					(int) (Cheese.size * (random.nextFloat(0.6f) + 0.1f)), 
					random.nextInt(holeSizeMax - holeSizeMin) + holeSizeMin
			);
		}
		
		this.holesCreated = true;
	}
	
	public String getName() {return name;}
	
	public int getRindSize() {return rindSize;}
	
	public Color getRindColor() {return rindColor;}
	
	public Color getCoreColor() {return coreColor;}
	
	public Color getHoleColor() {return holeColor;}
	
	public int getHoleCount() {return holeCount;}
	
	public Hole[] getHoles() 
	{
		if (this.holes == null) {return new Hole[] {};}
		return holes.clone();
	}
	
	public boolean holesCreated()
	{return holesCreated;}
	
	public int getHoleCountMin() {return holeCountMin;}
	
	public int getHoleCountMax() {return holeCountMax;}
	
	public int getHoleSizeMin() {return holeSizeMin;}
	
	public int getHoleSizeMax() {return holeSizeMax;}
}
