import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

public class ParallelUniversesPanel extends JPanel
{
	private static final long serialVersionUID = 9082922097976866954L;
	
	int PANEL_SIZE = 623;
	
	int[] location = {313,313};			//LOCATION ON THE ENTIRE
	int[] relativeLocation = {6,6};		//LOCATION WITHIN THE MAIN UNIVERSE
	

	int borderSize = 2; //BORDER BETWEEN UNIVERSES
	
	// 64 --> BITS FOR THE RELATIVELOCATION (WHEN SHOULD AN OVERFLOW OCCUR)
	//--> LOWER BOUND = -32 , UPPER BOUND = 31
	//OFFSET +3 BECAUSE OF PLAYERSIZE - 1/2 BORDERSIZE = 3 (SEE MOVEMENT CALCULATION FOR FURTHER EXPLANATION)
	int upperEdge = 34;
	int lowerEdge = -29;
		
	int playerSize = 4;
	int halfPlayerSize = 2;
	
	int constantAdd = 307;		//CONSTANT THAT NEEDS TO BE ADDED TO THE RELATIVELOCATION TO MAKE IT ABSOLUTE FOR DRAW()

	int universesPerRow = 9;
	
	//OFFSETS NEEDED FOR THE PARALLELUNIVERSES TO LINE UP WITH THE MAIN UNIVERSE PROPERLY
	int outlineOffset = 1;			
	int rect1Offset = outlineOffset + 15;
	int rect2Offset = outlineOffset + 35;
	
	int rect1Size = 25;
	int rect2Size = 15;
	
	int universeSize = 69;
	
	boolean universesVisible = true;
	
	ParallelUniversesPanel()
	{this.setPreferredSize(new Dimension (PANEL_SIZE,PANEL_SIZE));}
	
	public void paint (Graphics g)
	{
		Graphics2D g2D = (Graphics2D) g;
		super.paint(g);
		
		//BACKGROUND
		g2D.setPaint(new Color (80,80,80)); //DARK GRAY
		g2D.fillRect(0, 0, PANEL_SIZE, PANEL_SIZE);
	
		//SETTINGS FOR THE UNIVERSES
		g2D.setPaint(new Color (180,180,180)); //GRAY
		g2D.setStroke(new BasicStroke(borderSize));
		
		if (universesVisible)
		{
			for (int i = 0; i < universesPerRow; i++)
			{
				for (int j = 0; j < universesPerRow; j++)
				{
					if (!(i == 4 && j == 4))
					{
						g2D.drawRect(outlineOffset+(j*(universeSize)), outlineOffset+(i*(universeSize)), universeSize, universeSize);
						g2D.drawRect(rect1Offset+(j*(universeSize)), rect1Offset+(i*(universeSize)), rect1Size, rect1Size);
						g2D.drawRect(rect2Offset+(j*(universeSize)), rect2Offset+(i*(universeSize)), rect2Size, rect2Size);
					}
				}
			}
		}
		
		//ORIGINAL UNIVERSE
		g2D.setPaint(new Color (50,170,250)); //BLUE-ISH
		g2D.drawRect(rect1Offset+(4*(universeSize)), rect1Offset+(4*(universeSize)), rect1Size, rect1Size);
		g2D.drawRect(rect2Offset+(4*(universeSize)), rect2Offset+(4*(universeSize)), rect2Size, rect2Size);
		g2D.drawRect(outlineOffset+(4*(universeSize)), outlineOffset+(4*(universeSize)), universeSize, universeSize);
		
		
		//DRAW PLAYER LOCATION
		g2D.setPaint(new Color (50,250,140)); //GREEN-ISH
		g2D.fillRect(location[0], location[1], playerSize, playerSize);
		
		//DRAW RELATIVE PLAYER LOCATION
		g2D.setPaint(new Color (255,80,80)); //RED
		g2D.fillRect(relativeLocation[0]+constantAdd, relativeLocation[1]+constantAdd, playerSize, playerSize);
		
	}
	
	public void hideUniverses()
	{
		universesVisible = !universesVisible;
		repaint();
	}
	
	public void move(int x, int y)
	{
		location[0]+=x;
    	location[1]+=y;
    	
    	//SIMULATE THE INTEGER OVERFLOWS
    	relativeLocation[0]+= x;
		
		if (relativeLocation[0] > upperEdge) 
    	{
    		relativeLocation[0] = lowerEdge; 
    		location[0]+=playerSize + 1;
    		//TO GO THROUGH THE BORDER TO THE RIGHT, FIRST PUT THE COORDINATE (LEFT CORNER) TO WHERE THE 
    		//RIGHT CORNER WAS WITH +PLAYERSIZE, THEN, SINCE LOCATION HAS ALREADY BEEN INCREMENTED BY 1  
    		//AND BORDERSIZE IS 2, INCREASE ONLY BY 1 MORE (1/2 BORDERSIZE) TO GO  THROUGH THE WALL
    	}
		
    	else if (relativeLocation[0] < lowerEdge) 
    	{
    		relativeLocation[0] = upperEdge;
    		location[0]-=playerSize + 1;
    		//TO GO THROUGH THE BORDER TO THE LEFT, FIRST PUT THE RIGHT CORNER TO WHERE THE 
    		//LEFT CORNER WAS WITH -PLAYERSIZE, THEN, SINCE LOCATION HAS ALREADY BEEN DECREMENTED BY 1 
    		//AND BORDERSIZE IS 2, DECREASE ONLY BY 1 MORE (1/2 BORDERSIZE) TO GO THROUGH THE WALL
    	}
		
		//SAME THING BUT FOR Y COORDINATES
		relativeLocation[1]+= y;
		
		if (relativeLocation[1] > upperEdge) 
    	{relativeLocation[1] = lowerEdge; location[1]+=playerSize + 1;}
    	else if (relativeLocation[1] < lowerEdge) 
    	{relativeLocation[1] = upperEdge;location[1]-=playerSize + 1;}
		
	}
}
