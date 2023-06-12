package cheeseBreeder;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import Main.EJC_Interface;
import Main.WindowEventHandler;

public class EJC_CheeseBreeder extends JFrame implements EJC_Interface, ActionListener
{
	private static final long serialVersionUID = 4927061210031934469L;
	private static final int index = 18;

	private BreederPanel panel;
	
	private JMenuItem[] cheese_items = 
	{
			new JMenuItem("Emmentaler"), new JMenuItem("Gouda"), new JMenuItem("Camembert"), 
			new JMenuItem("Cheddar"), new JMenuItem("Mozarella"), new JMenuItem("Blue Cheese"),
			new JMenuItem("Pink Cheese"), new JMenuItem("Pommier"), new JMenuItem("Brownie"),
			new JMenuItem("Cheese Coal")
	};
	
	@Override
	public void start(WindowEventHandler eventHandler) 
	{
		this.addWindowListener(eventHandler);
		this.setTitle("Cheese Breeder");
		this.setResizable(false);
		
		panel = new BreederPanel();
		this.add(panel);
		this.pack();
		
		this.addKeyListener(new KeyListener() 
		{
			@Override
			public void keyTyped(KeyEvent e) 
			{
				if (e.getKeyChar() == 'r') {panel.reset();}
				if (e.getKeyChar() == 'b') {panel.breed();}
			}

			@Override
			public void keyPressed(KeyEvent e) {}
			@Override
			public void keyReleased(KeyEvent e) {}

		});
		
		//MENU BAR
		JMenuBar menuBar = new JMenuBar();
		JMenu cheese_menu = new JMenu ("Elemental cheeses");

		for (JMenuItem item : cheese_items)
		{setItemBasics(item, cheese_menu);}
		
		cheese_menu.setForeground(new Color (230,230,250));
		cheese_menu.setBorder(BorderFactory.createLineBorder(new Color (100,100,120)));
		menuBar.add(cheese_menu);
			
		menuBar.setBackground(new Color (100,100,120));
		menuBar.setBorder(BorderFactory.createLineBorder(new Color (115,115,135), 2));
		this.setJMenuBar(menuBar);

		this.setVisible(true);
	}
	
	public void setItemBasics(JMenuItem item, JMenu menu)
	{
		item.addActionListener(this);
		menu.add(item);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		for (JMenuItem item : cheese_items)
		{
			if (e.getSource() == item)
			{
				panel.spawnCheese(item.getText());
				return;
			}
		}
		
	}

	@Override
	public void stop() 
	{	
		
	}

	@Override
	public int getIndex() 
	{return index;}
}