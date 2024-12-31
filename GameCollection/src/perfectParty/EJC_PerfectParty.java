package perfectParty;

import javax.swing.JFrame;
import ejcMain.EJC_Game;
import ejcMain.EJC_WindowEventHandler;
import perfectParty.election.ElectionHandler;
import perfectParty.gui.FrameManager;

public class EJC_PerfectParty extends JFrame implements EJC_Game
{
	private static final long serialVersionUID = 386670748457681736L;
	
	public void start(EJC_WindowEventHandler eventHandler)
	{
		this.addWindowListener(eventHandler);
		this.setTitle("PerfectParty");
		
		FrameManager frameManager = new FrameManager(this);
		ElectionHandler handler = new ElectionHandler(frameManager);
		
		frameManager.setElectionHandler(handler);
		frameManager.buildFrame();
		handler.startRound();
	}

	@Override
	public void stop() {}
}