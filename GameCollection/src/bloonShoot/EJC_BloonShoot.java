package bloonShoot;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;

import bloonShoot.level.LevelHandler.LoadOperations;
import ejcMain.EJC_Game;
import ejcMain.EJC_WindowEventHandler;

public class EJC_BloonShoot extends JFrame implements EJC_Game
{
	private static final long serialVersionUID = -1952542729679771029L;
	
	private BlS_Panel panel;

	@Override
	public void start(EJC_WindowEventHandler eventHandler)
	{
		this.addWindowListener(eventHandler);
		this.setTitle("Bloon Shooting");
		panel = new BlS_Panel();
		this.add(panel);
		this.pack();
		
		this.addKeyListener(new KeyListener() 
		{
			@Override
			public void keyPressed(KeyEvent e) 
			{
				int code = e.getKeyCode();
				
				if (code == KeyEvent.VK_G) {panel.changeGridVisibility();}
				else if (code == KeyEvent.VK_MINUS) {panel.changeSize(-1); pack();}
				else if (code == KeyEvent.VK_PLUS) {panel.changeSize(1); pack();}
				else if (code == KeyEvent.VK_LEFT) {panel.changeLevel(LoadOperations.previousLevel);}
				else if (code == KeyEvent.VK_RIGHT) {panel.changeLevel(LoadOperations.nextLevel);}
				else if (code == KeyEvent.VK_R) {panel.changeLevel(LoadOperations.reload);}
			}
			
			@Override
			public void keyReleased(KeyEvent e) {}
			@Override
			public void keyTyped(KeyEvent e) {}
		});
		
		this.setResizable(false);
		this.setVisible(true);
	}
	
	@Override
	public void stop()
	{panel.stop(); panel = null;}
}