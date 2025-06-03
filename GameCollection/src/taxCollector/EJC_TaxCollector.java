package taxCollector;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.Timer;
import ejcMain.EJC_Game;
import ejcMain.EJC_WindowEventHandler;
import ejcMain.MusicManager;
import ejcMain.MusicManager.EJC_Track;
import ejcMain.util.EJC_GUI.EJC_MenuBar;
import ejcMain.util.EJC_Util.Direction;
import taxCollector.MapHandler.ResetActions;

public class EJC_TaxCollector extends JFrame implements EJC_Game, ActionListener
{
	private static final long serialVersionUID = -5389438519379536388L;
	
	private TC_Panel panel;
	private HashSet<Integer> pressedKeys = new HashSet<>();
	private Timer timer;
	private JMenuItem seedItem;
	
	@Override
	public void start(EJC_WindowEventHandler eventHandler) 
	{
		this.addWindowListener(eventHandler);
		this.setTitle("Tax Collector");
		this.setResizable(false);
		
		panel = new TC_Panel(this);
		this.add(panel);
		this.pack();
		pressedKeys = new HashSet<>();
		
		this.addKeyListener(new KeyListener() 
		{
			@Override
			public void keyPressed(KeyEvent e) 
			{
				int code = e.getKeyCode();
				pressedKeys.add(code);
				
				if (code == KeyEvent.VK_R) {panel.restart(ResetActions.newSeed);}
				
				if (!panel.updateValid()) {return;}
				else if (code == KeyEvent.VK_G) {panel.switchGridBool();}
				else if (code == KeyEvent.VK_E) {panel.interaction();}
				else if (code == KeyEvent.VK_T) {panel.switchDebugBool();}
 			}
			
			@Override
			public void keyReleased(KeyEvent e)
			{pressedKeys.remove(e.getKeyCode());}
			
			@Override
			public void keyTyped(KeyEvent e) {}
		});

		this.setVisible(true);

		timer = new Timer(25, new ActionListener()
		{	@Override
			public void actionPerformed(ActionEvent e) 
			{
				if (!panel.updateValid()) {return;}
			
				if (pressedKeys.contains(KeyEvent.VK_D)) {panel.move(Direction.EAST);}
				else if (pressedKeys.contains(KeyEvent.VK_A)) {panel.move(Direction.WEST);}
				else if (pressedKeys.contains(KeyEvent.VK_W)) {panel.move(Direction.NORTH);}
				else if (pressedKeys.contains(KeyEvent.VK_S)) {panel.move(Direction.SOUTH);}

				panel.advanceFrame();
			}	
		});

		timer.setInitialDelay(100);
		timer.start();
		
		//MENU BAR
		seedItem = new JMenuItem("Set seed");
		seedItem.addActionListener(this);
		
		EJC_MenuBar menuBar = new EJC_MenuBar(this);
		menuBar.addEJCMenu("Seed", new JMenuItem[] {seedItem});
		menuBar.setTimersToControl(new Timer[] {timer});
		menuBar.setKeyListToControl(pressedKeys);
		
		MusicManager.loopTrack(this, EJC_Track.UrgentTaxation);
	}
	
	public void resetTrack()
	{
		MusicManager.closeAllTracks(this);
		MusicManager.loopTrack(this, EJC_Track.UrgentTaxation);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource() == seedItem)
		{
			timer.stop();
			panel.createSeed();
			timer.start();
		}
	}

	@Override
	public void stop() 
	{
		timer.stop(); panel.stop(); panel = null;
	}
}