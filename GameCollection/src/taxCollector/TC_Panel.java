package taxCollector;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

public class TC_Panel extends JPanel
{
	private static final long serialVersionUID = -5017286155233171011L;
	
	private int PANEL_HEIGHT = 700;
	private int PANEL_WIDTH = (int) (PANEL_HEIGHT * 1.52);
	
	private MapHandler mapHandler;
	private TaxCollector taxCollector;
	
	TC_Panel()
	{
		this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		
		mapHandler = new MapHandler(this);
		taxCollector = mapHandler.getTaxCollector();
	}
	
	//---------------------------------------PAINT---------------------------------------
	
	private static final Color background_col = new Color(55, 55, 70);
	
	public void paint(Graphics g)
	{
		Graphics2D g2D = (Graphics2D) g;
		
		//BACKGROUND
		g2D.setPaint(background_col);
		g2D.fillRect(0, 0, PANEL_WIDTH, PANEL_HEIGHT);
		
		//UI
		
	}
}
