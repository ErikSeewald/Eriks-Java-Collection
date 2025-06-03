package pathfindGame;

public class Chaser 
{
	int x,y;
	private boolean prefersX;
	
	private Player target;
	private	Square[][] board;
	
	Chaser(int x, int y, boolean prefersX, Player target, Square[][] board)
	{
		this.x = x; 
		this.y = y; 
		this.prefersX = prefersX;
		this.target = target;
		this.board = board;
	}
	
	public void nextStep()
	{	
		this.prefersX = !this.prefersX;
		
		//MOVE
		if (this.prefersX)
		{
			if (xMoveWorked()) {return;}	
			if (yMoveWorked()) {return;}
		}
			
		else
		{
			if (yMoveWorked()) {return;}	
			if (xMoveWorked()) {return;}
		}
		
		//NO VALID MOVE FOUND -> START ATTACKING SQUARE
		int attackX = this.x, attackY = this.y;
		
		if (target.x > this.x)
		{attackX++;}
				
		else if (target.x < this.x)
		{attackX--;}
		
		else if (target.y > this.y)
		{attackY++;}
				
		else
		{attackY--;}
		
		board[attackX][attackY].getHit();
	}
	
	private boolean xMoveWorked()
	{
		if (target.x > this.x)
		{
			if (!board[this.x + 1] [this.y].isAlive) 
			{this.x++; return true;}
		}
		
		else if (target.x < this.x)
		{
			if (!board[this.x - 1] [this.y].isAlive) 
			{this.x--; return true;}
		}
		
		return false;
	}
	
	private boolean yMoveWorked()
	{		
		if (target.y > this.y)
		{
			if (!board[this.x] [this.y + 1].isAlive) 
			{this.y++; return true;}
		}
		
		else if (target.y < this.y)
		{
			if (!board[this.x] [this.y - 1].isAlive) 
			{this.y--; return true;}
		}
		return false;
	}
}