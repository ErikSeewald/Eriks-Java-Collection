package bloonShoot;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import bloonShoot.shot.*;

public class MouseHandler 
{
	private ShotHandler shotHandler;
	private BlS_Panel panel;
	
	MouseHandler(ShotHandler shotHandler, BlS_Panel panel) 
	{this.shotHandler = shotHandler; this.panel = panel;}
	
	public class ClickListener extends MouseAdapter
	{
		public void mousePressed(MouseEvent e) 
		{shotHandler.setDragValid(e.getX(), e.getY());}
	}
	   
	public class DragListener extends MouseMotionAdapter
	{
		public void mouseDragged(MouseEvent e) 
		{
			shotHandler.moveSling(e.getX(), e.getY());
			panel.repaint();
		}
	}
	
	public class ReleaseListener extends MouseAdapter
	{
		public void mouseReleased(MouseEvent e) 
		{shotHandler.releaseSling();}
	}
}