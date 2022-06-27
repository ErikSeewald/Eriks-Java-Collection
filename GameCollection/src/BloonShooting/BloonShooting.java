package BloonShooting;

import javax.swing.JFrame;

import Main.MainMenu;
import Main.WindowEventHandler;

public class BloonShooting extends JFrame
{
	private static final long serialVersionUID = -1952542729679771029L;
	
	BloonShootingPanel panel;

	public BloonShooting()
	{
		panel = new BloonShootingPanel();
		this.setIconImage(MainMenu.img.getImage());
		this.add(panel);
		this.pack();
		this.setTitle("Bloon Shooting");
		
		this.setResizable(false);
		this.setVisible(true);
	}
	
	public void start(WindowEventHandler eventHandler)
	{this.addWindowListener(eventHandler);}
}
