package Sierpinski;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.JPanel;
import javax.swing.Timer;

public class SierpinskiSlow extends JPanel implements ActionListener
{
	private static final long serialVersionUID = 9082922097976866954L;
	
	private int[] P = new int[2];
	private int[] v = new int[2];
	private int[] nextP = new int[2];
	private int[] prevP = new int [2];
	
	private int[][] allP = new int [2][10000];
	private int numP = 0;
	
	private Point ACorner;
	private Point BCorner;
	private Point CCorner;
	
	private Random random;
	
	static Timer timer1;
	static Timer timer2;
	
	SierpinskiSlow(boolean slowMode)
	{
		random = new Random();
		
		P[0] = 550;
		P[1] = 550;
		
		prevP[0] = P[0];
		prevP[1] = P[1];
		nextP[0] = P[0];
		nextP[1] = P[1];
		
		this.setPreferredSize(new Dimension(600,600));
		this.setLayout(null);
		
		ACorner = new Point(10,550);
		BCorner = new Point(300,10);
		CCorner = new Point(590,550);
			
		if (slowMode)
		{
			timer1 = new Timer(300, this);
			timer1.start();
		}
		
		else
		{
			timer2 = new Timer(50, this);
			timer2.start();
		}
	}
	
	public static void stop()
	{if (timer1 != null) {timer1.stop();} if (timer2 != null) {timer2.stop();}}
	
	public void paint(Graphics g) 
	{
		Graphics2D g2D = (Graphics2D) g;
		
		super.paint(g);
		
		g2D.fillRect((int)ACorner.getX()-2,(int)ACorner.getY()-2, 5, 5);
		g2D.fillRect((int)BCorner.getX()-2,(int)BCorner.getY()-2, 5, 5);
		g2D.fillRect((int)CCorner.getX()-2,(int)CCorner.getY()-2, 5, 5);
		
		g2D.setStroke(new BasicStroke(3));
		
		g2D.setPaint(Color.cyan);
		g2D.drawLine(prevP[0], prevP[1], P[0], P[1]);
		g2D.setPaint(Color.red);
		g2D.drawLine(P[0], P[1], nextP[0], nextP[1]);
			
		for (int i = 0; i < numP; i++) 
		{
			g2D.setPaint(Color.DARK_GRAY);
			g2D.fillRect(allP[0][i]-2, allP[1][i]-2, 5, 5); 
			g2D.setPaint(Color.GRAY);
			g2D.fillRect(allP[0][i+1]-2, allP[1][i+1]-2, 5, 5);
		}
	}
	
	public void rollDice() 
	{
	
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
	
		prevP[0] = P[0];
		prevP[1] = P[1];
	
		//New P Position
		P[0] += v[0];
		P[1] += v[1];
	
		numP++;
		allP[0][numP]=P[0];
		allP[1][numP]=P[1];
	
		repaint();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{rollDice();}
}
 