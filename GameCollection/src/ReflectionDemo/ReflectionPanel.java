package ReflectionDemo;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import javax.swing.JPanel;

public class ReflectionPanel extends JPanel implements MouseWheelListener 
{
	private static final long serialVersionUID = 6487656587L;
	
	int panelSize = 700;
	
	int reflectcount = 1; //HOW MANY RAYS WILL BE DRAWN
	
	//ANGLE USED FOR ROTATING THE RAY
	final static double PI = Math.PI;
	final static double DEG90 = PI/2;
	final static double DEG270 = 3*DEG90;
	double rotationSpeed = 0.01;
	
	int blue = 1; //CHANGING THE AMOUNT OF BLUE MIXED INTO THE RAY COLOR
	
	private class Ray
	{
		double[] origin = new double[2];
		double angle;
	}
	
	Ray[] rays = new Ray[50];
	
	ReflectionPanel()
	{
		DragListener dragListener = new DragListener();
		this.addMouseMotionListener(dragListener);
		this.addMouseWheelListener(this);
		          
		this.setPreferredSize(new Dimension(panelSize,panelSize));
		this.setLayout(null);
		
		for (int i = 0; i < rays.length; i++)
		{rays[i] = new Ray();}
		
		rays[0].origin[0] = 350;
		rays[0].origin[1] = 350;
		
		rays[0].angle = 0.01;
		makeRays();
	}
	
	public void changeReflectCount(boolean increase)
	{
		if (increase) {if (reflectcount < 49) {reflectcount++;}}	
		else if (reflectcount > 1) {reflectcount--;}
			
		makeRays();
		repaint();
	}
	
	public void changeRotationSpeed(boolean increase)
	{
		if (increase) {rotationSpeed*=2;}
		else {rotationSpeed/=2;}
	}
	
	public void rayRotation(double change)
	{
		rays[0].angle+=change;
		if (rays[0].angle <0) {rays[0].angle+=2*PI;}
		if (rays[0].angle > 2*PI) {rays[0].angle -=2*PI;}
		makeRays();
	}
	
	public void makeRays()
	{
		for (int i = 1; i < reflectcount+1; i++)
		{rayCalculation(rays[i-1], rays[i]);}
	}
	
	double dist(double ax, double ay, double bx, double by)
	{
		double v1 = bx-ax, v2 = by-ay;
		return ( Math.sqrt(v1*v1 + v2*v2) );
	}
	
	public void rayCalculation(Ray prevRay, Ray ray)
	{	
		double rayAngle = prevRay.angle;
		
		double rayX = 0, rayY = 0;
		double prevX = prevRay.origin[0], prevY = prevRay.origin[1];
		
		//CHECK HORIZONTAL LINES
		double distHor;
		double aTan=-1/Math.tan(rayAngle);
				
		//looking up
		if(rayAngle > PI) 
		{
			rayY = 0;
			rayX = prevX + (prevY-rayY)*aTan; 
			// tan = opposite/adjacent -> Tan a * Adjacent = Opposite -> aTan * Opposite = Adjacent -> aTan * ry = rx 
		} 
				
		//looking down
		if(rayAngle < PI) 
		{
			rayY = panelSize; 
			rayX = prevX + (prevY-rayY)*aTan; 
		}		
		distHor = dist(prevX,prevY,rayX,rayY);
		double horX = rayX, horY = rayY;
		
		
		//CHECK VERTICAL LINES
		double distVer;
		double nTan= -Math.tan(rayAngle);
				
		//looking left
		if(rayAngle > DEG90 && rayAngle < DEG270) 
		{
			rayX = 0;
			rayY = prevY + (prevX-rayX)*nTan;
		} 
				
		//looking right
		if(rayAngle < DEG90 || rayAngle > DEG270)
		{
			rayX = panelSize;
			rayY = prevY + (prevX-rayX)*nTan;
		}	
		distVer = dist(prevX,prevY,rayX,rayY);
		
		
		if (distHor < distVer) //y = 0 or y = panelSize
		{
			rayX = horX; rayY = horY;
			
			ray.angle = 2*PI - rayAngle; //reflect ray along x axis
		}
		else 
		{ray.angle = PI - rayAngle;} //reflect ray along y axis
		
		if (ray.angle < 0) {ray.angle+=2*PI;}
		if (ray.angle > 2*PI) {ray.angle -=2*PI;}
	
		ray.origin[0] = rayX;
		ray.origin[1] = rayY;	
	}
	
	public void paint (Graphics g) 
	{
		Graphics2D g2D = (Graphics2D) g;

		//BACKGROUND
		g2D.setPaint(new Color(50,50,60));
		g2D.fillRect(0, 0, panelSize, panelSize); 
			
		//RAYS
		g2D.setStroke(new BasicStroke(3));
		g2D.setPaint(new Color(100,250,0)); //GREEN
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		for (int i = 0; i < reflectcount; i++)
		{
			blue= i*5;
			g2D.setPaint(new Color(100,250,blue));
			
			g2D.drawLine((int) rays[i].origin[0], (int) rays[i].origin[1], (int) rays[i+1].origin[0], (int) rays[i+1].origin[1]);
		}
		
		//MOVEABLE ORIGIN
		g2D.setPaint(Color.GRAY);
		g2D.fillOval( (int) rays[0].origin[0]-8, (int) rays[0].origin[1]-8, 15, 15);	
	}
	
	public void changeSize(int c) 
	{
		panelSize+=c;
		repaint();
		this.setPreferredSize(new Dimension(panelSize,panelSize));
	}

	public void mouseWheelMoved(MouseWheelEvent e) 
	{
		double change;
		
		if (e.getWheelRotation() > 0) 
	    {change = rotationSpeed;} 
	    else  
	    {change = -rotationSpeed;}
	    
		rayRotation(change);
	    repaint();
	}
	
	private class DragListener extends MouseMotionAdapter
	{
	    public void mouseDragged(MouseEvent e) 
	    { 
	    	rays[0].origin[0] = e.getX();
	    	rays[0].origin[1] = e.getY();
	        makeRays();
	        repaint();
	    }
	}
}