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
	
	int PANEL_SIZE = 600;
	int PIXEL_SIZE = 3;
	int pixelsPerRow = PANEL_SIZE/PIXEL_SIZE;
	
	int[][] moveableObjects = new int[2][1];
	int objectAmount = 1;
	
	int activeGRIDX = -1;	//column that has been clicked on - by default outside of screen
	int activeGRIDY = -1;
	
	boolean flyMode = false;
	boolean creationMode = false;
	boolean collision = false;
	
	byte[][] grid = new byte[pixelsPerRow][pixelsPerRow]; //actual grid on screen
	
	int creationGridLength = pixelsPerRow/3;	//grid where user can draw their object
		
	PixelCollisionPanel()
	{				
		this.setPreferredSize(new Dimension (PANEL_SIZE,PANEL_SIZE));
		
		moveableObjects[0][0] = PIXEL_SIZE;
		moveableObjects[1][0] = PIXEL_SIZE;
		
		DragListener dragListener = new DragListener();
		this.addMouseMotionListener(dragListener);
	}
	
	public void start()
	{
		pixelsPerRow = PANEL_SIZE/PIXEL_SIZE;
		creationGridLength = pixelsPerRow/3;
		
		//memory management and also fitting the array sizes to the new values
		grid = null;
		System.gc();
		grid = new byte[pixelsPerRow][pixelsPerRow];
		
		repaint();
	}
	
	public void newObject()
	{
		creationMode = true; //for paint method (drawing the rectangle around the space for user input)
		
		//get rid of any filled out pixels within the area for user input (offset the grid by 2 for better look)
		for (int i = 0; i < creationGridLength+2; i++)
		{
			for (int j = 0; j < creationGridLength+2; j++)
			{grid[i+2][j+2] = 0;}
		}
		repaint();
	}
	
	public void saveObject()
	{
		creationMode = false;
		
		//count the amount of pixels
		objectAmount = 0;
		for (int i = 2; i < creationGridLength+4; i++)
		{
			for (int j = 2; j < creationGridLength+4; j++)
			{
				if (grid[i][j] != 0)
				{objectAmount++;}
			}
		}
		
		//reconstruct the array with the right size
		moveableObjects = null;
		System.gc();
		moveableObjects = new int[2][objectAmount];
		
		//project the objects indices into screen coordinates
		int index = 0;
		for (int x = 2; x < creationGridLength+4; x++)
		{
			for (int y = 2; y < creationGridLength+4; y++)
			{
				if (grid[x][y] != 0)
				{
					moveableObjects[0][index] = x*PIXEL_SIZE;
					moveableObjects[1][index] = y*PIXEL_SIZE;
					index++;
				}
			}
		}
		
		//now get rid of the drawn pixels
		for (int i = 2; i < creationGridLength+4; i++)
		{
			for (int j = 2; j < creationGridLength+4; j++)
			{grid[i+2][j+2] = 0;}
		}
		repaint();
	}
	
	public void paint (Graphics g)
	{
		Graphics2D g2D = (Graphics2D) g;
		super.paint(g);
		
		//background
		g2D.setPaint(new Color (50,50,60));
		g2D.fillRect(0, 0, PANEL_SIZE, PANEL_SIZE);
		
		if (creationMode)
		{
			g2D.setPaint(new Color (50,250,150));
			g2D.setStroke(new BasicStroke(2));
			g2D.drawRect(2*PIXEL_SIZE, 2*PIXEL_SIZE, (creationGridLength+2)*PIXEL_SIZE, (creationGridLength+2)*PIXEL_SIZE);
		}
		
		//filling in the pixels
		g2D.setPaint(new Color (50,180,250));
		for (int i = 0; i < pixelsPerRow; i++)
		{
			for (int j = 0; j < pixelsPerRow; j++)
			{
				if (grid[i][j] != 0)
				{
					g2D.fillRect(i*PIXEL_SIZE, j*PIXEL_SIZE, PIXEL_SIZE, PIXEL_SIZE);
				}
			}
		}
		
		//drawing the moveableObjects
		g2D.setPaint(Color.red);
		for (int i = 0; i < objectAmount; i++)
		{g2D.fillRect(moveableObjects[0][i], moveableObjects[1][i], PIXEL_SIZE, PIXEL_SIZE);}
	}

	private class DragListener extends MouseMotionAdapter
	{
	    public void mouseDragged(MouseEvent e) 
	    {  
	    	int x = e.getX();
	    	int y = e.getY();
	    	
	    	//make sure the event occurred on screenspace
	    	if (x > PANEL_SIZE-PIXEL_SIZE || x < 0 || y > PANEL_SIZE-PIXEL_SIZE || y < 0) {return;}
	    	
	    	activeGRIDX = x / PIXEL_SIZE;
	    	activeGRIDY = y / PIXEL_SIZE;

	    	if (e.isShiftDown()) 
			{grid[activeGRIDX][activeGRIDY] = 0;}
			else 
			{grid[activeGRIDX][activeGRIDY] = 1;}
	    	
	    	repaint();
	    }
	}
	
	public void move (int x, int y, int fallingSpeed)
	{
		//grid indices of the objects
		int objectGRIDX;
		int objectGRIDY;
		
		for (int i = 0; i < objectAmount; i++)
		{
			moveableObjects[0][i]+=x; 
			moveableObjects[1][i]+=y;
			
			collision = false;

			//make sure no object exits screenspace + a few pixels buffer
			if (moveableObjects[0][i] <= 0 || moveableObjects[1][i] <= 0 || moveableObjects[0][i] >= PANEL_SIZE-PIXEL_SIZE*2 || moveableObjects[1][i] >= PANEL_SIZE-PIXEL_SIZE*2)
			{abortMove(x,y, i); collision = true;}
			
			objectGRIDX = moveableObjects[0][i] / PIXEL_SIZE;
			objectGRIDY = moveableObjects[1][i] / PIXEL_SIZE;
		
			if (grid[objectGRIDX][objectGRIDY] != 0)
			{abortMove(x,y, i); collision = true;}
				
			//THE FOLLOWING NEEDS TO BE CHECKED THOUGH IT BECOMES LESS IMPORTANT THE MORE OBJECTS
			//THERE ARE, BUT SINCE PERFORMANCE IS SO GOOD, CHECK IT EVERY TIME
			
			int xCheck = 1;
			int yCheck = 1;
			
			if (x > 0) {xCheck = -1;} 
			if (y > 0) {yCheck = -1;}
				
			//DO THIS TO CHECK THE TWO PIXELS THAT ARE BETWEEN PREV AND CURRENT POSITION
			//DURING DIAGONAL MOVEMENT
			/* 0 IS PREVIOUS, P IS CURRENT POSITION
			* CASES:
			* 		[]P		 []0	 0[]	  P[]
			* 		0[]		 P[]	 []P	  []0
			* 		x+ y-	x- y+	x+ y+	 x- y-
			*  ========================================= --> INVERSE X AND Y
			*  	 [x-]P	    [y-]0	  0[y-]	 	P[x+]
			* 	   0[y+]	  P[x+]	 [x-]P    [y+]0
			*  =========================================  
			*/
				
			if ((grid[objectGRIDX+xCheck][objectGRIDY] != 0) && (grid[objectGRIDX][objectGRIDY+yCheck] != 0))
			{abortMove(x,y,i); collision = true;}
			
			if (collision) {break;} //no need to do more calculations if we about this rounds movement anyway	
		}
		
		//do the checks for falling movement after so we can abort fall movement separately
		if (fallingSpeed != 0)
		{
			for (int i = 0; i < objectAmount; i++)
			{
				//make sure it's in screenspace
				if (moveableObjects[1][i]+fallingSpeed < PANEL_SIZE-PIXEL_SIZE && moveableObjects[1][i]+fallingSpeed >0)
				{
					moveableObjects[1][i]+=fallingSpeed;
					objectGRIDX = moveableObjects[0][i] / PIXEL_SIZE;
					objectGRIDY = moveableObjects[1][i] / PIXEL_SIZE;
					
					if (grid[objectGRIDX][objectGRIDY] != 0) 
					{abortFall(fallingSpeed, i); collision = true; break;}
				}
			}
		}
		
		repaint();	
	}
	
	//don't need to abort beyond index, because those objects haven't been moved yet
	public void abortFall(int fallingSpeed, int index)
	{
		for (int i = 0; i <= index; i++) 
		{moveableObjects[1][i]-=fallingSpeed;}
	}
	
	public void abortMove(int x, int y, int index)
	{
		for (int i = 0; i <= index; i++)
		{moveableObjects[0][i]-=x; moveableObjects[1][i]-=y;}
	}
	
	public void changeSize(int amount)
	{
		PANEL_SIZE+=amount;
		pixelsPerRow = PANEL_SIZE/PIXEL_SIZE;
		
		grid = null;
		System.gc();
		grid = new byte[pixelsPerRow][pixelsPerRow];
		
		this.setPreferredSize(new Dimension (PANEL_SIZE,PANEL_SIZE));
		repaint();
	}
}
