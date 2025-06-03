package randBattle;

import java.util.Random;

public class Fighter 
{
	public boolean isAlive = true;
	public int damageFrames = 0;
	
	double x,y;
	double projectile_x, projectile_y;
	int goalX;
	int goalY;
	
	private Fighter target;
	private double[] shootVec = new double[2];
	private double[] moveVec = new double[2];
	
	double moveSpeed;
	double projectileSpeed;
	int damage;
	int health;
	int startingHealth;
	
	private Random random;
	
	public Fighter(Random random)
	{this.random = random;}
	
	public void move()
	{	
		if (x > goalX-10 && x < goalX+10 && y > goalY-10 && y < goalY+10) 
		{updateGoal();}
		
		x+= moveVec[0];
		y+= moveVec[1];
		
		if (damageFrames > 0) {damageFrames--;}
	}
	
	private void setMoveVect()
	{
		moveVec[0] = goalX - x;
		moveVec[1] = goalY - y;
		
		normalizeByFactor(moveVec, moveSpeed);
	}
	
	public void shoot()
	{
		projectile_x+= shootVec[0];
		projectile_y+= shootVec[1];
		
		if (projectile_x > RB_Panel.PANEL_WIDTH || projectile_x < 0 || projectile_y > RB_Panel.PANEL_HEIGHT || projectile_y < 0) 
		{resetProjectile();}
	}
	
	public void resetProjectile()
	{
		projectile_x = x;
		projectile_y = y;
		setShootVect();
	}
	
	private void setShootVect()
	{
		shootVec[0] = target.x - projectile_x;
		shootVec[1] = target.y - projectile_y;
	
		normalizeByFactor(shootVec, projectileSpeed);
	}
	
	private void normalizeByFactor(double[] vec, double factor)
	{
		double movelength = Math.sqrt(Math.pow(vec[0], 2)+Math.pow(vec[1], 2));
		vec[0] /= movelength/factor; 
		vec[1] /= movelength/factor;
	}
	
	public void takeDamage(int DAMAGE)
	{	
		health -= DAMAGE;
		if (health < 1) {isAlive = false;}
		
		damageFrames = 10;
	}
	
	public void updateGoal()
	{
		goalX = random.nextInt(RB_Panel.PANEL_WIDTH);
		goalY = random.nextInt(RB_Panel.PANEL_HEIGHT);

		setMoveVect();
	}
	
	//GET-SET
	public Fighter getTarget()
	{return target;}
	
	public void setTarget(Fighter target)
	{
		this.target = target;
		projectile_x = x;
		projectile_y = y;
		setShootVect();
	}
	
	public int getSize()
	{
		return this.startingHealth / 10;
	}
}