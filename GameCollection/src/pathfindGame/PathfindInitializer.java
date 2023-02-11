package pathfindGame;
import java.util.Random;
import pathfindGame.PathfindPanel.ControlBooleans;

public class PathfindInitializer 
{	
	public static Square[][] initBoard(Square[][] board, boolean fullReset, Random random)
	{
		int GRID_SIZE = PathfindPanel.GRID_SIZE;
		
		if (fullReset)
		{
			board = null; System.gc();
			board = new Square[GRID_SIZE][GRID_SIZE];
		}
					
		for (int x = 0; x < GRID_SIZE; x++)
		{
			for (int y = 0; y < GRID_SIZE; y++)
			{	
				if (fullReset)
				{
					boolean isAlive = random.nextInt(4) == 1;
					board[x][y] = new Square(isAlive);
				}

				else 
				{
					//only revive the squares that were alive from the start
					if (board[x][y].hp != 3) {board[x][y].hp = 3; board[x][y].isAlive = true;}
				}
			}
		}
		
		return board;
	}
	
	public static void initPlayer(Player player, Square[][] board, Chaser[] chasers, Random random)
	{
		player.move_count = 0;
		player.board = board;
		
		int[] pos = PathfindInitializer.generateValidPos(ControlBooleans.isPlayer, board, chasers, random);
		player.x = pos[0];
		player.y = pos[1];
	}
	
	public static Chaser[] initChasers(Chaser[] chasers, int chaser_count, Player player, Square[][] board, Random random)
	{
		chasers = null; System.gc();
		chasers = new Chaser[chaser_count];
		for (int i = 0; i < chaser_count; i++)
		{
			int[] pos = generateValidPos(ControlBooleans.isChaser, board, chasers, random);
			chasers[i] = new Chaser(pos[0],pos[1], random.nextBoolean(), player, board);
		}
		
		return chasers;
	}
	
	public static int[] generateValidPos(boolean isPlayer, Square[][] board, Chaser[] chasers, Random random)
	{
		int[] pos = new int[2];
		
		boolean validPos = false;
		while(!validPos)
		{
			pos[0] = random.nextInt(PathfindPanel.GRID_SIZE);
			pos[1] = random.nextInt(PathfindPanel.GRID_SIZE);
			
			if (board[pos[0]][pos[1]].isAlive) {continue;} //generate new coordinates
			validPos = true;
			
			if (isPlayer)
			{
				for (Chaser chaser : chasers)
				{
					if (pos[0] == chaser.x && pos[1] == chaser.y)
					{validPos = false; break;}
				}
			}
		}
		
		return pos;
	}
}