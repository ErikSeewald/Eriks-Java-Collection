package mouseDodge;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class DragListener extends MouseMotionAdapter
{	
	private Player player;
	private Board board;
	DragListener(Player player, Board board) {this.player = player; this.board = board;}

	public void mouseDragged(MouseEvent e) 
	{movement(e);}

	public void mouseMoved(MouseEvent e) 
	{movement(e);}

	private void movement(MouseEvent e)
	{
		if (player.isMoveAllowed())
		{ 				
			int x = e.getX(), y = e.getY();

			if ((x > board.X && x < board.X + board.width)) {player.X = x-player.size/2;}
			if ((y > board.Y && y < board.Y + board.height)) {player.Y = y-player.size/2;}		    				
		}			
	}
}