package taxCollector;

import java.util.Random;
import Main.EJC_Util;
import Main.EJC_Util.Direction;
import taxCollector.mapItem.*;
import taxCollector.mapItem.Road.RoadRail;

public class MapGenerator 
{
	private static int topLeftX, topLeftY;
	private static Random random;
	private static IRS irs;
	private static Car[] cars;
	
	//DISTANCES TO BE KEPT WITHIN MAP GENERATION
	public static final int house_to_house_distance = 10;
	public static final int tree_to_house_distance = 4;
	
	public static void generateMap(MapItem[][] map, int t_left_x, int t_left_y, Random r, IRS i, Car[] c)
	{
		topLeftX = t_left_x;
		topLeftY = t_left_y;
		random = r;
		irs = i;
		cars = c;
		
		//ORDER OF THESE FUNCTION CALLS IS VERY IMPORTANT DUE TO FUNCTIONS 
		//ONLY ACCOUNTING FOR DISTANCE TO OBJECTS CREATED EARLIER
		generateRoads(map);
		generateCars(map);
		placeIRS(map);
		generateLakes(map);
		generateHouses(map);
		generateTrees(map);
	}
	
	//ROADS

	// road_iter_count times: pick a random edge of the map, then begin walking random distances
	// in a direction, stopping, turning to a perpendicular direction and repeat until another edge of the map is reached.
	// The path traced out by this forms the roads.
	private static final int road_iter_count = 15;
	
	private static class RoadPoint
	{
		int x, y;
		Direction direction;
		
		RoadPoint(int x, int y, Direction direction)
		{this.x = x; this.y = y; this.direction = direction;}
	}
	
	private static void generateRoads(MapItem[][] map)
	{
		Direction corner_dir = Direction.NORTH;
		for (int i = 0; i < road_iter_count; i++)
		{
			corner_dir = EJC_Util.perpendicular(corner_dir); // go around the 4 corners clockwise
			RoadPoint p = pickRandomCornerPoint(corner_dir);
			moveRoadPointOneStep(p, map);
			
			// WALK RANDOMLY UNTIL WE REACH AN EDGE OF THE MAP
			while (p.x > 0 && p.x < MapHandler.map_size - 1 && p.y > 0 && p.y < MapHandler.map_size - 1)
			{
				p.direction = EJC_Util.perpendicular(p.direction);
				if (random.nextBoolean()) {p.direction = EJC_Util.reverse(p.direction);}
				
				moveRoadPointOneStep(p, map);
			}
		}
		
		//OVERWRITE ANY 2x2 ROADRAIL SQUARE CAUSED BY ROADS WITH 0 DISTANCE BETWEEN EACH OTHER
		//OTHERWISE CARS WILL GLITCH OUT
		for (int i = 4; i < MapHandler.map_size - 4; i++)
		{
			for (int j = 4; j < MapHandler.map_size - 4; j++)
			{
				if (map[i][j] instanceof RoadRail && map[i + 1][j] instanceof RoadRail
						&& map[i][j + 1] instanceof RoadRail && map[i + 1][j + 1] instanceof RoadRail)
				{map[i][j + 1] = new Road(i, j +1);}
			}
		}
	}
	
	private static RoadPoint pickRandomCornerPoint(Direction direction)
	{
		// Direction in which to go -> NORTH means we start at bottom corner
		int x = 0, y = 0;
		
		switch (direction)
		{
			case NORTH: x = random.nextInt(MapHandler.map_size - 10) + 10; y = MapHandler.map_size - 1; break;
			case SOUTH: x = random.nextInt(MapHandler.map_size - 10) + 10; y = 0; 		break;
			case EAST:  x = 0; y = random.nextInt(MapHandler.map_size -10) + 10; 		break;
			case WEST:  x = MapHandler.map_size - 1; y = random.nextInt(MapHandler.map_size -10) + 10; break;
		}
		
		return new RoadPoint(x, y, direction);
	}
	
	// One Step being a walk of variable length in one direction
	private static final int low_bound = MapHandler.map_size / 20, high_bound = low_bound * 5; 
	private static final int road_radius = 5; // -> width = 10
	private static void moveRoadPointOneStep(RoadPoint p, MapItem[][] map)
	{
		int distance = random.nextInt(high_bound - low_bound) + low_bound;
		int i = p.x, j = p.y;
		
		if (p.direction == Direction.NORTH)
		{
			while (j > p.y - distance)
			{
				if (j < 0) {break;}
				
				expandHorizontally(p.x, j, map);
				map[p.x][j] = new RoadRail(p.x, j);
				j--;
			}
		}
		
		else if (p.direction == Direction.SOUTH)
		{
			while (j < p.y + distance)
			{
				if (j >= MapHandler.map_size) {break;}
				
				expandHorizontally(p.x, j, map);
				map[p.x][j] = new RoadRail(p.x, j);
				j++;
			}
		}
		
		else if (p.direction == Direction.EAST)
		{
			while (i < p.x + distance)
			{
				if (i >= MapHandler.map_size) {break;}
				
				expandVertically(i, p.y, map);
				map[i][p.y] = new RoadRail(i, p.y);
				i++;
			}
		}
		
		else if (p.direction == Direction.WEST)
		{
			while (i > p.x - distance)
			{
				if (i < 0) {break;}
				
				expandVertically(i, p.y, map);
				map[i][p.y] = new RoadRail(i, p.y);
				i--;
			}
		}
		
		p.x = i;
		p.y = j;
	}
	
	private static void expandHorizontally(int i, int j, MapItem[][] map)
	{
		for (int k = i - road_radius; k < i + road_radius; k++)
		{
			if (k < 0 || k >= MapHandler.map_size || map[k][j] instanceof RoadRail) 
			{continue;}
			map[k][j] = new Road(k, j);
		}
	}
	
	private static void expandVertically(int i, int j, MapItem[][] map)
	{
		for (int k = j - road_radius; k < j + road_radius; k++)
		{
			if (k < 0 || k >= MapHandler.map_size || map[i][k] instanceof RoadRail) 
			{continue;}
			map[i][k] = new Road(i, k);
		}
	}
	
	//CARS
	private static void generateCars(MapItem[][] map) // does not actually "generate" - merely moves the existing ones to new positions
	{	
		for (Car car : cars)
		{
			while (true) // exited out of if a valid position has been found
			{
				//Find spawnpoint by finding a random position on the map, then walking from there until a road is hit
				car.i = random.nextInt(MapHandler.map_size);
				car.j = random.nextInt(MapHandler.map_size);
				
				int i_add = car.i < MapHandler.map_size / 2 ? 1 : -1;
				int j_add = car.j < MapHandler.map_size / 2 ? 1 : -1;
		
				while (car.i > 1 && car.i < MapHandler.map_size - 1 && car.j > 1 && car.j < MapHandler.map_size - 1)
				{
						car.i += i_add;
						car.j += j_add;
						if (map[car.i][car.j] instanceof RoadRail) {break;}
				}
				
				//ALIGN THE CARS STARTING DIRECTION WITH THE ROAD
				if ((car.i + 1 < MapHandler.map_size && map[car.i + 1][car.j] instanceof RoadRail)
						|| (car.i - 1 > 0 && map[car.i - 1][car.j] instanceof RoadRail)) // horizontal
				{
					if (car.i > MapHandler.map_size / 2) {car.changeDirection(Direction.WEST);}
					else {car.changeDirection(Direction.EAST);}
				}
				
				else if ((car.j + 1 < MapHandler.map_size && map[car.i][car.j + 1] instanceof RoadRail)
						|| (car.j - 1 > 0 && map[car.i][car.j - 1] instanceof RoadRail)) // vertical
				{
					if (car.j > MapHandler.map_size / 2) {car.changeDirection(Direction.NORTH);}
					else {car.changeDirection(Direction.SOUTH);}
				}
				
				car.setStartingValues();
				
				// IS POSITION VALID, THEN MOVE ON TO NEXT CAR, OTHERWISE CONTINUE LOOPING
				if (car.i > 10 && car.i < MapHandler.map_size - 10 && car.j > 10 && car.j < MapHandler.map_size - 10)
				{break;}
			}
		}
	}
	
	//IRS
	private static void placeIRS(MapItem[][] map)
	{
		int x = topLeftX + MapHandler.tiles_on_screen_x / 2, y = topLeftY + MapHandler.tiles_on_screen_y / 2;
		
		while (map[x][y] instanceof Lake || map[x][y] instanceof Road)
		{
			if (random.nextBoolean()) {x++;}
			else {y++;}
		}
		
		irs.reset(x, y);;
		map[irs.i][irs.j] = irs;
	}
	
	//LAKES
	// Place random origin points around the map, then place "stamp origins" around the lake origins
	// decreasing in spawn probability the further apart they are. Those "stamp origins" are then
	// overwritten by a 8x10 rectangle of lake tiles, a stamp. These stamps overlapping each other
	// create unique looking lake shapes.
	private static void generateLakes(MapItem[][] map)
	{
		// PLACE ORIGINS
		for (int i = 0; i < MapHandler.map_size; i++)
		{
			for (int j = 0; j < MapHandler.map_size; j++)
			{
				// do not spawn a lake too close to the irs
				if (i > irs.i - 30 && i < irs.i + 30 && j > irs.j - 30 && j < irs.j + 30)
				{continue;}
				
				if (map[i][j] != null) {continue;}
				if (random.nextInt(4000) == 1)
				{expandLake(i,j, map);}
			}
		}
	}
	
	private static final int lake_radius = 10;
	private static void expandLake(int i, int j, MapItem[][] map)
	{
		map[i][j] = new Lake(i, j); // origin
		
		for (int x = i - lake_radius; x < i + lake_radius; x++)
		{
			for (int y = j - lake_radius; y < j + lake_radius; y++)
			{
				if (x < 0 || x >= MapHandler.map_size || y < 0 || y >= MapHandler.map_size) {continue;}
				if (map[x][y] != null) {continue;}
				
				// Decrease probability of placing lake the further you are away from the origin
				int dist = Math.abs(x - i) + Math.abs(y - j);
				if (random.nextInt(dist * dist) == 1)
				{placeLakeStamp(x, y, map);}
			}
		}
	}
	
	private static void placeLakeStamp(int i, int j, MapItem[][] map)
	{
		for (int x = i - 4; x < i + 4; x++)
		{
			for (int y = j - 5; y < j + 5; y++)
			{
				if (x < 0 || x >= MapHandler.map_size || y < 0 || y >= MapHandler.map_size) {continue;}
				if (map[x][y] != null) {continue;}
				map[x][y] = new Lake(x, y);
			}
		}
	}
	
	//HOUSES
	private static void generateHouses(MapItem[][] map)
	{
		for (int i = 0; i < MapHandler.map_size; i++)
		{
			for (int j = 0; j < MapHandler.map_size; j++)
			{
				if (map[i][j] != null) {continue;}
				if (random.nextInt(500) == 1)
				{
					if (nothingElseInDistance(i, j, house_to_house_distance, map) && clearOfRoads(i, j, map))
					{map[i][j] = new House(i, j, random);}
				}
			}
		}
	}
	
	private static boolean nothingElseInDistance(int i, int j, int distance, MapItem[][] map)
	{
		for (int x = i - distance; x < i + distance; x++)
		{
			for (int y = j - distance; y < j + distance; y++)
			{
				if (x < 0 || x >= MapHandler.map_size || y < 0 || y >= MapHandler.map_size) {continue;}
				if (map[x][y] instanceof House || map[x][y] instanceof IRS || map[x][y] instanceof Lake) 
				{return false;}
			}
		}
		return true;
	}
	
	private static boolean clearOfRoads(int i, int j, MapItem[][] map)
	{
		for (int x = i - 5; x < i + 5; x++)
		{
			for (int y = j - 5; y < j + 5; y++)
			{
				if (x < 0 || x >= MapHandler.map_size || y < 0 || y >= MapHandler.map_size) {continue;}
				if (map[x][y] instanceof Road) 
				{return false;}
			}
		}
		return true;
	}
	
	//TREES
	private static void generateTrees(MapItem[][] map)
	{
		for (int i = 0; i < MapHandler.map_size; i++)
		{
			for (int j = 0; j < MapHandler.map_size; j++)
			{			
				if (map[i][j] != null) {continue;}
				if (random.nextInt(100) == 1)
				{	
					// only check for houses, trees are allowed to be right next to other trees
					if (nothingElseInDistance(i, j, tree_to_house_distance, map) && clearOfRoads(i, j, map))
					{map[i][j] = new Tree(i, j, random);}
				}
			}
		}
	}
}
