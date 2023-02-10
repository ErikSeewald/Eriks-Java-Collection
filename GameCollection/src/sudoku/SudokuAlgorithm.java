package sudoku;

public class SudokuAlgorithm 
{	
	public static boolean solve(int[][] board)
	{	
		for (int row = 0; row < 9; row++)
		{
			for (int column = 0; column < 9; column++)
			{
				if (board[row][column] == 0)
				{
					for (int number = 1; number <= 9; number++)
					{
						if (isValidPlacement(board, number,row,column))
						{
							board[row][column] = number;
							
							if (solve(board)) {return true;}
							else {board[row][column] = 0;}
						}
					}
					
					return false;
				}
			}
		}
		
		return true;
	}
	
	public static boolean isValidPlacement(int[][] board, int number, int row, int column)
	{
		return !isNumberInRow(board, number,row) && !isNumberInColumn(board, number,column)
				&& !isNumberInBox(board, number,row,column);
	}

	public static boolean isNumberInRow(int[][] board, int number, int row)
	{
		for (int i = 0; i < 9; i++)
		{if(board[row][i] == number) {return true;}}
		return false;
	}
	
	public static boolean isNumberInColumn(int[][] board, int number, int column)
	{
		for (int i = 0; i < 9; i++)
		{if(board[i][column] == number) {return true;}}
		return false;
	}
	
	public static boolean isNumberInBox(int[][] board, int number, int row, int column)
	{
		int localBoxRow = row - row % 3;
		int localBoxColumn = column - column % 3;
		
		for (int i = localBoxRow; i < localBoxRow + 3; i++)
		{
			for (int j = localBoxColumn; j < localBoxColumn + 3; j++)
			{if(board[i][j] == number) {return true;}}
		}
		return false;	
	}
}