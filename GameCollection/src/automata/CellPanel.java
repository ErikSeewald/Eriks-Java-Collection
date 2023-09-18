package automata;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class CellPanel extends JPanel
{
	private static final long serialVersionUID = 342384L;
	public static final int PANEL_WIDTH = 1500;
	public static final int PANEL_HEIGHT = 900;
	
	private CellHandler cellHandler;
	
	CellPanel()
	{
		this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		
		this.cellHandler = new CellHandler();
	}
	
	public void update()
	{
		cellHandler.update();
		repaint();
	}
	
	public void stop()
	{
		cellHandler.stop();
		cellHandler = null;
	}
	
	@Override
	public void paint(Graphics g)
	{
		Graphics2D g2D = (Graphics2D) g;
		
		//BACKGROUND (DO NOT DRAW ALL STATE 0 CELLS INDIVIDUALLY
		g2D.setPaint(color0);
		g2D.fillRect(0, 0, PANEL_WIDTH, PANEL_HEIGHT);
		
		//CELLS
		paintCells(g2D, cellHandler.getCells());
	}
	
	//Colors to blend between based on cell state
	private static final Color color0 = new Color(0,0,0);
	private static final Color color1 = new Color(0, 180, 255);
	
	private void paintCells(Graphics2D g2D, float[][] cells)
	{
		int width = cells.length;
		int cellWidth = PANEL_WIDTH / width;
		
		int height = cells[0].length;
		int cellHeight = PANEL_HEIGHT / height;
		
		for (int x = 0; x < width; x++)
		{
			for (int y = 0; y < height; y++)
			{
				if (cells[x][y] == 0) {continue;}
				
				g2D.setPaint(mix(color0, color1, cells[x][y]));
				g2D.fillRect(x * cellWidth, y * cellWidth, cellWidth, cellHeight);
			}
		}
	}
	
	private Color mix(Color a, Color b, double ratio) 
	{
        return new Color((int) (a.getRed() * ratio + b.getRed() * (1.0 - ratio)),
                (int) (a.getGreen() * ratio + b.getGreen() * (1.0 - ratio)),
                (int) (a.getBlue() * ratio + b.getBlue() * (1.0 - ratio)));
    }
}