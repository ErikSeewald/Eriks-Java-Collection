import javax.swing.JFrame;

public class Sierpinski{
	
	SierpinskiSlow slowPanel;
	SierpinskiFast fastPanel;
	
	int gamemode = 0;
	
	WindowEventHandler eventHandler;
	
	Sierpinski(WindowEventHandler eventHandler, int gamemode)
	{
		this.gamemode = gamemode;
		this.eventHandler = eventHandler;
		
		start();
	}	
	
	public void start()
	{
		JFrame frame = new JFrame("Sierpinski");
		
		frame.setResizable(false);
		frame.addWindowListener(eventHandler);
		
		switch (gamemode)
		{
		case 1: slowPanel = new SierpinskiSlow(true); frame.add(slowPanel); break;
		case 2: slowPanel = new SierpinskiSlow(false); frame.add(slowPanel); break;
		case 3: fastPanel = new SierpinskiFast(); frame.add(fastPanel); break;
		}
		
		frame.pack();

		frame.setVisible(true);
	}
}