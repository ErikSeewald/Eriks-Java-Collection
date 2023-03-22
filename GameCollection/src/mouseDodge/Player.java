package mouseDodge;

import java.awt.Color;

public class Player
{
	double X, Y;
	int size;
	final static Color color = new Color (220,50,50);
	
	private boolean moveAllowed;
	public ScoreHandler score;
	
	Player(int PANEL_SIZE)
	{this.initialize(PANEL_SIZE); score = new ScoreHandler(); this.moveAllowed = false;}
	
	public void initialize(int PANEL_SIZE)
	{
		this.X = PANEL_SIZE;
		this.Y = PANEL_SIZE/2;
		this.size = PANEL_SIZE/35;
		
		this.moveAllowed = true;
	}
	
	public boolean collisionCheck(Enemy[] enemies)
	{
		for (Enemy enemy : enemies)
		{
			if (insideEnemy(enemy.X, enemy.Y, enemy.size)) {return true;}
		}
		
		return false;
	}
	
	private boolean insideEnemy(double eX, double eY,  double eSize)
	{	
		return 
				(
						(this.X > eX &&  this.X < eX+eSize && this.Y > eY &&  this.Y < eY+eSize)
					||	(this.X+size > eX &&  this.X+size < eX+eSize && this.Y+size > eY &&  this.Y+size < eY+eSize)
					||	(this.X+size > eX &&  this.X+size < eX+eSize && this.Y > eY &&  this.Y < eY+eSize)
					||	(this.X > eX &&  this.X < eX+eSize && this.Y+size > eY &&  this.Y+size < eY+eSize)
				);
	}
	
	public boolean isMoveAllowed() {return this.moveAllowed;}
	
	public void setMoveAllowed(boolean allowed) {this.moveAllowed = allowed;}
}