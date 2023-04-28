package infdungeons.enemies;

import java.util.Random;

import infdungeons.player.Player;

public abstract class Enemy 
{
	public int index0, index1; // tile array index
	public int x, y;
	public int hp;
	public boolean isAlive = true;
	public int size;
	public int speed;
	
	public int dmg;
	
	public static final byte type_reddorb = 3, type_yellorb = 4, type_projectile = 5;
	
	public void attack(Player player) 
	{
		int buffer = size / 2;
		
		if (player.x > this.x - buffer && player.x < this.x + size
				&& player.y > this.y - buffer && player.y < this.y + size )
		{player.getHit(this.dmg);}
	}
	
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
	
	public void setSize(int size) 
	{
		this.size = size;
		this.speed = size / 3;
	}
}