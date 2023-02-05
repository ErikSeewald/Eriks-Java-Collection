package parallelUniverses;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

public class ParallelUniversesPanel extends JPanel
{
	private static final long serialVersionUID = 9082922097976866954L;
	
	private static final int PANEL_SIZE = 623;
	
	private int[] location = {313,313};			//LOCATION ON THE ENTIRE MAP
	private int[] relativeLocation = {6,6};		//LOCATION WITHIN THE MAIN UNIVERSE
	

	private final static int borderSize = 2; //BORDER BETWEEN UNIVERSES
	
	// 64 BITS FOR THE RELATIVE LOCATION (WHEN SHOULD AN OVERFLOW OCCUR)
	//--> LOWER BOUND = -32 , UPPER BOUND = 31
	//OFFSET +3 BECAUSE OF PLAYERSIZE - 1/2 BORDERSIZE = 3 (SEE MOVEMENT CALCULATION FOR FURTHER EXPLANATION)
	private static final int upperEdge = 34;
	private static final int lowerEdge = -29;
		
	private static final int playerSize = 4;
	
	private static final int constantAdd = 307;	//CONSTANT THAT NEEDS TO BE ADDED TO THE RELATIVELOCATION TO MAKE IT ABSOLUTE FOR DRAW()

	private static final int universesPerRow = 9;
	
	//OFFSETS NEEDED FOR THE PARALLELUNIVERSES TO LINE UP WITH THE MAIN UNIVERSE PROPERLY
	private static final int outlineOffset = 1;			
	private static final int rect1Offset = outlineOffset + 15;
	private static final int rect2Offset = outlineOffset + 35;
	
	private static final int rect1Size = 25;
	private static final int rect2Size = 15;
	
	private static final int universeSize = 69;
	
	private boolean universesVisible = true;
	
	ParallelUniversesPanel()
	{this.setPreferredSize(new Dimension (PANEL_SIZE,PANEL_SIZE));}

	public void paint (Graphics g)
	{
		Graphics2D g2D = (Graphics2D) g;
		super.paint(g);
		
		//BACKGROUND
		g2D.setPaint(new Color (60,60,70));
		g2D.fillRect(0, 0, PANEL_SIZE, PANEL_SIZE);
	
		//UNIVERSES
		g2D.setPaint(new Color (180,180,190));
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
		g2D.setPaint(new Color (50,170,250));
		g2D.drawRect(rect1Offset+(4*(universeSize)), rect1Offset+(4*(universeSize)), rect1Size, rect1Size);
		g2D.drawRect(rect2Offset+(4*(universeSize)), rect2Offset+(4*(universeSize)), rect2Size, rect2Size);
		g2D.drawRect(outlineOffset+(4*(universeSize)), outlineOffset+(4*(universeSize)), universeSize, universeSize);
		
		
		//DRAW PLAYER LOCATION
		g2D.setPaint(new Color (50,250,140));
		g2D.fillRect(location[0], location[1], playerSize, playerSize);
		
		//DRAW RELATIVE PLAYER LOCATION
		g2D.setPaint(new Color (255,80,80));
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
    	
    	//OVERFLOWS
    	relativeLocation[0]+= x;
		
		if (relativeLocation[0] > upperEdge) 
    	{
    		relativeLocation[0] = lowerEdge; 
    		location[0]+=playerSize + 1;
    		//Since location has already been incremented by 1 and border size is 2, increase by 1 more
    	}
		
    	else if (relativeLocation[0] < lowerEdge) 
    	{
    		relativeLocation[0] = upperEdge;
    		location[0]-=playerSize + 1;
    	}
		
		relativeLocation[1]+= y;
		
		if (relativeLocation[1] > upperEdge) 
    	{
			relativeLocation[1] = lowerEdge; 
			location[1]+=playerSize + 1;
		}
		
    	else if (relativeLocation[1] < lowerEdge) 
    	{
    		relativeLocation[1] = upperEdge;
    		location[1]-=playerSize + 1;
    	}
	}
}