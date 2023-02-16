package Main;
import javax.swing.JOptionPane;

public class EJC_Utilities
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
}