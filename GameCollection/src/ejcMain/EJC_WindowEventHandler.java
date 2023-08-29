package ejcMain;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class EJC_WindowEventHandler extends WindowAdapter
{	
	public void windowClosing(WindowEvent evt)
	{
		EJC_GameHandler.closeGame((EJC_Game) evt.getComponent());
		System.gc();
	}
}