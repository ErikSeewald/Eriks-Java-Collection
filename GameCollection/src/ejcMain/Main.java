package ejcMain;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import ejcMain.menu.MainMenu;

public class Main 
{
	public static void main(String[] args) 
	{
		MainMenu menu = new MainMenu();
		menu.setVisible(true);
		
		menu.addKeyListener(new KeyListener()
		{
			@Override
			public void keyTyped(KeyEvent e) 
			{
				if (e.getKeyChar() == '+') {menu.changeSize(10);}
				else if (e.getKeyChar() == '-') {menu.changeSize(-10);}
			}

			@Override
			public void keyPressed(KeyEvent e) {}

			@Override
			public void keyReleased(KeyEvent e) {}
		});
	}
}