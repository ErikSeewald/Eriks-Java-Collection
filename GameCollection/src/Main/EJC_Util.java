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
	
	public static enum Direction
	{
		NORTH(0), EAST(90), SOUTH(180), WEST(270);
		
		public int angle;
		Direction(int angle) {this.angle = angle;}
	};
}