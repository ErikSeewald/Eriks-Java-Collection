package GravityVectors;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class GravityVectorsPanel extends JPanel
{
	private static final long serialVersionUID = 3933829351442493815L;
	
	int PANEL_WIDTH = 900;
	int PANEL_HEIGHT = (int) (PANEL_WIDTH*(0.66));
	
	final int POINT_SIZE = 6;
	final int POINT_DISTANCE = POINT_SIZE * 5;
	int POINT_COUNT_X = 30;
	int POINT_COUNT_Y = 20;
	VectorPoint[] points;
	
	final private Color BACKGROUND_COLOR = new Color(50,50,70);
	final private Color ARROW_COLOR = new Color(230,255,230);
	
	GravityVectorsPanel()
	{
		this.setPreferredSize(new Dimension(PANEL_WIDTH,PANEL_HEIGHT));
		
		start();
	}
	
	public void changeSize(int amount)
	{
		PANEL_WIDTH += amount;
		PANEL_HEIGHT = (int) (PANEL_WIDTH*(0.66));
		this.setPreferredSize(new Dimension(PANEL_WIDTH,PANEL_HEIGHT));
		
		POINT_COUNT_X++;
		POINT_COUNT_Y++;
		
		start();
		repaint();
	}
	
	public void start()
	{
		points = null;
		System.gc();
		points = new VectorPoint[POINT_COUNT_X * POINT_COUNT_Y];
		
		int index = 0;
		
		for (int i = 0; i < POINT_COUNT_X; i++)
		{
			for (int j = 0; j < POINT_COUNT_Y; j++)
			{
				points[index] = (new VectorPoint(i*30+(POINT_DISTANCE / 3), j*30+(POINT_DISTANCE / 3)));
				index++;
			}
		}
	}
	
	private class VectorPoint
	{
		VectorPoint(int locX, int locY)
		{this.locX = locX; this.locY = locY; arrowX = locX + 15;  arrowY = locY;}
		
		int locX;
		int locY;
		
		int arrowX;
		int arrowY;
	}
	
	private class GravityPoint
	{
		int locX;
		int locY;
	}
	
	public void simulate()
	{
		
	}
	
	public void paint(Graphics g)
	{
		Graphics2D g2D = (Graphics2D) g;
		super.paint(g2D);
		
		g2D.setPaint(BACKGROUND_COLOR);
		g2D.fillRect(0, 0, PANEL_WIDTH, PANEL_HEIGHT);
		
		g2D.setPaint(ARROW_COLOR);
		for (int i = 0; i < points.length; i++)
		{
			g2D.drawLine(points[i].locX, points[i].locY, points[i].arrowX, points[i].arrowY);
			g2D.drawRect(points[i].locX-1, points[i].locY-1, 2, 2);
		}
	}
}
