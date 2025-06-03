package infdungeons.player;

public class Bomb 
{
	public int x, y;
	public int timer;
	public int size;
	public int dmgRadius;
	public static final int dmg = 8;

	private int explosionAnimation;
	public boolean isExploding = false;
	
	Bomb(int x, int y, int size)
	{
		this.x = x;
		this.y = y;
		this.size = size;
		this.dmgRadius = this.size * 8;
		this.explosionAnimation = -1;
		
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
			this.explosionAnimation = 8;
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
		
		this.explosionAnimation--;
		return this.explosionAnimation <= 0;
	}
}