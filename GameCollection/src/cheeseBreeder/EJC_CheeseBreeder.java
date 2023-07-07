package cheeseBreeder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import Main.EJC_GUI.EJC_MenuBar;
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
			new JMenuItem("Cheese Coal"), new JMenuItem("Coffee"), new JMenuItem("Fraise")
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
		EJC_MenuBar menuBar = new EJC_MenuBar(this);
		menuBar.addEJCMenu("Elemental cheeses", cheese_items);

		for (JMenuItem item : cheese_items)
		{item.addActionListener(this);}

		this.setVisible(true);
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
	{}

	@Override
	public int getIndex() 
	{return index;}
}