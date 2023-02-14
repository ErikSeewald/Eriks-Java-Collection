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
	public static final int PANEL_SIZE = 650;
	
	private Random random = new Random();
	
	ParticlesPanel()
	{
		this.addMouseMotionListener(new DragListener());		
		this.setPreferredSize(new Dimension(PANEL_SIZE,PANEL_SIZE));
		this.setLayout(null);
         
		start();
	}
	
	public void stop()
	{movetimer.stop(); repaintTimer.stop(); movetimer = null; repaintTimer = null; particles = null;}
	
	public void start()
	{
		initParticles();
		movetimer.start();
		repaintTimer.start();
	}
	
	//---------------------------------------MOUSE---------------------------------------
	
	private class Mouse
	{
		int X = 0, Y = 0; 	
		int prevX = 0;
		int size = 5;	//size of mouse influence
	}
	private Mouse mouse = new Mouse();
	
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
	
	//---------------------------------------PARTICLES---------------------------------------

	private static final int particleAmount = 6000;
	private Particle[] particles;
	
	private Timer movetimer = new Timer(50, this);		//timer for particle movement
	private Timer repaintTimer = new Timer(14, this); 
	private int timeSincePaletteChange = 0;
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource()==movetimer) 
		{
			moveParticles();
			
			if (timeSincePaletteChange > 100) 
			{
				changePalette();
				timeSincePaletteChange = 0;
			}
			timeSincePaletteChange++;
		}	
    	repaint();  
	}
	
	private void initParticles()
	{
		particles = null; System.gc();
		particles =  new Particle[particleAmount];
		
		for (int i = 0; i < particleAmount; i++)
		{
			int X = random.nextInt(PANEL_SIZE-10), Y = random.nextInt(PANEL_SIZE-10);
			int sizeX = random.nextInt(9)+5, sizeY = random.nextInt(9)+3;
			
			particles[i] = new Particle(X, Y, sizeX, sizeY);
			particles[i].setColor(palette[random.nextInt(2) + palette_pos]);
		}
	}
	
	private void pushParticles(int amount)
	{
		for (Particle p : particles)
		{p.push(mouse.X, mouse.Y, mouse.size, amount);}
	}

	private void moveParticles()
	{
		for (Particle p : particles)
		{p.move(random);}
	}
	
	//---------------------------------------PAINT---------------------------------------
	
	private static final Color[] palette = 
	{
			new Color (170,170,245), new Color (245,170,245),
			new Color (170,245,245), new Color (170,170,245)		
	};
	private int palette_pos = 0;
	
	private void changePalette()
	{
		palette_pos = (palette_pos + 2) % 4;
		for (Particle p : particles)
		{p.setColor(palette[random.nextInt(2) + palette_pos]);}
	}
	
	public void paint(Graphics g) 
	{	
		Graphics2D g2D = (Graphics2D) g;
	
		//BACKGROUND
		g2D.setPaint(new Color(60,60,95));
		g2D.fillRect(0, 0, PANEL_SIZE, PANEL_SIZE); 
		
		//PARTICLES
		for (Particle p : particles)
		{		
			g2D.setPaint(p.color);
			g2D.fillRect(p.X, p.Y, p.sizeX, p.sizeY);
		}	
	}
}