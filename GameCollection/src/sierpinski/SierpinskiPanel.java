package sierpinski;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.JPanel;
import javax.swing.Timer;

public class SierpinskiPanel extends JPanel implements ActionListener
{
	private static final long serialVersionUID = 9082922097976866954L;
	private Random random = new Random();
	
	private Timer timer;
	private Thread thread;
	
	private static class TimerSpeeds
	{
		final static int slow = 300;
		final static int medium = 50;
	}
	private int speed;
	
	SierpinskiPanel(int speed)
	{	
		this.setPreferredSize(new Dimension(600,600));
		this.setLayout(null);
			
		this.initialize(speed);
	}
	
	public void stop()
	{if (timer != null) {timer.stop();} random = null;}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{rollDice(); repaint();}
	
	//---------------------------------------INITIALIZATION---------------------------------------
	
	private void initialize(int speed)
	{
		this.speed = speed;
		
		switch (speed)
		{
			case 1: initTimer(TimerSpeeds.slow); break;
			case 2: initTimer(TimerSpeeds.medium); break;
			case 3: initThread(); break;
		}
		
		point = new SP_Point(550,550);
	}
	
	private void initTimer(int speed)
	{
		timer = new Timer(speed, this);
		timer.start();
	}
	
	private void initThread() 
	{
		thread = new Thread(new Runnable() 
		{
		    @Override
		    public void run() 
		    {
		    	while (random != null) 
		    	{
		    		rollDice();
		    		if (random.nextInt(20) == 1) //makes it look a little more organic
		    		{try {Thread.sleep(1); repaint();} catch (InterruptedException e) {e.printStackTrace();}}
		    	}
		    }	 		    		
		});
		
		thread.start();
	}
	
	//---------------------------------------CALCULATION---------------------------------------
	
	//POINTS
	private class SP_Point
	{
		int x, y;
		int vector[] = {0,0};

		SP_Point(int x, int y)
		{this.x = x; this.y = y;}
	}
	private SP_Point point;

	private static final int point_count = 1000000;
	private int[][] allPoints = new int[2][point_count];
	private int pointIndex = 0;

	//OUTER TRIANGLE
	private static class SP_Triangle
	{
		final static int[][] corners = {{10,550},{300,10},{590,550}};
	}
	
	private void rollDice() 
	{
		if (point == null || pointIndex >= point_count-1) {return;}
		
		int[] corner = SP_Triangle.corners[random.nextInt(3)];
		point.vector[0] = (corner[0] - point.x) / 2;
		point.vector[1] = (corner[1] - point.y) / 2;
		
		point.x += point.vector[0];
		point.y += point.vector[1];
		
		allPoints[0][pointIndex] = point.x;
		allPoints[1][pointIndex] = point.y;
		pointIndex++;
	}
	
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
		g2D.setPaint(foreground);
		for (int i = 0; i < pointIndex; i++)
		{g2D.fillRect(allPoints[0][i], allPoints[1][i], 1, 1);}
	}
	
	private void paintBasicMode(Graphics2D g2D)
	{
		//VECTOR LINE
		g2D.setStroke(new BasicStroke(3));
		
		g2D.setPaint(Color.cyan);
		g2D.drawLine(point.x - point.vector[0], point.y - point.vector[1], point.x, point.y);
		g2D.setPaint(Color.red);
		g2D.drawLine(point.x, point.y, point.x + point.vector[0], point.y + point.vector[1]);
			
		//OLD POINTS
		for (int i = 0; i < pointIndex; i++) 
		{
			g2D.setPaint(Color.white);
			g2D.fillRect(allPoints[0][i] - 2, allPoints[1][i] - 2, 5, 5);
		}
		
		//NEW POINT
		g2D.fillRect(point.x - 5, point.y - 5, 10, 10);
	}
}