package pixelCollision;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JPanel;

public class PixelCollisionPanel extends JPanel
{
	private static final long serialVersionUID = 9082942097976766954L;
	
	private int PANEL_SIZE = 600;
	private int PIXEL_SIZE = 3;
	private int pixelsPerRow;

	private MovableObject movObj;
	private byte[][] grid = new byte[pixelsPerRow][pixelsPerRow];
	
	private boolean creationMode = false;
	private int creationGridSize;
		
	PixelCollisionPanel()
	{
		this.setPreferredSize(new Dimension (PANEL_SIZE,PANEL_SIZE));
		this.addMouseMotionListener(new DragListener());
		movObj = new MovableObject(this);
		start();
	}
	
	public void start()
	{
		pixelsPerRow = PANEL_SIZE/PIXEL_SIZE;
		creationGridSize = pixelsPerRow/3;
		
		grid = null; System.gc();
		grid = new byte[pixelsPerRow][pixelsPerRow];
		repaint();
	}
	
	public void changeSize(int amount)
	{
		endCreationMode();
		PANEL_SIZE+=amount;
		this.setPreferredSize(new Dimension (PANEL_SIZE,PANEL_SIZE));
		start();
	}
	
	public MovableObject getMovableObject() {return movObj;}
	
	public int getPanelSize() {return PANEL_SIZE;}
	
	public int getPixelSize() {return PIXEL_SIZE;}
	
	public int getCreationGridSize() {return creationGridSize;}
	
	public boolean isPixelSolid(int x, int y) {return (grid[x][y] == 1);}
	
	public void setPixelSize(int size) 
	{
		PIXEL_SIZE = size;
		start();
		startCreationMode();
	}
	
	//---------------------------------------MOUSE---------------------------------------
	
	private class DragListener extends MouseMotionAdapter
	{
		public void mouseDragged(MouseEvent e) 
		{setPixel(e.getX(), e.getY(), e.isShiftDown());}
	}

	private void setPixel(int x, int y, boolean shiftDown)
	{	
		if (x > PANEL_SIZE-PIXEL_SIZE || x < 0 || y > PANEL_SIZE-PIXEL_SIZE || y < 0) {return;}

		grid[x / PIXEL_SIZE][y / PIXEL_SIZE] = (byte) (shiftDown ? 0 : 1);
		repaint();
	}
	
	//---------------------------------------CREATION_MODE---------------------------------------
	
	public void startCreationMode()
	{
		creationMode = true;
		clearInputBox();
	}
	
	public void endCreationMode()
	{
		creationMode = false;
		movObj.save();
		clearInputBox();
	}
	
	private void clearInputBox()
	{
		// offset by 2 to account for border size
		for (int i = 2; i < creationGridSize+2; i++)
		{
			for (int j = 2; j < creationGridSize+2; j++)
			{grid[i][j] = 0;}
		}
		repaint();
	}
	
	//---------------------------------------PAINT---------------------------------------
	
	private static final Color background_col = new Color (50,50,60);
	private static final Color outline_col = new Color (50,250,150);
	private static final Color pixel_col = new Color(50,180,250);
	private static final Color object_col = Color.red;
	
	public void paint (Graphics g)
	{
		Graphics2D g2D = (Graphics2D) g;
		
		//BACKGROUND
		g2D.setPaint(background_col);
		g2D.fillRect(0, 0, PANEL_SIZE, PANEL_SIZE);
		
		//OUTLINE
		if (creationMode)
		{
			g2D.setPaint(outline_col);
			g2D.setStroke(new BasicStroke(2));
			g2D.drawRect(2*PIXEL_SIZE, 2*PIXEL_SIZE, creationGridSize*PIXEL_SIZE, creationGridSize*PIXEL_SIZE);
		}
		
		//PIXELS
		g2D.setPaint(pixel_col);
		for (int i = 0; i < pixelsPerRow; i++)
		{
			for (int j = 0; j < pixelsPerRow; j++)
			{
				if (grid[i][j] != 0)
				{g2D.fillRect(i*PIXEL_SIZE, j*PIXEL_SIZE, PIXEL_SIZE, PIXEL_SIZE);}
			}
		}
		
		//MOVABLE OBJECT
		g2D.setPaint(object_col);
		for (int i = 0; i < movObj.pixelCount; i++)
		{g2D.fillRect(movObj.pixelsX[i], movObj.pixelsY[i], PIXEL_SIZE, PIXEL_SIZE);}
	}
}