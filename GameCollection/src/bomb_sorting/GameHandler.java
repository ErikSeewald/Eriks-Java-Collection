package bomb_sorting;

import java.util.ArrayList;
import java.util.Random;

public class GameHandler 
{
	ArrayList<Bomb> bombs;
	
	private Random random;
	private Sort_Panel panel;
	
	GameHandler(Sort_Panel panel)
	{
		this.panel = panel;
		bombs = new ArrayList<>();
		random = new Random();
		
		//REMOVE
		for (int i = 0; i < 10; i++) {addBomb();}
	}
	
	public void stop()
	{bombs = null;}
	
	private void addBomb()
	{
		byte type = (byte) (random.nextInt(Bomb.RED) + Bomb.BLACK);
		bombs.add(new Bomb(type, random.nextInt(700), random.nextInt(700)));
	}
	
	private void bombTimerCheck()
	{
		for (Bomb bomb: bombs)
		{
			if (bomb.isSorted) {continue;}
			
			bomb.timer--;
			if (bomb.timer < 1) {explosionEvent(bomb);}
		}
	}
	
	private void explosionEvent(Bomb bomb)
	{
		
	}
}
