package taxCollector;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

import Main.EJC_Util;
import Main.EJC_Util.Direction;
import taxCollector.mapItem.House;
import taxCollector.mapItem.IRS;
import taxCollector.mapItem.Lake;
import taxCollector.mapItem.MapItem;
import taxCollector.mapItem.Tree;
import taxCollector.mapItem.MapItem.TempTile;
import taxCollector.mapItem.Road;

public class MapHandler 
{	
	//MAP
	private MapItem[][] map;
	
	private static final int map_size = 1000; // index value
	private int tile_size; // pixel value
	
	public static final int tiles_on_screen_x = 75, tiles_on_screen_y = 50;
	private int top_left_x, top_left_y; // indices of where the screen is on the map
	
	//DISTANCES TO BE KEPT WITHIN MAP GENERATION
	public static final int house_to_house_distance = 10;
	public static final int tree_to_house_distance = 4;
	
	//OTHERS
	private TaxCollector taxCollector; // specific pointer, independent of map but still bound to a grid position
	private IRS irs; // both map item and specific pointer
	private TC_Panel panel;
	private Random random;
	
	MapHandler(TC_Panel panel)
	{
		this.panel = panel;
		this.random = new Random();
		this.initMainValues();
		this.generateMap();
	}
	
	//------MAP GENERATION------
	
	private void initMainValues()
	{
		tile_size = 700 / 50; //700 == PANEL_HEIGHT
		top_left_x = top_left_y = map_size / 2;
	}
	
	public void generateMap()
	{
		map = new MapItem[map_size][map_size];
		System.gc();
		
		//ORDER OF THESE FUNCTION CALLS IS VERY IMPORTANT DUE TO FUNCTIONS 
		//ONLY ACCOUNTING FOR DISTANCE TO OBJECTS CREATED EARLIER
		generateRoads();
		placeIRS();
		generateLakes();
		generateHouses();
		generateTrees();
		
		taxCollector = new TaxCollector(irs.i, irs.j, (int) (tile_size * 1.5));
	}
	
	private void placeIRS()
	{
		int x = top_left_x + tiles_on_screen_x / 2, y = top_left_y + tiles_on_screen_y / 2;
		
		while (map[x][y] instanceof Lake || map[x][y] instanceof Road)
		{
			if (random.nextBoolean()) {x++;}
			else {y++;}
		}
		
		irs = new IRS(x, y);
		map[irs.i][irs.j] = irs;
	}
	
	//ROADS
	
	// road_iter_count times: pick a random corner on the edge of the map, then begin walking random distances
	// in a direction, stopping, turning to a perpendicular direction and repeat until another edge of the map is reached.
	// The path traced out by this forms the roads.
	private static final int road_iter_count = 15;
	
	private class RoadPoint
	{
		int x, y;
		Direction direction;
		
		RoadPoint(int x, int y, Direction direction)
		{this.x = x; this.y = y; this.direction = direction;}
	}
	
	private void generateRoads()
	{
		Direction corner_dir = Direction.NORTH;
		for (int i = 0; i < road_iter_count; i++)
		{
			RoadPoint p = pickRandomCornerPoint(corner_dir);
			moveRoadPointOneStep(p);
			corner_dir = EJC_Util.perpendicular(corner_dir); // go around the 4 corners clockwise
			
			while (p.x > 0 && p.x < map_size - 1 && p.y > 0 && p.y < map_size - 1)
			{
				p.direction = EJC_Util.perpendicular(p.direction);
				if (random.nextBoolean()) {p.direction = EJC_Util.reverse(p.direction);}
				
				moveRoadPointOneStep(p);
			}
		}
	}
	
	private RoadPoint pickRandomCornerPoint(Direction direction)
	{
		// Direction in which to go -> NORTH means we start at bottom corner
		int x = 0, y = 0;
		
		switch (direction)
		{
			case NORTH: x = random.nextInt(map_size - 10) + 10; y = map_size - 1; break;
			case SOUTH: x = random.nextInt(map_size - 10) + 10; y = 0; 		break;
			case EAST:  x = 0; y = random.nextInt(map_size -10) + 10; 		break;
			case WEST:  x = map_size - 1; y = random.nextInt(map_size -10) + 10; break;
		}
		
		return new RoadPoint(x, y, direction);
	}
	
	// One Step being a walk of variable length in one direction
	private static final int low_bound = map_size / 20, high_bound = low_bound * 5; 
	private static final int road_radius = 5; // -> width = 10
	private void moveRoadPointOneStep(RoadPoint p)
	{
		int distance = random.nextInt(high_bound - low_bound) + low_bound;
		int i = p.x, j = p.y;
		
		if (p.direction == Direction.NORTH)
		{
			while (j > p.y - distance)
			{
				if (j < 0) {break;}
				
				//expand horizontally
				for (int k = p.x - road_radius; k < p.x + road_radius; k++)
				{
					if (k < 0 || k >= map_size) {continue;}
					map[k][j] = new Road(k, j);
				}
				
				j--;
			}
		}
		
		else if (p.direction == Direction.SOUTH)
		{
			while (j < p.y + distance)
			{
				if (j >= map_size) {break;}
				
				//expand horizontally
				for (int k = p.x - road_radius; k < p.x + road_radius; k++)
				{
					if (k < 0 || k >= map_size) {continue;}
					map[k][j] = new Road(k, j);
				}
				
				j++;
			}
		}
		
		else if (p.direction == Direction.EAST)
		{
			while (i < p.x + distance)
			{
				if (i >= map_size) {break;}
				
				//expand vertically
				for (int k = p.y - road_radius; k < p.y + road_radius; k++)
				{
					if (k < 0 || k >= map_size) {continue;}
					map[i][k] = new Road(i, k);
				}
				
				i++;
			}
		}
		
		else if (p.direction == Direction.WEST)
		{
			while (i > p.x - distance)
			{
				if (i < 0) {break;}
				
				//expand vertically
				for (int k = p.y - road_radius; k < p.y + road_radius; k++)
				{
					if (k < 0 || k >= map_size) {continue;}
					map[i][k] = new Road(i, k);
				}
				
				i--;
			}
		}
		
		p.x = i;
		p.y = j;
	}
	
	//LAKES
	private void generateLakes()
	{
		// PLACE ORIGINS
		for (int i = 0; i < map_size; i++)
		{
			for (int j = 0; j < map_size; j++)
			{
				// do not spawn a lake too close to the irs
				if (i > irs.i - 30 && i < irs.i + 30 && j > irs.j - 30 && j < irs.j + 30)
				{continue;}
				
				if (map[i][j] != null) {continue;}
				if (random.nextInt(4000) == 1)
				{expandLake(i,j);}
			}
		}
		
		// OVERDRAW SINGLE LAKE TILES WITH LARGER STAMP PATTERS FOR CONINUITY
		for (int i = 0; i < map_size; i++)
		{
			for (int j = 0; j < map_size; j++)
			{
				if (map[i][j] instanceof Lake) 
				{placeLakeStamp(i, j);}
			}
		}
		
		// FILL IN TEMPTILES
		for (int i = 0; i < map_size; i++)
		{
			for (int j = 0; j < map_size; j++)
			{
				if (map[i][j] instanceof TempTile) 
				{map[i][j] = new Lake(i, j);}
			}
		}
		System.gc();
	}
	
	private static final int lake_radius = 10;
	private void expandLake(int i, int j)
	{
		map[i][j] = new Lake(i, j); // origin
		
		for (int x = i - lake_radius; x < i + lake_radius; x++)
		{
			for (int y = j - lake_radius; y < j + lake_radius; y++)
			{
				if (x < 0 || x >= map_size || y < 0 || y >= map_size) {continue;}
				if (map[x][y] != null) {continue;}
				
				// Decrease probability of placing lake the further you are away from the origin
				int dist = Math.abs(x - i) + Math.abs(y - j);
				if (random.nextInt(dist * dist) == 1)
				{map[x][y] = new Lake(x, y);}
			}
		}
	}
	
	private void placeLakeStamp(int i, int j)
	{
		for (int x = i - 4; x < i + 4; x++)
		{
			for (int y = j - 5; y < j + 5; y++)
			{
				if (x < 0 || x >= map_size || y < 0 || y >= map_size) {continue;}
				if (map[x][y] != null) {continue;}
				map[x][y] = new TempTile(x, y);
				
				// TempTile so we do not flood the world by having stamps be seen as lake origins
				// -> change TempTiles to Lakes later
			}
		}
	}
	
	//HOUSES
	private void generateHouses()
	{
		for (int i = 0; i < map_size; i++)
		{
			for (int j = 0; j < map_size; j++)
			{
				if (map[i][j] != null) {continue;}
				if (random.nextInt(1000) == 1)
				{
					if (nothingElseInDistance(i, j, house_to_house_distance) && clearOfRoads(i, j))
					{map[i][j] = new House(i, j, random);}
				}
			}
		}
	}
	
	private boolean nothingElseInDistance(int i, int j, int distance)
	{
		for (int x = i - distance; x < i + distance; x++)
		{
			for (int y = j - distance; y < j + distance; y++)
			{
				if (x < 0 || x >= map_size || y < 0 || y >= map_size) {continue;}
				if (map[x][y] instanceof House || map[x][y] instanceof IRS || map[x][y] instanceof Lake) 
				{return false;}
			}
		}
		return true;
	}
	
	private boolean clearOfRoads(int i, int j)
	{
		for (int x = i - 5; x < i + 5; x++)
		{
			for (int y = j - 5; y < j + 5; y++)
			{
				if (x < 0 || x >= map_size || y < 0 || y >= map_size) {continue;}
				if (map[x][y] instanceof Road) 
				{return false;}
			}
		}
		return true;
	}
	
	//TREES
	private void generateTrees()
	{
		for (int i = 0; i < map_size; i++)
		{
			for (int j = 0; j < map_size; j++)
			{			
				if (map[i][j] != null) {continue;}
				if (random.nextInt(100) == 1)
				{	
					// only check for houses, trees are allowed to be right next to other trees
					if (nothingElseInDistance(i, j, tree_to_house_distance) && clearOfRoads(i, j))
					{map[i][j] = new Tree(i, j, random);}
				}
			}
		}
	}
	
	//------GAMEPLAY------
	
	public void reset()
	{
		this.initMainValues();
		this.generateMap();
		panel.updateMapReferences();
	}
	
	public void update()
	{	
		taxCollector.update();
		
		//MAP ITEMS
		for (int i = 0; i < map_size; i++)
		{
			for (int j = 0; j < map_size; j++)
			{
				if (map[i][j] != null) {map[i][j].update();}
			}
		}
	}
	
	public void interaction()
	{	
		for (int i = taxCollector.i - TaxCollector.collect_tile_range; i <= taxCollector.i + TaxCollector.collect_tile_range; i++)
		{
			for (int j = taxCollector.j - TaxCollector.collect_tile_range; j <= taxCollector.j + TaxCollector.collect_tile_range; j++)
			{
				if (map[i][j] == null) {continue;}
				
				if (map[i][j] instanceof House) {taxCollector.collect((House) map[i][j]);}
				else if (map[i][j] instanceof IRS) {irs.addFunds(taxCollector.emptyCollected());}
			}
		}
	}
	
	public void moveTaxCollector(Direction direction)
	{	
		int i = taxCollector.i, j = taxCollector.j;
		
		switch (direction)
		{
			case EAST: i++; break;
			case SOUTH: j++; break;
			case WEST: i--; break;
			case NORTH: j--; break;
		}
			
		if (i >= 0 && i < map_size && j >= 0 && j < map_size) 
		{
			// COLLISION
			if (map[i][j] instanceof Lake) {return;}
			
			taxCollector.i = i;
			taxCollector.j = j;
			this.scroll(i, j);	
		}
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
				scrolled = true;
			}
		}
		
		else if (index_x < top_left_x + tiles_on_screen_x * 0.2)
		{
			if (top_left_x - 1 >= 0)
			{
				top_left_x--; 
				scrolled = true;
			}
		}
		
		//Y
		if (index_y > top_left_y + tiles_on_screen_y * 0.8)
		{
			if (top_left_y + 1 < map_size - tiles_on_screen_y)
			{
				top_left_y++; 
				scrolled = true;
			}
		}
		
		else if (index_y < top_left_y + tiles_on_screen_y * 0.2)
		{
			if (top_left_y - 1 >= 0)
			{
				top_left_y--; 
				scrolled = true;
			}
		}
	
		if (scrolled) {panel.updateMapReferences();}
	}
	
	// COMMUNICATION
	public ArrayList<MapItem> getAllMapItemsOnScreen()
	{
		ArrayList<MapItem> items = new ArrayList<>();
		
		for (int i = top_left_x; i <= top_left_x + tiles_on_screen_x; i++)
		{
			for (int j = top_left_y; j <= top_left_y + tiles_on_screen_y; j++)
			{
				if (map[i][j] != null) {items.add(map[i][j]);}
			}
		}
		
		return items;
	}
	
	public void setSeed(long seed)
	{random.setSeed(seed);}
	
	public int getTileSize() {return tile_size;}
	
	public int getTopLeftX() {return top_left_x;}
	
	public int getTopLeftY() {return top_left_y;}
	
	public TaxCollector getTaxCollector() {return taxCollector;}
	
	public IRS getIRS() {return irs;}
}