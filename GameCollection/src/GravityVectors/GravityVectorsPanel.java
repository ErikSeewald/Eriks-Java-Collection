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
	
	final private int APOINT_SIZE = 6;
	final private int ARROW_LENGTH = 12;
	final private int APOINT_DISTANCE = APOINT_SIZE * 5;
	private int APOINT_COUNT_X = 30;
	private int APOINT_COUNT_Y = 20;
	private ArrowPoint[] arrowPoints;
	
	final private int PPOINT_SIZE = 12;
	private int PPOINT_COUNT = 1;
	private int selectedPPOINT = 0;
	private PullPoint[] pullPoints = {new PullPoint(), new PullPoint()};
	
	final private Color BACKGROUND_COLOR = new Color(50,50,70);
	final private Color ARROW_COLOR = new Color(180,180,200);
	final private Color PPOINT_COLOR = new Color(255,80,80);
	
	boolean gradientMode = false;
	final private float[] gradientFractions = {0.0f, 1.0f};
	final private Color[] gradientColors = {PPOINT_COLOR, Color.green};
	
	boolean validDrag = false;
	
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
		
		simulate();
	}
	
	public void changePPOINT_COUNT(int amount)
	{
		selectedPPOINT = 0;
		
		if (PPOINT_COUNT + amount < 1) {return;}
		PPOINT_COUNT+= amount;
		
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
		
		simulate(); repaint();
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
		int locX = 30;
		int locY = 30;
	}
	
	private void simulate()
	{
		for (ArrowPoint point : arrowPoints)
		{
			float[] distances = new float[PPOINT_COUNT];
			
			for (int i = 0; i < PPOINT_COUNT; i++)
			{
				distances[i] = (float) Math.sqrt(Math.pow((point.locX - pullPoints[i].locX),2) + Math.pow((point.locY - pullPoints[i].locY),2));
			}
			
			float[] strengths = new float[PPOINT_COUNT];
			for (int i = 0; i < PPOINT_COUNT; i++)
			{
				strengths[i] = (float) Math.pow(0.99, distances[i]);
			}
			
			float[] vectorsX = new float[PPOINT_COUNT];
			float[] vectorsY = new float[PPOINT_COUNT];
			
			for (int i = 0; i < PPOINT_COUNT; i++)
			{
				vectorsX[i] = pullPoints[i].locX - point.locX;
				vectorsY[i] = pullPoints[i].locY - point.locY;
			}
			
			float[][] vectors = normalize(vectorsX, vectorsY, PPOINT_COUNT);
			
			float[] vectorX = {0};
			float[] vectorY = {0};
			
			for (int i = 0; i < PPOINT_COUNT; i++)
			{
				vectorX[0]+= vectors[i][0] *strengths[i];
				vectorY[0]+= vectors[i][1] *strengths[i];
			}
			vectorX[0]/= PPOINT_COUNT;
			vectorY[0]/= PPOINT_COUNT;
			
			float[][] goal = normalize(vectorX, vectorY, 1);
			
			point.arrowX = (int) (point.locX + goal[0][0]* ARROW_LENGTH);
			point.arrowY = (int) (point.locY +goal[0][1]* ARROW_LENGTH);
		}
	}
	
	public float[][] normalize(float[] x , float[] y, int iterations)
	{	
		float[][] result = new float[iterations][2];
		
		for (int i = 0; i < iterations; i++)
		{
			float length = (float)Math.sqrt(Math.pow(x[i], 2)+Math.pow(y[i], 2));
			
			result[i][0] = x[i]/length;
			result[i][1] = y[i]/length;
		}
		
		return result;
	}
	
	public void paint(Graphics g)
	{
		Graphics2D g2D = (Graphics2D) g;
		super.paint(g2D);
		
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
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
			if (arrowPoints[i].arrowX != 0) 
			{g2D.drawLine(arrowPoints[i].locX, arrowPoints[i].locY, arrowPoints[i].arrowX, arrowPoints[i].arrowY);}
			
			g2D.drawRect(arrowPoints[i].arrowX-1, arrowPoints[i].arrowY-1, 2, 2);
		}
		
		//PULL POINTS
		if (PPOINT_COUNT != 0)
		{
			g2D.setPaint(PPOINT_COLOR);
			for (int i = 0; i < PPOINT_COUNT; i++)
			{
				g2D.fillOval(pullPoints[i].locX-6, pullPoints[i].locY-6, PPOINT_SIZE, PPOINT_SIZE);
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
