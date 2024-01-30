package juicePoet.poem;

import ejcMain.util.Draggable;

public class Poem implements Draggable
{
	protected String text;
	
	public int x;
	public int y;
	
	protected Poem(String text, int x, int y)
	{
		this.text = text;
		this.x = x;
		this.y = y;
	}
	
	@Override
	public void dragTo(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
}
