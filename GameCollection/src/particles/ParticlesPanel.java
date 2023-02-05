package particles;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.Random;
import javax.swing.JPanel;
import javax.swing.Timer;


public class ParticlesPanel extends JPanel implements ActionListener
{
	private static final long serialVersionUID = 6395869615896081681L;
	
	private Timer movetimer; 	//timer for particle movement
	private Timer colorTimer;	//timer for color palette change
	
	private Random random = new Random();

	//PARTICLES
	private class Particle
	{
		int X, Y;
		int sizeX, sizeY;
		Color color;
		
		Particle(int X, int Y, int sizeX, int sizeY)
		{this.X = X; this.Y = Y; this.sizeX = sizeX; this.sizeY = sizeY;}
	}
	
	private static final int particleAmount = 6000;
	private Particle[] particles;
	
	private static final Color[] palette = 
	{
			new Color (170,170,245), new Color (245,170,245),
			new Color (170,245,245), new Color (170,170,245)		
	};
	private int palette_pos = 0;
		
	//MOUSE
	private class Mouse
	{
		int X = 0; 	
		int prevX = 0; 	
		int Y = 0;
		int size = 5;	//size of mouse influence
	}
	
	private Mouse mouse;
	
	ParticlesPanel()
	{
		DragListener dragListener = new DragListener(); 
		this.addMouseMotionListener(dragListener);
		
		this.setPreferredSize(new Dimension(650,650));
		this.setLayout(null);
		
		movetimer = new Timer(50, this);
		movetimer.start();
		
		colorTimer = new Timer(5000, this);
		colorTimer.start();
		          
		start();
	}
	
	//CONTROL
	public void stop()
	{movetimer.stop(); colorTimer.stop(); particles = null;}
	
	public void start()
	{
		particles = null;
		System.gc();
		particles =  new Particle[particleAmount];
		
		for (int i = 0; i < particleAmount; i++)
		{
			int X = random.nextInt(640), Y = random.nextInt(640);
			int sizeX = random.nextInt(9)+5, sizeY = random.nextInt(9)+3;
			
			particles[i] = new Particle(X,Y,sizeX,sizeY);
			setColor(particles[i]);
		}
		
		mouse = new Mouse();
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource()==movetimer) 
		{moveParticles();}
		
		else 
		{changePalette();}
	}
	
	//PARTICLES
	private void moveParticles()
	{
		for (Particle p : particles) 
		{	
			switch (random.nextInt(4))
			{
				case 0: p.X+=random.nextInt(2); break;
				case 1: p.X-=random.nextInt(2); break;
				case 2: p.Y+=random.nextInt(2); break;
				case 3: p.Y-=random.nextInt(2); break;
			}
		}
		repaint();
	}
	
	private void pushParticles(int amount)
	{
		for (Particle p : particles)
    	{ 
    		if (p.X > mouse.X-16 && p.X < mouse.X+mouse.size && p.Y > mouse.Y-16 && p.Y < mouse.Y+mouse.size)	
       	  	{	
    			p.X+=amount;
    			p.Y+=amount;	
       	  	}
    	}
    	repaint();  
	}
	
	private void changePalette()
	{
		palette_pos = (palette_pos + 2) % 4;
		for (Particle p : particles)
		{setColor(p);}
	}
	
	private void setColor(Particle p)
	{
		Color color = palette[random.nextInt(2) + palette_pos];
		
		double distFromCenter = Math.abs(p.X-325) + Math.abs(p.Y-325);
		int R = (int) Math.abs(color.getRed()-(distFromCenter / 5));
		int G = (int) Math.abs(color.getGreen()-(distFromCenter / 5));
		int B = (int) Math.abs(color.getBlue()-(distFromCenter / 5));
		p.color= new Color(R,G,B);
	}
	
	public void paint(Graphics g) 
	{	
		Graphics2D g2D = (Graphics2D) g;
	
		//BACKGROUND
		g2D.setPaint(new Color(60,60,95));
		g2D.fillRect(0, 0, 650, 650); 
		
		//PARTICLES
		for (Particle p : particles)
		{		
			g2D.setPaint(p.color);
			g2D.fillRect(p.X, p.Y, p.sizeX, p.sizeY);
		}	
	}
	
	//MOUSE
	private class DragListener extends MouseMotionAdapter
	{
	    public void mouseDragged(MouseEvent e) 
	    { 
	    	mouse.X = e.getX();
	    	mouse.Y = e.getY();
	    	
	    	int amount = 10;
	    	if (mouse.prevX > mouse.X) {amount = -10;}
	    	pushParticles(amount);
	    	
	    	mouse.prevX = mouse.X;  
	    }
	}
}