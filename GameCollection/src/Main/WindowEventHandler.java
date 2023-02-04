package Main;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.URISyntaxException;

public class WindowEventHandler extends WindowAdapter
{
	public void windowClosing(WindowEvent evt) 
	  { 
		EJC_Interface game = (EJC_Interface) evt.getComponent();
		game.stop();

		MainMenu.isOpened[game.getIndex()] = false;
		
		try {MainMenu.gameClosed();}
		catch (IOException e1) {e1.printStackTrace();} catch (URISyntaxException e2) {e2.printStackTrace();}
	  }
}