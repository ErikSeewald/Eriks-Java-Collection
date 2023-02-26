package snakesAndLadders.board;
import snakesAndLadders.SnL_Panel;

public class Player
{
	public int playerNum;
	
	public int x, y;
	
	public int gridPos = 0, rolledGridPos = 0;
	
	//goal when moving along a snake or ladder
	public int goalX, goalY;
	
	Player(int playerNum)
	{this.playerNum = playerNum; setScreenPosition();}
	
	public void setScreenPosition()
	{
		int gridX = this.gridPos % BoardHandler.columns;
		if (this.gridPos / BoardHandler.columns % 2 == 1 ) 
		{
			gridX = BoardHandler.columns - 1 - gridX; //flip
		}
		this.x = 35 + SnL_Panel.square_size * gridX;
		this.y = SnL_Panel.PANEL_HEIGHT - 95 - SnL_Panel.square_size * (this.gridPos / BoardHandler.columns);
	}
	
	public void drag(int x, int y)
	{
		if (x < SnL_Panel.PANEL_WIDTH && x > 0) 
		{this.x = x - SnL_Panel.player_size/2;}

		if (y < SnL_Panel.PANEL_HEIGHT && y > 0) 
		{this.y = y - SnL_Panel.player_size/2;}
	}
}