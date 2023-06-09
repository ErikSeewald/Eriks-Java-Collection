package cheeseBreeder;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import javax.swing.JPanel;
import cheeseBreeder.cheese.*;
import cheeseBreeder.cheese.Cheese.Hole;

public class BreederPanel extends JPanel
{
	private static final long serialVersionUID = 326279601684727567L;

	private int PANEL_HEIGHT = 700;
	private int PANEL_WIDTH = (int) (PANEL_HEIGHT * 1.52);
	
	BreederPanel()
	{
		this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
	}
	
	//---------------------------------------PAINT---------------------------------------
	
	private static final Color background_col = new Color(55, 55, 70);
	private static final Color text_col = new Color(220, 220, 250);
	private static final Font cheese_font = new Font("", Font.BOLD, 20);
	
	public void paint(Graphics g)
	{
		Graphics2D g2D = (Graphics2D) g;
		
		//BACKGROUND
		g2D.setPaint(background_col);
		g2D.fillRect(0, 0, PANEL_WIDTH, PANEL_HEIGHT);
		
		//CHEESE
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//		drawCheese(g2D, new Emmentaler(100, 100));
//		drawCheese(g2D, new Gouda(450, 100));
//		drawCheese(g2D, new Camembert(800, 100));
//		drawCheese(g2D, new Cheddar(100, 400));
//		drawCheese(g2D, new Mozarella(450, 400));
//		drawCheese(g2D, new BlueCheese(800, 400));
		drawCheese(g2D, new Breedery().breed(new Emmentaler(100, 100), new Mozarella(450, 100)));
	}
	
	public void drawCheese(Graphics2D g2D, Cheese cheese)
	{	
		if (cheese instanceof CheeseChild && !((CheeseChild) cheese).isGrownUp())
		{throw new IllegalArgumentException("Cannot draw CheeseChild with grownUp == false");}
		
		if (!cheese.holesCreated())
		{throw new IllegalArgumentException("Cannot draw Cheese with holesCreated == false");}
		
		//NAME
		g2D.setFont(cheese_font);
		g2D.setPaint(text_col);
		g2D.drawString(cheese.getName(), cheese.x, cheese.y + Cheese.size * 5/4);
		
		//CORE
		g2D.setPaint(cheese.getCoreColor());
		
		Polygon core = new Polygon();
		core.addPoint(cheese.x, cheese.y + Cheese.size);
        core.addPoint(cheese.x + Cheese.size, cheese.y + Cheese.size / 2);
        core.addPoint(cheese.x, cheese.y);
		g2D.fillPolygon(core);
		
		g2D.setClip(core); // make sure no other things we draw clips outside of the cheese triangle
		
		//RIND
		g2D.setPaint(cheese.getRindColor());
		int rind_size = cheese.getRindSize();
		g2D.fillRect(cheese.x, cheese.y, rind_size, Cheese.size);

		//HOLES
		g2D.setPaint(cheese.getHoleColor());
		for (Hole hole : cheese.getHoles())
		{
			g2D.fillOval(cheese.x + hole.x, cheese.y + hole.y, hole.size, hole.size);
		}
		
		g2D.setClip(null); // reset the clip
	}
}