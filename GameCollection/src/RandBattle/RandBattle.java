package RandBattle;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

import Main.MainMenu;
import Main.WindowEventHandler;

public class RandBattle extends JFrame
{
	private static final long serialVersionUID = 411705531616331949L;
	
	public RandBattle()
	{
		RB_Panel panel = new RB_Panel();
		this.setIconImage(MainMenu.img.getImage());
		this.add(panel);
		this.pack();
		this.setTitle("Rand Battle");
		
		this.addKeyListener(new KeyListener() 
		{
			@Override
			public void keyPressed(KeyEvent e) 
			{
				int code = e.getKeyCode();
				
//				if (code == 71) {panel.changeGridVisibility();} //G
//				else if (code == 45) {panel.changeSize(-10); pack();} //-
//				else if (code == 521) {panel.changeSize(10); pack();} //+
//				else if (code == 37) {panel.loadLevel(panel.levelNum-1);} //LEFT
//				else if (code == 39) {panel.loadLevel(panel.levelNum+1);} //RIGHT	
//				else if (code == 82) {panel.loadLevel(panel.levelNum);} //R	
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
