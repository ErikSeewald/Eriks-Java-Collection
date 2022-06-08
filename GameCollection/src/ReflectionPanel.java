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
	
	//YES I KNOW THERE ARE MORE EFFICIENT WAYS TO DO THIS, BUT I WANTED TO DO THIS WITHOUT LOOKING ANYTHING UP
	
	int panelSize = 600;
	
	int[][] rayOrigin = new int[2][50];	 
	double[][] rayVector = new double[2][50];
	int reflectcount = 3; //HOW MANY RAYS WILL BE DRAWN
	
	//ANGLE USED FOR ROTATING THE RAY
	double angle = 0;	
	double rotationSpeed = 0.01;
	
	int accuracy = 1000; //SEE GETHITFACTOR(); FOR EXPLANATION 
	
	int blue = 1; //CHANGING THE AMOUNT OF BLUE MIXED INTO THE RAY COLOR
	
	ReflectionPanel()
	{
		DragListener dragListener = new DragListener();
		this.addMouseMotionListener(dragListener);
		this.addMouseWheelListener(this);
		          
		this.setPreferredSize(new Dimension(panelSize,panelSize));
		this.setLayout(null);
		
		rayOrigin[0][0] = 300;
		rayOrigin[1][0] = 300;
		
		rayVector[0][0] = panelSize*10;
		rayVector[1][0] = 0;
		
		makeRays();
	}
	
	public void changeReflectCount(boolean increase)
	{
		if (increase) {if (reflectcount < 51) {reflectcount++;}}	
		else {reflectcount--;}
		
		if (reflectcount < 1) {reflectcount = 1;}
			
		makeRays();
		repaint();
	}
	
	public void changeRotationSpeed(boolean increase)
	{
		if (increase) {rotationSpeed*=2;}
		else {rotationSpeed/=2;}
	}
	
	public void rayRotation()
	{
		rayVector[0][0] = rayVector[0][0]*Math.cos(angle)-rayVector[1][0]*Math.sin(angle);
		rayVector[1][0] = rayVector[0][0]*Math.sin(angle)+rayVector[1][0]*Math.cos(angle);
		makeRays();
	}
	
	public void makeRays()
	{
		for (int i = 1; i < reflectcount-1; i++)
		{rayCalculation(i);}
	}
	
	public void rayCalculation(int raynum)
	{
		rayVector[0][raynum] = rayVector[0][raynum-1];
		rayVector[1][raynum] = rayVector[1][raynum-1];
		
		double i = 0; //horizontal
		double j = 0; //vertical
		
		//IF VECTOR[0] IS POSITIVE, YOU CHECK THE FOR RIGHT, ELSE FOR LEFT
		//IF VECTOR[1] IS POSITIVE, YOU CHECK FOR  TOP, ELSE FOR BOTTOM
		// -> YOU WILL END UP WITH TWO EDGES THAT WERE HIT, ONE VERTICAL AND ONE HORIZONAL
		//THEN YOU SIMPLY CHECK WHICH ONE IS CLOSER TO THE ORIGIN (IT'S VECTORS DIVIDER IS BIGGER) AND USE
		//THAT ONE TO GET YOUR NEW ORIGIN. THEN YOU SIMPLY USE THE INVERSE OF THE LAST VECTOR FOR THE
		//CURRENT VECTOR AND YOU HAVE YOUR REFLECTION
		
		if (rayVector[0][raynum] > 0) {i = getHitFactor(raynum-1, rayOrigin[0][raynum-1], panelSize, 0);}
		else {i = getHitFactor(raynum-1, rayOrigin[0][raynum-1], 0, 0);}
		
		if (rayVector[1][raynum] < 0) {j = getHitFactor(raynum-1, rayOrigin[1][raynum-1], 0, 1);}
		else {j = getHitFactor(raynum-1, rayOrigin[1][raynum-1], panelSize, 1);}
		
		double divider;
		if (i > j) 	{divider = i;}
		else 		{divider = j;}
		
		rayOrigin[0][raynum] = (int)(rayOrigin[0][raynum-1] + (rayVector[0][raynum] / divider));
		rayOrigin[1][raynum] = (int)(rayOrigin[1][raynum-1] + (rayVector[1][raynum] / divider));
		
		if (i > j)
		{rayVector[0][raynum] = -rayVector[0][raynum];}
		else
		{rayVector[1][raynum] = -rayVector[1][raynum];}
	}
		
	public double getHitFactor(int rayNum, int origin, int check, int vector0or1)
	{
		double i = 2.0;
		int test;
		
		//DIVIDE THE VECTOR WITH INCREASINGLY HIGH NUMBERS UNTIL YOU GET CLOSE ENOUGH TO HAVING FOUND THE 
		//INTERSECTION WITH POINT "CHECK" WHERRE THE CONVERSION TO INT MAKES THE DIFFERENCE BETWEEN THE TWO 0
		//DEFAULTS TO 0 IF THE ACCURACY IS NOT HIGH ENOUGH -> LEAVES VECTOR AT IT'S ORIGINAL LENGTH
		while (i < accuracy)
		{
			i+=0.01;
			test = (int)(origin + (rayVector[vector0or1][rayNum] / i));
					
			if (test == check) {return i;}	
		}
		i = 0; return i;
	}
	
	public void paint (Graphics g) 
	{
		Graphics2D g2D = (Graphics2D) g;
		super.paint(g2D);
		
		//BACKGROUND
		g2D.fillRect(0, 0, panelSize, panelSize); 
			
		//RAYS
		g2D.setStroke(new BasicStroke(3));
		g2D.setPaint(new Color(100,250,0)); //GREEN
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		for (int i = 0; i < reflectcount-2; i++)
		{
			blue= i*5;
			g2D.setPaint(new Color(100,250,blue));
			
			g2D.drawLine(rayOrigin[0][i], rayOrigin[1][i], rayOrigin[0][i+1], rayOrigin[1][i+1]);
		}
		
		//MOVEABLE ORIGIN
		g2D.setPaint(Color.GRAY);
		g2D.fillOval(rayOrigin[0][0]-8, rayOrigin[1][0]-8, 15, 15);	
	}
	
	public void changeSize(int c) 
	{
		panelSize+=c;
		repaint();
		this.setPreferredSize(new Dimension(panelSize,panelSize));
	}

	public void mouseWheelMoved(MouseWheelEvent e) 
	{
		if (e.getWheelRotation() > 0) 
	    {angle = rotationSpeed;} 
	    else  
	    {angle = -rotationSpeed;}
	    
		rayRotation();
	    repaint();
	}
	
	private class DragListener extends MouseMotionAdapter
	{
	    public void mouseDragged(MouseEvent e) 
	    { 
	    	rayOrigin[0][0] = e.getX();
	        rayOrigin[1][0] = e.getY();
	        makeRays();
	        repaint();
	    }
	}
}