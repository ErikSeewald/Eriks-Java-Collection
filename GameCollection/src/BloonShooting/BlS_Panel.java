package BloonShooting;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JPanel;

public class BlS_Panel extends JPanel
{
	private static final long serialVersionUID = 5219711456361037203L;
	
	int PANEL_WIDTH = 900;
	int PANEL_HEIGHT = 600;

	int PIXEL_SIZE = PANEL_WIDTH / 200;
	
	private final Color BACKGROUND = new Color(50,50,60);
	
	//SLINGSHOT
	private final Color SLING_LIGHT = new Color(110,125,170);
	private final Color SLING_DARK = new Color(95,110,160);
	private final Color SLING_BAND = new Color(75,75,110);
	
	private int SLING_ORIGINX = PANEL_WIDTH/10;
	private int SLING_ORIGINY = PANEL_HEIGHT - (PANEL_HEIGHT/4);
	
	private int PULL_POINTX = (int) (PANEL_WIDTH/7.3);
	private int PULL_POINTY = PANEL_HEIGHT - (PANEL_HEIGHT/5);
	
	private boolean dragValid = false;
	
	//MAP & GRID
	private boolean gridVisible = false;
	

	BlS_Panel()
	{
		this.setPreferredSize(new Dimension( PANEL_WIDTH, PANEL_HEIGHT));
		
		ClickListener clickListener = new ClickListener();
		DragListener dragListener = new DragListener();
		ReleaseListener releaseListener = new ReleaseListener();
		
		this.addMouseListener(clickListener);
		this.addMouseMotionListener(dragListener);
		this.addMouseListener(releaseListener);
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
		paintSprite(new Color[] {SLING_LIGHT,SLING_DARK}, BlS_Databox.SLINGSHOT_SPRITE, g2D);
		
		g2D.setStroke(new BasicStroke(PANEL_WIDTH/150));
		g2D.setPaint(SLING_BAND);
		g2D.drawLine(SLING_ORIGINX+PIXEL_SIZE, SLING_ORIGINY+PIXEL_SIZE, PULL_POINTX, PULL_POINTY);
		g2D.drawLine(SLING_ORIGINX+PIXEL_SIZE*16, SLING_ORIGINY+PIXEL_SIZE, PULL_POINTX, PULL_POINTY);
		
	}
	
	private void paintSprite(Color[] colors, byte[] sprite, Graphics2D g2D)
	{
		int row = 0;
		int column = 0;
		
		for (int i = 0; i < 16*16; i++)
		{
			if (column == 16) {row++; column = 0;}
			
			if (sprite[i] != 0)
			{
				g2D.setPaint(colors[sprite[i]-1]);
				g2D.fillRect(SLING_ORIGINX + column*PIXEL_SIZE, SLING_ORIGINY+ row*PIXEL_SIZE, PIXEL_SIZE, PIXEL_SIZE);
			}
			
			column++;
		}
		
	}
	
	private class ClickListener extends MouseAdapter
	{
		public void mousePressed(MouseEvent e) 
		{
			int x = e.getX(), y = e.getY();
			dragValid = (x > PULL_POINTX-10 && x < PULL_POINTX+10 && y > PULL_POINTY-10 && y < PULL_POINTY+10);
		}
	}
	   
	private class DragListener extends MouseMotionAdapter
	{
		public void mouseDragged(MouseEvent e) 
		{
			if (!dragValid) {return;}
			
			PULL_POINTX = e.getX();
			PULL_POINTY = e.getY();
			repaint();
		}
	}
	
	private class ReleaseListener extends MouseAdapter
	{
		public void mouseReleased(MouseEvent e) 
		{
			if (!dragValid) {return;}
			
			PULL_POINTX = (int) (PANEL_WIDTH/7.3);
			PULL_POINTY = PANEL_HEIGHT - (PANEL_HEIGHT/5);
			repaint();
		}
	}
	
	public void changeGridVisibility()
	{gridVisible = !gridVisible; repaint();}
}