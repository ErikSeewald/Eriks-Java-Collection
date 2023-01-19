package JumpAndRun;
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
import javax.swing.JOptionPane;
import javax.swing.Timer;

import Main.MainMenu;
import Main.WindowEventHandler;

public class JumpAndRun implements ActionListener
{	
	private static Timer timer;
	private JumpAndRunPanel panel;
	
	public void start(WindowEventHandler eventHandler, boolean fastMode) 
	{
		//INITIALIZATION
		JFrame frame = new JFrame("Sidescroller");
		frame.setIconImage(MainMenu.img.getImage());
		frame.addWindowListener(eventHandler);
		
		int scrollingSpeed = 2;
		if (fastMode) 
		{
			frame.setTitle("Speedrun");
			scrollingSpeed = 0;
		}
		
		panel = new JumpAndRunPanel(scrollingSpeed);
		
		frame.add(panel);
		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true);
		
		//MOVEMENT
		HashSet<Integer> pressedKeys = new HashSet<>();
		
		timer = new Timer(14, new ActionListener() 
		{	
			int timeUntilFall = 0;
			int xSpeed, ySpeed;
			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				xSpeed = 0;
				if (pressedKeys.contains(68)) //D
				{xSpeed = 5;} 	
				
				if (pressedKeys.contains(65)) //A
				{xSpeed = -5;}
				
				//JUMPING
				if (pressedKeys.contains(32) && panel.player.jumpAllowed) //SPACE
				{
					ySpeed = -5;
					panel.player.jumpAllowed = false; 
					timeUntilFall = 35;
				}
					
				//FALLING
				if (timeUntilFall > 0) {timeUntilFall--;}	
				if (timeUntilFall == 10) {ySpeed = 1;}	//for a smoother transition in fallingSpeed
				else if (timeUntilFall == 0) {ySpeed = 5;}
				
				panel.scroll();
				
				if (fastMode) {xSpeed*=2;}
				panel.movePlayer(xSpeed,ySpeed);	
			}	
		});
		timer.start();
		
		frame.addKeyListener(new KeyListener() 
		{	
			@Override
			public void keyPressed(KeyEvent e) 
			{	
				int code = e.getKeyCode();
				
				if (code == 82) {panel.start(JumpAndRunPanel.StartOperations.restart);} //R
				else if (code == 84) {panel.start(JumpAndRunPanel.StartOperations.newMap);} //T
				else if (code == 27) //ESC
				{pause();} 
				
				else
				{pressedKeys.add(code);}
				
			}
			@Override
			public void keyReleased(KeyEvent e) 
			{pressedKeys.remove(e.getKeyCode());}
			
			@Override
			public void keyTyped(KeyEvent e) 
			{}
		});
		
		//MENU BAR
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(new Color (100,100,120));
		menuBar.setBorder(BorderFactory.createLineBorder(new Color (115,115,135), 2));
				
		JMenu fileMenu = new JMenu("Seed");	
		fileMenu.setForeground(new Color (230,230,250));
		fileMenu.setBorder(BorderFactory.createLineBorder(new Color (100,100,120)));
				
		JMenuItem seedItem = new JMenuItem("Set seed");
		seedItem.addActionListener(this);
				
		fileMenu.add(seedItem);
		menuBar.add(fileMenu);
		frame.setJMenuBar(menuBar);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		String seedStr = JOptionPane.showInputDialog("Seed:");
		if (seedStr == null)
		{return;}
		
		int seed = 0;
		byte[] str = seedStr.getBytes();
					
		for (byte b : str)
		{
			seed+= (int) b;
		}
				
		panel.setSeed(seed);
	}
	
	public void pause()
	{
		panel.paused = !panel.paused; 
		panel.repaint(); 
		if (timer.isRunning()) {timer.stop();} else {timer.start();}
	}
	
	public static void stop()
	{timer.stop();}
}