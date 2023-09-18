package automata;

public class CellHandler 
{
	private float[][] cells; //cell state between 0 and 1
	private static final int cells_x = 750;
	private static final int cells_y = 400;
	
	CellHandler()
	{
		cells = new float[cells_x][cells_y];
	}
	
	public void update()
	{
		for (float[] column : cells)
		{
			for (float cell : column)
			{
				
			}
		}
	}
	
	public void stop()
	{
		cells = null;
	}
	
	public float[][] getCells() {return cells.clone();}
}
