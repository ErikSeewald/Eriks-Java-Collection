package juicePoet.poem;

import ejcMain.util.Draggable;

/**
 * Class representing a poem. Holds the {@link String} text and implements {@link Draggable}.
 */
public class Poem implements Draggable
{
	protected final String text;
	
	public static final int WIDTH = 120;
	public static final int HEIGHT = 80;
	
	//COORDINATES - DRAGGABLE
	private int x;
	private int y;
	
	Poem(String text, int x, int y)
	{
		this.text = text;
		this.x = x;
		this.y = y;
	}
	
	public String getText() {return text;}
	
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