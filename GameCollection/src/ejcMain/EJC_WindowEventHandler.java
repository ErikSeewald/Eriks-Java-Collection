package ejcMain;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class EJC_WindowEventHandler extends WindowAdapter
{	
	public void windowClosing(WindowEvent evt)
	{
		EJC_Game game = (EJC_Game) evt.getComponent();
		EJC_GameHandler.closeGame(game);
		MusicManager.closeAllTracks(game);
		System.gc();
	}
}