package taxCollector;

import java.util.ArrayList;
import java.util.Random;

import Main.EJC_Util;
import Main.EJC_Util.Direction;
import taxCollector.mapItem.House;
import taxCollector.mapItem.IRS;
import taxCollector.mapItem.Lake;
import taxCollector.mapItem.MapItem;
import taxCollector.mapItem.Road.RoadRail;

public class MapHandler 
{	
	//MAP
	private MapItem[][] map;
	
	public static final int map_size = 1000; // index value
	private int tile_size; // pixel value
	
	public static final int tiles_on_screen_x = 75, tiles_on_screen_y = 50;
	private int top_left_x, top_left_y; // indices of where the screen is on the map
	
	//CARS
	private Car[] cars;
	public static final int car_count = 500;
	
	//OTHERS
	private TaxCollector taxCollector; // specific pointer, independent of map but still bound to a grid position
	private IRS irs; // both map item and specific pointer
	private TC_Panel panel;
	private Random random;
	
	MapHandler(TC_Panel panel)
	{
		this.panel = panel;
		this.random = new Random();
		
		cars = new Car[car_count];
		for (int i = 0; i < car_count; i++)
		{cars[i] = new Car(0, 0, Direction.NORTH);}
		
		this.irs = new IRS(0, 0);
		
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
		
		MapGenerator.generateMap(map, top_left_x, top_left_x, random, irs, cars);
		taxCollector = new TaxCollector(irs.i, irs.j, (int) (tile_size * 1.5));
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
		
		//CARS
		Direction cur_dir;
		Direction[] dirs = Direction.values();
		for (Car car : cars)
		{	
			// Start at random direction, then loop over directions, if one is valid, go there
			// -> random start index ensures that cars do not always turn in the same direction
			int dir_index = random.nextInt(4);
			
			for (int i = dir_index; i < dir_index + 4; i++)
			{
				cur_dir = dirs[i % 4];
				if (!directionValid(cur_dir, car)) {continue;}
				
				if (EJC_Util.isPerpendicular(car.getDirection(), cur_dir))
				{car.changeDirection(cur_dir); break;}
			}
			
			car.move();
			
			//RESPAWN
			if (car.i <= 0 || car.i >= map_size - 1 || car.j <= 0 || car.j >= map_size - 1)
			{car.i = car.start_i; car.j = car.start_j; car.changeDirection(car.start_direction);;}
			
			//HIT TAXCOLLECTOR?
			hitDetect(car);
		}
	}
	
	private void hitDetect(Car car)
	{
			if (taxCollector.i >= car.i &&  taxCollector.i <= car.i + car.getSizeTilesX()
				&& taxCollector.j >= car.j &&  taxCollector.j <= car.j + car.getSizeTilesY())
			{	
				//KNOCKBACK
				if (!taxCollector.damageAnimation())
				{
					int x = taxCollector.i, y = taxCollector.j;
					switch (car.getDirection())
					{
						case NORTH: y -= 2; break;
						case SOUTH: y += 2; break;
						case EAST: x += 2; break;
						case WEST: x -= 2; break;
					}
					if (x > 0 && x < map_size - 1 && y > 0 && y < map_size - 1)
					{
						taxCollector.i = x;
						taxCollector.j = y;
					}
				}
				
				taxCollector.getHit();
			}
	}
	
	private boolean directionValid(Direction direction, Car car)
	{
		if (direction == Direction.NORTH)
		{return map[car.i][car.j - 1] instanceof RoadRail;}
		
		else if (direction == Direction.SOUTH)
		{return map[car.i][car.j + 1] instanceof RoadRail;}
		
		else if (direction == Direction.EAST)
		{return map[car.i + 1][car.j] instanceof RoadRail;}
		
		return map[car.i - 1][car.j] instanceof RoadRail;
	}
	
	public void interaction()
	{	
		for (int i = taxCollector.i - TaxCollector.collect_tile_range; i <= taxCollector.i + TaxCollector.collect_tile_range; i++)
		{
			for (int j = taxCollector.j - TaxCollector.collect_tile_range; j <= taxCollector.j + TaxCollector.collect_tile_range; j++)
			{
				if (i < 0 || i >= MapHandler.map_size || j < 0 || j >= MapHandler.map_size) {continue;}
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
	
	public ArrayList<Car> getAllCarsOnScreen()
	{
		ArrayList<Car> c = new ArrayList<>();
		
		for (Car car : cars)
		{
			if (car.i >= top_left_x - 3 && car.i <= top_left_x + tiles_on_screen_x + 3
					&& car.j >= top_left_y - 3 && car.j <= top_left_y + tiles_on_screen_y + 3)
			{c.add(car);}
		}
		
		return c;
	}
	
	public void setSeed(long seed)
	{random.setSeed(seed);}
	
	public int getTileSize() {return tile_size;}
	
	public int getTopLeftX() {return top_left_x;}
	
	public int getTopLeftY() {return top_left_y;}
	
	public TaxCollector getTaxCollector() {return taxCollector;}
	
	public IRS getIRS() {return irs;}
}