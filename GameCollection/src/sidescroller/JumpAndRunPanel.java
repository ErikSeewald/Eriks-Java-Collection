package sidescroller;
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

public class JumpAndRunPanel extends JPanel
{
	private static final long serialVersionUID = 6717353794276866444L;

	//PANEL
	private static final int PANEL_HEIGHT = 600;
	private static final int PANEL_WIDTH = 900;
	
	//MAP
	private byte[] map = new byte[10000];
	private int scrollingSpeed = 1;
	private int scrollAmount = 0;
	
	//ELEMENTS
	private static final int cubeSize = 30;
	private static final int[] layoutChances = {50,60,70,85,90,95,100};
	//private int[] layoutChances = {5,45,55,80,90,95,100};
	
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
			this.x = this.slot * cubeSize;
		}
	}
	private LinkedList<Element> elements = new LinkedList<>();
	
	//PLAYER
	class Player
	{
		int x = -1, y = 30;
		int slot = 0;
		int airTime = 0;
		
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
	
	JumpAndRunPanel()
	{
		this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		start(StartOperations.newMap);
	}
	
	public void start(boolean newMap)
	{
		player = new Player();
		scrollAmount = 0;
		scrollingSpeed = 1;
			
		//MAP
		if (newMap)
		{makeMap(map);}
		
		//ELEMENTS
		elements.removeAll(elements);
		for (int i = 0; i < 32; i++)
		{elements.add(new Element(map[i], i));}	
	}
	
	//MAP
	private void makeMap(byte[] map)
	{
		for (int i = 1; i < map.length; i++)
		{
			int rand = random.nextInt(100);
			
			if (map[i-1] > 4 && random.nextBoolean()) {map[i] = (byte) (random.nextInt(2)+4);}
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
	
	public void scroll()
	{
		if (elements.getLast().slot > 50) {scrollingSpeed = 2;}
		if (elements.getLast().slot > 300) {scrollingSpeed = 3;}
		
		scrollAmount-=scrollingSpeed;
	}
	
	public void paint(Graphics g)
	{
		Graphics2D g2D = (Graphics2D) g;
		g2D.translate(scrollAmount, 0);
		
		//BACKROUND
		g2D.setPaint(backgroundColor);
		g2D.fillRect(-scrollAmount, 0, PANEL_WIDTH, PANEL_HEIGHT);
		
		//ELEMENTS
		g2D.setPaint(elementColor);
		
		Iterator<Element> e = elements.iterator();
		while (e.hasNext())
		{	
			Element element = e.next();
			
			Layouts layout = element.layout;
			if (layout.hitBox1 != null)
			{g2D.fillRect(element.x, layout.hitBox1[0], cubeSize, layout.hitBox1[1]);}
			
			if (layout.hitBox2 != null)
			{g2D.fillRect(element.x, layout.hitBox2[0], cubeSize, layout.hitBox2[1]);}
		}
			
		//PLAYER
		g2D.setPaint(playerColor);
		g2D.fillRect(player.x, player.y, cubeSize, cubeSize);
		
		//UI
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2D.setFont(new Font("", Font.BOLD, 60));
		g2D.drawString("" + player.slot, 750 - scrollAmount, 80);
		
		if (paused)
		{
			g2D.setFont(new Font("", Font.BOLD, 60));
			g2D.drawString("PAUSED", 40 - scrollAmount, 100);
		}
		
		//LAVA
		g2D.setPaint(lavaColor);
		g2D.fillRect(-scrollAmount, 560, PANEL_WIDTH , cubeSize);
	}
	
	//PLAYER
	public void movePlayer(int x, int y)
	{	
		//X
		player.x+= x;
		Element element = xCheck();
		if (element != null) 
		{
			if (x > 0) {player.x = element.x - cubeSize - 1;}
			else {player.x = element.x + cubeSize - 1;}
		}
		
		//Y
		player.y+=y;
		int[] hitbox = yCheck();
		if (hitbox != null)
		{
			if (y > 0) 
			{player.y = hitbox[0] - cubeSize;}
			else 
			{player.y = hitbox[0] + hitbox[1];}

			player.jumpAllowed = true;
		}
		else {player.jumpAllowed = false;}
		
		//DEATH CHECK
		if (player.y >= PANEL_HEIGHT-cubeSize || player.x < -scrollAmount) {start(StartOperations.restart);}
		
		//UPDATE
		player.slot = player.x / cubeSize;
		updateElements();
		repaint();
	}
	
	private void updateElements()
	{		
		if (scrollAmount > -30) {return;}
		
		if (scrollAmount % 30 == 0)
		{
			elements.removeFirst();
			
			int slot = elements.getLast().slot + 1;
			if (slot < map.length) {elements.add(new Element(map[slot], slot));}
		}
	}
	
	private Element xCheck()
	{
		Iterator<Element> e = elements.iterator();
		while (e.hasNext())
		{
			Element element = e.next();
			if (player.x - cubeSize > element.x || player.x + cubeSize < element.x)
			{continue;} //skip all elements that aren't close
			
			if (hitboxCheck(element.layout.hitBox1)) {return element;}
			if (hitboxCheck(element.layout.hitBox2)) {return element;}	
		}
		
		return null;
	}
	
	private int[] yCheck()
	{
		Iterator<Element> e = elements.iterator();
		while (e.hasNext())
		{
			Element element = e.next();
			if (player.x - cubeSize +2 > element.x || player.x + cubeSize < element.x)
			{continue;} //skip all elements that aren't close
			
			if (hitboxCheck(element.layout.hitBox1)) {return element.layout.hitBox1;}
			if (hitboxCheck(element.layout.hitBox2)) {return element.layout.hitBox2;}	
		}
		
		return null;
	}
	
	private boolean hitboxCheck(int[] hitbox)
	{
		if (hitbox == null) {return false;}
		
		return 		player.y >= hitbox[0] 				&& 		player.y < (hitbox[0] + hitbox[1])
				|| (player.y+cubeSize) > hitbox[0] 	&& 		(player.y+cubeSize) <= (hitbox[0] + hitbox[1]);		
	}
}