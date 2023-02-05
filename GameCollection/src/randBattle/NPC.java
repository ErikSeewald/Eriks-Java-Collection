package randBattle;

import java.awt.Color;
import java.util.Random;

public class NPC 
{
	public boolean isAlive = true;
	public int damageAnimation = 0;
	
	private double LOC_X;
	private double LOC_Y;
	
	private double PROJECTILE_LOC_X;
	private double PROJECTILE_LOC_Y;
	
	private int GOAL_X;	//NPC constantly moves towards a goal
	private int GOAL_Y;
	
	private NPC TARGET;
	
	private double[] SHOOT_VECT = new double[2];
	private double[] MOVE_VECT = new double[2];
	
	private int SIZE;
	private double MOVE_SPEED;
	private double PROJECTILE_SPEED;
	private int DAMAGE;
	private int HEALTH;
	public int STARTING_HEALTH;
	private Color COLOR;
	
	private int PANEL_WIDTH;
	private int PANEL_HEIGHT;
	
	Random random = new Random();
	
	NPC(int PANEL_WIDTH, int PANEL_HEIGHT)
	{
		this.PANEL_WIDTH = PANEL_WIDTH;
		this.PANEL_HEIGHT = PANEL_HEIGHT;
		initStats();
		
		STARTING_HEALTH = HEALTH;
	}
	
	public void move()
	{	
		//has the NPC reached its goal? -> new goal
		if (LOC_X > GOAL_X-10 && LOC_X < GOAL_X+10 && LOC_Y > GOAL_Y-10 && LOC_Y < GOAL_Y+10) 
		{
			GOAL_X = random.nextInt(PANEL_WIDTH);
			GOAL_Y = random.nextInt(PANEL_HEIGHT);
			setMoveVect();
		}
		
		LOC_X+= MOVE_VECT[0];
		LOC_Y+= MOVE_VECT[1];
		
		if (damageAnimation > 0) {damageAnimation--;}
	}
	
	private void setMoveVect()
	{
		MOVE_VECT[0] = GOAL_X - LOC_X;
		MOVE_VECT[1] = GOAL_Y - LOC_Y;
	
		double movelength = Math.sqrt(Math.pow(MOVE_VECT[0], 2)+Math.pow(MOVE_VECT[1], 2));
		
		MOVE_VECT[0] /= movelength/MOVE_SPEED; 
		MOVE_VECT[1] /= movelength/MOVE_SPEED;
		
	}
	
	public void shoot()
	{
		PROJECTILE_LOC_X+= SHOOT_VECT[0];
		PROJECTILE_LOC_Y+= SHOOT_VECT[1];
		
		if (PROJECTILE_LOC_X > PANEL_WIDTH || PROJECTILE_LOC_X < 0 || PROJECTILE_LOC_Y > PANEL_HEIGHT || PROJECTILE_LOC_Y < 0) 
		{
			PROJECTILE_LOC_X = LOC_X;
			PROJECTILE_LOC_Y = LOC_Y;
			setShootVect();
		}
	}
	
	private void setShootVect()
	{
		SHOOT_VECT[0] = TARGET.getX() - PROJECTILE_LOC_X;
		SHOOT_VECT[1] = TARGET.getY() - PROJECTILE_LOC_Y;
	
		double movelength = Math.sqrt(Math.pow(SHOOT_VECT[0], 2)+Math.pow(SHOOT_VECT[1], 2));
		
		SHOOT_VECT[0] /= movelength/PROJECTILE_SPEED; 
		SHOOT_VECT[1] /= movelength/PROJECTILE_SPEED;
	}
	
	public void takeDamage(int DAMAGE)
	{	
		HEALTH -= DAMAGE;
		if (HEALTH < 1) {isAlive = false;}
		
		damageAnimation = 10;
	}
	
	//INITIALIZATION
	private void initStats()
	{
		LOC_X = random.nextInt(PANEL_WIDTH);
		LOC_Y = random.nextInt(PANEL_HEIGHT);
		
		PROJECTILE_LOC_X = LOC_X;
		PROJECTILE_LOC_Y = LOC_Y;
		
		GOAL_X = random.nextInt(PANEL_WIDTH);
		GOAL_Y = random.nextInt(PANEL_HEIGHT);
		
		SIZE = random.nextInt(20)+15;
		MOVE_SPEED = random.nextDouble(2.0)+0.2;
		PROJECTILE_SPEED = random.nextDouble(5)+5;
		DAMAGE = random.nextInt(90)+10;
		HEALTH = random.nextInt(900)+100;
		COLOR = new Color(100,100, random.nextInt(50)+120);
		
		setMoveVect();
	}
	
	//GET-SET
	public double getX()
	{return LOC_X;}
	public void setX(double LOC_X)
	{this.LOC_X = LOC_X;}
	
	public double getY()
	{return LOC_Y;}
	public void setY(double LOC_Y)
	{this.LOC_Y = LOC_Y;}
	
	public double getProjectileX()
	{return PROJECTILE_LOC_X;}
	public double getProjectileY()
	{return PROJECTILE_LOC_Y;}
	
	public int getSize()
	{return SIZE;}
	public void setSize(int SIZE)
	{this.SIZE = SIZE;}
	
	public double getMoveSpeed()
	{return MOVE_SPEED;}
	public void setMoveSpeed(double MOVE_SPEED)
	{this.MOVE_SPEED = MOVE_SPEED;}
	
	public double getProjectileSpeed()
	{return PROJECTILE_SPEED;}
	public void setProjectileSpeed(double PROJECTILE_SPEED)
	{this.PROJECTILE_SPEED = PROJECTILE_SPEED;}
	
	public int getDamage()
	{return DAMAGE;}
	public void setDamage(int DAMAGE)
	{this.DAMAGE = DAMAGE;}
	
	public int getHealth()
	{return HEALTH;}
	public void setHealth(int HEALTH)
	{this.HEALTH = HEALTH;}
	
	public Color getColor()
	{return COLOR;}
	public void setColor(Color COLOR)
	{this.COLOR = COLOR;}
	
	public NPC getTarget()
	{return TARGET;}
	public void setTarget(NPC TARGET)
	{
		this.TARGET = TARGET;
		PROJECTILE_LOC_X = LOC_X;
		PROJECTILE_LOC_Y = LOC_Y;
		setShootVect();
	}

}
