package Main;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class WindowEventHandler extends WindowAdapter
{
	MainMenu menu;
	
	WindowEventHandler(MainMenu menu)
	{this.menu = menu;}
	
	public void windowClosing(WindowEvent evt) 
	{menu.closeGame((EJC_Interface) evt.getComponent());}
}