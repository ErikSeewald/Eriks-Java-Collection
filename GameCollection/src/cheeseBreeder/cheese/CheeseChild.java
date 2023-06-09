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
		this.core_color = coreColor;
		this.rind_color = rindColor;
		this.hole_color = holeColor;
	}
	
	public void setRindSize(int rindSize)
	{this.rind_size = rindSize;}
	
	public void setHoleNumbers(int hole_count_min, int hole_count_max, int hole_size_min, int hole_size_max)
	{
		this.hole_count_min = hole_count_min;
		this.hole_count_max = hole_count_max;
		this.hole_size_min = hole_size_min;
		this.hole_size_max = hole_size_max;
	}
}
