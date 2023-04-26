package infdungeons;

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
		this.setSize(size);
		this.index0 = index0;
		this.index1 = index1;
		this.hp = Reddorb.starting_hp;
	}
	
	@Override
	public byte getType() {return Enemy.type_reddorb;}
}
