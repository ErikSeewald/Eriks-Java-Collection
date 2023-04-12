package infdungeons;

import java.util.Random;

public class GameHandler 
{
	protected Player player;
	private Random random;
	
	GameHandler()
	{
		random = new Random();
		
		player = new Player();
		player.setRoom(new Room(random));
	}
}
