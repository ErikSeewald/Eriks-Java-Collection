package Particles;
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


public class ParticlesPanel extends JPanel implements ActionListener{
	private static final long serialVersionUID = 6395869615896081681L;
		
	int xloc = 0; 		//x location of the mouse
	int prevloc = 0; 	//previous x location of the mouse
	int yloc = 0;		//y location of the mouse
	int size = 20;		//size of mouse influence
	
	
	int length = 6000;	//length of array of squares
	
	int[][] sqloc = new int [2][length]; //location of squares 0-x 1-y
	
	int[] sqsizeX = new int [length];		//x sizes of squares
	int[] sqsizeY = new int [length];		//y sizes of squares
	
	double [] dist = new double [length];	//distance of squares from the center
	
	boolean[] colorbool = new boolean[length];	//which ever color of two the square will have
	
	Color[] colors = new Color[length]; //square colors
	
	static Timer timer; 		//timer for square movement
	static Timer timer2;		//timer for color palette change
	
	Random random;	
	
	//starting color palette
	Color color1 = (new Color (170,170,245)); 
	Color color2 = (new Color (245,170,245));
	
	
	ParticlesPanel(){
		
		random = new Random();
		
		start();
		
		DragListener dragListener = new DragListener(); 
		
		timer = new Timer(50, this);
		timer.start();
		
		timer2 = new Timer(5000, this);
		timer2.start();
		          
		this.addMouseMotionListener(dragListener);
		
		this.setPreferredSize(new Dimension(650,650));
		this.setLayout(null);
		
	}
	
	public static void stop()
	{timer.stop(); timer2.stop();}
	
	public void start()
	{
		for (int i = 0; i < length-1; i++)
		{
			sqloc[0][i] = random.nextInt(640);
			sqloc[1][i] = random.nextInt(640);
			
			sqsizeX[i] = random.nextInt(9)+5;
			sqsizeY[i] = random.nextInt(9)+3;
			
			dist[i] = Math.abs(sqloc[0][i]-325) + Math.abs(sqloc[1][i]-325); //distance from center
			
			colorbool[i] = random.nextBoolean();
			
			if (colorbool[i])	//switch between the two colors, then caculates brightness by distance
			{colors[i] = (new Color ((int) (Math.abs(color1.getRed()-(dist[i] / 5))),(int) (Math.abs(color1.getGreen()-(dist[i] / 5))),(int) (Math.abs(color1.getBlue()-(dist[i] / 5)))));}
			else 
			{colors[i] = (new Color ((int) (Math.abs(color2.getRed()-(dist[i] / 5))),(int) (Math.abs(color2.getGreen()-(dist[i] / 5))),(int) (Math.abs(color2.getBlue()-(dist[i] / 5)))));}	
			
		}
	}
	
	public void paint(Graphics g) {
		
		Graphics2D g2D = (Graphics2D) g;
	
		g2D.setPaint(new Color(60,60,95));
		g2D.fillRect(0, 0, 650, 650); 		//background
		
		for (int i = 0; i < length-1; i++) //all squares
		{		
			g2D.setPaint(colors[i]);
			g2D.fillRect(sqloc[0][i], sqloc[1][i], sqsizeX[i], sqsizeY[i]); 
		}	
	}
	
	private class DragListener extends MouseMotionAdapter
	{
	    public void mouseDragged(MouseEvent e) 
	    { 
	      xloc = e.getX();
	      yloc = e.getY();
	               
	      for (int i = 0; i < length-1; i++) //pushes squares to the side if they are inside mouse obj
	      { 
	    	  if (sqloc[0][i] > xloc-16 && sqloc[0][i] < xloc+size-12 && sqloc[1][i] > yloc-16 && sqloc[1][i] < yloc+size-12)	
	       	  {	
	    		  if (prevloc < xloc) {sqloc[0][i]+=10; sqloc[1][i]+=10;}
	       		  else {sqloc[0][i]-=10; sqloc[1][i]-=10;}	
	       	  }
	      }
	      prevloc = xloc;
	      repaint();    
	    }
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
	
		if (e.getSource()==timer) 
		{
		
			for (int i = 0; i < length-1; i++) 
			{	
					switch (random.nextInt(4)) //moves squares randomly
					{
						case 0: sqloc[0][i]+=random.nextInt(2);
						break;
						case 1: sqloc[0][i]-=random.nextInt(2);
						break;
						case 2: sqloc[1][i]+=random.nextInt(2);
						break;
						case 3: sqloc[1][i]-=random.nextInt(2);
						break;
					}
			}
			repaint();
		}
		
		else 
		{
		
			switch (random.nextInt(2)) //changes colors
			{	
				case 0: color1 = (new Color (170,245,245)); color2 = (new Color (170,170,245));
				break;
				case 1: color1 = (new Color (170,170,245)); color2 = (new Color (245,170,245));
				break;
			}
		
			for (int i = 0; i < length-1; i++) 
			{
				if (colorbool[i]) 
				{colors[i] = (new Color ((int) (Math.abs(color1.getRed()-(dist[i] / 5))),(int) (Math.abs(color1.getGreen()-(dist[i] / 5))),(int) (Math.abs(color1.getBlue()-(dist[i] / 5)))));}
			
				else 
				{colors[i] = (new Color ((int) (Math.abs(color2.getRed()-(dist[i] / 5))),(int) (Math.abs(color2.getGreen()-(dist[i] / 5))),(int) (Math.abs(color2.getBlue()-(dist[i] / 5)))));}	
			}
		}
	
	}
}
