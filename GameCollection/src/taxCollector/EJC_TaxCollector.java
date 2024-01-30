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
		
		panel = new TC_Panel();
		this.add(panel);
		this.pack();
		pressedKeys = new HashSet<>();
		
		this.addKeyListener(new KeyListener() 
		{
			@Override
			public void keyTyped(KeyEvent e)
			{}
			@Override
			public void keyPressed(KeyEvent e) 
			{
				int code = e.getKeyCode();
				pressedKeys.add(code);
				
				char c = e.getKeyChar();
				if (c == 'r') {panel.restart(ResetActions.newSeed);}
				
				if (!panel.updateValid()) {return;}
				else if (c == 'g') {panel.switchGridBool();}
				else if (c == 'e') {panel.interaction();}
				else if (c == 't') {panel.switchDebugBool();}
 			}
			@Override
			public void keyReleased(KeyEvent e)
			{pressedKeys.remove(e.getKeyCode());}
		});

		this.setVisible(true);

		timer = new Timer(25, new ActionListener()
		{	@Override
			public void actionPerformed(ActionEvent e) 
			{
				if (!panel.updateValid()) {return;}
			
				if (pressedKeys.contains(68)) {panel.move(Direction.EAST);} 	//D
				else if (pressedKeys.contains(65)) {panel.move(Direction.WEST);} //A
				else if (pressedKeys.contains(87)) {panel.move(Direction.NORTH);} //W
				else if (pressedKeys.contains(83)) {panel.move(Direction.SOUTH);} 	//S

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