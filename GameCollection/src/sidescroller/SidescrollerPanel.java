package sidescroller;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.JPanel;

import ejcMain.util.EJC_Util;

public class SidescrollerPanel extends JPanel
{
	private static final long serialVersionUID = 6717353794276866444L;

	//PANEL
	public static final int PANEL_HEIGHT = 600;
	public static final int PANEL_WIDTH = 900;
	
	//MAP
	private static final int maplength = 10000;
	private byte[] map = new byte[maplength];
	private LinkedList<Element> elements = new LinkedList<>();
	private MapHandler mapHandler;
	
	//OTHERS
	Player player;
	private boolean paused = false;
	
	static class StartOperations
	{
		final static boolean newMap = true;
		final static boolean restart = false;
	}
	
	SidescrollerPanel()
	{
		this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		mapHandler = new MapHandler(elements);
		player = new Player(this, mapHandler);
		start(StartOperations.newMap);
	}
	
	public void start(boolean newMap)
	{
		player.spawn();
			
		//MAP
		mapHandler.reset();
		if (newMap)
		{map = mapHandler.makeMap(maplength);}
		
		elements.removeAll(elements);
		for (int i = 0; i < 32; i++)
		{elements.add(new Element(map[i], i));}	
	}
	
	public void createSeed()
	{
		mapHandler.setSeed(EJC_Util.createSeed());
		start(StartOperations.newMap);
	}

	public void update(int x, int y)
	{
		if (paused) {return;}
		
		player.move(x, y);
		mapHandler.scroll();
		mapHandler.updateElements(map);
		repaint();
	}
	
	public void pause()
	{
		paused = !paused;
		repaint();
	}

	//---------------------------------------PAINT---------------------------------------
	
	private static final Color backgroundColor = new Color(55,55,70);
	private static final Color elementColor = new Color(20,20,25);
	private static final Color playerColor = new Color(120,210,120);
	private static final Color lavaColor = new Color(220,60,60);
	
	public void paint(Graphics g)
	{
		Graphics2D g2D = (Graphics2D) g;
		
		int scrollAmount = mapHandler.getScrollAmount();
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
			{g2D.fillRect(element.x, layout.hitBox1[0], Layouts.cubeSize, layout.hitBox1[1]);}
			
			if (layout.hitBox2 != null)
			{g2D.fillRect(element.x, layout.hitBox2[0], Layouts.cubeSize, layout.hitBox2[1]);}
		}
			
		//PLAYER
		g2D.setPaint(playerColor);
		g2D.fillRect(player.x, player.y, Layouts.cubeSize, Layouts.cubeSize);
		
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
		g2D.fillRect(-scrollAmount, 560, PANEL_WIDTH , Layouts.cubeSize);
	}
}