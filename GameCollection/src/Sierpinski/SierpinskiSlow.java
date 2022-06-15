package Sierpinski;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.Random;
import javax.swing.JPanel;
import javax.swing.Timer;

public class SierpinskiSlow extends JPanel implements ActionListener{
	private static final long serialVersionUID = 9082922097976866954L;
	
	int[] P = new int[2];
	int[] v = new int[2];
	int[] nextP = new int[2];
	int[] prevP = new int [2];
	
	int[][] allP = new int [2][10000];
	int numP = 0;
	
	
	Point ACorner;
	Point BCorner;
	Point CCorner;
	
	Point prevPt;
	int dragwhich;
	
	Random random;
	
	boolean moving = false;
	
	Thread t1;
	
	static Timer timer1;
	static Timer timer2;
	
	SierpinskiSlow(boolean slowMode){
		
		random = new Random();
		
		P[0] = 550;
		P[1] = 550;
		
		prevP[0] = P[0];
		prevP[1] = P[1];
		nextP[0] = P[0];
		nextP[1] = P[1];
		
		ClickListener clickListener = new ClickListener();
		DragListener dragListener = new DragListener();
		ReleaseListener releaseListener = new ReleaseListener();
		this.addMouseListener(releaseListener);
		this.addMouseListener(clickListener);
		this.addMouseMotionListener(dragListener);
		
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
	
	
	public void paint(Graphics g) {
		
		Graphics2D g2D = (Graphics2D) g;
	
		if (moving) {super.paint(g);}
		
		super.paint(g);
		
		g2D.fillRect((int)ACorner.getX(),(int)ACorner.getY(), 5, 5);
		g2D.fillRect((int)BCorner.getX(),(int)BCorner.getY(), 5, 5);
		g2D.fillRect((int)CCorner.getX(),(int)CCorner.getY(), 5, 5);
		
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
	
	//Vektor
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
	
	private class ClickListener extends MouseAdapter{
	    public void mousePressed(MouseEvent e) {  
	    	
	    	prevPt = e.getPoint(); 
	           if ((e.getPoint().getX() > ACorner.getX()) && 
	              (e.getPoint().getX() < (ACorner.getX() + 50)) &&
	              (e.getPoint().getY() > ACorner.getY()) &&
	              (e.getPoint().getY() < (ACorner.getY() + 50))){dragwhich = 0;}
	           else if ((e.getPoint().getX() > BCorner.getX()) && 
	 	              (e.getPoint().getX() < (BCorner.getX() + 50)) &&
		              (e.getPoint().getY() > BCorner.getY()) &&
		              (e.getPoint().getY() < (BCorner.getY() + 50))){dragwhich = 1;}
	           else if ((e.getPoint().getX() > CCorner.getX()) && 
	 	              (e.getPoint().getX() < (CCorner.getX() + 50)) &&
		              (e.getPoint().getY() > CCorner.getY()) &&
		              (e.getPoint().getY() < (CCorner.getY() + 50))){dragwhich = 2;}
	    }
	}
	
	private class DragListener extends MouseMotionAdapter{
	    public void mouseDragged(MouseEvent e) { 
	           	
	    	if (e.isShiftDown()) {	
			
	    	moving = true;
	    	
	    	Point currentPt = e.getPoint(); 

	    	if(dragwhich == 0){	
	        ACorner.translate((int)(currentPt.getX() - prevPt.getX()), (int)(currentPt.getY() - prevPt.getY()));          
	    	}
	    	else if (dragwhich == 1){	
		    BCorner.translate((int)(currentPt.getX() - prevPt.getX()), (int)(currentPt.getY() - prevPt.getY()));          
		    }
	    	else if (dragwhich == 2){	
			CCorner.translate((int)(currentPt.getX() - prevPt.getX()), (int)(currentPt.getY() - prevPt.getY()));          
			}
	                
	                prevPt = currentPt;
	                
	                repaint();
	                
	    			}
	            }
	    }
	
	private class ReleaseListener extends MouseAdapter
	{
	    public void mouseReleased(MouseEvent e) 
	    {  	
	    moving = false;
	    }
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
	
	rollDice();
		
	}
}
 