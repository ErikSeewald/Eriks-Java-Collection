package gravityVectors;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;

import ejcMain.EJC_Game;
import ejcMain.EJC_WindowEventHandler;

public class EJC_GravityVectors extends JFrame implements EJC_Game
{
	private static final long serialVersionUID = -7941120893843254800L;
	
	private GravityVectorsPanel panel;

	public void start(EJC_WindowEventHandler eventHandler) 
	{
		this.addWindowListener(eventHandler);
		this.setTitle("Gravity Vectors");
		
		panel = new GravityVectorsPanel();
	
		this.addKeyListener(new KeyListener()
		{
			@Override
			public void keyPressed(KeyEvent e) 
			{
				int code = e.getKeyCode();
				
				if (code == KeyEvent.VK_PLUS) {panel.changeSize(30); pack();}
				else if (code == KeyEvent.VK_MINUS) {panel.changeSize(-30); pack();}
				else if (code == KeyEvent.VK_1) {panel.changePPointCount(-1);}
				else if (code == KeyEvent.VK_2) {panel.changePPointCount(1);}
				else if (code == KeyEvent.VK_G) {panel.gradientMode();}  
				else if (code == KeyEvent.VK_N) {panel.switchArrowNormalization();}        
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
	public void stop() {panel = null;}
}