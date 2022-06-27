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
	
	int PANEL_WIDTH = 900;
	int PANEL_HEIGHT = 600;

	int PIXEL_SIZE = PANEL_WIDTH / 200;
	
	private final Color BACKGROUND = new Color(50,50,60);
	
	//SLINGSHOT
	private Slingshot slingshot = new Slingshot();
	
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
		
		slingshot.initPoints(PANEL_WIDTH, PANEL_HEIGHT);
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
			for (int i = 0; i < 30; i++)
			{g2D.drawLine(i*30, 0, i*30, PANEL_HEIGHT);}
			for (int i = 0; i < 20; i++)
			{g2D.drawLine(0, i*30, PANEL_WIDTH, i*30);}
		}
		
		//SLINGSHOT
		paintSprite
		(new Color[] {Slingshot.LIGHTcol,Slingshot.DARKcol}, BlS_Databox.SLINGSHOT_SPRITE, slingshot.getOrigin(), g2D);
		
		//slingshot band
		g2D.setStroke(new BasicStroke(PANEL_WIDTH/150));
		g2D.setPaint(Slingshot.BANDcol);
		int[] paintOrigin = slingshot.getPaintOrigin(PIXEL_SIZE);
		int[] pullPoint = slingshot.getPullPoint();
		g2D.drawLine(paintOrigin[0], paintOrigin[1], pullPoint[0], pullPoint[1]);
		g2D.drawLine(paintOrigin[0]+(PIXEL_SIZE*14), paintOrigin[1], pullPoint[0], pullPoint[1]);
		
	}
	
	private void paintSprite(Color[] colors, byte[] sprite, int [] origin, Graphics2D g2D)
	{
		int row = 0;
		int column = 0;
		
		for (int i = 0; i < 16*16; i++)
		{
			if (column == 16) {row++; column = 0;}
			
			if (sprite[i] != 0)
			{
				g2D.setPaint(colors[sprite[i]-1]);
				g2D.fillRect(origin[0] + column*PIXEL_SIZE, origin[1]+ row*PIXEL_SIZE, PIXEL_SIZE, PIXEL_SIZE);
			}
			
			column++;
		}
		
	}
	
	private class ClickListener extends MouseAdapter
	{
		public void mousePressed(MouseEvent e) 
		{slingshot.setDragValid(e.getX(), e.getY());}
	}
	   
	private class DragListener extends MouseMotionAdapter
	{
		public void mouseDragged(MouseEvent e) 
		{
			if (!slingshot.isDragValid()) {return;}
			
			slingshot.setPullPoint(e.getX(), e.getY());
			repaint();
		}
	}
	
	private class ReleaseListener extends MouseAdapter
	{
		public void mouseReleased(MouseEvent e) 
		{
			if (!slingshot.isDragValid()) {return;}
			shootTimer.start();
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{	
		moveSling();
		
		
	}
	
	private void moveSling()
	{
		int[] returnVect = slingshot.getReturnVect();
		
		
	}
	
	public void changeGridVisibility()
	{gridVisible = !gridVisible; repaint();}

}