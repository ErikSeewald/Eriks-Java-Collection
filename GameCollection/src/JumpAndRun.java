import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.Timer;

public class JumpAndRun 
{
	int fallingSpeed = 5;
	int timeUntilFall = 0;
	
	static Timer timer;			//decides the framerate (at 14 -> around 60fps)

	int scrollingSpeed;
	
	public void start(WindowEventHandler eventHandler, boolean fastMode) 
	{
		
		JFrame frame = new JFrame("Sidescroller");
		if (fastMode) {frame.setTitle("Speedrun");}
		
		if (fastMode) 
		{scrollingSpeed = 0;}
		else
		{scrollingSpeed = 2;}
		
		JumpAndRunPanel panel = new JumpAndRunPanel(scrollingSpeed);
		
		frame.addWindowListener(eventHandler);
		
		frame.add(panel);
		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true);
		
		ArrayList<Integer> pressedKeys = new ArrayList<>();
		
		timer = new Timer(14, new ActionListener() 
		{	@Override
			public void actionPerformed(ActionEvent e) 
			{
				int x = 0;
				int y = 0;
				
				if (pressedKeys.contains(68)) //D
				{if (fastMode) {x = 10;} else {x = 5;}} 	
				
				if (pressedKeys.contains(65)) //A
				{if (fastMode) {x = -10;} else {x = -5;}}
				
				if (panel.flyMode)
				{
					if (pressedKeys.contains(87)) {y = -5;} 	//W
					if (pressedKeys.contains(83)) {y = 5;} 		//S
					fallingSpeed = 0;
				}
				
				else 
				{
					if (pressedKeys.contains(32) && panel.jumpAllowed) //SPACE
					{
						fallingSpeed = -5; panel.jumpAllowed = false; 
						timeUntilFall = 35;	//countdown until player begins falling again starts
					}
					 
					
					if (timeUntilFall > 0) {timeUntilFall--;}	//counting down
					
					if (timeUntilFall == 10) {fallingSpeed = 1;}	//for a smoother transition in fallingSpeed
					else if (timeUntilFall == 0) {fallingSpeed = 5;}
					
					if (panel.onGround) {panel.jumpAllowed = true;}
				}
				
				panel.offset+=panel.scrollingSpeed;
				panel.movePlayer(x,y,fallingSpeed);	
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
				
//				if (code ==521) {if (panel.changeSize(10)) {frame.pack();}}		//+
//				else if (code ==45) {if (panel.changeSize(-10)) {frame.pack();}}	//-
				
				if (!(pressedKeys.contains(code))) {pressedKeys.add(code);}
				
				if (code == 70) {panel.flyMode = !panel.flyMode;}	//F
				
				else if (code == 82) {panel.start(false,false);} //R Restart
				else if (code == 84) {panel.start(true,false);} //T	Restart + new map
				
				//CTRL S
				else if (code == 83 && e.isControlDown()) 
				{if (!panel.paused) {pause();} panel.saveMap(); pressedKeys.remove(pressedKeys.indexOf(83));} //otherwise S stays pressed
				
				//CTRL L
				else if (code == 76 && e.isControlDown()) {if (!panel.paused) {pause();} panel.loadMap(); frame.pack();} 
				
				else if (code == 27) //ESC
				{pause();} 
				
			}
			@Override
			public void keyReleased(KeyEvent e) 
			{
				int x = pressedKeys.indexOf(e.getKeyCode());
				pressedKeys.remove(x);
			}	
			
			public void pause()
			{panel.paused = !panel.paused; panel.repaint(); if (timer.isRunning()) {timer.stop();} else {timer.start();}}
		});	
	}
	
	public static void stop()
	{ if (timer.isRunning()) {timer.stop();}}
}
