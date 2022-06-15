package ClothSim;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JPanel;

public class ClothSimPanel extends JPanel
{
	private static final long serialVersionUID = -5643933341241043804L;
	
	int PANEL_SIZEY = 600;
	int PANEL_SIZEX = (int)(PANEL_SIZEY *1.6);
	
	int pointAmount = 0;	//HOW MANY OF THE 200 PLACES IN THE ARRAY ARE IN USE
	Point[] points = new Point[200];
	
	int connectorAmount = 0;
	Connector[] connectors = new Connector[300];
	
	int pointSize = 10;
	
	int prevSelectedPoint;
	int selectedPoint;
	
	int sizeBuffer = 10; //PREVENT SPAWNING POINTS TOO CLOSE TOGETHER
	
	boolean mouseHeld = false;
	
	ClothSimPanel()
	{
		this.setPreferredSize(new Dimension(PANEL_SIZEX,PANEL_SIZEY));
		
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
		super.paint(g2D);
		
		//BACKGROUND
		g2D.setPaint(new Color(50,50,60));
		g2D.fillRect(0, 0,this.getWidth(), this.getHeight());
		
		if (pointAmount == 0) {return;}
	
		//POINTS
		g2D.setPaint(new Color(230,230,235));
		for (int i = 0; i < pointAmount; i++)
		{
			if (points[i].isLocked) 
			{g2D.setPaint(new Color(255,150,150));}
			else 
			{g2D.setPaint(new Color(230,230,235));}
			
			g2D.fillRect((int)points[i].positionX-pointSize/2, (int)points[i].positionY-pointSize/2, pointSize, pointSize);	
		}
		
		//SELECTED POINTS
		g2D.setPaint(new Color(150,255,150));
		if (selectedPoint != -1)
		{
			if (!points[selectedPoint].isLocked)
			{g2D.fillRect((int)points[selectedPoint].positionX-pointSize/2, (int)points[selectedPoint].positionY-pointSize/2, pointSize, pointSize);}
		}
		
		if (prevSelectedPoint != -1)
		{
			if (!points[prevSelectedPoint].isLocked)
			{g2D.fillRect((int)points[prevSelectedPoint].positionX-pointSize/2, (int)points[prevSelectedPoint].positionY-pointSize/2, pointSize, pointSize);}
		}
		
		//CONNECTORS
		g2D.setPaint(new Color(230,230,235));
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		for (int i = 0; i < connectorAmount; i++)
		{
			if (connectors[i].isAlive)
			{
				g2D.drawLine
				((int)connectors[i].pointA.positionX, (int)connectors[i].pointA.positionY, 
				(int)connectors[i].pointB.positionX, (int)connectors[i].pointB.positionY);
			}
		}
	}

	public class Point
	{
		float positionX;
		float positionY;
		
		float prevPositionX;
		float prevPositionY;
		
		boolean isLocked;
		
		int index;
		
		Point(int index)
		{this.index = index;}
	}
	
	public class Connector
	{
		Point pointA;
		Point pointB;
		
		float length;
		
		boolean isAlive = true;
	}
	
	public void simulation()
	{

		for (int i = 0; i < pointAmount; i++)
		{
			if (!points[i].isLocked)
			{	
				float positionXbefore = points[i].positionX;
				float positionYbefore = points[i].positionY;
				
				//VELOCITY
				points[i].positionX+= points[i].positionX - points[i].prevPositionX;
				points[i].positionY+= points[i].positionY - points[i].prevPositionY;
				
				points[i].positionY+= 1; //GRAVITY
				
				points[i].prevPositionX = positionXbefore;
				points[i].prevPositionY = positionYbefore;
				
				//FLOOR COLLISION
				if (points[i].positionY > this.getHeight()-pointSize/2) {points[i].positionY = this.getHeight()-pointSize/2;}
			}
		}
	
		for (int i = 0; i < connectorAmount; i++)
		{	
			if (connectors[i].isAlive)
			{
				float[] connectorCenter = {(connectors[i].pointA.positionX + connectors[i].pointB.positionX) / 2, 
										  (connectors[i].pointA.positionY + connectors[i].pointB.positionY) / 2};
					
				float vectorX = connectors[i].pointA.positionX - connectors[i].pointB.positionX;
				float vectorY = connectors[i].pointA.positionY - connectors[i].pointB.positionY;
						
				float[] connectorVector = normalize(vectorX, vectorY);
					
				//KEEP UP THE CONNECTORS LENGTH BY MOVING THE POINTS HALFWAY IT'S LENGTH IN THE CORRECT DIRECTION FROM IT'S CENTER	
				if (!connectors[i].pointA.isLocked)
				{
					connectors[i].pointA.positionX =  connectorCenter[0] + connectorVector[0] * connectors[i].length/2;
					connectors[i].pointA.positionY =  connectorCenter[1] + connectorVector[1] * connectors[i].length/2;
				}
						
				if (!connectors[i].pointB.isLocked)
				{
					connectors[i].pointB.positionX =  connectorCenter[0] - connectorVector[0] * connectors[i].length/2;
					connectors[i].pointB.positionY =  connectorCenter[1] - connectorVector[1] * connectors[i].length/2;
				}	
			}
		}
		repaint();
	}
	
	public float[] normalize(float x , float y)
	{	
		float[] result = new float[2];
		float length = (float)Math.sqrt(Math.pow(x, 2)+Math.pow(y, 2));
		
		result[0] = x/length;
		result[1] = y/length;
		
		return result;
	}
	
	public void connect(boolean repaint)
	{
		if (selectedPoint == -1 || prevSelectedPoint == -1 || pointAmount < 1 || selectedPoint == prevSelectedPoint) {return;}
		
		connectorAmount++;
		
		int cIndex = connectorAmount-1;
    	connectors[cIndex] = new Connector();
		
		connectors[cIndex].pointA = points[prevSelectedPoint];
		connectors[cIndex].pointB = points[selectedPoint];
		
		connectors[cIndex].length = (float)Math.sqrt
		(Math.pow(connectors[cIndex].pointA.positionX - connectors[cIndex].pointB.positionX, 2) 
		+ Math.pow(connectors[cIndex].pointA.positionY - connectors[cIndex].pointB.positionY, 2));
		
		if (repaint) {repaint();}
	}
	
	public void newPoint(int index)
	{points[index] = new Point(index);}
	
	public void removeLastConnector()
	{connectorAmount--; repaint();}
	
	public void lockPoint()
	{
		if (selectedPoint == -1 || pointAmount < 1) {return;}
		points[selectedPoint].isLocked = !points[selectedPoint].isLocked;
		repaint();
	}
	
	private class ClickListener extends MouseAdapter
	{
	    public void mousePressed(MouseEvent e) 
	    {	
	    	prevSelectedPoint = selectedPoint;
			selectedPoint = getPointIndex(e.getX(), e.getY());
			
			if (e.isControlDown()) {lockPoint(); return;}
		    		
		    if (selectedPoint == -1) //= NULL -> ADD POINT THERE
		    {
		    	pointAmount++;
				    	
				int pIndex = pointAmount-1;
				points[pIndex] = new Point(pIndex);
				    
				points[pIndex].positionX = e.getX();
				points[pIndex].positionY = e.getY();
						
				points[pIndex].prevPositionX = points[pIndex].positionX;
				points[pIndex].prevPositionY = points[pIndex].positionY;
		    }	
	    	repaint();	
	    }
	}
	
	private class DragListener extends MouseMotionAdapter
	{
	    public void mouseDragged(MouseEvent e) 
	    {
	    	if (!mouseHeld || e.isShiftDown()) //ONLY CHANGE THE SELECTED POINT WHEN CUTTING OR WHEN JUST STARTING TO PRESS
	    	{selectedPoint = getPointIndex(e.getX(), e.getY());}
	    	
    		if (selectedPoint == -1 || pointAmount < 1) {return;}
	    	
	    	if (!e.isShiftDown()) //MOVE HELD POINT
	    	{
	    		points[selectedPoint].positionX = e.getX();
	    		points[selectedPoint].positionY = e.getY();
	    	}
	    	
	    	else //"CUT" POINT BY MOVING IT OFF SCREEN AND KILLING IT'S CONNECTOR
	    	{
	    		points[selectedPoint].positionX = -10;
	    		points[selectedPoint].positionY = -10;
	    		
	    		for (int i = 0; i < connectorAmount; i++)
	    		{
	    			if (connectors[i].pointA == points[selectedPoint]) {connectors[i].isAlive = false;}
	    			else if (connectors[i].pointB == points[selectedPoint]) {connectors[i].isAlive = false;}
	    		}
	    	}
	    	
	    	mouseHeld = true;
	    	repaint();
	    }
	}
	
	private class ReleaseListener extends MouseAdapter	
	{
	    public void mouseReleased(MouseEvent e) 
	    {mouseHeld = false;}
	}
	
	public int getPointIndex(int x, int y)
	{
		int index = 0;
		
		x+= pointSize/2;
		y+= pointSize/2;
		
		for (int i = 0; i < pointAmount; i++)
		{
			if (x > points[i].positionX-sizeBuffer && x < points[i].positionX+pointSize+sizeBuffer &&
				y > points[i].positionY-sizeBuffer && y < points[i].positionY+pointSize+sizeBuffer)
			{return index;}
			index++;
		}
		return -1; //NO POINT FOUND
	}
	
	public void restart()
	{
		pointAmount = 0;
		connectorAmount = 0;
		prevSelectedPoint = -1;
		selectedPoint = -1;
		
		System.gc();
		repaint();
	}
}
