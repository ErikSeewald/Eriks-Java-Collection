package bloonShoot;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import Main.EJC_Interface;
import Main.WindowEventHandler;

public class BloonShooting extends JFrame implements EJC_Interface
{
	private static final long serialVersionUID = -1952542729679771029L;
	private static final int index = 12;
	
	private BlS_Panel panel;
	
	public BloonShooting()
	{
		panel = new BlS_Panel();
		this.add(panel);
		this.pack();
		this.setTitle("Bloon Shooting");
		
		this.addKeyListener(new KeyListener() 
		{
			@Override
			public void keyPressed(KeyEvent e) 
			{
				int code = e.getKeyCode();
				
				if (code == 71) {panel.changeGridVisibility();} //G
				else if (code == 45) {panel.changeSize(-10); pack();} //-
				else if (code == 521) {panel.changeSize(10); pack();} //+
				else if (code == 37) {panel.loadLevel(panel.levelNum-1);} //LEFT
				else if (code == 39) {panel.loadLevel(panel.levelNum+1);} //RIGHT	
				else if (code == 82) {panel.loadLevel(panel.levelNum);} //R	
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
	public void start(WindowEventHandler eventHandler)
	{this.addWindowListener(eventHandler);}
	
	@Override
	public void stop()
	{panel.shot.stop(); panel = null;}

	@Override
	public int getIndex() {return index;}
}