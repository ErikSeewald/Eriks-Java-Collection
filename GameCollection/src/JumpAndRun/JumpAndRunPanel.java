package JumpAndRun;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.JPanel;

public class JumpAndRunPanel extends JPanel //implements ActionListener
{
	private static final long serialVersionUID = 6717353794276866444L;

	//PANEL
	private static final int PANEL_HEIGHT = 600;
	private static final int PANEL_WIDTH = 900;
	
	//MAP
	private byte[] map = new byte[10000];
	private int scrollingSpeed;
	private int offset = 0;		//used for pushing the elements to the left -> scrolling
	
	//ELEMENTS
	private static final int cubeSize = 30;
	private int[] layoutChances = new int[8];
	
	private static enum Layouts
	{

		/*    smallCol 	| mediumCol	|  bigCol	|  flyBlock	| lowTunnel	| highTunnel
		 *				|			|			|			|			|	||
		 *				|			|			|			|			|
		 *				|			|			|			|	||		|
		 * 				|			|			|	||		|	||		|	||
		 * 				|			|			|			|			|
		 * 				|			|	||		|			|			|
		 * 				|	||		|	||		|			|			|
		 *  	||		|	||		|	||		|			|	||		|
		 */
		
		smallCol(new int[] {540, cubeSize}, null),
		mediumCol(new int[] {510, cubeSize*2}, null),
		bigCol(new int[] {480, cubeSize*3}, null),
		flyBlock(new int[] {420, cubeSize}, null),
		lowTunnel(new int[] {390, cubeSize*2}, new int[] {540, cubeSize}),
		highTunnel(new int[] {330, cubeSize}, new int[] {420, cubeSize}),
		empty(null,null);
		
		int[] hitBox1, hitBox2;
		Layouts(int[] hitBox1, int[] hitBox2)
		{this.hitBox1 = hitBox1; this.hitBox2 = hitBox2;}
	}
	
	private class Element
	{	
		int x;
		int slot;
		Layouts layout;
		
		Element(byte type, int slot)
		{
			switch (type)
			{
				case 1: this.layout = Layouts.smallCol;
				break;
				case 2: this.layout = Layouts.mediumCol;
				break;
				case 3: this.layout = Layouts.bigCol;
				break;
				case 4: this.layout = Layouts.flyBlock;
				break;
				case 5: this.layout = Layouts.lowTunnel;
				break;
				case 6: this.layout = Layouts.highTunnel;
				break;
					
				default: this.layout = Layouts.empty;
			}
			
			this.slot = slot;
			this.x = (this.slot % 32) * cubeSize;
		}
	}
	private LinkedList<Element> elements = new LinkedList<>();
	
	//PLAYER
	class Player
	{
		int x,y;
		int prevY;
		
		int slot;
		
		boolean jumpAllowed = false;
	}
	Player player = new Player();
	
	//COLORS
	private static final Color backgroundColor = new Color(55,55,70);
	private static final Color elementColor = new Color(20,20,25);
	private static final Color playerColor = new Color(120,210,120);
	private static final Color lavaColor = new Color(220,60,60);
	
	//OTHERS
	boolean paused = false;
	private Random random = new Random();
	
	static class StartOperations
	{
		final static boolean newMap = true;
		final static boolean restart = false;
	}
	
	JumpAndRunPanel(int scrollingSpeed)
	{
		this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		this.scrollingSpeed = scrollingSpeed;
		
		//CHANGE LAYOUT CHANCES DEPENDING ON SCROLLING MODE
		int[] layoutSlow = {50,60,70,85,90,95,100};
		int[] layoutFast = {5,45,55,80,90,95,100};
		
		if (scrollingSpeed == 0)
		{layoutChances = layoutFast;}
		else {layoutChances = layoutSlow;}
		
		start(StartOperations.newMap);
	}
	
	public void start(boolean newMap)
	{
		player.x = 0;
		player.y = 10;
		player.slot = 0;
		offset = 0;
			
		//MAP
		if (newMap)
		{makeMap(map);}
		
		//ELEMENTS
		elements.removeAll(elements);
		for (int i = 0; i < 31; i++)
		{elements.add(new Element(map[i], i));}	
		
		updateElements();
	}
	
	//MAP
	public void makeMap(byte[] map)
	{
		for (int i = 1; i < map.length; i++)
		{
			int rand = random.nextInt(100);
			
			if (map[i-1] > 4 && random.nextBoolean()) {map[i] = (byte) (random.nextInt(3)+4);}
			//if the last one was floating, there is a 50% change the next one will be too	
			
			for (byte j = 0; j < 7; j++)
			{
				if (rand < layoutChances[j]) 
				{map[i] = j; break;}
			}
		}
	}
	
	public void setSeed(int seed)
	{
		random.setSeed(seed);
		start(StartOperations.newMap);
	}
	
	public void paint(Graphics g)
	{
		Graphics2D g2D = (Graphics2D) g;
		
		//BACKROUND
		g2D.setPaint(backgroundColor);
		g2D.fillRect(0, 0, PANEL_WIDTH, PANEL_HEIGHT);
		
		//ELEMENTS
		g2D.setPaint(elementColor);
		
		Iterator<Element> e = elements.iterator();
		while (e.hasNext())
		{	
			Element element = e.next();
			
			Layouts layout = element.layout;
			if (layout.hitBox1 != null)
			{g2D.fillRect(element.x - offset, layout.hitBox1[0], cubeSize, layout.hitBox1[1]);}
			
			if (layout.hitBox2 != null)
			{g2D.fillRect(element.x - offset, layout.hitBox2[0], cubeSize, layout.hitBox2[1]);}
		}
			
		//PLAYER
		g2D.setPaint(playerColor);
		g2D.fillRect(player.x, player.y, cubeSize, cubeSize);
		
		//UI
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2D.setFont(new Font("", Font.BOLD, 60));
		g2D.drawString("" + player.slot, 820, 80);
		
		if (paused) 
		{
			g2D.setFont(new Font("", Font.BOLD, 60));
			g2D.drawString("PAUSED", 40, 100);
		}
		
		//LAVA
		g2D.setPaint(lavaColor);
		g2D.fillRect(0, 570, PANEL_WIDTH, cubeSize);
	}
	
	public void scroll()
	{offset+=scrollingSpeed;}
	
	//PLAYER
	public void movePlayer(int x, int y)
	{
		player.prevY = player.y;
		player.y+= y;
		
		player.x+= (x - scrollingSpeed);
		
		player.slot =(player.x + offset) / cubeSize;
		
		if (scrollingSpeed == 0)
		{
			if ((player.slot % 32) > 15)  //on the right of the screen -> scroll further
			{
				offset+=x;
				player.x-=x;		//keep the player at the same position relative to the screen
			}
			
			else if (player.slot > 15 && (player.slot % 32) < 10) //on the left of the screen -> scroll back
			{
				offset+=x;
				player.x-=x;
			}

		}
		
		//remove any changes to playerY of this round for X Axis collision detection, then add it back
		player.y-=y;
		player.x-=1;
		if (hitCheck()) {player.x-=x;}	
		 player.x+=1;
		player.y+=y;
		
		if (hitCheck()) {player.y-=y;}		//hit bottom
		
		player.jumpAllowed = (player.y - player.prevY) == 0;
		
		//DEATH CHECK
		if (player.y >= PANEL_HEIGHT-cubeSize || player.x < 0) {start(StartOperations.restart);}

		offset = offset % 601;
		updateElements();
		
		repaint();
	}
	
	private void updateElements()
	{	
		if ( (elements.getFirst().x - offset + cubeSize) < 0)
		{
			elements.removeFirst();
			
			int slot = elements.getLast().slot + 1;
			elements.addLast(new Element(map[slot], slot));
		}
	}
	
	private boolean hitCheck()
	{
		//LEFT CHECK
		Element element = elements.get(player.slot);
		
		if (hitboxCheck(element.layout.hitBox1)) {return true;}
		if (hitboxCheck(element.layout.hitBox2)) {return true;}	
		
		//RIGHT CHECK
		element = elements.get((player.x - offset + cubeSize) / cubeSize);
		if (player.x + cubeSize > element.x - offset && player.x + cubeSize < element.x - offset + cubeSize)
		{
			if (hitboxCheck(element.layout.hitBox1)) {return true;}
			if (hitboxCheck(element.layout.hitBox2)) {return true;}	
		}
		return false;
	}
	
	private boolean hitboxCheck(int[] hitbox)
	{
		if (hitbox == null) {return false;}
		
		return 		player.y >= hitbox[0] 				&& 		player.y < (hitbox[0] + hitbox[1])
				|| (player.y+cubeSize) > hitbox[0] 	&& 		(player.y+cubeSize) <= (hitbox[0] + hitbox[1]);		
	}
}