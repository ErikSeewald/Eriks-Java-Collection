package bloonShoot;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.Timer;
import bloonShoot.shot.*;

public class MouseHandler 
{
	private Timer shot;
	private Slingshot slingshot;
	private Projectile projectile;
	private BlS_Panel panel;
	
	MouseHandler(Timer shot, Slingshot slingshot, Projectile projectile, BlS_Panel panel) 
	{this.shot = shot; this.slingshot = slingshot; this.projectile = projectile; this.panel = panel;}
	
	public class ClickListener extends MouseAdapter
	{
		public void mousePressed(MouseEvent e) 
		{
			if(shot.isRunning()) {return;}
			slingshot.setDragValid(e.getX(), e.getY());
		}
	}
	   
	public class DragListener extends MouseMotionAdapter
	{
		public void mouseDragged(MouseEvent e) 
		{
			if (!slingshot.isDragValid() || shot.isRunning()) {return;} 
			
			slingshot.setPullPoint(e.getX(), e.getY());
			projectile.setNewOrigin(slingshot.getPullPoint(), panel.pixelSize);
			panel.repaint();
		}
	}
	
	public class ReleaseListener extends MouseAdapter
	{
		public void mouseReleased(MouseEvent e) 
		{
			if (!slingshot.isDragValid() || shot.isRunning()) {return;}
			
			slingshot.setReturnVect();
			projectile.setSpeed(slingshot.getReturnVect());
			shot.start();
		}
	}
}