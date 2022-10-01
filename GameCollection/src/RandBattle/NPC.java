package RandBattle;

import java.awt.Color;
import java.util.Random;

public class NPC 
{
	private double LOC_X;
	private double LOC_Y;
	
	private double PROJECTILE_LOC_X;
	private double PROJECTILE_LOC_Y;
	
	private int GOAL_X;	//NPC constantly moves towards a goal
	private int GOAL_Y;
	
	private NPC TARGET;
	
	private int SIZE;
	private double MOVE_SPEED;
	private double PROJECTILE_SPEED;
	private int DAMAGE;
	private int HEALTH;
	private Color COLOR;
	
	private int PANEL_WIDTH;
	private int PANEL_HEIGHT;
	
	Random random = new Random();
	
	NPC(int PANEL_WIDTH, int PANEL_HEIGHT)
	{
		this.PANEL_WIDTH = PANEL_WIDTH;
		this.PANEL_HEIGHT = PANEL_HEIGHT;
		initStats();
	}
	
	public void move()
	{
		double[] v = new double[2];
		v[0] = GOAL_X - LOC_X;
		v[1] = GOAL_Y - LOC_Y;
	
		double movelength = Math.sqrt(Math.pow(v[0], 2)+Math.pow(v[1], 2));
		
		//has the NPC reached its goal? -> new goal
		if (Math.abs(v[0]) < 10 && Math.abs(v[1]) < 10) 
		{
			GOAL_X = random.nextInt(PANEL_WIDTH);
			GOAL_Y = random.nextInt(PANEL_HEIGHT);
		}
		
		v[0] /= movelength/MOVE_SPEED; 
		v[1] /= movelength/MOVE_SPEED;
		
		LOC_X+= v[0];
		LOC_Y+= v[1];
	}
	
	public void shoot()
	{
		double[] v = new double[2];
		v[0] = TARGET.getX() - PROJECTILE_LOC_X;
		v[1] = TARGET.getY() - PROJECTILE_LOC_Y;
	
		double movelength = Math.sqrt(Math.pow(v[0], 2)+Math.pow(v[1], 2));
		
		
		v[0] /= movelength/PROJECTILE_SPEED; 
		v[1] /= movelength/PROJECTILE_SPEED;
		
		PROJECTILE_LOC_X+= v[0];
		PROJECTILE_LOC_Y+= v[1];
	}
	
//	private boolean hasProjectileHit()
//	{
//		if Projectile()
//	}
	
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
		PROJECTILE_SPEED = random.nextDouble(10)+10;
		DAMAGE = random.nextInt(90)+10;
		HEALTH = random.nextInt(9000)+1000;
		COLOR = new Color(100,100, random.nextInt(50)+120);
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
	{this.TARGET = TARGET;}
	
	
	
}
