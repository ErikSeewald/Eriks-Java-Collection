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
import JumpAndRun.JumpAndRun;
import MouseDodge.MouseDodge;
import ParallelUniverses.ParallelUniverses;
import Particles.Particles;
import PathfindGame.Pathfind;
import PixelCollision.PixelCollision;
import RandBattle.RandBattle;
import RandGrowth.RandGrowth;
import ReflectionDemo.ReflectionDemo;
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
	private JLabel headline = new JLabel("ERIK'S COLLECTION");
	private JLabel guide1 = new JLabel("");
	private JLabel guide2 = new JLabel("");
	private JLabel guide3 = new JLabel("");
	private JLabel guide4 = new JLabel("");
	private JLabel guide5 = new JLabel("");
	
	private JLabel[] guideLabels = {headline, guide1, guide2, guide3, guide4, guide5};
	
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
		
		headline.setBounds(resolution/40,resolution/60,resolution+100,resolution/15);
		headline.setFont(new Font ("", Font.BOLD, resolution/26));
		guide1.setBounds(resolution/40,resolution/15,resolution+100,resolution/15);
		guide2.setBounds(resolution/40,resolution/10,resolution+100,resolution/15);
		guide3.setBounds(resolution/40,(int)(resolution/7.5),resolution+100,resolution/15);
		guide4.setBounds(resolution/40,resolution/6,resolution+100,resolution/15);
		guide5.setBounds(resolution/40,resolution/5,resolution+100,resolution/15);
		for (int i = 0; i < guideLabels.length; i++)
		{setTextBasics(guideLabels[i], i);}
		
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
		String[][] titles = new String[24][6];
			
		titles[0][0] = "INSECTS";
		titles[0][1] ="Observe as the insects try to survive in this maze";
		titles[0][2] ="You can see the current amount of insects in the top left corner.";
		titles[0][3] ="Blue areas are sources of water, red ones are food. The number of insects";
		titles[0][4] ="changes each round in relation to how many are within these sources.";
		titles[0][5] ="Change rate rounds down. (0.01 -> 0  | -0.01 -> -1)";
		
		titles[1][0] = "PARTICLES";
		titles[1][1] ="Press down on your mouse to draw on the particles.";
		titles[1][2] ="Keys:";
		titles[1][3] ="R - Reset";
		
		titles[2][0] = "SIERPINSKI";
		titles[2][1] ="Every turn there is a random chance for the point to move towards one of the";
		titles[2][2] ="3 points of the triangle. Then it leaves a new point halfway on the path to the";
		titles[2][3] ="chosen point. Repeating this will slowly create a sierpinski triangle.";
		
		titles[3][0] = "SUDOKU";
		titles[3][1] ="Use your mouse to click on a square, then type in a number.";
		titles[3][2] ="Keys:";
		titles[3][3] ="S -- Solve | R -- Reset";
		titles[3][4] ="+/- -- Change screen size";
		
		titles[4][0] = "PATHFIND";
		titles[4][1] ="Use W,A,S,D to escape the chasers for as many turns as possible.";
		titles[4][2] ="The chasers only move when you move and can break obstacles in 3 hits.";
		titles[4][3] ="Keys:";
		titles[4][4] ="R -- Reset board | T -- Generate new board";
		titles[4][5] ="+/- -- Change screen size | Use the menu bar to input a board seed.";
		
		titles[5][0] = "PARALLEL UNIVERSES";
		titles[5][1] ="The red point represents your position in the universe.";
		titles[5][2] ="The green point represents your position in the parallel universes.";
		titles[5][3] ="Move with W,A,S,D and hold down shift before pressing the keys to move faster.";
		titles[5][4] ="Hide the parallel universes by pressing H";
		
		titles[6][0] = "REFLECTION DEMO";
		titles[6][1] ="Move the ray origin with your mouse held down and rotate it with the mouse wheel.";
		titles[6][2] ="Keys:";
		titles[6][3] ="1 - Less reflections  |  2 - More reflections";
		titles[6][4] ="3 - Less rotation speed  |  4 - More rotation speed";
		
		titles[7][0] = "MOUSE DODGE";
		titles[7][1] = "Move your mouse to dodge the enemies.";
		titles[7][2] = "Press F to switch dark mode on or off.";
		titles[7][3] = "Other controls are displayed ingame.";
		
		titles[8][0] = "2D SIDESCROLLER";
		titles[8][1] ="Outrun the screen and don't fall into the lava. Grab on to ceilings by";
		titles[8][2] ="holding space. Use the menu bar to input a level seed.";
		titles[8][3] ="Keys:";
		titles[8][4] ="Use W,A,S,D to move and press space to jump.";
		titles[8][5] ="R -- Restart | T -- Generate new map | ESC -- Pause";
		
		titles[9][0] = "PIXEL COLLISION";
		titles[9][1] ="Anything you drawn on screen will become a collision object.";
		titles[9][2] ="USe the Object Menu in the top left to drawn an object into the rectangle that";
		titles[9][3] ="appears, save it and then move it with W,A,S,D. Use shift to move faster.";
		titles[9][4] ="Use the Pixel Menu to change the size of the pixel grid";
		titles[9][5] ="+/- - Change screen size| F - Fly mode | R - Reset | Shift click - Erase pixel";
		
		titles[10][0] = "CLOTH SIM";
		titles[10][1] ="Click anywhere on screen to create a point, CTRL click to lock it in place.";
		titles[10][2] ="Click on a point again to select it. When you have two points selected, press";
		titles[10][3] ="C to connect them. Start or stop the simulation with S and reset it with R.";
		titles[10][4] ="Move point = Press mouse down and move | Cut point = Hold shift, press down and then move";
		titles[10][5] ="over it | Delete last connection = CTRL Z | Save/load layouts with the menu bar";
		
		titles[11][0] = "GRAVITY VECTORS";
		titles[11][1] ="Move gravity points with the mouse held down";
		titles[11][2] ="Keys:";
		titles[11][3] ="1 - Add another gravity point | 2 - Remove last gravity point";
		titles[11][4] ="+/- - Change screen size";
		titles[11][5] ="G - Activate gradient rendering | N - Turn off arrow normalization";
		
		titles[12][0] = "BALLOON SHOOTING";
		titles[12][1] ="I wanted to recreate some aspects of the original bloons game in Java with";
		titles[12][2] ="my own sprite renderer. Shoot the balloons using the slingshot with the";
		titles[12][3] ="mouse held down. Press the right arrow key to load the next level,";
		titles[12][4] ="PREVIOUS LEVEL. R TO RESTART THE LEVEL | G TO SHOW THE LEVEL GRID | USE";
		titles[12][4] ="left for the previous level. R - Restart level | G - Show level grid";
		titles[12][5] ="Use shift press to move the entire slingshot, not just the projectile.";
	
		titles[13][0] = "RAND BATTLE";
		titles[13][1] = "NPCs with random sizes, hp, damage, movement speed and projectile speed";
		titles[13][2] = "are generated. They pick a random target and try to shoot it until it is";
		titles[13][3] = "DEAD, THEN THEY PICK A NEW ONE. WHO WILL BE THE LAST SURVIVOR?";
		titles[13][3] = "dead, then they pick a new one. Who will be victorious?";
		titles[13][4] = "Keys:";
		titles[13][5] = "R - Restart | S - Show stats | H - Only show Health";
		
		titles[14][0] = "RAND GROWTH";
		titles[14][1] = "Once you have pressed the start button, press and move your mouse";
		titles[14][2] = "across the screen to spawn pixels and watch them try to survive and grow";
		titles[14][3] = "according to the rules you set with the UI included in the program.";
		
		titles[15][0] = "SNAKES AND LADDERS";
		titles[15][1] = "A classic game of Snakes And Ladders. Set the amount of players and press 'Start'.";
		titles[15][2] = "Once you have pressed on the roll button you can either move your player piece with";
		titles[15][3] = "the mouse, or you can press on Auto Move and have the piece move for you.";
		titles[15][4] = "Try to reach square 53!";
		
		
		headline.setText(titles[game][0]);
		guide1.setText(titles[game][1]);
		guide2.setText(titles[game][2]);
		guide3.setText(titles[game][3]);
		guide4.setText(titles[game][4]);
		guide5.setText(titles[game][5]);
	}
	
	public static void restart() throws IOException, URISyntaxException
	{
		System.gc();
		
		closeTurn++;
		if (!(closeTurn == 10)) {return;} //only restart the JVM (Build new JAR) after frames have been closed 10 times
		
		final String javaBin = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
		final File currentJar = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI());

		/* is it a jar file? */
		if(!currentJar.getName().endsWith(".jar"))
	    return;

		/* Build command: java -jar application.jar */
		final ArrayList<String> command = new ArrayList<String>();
		command.add(javaBin);
	  	command.add("-jar");
	  	command.add(currentJar.getPath());

	  	final ProcessBuilder builder = new ProcessBuilder(command);
	  	builder.start();
	  	System.exit(0);
	}
}