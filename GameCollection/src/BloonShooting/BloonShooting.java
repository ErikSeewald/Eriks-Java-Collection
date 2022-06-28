package BloonShooting;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

import Main.MainMenu;
import Main.WindowEventHandler;

public class BloonShooting extends JFrame
{
	private static final long serialVersionUID = -1952542729679771029L;
	
	BlS_Panel panel;

	public BloonShooting()
	{
		panel = new BlS_Panel();
		this.setIconImage(MainMenu.img.getImage());
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
				
			}

			@Override
			public void keyReleased(KeyEvent e) {}
			@Override
			public void keyTyped(KeyEvent e) {}
			
		});
		
		this.setResizable(false);
		this.setVisible(true);
	}
	
	public void start(WindowEventHandler eventHandler)
	{this.addWindowListener(eventHandler);}
}