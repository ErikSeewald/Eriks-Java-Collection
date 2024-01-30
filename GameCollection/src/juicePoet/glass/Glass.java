package juicePoet.glass;

import ejcMain.util.Draggable;
import juicePoet.juice.Juice;

public class Glass implements Draggable
{
	private Juice juice;

	public int x;
	public int y;
	
	Glass(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	public void fill(Juice juice) 
	{
		this.juice = juice;
	}
	
	public boolean isEmpty() 
	{
		return juice == null;
	}
	
	public Juice getJuice()
	{
		return juice;
	}
	
	@Override
	public void dragTo(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
}
