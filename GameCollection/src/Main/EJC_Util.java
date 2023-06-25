package Main;
import javax.swing.JOptionPane;

public class EJC_Util
{
	public static long createSeed()
	{
		String seedStr = JOptionPane.showInputDialog("Seed:");
		if (seedStr == null)
		{return 0;}
		
		long seed = 0;
		byte[] str = seedStr.getBytes();
					
		for (byte b : str)
		{seed+= (int) b;}
				
		return seed;
	}
	
	public static float[] normalize(float x , float y)
	{	
		float length = (float) Math.sqrt(x*x + y*y);
		return new float[] {x/length, y/length};
	}
	
	public static double round(double value, int places) 
	{
	    long factor = (long) Math.pow(10, places);
	    value = value * factor;
	    long tmp = Math.round(value);
	    return (double) tmp / factor;
	}

	//DIRECTION
	public static enum Direction
	{
		NORTH(0), EAST(90), SOUTH(180), WEST(270);
		
		public int angle;
		Direction(int angle) {this.angle = angle;}
	};

	public static Direction reverse(Direction direction)
	{
		return Direction.values()[(direction.ordinal() + 2) % 4];
	}
	
	public static Direction perpendicular(Direction direction)
	{
		return Direction.values()[(direction.ordinal() + 1) % 4];
	}
	
	public static boolean isPerpendicular(Direction d1, Direction d2)
	{
		return (d1.ordinal() + d2.ordinal()) % 2 == 1;
	}
}