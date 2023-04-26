package infdungeons;

public class Yellorb extends Enemy
{
	public static final int attack_dmg = 2;
	public static final int starting_hp = 10;
	
	/**
     * Creates a Yellorb enemy object
     * @param x the x coordinate
     * @param y the y coordinate
     * @param size the Yellorbs screen size
     * @param index0 first index of Yellorb in tile array
     * @param index1 second index of Yellorb in tile array
     */
	Yellorb(int x, int y, int size, int index0, int index1)
	{
		this.x = x;
		this.y = y;
		this.setSize(size);
		this.index0 = index0;
		this.index1 = index1;
		this.hp = Yellorb.starting_hp;
	}
	
	@Override
	public byte getType() {return Enemy.type_yellorb;}
}
