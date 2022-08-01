package Sierpinski;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.Random;
import javax.swing.JPanel;

public class SierpinskiFast extends JPanel{
	private static final long serialVersionUID = 9082922097976866954L;
	
	int[] P = new int[2];
	int[] v = new int[2];
	int[] nextP = new int[2];
	
	int[][] points = new int[2][1000000];
	int pointIndex = 0;
	
	Point ACorner;
	Point BCorner;
	Point CCorner;
	
	Point prevPt;
	int dragwhich;
	
	Random random;
	
	boolean moving = false;
	
	Thread t1;
	
	SierpinskiFast(){
		
		random = new Random();
		
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
	
	
	public void paint(Graphics g) {
		
		Graphics2D g2D = (Graphics2D) g;
		
		super.paint(g2D);
		
		g2D.fillRect((int)ACorner.getX(),(int)ACorner.getY(), 5, 5);
		g2D.fillRect((int)BCorner.getX(),(int)BCorner.getY(), 5, 5);
		g2D.fillRect((int)CCorner.getX(),(int)CCorner.getY(), 5, 5);
		
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
	
	//Vektor
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
			
	    	points = null;
	    	System.gc();
	    	points = new int[2][1000000];
	    		
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
	    {moving = false;}
	}
}
 