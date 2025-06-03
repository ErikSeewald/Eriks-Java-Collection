package parallelUniverses;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import javax.swing.JFrame;
import javax.swing.Timer;

import ejcMain.EJC_Game;
import ejcMain.EJC_WindowEventHandler;

public class EJC_ParallelUniverses extends JFrame implements EJC_Game
{
	private static final long serialVersionUID = -728908076775898206L;
	
	private PU_Panel panel;
	private HashSet<Integer> pressedKeys = new HashSet<>();
	private Timer timer;
	
	@Override
	public void start(EJC_WindowEventHandler eventHandler) 
	{
		this.addWindowListener(eventHandler);
		this.setTitle("Parallel Universes");
		panel = new PU_Panel();
		this.add(panel);
		this.setResizable(false);
		this.pack();
		
		this.addKeyListener(new KeyListener() 
		{	
			@Override
			public void keyPressed(KeyEvent e) 
			{
				int code = e.getKeyCode();
				pressedKeys.add(code);
				
				if (code == KeyEvent.VK_H) {panel.hideUniverses();}
			}
			
			@Override
			public void keyReleased(KeyEvent e) 
			{pressedKeys.remove(e.getKeyCode());}
			
			@Override
			public void keyTyped(KeyEvent e) {}
		});	
		
		timer = new Timer(25, new ActionListener()
		{	@Override
			public void actionPerformed(ActionEvent e) 
			{
				int x = 0, y = 0, moveCount = 1;

				if (pressedKeys.contains(KeyEvent.VK_D)) {x = 1;}
				if (pressedKeys.contains(KeyEvent.VK_A)) {x = -1;}
				if (pressedKeys.contains(KeyEvent.VK_W)) {y = -1;}
				if (pressedKeys.contains(KeyEvent.VK_S)) {y = 1;}

				if (pressedKeys.contains(KeyEvent.VK_SHIFT)) {moveCount = 4;}

				if(x == 0 && y == 0)
				{return;}

				panel.movePlayer(x, y, moveCount);
			}	
		});

		timer.start();
		this.setVisible(true);
	}
	
	@Override
	public void stop()
	{timer.stop(); panel = null; pressedKeys = null;}
}