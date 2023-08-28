package particles;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;

import Main.menu.WindowEventHandler;
import ejcMain.EJC_Interface;

public class EJC_Particles extends JFrame implements EJC_Interface
{	
	private static final long serialVersionUID = -1650331621506133111L;
	private static final int index = 1;
	
	private ParticlesPanel panel;
	
	@Override
	public void start(WindowEventHandler eventHandler)
	{
		this.addWindowListener(eventHandler);
		panel = new ParticlesPanel();

		this.setTitle("Particles");

		this.addKeyListener(new KeyListener() 
		{
			@Override
			public void keyTyped(KeyEvent e) 
			{
				if (e.getKeyChar() == 'r') {panel.start();}
			}

			@Override
			public void keyPressed(KeyEvent e) {}
			@Override
			public void keyReleased(KeyEvent e) {}

		});

		this.setResizable(false);
		this.add(panel);
		this.pack();
		this.setVisible(true);	
	}

	@Override
	public void stop() {panel.stop(); panel = null;}
	
	@Override
	public int getIndex() {return index;}
}