package taxCollector;

import java.util.ArrayList;
import java.util.Random;

public class MapHandler 
{
	private MapItem[][] map;
	
	private TaxCollector taxCollector;
	private IRS irs;
	private TC_Panel panel;
	private Random random;
	
	private int tile_size;
	
	public static final int tiles_on_screen_x = 75, tiles_on_screen_y = 50;
	private int top_left_x, top_left_y; // indices
	
	public static enum Directions
	{
		EAST, SOUTH, WEST, NORTH;
	}
	
	public static final int house_size_tiles = 2; // houses are 2x2 tiles
	public static final int house_distance = 10; // how many tiles must be between two houses at minimum
	
	private static final int map_size = 1000;
	
	MapHandler(TC_Panel panel)
	{
		this.panel = panel;
		
		top_left_x = top_left_y = map_size / 2;
		tile_size = 700 / 50; //700 == PANEL_HEIGHT
		taxCollector = new TaxCollector((map_size/2 + 37) * tile_size, (map_size/2 + 27) * tile_size, (int) (tile_size * 1.5));
		irs = new IRS();
		
		random = new Random();
		
		this.generateMap();
	}
	
	// MAP GENERATION
	public void generateMap()
	{
		map = new MapItem[map_size][map_size];
		System.gc();
		
		//ORDER OF THESE FUNCTION CALLS IS VERY IMPORTANT DUE TO FUNCTIONS 
		//ONLY ACCOUNTING FOR DISTANCE TO OBJECTS CREATED EARLIER
		generateHouses();
	}
	
	private void generateHouses()
	{
		for (int i = 0; i < map_size; i++)
		{
			for (int j = 0; j < map_size; j++)
			{
				if (random.nextInt(1000) == 1)
				{
					if (houseAllowed(i, j))
					{map[i][j] = new House(i, j);}
				}
			}
		}
	}
	
	private boolean houseAllowed(int i, int j)
	{
		for (int x = i - house_distance; x < i + house_distance; x++)
		{
			for (int y = j - house_distance; y < j + house_distance; y++)
			{
				if (x < 0 || x >= map_size || y < 0 || y >= map_size) {continue;}
				if (map[x][y] instanceof House) {return false;}
			}
		}
		return true;
	}
	
	// GAMEPLAY
	public void update()
	{
		irs.updateFunds();
		
		//MAP ITEMS
		for (int i = 0; i < map_size; i++)
		{
			for (int j = 0; j < map_size; j++)
			{
				if (map[i][j] != null) {map[i][j].update();}
			}
		}
	}
	
	public void collectAction()
	{
		int index_x = taxCollector.x / tile_size;
		int index_y = taxCollector.y / tile_size;
		
		for (int i = index_x - TaxCollector.collect_tile_range; i < index_x + TaxCollector.collect_tile_range; i++)
		{
			for (int j = index_y - TaxCollector.collect_tile_range; j < index_y + TaxCollector.collect_tile_range; j++)
			{
				if (map[i][j] instanceof House) {taxCollector.collect((House) map[i][j]);}
			}
		}
	}
	
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
		
		int index_x = taxCollector.x / tile_size;
		int index_y = taxCollector.y / tile_size;
		
		this.scroll(index_x, index_y);	
	}
	
	private void scroll(int index_x, int index_y)
	{	
		boolean scrolled = false;
		
		//X
		if (index_x > top_left_x + tiles_on_screen_x * 0.8)
		{
			// don't scroll beyond bounds
			if (top_left_x + 1 < map_size - tiles_on_screen_x)
			{
				top_left_x++; 
				taxCollector.x += tile_size / 2;
				scrolled = true;
			}
			else if (index_x + 1 >= map_size) // only walk up to edge of map
			{taxCollector.x -= taxCollector.speed;}
		}
		
		else if (index_x < top_left_x + tiles_on_screen_x * 0.2)
		{
			if (top_left_x - 1 >= 0)
			{
				top_left_x--; 
				taxCollector.x -= tile_size / 2;
				scrolled = true;
			}
			else if (index_x - 1 <= 0)
			{taxCollector.x += taxCollector.speed;}
		}
		
		//Y
		if (index_y > top_left_y + tiles_on_screen_y * 0.8)
		{
			if (top_left_y + 1 < map_size - tiles_on_screen_y)
			{
				top_left_y++; 
				taxCollector.y += tile_size / 2;
				scrolled = true;
			}
			else if (index_y + 2 >= map_size)
			{taxCollector.y -= taxCollector.speed;}
		}
		
		else if (index_y < top_left_y + tiles_on_screen_y * 0.2)
		{
			if (top_left_y - 1 >= 0)
			{
				top_left_y--; 
				taxCollector.y -= tile_size / 2;
				scrolled = true;
			}
			else if (index_y - 1 <= 0)
			{taxCollector.y += taxCollector.speed;}
		}
	
		if (scrolled) {panel.updateMapReferences();}
	}
	
	// COMMUNICATION
	
	public TaxCollector getTaxCollector() {return taxCollector;}
	
	public int getTileSize() {return tile_size;}
	
	public int getTopLeftX() {return top_left_x;}
	
	public int getTopLeftY() {return top_left_y;}
	
	public ArrayList<House> getAllHousesOnScreen()
	{
		ArrayList<House> houses = new ArrayList<>();
		
		for (int i = top_left_x; i <= top_left_x + tiles_on_screen_x; i++)
		{
			for (int j = top_left_y; j <= top_left_y + tiles_on_screen_y; j++)
			{
				if (map[i][j] instanceof House) {houses.add((House) map[i][j]);}
			}
		}
		
		return houses;
	}
}