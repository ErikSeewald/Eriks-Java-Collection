package snakesAndLadders.board;

import snakesAndLadders.SnL_Panel;

public class Square
{
	public Square ladder, snake;
	
	public int gridPos;
	public int centerX, centerY;
	
	public void calculateCenterPoint()
	{
		int gridX = this.gridPos % BoardHandler.columns;
		if (this.gridPos / BoardHandler.columns % 2 == 1 ) 
		{
			gridX = BoardHandler.columns - 1 - gridX; //flip
		}
		
		this.centerX = 60 + SnL_Panel.square_size * gridX;
		this.centerY = SnL_Panel.PANEL_HEIGHT - 20 - SnL_Panel.square_size * (this.gridPos / BoardHandler.columns);
	}
}