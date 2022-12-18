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
	private static ArrayList<Integer> pressedKeys = new ArrayList<>();
	
	private static ParallelUniversesPanel panel;
	
	private static Timer timer = new Timer(25, new ActionListener()
	{	@Override
		public void actionPerformed(ActionEvent e) 
	 	{
			int x = 0, y = 0, loop = 1;	
			boolean move = false;
			
			if (pressedKeys.contains(68)) {x = 1;  move = true;} 	//D
			if (pressedKeys.contains(65)) {x = -1; move = true;} 	//A
			if (pressedKeys.contains(87)) {y = -1; move = true;} 	//W
			if (pressedKeys.contains(83)) {y = 1;  move = true;} 	//S
			
			if (pressedKeys.contains(16)) {loop = 4;}	//SHIFT
		
			if(move)
			{
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
				
				if (code == 72) {panel.hideUniverses();} //H
			
			}
			@Override
			public void keyReleased(KeyEvent e) 
			{
				pressedKeys.remove(pressedKeys.indexOf(e.getKeyCode()));
			}		
		});	
		
		frame.setVisible(true);
	}
	
	public static void stop()
	{timer.stop();}
}