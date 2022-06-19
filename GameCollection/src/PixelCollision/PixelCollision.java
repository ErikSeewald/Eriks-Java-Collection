package PixelCollision;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.Timer;

import Main.MainMenu;
import Main.WindowEventHandler;

public class PixelCollision 
{
	static ArrayList<Integer> pressedKeys = new ArrayList<>();
	//use the ArrayList instead of the standard keyPressed to avoid delay when changing inputs
	
	static PixelCollisionPanel panel;
	
	static int fallingSpeed;
	static int timeUntilFall;
	static int timeUntilJumpAllowed;
	static boolean jumpAllowed = true;
	
	JMenuItem objectNew  = new JMenuItem("New");		//create area in which a new object can be drawn
	JMenuItem objectSave  = new JMenuItem("Save");		//save object in that box -> now controllable
	
	//Items for picking the size of a pixel (4 -> 4 by 4 real pixels for one pixel)
	JMenuItem pix1  = new JMenuItem("1");
	JMenuItem pix2  = new JMenuItem("2");
	JMenuItem pix3  = new JMenuItem("3");
	JMenuItem pix4  = new JMenuItem("4");
	JMenuItem pix5  = new JMenuItem("5");
	
	JMenuItem[] pixelSizes = {pix1, pix2, pix3, pix4, pix5};
	
	ActionListener actionListener; //for MenuBar
	
	//static so it can only exist once and be called from other classes in GameCollection
	static Timer timer = new Timer(20, new ActionListener() 
	{	@Override
		public void actionPerformed(ActionEvent e) 
		{
			int x = 0;
			int y = 0;	
			
			boolean move = false; //don't do movement calculations if there has been no change in position
			int loop = 1;		  //how many times should the movement calculation run
			//(Can't just increase the movement speed because that would cause the collision detection to break)
			
			if (pressedKeys.contains(68)) {x = panel.PIXEL_SIZE;  move = true;} 	//D
			if (pressedKeys.contains(65)) {x = -panel.PIXEL_SIZE; move = true;} 	//A
			
			if (panel.flyMode)
			{
				if (pressedKeys.contains(87)) {y = -panel.PIXEL_SIZE; move = true;} 		//W
				if (pressedKeys.contains(83)) {y = panel.PIXEL_SIZE; move = true;} 			//S
				
				if (pressedKeys.contains(16)) //SHIFT
				{loop = 4;}
			
				if(move) 
				{
					for (int i = 0; i < loop; i++) 
					{panel.move(x,y,0);} 
				}
			}
			
			else 
			{
				if (panel.collision) {fallingSpeed = panel.PIXEL_SIZE;} //remove all upward speed when a roof is hit
				
				if (pressedKeys.contains(32) && jumpAllowed) //SPACE
				{
					fallingSpeed = -panel.PIXEL_SIZE; 
					jumpAllowed = false; timeUntilFall = 20; 
				}
				 
				if (timeUntilFall > 0) {timeUntilFall--;}	//counting down
				
				if (timeUntilFall == 5) {fallingSpeed = 0;}	//for a smoother transition in fallingSpeed
				else if (timeUntilFall == 0) {fallingSpeed = panel.PIXEL_SIZE;} //back to normal
				
				if (panel.collision) {jumpAllowed = true;}
				
				loop = (5 - panel.PIXEL_SIZE)+1; //adjust the movement speed to pixelSize
				
				for (int i = 0; i < loop; i++) 
				{panel.move(x,y,fallingSpeed);} 
			}	
		}	
	});
	
	public void start(WindowEventHandler eventHandler) 
	{
		
		JFrame frame = new JFrame("Pixel Collision");
		frame.setIconImage(MainMenu.img.getImage());
		panel = new PixelCollisionPanel();
		
		frame.addKeyListener(new KeyListener() 
		{
			@Override
			public void keyTyped(KeyEvent e) 
			{}
			
			@Override
			public void keyPressed(KeyEvent e) 
			{
				int code = e.getKeyCode();
				if (!(pressedKeys.contains(code))) {pressedKeys.add(code);}
					
				if (code == 82) {panel.start();} 	//R
				else if (code == 70) {panel.flyMode = !panel.flyMode;}		 //F
				
				else if (code == 45) {panel.saveObject(); panel.changeSize(-10); frame.pack();} 	//-
				else if (code == 521) {panel.saveObject(); panel.changeSize(10); frame.pack();} 	//+
				//saveObject to avoid dealing with object scaling -> just resets it	
			}
			@Override
			public void keyReleased(KeyEvent e) 
			{
				int x = pressedKeys.indexOf(e.getKeyCode());
				if (x > -1) {pressedKeys.remove(x);} //out of bounds exception for alt + key
			}		
		});
		
		JMenuBar menuBar = new JMenuBar();
		JMenu objectMenu= new JMenu("Object");
		JMenu pixelMenu = new JMenu ("Pixel Size");
		
		actionListener = new ActionListener()
		{	@Override
			public void actionPerformed(ActionEvent e) 
			{
				if (e.getSource() == objectNew)
				{panel.newObject();}
				
				else if (e.getSource() == objectSave)
				{panel.saveObject();}
				
				else
				{
					if (e.getSource() == pix1)
					{panel.PIXEL_SIZE = 1; }
					else if (e.getSource() == pix2)
					{panel.PIXEL_SIZE = 2;}
					else if (e.getSource() == pix3)
					{panel.PIXEL_SIZE = 3;}
					else if (e.getSource() == pix4)
					{panel.PIXEL_SIZE = 4;}
					else if (e.getSource() == pix5)
					{panel.PIXEL_SIZE = 5;}
					
					panel.start(); panel.newObject();
				}
			}
		};
		
		for (JMenuItem item : pixelSizes)
		{setItemBasics(item,pixelMenu);}
		
		setItemBasics(objectNew, objectMenu);
		setItemBasics(objectSave, objectMenu);
		
		setMenuBasics(objectMenu, menuBar);
		setMenuBasics(pixelMenu, menuBar);
			
		menuBar.setBackground(new Color (100,100,120));
		menuBar.setBorder(BorderFactory.createLineBorder(new Color (115,115,135), 2));
		frame.setJMenuBar(menuBar);
		
		frame.addWindowListener(eventHandler);
		frame.setResizable(false);
		frame.add(panel);
		frame.pack();
			
		frame.setVisible(true);
		timer.start();
	}
	
	public void setItemBasics(JMenuItem item, JMenu menu)
	{
		item.addActionListener(actionListener);
		menu.add(item);
	}
	
	public void setMenuBasics(JMenu menu, JMenuBar menuBar)
	{
		menu.setForeground(new Color (230,230,250));
		menu.setBorder(BorderFactory.createLineBorder(new Color (100,100,120)));
		menuBar.add(menu);
	}
	
	public static void stop()
	{if (timer.isRunning()) {timer.stop();}}
}