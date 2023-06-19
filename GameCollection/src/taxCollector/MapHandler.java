package taxCollector;

import java.util.ArrayList;

public class MapHandler 
{
	private MapItem[][] map;
	
	private TaxCollector taxCollector;
	private TC_Panel panel;
	
	private int tile_size;
	
	public static final int tiles_on_screen_x = 75, tiles_on_screen_y = 50;
	private int top_left_x, top_left_y; // indices
	
	public static enum Directions
	{
		EAST, SOUTH, WEST, NORTH;
	}
	
	public static final int house_size_tiles = 2; // houses are 2x2 tiles
	
	MapHandler(TC_Panel panel)
	{
		this.panel = panel;
		
		map = new MapItem[1000][1000];
		
		top_left_x = top_left_y = 0;
		tile_size = 700 / 50; //700 == PANEL_HEIGHT
		taxCollector = new TaxCollector(400, 400, (int) (tile_size * 1.5));
		
		map[10][10] = new House(10, 10);
	}
	
	public TaxCollector getTaxCollector() {return taxCollector;}
	
	public int getTileSize() {return tile_size;}
	
	public int getTopLeftX() {return top_left_x;}
	
	public int getTopLeftY() {return top_left_y;}
	
	public void moveTaxCollector(Directions direction)
	{
		switch (direction)
		{
		case EAST: taxCollector.x += taxCollector.speed;
			break;
		case SOUTH: taxCollector.y += taxCollector.speed;
			break;
		case WEST: taxCollector.x -= taxCollector.speed;
			break;
		case NORTH: taxCollector.y -= taxCollector.speed;
			break;
		}
		
		this.scroll();
	}
	
	private void scroll()
	{	
		int index_x = taxCollector.x / tile_size;
		int index_y = taxCollector.y / tile_size;
		
		//X
		if (index_x > top_left_x + tiles_on_screen_x * 0.8)
		{top_left_x++; taxCollector.x += tile_size / 2;}
		
		else if (index_x < top_left_x + tiles_on_screen_x * 0.2)
		{top_left_x--; taxCollector.x -= tile_size / 2;}
		
		//Y
		else if (index_y > top_left_y + tiles_on_screen_y * 0.8)
		{top_left_y++; taxCollector.y += tile_size / 2;}
		
		else if (index_y < top_left_y + tiles_on_screen_y * 0.2)
		{top_left_y--; taxCollector.y -= tile_size / 2;}
		
		else {return;}
	
		panel.updateMapReferences();
	}
	
	public ArrayList<House> getAllHousesOnScreen()
	{
		ArrayList<House> houses = new ArrayList<>();
		
		for (int i = top_left_x; i < top_left_x + tiles_on_screen_x; i++)
		{
			for (int j = top_left_y; j < top_left_y + tiles_on_screen_y; j++)
			{
				if (map[i][j] instanceof House) {houses.add((House) map[i][j]);}
			}
		}
		
		return houses;
	}
}
