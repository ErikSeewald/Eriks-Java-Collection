package bloonShoot;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import Main.EJC_Interface;
import Main.menu.WindowEventHandler;
import bloonShoot.level.LevelHandler.LoadOperations;

public class EJC_BloonShoot extends JFrame implements EJC_Interface
{
	private static final long serialVersionUID = -1952542729679771029L;
	private static final int index = 12;
	
	private BlS_Panel panel;

	@Override
	public void start(WindowEventHandler eventHandler)
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
				
				if (code == 71) {panel.changeGridVisibility();} //G
				else if (code == 45) {panel.changeSize(-1); pack();} //-
				else if (code == 521) {panel.changeSize(1); pack();} //+
				else if (code == 37) {panel.changeLevel(LoadOperations.previousLevel);} //LEFT
				else if (code == 39) {panel.changeLevel(LoadOperations.nextLevel);} //RIGHT	
				else if (code == 82) {panel.changeLevel(LoadOperations.reload);} //R	
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

	@Override
	public int getIndex() {return index;}
}