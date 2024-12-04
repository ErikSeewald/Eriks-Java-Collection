package particles;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;

import ejcMain.EJC_Game;
import ejcMain.EJC_WindowEventHandler;

public class EJC_Particles extends JFrame implements EJC_Game
{	
	private static final long serialVersionUID = -1650331621506133111L;
	
	private ParticlesPanel panel;
	
	@Override
	public void start(EJC_WindowEventHandler eventHandler)
	{
		this.addWindowListener(eventHandler);
		panel = new ParticlesPanel();

		this.setTitle("Particles");

		this.addKeyListener(new KeyListener() 
		{
			@Override
			public void keyPressed(KeyEvent e) 
			{
				if (e.getKeyCode() == KeyEvent.VK_R) {panel.start();}
			}
			
			@Override
			public void keyReleased(KeyEvent e) {}
			
			@Override
			public void keyTyped(KeyEvent e) {}

		});

		this.setResizable(false);
		this.add(panel);
		this.pack();
		this.setVisible(true);	
	}

	@Override
	public void stop() {panel.stop(); panel = null;}
}