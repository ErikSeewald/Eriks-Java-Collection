package GravityVectors;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

import Main.WindowEventHandler;

public class GravityVectors extends JFrame
{
	private static final long serialVersionUID = -7941120893843254800L;

	public void start(WindowEventHandler eventHandler) 
	{
		GravityVectorsPanel panel = new GravityVectorsPanel();
		
		this.addKeyListener(new KeyListener()
		{
			@Override
			public void keyPressed(KeyEvent e) 
			{
				int code = e.getKeyCode();
				
				if (code == 521) {panel.changeSize(30); pack();} //+
				else if (code == 45) {panel.changeSize(-30); pack();} //-
				else if (code == 49) {panel.changePPOINT_COUNT(-1);} //1
				else if (code == 50) {panel.changePPOINT_COUNT(1);} //2
			}
			
			
			@Override
			public void keyTyped(KeyEvent e) 
			{}
			@Override
			public void keyReleased(KeyEvent e) {}
			
		});
		
		this.setResizable(false);
		this.add(panel);
		this.pack();
		this.setVisible(true);
		
	}

}
