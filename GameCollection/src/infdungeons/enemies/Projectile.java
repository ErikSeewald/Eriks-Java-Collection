package infdungeons.enemies;

import java.util.Random;

import Main.EJC_Util;

public class Projectile extends Enemy
{
    private float[] vec;
    public byte parentType;
    private int lifetime = Projectile.life_time;
    
    public static final int attack_dmg = 2, life_time = 600;
    
    private float fx, fy; // floating point locations for saving smaller steps
	
	/**
	* Creates a projectile enemy object
    * @param x the x coordinate
    * @param y the y coordinate
    * @param size the projectile's screen size
    * @param vec the shot vector
    * @param parent_type enemy type of object that fired this projectile
    */
	public Projectile(int x, int y, int size, int[] vec, byte parent_type)
	{
		this.fx = this.x = x;
		this.fy = this.y = y;
		this.setSize(size);
		this.vec = EJC_Util.normalize((float) vec[0], (float) vec[1]);
		
		this.parentType = parent_type;
		
		this.index0 = -1;
		this.index1 = -1;
		
		this.dmg = Projectile.attack_dmg;
	}
	
	@Override
	public void move(Random random, int[] room) 
	{
		this.fx += vec[0];
		this.fy += vec[1];
		this.x = (int) this.fx;
		this.y = (int) this.fy;
		
		lifetime--;
		if (lifetime <= 0) {this.isAlive = false;}
	}
	
	@Override
	public byte getType() {return Enemy.type_projectile;}
	
	@Override
	public void getHit(int damage)
	{this.isAlive = false;}
}