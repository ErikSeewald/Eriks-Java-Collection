package automata;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;

import javax.swing.JPanel;

public class CellPanel extends JPanel
{
	private static final long serialVersionUID = 342384L;
	public static final int PANEL_WIDTH = 1500;
	public static final int PANEL_HEIGHT = 900;
	
	private CellHandler cellHandler;
	private Random random;
	
	CellPanel()
	{
		this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		this.random = new Random();
		this.mainColor = randomColor();
		this.cellHandler = new CellHandler();
	}
	
	public void update()
	{
		cellHandler.update();
		repaint();
	}
	
	public void randomSwitch()
	{
		cellHandler.generateRules();
		this.randomColor();
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
		
		//CELLS
		paintCells(g2D, cellHandler.getCells());
	}
	
	private Color mainColor;
	private static final Color[] colors =
	{
		new Color(5, 125, 37)
	};
	
	private Color randomColor()
	{
		return new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
		//return colors[random.nextInt(colors.length)];
	}
	
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
				g2D.setPaint(blendCellColor(mainColor, cells[x][y]));
				g2D.fillRect(x * cellWidth, y * cellWidth, cellWidth, cellHeight);
			}
		}
	}
	
	private Color blendCellColor(Color color, float brightness) 
	{
		// Calculate the blended RGB values
		int r,g,b;
		if (brightness >= 0.5f)
		{
			brightness -= 0.5;
			r = (int) (color.getRed() * (1.0 - brightness) + 255 * brightness);
	        g = (int) (color.getGreen() * (1.0 - brightness) + 255 * brightness);
	        b = (int) (color.getBlue() * (1.0 - brightness) + 255 * brightness);
		}
		else
		{
			r = (int) (color.getRed() * brightness);
	        g = (int) (color.getGreen() * brightness);
	        b = (int) (color.getBlue() * brightness);
		}
        
        // Create and return the blended color
        return new Color(r, g, b);
    }
}