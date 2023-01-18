package JumpAndRun;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;

import javax.swing.JFrame;
import javax.swing.Timer;

import Main.MainMenu;
import Main.WindowEventHandler;

public class JumpAndRun 
{
	private int timeUntilFall = 0;
	private int scrollingSpeed;
	private int xSpeed, ySpeed;
	
	private static Timer timer;			//decides the framerate (at 14 -> around 60fps)
	
	public void start(WindowEventHandler eventHandler, boolean fastMode) 
	{
		//INITIALIZATION
		JFrame frame = new JFrame("Sidescroller");
		if (fastMode) {frame.setTitle("Speedrun");}
		
		frame.setIconImage(MainMenu.img.getImage());
		frame.addWindowListener(eventHandler);
		
		if (fastMode) 
		{scrollingSpeed = 0;}
		else
		{scrollingSpeed = 2;}
		
		JumpAndRunPanel panel = new JumpAndRunPanel(scrollingSpeed);
		
		frame.add(panel);
		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true);
		
		//MOVEMENT
		HashSet<Integer> pressedKeys = new HashSet<>();
		
		timer = new Timer(14, new ActionListener() 
		{	@Override
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
					
				if (panel.player.onGround) {panel.player.jumpAllowed = true;}
				
				
				panel.offset+=panel.scrollingSpeed;
				
				if (fastMode) {xSpeed*=2;}
				panel.movePlayer(xSpeed,ySpeed);	
			}	
		});
		
		timer.start();
		
		frame.addKeyListener(new KeyListener() 
		{
			@Override
			public void keyTyped(KeyEvent e) 
			{}
			
			@Override
			public void keyPressed(KeyEvent e) 
			{	
				int code = e.getKeyCode();
				
				if (code == 82) {panel.start(false,false);} //R Restart
				else if (code == 84) {panel.start(true,false);} //T	Restart + new map
				
				//CTRL S
				else if (code == 83 && e.isControlDown())
				{if (!panel.paused) {pause();} panel.saveMap(); pressedKeys.remove(83);} //otherwise S stays pressed
				
				//CTRL L
				else if (code == 76 && e.isControlDown()) {if (!panel.paused) {pause();} panel.loadMap(); frame.pack();} 
				
				else if (code == 27) //ESC
				{pause();} 
				
				else
				{pressedKeys.add(code);}
				
			}
			@Override
			public void keyReleased(KeyEvent e) 
			{
				pressedKeys.remove(e.getKeyCode());
			}	
	
			public void pause()
			{panel.paused = !panel.paused; panel.repaint(); if (timer.isRunning()) {timer.stop();} else {timer.start();}}
		});	
	}
	
	public static void stop()
	{timer.stop();}
}
