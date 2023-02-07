package pathfindGame;

public class Square 
{
	int hp = 3;
	boolean isAlive;
	
	Square(boolean isAlive)
	{this.isAlive = isAlive;}
	
	public void getHit()
	{
		hp--;
		if (hp <= 0)
		{isAlive = false;}
	}
}