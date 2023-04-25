package infdungeons;

import java.util.Random;

public abstract class Enemy 
{
	protected int index0, index1; // tile array index
	protected int x, y;
	protected int hp;
	protected boolean isAlive = true;
	protected int size;
	protected int speed;
	
	public static final byte type_reddorb = 3;
	
	public void attack(Player player) {}
	
	public void getHit(int damage)
	{
		this.hp -= damage;
		if (this.hp <= 0) {this.isAlive = false;} 
	}
	
	public void move(Random random, int[] room) 
	{
		switch (random.nextInt(5)) //0 = no movement
		{ 
			case 1: this.x += this.speed; if (this.x > room[0] + room[2] - this.size) {this.x -= this.speed;}
			break;
			case 2: this.x -= this.speed; if (this.x < room[0]) {this.x += this.speed;}
			break;
			case 3: this.y += this.speed; if (this.y > room[1] + room[3] - this.size) {this.y -= this.speed;}
			break;
			case 4: this.y -= this.speed; if (this.y < room[1]) {this.y += this.speed;}
			break;
		}
	}
	
	public byte getType() {return -1;}
	
	public void setSize(int size) {}
}
