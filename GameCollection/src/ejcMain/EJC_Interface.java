package ejcMain;

import Main.menu.WindowEventHandler;

public interface EJC_Interface
{
	public void start(WindowEventHandler eventHandler);
	public void stop();
	public int getIndex();
}
