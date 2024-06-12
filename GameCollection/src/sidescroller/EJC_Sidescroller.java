package sidescroller;

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

public class EJC_Sidescroller extends JFrame implements ActionListener, EJC_Game
{	
	private static final long serialVersionUID = -7018763596595532898L;
	
	private Timer timer;
	private SidescrollerPanel panel;
	private int xSpeed, ySpeed;
	private HashSet<Integer> pressedKeys;
	private JMenuItem seedItem;
	
	public void start(EJC_WindowEventHandler eventHandler) 
	{
		this.addWindowListener(eventHandler);
		this.setTitle("Sidescroller");
		panel = new SidescrollerPanel(this);
		this.add(panel);
		this.pack();
		this.setResizable(false);
		
		//MOVEMENT
		pressedKeys = new HashSet<>();
		
		this.addKeyListener(new KeyListener() 
		{	
			@Override
			public void keyPressed(KeyEvent e) 
			{	
				int code = e.getKeyCode();
				
				if (code == 82) {panel.start(SidescrollerPanel.StartOperations.restart);} //R
				else if (code == 84) {panel.start(SidescrollerPanel.StartOperations.newMap);} //T
				else if (code == 27) {panel.pause();} //ESC
				
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
		
		timer = new Timer(14, this);
		timer.start();
		
		//MENU BAR 
		seedItem = new JMenuItem("Set seed");
		seedItem.addActionListener(this);
		
		EJC_MenuBar menuBar = new EJC_MenuBar(this);
		menuBar.addEJCMenu("Seed", new JMenuItem[] {seedItem});
		menuBar.setTimersToControl(new Timer[] {timer});
		menuBar.setKeyListToControl(pressedKeys);

		this.setVisible(true);
	}
	
	public void resetTrack()
	{
		MusicManager.closeAllTracks(this);
		MusicManager.loopTrack(this, EJC_Track.SquareDancing);
	}
	
	@Override
	public void stop()
	{timer.stop(); panel = null;}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource() == seedItem)
		{
			timer.stop();
			panel.createSeed();
			timer.start();
		}
		
		if (e.getSource() == timer)
		{keyToMovement();}	
	}
	
	private void keyToMovement()
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
			panel.player.airTime = 35;
		}
			
		//FALLING
		if (panel.player.airTime > 0) {panel.player.airTime--;}	
		if (panel.player.airTime == 10) {ySpeed = 1;}	//for a smoother transition in fallingSpeed
		else if (panel.player.airTime <= 0) {ySpeed = 5;}
		
		panel.update(xSpeed,ySpeed);	
	}
}