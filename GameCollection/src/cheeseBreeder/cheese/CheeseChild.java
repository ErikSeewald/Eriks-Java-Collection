package cheeseBreeder.cheese;

import java.awt.Color;

import cheeseBreeder.Breedery;

public class CheeseChild extends Cheese
{
	private boolean grownUp;
	
	public CheeseChild(int x, int y) 
	{
		super(x, y);
		grownUp = false;
	}

	public boolean isGrownUp()
	{return grownUp;}
	
	public void setGrownUp(boolean state, Object signature)
	{
		if (!(signature instanceof Breedery))
		{throw new IllegalArgumentException("Non Breedery signatures cannot change grownUp");}
		
		this.grownUp = state;
	}
	
	public void setName(String name)
	{this.name = name;}
	
	public void setColors(Color coreColor, Color rindColor, Color holeColor)
	{
		this.coreColor = coreColor;
		this.rindColor = rindColor;
		this.holeColor = holeColor;
	}
	
	public void setRindSize(int rindSize)
	{this.rindSize = rindSize;}
	
	public void setHoleNumbers(int hole_count_min, int hole_count_max, int hole_size_min, int hole_size_max)
	{
		this.holeCountMin = hole_count_min;
		this.holeCountMax = hole_count_max;
		this.holeSizeMin = hole_size_min;
		this.holeSizeMax = hole_size_max;
	}
}
