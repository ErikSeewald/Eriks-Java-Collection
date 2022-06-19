package ParallelUniverses;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.Timer;

import Main.MainMenu;
import Main.WindowEventHandler;

public class ParallelUniverses 
{
	//pressedKeys List because keyPressed() has an annoying delay before it registers a key as held down
	static ArrayList<Integer> pressedKeys = new ArrayList<>();
	
	static ParallelUniversesPanel panel;
	
	static Timer timer = new Timer(25, new ActionListener()
	{	@Override
		public void actionPerformed(ActionEvent e) 
		{
			int x = 0;
			int y = 0;	
			
			boolean move = false;
			int loop = 1;
			
			if (pressedKeys.contains(68)) {x = 1;  move = true;} 	//D
			if (pressedKeys.contains(65)) {x = -1; move = true;} 	//A
			if (pressedKeys.contains(87)) {y = -1; move = true;} 	//W
			if (pressedKeys.contains(83)) {y = 1;  move = true;} 	//S
			
			if (pressedKeys.contains(16)) {loop = 4;}	//SHIFT
		
			if(move) //don't do an unnecessary calculation
			{
				//handle speed thorugh loops instead of increasing x  or y because we need to calculate 1 pixel at a time
				for (int i = 0; i < loop; i++) 
				{panel.move(x,y);} 
				panel.repaint();
			}
		}	
	});
	
	public void start(WindowEventHandler eventHandler) 
	{
		JFrame frame = new JFrame("Parallel Universes");
		frame.setIconImage(MainMenu.img.getImage());
		panel = new ParallelUniversesPanel();
		
		timer.start();
		
		frame.addWindowListener(eventHandler);
		frame.setResizable(true);
		frame.add(panel);
		frame.pack();
		
		frame.addKeyListener(new KeyListener() 
		{
			@Override
			public void keyTyped(KeyEvent e) 
			{}
			
			@Override
			public void keyPressed(KeyEvent e) 
			{
				int code = e.getKeyCode();
				
				if (!(pressedKeys.contains(code))) {pressedKeys.add(code);}
				
				if (e.getKeyCode() == 69) {panel.hideUniverses();} //E
			
			}
			@Override
			public void keyReleased(KeyEvent e) 
			{
				int x = pressedKeys.indexOf(e.getKeyCode());
				pressedKeys.remove(x);
			}		
		});	
		frame.setVisible(true);
	}
	
	public static void stop()
	{if (timer.isRunning()) {timer.stop();}}
}