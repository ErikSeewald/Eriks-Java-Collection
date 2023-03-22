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
	private Timer score_timer;
	private Timer movement_timer;
	private int time_since_explosion;
	
	// SPAWNER COORDINATES
	private int spawner;
	
	// PLATES
	private int plate_black_right;
	private int plate_black_left;
	private int plate_red_right;
	private int plate_red_left;
	private int plate_y;
	private int plate_height;
	
	// OTHERS
	private Random random;
	private Sort_Panel panel;
	ArrayList<Bomb> bombs;
	
	GameHandler(Sort_Panel panel)
	{
		this.panel = panel;
		bombs = new ArrayList<>();
		random = new Random();
		
		score_timer = new Timer(1000, this);
		movement_timer = new Timer(16, this);
	}
	
	public void start()
	{
		time_since_explosion = 0;
		score = 0;
		bombs.clear();
		System.gc();
		score_timer.start();
		movement_timer.start();
	}
	
	public void stop()
	{score_timer.stop(); score_timer = null; movement_timer.stop(); movement_timer = null; bombs.clear(); bombs = null;}
	
	public int getScore() {return score;}
	
	public void setSpawnerCoordinates(int coords)
	{this.spawner = coords;}
	
	public void setPlateCoordinates(int red_x, int black_x, int y, int width, int height)
	{
		plate_red_left = red_x;
		plate_black_left = black_x;
		plate_y = y;
		plate_height = height;
		plate_red_right = red_x + width;
		plate_black_right = black_x + width;
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
		
		for (Bomb bomb: bombs)
		{
			bomb.sort_state = checkIfSorted(bomb);
			if (bomb.sort_state == Bomb.sorted)
			{score++; continue;}
			
			if (bomb.sort_state == Bomb.sorted_incorrectly)
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
		if (!(bomb.y > plate_y && bomb.y < plate_y + plate_height) || bomb.isHeld)
		{return Bomb.not_sorted;}
		
		// CHECK FOR X
		if (bomb.type == Bomb.RED)
		{
			if (bomb.x > plate_red_left && bomb.x < plate_red_right) 
			{return Bomb.sorted;}
			
			else if (bomb.x > plate_black_left && bomb.x < plate_black_right)
			{return Bomb.sorted_incorrectly;}
		}
		
		else
		{
			if (bomb.x > plate_black_left && bomb.x < plate_black_right) 
			{return Bomb.sorted;}
			
			else if (bomb.x > plate_red_left && bomb.x < plate_red_right)
			{return Bomb.sorted_incorrectly;}
		}
		
		return Bomb.not_sorted;
	}
	
	public void explosionEvent(Bomb bomb)
	{
		movement_timer.stop();
		
		bombs.removeIf(b -> b != bomb); // remove all bombs except for the exploding one
		score = 0;
		panel.repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource() == movement_timer)
		{
			bombs.forEach(bomb -> bomb.move(random));
			panel.repaint();
		}
		
		else if (e.getSource() == score_timer)
		{
			addBomb();
			bombTimerCheck();
			
			// restart after explosion event
			if (!movement_timer.isRunning())
			{
				time_since_explosion++;
				if (time_since_explosion > 2)
				{this.start();}
			}
		}
	}
}