package pathfindGame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JMenuItem;

import ejcMain.EJC_Game;
import ejcMain.EJC_WindowEventHandler;
import ejcMain.util.EJC_GUI.EJC_MenuBar;
import pathfindGame.PathfindPanel.ControlBooleans;


public class EJC_Pathfind extends JFrame implements ActionListener, EJC_Game
{
	private static final long serialVersionUID = 386670748457681736L;

	private JMenuItem seedItem;
	private PathfindPanel panel;
	
	public void start(EJC_WindowEventHandler eventHandler)
	{
		this.addWindowListener(eventHandler);
		this.setTitle("Pathfind");
		panel = new PathfindPanel();
		this.setResizable(false);
		
		//MENU BAR
		seedItem = new JMenuItem("Set seed");
		seedItem.addActionListener(this);
		
		EJC_MenuBar menuBar = new EJC_MenuBar(this);
		menuBar.addEJCMenu("Seed", new JMenuItem[] {seedItem});
		
		this.addKeyListener(new KeyListener() 
		{
			@Override
			public void keyTyped(KeyEvent e) 
			{	
				char key = e.getKeyChar();
				
				if (key == '+') {panel.changeSize(10); pack();}
				else if (key == '-') {panel.changeSize(-10); pack();}
				else if (key == 'r') {panel.initialize(ControlBooleans.restart);}
				else if (key == 't') {panel.initialize(ControlBooleans.fullReset);}
					
				else if (key == 'a' || key == 's' || key == 'd' || key == 'w')
				{panel.nextMove(key);}
			}
			@Override
			public void keyPressed(KeyEvent e) {}
			@Override
			public void keyReleased(KeyEvent e) {}		
		});
		
		this.add(panel);
		this.pack();
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource() == seedItem)
		{panel.createSeed();}
	}

	@Override
	public void stop() {panel = null; seedItem = null;}
}