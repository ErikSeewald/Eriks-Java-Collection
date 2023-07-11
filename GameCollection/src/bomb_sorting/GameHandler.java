package bomb_sorting;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.Timer;

public class GameHandler implements ActionListener
{
	//TIMERS
	private int score;
	private Timer scoreTimer;
	private Timer movementTimer;
	private int timeSinceExplosion;
	
	// SPAWNER COORDINATES
	private int spawner;
	
	// PLATES
	private int plateBlackRight;
	private int plateBlackLeft;
	private int plateRedRight;
	private int plateRedLeft;
	private int plateY;
	private int plateHeight;
	
	// OTHERS
	private Random random;
	private Sort_Panel panel;
	ArrayList<Bomb> bombs;
	
	GameHandler(Sort_Panel panel)
	{
		this.panel = panel;
		bombs = new ArrayList<>();
		random = new Random();
		
		scoreTimer = new Timer(1000, this);
		movementTimer = new Timer(16, this);
	}
	
	public void start()
	{
		timeSinceExplosion = 0;
		score = 0;
		bombs.clear();
		System.gc();
		scoreTimer.start();
		movementTimer.start();
	}
	
	public void stop()
	{scoreTimer.stop(); scoreTimer = null; movementTimer.stop(); movementTimer = null; bombs.clear(); bombs = null;}
	
	public int getScore() {return score;}
	
	public void setSpawnerCoordinates(int coords)
	{this.spawner = coords;}
	
	public void setPlateCoordinates(int red_x, int black_x, int y, int width, int height)
	{
		plateRedLeft = red_x;
		plateBlackLeft = black_x;
		plateY = y;
		plateHeight = height;
		plateRedRight = red_x + width;
		plateBlackRight = black_x + width;
	}
	
	private void addBomb()
	{
		byte type = (byte) (random.nextInt(Bomb.RED) + Bomb.BLACK);
		Bomb bomb;
				
		do
		{
			bomb = new Bomb(type, 
					random.nextInt(panel.getWidth() - (Bomb.size * 2)) + Bomb.size, 
					random.nextInt(spawner * 2) + spawner);
		}
		while (checkIfSorted(bomb) != Bomb.not_sorted);

		bombs.add(bomb);
	}

	private void bombTimerCheck()
	{
		score = 0;
		
		for (Bomb bomb : bombs)
		{
			bomb.sortState = checkIfSorted(bomb);
			if (bomb.sortState == Bomb.sorted)
			{score++; continue;}
			
			if (bomb.sortState == Bomb.sorted_incorrectly)
			{explosionEvent(bomb); return;}
			
			bomb.timer--;
			if (bomb.timer < 1) {explosionEvent(bomb); return;}
		}
	}
	
	/**
	 * Returns one of three possible sorting states
	 * @param bomb the bomb to be checked
	 * @return
	 * <ul>
	 * <li>{@link Bomb#not_sorted}</li>
	 * <li>{@link Bomb#sorted}</li>
	 * <li>{@link Bomb#sorted_incorrectly}</li>
	 * </ul>
	 */
	public byte checkIfSorted(Bomb bomb)
	{
		if (bomb == null) {return Bomb.not_sorted;}
		
		// CHECK FOR Y
		if (!(bomb.y > plateY && bomb.y < plateY + plateHeight) || bomb.isHeld)
		{return Bomb.not_sorted;}
		
		// CHECK FOR X
		if (bomb.type == Bomb.RED)
		{
			if (bomb.x > plateRedLeft && bomb.x < plateRedRight) 
			{return Bomb.sorted;}
			
			else if (bomb.x > plateBlackLeft && bomb.x < plateBlackRight)
			{return Bomb.sorted_incorrectly;}
		}
		
		else
		{
			if (bomb.x > plateBlackLeft && bomb.x < plateBlackRight) 
			{return Bomb.sorted;}
			
			else if (bomb.x > plateRedLeft && bomb.x < plateRedRight)
			{return Bomb.sorted_incorrectly;}
		}
		
		return Bomb.not_sorted;
	}
	
	public void explosionEvent(Bomb bomb)
	{
		movementTimer.stop();
		
		bombs.removeIf(b -> b != bomb); // remove all bombs except for the exploding one
		score = 0;
		panel.repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource() == movementTimer)
		{
			bombs.forEach(bomb -> bomb.move(random));
			panel.repaint();
		}
		
		else if (e.getSource() == scoreTimer)
		{
			addBomb();
			bombTimerCheck();
			
			// restart after explosion event
			if (!movementTimer.isRunning())
			{
				timeSinceExplosion++;
				if (timeSinceExplosion > 2)
				{this.start();}
			}
		}
	}
}