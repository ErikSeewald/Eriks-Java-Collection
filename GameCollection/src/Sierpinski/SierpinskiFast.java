package Sierpinski;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.Random;
import javax.swing.JPanel;

public class SierpinskiFast extends JPanel
{
	private static final long serialVersionUID = 9082922097976866954L;
	
	private int[] P = new int[2];
	private int[] v = new int[2];
	private int[] nextP = new int[2];
	
	private int[][] points = new int[2][1000000];
	private int pointIndex = 0;
	
	private Point ACorner;
	private Point BCorner;
	private Point CCorner;
	
	private Random random;
	
	private Thread t1;
	
	SierpinskiFast()
	{
		
		random = new Random();
		
		this.setPreferredSize(new Dimension(600,600));
		this.setLayout(null);
		
		ACorner = new Point(10,550);
		BCorner = new Point(300,10);
		CCorner = new Point(590,550);
			
		P[0] = 550;
		P[1] = 550;
		
		t1 = new Thread(new Runnable() 
		{
		    @Override
		    public void run() 
		    {
		    	while (true) 
		    	{
		    		rollDice();
		    		if (random.nextInt(20) == 1) 
		    		{
		    			try {Thread.sleep(1);} catch (InterruptedException e) {e.printStackTrace();}
		    		}
		    	}
		    }	 		    		
		});
		
		t1.start();
	}
	
	public void paint(Graphics g) 
	{
		Graphics2D g2D = (Graphics2D) g;
		
		super.paint(g2D);
		
		g2D.fillRect((int)ACorner.getX()-2,(int)ACorner.getY()-2, 5, 5);
		g2D.fillRect((int)BCorner.getX()-2,(int)BCorner.getY()-2, 5, 5);
		g2D.fillRect((int)CCorner.getX()-2,(int)CCorner.getY()-2, 5, 5);
		
		g2D.fillRect(P[0], P[1], 3, 3);
		
		for (int i = 0; i < pointIndex; i++)
		{
			g2D.fillRect(points[0][i], points[1][i], 1, 1);
		}
	}
	
	public void rollDice() 
	{
		if (points == null) {return;}
		
		switch (random.nextInt(3)) 
		{
			case 0: nextP[0] = (int)ACorner.getX(); nextP[1] = (int)ACorner.getY();
			break;
			case 1: nextP[0] = (int)BCorner.getX(); nextP[1] = (int)BCorner.getY();
			break;
			case 2: nextP[0] = (int)CCorner.getX(); nextP[1] = (int)CCorner.getY();
			break;
		}	
	
		//Vector
		v[0] = (int)(nextP[0] - P[0])/2;
		v[1] = (int)(nextP[1] - P[1])/2;
	
		//New P Position
		P[0]+= v[0];
		P[1]+= v[1];
	
		points[0][pointIndex] = P[0];
		points[1][pointIndex] = P[1];
	
		pointIndex++;

		repaint();	
	}
}
 