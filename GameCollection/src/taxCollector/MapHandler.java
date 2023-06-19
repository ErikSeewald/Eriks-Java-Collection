package taxCollector;

public class MapHandler 
{
	private int[][] map;
	
	private TaxCollector taxCollector;
	private TC_Panel panel;
	
	private int tile_size;
	
	public static final int tiles_on_screen_x = 75, tiles_on_screen_y = 50;
	private int top_left_x, top_left_y; // indices
	
	public static enum Directions
	{
		EAST, SOUTH, WEST, NORTH;
	}
	
	MapHandler(TC_Panel panel, int PANEL_HEIGHT, int PANEL_WIDTH)
	{
		this.panel = panel;
		
		map = new int[1000][1000];
		
		top_left_x = top_left_y = 0;
		tile_size = PANEL_HEIGHT / 50; //cannot use panel.getHeight() yet as we are still instantiating
		taxCollector = new TaxCollector(PANEL_HEIGHT / 2, PANEL_WIDTH / 2, (int) (tile_size * 1.5));
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
		
		//MOVE SCREEN (scroll one tile at a time)
		int index_x = taxCollector.x / tile_size;
		int index_y = taxCollector.y / tile_size;
		
		if (index_x > top_left_x + tiles_on_screen_x * 0.8)
		{top_left_x++;}
		
		else if (index_x < top_left_x + tiles_on_screen_x * 0.2)
		{top_left_x--;}
		
		if (index_y > top_left_y + tiles_on_screen_y * 0.8)
		{top_left_y++;}
		
		else if (index_y < top_left_y + tiles_on_screen_y * 0.2)
		{top_left_y--;}
		
	}
}
