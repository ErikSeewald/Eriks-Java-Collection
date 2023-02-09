package sierpinski;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.Timer;

public class SierpinskiAlgorithm implements ActionListener
{
	private SierpinskiPanel panel;
	private Random random = new Random();
	private Timer timer;
	private Thread thread;
	
	private static class TimerSpeeds
	{
		final static int slow = 300;
		final static int medium = 50;
	}
	private int speed;
	
	SierpinskiAlgorithm(int speed, SierpinskiPanel panel)
	{
		this.speed = speed;
		this.panel = panel;
		this.initialize();
	}
	
	private void initialize()
	{	
		switch (speed)
		{
			case 1: initTimer(TimerSpeeds.slow); break;
			case 2: initTimer(TimerSpeeds.medium); break;
			case 3: initThread(); break;
		}
		
		point = new SP_Point(550,550, new int[] {0,0});
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
		    		{try {Thread.sleep(1); panel.repaint();} catch (InterruptedException e) {e.printStackTrace();}}
		    	}
		    }	 		    		
		});
		
		thread.start();
	}
	
	//---------------------------------------CALCULATION---------------------------------------
	
	//POINTS
	class SP_Point
	{
		int x, y;
		int[] vector;

		SP_Point(int x, int y, int[] vector)
		{this.x = x; this.y = y; this.vector = vector;}
	}
	private SP_Point point;

	static final int max_points = 1000000;
	int[][] allPoints = new int[2][max_points];
	int pointIndex = 0;

	private static class OuterTriangle
	{final static int[][] corners = {{10,550},{300,10},{590,550}};}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{rollDice(); panel.repaint();}
	
	private void rollDice() 
	{
		if (point == null || pointIndex >= max_points-1) {return;}
		
		int[] corner = OuterTriangle.corners[random.nextInt(3)];
		point.vector[0] = (corner[0] - point.x) / 2;
		point.vector[1] = (corner[1] - point.y) / 2;
		
		point.x += point.vector[0];
		point.y += point.vector[1];
		
		allPoints[0][pointIndex] = point.x;
		allPoints[1][pointIndex] = point.y;
		pointIndex++;
	}
	
	public SP_Point getPointCopy() {return new SP_Point(point.x, point.y, point.vector);}
	
	public void stop()
	{if (timer != null) {timer.stop();} random = null; timer = null; allPoints = null;}
}