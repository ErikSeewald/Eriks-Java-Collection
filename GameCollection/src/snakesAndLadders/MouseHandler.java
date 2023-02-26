package snakesAndLadders;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import snakesAndLadders.board.BoardHandler;
import snakesAndLadders.board.Player;
import snakesAndLadders.gui.SnL_GUI;

public class MouseHandler 
{
	private boolean clickInside = false;
	
	private SnL_Panel panel;
	private SnL_GUI GUI;
	private BoardHandler boardHandler;
	
	MouseHandler(SnL_Panel panel, SnL_GUI GUI, BoardHandler boardHandler)
	{this.panel = panel; this.GUI = GUI; this.boardHandler = boardHandler;}

	public class ClickListener extends MouseAdapter
	{
		public void mousePressed(MouseEvent e) 
		{
			if (!boardHandler.isMoveAvailable()) {return;}
			
			Player player = boardHandler.getCurrentPlayer();

			clickInside =
					e.getX() > player.x && e.getX() < player.x + SnL_Panel.player_size
					&& e.getY() > player.y && e.getY() < player.y + SnL_Panel.player_size;
			
		}
	}

	public class DragListener extends MouseMotionAdapter
	{
		public void mouseDragged(MouseEvent e) 
		{
			if (!boardHandler.isMoveAvailable() || !clickInside) {return;}

			boardHandler.getCurrentPlayer().drag(e.getX(), e.getY());;

			GUI.enableAutoMoveButton(false);
			panel.repaint();
		}
	}

	public class ReleaseListener extends MouseAdapter
	{
		public void mouseReleased(MouseEvent e) 
		{
			if (!boardHandler.isMoveAvailable() || !clickInside) {return;}
			clickInside = false;

			boardHandler.releasePlayer(boardHandler.getCurrentPlayer(), e.getX(), e.getY());
			
			panel.repaint();
		}
	}
}