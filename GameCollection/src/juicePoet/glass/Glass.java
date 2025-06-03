package juicePoet.glass;

import ejcMain.util.Draggable;
import juicePoet.juice.Juice;

/**
 * A class representing a glass to be filled with {@link Juice}. Implements {@link Draggable}.
 */
public class Glass implements Draggable
{
	private Juice juice;
	
	public static final int WIDTH = 100;
	public static final int HEIGHT = 150;

	//COORDINATES - DRAGGABLE
	private int x;
	private int y;
	
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

	@Override
	public int getX() {return x;}

	@Override
	public int getY() {return y;}
}