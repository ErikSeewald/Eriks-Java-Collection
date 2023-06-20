package taxCollector;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;

import javax.swing.JPanel;

public class TC_Panel extends JPanel
{
	private static final long serialVersionUID = -5017286155233171011L;
	
	private int PANEL_HEIGHT = 700;
	private int PANEL_WIDTH = (int) (PANEL_HEIGHT * 1.52);
	
	private MapHandler mapHandler;
	private TaxCollector taxCollector;
	private IRS irs;
	
	TC_Panel()
	{
		this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		
		mapHandler = new MapHandler(this);
		taxCollector = mapHandler.getTaxCollector();
		irs = mapHandler.getIRS();
		tile_size = mapHandler.getTileSize();
		
		updateMapReferences();
	}
	
	public void move(MapHandler.Directions direction)
	{
		mapHandler.moveTaxCollector(direction);
	}
	
	public void switchGridBool()
	{
		draw_grid = !draw_grid;
		repaint();
	}
	
	public void advanceFrame()
	{
		mapHandler.update();
		repaint();
	}
	
	public void interaction()
	{
		mapHandler.interaction();
		repaint();
	}
	
	public void restart()
	{
		mapHandler.reset();
		this.taxCollector = mapHandler.getTaxCollector();
		this.irs = mapHandler.getIRS();
		repaint();
	}
	
	//---------------------------------------PAINT---------------------------------------
	
	private static final Color background_col = new Color(55, 55, 70);
	private static final Color tax_collector_col = new Color(30, 30, 35);
	private static final Color house_col_1 = new Color(150, 100, 55);
	private static final Color house_col_2 = new Color(240, 180, 45);
	private static final Color ui_col = new Color(220, 220, 230);
	private static final Color bankrupt_col = new Color(245, 120, 120);
	public static final Color irs_col = new Color(80, 80, 85);
	
	private boolean draw_grid;
	
	private int tile_size; //controlled by MapHandler
	
	private int scroll_x, scroll_y;
	
	private ArrayList<House> houses;
	
	public void updateMapReferences()
	{
		houses = mapHandler.getAllHousesOnScreen();
	}
	
	public void paint(Graphics g)
	{
		Graphics2D g2D = (Graphics2D) g;
		super.paint(g2D);
		
		//BACKGROUND
		g2D.setPaint(background_col);
		g2D.fillRect(0, 0, PANEL_WIDTH, PANEL_HEIGHT);
		
		if (irs.isBankrupt())
		{
			g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2D.setPaint(bankrupt_col);
			g2D.setFont(new Font("", Font.BOLD, 150));
			g2D.drawString("BANKRUPT", (int) (PANEL_WIDTH * 0.11), (int) (PANEL_HEIGHT * 0.6));
			return;
		}
		
		if (draw_grid) {drawGrid(g2D);}
		
		//SCREEN COORDINATES
		scroll_x = -(mapHandler.getTopLeftX() * tile_size);
		scroll_y = -(mapHandler.getTopLeftY() * tile_size);
		g2D.translate(scroll_x, scroll_y); // works like glTransform, i.e every following x coordinate will get scroll_x added to it
		
		//HOUSES
		g2D.setStroke(new BasicStroke(4));
		for(House house : houses) //List updated by mapHandler when scrolling
		{drawHouse(g2D, house);}
		
		//TAX COLLECTOR
		g2D.setPaint(tax_collector_col);
		int tcSize = taxCollector.size;
		g2D.fillRect(taxCollector.x - tcSize / 2, taxCollector.y - tcSize / 2, tcSize, tcSize);
		
		//IRS
		g2D.setPaint(irs_col);
		g2D.fillRect
		(
				(irs.tile_x * tile_size) - (MapHandler.irs_size_tiles / 2) * tile_size, 
				(irs.tile_y * tile_size) - (MapHandler.irs_size_tiles / 2) * tile_size, 
				MapHandler.irs_size_tiles * tile_size, 
				MapHandler.irs_size_tiles * tile_size
		);
		
		//UI
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2D.setFont(new Font("", Font.BOLD, 20));
		g2D.setPaint(ui_col);
		
		g2D.drawString("IRS", irs.tile_x * tile_size - 10, irs.tile_y * tile_size + 15);
		
		g2D.translate(-scroll_x, -scroll_y);
		
		g2D.drawString("Collected: " + taxCollector.getCollected(), (int) (PANEL_WIDTH * 0.6),PANEL_HEIGHT >> 4);
		g2D.drawString("IRS Funds: " + irs.getFunds(), PANEL_WIDTH - PANEL_WIDTH / 5, PANEL_HEIGHT >> 4);
	}
	
	private void drawGrid(Graphics2D g2D)
	{	
		g2D.setPaint(Color.LIGHT_GRAY);
		for (int i = 1; i <= MapHandler.tiles_on_screen_x; i++)
		{
			g2D.drawLine(i * tile_size, 0, i * tile_size, PANEL_HEIGHT);
			if (i % 2 == 0) {continue;}
			g2D.drawString("" + (i + mapHandler.getTopLeftX()), i * tile_size, tile_size);
		}
		
		for (int i = 1; i <= MapHandler.tiles_on_screen_y; i++)
		{
			g2D.drawLine(0, i* tile_size, PANEL_WIDTH, i * tile_size);
			g2D.drawString("" + (i + mapHandler.getTopLeftY()), tile_size / 4, i * tile_size);
		}
	}
	
	private void drawHouse(Graphics2D g2D, House house)
	{
		g2D.setPaint(house_col_1);
		g2D.fillRect
		(
				(house.i * tile_size) - (MapHandler.house_size_tiles / 2) * tile_size, 
				(house.j * tile_size) - (MapHandler.house_size_tiles / 2) * tile_size, 
				MapHandler.house_size_tiles * tile_size, 
				MapHandler.house_size_tiles * tile_size
		);
		
		if (!house.taxDue()) {return;}
		
		g2D.setPaint(house_col_2);
		g2D.drawRect
		(
				(house.i * tile_size) - (MapHandler.house_size_tiles / 2) * tile_size, 
				(house.j * tile_size) - (MapHandler.house_size_tiles / 2) * tile_size, 
				MapHandler.house_size_tiles * tile_size, 
				MapHandler.house_size_tiles * tile_size
		);
	}
}
