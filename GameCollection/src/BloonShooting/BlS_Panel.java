package BloonShooting;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JPanel;
import javax.swing.Timer;

public class BlS_Panel extends JPanel implements ActionListener
{
	private static final long serialVersionUID = 5219711456361037203L;
	
	private int PANEL_WIDTH = 1000;
	private int PANEL_HEIGHT = 600;
	
	private final Color BACKGROUND = new Color(50,50,60);
	
	//SLINGSHOT
	private Slingshot slingshot = new Slingshot();
	private Color[] slingshotColors = {Slingshot.Color1,Slingshot.Color2};
	
	//PROJECTILE
	private Projectile projectile = new Projectile();
	private Color[] projectileColors = {Projectile.Color1,Projectile.Color2,Projectile.Color3,Projectile.Color4};
	
	//MAP & GRID
	private boolean gridVisible = false;
	
	//SIMULATION
	private final Timer shootTimer = new Timer(15,this);

	BlS_Panel()
	{
		this.setPreferredSize(new Dimension( PANEL_WIDTH, PANEL_HEIGHT));
		
		ClickListener clickListener = new ClickListener();
		DragListener dragListener = new DragListener();
		ReleaseListener releaseListener = new ReleaseListener();
		
		this.addMouseListener(clickListener);
		this.addMouseMotionListener(dragListener);
		this.addMouseListener(releaseListener);
		
		slingshot.initialize(PANEL_WIDTH, PANEL_HEIGHT, PANEL_WIDTH/250);
		projectile.initialize(slingshot.getPullPoint(), PANEL_WIDTH/450);
	}
	
	public void paint(Graphics g)
	{
		Graphics2D g2D = (Graphics2D) g;
		super.paint(g);
		
		//BACKGROUND
		g2D.setPaint(BACKGROUND);
		g2D.fillRect(0, 0, PANEL_WIDTH, PANEL_HEIGHT);
		
		//GRID
		if (gridVisible)
		{
			g2D.setPaint(Color.LIGHT_GRAY);
			for (int i = 0; i < 34; i++)
			{g2D.drawLine(i*30, 0, i*30, PANEL_HEIGHT);}
			for (int i = 0; i < 20; i++)
			{g2D.drawLine(0, i*30, PANEL_WIDTH, i*30);}
		}
		
		//SLINGSHOT
		paintSprite(slingshotColors, Slingshot.SPRITE, slingshot.getOrigin(), slingshot.getPixelSize(), g2D);
		
		//slingshot band
		g2D.setStroke(new BasicStroke(PANEL_WIDTH/150));
		g2D.setPaint(Slingshot.SlingColor);
		int[] paintOrigin = slingshot.getPaintOrigin();
		int[] pullPoint = slingshot.getPullPoint();
		g2D.drawLine(paintOrigin[0], paintOrigin[1], pullPoint[0], pullPoint[1]);
		g2D.drawLine(paintOrigin[2], paintOrigin[1], pullPoint[0], pullPoint[1]);
		
		//PROJECTILE
		paintSprite(projectileColors, Projectile.SPRITE, projectile.getOrigin(), projectile.getPixelSize(), g2D);
		
	}
	
	private void paintSprite(Color[] colors, byte[] sprite, int [] origin, int pixelSize, Graphics2D g2D)
	{
		int row = 0, column = 0;
		
		for (int i = 0; i < 16*16; i++)
		{
			if (column == 16) {row++; column = 0;}
			
			if (sprite[i] != 0)
			{
				g2D.setPaint(colors[sprite[i]-1]);
				g2D.fillRect(origin[0] + column*pixelSize, origin[1]+ row*pixelSize, pixelSize, pixelSize);
			}
			column++;
		}
	}
	
	private class ClickListener extends MouseAdapter
	{
		public void mousePressed(MouseEvent e) 
		{
			if(!shootTimer.isRunning())
			slingshot.setDragValid(e.getX(), e.getY());
		}
	}
	   
	private class DragListener extends MouseMotionAdapter
	{
		public void mouseDragged(MouseEvent e) 
		{
			if (!slingshot.isDragValid() || shootTimer.isRunning()) {return;}
			
			slingshot.setPullPoint(e.getX(), e.getY());
			projectile.attachedMove(slingshot.getPullPoint());
			repaint();
		}
	}
	
	private class ReleaseListener extends MouseAdapter
	{
		public void mouseReleased(MouseEvent e) 
		{
			if (!slingshot.isDragValid() || shootTimer.isRunning()) {return;}
			slingshot.setReturnVect(); slingshot.slingReturnRounds = 0;
			projectile.setSpeed(slingshot.getReturnVect());
			shootTimer.start();
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{	
		if (slingshot.moveSling()) //moves slingshot while also checking if the slingshot has stopped moving
		{projectile.attachedMove(slingshot.getPullPoint());}
		
		else
		{
			if (!projectile.detachedMove(PANEL_HEIGHT)) //moves projectile while also checking if the projectile is still in the air
			{shootTimer.stop(); projectile.initialize(slingshot.getPullPoint(), PANEL_WIDTH/450);}
		} 
		repaint();
	}
	
	public void changeGridVisibility()
	{gridVisible = !gridVisible; repaint();}

}