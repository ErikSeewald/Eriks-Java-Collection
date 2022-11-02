package Sudoku;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;

import Main.MainMenu;
import Main.WindowEventHandler;

public class Sudoku
{
	public void start(WindowEventHandler eventHandler) 
	{
		
		JFrame frame = new JFrame("Sudoku");
		frame.setIconImage(MainMenu.img.getImage());
		frame.setResizable(false);
		
		SudokuPanel panel = new SudokuPanel();
	
		frame.addWindowListener(eventHandler);
		frame.addKeyListener(new KeyListener() 
		{
			@Override
			public void keyTyped(KeyEvent e) 
			{	
				if (e.getKeyChar() == 's') {panel.sudoku();}
				else if (e.getKeyChar() == '+') {panel.changeSize(10); frame.pack();}
				else if (e.getKeyChar() == '-') {panel.changeSize(-10); frame.pack();}
				else if (e.getKeyChar() == 'r') {panel.reset();}
				
				else 
				{
					int x =  e.getKeyChar() - '0'; //gives the int value
					try{panel.changeValue(x);} catch (Exception ex) {}
				}
			}
			@Override
			public void keyPressed(KeyEvent e) {}
			@Override
			public void keyReleased(KeyEvent e) {}		
		});
		
		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
	}
}
