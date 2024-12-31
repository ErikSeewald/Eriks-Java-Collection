package mouseDodge;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import ejcMain.EJC_Game;
import ejcMain.EJC_WindowEventHandler;
import ejcMain.MusicManager;
import ejcMain.MusicManager.EJC_Track;

public class EJC_MouseDodge extends JFrame implements EJC_Game
{
	private static final long serialVersionUID = 1453082636502752179L;
	
	private MouseDodgePanel panel;
	
	public void start(EJC_WindowEventHandler eventHandler) 
	{
		panel = new MouseDodgePanel(this);

		this.setTitle("Mouse Dodge");
		this.addWindowListener(eventHandler);
		this.setResizable(false);
		
		this.addKeyListener(new KeyListener() 
		{			
			@Override
			public void keyPressed(KeyEvent e) 
			{
				int code = e.getKeyCode();
				
				if (code == KeyEvent.VK_PLUS) {panel.changeSize(10); pack();}
				else if (code == KeyEvent.VK_MINUS) {panel.changeSize(-10); pack();}
				else if (code == KeyEvent.VK_R) {panel.start();}
				else if (code == KeyEvent.VK_ESCAPE) {panel.pause();}
				else if (code == KeyEvent.VK_D) {panel.darkMode = !panel.darkMode; panel.repaint();}
			}
			
			@Override
			public void keyReleased(KeyEvent e) {}	
			
			@Override
			public void keyTyped(KeyEvent e)  {}
		});
		
		this.add(panel);
		this.pack();
		this.setVisible(true);	
		MusicManager.loopTrack(this, EJC_Track.SquareDancing);
	}
	
	public void resetTrack()
	{
		MusicManager.closeAllTracks(this);
		MusicManager.loopTrack(this, EJC_Track.SquareDancing);
	}

	@Override
	public void stop() {panel.stop(); panel = null;}
}