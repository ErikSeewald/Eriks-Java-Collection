package pixelCollision;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.Timer;

import ejcMain.EJC_Game;
import ejcMain.EJC_WindowEventHandler;
import ejcMain.EJC_GUI.EJC_MenuBar;

public class EJC_PixelCollision extends JFrame implements EJC_Game, ActionListener
{
	private static final long serialVersionUID = 1188212923048325213L;
	
	private HashSet<Integer> pressedKeys = new HashSet<>();
	private KeyHandler keyHandler;
	
	private JMenuItem objectNew  = new JMenuItem("New");
	private JMenuItem objectSave  = new JMenuItem("Save");
	private JMenuItem[] pixel_items = 
	{new JMenuItem("1"), new JMenuItem("2"), new JMenuItem("3"), new JMenuItem("4"), new JMenuItem("5")};
	
	private Timer timer;
	private PixelCollisionPanel panel;
	
	public void start(EJC_WindowEventHandler eventHandler) 
	{
		this.addWindowListener(eventHandler);
		this.setTitle("Pixel Collision");
		this.setResizable(false);
		panel = new PixelCollisionPanel();
		timer = new Timer(20, this);
		
		this.addKeyListener(new KeyListener() 
		{
			@Override
			public void keyPressed(KeyEvent e) 
			{
				int code = e.getKeyCode();
					
				if (code == 82) {panel.start();} 	//R
				else if (code == 70) {keyHandler.flyMode();}		 //F	
				else if (code == 45) {panel.changeSize(-30); pack();} 	//-
				else if (code == 521) {panel.changeSize(30); pack();} 	//+
				
				else {pressedKeys.add(code);}
			}
			@Override
			public void keyReleased(KeyEvent e) 
			{pressedKeys.remove(e.getKeyCode());}
			
			@Override
			public void keyTyped(KeyEvent e) {}
		});
		
		keyHandler = new KeyHandler(pressedKeys, panel);
		
		//MENU BAR
		EJC_MenuBar menuBar = new EJC_MenuBar(this);
		menuBar.addEJCMenu("Object", new JMenuItem[] {objectNew, objectSave});
		menuBar.addEJCMenu("Pixel size", pixel_items);
		menuBar.setTimersToControl(new Timer[] {timer});
		menuBar.setKeyListToControl(pressedKeys);

		for (JMenuItem item : pixel_items)
		{item.addActionListener(this);}
		
		objectNew.addActionListener(this);
		objectSave.addActionListener(this);
		
		this.add(panel);
		this.pack();	
		this.setVisible(true);
		timer.start();
	}
	
	public void setMenuBasics(JMenu menu, JMenuBar menuBar)
	{
		menu.setForeground(new Color (230,230,250));
		menu.setBorder(BorderFactory.createLineBorder(new Color (100,100,120)));
		menuBar.add(menu);
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource() == timer)
		{keyHandler.update();}
		
		else if (e.getSource() == objectNew)
		{panel.startCreationMode(); pressedKeys.removeAll(pressedKeys);}
		
		else if (e.getSource() == objectSave)
		{panel.endCreationMode(); pressedKeys.removeAll(pressedKeys);}
		
		else
		{	
			for (int i = 0; i < pixel_items.length; i++)
			{
				if (e.getSource() != pixel_items[i]) 
				{continue;}
				
				panel.setPixelSize(i + 1);
				return;
			}
		}
	}
	
	@Override
	public void stop()
	{timer.stop(); panel = null; pressedKeys = null;}
}