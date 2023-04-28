package infdungeons.player;

public class Bomb 
{
	public int x, y;
	public int timer;
	public int size;
	public int dmg_radius;
	public static final int dmg = 8;

	private int explosion_animation;
	public boolean isExploding = false;
	
	Bomb(int x, int y, int size)
	{
		this.x = x;
		this.y = y;
		this.size = size;
		this.dmg_radius = this.size * 8;
		this.explosion_animation = -1;
		
		this.timer = 0;
	}
	
	public void startTimer()
	{
		this.timer = 35;
	}
	
	public static final boolean reached_zero = true;
	public static final boolean not_zero = false;
	
	public void countDown()
	{
		this.timer--;
		if (this.timer <= 0) 
		{
			this.explosion_animation = 8;
			this.isExploding = true;
		}
	}

	/**
	 * Counts down the bombs animation timer and returns whether
	 * timer has reached zero. Discard the Bomb object afterwards.
	 */	
	public boolean hasExplosionFinished()
	{
		if (!this.isExploding) {return false;}
		
		this.explosion_animation--;
		return this.explosion_animation <= 0;
	}
}