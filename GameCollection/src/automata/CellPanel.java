package automata;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;
import javax.swing.JPanel;

/**
 * Panel to display the automata simulation. Extends {@link JPanel}.
 */
public class CellPanel extends JPanel
{
	private static final long serialVersionUID = 342384L;
	public static final int PANEL_WIDTH = 1500;
	public static final int PANEL_HEIGHT = 900;
	
	private CellHandler cellHandler;
	private Random random;
	private Color mainColor;
	
	/**
	 * Creates an instance of {@link CellPanel}.
	 */
	CellPanel()
	{
		this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		this.random = new Random();
		this.mainColor = randomColor();
		this.cellHandler = new CellHandler();
		
		this.addMouseListener(new ClickListener());
	}
	
	/**
	 * {@link CellPanel} specific extension of {@link MouseAdapter}.
	 */
	public class ClickListener extends MouseAdapter
	{
	    /**
	     * Handles {@link MouseEvent}s for drawing on the automata cells.
	     * 
	     * @param e the {@link MouseEvent} for 'mousePressed'
	     */
		@Override
		public void mousePressed(MouseEvent e) 
		{
			cellHandler.draw(e.getX(), e.getY());
		}
	}
	
	/**
	 * Updates the view of this panel instance.
	 * Additionally handles updating this panels {@link CellHandler}.
	 */
	public void update()
	{
		cellHandler.update();
		repaint();
	}
	
	/**
	 * Handles the switch to a random new set of rules for this panels {@link CellHandler}
	 * and to new colors for the cells.
	 */
	public void randomSwitch()
	{
		cellHandler.generateRules();
		mainColor = this.randomColor();
	}
	
	/**
	 * Transfers a request to switch the cell/pixel size to {@link CellHandler}.
	 */
	public void switchPixelSize()
	{
		cellHandler.switchPixelSize();
	}
	
	/**
	 * Helper method for {@link EJC_Automata}s stop method.
	 * Calls {@link CellHandler}s stop method and frees memory.
	 */
	public void stop()
	{
		cellHandler.stop();
		cellHandler = null;
	}
	
	/**
	 * Renders a view of the automata cells to the given {@link Graphics} object.
	 * 
	 * @param g the {@link Graphics} object to render to.
	 */
	@Override
	public void paint(Graphics g)
	{
		Graphics2D g2D = (Graphics2D) g;
		
		//CELLS
		paintCells(g2D, cellHandler.getCells());
	}
	
	/**
	 * Gets a new random color. Utilizes the global {@link Random} object of this class.
	 * 
	 * @return new randomly generated {@link Color}
	 */
	private Color randomColor()
	{
		return new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
	}
	
	/**
	 * Draws the given cells to the given {@link Graphics2D} object with the current global parameters.
	 * 
	 * @param g2D the {@link Graphics2D} object to render to.
	 * @param cells the {@link float[][]} array containing the cells' values
	 */
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
	
	/**
	 * Blends the given {@link Color} based on the brightness parameter.
	 * If the brightness is 0, the returned {@link Color} is black. If it is 0.5, the input {@link Color} is returned.
	 * If brightness is 1, the returned {@link Color} is white. Everything in between is blended accordingly.
	 * 
	 * @param color the {@link Color} to blend
	 * @param brightness the {@link float} brightness parameter to blend with
	 * @return the new blended {@link Color}
	 */
	private Color blendCellColor(Color color, float brightness) 
	{
		int r,g,b;
		if (brightness >= 0.5f) //Blend between color and white
		{
			brightness -= 0.5;
			r = (int) (color.getRed() * (1.0 - brightness) + 255 * brightness);
	        g = (int) (color.getGreen() * (1.0 - brightness) + 255 * brightness);
	        b = (int) (color.getBlue() * (1.0 - brightness) + 255 * brightness);
		}
		else //Blend between black and color
		{
			r = (int) (color.getRed() * brightness);
	        g = (int) (color.getGreen() * brightness);
	        b = (int) (color.getBlue() * brightness);
		}
        
        return new Color(r, g, b);
    }
}