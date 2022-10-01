package RandBattle;

import java.awt.Color;
import java.util.Random;

public class NPC 
{
	private final Color COLOR = new Color(50,50,150);
	
	private int SIZE;
	private double MOVE_SPEED;
	private double PROJECTILE_SPEED;
	private int SHOOT_FREQUENCY;
	private int DAMAGE;
	private int HEALTH;
	
	NPC()
	{
		initStats();
	}
	
	
	//INITIALIZATION
	private void initStats()
	{
		Random random = new Random();
		
		SIZE = random.nextInt(50)+50;
		MOVE_SPEED = random.nextDouble(5)+5;
		PROJECTILE_SPEED = random.nextDouble(10)+10;
		SHOOT_FREQUENCY = random.nextInt(50)+10;
		DAMAGE = random.nextInt(90)+10;
		HEALTH = random.nextInt(9000)+1000;
	}
	
	//GET-SET
	
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
	
	public int getShootFrequency()
	{return SHOOT_FREQUENCY;}
	public void setShootFrequency(int  SHOOT_FREQUENCY)
	{this. SHOOT_FREQUENCY =  SHOOT_FREQUENCY;}
	
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
	
	
	
}
