package taxCollector;

import java.util.ArrayList;
import java.util.Random;

import ejcMain.util.EJC_Util;
import ejcMain.util.EJC_Util.Direction;
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
	private int tileSize; // pixel value
	
	public static final int tiles_on_screen_x = 75, tiles_on_screen_y = 50;
	private int topLeftX, topLeftY; // indices of where the screen is on the map
	
	//CARS
	private Car[] cars;
	public static final int car_count = 500;
	
	//OTHERS
	private TaxCollector taxCollector; // specific pointer, independent of map but still bound to a grid position
	private IRS irs; // both map item and specific pointer
	private TC_Panel panel;
	private Random random;
	private long seed;
	private boolean debug;
	
	MapHandler(TC_Panel panel)
	{
		this.panel = panel;
		this.random = new Random();
		
		cars = new Car[car_count];
		for (int i = 0; i < car_count; i++)
		{cars[i] = new Car(0, 0, Direction.NORTH);}
		
		this.irs = new IRS(0, 0);
		
		this.seed = random.nextLong();
		random.setSeed(seed);
		
		this.initMainValues();
		this.generateMap();
	}
	
	//------MAP GENERATION------
	
	private void initMainValues()
	{
		tileSize = 700 / 50; //700 == PANEL_HEIGHT
		topLeftX = topLeftY = map_size / 2;
	}
	
	public void generateMap()
	{
		map = new MapItem[map_size][map_size];
		System.gc();
		
		MapGenerator.generateMap(map, topLeftX, topLeftX, random, irs, cars);
		taxCollector = new TaxCollector(irs.i, irs.j, (int) (tileSize * 1.5));
	}
	
	//------GAMEPLAY------
	
	public static class ResetActions
	{
		public static final boolean newSeed = true;
		public static final boolean keepSeed = false;
	}
	
	public void reset(boolean newSeed)
	{
		if (newSeed)
		{
			this.seed = random.nextLong();
			random.setSeed(seed);
		}
		
		this.initMainValues();
		this.generateMap();
		panel.updateMapReferences();
	}
	
	public void update()
	{	
		taxCollector.update();
		
		//CARS
		Direction cur_dir;
		Direction[] dirs = Direction.values();
		for (Car car : cars)
		{	
			// Start at random direction, then loop over directions, if one is valid, go there
			// -> random start index ensures that cars do not always turn in the same direction
			// and do not always keep going forward
			int dir_index = random.nextInt(4);
			
			// CASES: TURN RIGHT, TURN LEFT, DO NOTHING AND THEREBY GO FORWARD
			for (int i = dir_index; i < dir_index + 4; i++)
			{
				cur_dir = dirs[i % 4];
				if (!directionValid(cur_dir, car)) {continue;}
				
				if (EJC_Util.isPerpendicular(car.getDirection(), cur_dir))
				{car.changeDirection(cur_dir); break;}
			}
			
			car.move();
			
			//RESPAWN IF CAR EXITS MAP
			if (car.i <= 0 || car.i >= map_size - 1 || car.j <= 0 || car.j >= map_size - 1)
			{car.respawn();}
			
			//HIT TAXCOLLECTOR?
			hitDetect(car);
		}
		
		if (debug) {return;}
		
		//MAP ITEMS
		for (int i = 0; i < map_size; i++)
		{
			for (int j = 0; j < map_size; j++)
			{
				if (map[i][j] != null) {map[i][j].update();}
			}
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
		if (index_x > topLeftX + tiles_on_screen_x * 0.8)
		{
			// don't scroll beyond bounds
			if (topLeftX + 1 < map_size - tiles_on_screen_x)
			{
				topLeftX++; 
				scrolled = true;
			}
		}
		
		else if (index_x < topLeftX + tiles_on_screen_x * 0.2)
		{
			if (topLeftX - 1 >= 0)
			{
				topLeftX--; 
				scrolled = true;
			}
		}
		
		//Y
		if (index_y > topLeftY + tiles_on_screen_y * 0.8)
		{
			if (topLeftY + 1 < map_size - tiles_on_screen_y)
			{
				topLeftY++; 
				scrolled = true;
			}
		}
		
		else if (index_y < topLeftY + tiles_on_screen_y * 0.2)
		{
			if (topLeftY - 1 >= 0)
			{
				topLeftY--; 
				scrolled = true;
			}
		}
	
		if (scrolled) {panel.updateMapReferences();}
	}
	
	// COMMUNICATION
	public ArrayList<MapItem> getAllMapItemsOnScreen()
	{
		ArrayList<MapItem> items = new ArrayList<>();
		
		for (int i = topLeftX; i <= topLeftX + tiles_on_screen_x; i++)
		{
			for (int j = topLeftY; j <= topLeftY + tiles_on_screen_y; j++)
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
			if (car.i >= topLeftX - 3 && car.i <= topLeftX + tiles_on_screen_x + 3
					&& car.j >= topLeftY - 3 && car.j <= topLeftY + tiles_on_screen_y + 3)
			{c.add(car);}
		}
		
		return c;
	}
	
	public void setSeed(long seed)
	{random.setSeed(seed); this.seed = seed;}
	
	public long getSeed() {return this.seed;}
	
	public int getTileSize() {return tileSize;}
	
	public int getTopLeftX() {return topLeftX;}
	
	public int getTopLeftY() {return topLeftY;}
	
	public TaxCollector getTaxCollector() {return taxCollector;}
	
	public IRS getIRS() {return irs;}
	
	public void stop() 
	{map = null; cars = null;}
	
	public void switchDebugBool() {debug = !debug;}
}