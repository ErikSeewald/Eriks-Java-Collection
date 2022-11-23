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
	
	private JLabel gameButton1 = new JLabel("      Insects");
	private JLabel gameButton2 = new JLabel("     Particles");
	private JLabel gameButton3 = new JLabel("    Sierpinski");
	private JLabel gameButton4 = new JLabel("      Sudoku");
	private JLabel gameButton5 = new JLabel("      Pathfind");
	private JLabel gameButton6 = new JLabel("    Parallel U.");
	private JLabel gameButton7 = new JLabel("    Reflection");
	private JLabel gameButton8 = new JLabel(" Mouse Dodge");
	private JLabel gameButton9 = new JLabel("   Sidescroller");
	private JLabel gameButton10 = new JLabel("    Speedrun");
	private JLabel gameButton11 = new JLabel(" Pixel Collision");
	private JLabel gameButton12 = new JLabel("    Cloth Sim");
	private JLabel gameButton13 = new JLabel(" Gravity Vects");
	private JLabel gameButton14 = new JLabel("  Bloon Shoot");
	private JLabel gameButton15 = new JLabel("  Rand Battle");
	private JLabel gameButton16 = new JLabel(" Rand Growth");
	private JLabel gameButton17 = new JLabel("     Ladders");
	private JLabel gameButton18 = new JLabel("");
	private JLabel gameButton19 = new JLabel("");
	private JLabel gameButton20 = new JLabel("");
	private JLabel gameButton21 = new JLabel("");
	private JLabel gameButton22 = new JLabel("");
	private JLabel gameButton23 = new JLabel("");
	private JLabel gameButton24 = new JLabel("");
	private int buttonSizeX;
	private int buttonSizeY;
	
	private JLabel[] gameButtons = 
	{
			gameButton1, gameButton2, gameButton3, gameButton4, gameButton5, gameButton6, gameButton7, gameButton8, 
			gameButton9, gameButton10, gameButton11, gameButton12, gameButton13, gameButton14, gameButton15, gameButton16, 
			gameButton17, gameButton18, gameButton19, gameButton20, gameButton21, gameButton22, gameButton23, gameButton24, 
	};

	public static boolean[] hasWindowOpen = new boolean[24]; //to check whether an instance of this game is already open
	
	private Color textColor = new Color(210,210,230);
	private Color buttonColor1 = new Color(75,75,105);
	private Color buttonColor2 = new Color(110,110,130);
	private Color buttonColor3 = new Color(130,130,150);
	
	
	private boolean buttonSizeIncreased = false;
	
	private JLabel headline = new JLabel("ERIK'S COLLECTION");
	private JLabel controls1 = new JLabel("");
	private JLabel controls2 = new JLabel("");
	private JLabel controls3 = new JLabel("");
	private JLabel controls4 = new JLabel("");
	private JLabel controls5 = new JLabel("");
	
	private JLabel[] guideLabels = {headline, controls1, controls2, controls3, controls4, controls5};
	
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
		controls1.setBounds(resolution/40,resolution/15,resolution+100,resolution/15);
		controls2.setBounds(resolution/40,resolution/10,resolution+100,resolution/15);
		controls3.setBounds(resolution/40,(int)(resolution/7.5),resolution+100,resolution/15);
		controls4.setBounds(resolution/40,resolution/6,resolution+100,resolution/15);
		controls5.setBounds(resolution/40,resolution/5,resolution+100,resolution/15);
		for (int i = 0; i < guideLabels.length; i++)
		{setTextBasics(guideLabels[i], i);}
		
		buttonSizeX = resolution/6;
		buttonSizeY = resolution/15;
		
		int j = 0;
		int k = 0;
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
			case 4: SelectionFrame pathfind = new SelectionFrame(); pathfind.start("Pathfind", eventHandler);
			break;
			case 5: ParallelUniverses parallelUniverses = new ParallelUniverses();parallelUniverses.start(eventHandler);
			break;
			case 6: ReflectionDemo reflectionDemo = new ReflectionDemo(); reflectionDemo.start(eventHandler);
			break;
			case 7: MouseDodge mouseDodge = new MouseDodge(); mouseDodge.start(eventHandler);
			break;
			case 8: JumpAndRun jumpAndRun = new JumpAndRun(); jumpAndRun.start(eventHandler, false);
			break;
			case 9: JumpAndRun jumpAndRun2 = new JumpAndRun(); jumpAndRun2.start(eventHandler, true);
			break;
			case 10: PixelCollision pixelCollision = new PixelCollision(); pixelCollision.start(eventHandler);
			break;
			case 11: ClothSim clothSim = new ClothSim(); clothSim.start(eventHandler);
			break;
			case 12: GravityVectors gravityVectors = new GravityVectors(); gravityVectors.start(eventHandler);
			break;
			case 13: BloonShooting bloonShooting = new BloonShooting(); bloonShooting.start(eventHandler);
			break;
			case 14: RandBattle randBattle = new RandBattle(); randBattle.start(eventHandler);
			break;
			case 15: RandGrowth randGrowth = new RandGrowth(); randGrowth.start(eventHandler);
			break;
			case 16: SnakesAndLadders snakesAndLadders = new SnakesAndLadders(); snakesAndLadders.start(eventHandler);
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
		titles[1][2] ="R - Reset";
		
		titles[2][0] = "SIERPINSKI";
		titles[2][1] ="Every turn there is a random chance for the point to move towards one of the";
		titles[2][2] ="3 points of the triangle. Then it leaves a new point halfway on the path to the";
		titles[2][3] ="chosen point. Repeating this will slowly create a sierpinski triangle.";
		
		titles[3][0] = "SUDOKU";
		titles[3][1] ="Use your mouse to click on a square, then type in a number.";
		titles[3][2] ="S -- Solve | R -- Reset";
		titles[3][3] ="+/- -- Change screen size";
		
		titles[4][0] = "PATHFIND";
		titles[4][1] ="Use W,A,S,D to escape the red enemies for as many turns as possible.";
		titles[4][2] ="Load and save levels with the menu bar. Shift + click on tiles to edit them.";
		titles[4][3] ="0 -- Wipe board | R -- Reset board";
		titles[4][4] ="T -- Generate new board | +/- -- Change screen size";
		
		titles[5][0] = "PARALLEL UNIVERSES";
		titles[5][1] ="The red point represents your position in the universe.";
		titles[5][2] ="The green point represents your position in the parallel universes.";
		titles[5][3] ="Move with W,A,S,D and hold down shift before pressing the keys to move faster.";
		titles[5][4] ="Hide the parallel universes with E";
		
		titles[6][0] = "REFLECTION DEMO";
		titles[6][1] ="Move the ray origin with your mouse held down and rotate it with the mouse wheel.";
		titles[6][2] ="1 - Less reflections  |  2 - More reflections";
		titles[6][3] ="3 - Less rotation speed  |  4 - More rotation speed";
		titles[6][4] ="5 - Low accuracy | 6 - Default accuracy | 7 - Ultra accuracy";
		
		titles[7][0] = "MOUSE DODGE";
		titles[7][1] = "Move your mouse to dodge the enemies.";
		titles[7][2] = "Press F to switch dark mode on or off.";
		titles[7][3] = "Other controls are displayed ingame.";
		
		titles[8][0] = "2D SIDESCROLLER";
		titles[8][1] ="Outrun the screen and don't fall into the lava.";
		titles[8][2] ="Use W,A,S,D to move and press space to jump. | F -- Flying mode";
		titles[8][3] ="R -- Restart | T -- Generate new map | ESC -- Pause";
		titles[8][4] ="CTRL + S -- Save | CTRL + L -- Load";
		
		titles[9][0] = "2D SPEEDRUN";
		titles[9][1] ="Move as fast as you can.";
		titles[9][2] ="Use W,A,S,D to move and press space to jump. | F -- Flying mode";
		titles[9][3] ="R -- Restart | T -- Generate new map | ESC -- Pause";
		titles[9][4] ="CTRL + S -- Save | CTRL + L -- Load";
		
		titles[10][0] = "PIXEL COLLISION";
		titles[10][1] ="Anything you drawn on screen will become a collision object.";
		titles[10][2] ="USe the Object Menu in the top left to drawn an object into the rectangle that";
		titles[10][3] ="appears, save it and then move it with W,A,S,D. Use shift to move faster.";
		titles[10][4] ="Use the Pixel Menu to change the size of the pixel grid";
		titles[10][5] ="+/- - Change screen size| F - Fly mode | R - Reset | Shift click - Erase pixel";
		
		titles[11][0] = "CLOTH SIM";
		titles[11][1] ="Click anywhere on screen to create a point, CTRL click to lock it in place.";
		titles[11][2] ="Click on a point again to select it. When you have two points selected, press";
		titles[11][3] ="C to connect them. Start or stop the simulation with S and reset it with R.";
		titles[11][4] ="Move point = Press mouse down and move | Cut point = Hold shift, press down and then move";
		titles[11][5] ="over it | Delete last connection = CTRL Z | Save/load layouts with the menu bar";
		
		titles[12][0] = "GRAVITY VECTORS";
		titles[12][1] ="This is not really a gravity or magnetic field simulation, but I could not";
		titles[12][2] ="find a good name for it. Move gravity points with the mouse held down";
		titles[12][3] ="1 - Add another gravity point | 2 - Remove last gravity point";
		titles[12][4] ="+/- - Change screen size";
		titles[12][5] ="G - Activate gradient rendering | N - Turn off arrow normalization";
		
		titles[13][0] = "BALLOON SHOOTING";
		titles[13][1] ="I wanted to recreate some aspects of the original bloons game in Java with";
		titles[13][2] ="my own sprite renderer. Shoot the balloons using the slingshot with the";
		titles[13][3] ="mouse held down. Press the right arrow key to load the next level,";
		titles[13][4] ="PREVIOUS LEVEL. R TO RESTART THE LEVEL | G TO SHOW THE LEVEL GRID | USE";
		titles[13][4] ="left for the previous level. R - Restart level | G - Show level grid";
		titles[13][5] ="Use shift press to move the entire slingshot, not just the projectile.";
		
		titles[14][0] = "RAND BATTLE";
		titles[14][1] = "NPCs with random sizes, hp, damage, movement speed and projectile speed";
		titles[14][2] = "are generated. They pick a random target and try to shoot it until it is";
		titles[14][3] = "DEAD, THEN THEY PICK A NEW ONE. WHO WILL BE THE LAST SURVIVOR?";
		titles[14][3] = "dead, then they pick a new one. Who will be victorious?";
		titles[14][4] = "R - Restart | S - Show stats | H - Only show Health";
		
		titles[15][0] = "RAND GROWTH";
		titles[15][1] = "Once you have pressed the start button, press and move your mouse";
		titles[15][2] = "across the screen to spawn pixels and watch them try to survive and grow";
		titles[15][3] = "according to the rules you set with the UI included in the program.";
		
		titles[16][0] = "SNAKES AND LADDERS";
		titles[16][1] = "A classic game of Snakes And Ladders. Set the amount of players and press 'Start'.";
		titles[16][2] = "Once you have pressed on the roll button you can either move your player piece with";
		titles[16][3] = "the mouse, or you can press on Auto Move and have the piece move for you.";
		titles[16][4] = "Try to reach square 53!";
		
		
		headline.setText(titles[game][0]);
		controls1.setText(titles[game][1]);
		controls2.setText(titles[game][2]);
		controls3.setText(titles[game][3]);
		controls4.setText(titles[game][4]);
		controls5.setText(titles[game][5]);
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
	