package cheeseBreeder;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import cheeseBreeder.cheese.Cheese;
import cheeseBreeder.cheese.Emmentaler;

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
		
		drawCheese(g2D, new Emmentaler(20, 20));
	}
	
	public void drawCheese(Graphics2D g2D, Cheese cheese)
	{	
		//NAME
		g2D.setFont(cheese_font);
		g2D.setPaint(text_col);
		g2D.drawString(cheese.getName(), cheese.x, cheese.y + Cheese.size * 5/4);
		
		
		//CORE
		g2D.setPaint(cheese.getCoreColor());
		g2D.fillPolygon
		(
				new int[] {cheese.x, cheese.x + Cheese.size, cheese.x}, 
				new int[] {cheese.y + Cheese.size, cheese.y + Cheese.size / 2, cheese.y}, 
				3
		);
		
		//RIND
		g2D.setPaint(cheese.getRindColor());
		int rind_size = cheese.getRindSize();
		g2D.setStroke(new BasicStroke(rind_size));
		g2D.drawLine(cheese.x, cheese.y + rind_size / 2, cheese.x, cheese.y + Cheese.size - rind_size / 2);
		
		//HOLES
		for (int i = 0; i < cheese.getHoleCount(); i++)
		{
			
		}
	}
	
}
