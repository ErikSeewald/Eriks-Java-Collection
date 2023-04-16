package infdungeons;

import java.util.Random;

public class Reddorb extends Enemy
{
	public static final int attack_dmg = 2;
	public static final int starting_hp = 5;
	
	/**
     * Creates a Reddorb enemy object
     * @param x the x coordinate
     * @param y the y coordinate
     * @param size the Reddorbs screen size
     * @param index0 first index of Reddorb in tile array
     * @param index1 second index of Reddorb in tile array
     */
	Reddorb(int x, int y, int size, int index0, int index1)
	{
		this.x = x;
		this.y = y;
		this.size = size;
		this.speed = size / 3;
		this.index0 = index0;
		this.index1 = index1;
		this.hp = Reddorb.starting_hp;
	}
	
	@Override
	public void attack(Player player)
	{
		if (player.x > this.x - size && player.x < this.x + size
				&& player.y > this.y - size && player.y < this.y + size )
		{player.getHit(Reddorb.attack_dmg);}
	}
	
	
	@Override
	public void move(Random random, int[] room)
	{
		switch (random.nextInt(5)) //0 = no movement
		{ 
			case 1: this.x+= this.speed; if (this.x > room[0] + room[2]) {this.x -= this.speed;}
			break;
			case 2: this.x-= this.speed; if (this.x < room[0]) {this.x += this.speed;}
			break;
			case 3: this.y+= this.speed; if (this.y > room[1] + room[3]) {this.x -= this.speed;}
			break;
			case 4: this.y-= this.speed; if (this.y < room[1]) {this.x += this.speed;}
			break;
		}
	}
	
	@Override
	public byte getType() {return Enemy.type_reddorb;}
}
