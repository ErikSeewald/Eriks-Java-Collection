package GravityVectors;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JPanel;

public class GravityVectorsPanel extends JPanel
{
	private static final long serialVersionUID = 3933829351442493815L;
	
	private int PANEL_WIDTH = 900;
	private int PANEL_HEIGHT = (int) (PANEL_WIDTH*(0.66));
	
	//ARROW VARIABLES
	final private int APOINT_SIZE = 6;
	final private int ARROW_LENGTH = 12;
	final private int APOINT_DISTANCE = APOINT_SIZE * 5; //distance between arrows
	private int APOINT_COUNT_X = 30; //how many arrows on x axis
	private int APOINT_COUNT_Y = APOINT_COUNT_X * 2/3; //how many arrows on y axis
	private ArrowPoint[] arrowPoints;
	
	//PULL/GRAVITY POINT VARIABLES
	final private int PPOINT_SIZE = 12;
	private int PPOINT_COUNT = 1;
	private int selectedPPOINT = 0;
	private PullPoint[] pullPoints = {new PullPoint()};
	
	//COLORS
	final private Color BACKGROUND_COLOR = new Color(50,50,70);
	final private Color ARROW_COLOR = new Color(180,180,200);
	final private Color PPOINT_COLOR = new Color(255,80,80);
	
	//GRADIENT MODE
	boolean gradientMode = false;
	final private float[] gradientFractions = {0.0f, 1.0f};
	final private Color[] gradientColors = {PPOINT_COLOR, Color.green};
	
	boolean validDrag = false;
	private boolean normalizeVector = true;
	
	GravityVectorsPanel()
	{
		this.setPreferredSize(new Dimension(PANEL_WIDTH,PANEL_HEIGHT));
		
		ClickListener clickListener = new ClickListener();
		DragListener dragListener = new DragListener();
		this.addMouseListener(clickListener);
		this.addMouseMotionListener(dragListener);
		
		start();
	}
	
	public void start()
	{
		//CLEAR OLD ARRAY FROM MEMORY, SET UP NEW ONE WITH ADJUSTED SIZE
		arrowPoints = null;
		System.gc();
		arrowPoints = new ArrowPoint[APOINT_COUNT_X * APOINT_COUNT_Y];
		
		//ARROWPOINTS
		int index = 0;
		for (int i = 0; i < APOINT_COUNT_X; i++)
		{
			for (int j = 0; j < APOINT_COUNT_Y; j++)
			{
				arrowPoints[index] = new ArrowPoint(i*APOINT_DISTANCE+(APOINT_DISTANCE / 3), j*APOINT_DISTANCE+(APOINT_DISTANCE / 3));
				index++;
			}
		}
		
		simulate(); repaint();
	}
	
	private class ArrowPoint
	{
		ArrowPoint(int locX, int locY)
		{this.locX = locX; this.locY = locY; arrowX = locX + 15;  arrowY = locY;}
		
		//LOCATIONS OF THE ACTUAL POINTS
		int locX;
		int locY;
		
		//LOCATIONS OF THE ENDS OF THE ARROWS
		int arrowX;
		int arrowY;
	}
	
	private class PullPoint
	{
		int locX = 30;
		int locY = 30;
	}
	
	private void simulate()
	{
		for (ArrowPoint point : arrowPoints)
		{
			double[] vectorsX = new double[PPOINT_COUNT];
			double[] vectorsY = new double[PPOINT_COUNT];
			
			for (int i = 0; i < PPOINT_COUNT; i++)
			{
				vectorsX[i] = pullPoints[i].locX - point.locX;
				vectorsY[i] = pullPoints[i].locY - point.locY;
			}
			
			double[] distances = new double[PPOINT_COUNT];
			
			for (int i = 0; i < PPOINT_COUNT; i++)
			{
				distances[i] = Math.sqrt(Math.pow((vectorsX[i]),2) + Math.pow((vectorsY[i]),2));
			}
			
			//HAVE THE PPOINTS EFFECT THE ARROWS EXPONENTIALLY MORE, THE SMALLER THE DISTANCE BETWEEN THEM IS (0.99^distance)
			//-> HIGHER DISTANCE = SMALLER NUMBER WITH WHICH THE VECTOR WILL END UP BEING MULTIPLIED
			double[] strengths = new double[PPOINT_COUNT];
			for (int i = 0; i < PPOINT_COUNT; i++)
			{strengths[i] = Math.pow(0.99, distances[i]);}
			
			//NORMALIZE VECTORS SO "STRENGTHS" CAN ACTUALLY AFFECT THE VECTORS PROPORTIONALLY
			for (int i = 0; i < PPOINT_COUNT; i++)
			{
				double[] temp = normalize(vectorsX[i], vectorsY[i], distances[i]);
				vectorsX[i] = temp[0];
				vectorsY[i] = temp[1];
			}
			
			double arrowVectX = 0;
			double arrowVectY = 0;
			
			//COMBINE ALL THE VECTORS MULTIPLIED BY THEIR STRENGTH
			for (int i = 0; i < PPOINT_COUNT; i++)
			{
				arrowVectX+= vectorsX[i] * strengths[i];
				arrowVectY+= vectorsY[i] * strengths[i];
			}
			
			//NORMALIZE ONE FINAL TIME TO MULTIPLY WITH ARROW_LENGTH
			if (normalizeVector)
			{
				double length = Math.sqrt(Math.pow(arrowVectX, 2)+Math.pow(arrowVectY, 2));
				double[] temp = normalize(arrowVectX, arrowVectY, length);
				arrowVectX = temp[0];
				arrowVectY = temp[1];
			}
			
			point.arrowX = (int) (point.locX + arrowVectX * ARROW_LENGTH);
			point.arrowY = (int) (point.locY + arrowVectY * ARROW_LENGTH);
		}
	}
	
	public void switchArrowNormalization()
	{
		normalizeVector = !normalizeVector;
		simulate();
		repaint();
	}
	
	public double[] normalize(double x , double y, double length)
	{	
		double[] result = new double[2];
		
		result[0] = x/length;
		result[1] = y/length;
		
		return result;
	}
	 
	public void changeSize(int amount)
	{
		PANEL_WIDTH += amount;
		PANEL_HEIGHT = (int) (PANEL_WIDTH*(0.66));
		this.setPreferredSize(new Dimension(PANEL_WIDTH,PANEL_HEIGHT));
		
		APOINT_COUNT_X++;
		APOINT_COUNT_Y = APOINT_COUNT_X * 2/3;
		
		start();
	}
	
	public void changePPOINT_COUNT(int amount)
	{
		selectedPPOINT = 0; //make sure we don't keep having a point selected that is about to get deleted
		
		if (PPOINT_COUNT + amount < 1) {return;} //don't go below 1 PPOINT
		
		//save the locations so they can be reset after clearing the old array
		int[][] pullPointLocations = new int[PPOINT_COUNT][2];	
		for (int i = 0; i < PPOINT_COUNT; i++)
		{
			pullPointLocations[i][0] = pullPoints[i].locX;
			pullPointLocations[i][1] = pullPoints[i].locY;
		}
		
		PPOINT_COUNT+= amount;
				
		pullPoints = null;
		System.gc();
		pullPoints = new PullPoint[PPOINT_COUNT];
		
		//RESET TO OLD LOCATIONS
		//IF POINTS DECREASED, GRAB OLD LOCATIONS OF ALL POINTS, IF INCREASED, GRAB LOCATIONS OF ALL POINTS -1,
		//BECAUSE THE NEWEST DOES NOT HAVE AN OLD LOCATION
		int incOrdecr = -1;
		if (amount < 0) {incOrdecr = 0;}
		
		for (int i = 0; i < PPOINT_COUNT + incOrdecr; i++)
		{
			pullPoints[i] = new PullPoint();
			pullPoints[i].locX = pullPointLocations[i][0];
			pullPoints[i].locY = pullPointLocations[i][1];
		}
		
		//set new PullPoint, if there is one
		if (amount > 0)
		{pullPoints[PPOINT_COUNT-1] = new PullPoint();}
		
		simulate(); repaint();
	}	
	
	public void paint(Graphics g)
	{
		Graphics2D g2D = (Graphics2D) g;
		super.paint(g2D);
		
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		//BACKGROUND
		g2D.setPaint(BACKGROUND_COLOR);
		g2D.fillRect(0, 0, PANEL_WIDTH, PANEL_HEIGHT);
		
		//ARROW POINTS
		if (gradientMode)
		{
			RadialGradientPaint distFromCenter = 
			new RadialGradientPaint(pullPoints[selectedPPOINT].locX, pullPoints[selectedPPOINT].locY, 500, gradientFractions, gradientColors);
			g2D.setPaint(distFromCenter);
		}
		
		else 
		{g2D.setPaint(ARROW_COLOR);}
		
		for (int i = 0; i < arrowPoints.length; i++)
		{
			//prevent trying to draw line when the mouse is exactly on the point
			if (arrowPoints[i].arrowX != 0) 
			{g2D.drawLine(arrowPoints[i].locX, arrowPoints[i].locY, arrowPoints[i].arrowX, arrowPoints[i].arrowY);}
			
			g2D.drawRect(arrowPoints[i].arrowX-1, arrowPoints[i].arrowY-1, 2, 2);
		}
		
		//PULL POINTS
		g2D.setPaint(PPOINT_COLOR);
		for (int i = 0; i < PPOINT_COUNT; i++)
		{g2D.fillOval(pullPoints[i].locX-6, pullPoints[i].locY-6, PPOINT_SIZE, PPOINT_SIZE);}
		
	}
	
	private class ClickListener extends MouseAdapter
	{
	    public void mousePressed(MouseEvent e)
	    {selectGravityPoint(e.getX(), e.getY());} 
	}
	
	private class DragListener extends MouseMotionAdapter
	{
	    public void mouseDragged(MouseEvent e) 
	    {moveGravityPoint(e.getX(), e.getY()); simulate(); repaint();}  
	}
	
	private void selectGravityPoint(int X, int Y)
	{	
		validDrag = false;
		
		for (int i = 0; i < PPOINT_COUNT; i++)
		{
			if 
			(
				X > pullPoints[i].locX-10 && X < pullPoints[i].locX+10 &&
				Y > pullPoints[i].locY-10 && Y < pullPoints[i].locY+10 
			)
			{selectedPPOINT = i; validDrag = true; break;}
		}
	}
	
	private void moveGravityPoint(int X, int Y)
	{
		if (validDrag)
		{
			pullPoints[selectedPPOINT].locX = X;
			pullPoints[selectedPPOINT].locY = Y;
			
			repaint();
		}
	}
}
