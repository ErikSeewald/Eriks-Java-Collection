package Main;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;

import PathfindGame.Pathfind;
import Sierpinski.Sierpinski;

public class SelectionFrame 
{
	String gameName;
	
	public void start(String gameName, WindowEventHandler eventHandler)
	{
		this.gameName = gameName;
		
		JFrame selection = new JFrame("Selection");
		JLabel headline = new JLabel("PICK A GAMEMODE BY PRESSING THE CORRESPONDING KEY");
		headline.setForeground(new Color(220,220,240));
		JLabel mode1 = new JLabel();
		mode1.setForeground(new Color(220,220,240));
		JLabel mode2 = new JLabel();
		mode2.setForeground(new Color(220,220,240));
		JLabel mode3 = new JLabel();
		mode3.setForeground(new Color(220,220,240));
		selection.getContentPane().setBackground(new Color(50,50,70));
		selection.setLayout(null);
		headline.setBounds(20,5,500,40);
		mode1.setBounds(20,40,500,40);
		mode2.setBounds(20,70,500,40);
		mode3.setBounds(20,100,500,40);
		selection.add(headline);
		selection.add(mode1);
		selection.add(mode2);
		selection.add(mode3);
		selection.setSize(500, 200);
		selection.setVisible(true);
		
		switch(gameName)
		{
			case "Pathfind":  mode1.setText("1 - NORMAL"); mode2.setText("2 - TRAIL"); mode3.setText("3 - DEMO");
			break;
			case "Sierpinski":mode1.setText("1 - SLOW"); mode2.setText("2 - NORMAL"); mode3.setText("3 - FAST");
			break;
			
		}
		
		selection.addKeyListener(new KeyListener() 
		{
			@Override
			public void keyTyped(KeyEvent e) 
			{	
				char key = e.getKeyChar();
				
				int mode = key - '0'; //char to int
				
				switch (gameName)
				{
					case "Pathfind": new Pathfind(mode); selection.dispatchEvent(new WindowEvent(selection, WindowEvent.WINDOW_CLOSING));
					break;
					case "Sierpinski": new Sierpinski(eventHandler, mode); selection.dispatchEvent(new WindowEvent(selection, WindowEvent.WINDOW_CLOSING));
					break;
				}
					
			}
			@Override
			public void keyPressed(KeyEvent e) {}
			@Override
			public void keyReleased(KeyEvent e) {}		
		});
	}
}
