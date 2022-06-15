package Coordinates3D;
import javax.swing.JOptionPane;

import Main.WindowEventHandler;

public class Coordinates3D 
{
	
	public void start(WindowEventHandler eventHandler) 
	{
		
		int[] a = getResults("A");
		
		int[] b = getResults("B");

		int[] c = getResults("C");
		
		new Coordinates3DFrame(a,b,c,eventHandler);	

	}
	
	public static int[] getResults(String y)
	{
		
		int[] x = new int[3];
		char t = ' ';
		
		for (int i = 0; i < 3; i++) //3 Durchgänge -> x,y,z Wert
		{ 	
			
			switch (i) //Wechsel der Anzeige zum momentan verlangten Wert
			{case 0: t = 'x';break;case 1: t = 'y';break;case 2: t = 'z';break;} 
			
			while (true) 
			{
				try 
				{	//Y ist der Punkt, t ist x,y oder z
					x[i] = Integer.parseInt(JOptionPane.showInputDialog(y+" - "+t+" von -25 bis 25")); 
					if (x[i] >=-25 && x[i] <= 25) {break;}
					else {JOptionPane.showMessageDialog(null, "-25 bis 25");}
				}
				
				catch (NumberFormatException f) {return x;}
			}	
		}
				
		return x;
	}
}
