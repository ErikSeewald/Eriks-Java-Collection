package GravityVectors;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JPanel;

public class GravityVectorsPanel extends JPanel
{
	private static final long serialVersionUID = 3933829351442493815L;
	
	private int PANEL_WIDTH = 900;
	private int PANEL_HEIGHT = (int) (PANEL_WIDTH*(0.66));
	
	final private int APOINT_SIZE = 6;
	final private int APOINT_DISTANCE = APOINT_SIZE * 5;
	private int APOINT_COUNT_X = 30;
	private int APOINT_COUNT_Y = 20;
	private ArrowPoint[] arrowPoints;
	
	final private int PPOINT_SIZE = 12;
	private int PPOINT_COUNT = 0;
	private int selectedPPOINT;
	private PullPoint[] pullPoints;
	
	final private Color BACKGROUND_COLOR = new Color(50,50,70);
	final private Color ARROW_COLOR = new Color(180,180,200);
	final private Color PPOINT_COLOR = new Color(255,80,80);
	
	GravityVectorsPanel()
	{
		this.setPreferredSize(new Dimension(PANEL_WIDTH,PANEL_HEIGHT));
		
		ClickListener clickListener = new ClickListener();
		DragListener dragListener = new DragListener();
		
		this.addMouseListener(clickListener);
		this.addMouseMotionListener(dragListener);
		
		start();
	}
	 
	public void changeSize(int amount)
	{
		PANEL_WIDTH += amount;
		PANEL_HEIGHT = (int) (PANEL_WIDTH*(0.66));
		this.setPreferredSize(new Dimension(PANEL_WIDTH,PANEL_HEIGHT));
		
		APOINT_COUNT_X++;
		APOINT_COUNT_Y++;
		
		start();
		repaint();
	}
	
	public void start()
	{
		arrowPoints = null;
		System.gc();
		arrowPoints = new ArrowPoint[APOINT_COUNT_X * APOINT_COUNT_Y];
		
		int index = 0;
		
		//ARROWPOINTS
		for (int i = 0; i < APOINT_COUNT_X; i++)
		{
			for (int j = 0; j < APOINT_COUNT_Y; j++)
			{
				arrowPoints[index] = new ArrowPoint(i*30+(APOINT_DISTANCE / 3), j*30+(APOINT_DISTANCE / 3));
				index++;
			}
		}
	}
	
	public void changePPOINT_COUNT(int amount)
	{
		if (PPOINT_COUNT + amount < 0) {return;}
		PPOINT_COUNT+= amount;
		
		if (PPOINT_COUNT == 0) {repaint(); return;}
		
		//save the locations so they can be reset after clearing the old array
		int[][] pullPointLocations = new int[PPOINT_COUNT][2];
		
		for (int i = 0; i < PPOINT_COUNT-1; i++)
		{
				pullPointLocations[i][0] = pullPoints[i].locX;
				pullPointLocations[i][1] = pullPoints[i].locY;
		}
	
		
		pullPoints = null;
		System.gc();
		pullPoints = new PullPoint[PPOINT_COUNT];
		
		//reset to old locations
		for (int i = 0; i < PPOINT_COUNT-1; i++)
		{
			pullPoints[i] = new PullPoint();
			pullPoints[i].locX = pullPointLocations[i][0];
			pullPoints[i].locY = pullPointLocations[i][1];
		}
		
		//set newest PullPoint
		pullPoints[PPOINT_COUNT-1] = new PullPoint();
		pullPoints[PPOINT_COUNT-1].locX = 30;
		pullPoints[PPOINT_COUNT-1].locY = 30;
		
		repaint();
	}
	
	private class ArrowPoint
	{
		ArrowPoint(int locX, int locY)
		{this.locX = locX; this.locY = locY; arrowX = locX + 15;  arrowY = locY;}
		
		int locX;
		int locY;
		
		int arrowX;
		int arrowY;
	}
	
	private class PullPoint
	{
		int locX;
		int locY;
	}
	
	private void simulate()
	{
		
	}
	
	public void paint(Graphics g)
	{
		Graphics2D g2D = (Graphics2D) g;
		super.paint(g2D);
		
		g2D.setPaint(BACKGROUND_COLOR);
		g2D.fillRect(0, 0, PANEL_WIDTH, PANEL_HEIGHT);
		
		//ARROW POINTS
		g2D.setPaint(ARROW_COLOR);
		for (int i = 0; i < arrowPoints.length; i++)
		{
			g2D.drawLine(arrowPoints[i].locX, arrowPoints[i].locY, arrowPoints[i].arrowX, arrowPoints[i].arrowY);
			g2D.drawRect(arrowPoints[i].locX-1, arrowPoints[i].locY-1, 2, 2);
		}
		
		//PULL POINTS
		if (PPOINT_COUNT != 0)
		{
			g2D.setPaint(PPOINT_COLOR);
			for (int i = 0; i < pullPoints.length; i++)
			{
				g2D.fillOval(pullPoints[i].locX, pullPoints[i].locY, PPOINT_SIZE, PPOINT_SIZE);
			}
		}
	}
	
	private class ClickListener extends MouseAdapter
	{
	    public void mousePressed(MouseEvent e)
	    {selectGravityPoint(e.getX(), e.getY());} 
	}
	
	private class DragListener extends MouseMotionAdapter
	{
	    public void mouseDragged(MouseEvent e) 
	    {moveGravityPoint(e.getX(), e.getY());}  
	}
	
	private void selectGravityPoint(int X, int Y)
	{	
		selectedPPOINT = -1;
		
		for (int i = 0; i < PPOINT_COUNT; i++)
		{
			if 
			(
				X > pullPoints[i].locX-10 && X < pullPoints[i].locX+10 &&
				Y > pullPoints[i].locY-10 && Y < pullPoints[i].locY+10 
			)
			{selectedPPOINT = i; break;}
		}
	}
	
	private void moveGravityPoint(int X, int Y)
	{
		if (selectedPPOINT == -1) {return;}
		
		pullPoints[selectedPPOINT].locX = X;
		pullPoints[selectedPPOINT].locY = Y;
		
		repaint();
	}
}
