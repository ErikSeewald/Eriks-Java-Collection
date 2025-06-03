package reflectionDemo;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import javax.swing.JPanel;

public class ReflectionPanel extends JPanel implements MouseWheelListener 
{
	private static final long serialVersionUID = 6487656587L;
	private int PANEL_SIZE = 700;
	
	private int reflect_count = 1; //HOW MANY RAYS WILL BE DRAWN
	private double rotation_speed = 0.01;
	private Ray[] rays = new Ray[50];
	
	ReflectionPanel()
	{
		this.setPreferredSize(new Dimension(PANEL_SIZE,PANEL_SIZE));
		this.setLayout(null);
		
		this.addMouseMotionListener(new DragListener());
		this.addMouseWheelListener(this);
		
		for (int i = 0; i < rays.length; i++)
		{rays[i] = new Ray(350,350,0.01);}
		buildRays();
	}
	
	public void changeSize(int c) 
	{
		PANEL_SIZE+=c;
		this.setPreferredSize(new Dimension(PANEL_SIZE,PANEL_SIZE));
		repaint();
	}
	
	public void changeReflectCount(boolean increase)
	{
		if (increase && reflect_count < 49) {reflect_count++;}	
		else if (!increase && reflect_count > 1) {reflect_count--;}	
		buildRays();
	}
	
	public void changeRotationSpeed(boolean increase)
	{rotation_speed = increase ? rotation_speed*2 : rotation_speed / 2;}

	public void mouseWheelMoved(MouseWheelEvent e) 
	{
		double angle = (e.getWheelRotation() > 0) ? rotation_speed : -rotation_speed;
		rays[0].rotate(angle);
		buildRays();
	}
	
	private class DragListener extends MouseMotionAdapter
	{
	    public void mouseDragged(MouseEvent e) 
	    { 
	    	rays[0].origin[0] = e.getX();
	    	rays[0].origin[1] = e.getY();
	        buildRays();
	    }
	}
	
	private void buildRays()
	{
		for (int i = 1; i < reflect_count+1; i++)
		{ReflectionAlgorithm.rayCalculation(rays[i-1], rays[i], PANEL_SIZE);}
	    repaint();
	}
	
	//---------------------------------------PAINT---------------------------------------
	
	public void paint (Graphics g) 
	{
		Toolkit.getDefaultToolkit().sync(); // Force flush (for X11)
		Graphics2D g2D = (Graphics2D) g;

		//BACKGROUND
		g2D.setPaint(new Color(50,50,60));
		g2D.fillRect(0, 0, PANEL_SIZE, PANEL_SIZE); 
			
		//RAYS
		g2D.setStroke(new BasicStroke(3));
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		int blue;
		for (int i = 0; i < reflect_count; i++)
		{
			blue= i*5;
			g2D.setPaint(new Color(100,250,blue));
			g2D.drawLine((int) rays[i].origin[0], (int) rays[i].origin[1], (int) rays[i+1].origin[0], (int) rays[i+1].origin[1]);
		}
		
		//MOVEABLE ORIGIN
		g2D.setPaint(Color.GRAY);
		g2D.fillOval( (int) rays[0].origin[0]-8, (int) rays[0].origin[1]-8, 15, 15);	
	}
}