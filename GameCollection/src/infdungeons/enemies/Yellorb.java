package infdungeons.enemies;

import infdungeons.player.Player;

public class Yellorb extends Enemy
{
	public static final int attack_dmg = 2;
	public static final int starting_hp = 10;
	
	private int shotCooldown;
	
	private Projectile projectile;
	
	/**
     * Creates a Yellorb enemy object
     * @param x the x coordinate
     * @param y the y coordinate
     * @param size the Yellorb's screen size
     * @param index0 first index of Yellorb in tile array
     * @param index1 second index of Yellorb in tile array
     */
	public Yellorb(int x, int y, int size, int index0, int index1)
	{
		this.x = x;
		this.y = y;
		this.setSize(size);
		this.index0 = index0;
		this.index1 = index1;
		this.hp = Yellorb.starting_hp;
		this.dmg = Yellorb.attack_dmg;
		this.shotCooldown = index0 + index1;
	}
	
	@Override
	public byte getType() {return Enemy.type_yellorb;}
	
	@Override
	public void attack(Player player) 
	{
		// COLLISION
		super.attack(player);
		
		//PROJECTILE
		if ((this.projectile == null || !this.projectile.isAlive) && this.shotCooldown <= 0)
		{
			int[] vec = new int[2];
			vec[0] = player.x - this.x;
			vec[1] = player.y - this.y;
			
			this.projectile = new Projectile(this.x, this.y, this.size / 2, vec, this.getType());	
			this.shotCooldown = Projectile.life_time >> 3; // attack() is not called every frame unlike the projectile's movement
		}
		this.shotCooldown--;
	}
	
	public Projectile getProjectile() {return this.projectile;}
}
