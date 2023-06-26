package taxCollector;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.Timer;
import Main.EJC_Interface;
import Main.EJC_Util.Direction;
import Main.WindowEventHandler;
import taxCollector.MapHandler.ResetActions;

public class EJC_TaxCollector extends JFrame implements EJC_Interface, ActionListener
{
	private static final long serialVersionUID = -5389438519379536388L;
	private static final int index = 19;
	
	private TC_Panel panel;
	private HashSet<Integer> pressedKeys = new HashSet<>();
	private Timer timer;
	private JMenuItem seedItem;
	
	@Override
	public void start(WindowEventHandler eventHandler) 
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
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(new Color (100,100,120));
		menuBar.setBorder(BorderFactory.createLineBorder(new Color (115,115,135), 2));
				
		JMenu fileMenu = new JMenu("Seed");	
		fileMenu.setForeground(new Color (230,230,250));
		fileMenu.setBorder(BorderFactory.createLineBorder(new Color (100,100,120)));
				
		seedItem = new JMenuItem("Set seed");
		seedItem.addActionListener(this);
				
		fileMenu.add(seedItem);
		menuBar.add(fileMenu);
		this.setJMenuBar(menuBar);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource() == seedItem)
		{
			pressedKeys.removeAll(pressedKeys);
			panel.createSeed();
		}
	}

	@Override
	public void stop() 
	{
		timer.stop(); panel = null;
	}

	@Override
	public int getIndex() 
	{return index;}
}