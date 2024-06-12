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

import ejcMain.util.EJC_Util;
import ejcMain.util.EJC_Util.Direction;
import taxCollector.MapHandler.ResetActions;
import taxCollector.mapItem.House;
import taxCollector.mapItem.IRS;
import taxCollector.mapItem.Lake;
import taxCollector.mapItem.MapItem;
import taxCollector.mapItem.Road;
import taxCollector.mapItem.Tree;
import taxCollector.mapItem.Tree.ColorStates;

public class TC_Panel extends JPanel
{
	private static final long serialVersionUID = -5017286155233171011L;
	
	private int PANEL_HEIGHT = 700;
	private int PANEL_WIDTH = (int) (PANEL_HEIGHT * 1.52);
	
	private MapHandler mapHandler;
	private TaxCollector taxCollector;
	private IRS irs;
	private ArrayList<MapItem> mapItems;
	
	private boolean drawGrid;
	private int tileSize; //controlled by MapHandler	
	private int scrollX, scrollY;
	
	EJC_TaxCollector ejcTaxCollector;
	
	TC_Panel(EJC_TaxCollector ejcTaxCollector)
	{
		this.ejcTaxCollector = ejcTaxCollector;
		this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		
		mapHandler = new MapHandler(this);
		taxCollector = mapHandler.getTaxCollector();
		irs = mapHandler.getIRS();
		tileSize = mapHandler.getTileSize();
		
		updateMapReferences();
	}
	
	public void stop()
	{mapHandler.stop(); mapHandler = null;}
	
	public void move(Direction direction)
	{
		mapHandler.moveTaxCollector(direction);
	}
	
	public void switchGridBool()
	{
		drawGrid = !drawGrid;
		repaint();
	}
	
	public void switchDebugBool()
	{
		mapHandler.switchDebugBool();
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
	
	public void restart(boolean newSeed)
	{
		this.ejcTaxCollector.resetTrack();
		mapHandler.reset(newSeed);
		this.taxCollector = mapHandler.getTaxCollector();
		this.irs = mapHandler.getIRS();
		repaint();
	}
	
	public void updateMapReferences()
	{
		mapItems = mapHandler.getAllMapItemsOnScreen();
	}
	
	public boolean updateValid() {return !irs.isBankrupt();}
	
	public void createSeed()
	{
		mapHandler.setSeed(EJC_Util.createSeed());
		restart(ResetActions.keepSeed);
	}
	
	//---------------------------------------PAINT---------------------------------------
	
	private static final Color background_col = new Color(55, 55, 70);
	private static final Color tax_collector_col = new Color(30, 30, 35);
	private static final Color house_col_1 = new Color(150, 100, 55);
	private static final Color house_col_2 = new Color(240, 180, 45);
	private static final Color ui_col = new Color(220, 220, 230);
	private static final Color bankrupt_col = new Color(245, 120, 120);
	private static final Color damage_col = new Color(245, 60, 60);
	private static final Color irs_col = new Color(80, 80, 85);
	private static final Color irs_collect_col = new Color(30, 170, 40);
	private static final Color collect_col = new Color(120, 245, 120);
	private static final Color tree_crown_col = new Color(30, 120, 40);
	private static final Color tree_trunk_col = new Color(120, 75, 35);
	private static final Color lake_col = new Color(70, 120, 160);
	private static final Color road_col = new Color(45, 45, 50);
	private static final Color car_col = new Color(120, 120, 130);
	
	public void paint(Graphics g)
	{
		Graphics2D g2D = (Graphics2D) g;
		
		//BACKGROUND
		g2D.setPaint(background_col);
		g2D.fillRect(0, 0, PANEL_WIDTH, PANEL_HEIGHT);
		
		if (irs.isBankrupt())
		{
			g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2D.setFont(new Font("", Font.BOLD, 20));
			g2D.setPaint(ui_col);
			g2D.drawString("Seed: " + mapHandler.getSeed(), PANEL_WIDTH / 20, PANEL_HEIGHT >> 4);
			
			g2D.setPaint(bankrupt_col);
			g2D.setFont(new Font("", Font.BOLD, 150));
			g2D.drawString("BANKRUPT", (int) (PANEL_WIDTH * 0.11), (int) (PANEL_HEIGHT * 0.6));
			return;
		}
		
		if (drawGrid) {drawGrid(g2D);}
		
		//SCREEN COORDINATES
		scrollX = -(mapHandler.getTopLeftX() * tileSize);
		scrollY = -(mapHandler.getTopLeftY() * tileSize);
		g2D.translate(scrollX, scrollY); // works like glTransform, i.e every following coordinate will get scroll added to it
		
		//MAP ITEMS (Except for IRS)
		g2D.setStroke(new BasicStroke(4));
		for(MapItem item : mapItems) //List updated by mapHandler when scrolling
		{
			if (item instanceof House) {drawHouse(g2D, (House) item);}
			else if (item instanceof Tree) {drawTree(g2D, (Tree) item);}
			else if (item instanceof Lake) {drawLake(g2D, (Lake) item);}
			else if (item instanceof Road) {drawRoad(g2D, (Road) item);}
		}
		
		//TAX COLLECTOR
		g2D.setPaint(taxCollector.damageAnimation() ? damage_col : tax_collector_col);
		int tcSize = taxCollector.size;
		g2D.fillRect(taxCollector.i * tileSize - tcSize / 2, taxCollector.j  * tileSize - tcSize / 2, tcSize, tcSize);
		
		//IRS
		drawIRS(g2D);
		
		//CARS
		for (Car car : mapHandler.getAllCarsOnScreen())
		{drawCar(g2D, car);}
		
		//UI
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2D.setFont(new Font("", Font.BOLD, 20));
		
		g2D.setPaint(taxCollector.emptyAnimation() ? collect_col : ui_col);
		g2D.drawString("IRS", irs.i * tileSize - 10, irs.j * tileSize + 15);
		
		g2D.translate(-scrollX, -scrollY);
		
		if (taxCollector.emptyAnimation() || taxCollector.damageAnimation()) {g2D.setPaint(bankrupt_col);}
		else if (taxCollector.collectAnimation()) {g2D.setPaint(collect_col);}
		g2D.drawString("Collected: " +  EJC_Util.round(taxCollector.getCollected(), 2), (int) (PANEL_WIDTH * 0.6),PANEL_HEIGHT >> 4);
		
		if (taxCollector.emptyAnimation()) {g2D.setPaint(collect_col);}
		else {g2D.setPaint(ui_col);}
		g2D.drawString("IRS Funds: " + EJC_Util.round(irs.getFunds(), 2), PANEL_WIDTH - PANEL_WIDTH / 5, PANEL_HEIGHT >> 4);
		
		g2D.setPaint(ui_col);
		g2D.drawString("Seed: " + mapHandler.getSeed(), PANEL_WIDTH / 20, PANEL_HEIGHT >> 4);
	}
	
	private void drawGrid(Graphics2D g2D)
	{	
		g2D.setPaint(Color.LIGHT_GRAY);
		for (int i = 1; i <= MapHandler.tiles_on_screen_x; i++)
		{
			g2D.drawLine(i * tileSize, 0, i * tileSize, PANEL_HEIGHT);
			if (i % 2 == 1) {g2D.drawString("" + (i + mapHandler.getTopLeftX()), i * tileSize, tileSize);}
		}
		
		for (int i = 1; i <= MapHandler.tiles_on_screen_y; i++)
		{
			g2D.drawLine(0, i* tileSize, PANEL_WIDTH, i * tileSize);
			g2D.drawString("" + (i + mapHandler.getTopLeftY()), tileSize / 4, i * tileSize);
		}
	}
	
	private void drawCar(Graphics2D g2D, Car car)
	{	
		g2D.setPaint(car_col);
		g2D.fillRect(car.i * tileSize, car.j * tileSize, car.getSizeTilesX() * tileSize, car.getSizeTilesY() * tileSize);
	}
	
	private void drawHouse(Graphics2D g2D, House house)
	{		
		g2D.setPaint(house_col_1);
		g2D.fillRect
		(
				(house.i * tileSize) - (House.size_tiles / 2) * tileSize, 
				(house.j * tileSize) - (House.size_tiles / 2) * tileSize, 
				House.size_tiles * tileSize, 
				House.size_tiles * tileSize
		);
		
		if (!house.taxDue()) {return;}
		
		g2D.setPaint(house_col_2);
		g2D.drawRect
		(
				(house.i * tileSize) - (House.size_tiles / 2) * tileSize, 
				(house.j * tileSize) - (House.size_tiles / 2) * tileSize, 
				House.size_tiles * tileSize, 
				House.size_tiles * tileSize
		);
	}
	
	private void drawTree(Graphics2D g2D, Tree tree)
	{	
		//TRUNK
		g2D.setPaint(tree_trunk_col);
		g2D.fillRect
		(
				(int) ((tree.i + Tree.trunk_width_tiles * 1.5) * tileSize), 
				tree.j * tileSize, 
				(int) (Tree.trunk_width_tiles * tileSize), 
				(int) (Tree.trunk_height_tiles * tileSize)
		);
		
		//CROWN
		Color c = tree_crown_col;
		if (tree.colorState == ColorStates.BRIGHTER) {c = c.brighter();}
		else if (tree.colorState == ColorStates.DARKER) {c = c.darker();}
		g2D.setPaint(c);
		
		g2D.fillRect
		(
				tree.i * tileSize,
				(int) ((tree.j - Tree.trunk_height_tiles)* tileSize), 
				Tree.size_tiles * tileSize, 
				Tree.size_tiles * tileSize
		);
	}
	
	private void drawLake(Graphics2D g2D, Lake lake)
	{
		g2D.setPaint(lake_col);
		g2D.fillRect(lake.i * tileSize, lake.j * tileSize, Lake.size_tiles * tileSize, Lake.size_tiles * tileSize);
	}
	
	private void drawRoad(Graphics2D g2D, Road road)
	{		
		g2D.setPaint(road_col);
		g2D.fillRect(road.i * tileSize, road.j * tileSize, tileSize, tileSize);
	}
	
	private void drawIRS(Graphics2D g2D)
	{
		g2D.setPaint(taxCollector.emptyAnimation() ? irs_collect_col : irs_col);
		
		//ON SCREEN
		if (irs.i >= mapHandler.getTopLeftX() && irs.j >= mapHandler.getTopLeftY()
				&& irs.i < mapHandler.getTopLeftX() + MapHandler.tiles_on_screen_x
				&& irs.j < mapHandler.getTopLeftY() + MapHandler.tiles_on_screen_y)
		{
			g2D.fillRect
			(
					(irs.i * tileSize) - (IRS.size_tiles / 2) * tileSize, 
					(irs.j * tileSize) - (IRS.size_tiles / 2) * tileSize, 
					IRS.size_tiles * tileSize, 
					IRS.size_tiles * tileSize
			);
			return;
		}
		
		//OFFSCREEN
		// put a triangle partly on the way of a vector towards the IRS, one of the points on it, two of them on it's
		// normal => triangle pointing towards IRS
		int x = taxCollector.i * tileSize, y = taxCollector.j * tileSize;
		float[] vec = EJC_Util.normalize((irs.i * tileSize) - x, (irs.j * tileSize) - y);
		float[] normal = {-vec[1], vec[0]};
		
		g2D.fillPolygon
		(
				new int[] {(int) (x + vec[0] * 50), (int) ((x + vec[0] * 20) + (normal[0] * 10)), (int) ((x + vec[0] * 20) - (normal[0] * 10))}, 
				new int[] {(int) (y + vec[1] * 50), (int) ((y + vec[1] * 20) + (normal[1] * 10)), (int) ((y + vec[1] * 20) - (normal[1] * 10))},
				3
		);
	}
}
