package infdungeons;

import java.util.Random;

public class Enemy 
{
	protected int index0, index1; // tile array index
	protected int x, y;
	protected int hp;
	protected boolean isAlive = true;
	protected int size;
	protected int speed;
	
	public static final byte type_reddorb = 3;
	
	public void attack(Player player)
	{}
	
	public void getHit(int damage)
	{
		this.hp -= damage;
		if (this.hp <= 0) {this.isAlive = false;} 
	}
	
	public void move(Random random, int[] room)
	{}
	
	public byte getType()
	{return -1;}
}
