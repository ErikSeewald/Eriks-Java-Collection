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

import BloonShooting.BloonShooting;
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

	public static boolean[] hasWindowOpen = new boolean[24]; //to check whether an instance of this game is already open
	
	private static final Color textColor = new Color(210,210,230);
	private static final Color buttonColor1 = new Color(75,75,105);
	private static final Color buttonColor2 = new Color(110,110,130);
	private static final Color buttonColor3 = new Color(130,130,150);
	
	
	private boolean buttonSizeIncreased = false;
	
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
	
	private WindowEventHandler eventHandler;
	private static int closeTurn = 0;		//how many windows have been closed since the JVM has been launched
	
	public static final ImageIcon img = new ImageIcon("src\\Main\\logo.jpg");
	
	MainMenu()
	{
		eventHandler = new WindowEventHandler();
		
		this.setIconImage(img.getImage());
		
		this.setTitle("Menu");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.getContentPane().setBackground(new Color(40,40,55));
		
		this.setLayout(null);
		start();
		
		this.setVisible(true);
	}
	
	public void start()
	{
		this.setSize(resolution, resolution);
		
		guideLabels[0].setBounds(resolution/40,resolution/60,resolution+100,resolution/15);
		guideLabels[0].setFont(new Font ("", Font.BOLD, resolution/26));
		setTextBasics(guideLabels[0], 0);
		
		final double[] divs = {15,10,7.5, 6, 5};
		for (int i = 1; i < guideLabels.length; i++)
		{
			guideLabels[i].setBounds(resolution/40,(int) (resolution/divs[i-1]),resolution+100,resolution/15);
			setTextBasics(guideLabels[i], i);
		}
		
		buttonSizeX = resolution/6;
		buttonSizeY = resolution/15;
		
		int j = 0, k = 0;
		for (int i = 0; i < gameButtons.length; i++)
		{
			if (j > 3) {j = 0; k++;} //k++ -> move to next row after 4 buttons have been drawn
			gameButtons[i].setBounds((resolution/10) + j*(resolution/5), (resolution/3) + k*(resolution/10),buttonSizeX, buttonSizeY);
			j++;
			setButtonBasics(gameButtons[i]);
		}	
	}
	
	public void changeSize(int amount)
	{
		resolution+=amount;
		start();
		repaint();
	}
	
	public void setButtonBasics(JLabel x)
	{
		x.setFont(new Font ("", Font.PLAIN, resolution/42));
		x.setForeground(textColor);
		x.setBackground(buttonColor1);
		x.setOpaque(true);
		x.setBorder(BorderFactory.createLineBorder(new Color(100,100,130), resolution/300));
		x.addMouseListener(this);
		this.add(x);
	}
	
	public void setTextBasics(JLabel x, int index)
	{
		x.setForeground(textColor);
		if (index != 0) {x.setFont(new Font ("", Font.BOLD, resolution/42));}
		this.add(x);
	}

	@Override
	public void mouseClicked(MouseEvent e) 
	{	
		JLabel button = (JLabel)e.getSource();	
		
		buttonAnimation(button, -(buttonSizeX /25));
		button.setBackground(buttonColor2);

		//IMPLEMENT SOMETHING TO PREVENT PLAYERS FROM OPENING TWO OF THE SAME WINDOW
			
		int index = 0;
		for (int i = 0; i < gameButtons.length; i++)
		{if (button != gameButtons[i]) {index++;} else {break;}}
		if (button.getText() == "") {return;}
		
		if (hasWindowOpen[index]) {return;} //don't open another instance of the same game
		
		switch (index)
		{
			case 0: Insects insects = new Insects(); insects.start(eventHandler);
			break;
			case 1: Particles particles = new Particles(); particles.start(eventHandler);
			break;
			case 2: SelectionFrame sierpinski = new SelectionFrame(); sierpinski.start("Sierpinski", eventHandler);
			break;
			case 3: Sudoku sudoku = new Sudoku(); sudoku.start(eventHandler);
			break;
			case 4: Pathfind pathfind =  new Pathfind(); pathfind.start(eventHandler);
			break;
			case 5: ParallelUniverses parallelUniverses = new ParallelUniverses();parallelUniverses.start(eventHandler);
			break;
			case 6: ReflectionDemo reflectionDemo = new ReflectionDemo(); reflectionDemo.start(eventHandler);
			break;
			case 7: MouseDodge mouseDodge = new MouseDodge(); mouseDodge.start(eventHandler);
			break;
			case 8: JumpAndRun jumpAndRun = new JumpAndRun(); jumpAndRun.start(eventHandler);
			break;
			case 9: PixelCollision pixelCollision = new PixelCollision(); pixelCollision.start(eventHandler);
			break;
			case 10: ClothSim clothSim = new ClothSim(); clothSim.start(eventHandler);
			break;
			case 11: GravityVectors gravityVectors = new GravityVectors(); gravityVectors.start(eventHandler);
			break;
			case 12: BloonShooting bloonShooting = new BloonShooting(); bloonShooting.start(eventHandler);
			break;
			case 13: RandBattle randBattle = new RandBattle(); randBattle.start(eventHandler);
			break;
			case 14: RandGrowth randGrowth = new RandGrowth(); randGrowth.start(eventHandler);
			break;
			case 15: SnakesAndLadders snakesAndLadders = new SnakesAndLadders(); snakesAndLadders.start(eventHandler);
			break;
		}
		
		hasWindowOpen[index] = !hasWindowOpen[index];		
	}
	
	@Override
	public void mousePressed(MouseEvent e) 
	{
		JLabel button = (JLabel)e.getSource();
		if (buttonSizeIncreased) {buttonAnimation(button, -(buttonSizeX /25));}
		button.setBackground(buttonColor3);
	}

	@Override
	public void mouseReleased(MouseEvent e) 
	{}

	@Override
	public void mouseEntered(MouseEvent e) 
	{
		JLabel button = (JLabel)e.getSource();
		buttonAnimation(button, (buttonSizeX /25));
		button.setBackground(buttonColor2);
		
		int index = 0;
		for (int i = 0; i < gameButtons.length; i++)
		{if (button != gameButtons[i]) {index++;} else {break;}}
		if (button.getText() == "") {return;}
		
		changeInformation(index);
		
	}

	@Override
	public void mouseExited(MouseEvent e) 
	{
		JLabel button = (JLabel)e.getSource();
		if (buttonSizeIncreased) {buttonAnimation(button, -(buttonSizeX /25));}
		button.setBackground(buttonColor1);
		
	}
	
	public void buttonAnimation(JLabel button, int change)
	{
		if ((change > 0 && buttonSizeIncreased) || (change < 0 && !buttonSizeIncreased)) {return;} //don't go beyond max/min size
		
		if (change > 0) {buttonSizeIncreased = true;}
		else {buttonSizeIncreased = false;}
		
		button.setBounds
		(
		 button.getX()-(change/2), button.getY()-(change/2),
		 button.getWidth()+change, button.getHeight()+change
		);
	}
	
	public void changeInformation(int game)
	{	
		for(int i = 0; i < guideLabels.length; i++)
		{guideLabels[i].setText(titles[game].guide[i]);}
	}
	
	public static void restart() throws IOException, URISyntaxException
	{
		System.gc();
		
		closeTurn++;
		if (!(closeTurn == 10)) {return;} //only restart the JVM (Build new JAR) after frames have been closed 10 times
		
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