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
	
	// SPAWNER
	private int spawner_x;
	private int spawner_y;
	
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
		score = 0;
		bombs.clear();
		System.gc();
		score_timer.start();
		movement_timer.start();
	}
	
	public void stop()
	{score_timer.stop(); score_timer = null; movement_timer.stop(); movement_timer = null; bombs.clear(); bombs = null;}
	
	public int getScore() {return score;}
	
	public void setSpawnerCoordinates(int x, int y)
	{this.spawner_x = x + Bomb.size * 2; this.spawner_y = y + Bomb.size * 2;}
	
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
		bombs.add(new Bomb(type, spawner_x, spawner_y));
	}
	
	private void bombTimerCheck()
	{
		for (Bomb bomb: bombs)
		{
			checkIfSorted(bomb);
			if (bomb.isSorted) {continue;}
			
			bomb.timer--;
			if (bomb.timer < 1) {explosionEvent(bomb);}
		}
	}
	
	private void checkIfSorted(Bomb bomb)
	{
		// CHECK FOR Y
		if (!(bomb.y > plate_y && bomb.y < plate_y + plate_height))
		{bomb.isSorted = false; return;}
		
		// CHECK FOR X
		if (bomb.type == Bomb.RED)
		{
			bomb.isSorted = bomb.x > plate_red_left && bomb.x < plate_red_right;
		}
		
		else
		{
			bomb.isSorted = bomb.x > plate_black_left && bomb.x < plate_black_right;
		}
	}
	
	private void explosionEvent(Bomb bomb)
	{
		
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
			bombTimerCheck();
			addBomb();
		}
	}
}