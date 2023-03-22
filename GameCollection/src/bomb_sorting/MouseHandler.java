package bomb_sorting;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class MouseHandler 
{
	private Bomb selectedBomb = null;
	
	private Sort_Panel panel;
	private GameHandler gameHandler;
	
	MouseHandler(Sort_Panel panel, GameHandler gameHandler)
	{this.panel = panel; this.gameHandler = gameHandler;}

	public class ClickListener extends MouseAdapter
	{
		public void mousePressed(MouseEvent e) 
		{
			int x = e.getX();
			int y = e.getY();
			
			for (Bomb bomb: gameHandler.bombs)
			{
				if (x > bomb.x && x < bomb.x + Bomb.size && y > bomb.y && y < bomb.y + Bomb.size)
				{
					selectedBomb = bomb;
					bomb.isHeld = true;
					return;
				}
			}
		}
	}

	public class DragListener extends MouseMotionAdapter
	{
		public void mouseDragged(MouseEvent e) 
		{
			if (selectedBomb == null) {return;}

			selectedBomb.x = e.getX() - (Bomb.size >> 1);
			selectedBomb.y = e.getY() - (Bomb.size >> 1);
			panel.repaint();
		}
	}

	public class ReleaseListener extends MouseAdapter
	{
		public void mouseReleased(MouseEvent e) 
		{
			if (selectedBomb == null) {return;}
			
			selectedBomb.sort_state = gameHandler.checkIfSorted(selectedBomb);		
			if (selectedBomb.sort_state == Bomb.sorted_incorrectly)
			{gameHandler.explosionEvent(selectedBomb);}
			
			selectedBomb.isHeld = false;
			selectedBomb = null;
		}
	}
}