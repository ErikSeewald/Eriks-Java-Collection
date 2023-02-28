package pathfindGame;

public class Player 
{
	int x,y;
	int move_count;
	
	public Square[][] board;
	
	public boolean deathCheck(Chaser[] chasers)
	{
		for (Chaser chaser : chasers)
		{
			if (chaser.x == this.x && chaser.y == this.y) 
			{return true;}
		}
		return false;
	}
	
	public boolean move(char key) 
	{
		switch (key)
		{
			case 'a': if (isValidMove(this.x-1, this.y)) {this.x--;} else {return false;}
			break;
			case 's': if (isValidMove(this.x, this.y+1)) {this.y++;} else {return false;}
			break;
			case 'd': if (isValidMove(this.x+1, this.y)) {this.x++;} else {return false;}
			break;
			case 'w': if (isValidMove(this.x, this.y-1)) {this.y--;} else {return false;}
			break;
		}
			
		this.move_count++;
		return true;
	}
	
	private boolean isValidMove(int x, int y)
	{
		if (x > -1 && x < PathfindPanel.GRID_SIZE &&  y > -1 && y < PathfindPanel.GRID_SIZE)
		{return (!board[x][y].isAlive);}
		return false;
	}
}