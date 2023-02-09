package sierpinski;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

import sierpinski.SierpinskiAlgorithm.SP_Point;

public class SierpinskiPanel extends JPanel
{
	private static final long serialVersionUID = 9082922097976866954L;
	
	private SierpinskiAlgorithm algorithm;
	private int speed;
	
	SierpinskiPanel(int speed)
	{	
		this.setPreferredSize(new Dimension(600,600));
		this.setLayout(null);
			
		this.speed = speed;
		algorithm = new SierpinskiAlgorithm(speed, this);
		algorithm.start();
	}
	
	public void stop()
	{algorithm.stop();}
	
	//---------------------------------------PAINT---------------------------------------
	
	private static final Color background = new Color(40,40,50);
	private static final Color foreground = new Color(100,255,100);
	
	public void paint(Graphics g) 
	{
		Graphics2D g2D = (Graphics2D) g;
		
		//BACKGROUND
		g2D.setPaint(background);
		g2D.fillRect(0, 0, 600, 600);
		
		//SIERPINSKI
		if (speed > 2) {paintFastMode(g2D);}
		else {paintBasicMode(g2D);}
	}
	
	private void paintFastMode(Graphics2D g2D)
	{	
		int[][] points = algorithm.allPoints;
		
		g2D.setPaint(foreground);
		for (int i = 0; i < algorithm.pointIndex; i++)
		{g2D.fillRect(points[0][i], points[1][i], 1, 1);}
	}
	
	private void paintBasicMode(Graphics2D g2D)
	{
		//VECTOR LINE
		g2D.setStroke(new BasicStroke(3));
		
		SP_Point point = algorithm.getPointCopy();
		g2D.setPaint(Color.cyan);
		g2D.drawLine(point.x - point.vector[0], point.y - point.vector[1], point.x, point.y);
		g2D.setPaint(Color.red);
		g2D.drawLine(point.x, point.y, point.x + point.vector[0], point.y + point.vector[1]);
			
		//OLD POINTS
		int[][] points = algorithm.allPoints;
		for (int i = 0; i < algorithm.pointIndex; i++) 
		{
			g2D.setPaint(Color.white);
			g2D.fillRect(points[0][i] - 2, points[1][i] - 2, 5, 5);
		}
		
		//NEW POINT
		g2D.fillRect(point.x - 5, point.y - 5, 10, 10);
	}
}