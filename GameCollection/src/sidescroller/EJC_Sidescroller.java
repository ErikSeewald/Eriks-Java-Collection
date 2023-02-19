package sidescroller;
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
import Main.EJC_Interface;
import Main.WindowEventHandler;

public class EJC_Sidescroller extends JFrame implements ActionListener, EJC_Interface
{	
	private static final long serialVersionUID = -7018763596595532898L;
	private static final int index = 8;
	
	private Timer timer;
	private SidescrollerPanel panel;
	private int xSpeed, ySpeed;
	private HashSet<Integer> pressedKeys;
	private JMenuItem seedItem;
	
	public void start(WindowEventHandler eventHandler) 
	{
		this.addWindowListener(eventHandler);
		this.setTitle("Sidescroller");
		panel = new SidescrollerPanel();
		this.add(panel);
		this.pack();
		this.setResizable(false);
		
		//MOVEMENT
		pressedKeys = new HashSet<>();
		
		this.addKeyListener(new KeyListener() 
		{	
			@Override
			public void keyPressed(KeyEvent e) 
			{	
				int code = e.getKeyCode();
				
				if (code == 82) {panel.start(SidescrollerPanel.StartOperations.restart);} //R
				else if (code == 84) {panel.start(SidescrollerPanel.StartOperations.newMap);} //T
				else if (code == 27) {panel.pause();} //ESC
				
				else
				{pressedKeys.add(code);}			
			}
			@Override
			public void keyReleased(KeyEvent e) 
			{pressedKeys.remove(e.getKeyCode());}
			
			@Override
			public void keyTyped(KeyEvent e) 
			{}
		});
		
		timer = new Timer(14, this);
		timer.start();
		
		//MENU BAR
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(new Color (100,100,120));
		menuBar.setBorder(BorderFactory.createLineBorder(new Color (115,115,135), 2));
				
		JMenu fileMenu = new JMenu("Seed");	
		fileMenu.setForeground(new Color (230,230,250));
		fileMenu.setBorder(BorderFactory.createLineBorder(new Color (100,100,120)));
				
		seedItem = new JMenuItem("Set seed");
		seedItem.addActionListener(this);
				
		fileMenu.add(seedItem);
		menuBar.add(fileMenu);
		this.setJMenuBar(menuBar);
		this.setVisible(true);
	}
	
	@Override
	public void stop()
	{timer.stop(); panel = null;}
	
	@Override
	public int getIndex() {return index;}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource() == seedItem)
		{panel.createSeed();}
		
		if (e.getSource() == timer)
		{keyToMovement();}	
	}
	
	private void keyToMovement()
	{
		xSpeed = 0;
		if (pressedKeys.contains(68)) //D
		{xSpeed = 5;} 	
		
		if (pressedKeys.contains(65)) //A
		{xSpeed = -5;}
		
		//JUMPING
		if (pressedKeys.contains(32) && panel.player.jumpAllowed) //SPACE
		{
			ySpeed = -5;
			panel.player.jumpAllowed = false; 
			panel.player.airTime = 35;
		}
			
		//FALLING
		if (panel.player.airTime > 0) {panel.player.airTime--;}	
		if (panel.player.airTime == 10) {ySpeed = 1;}	//for a smoother transition in fallingSpeed
		else if (panel.player.airTime <= 0) {ySpeed = 5;}
		
		panel.update(xSpeed,ySpeed);	
	}
}