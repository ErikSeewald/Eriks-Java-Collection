package sidescroller;
import sidescroller.SidescrollerPanel.StartOperations;

public class Player
{
	int x, y;
	int slot;
	int airTime;
	
	boolean jumpAllowed = false;
	
	private SidescrollerPanel panel;
	private MapHandler mapHandler;
	
	Player(SidescrollerPanel panel, MapHandler mapHandler){this.panel = panel; this.mapHandler = mapHandler; this.spawn();}
	
	public void spawn()
	{
		this.x = 49; this.y = 30;
		this.slot = 0;
		this.airTime = 0;
		
		this.jumpAllowed = false;
	}
	
	public void move(int x, int y)
	{	
		//X
		this.x+= x;
		Element element = mapHandler.xCheck(this.x, this.y);
		if (element != null) 
		{
			if (x > 0) {this.x = element.x - Layouts.cubeSize - 1;}
			else {this.x = element.x + Layouts.cubeSize - 1;}
		}

		//Y
		this.y+=y;
		int[] hitbox = mapHandler.yCheck(this.x, this.y);
		if (hitbox != null)
		{
			if (y > 0) 
			{this.y = hitbox[0] - Layouts.cubeSize;}
			else 
			{this.y = hitbox[0] + hitbox[1];}

			this.jumpAllowed = true;
		}
		else {this.jumpAllowed = false;}

		//DEATH CHECK
		if (this.y >= SidescrollerPanel.PANEL_HEIGHT-Layouts.cubeSize) {panel.start(StartOperations.restart);}

		//UPDATE
		this.slot = this.x / Layouts.cubeSize;
	}
}