package randBattle;
import java.util.Random;

public class NPC 
{
	public boolean isAlive = true;
	public int damageFrames = 0;
	
	double x,y;
	double PROJECTILE_X, PROJECTILE_Y;
	private int GOAL_X, GOAL_Y;
	
	private NPC TARGET;
	private double[] SHOOT_VECT = new double[2];
	private double[] MOVE_VECT = new double[2];
	
	int SIZE;
	double MOVE_SPEED;
	double PROJECTILE_SPEED;
	int DAMAGE;
	int HEALTH;
	int STARTING_HEALTH;
	
	private int PANEL_WIDTH, PANEL_HEIGHT;
	private Random random;
	
	NPC(int PANEL_WIDTH, int PANEL_HEIGHT, Random random)
	{
		this.PANEL_WIDTH = PANEL_WIDTH;
		this.PANEL_HEIGHT = PANEL_HEIGHT;
		this.random = random;
		initStats();
	}
	
	public void reset()
	{isAlive = true; initStats();}
	
	public void move()
	{	
		if (x > GOAL_X-10 && x < GOAL_X+10 && y > GOAL_Y-10 && y < GOAL_Y+10) 
		{newGoal();}
		
		x+= MOVE_VECT[0];
		y+= MOVE_VECT[1];
		
		if (damageFrames > 0) {damageFrames--;}
	}
	
	private void setMoveVect()
	{
		MOVE_VECT[0] = GOAL_X - x;
		MOVE_VECT[1] = GOAL_Y - y;
		
		normalizeByFactor(MOVE_VECT, MOVE_SPEED);
		
	}
	
	public void shoot()
	{
		PROJECTILE_X+= SHOOT_VECT[0];
		PROJECTILE_Y+= SHOOT_VECT[1];
		
		if (PROJECTILE_X > PANEL_WIDTH || PROJECTILE_X < 0 || PROJECTILE_Y > PANEL_HEIGHT || PROJECTILE_Y < 0) 
		{
			PROJECTILE_X = x;
			PROJECTILE_Y = y;
			setShootVect();
		}
	}
	
	private void setShootVect()
	{
		SHOOT_VECT[0] = TARGET.x - PROJECTILE_X;
		SHOOT_VECT[1] = TARGET.y - PROJECTILE_Y;
	
		normalizeByFactor(SHOOT_VECT, PROJECTILE_SPEED);
	}
	
	private void normalizeByFactor(double[] vec, double factor)
	{
		double movelength = Math.sqrt(Math.pow(vec[0], 2)+Math.pow(vec[1], 2));
		vec[0] /= movelength/factor; 
		vec[1] /= movelength/factor;
	}
	
	public void takeDamage(int DAMAGE)
	{	
		HEALTH -= DAMAGE;
		if (HEALTH < 1) {isAlive = false;}
		
		damageFrames = 10;
	}
	
	//INITIALIZATION
	private void initStats()
	{
		x = random.nextInt(PANEL_WIDTH);
		y = random.nextInt(PANEL_HEIGHT);
		
		PROJECTILE_X = x;
		PROJECTILE_Y = y;
		
		SIZE = random.nextInt(20)+15;
		MOVE_SPEED = random.nextDouble(2.0)+0.2;
		PROJECTILE_SPEED = random.nextDouble(5)+5;
		DAMAGE = random.nextInt(90)+10;
		HEALTH = random.nextInt(900)+100;

		STARTING_HEALTH = HEALTH;
		
		newGoal();
	}
	
	private void newGoal()
	{
		GOAL_X = random.nextInt(PANEL_WIDTH);
		GOAL_Y = random.nextInt(PANEL_HEIGHT);
		setMoveVect();
	}
	
	//GET-SET
	public NPC getTarget()
	{return TARGET;}
	
	public void setTarget(NPC TARGET)
	{
		this.TARGET = TARGET;
		PROJECTILE_X = x;
		PROJECTILE_Y = y;
		setShootVect();
	}
}