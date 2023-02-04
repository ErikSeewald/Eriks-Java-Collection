package Main;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import BloonShoot.BloonShooting;
import ClothSim.ClothSim;
import GravityVectors.GravityVectors;
import Insects.Insects;
import MouseDodge.MouseDodge;
import ParallelUniverses.ParallelUniverses;
import Particles.Particles;
import PathfindGame.Pathfind;
import PixelCollision.PixelCollision;
import RandBattle.RandBattle;
import RandGrowth.RandGrowth;
import ReflectionDemo.ReflectionDemo;
import Sidescroller.JumpAndRun;
import SnakesAndLadders.SnakesAndLadders;
import Sudoku.Sudoku;

public class MainMenu extends JFrame implements MouseListener
{
	private static final long serialVersionUID = 1454253443346436L;

	private int resolution = 700;
	
	//BUTTONS
	private int buttonSizeX;
	private int buttonSizeY;
	
	private static final JLabel[] gameButtons = 
	{
			new JLabel("      Insects"), new JLabel("     Particles"), new JLabel("    Sierpinski"), new JLabel("      Sudoku"),
			new JLabel("      Pathfind"), new JLabel("    Parallel U."), new JLabel("    Reflection"), new JLabel(" Mouse Dodge"), 
			new JLabel("   Sidescroller"), new JLabel(" Pixel Collision"), new JLabel("    Cloth Sim"), new JLabel(" Gravity Vects"),
			new JLabel("  Bloon Shoot"), new JLabel("  Rand Battle"), new JLabel(" Rand Growth"), new JLabel("     Ladders"), 
			new JLabel(""), new JLabel(""), new JLabel(""), new JLabel(""), 
			new JLabel(""), new JLabel(""), new JLabel(""), new JLabel(""), 
	};
	
	private boolean buttonSizeIncreased = false;
	
	private static final Color text_color = new Color(210,210,230);
	private static final Color b_color_basic = new Color(75,75,105);
	private static final Color b_color_highlight = new Color(110,110,130);
	private static final Color b_color_pressed = new Color(130,130,150);
	
	//LABELS
	private JLabel[] guideLabels = 
	{new JLabel("ERIK'S COLLECTION"), new JLabel(""), new JLabel(""), new JLabel(""), new JLabel(""), new JLabel("")};
	
	private static final GameTitles[] titles = 
	{
			GameTitles.Insects, GameTitles.Particles, GameTitles.Sierpinski, GameTitles.Sudoku, 
			GameTitles.Pathfind, GameTitles.ParralelUniverses, GameTitles.ReflectionDemo, GameTitles.MouseDodge, 
			GameTitles.Sidescroller, GameTitles.PixelCollision,GameTitles.ClothSim, GameTitles.GravityVectors, 
			GameTitles.BalloonShooting, GameTitles.RandBattle, GameTitles.RandGrowth, GameTitles.SnakesAndLadders
	};
	
	//CONTROL
	private WindowEventHandler eventHandler = new WindowEventHandler();
	public static boolean[] isOpened = new boolean[24]; 	//to check whether an instance of the game is already open
	private static int windowsClosedCount = 0;				//how many windows have been closed since the JVM has been launched
	
	public static final ImageIcon img = new ImageIcon("src\\Main\\logo.jpg");
	
	MainMenu()
	{
		this.setTitle("Menu");
		this.setIconImage(img.getImage());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.getContentPane().setBackground(new Color(40,40,55));
		
		this.setLayout(null);
		initialize();
		this.setVisible(true);
	}
	
	private void initialize()
	{
		this.setSize(resolution, resolution);
		
		//LABELS
		final double[] divisors = {60, 15,10,7.5, 6, 5};
		for (int i = 0; i < guideLabels.length; i++)
		{
			guideLabels[i].setBounds(resolution/40,	(int) (resolution/divisors[i]),	resolution+100,	resolution/15);
			guideLabels[i].setForeground(text_color);
			
			if (i == 0) {guideLabels[i].setFont(new Font ("", Font.BOLD, resolution/26));}
			else {guideLabels[i].setFont(new Font ("", Font.BOLD, resolution/42));}
			
			this.add(guideLabels[i]);
		}
		
		//BUTTONS
		buttonSizeX = resolution/6;
		buttonSizeY = resolution/15;
		
		int x = 0, y = 0;
		for (JLabel button : gameButtons)
		{
			button.setBounds((resolution/10) + x*(resolution/5), (resolution/3) + y*(resolution/10),buttonSizeX, buttonSizeY);
			setButtonBasics(button);
			
			x++;
			if (x > 3) {x = 0; y++;} //move to next row after 4 buttons have been drawn
		}
	}

	//BUTTONS
	private void setButtonBasics(JLabel x)
	{
		x.setFont(new Font ("", Font.PLAIN, resolution/42));
		x.setForeground(text_color);
		x.setBackground(b_color_basic);
		x.setOpaque(true);
		x.setBorder(BorderFactory.createLineBorder(new Color(100,100,130), resolution/300));
		x.addMouseListener(this);
		this.add(x);
	}

	private void openWindow(JLabel button)
	{
		if (button.getText() == "") {return;}
		
		int index = getButtonIndex(button);
		if (isOpened[index]) {return;}
		
		switch (index)
		{
			case 0: new Insects().start(eventHandler); break;
			case 1: new Particles().start(eventHandler); break;
			case 2: new SelectionFrame().start("Sierpinski", eventHandler); break;
			case 3: new Sudoku().start(eventHandler); break;
			case 4: new Pathfind().start(eventHandler); break;
			case 5: new ParallelUniverses().start(eventHandler); break;
			case 6: new ReflectionDemo().start(eventHandler); break;
			case 7: new MouseDodge().start(eventHandler); break;
			case 8: new JumpAndRun().start(eventHandler); break;
			case 9: new PixelCollision().start(eventHandler); break;
			case 10: new ClothSim().start(eventHandler); break;
			case 11: new GravityVectors().start(eventHandler); break;
			case 12: new BloonShooting().start(eventHandler); break;
			case 13: new RandBattle().start(eventHandler); break;
			case 14: new RandGrowth().start(eventHandler); break;
			case 15: new SnakesAndLadders().start(eventHandler); break;
		}
		
		isOpened[index] = true;		
	}
	
	private int getButtonIndex(JLabel button)
	{
		int index = 0;
		for (JLabel b : gameButtons)
		{
			if (button == b) {break;} 
			index++;
		}
		return index;
	}
	
	private void buttonAnimation(JLabel button, int change)
	{
		if ((change > 0 && buttonSizeIncreased) || (change < 0 && !buttonSizeIncreased)) {return;} //don't go beyond max/min size
		buttonSizeIncreased = change > 0;
		
		button.setBounds
		(
		 button.getX()-(change/2), button.getY()-(change/2),
		 button.getWidth()+change, button.getHeight()+change
		);
	}
	
	private void changeInformation(int game)
	{	
		for(int i = 0; i < guideLabels.length; i++)
		{guideLabels[i].setText(titles[game].guide[i]);}
	}
	
	//MOUSE
	@Override
	public void mouseClicked(MouseEvent e) 
	{
		JLabel button = (JLabel) e.getSource();
		buttonAnimation(button, -(buttonSizeX /25));
		button.setBackground(b_color_highlight);
		
		openWindow(button);
	}
	
	@Override
	public void mousePressed(MouseEvent e) 
	{
		JLabel button = (JLabel)e.getSource();
		if (buttonSizeIncreased) {buttonAnimation(button, -(buttonSizeX /25));}
		button.setBackground(b_color_pressed);
	}

	@Override
	public void mouseReleased(MouseEvent e) 
	{}

	@Override
	public void mouseEntered(MouseEvent e) 
	{
		JLabel button = (JLabel)e.getSource();
		buttonAnimation(button, (buttonSizeX /25));
		button.setBackground(b_color_highlight);
		
		if (button.getText() != "") 
		{changeInformation(getButtonIndex(button));}
	}

	@Override
	public void mouseExited(MouseEvent e) 
	{
		JLabel button = (JLabel)e.getSource();
		if (buttonSizeIncreased) {buttonAnimation(button, -(buttonSizeX /25));}
		button.setBackground(b_color_basic);	
	}
	
	//CONTROL
	public void changeSize(int amount)
	{
		resolution+=amount;
		initialize();
	}
	
	public static void gameClosed() throws IOException, URISyntaxException
	{
		System.gc();
		
		windowsClosedCount++;
		if (windowsClosedCount < 10) {return;} //REBUILD THE JAR AFTER 10 GAMES TO AVOID ERORRS
		
		final String javaBin = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
		final File currentJar = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI());

		//is it a jar file?
		if(!currentJar.getName().endsWith(".jar"))
	    return;

		//Build command: java -jar application.jar
		final ArrayList<String> command = new ArrayList<String>();
		command.add(javaBin);
	  	command.add("-jar");
	  	command.add(currentJar.getPath());

	  	final ProcessBuilder builder = new ProcessBuilder(command);
	  	builder.start();
	  	System.exit(0);
	}
}