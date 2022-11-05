package SnakesAndLadders;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

import Main.MainMenu;
import Main.WindowEventHandler;

public class SnakesAndLadders extends JFrame 
{
	private static final long serialVersionUID = 7284705718024953236L;
	
	private SnL_Panel panel;
	private SnL_GUI GUI;
	
	public SnakesAndLadders()
	{
		panel = new SnL_Panel();
		GUI = new SnL_GUI(panel);
		
		this.add(panel, BorderLayout.WEST);
		this.add(GUI, BorderLayout.EAST);
		this.pack();
		
		this.setIconImage(MainMenu.img.getImage());
		this.setTitle("Snakes and Ladders");
		this.setResizable(false);
		this.setVisible(true);
		
		this.addKeyListener(new KeyListener() 
		{
			@Override
			public void keyPressed(KeyEvent e) 
			{
				int code = e.getKeyCode();
				//45 -- Minus
				//521 -- Plus
				
				if (code == 45) {panel.changePlayerPos(-1);} //-
				if (code == 521) {panel.changePlayerPos(1);} //+
			}
			@Override
			public void keyReleased(KeyEvent e) {}
			@Override
			public void keyTyped(KeyEvent e) {}
		});
	}
	
	public void start(WindowEventHandler eventHandler)
	{this.addWindowListener(eventHandler);}

}
