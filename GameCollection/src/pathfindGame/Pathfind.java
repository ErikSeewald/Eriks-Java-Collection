package pathfindGame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import Main.EJC_Interface;
import Main.WindowEventHandler;
import pathfindGame.PathfindPanel.ControlBooleans;


public class Pathfind extends JFrame implements ActionListener, EJC_Interface
{
	private static final long serialVersionUID = 386670748457681736L;
	private static final int index = 4;

	private JMenuItem seedItem;
	private PathfindPanel panel;
	
	public Pathfind()
	{
		this.setTitle("Pathfind");
		panel = new PathfindPanel();
		this.setResizable(false);
		
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("Seed");
		
		seedItem = new JMenuItem("Set seed");
		seedItem.addActionListener(this);
		
		fileMenu.add(seedItem);
		menuBar.add(fileMenu);
		this.setJMenuBar(menuBar);
		
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
				{panel.movePlayer(key);}
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
	
	public void start(WindowEventHandler eventHandler)
	{this.addWindowListener(eventHandler);}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource() == seedItem)
		{loadSeed();}
	}
	
	private void loadSeed()
	{
		String seedStr = JOptionPane.showInputDialog("Seed:");
		if (seedStr == null)
		{return;}
		
		int seed = 0;
		byte[] str = seedStr.getBytes();
					
		for (byte b : str)
		{seed+= (int) b;}
				
		panel.setSeed(seed);
	}

	@Override
	public void stop() {panel = null; seedItem = null;}
	
	@Override
	public int getIndex() {return index;}
}