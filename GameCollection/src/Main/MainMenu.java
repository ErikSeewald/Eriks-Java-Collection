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
import Coordinates3D.Coordinates3D;
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
import Sudoku.Sudoku;

public class MainMenu extends JFrame implements MouseListener
{
	private static final long serialVersionUID = 1454253443346436L;

	private int resolution = 600;
	
	private JLabel gameButton1 = new JLabel("   3D Coords");
	private JLabel gameButton2 = new JLabel("      Insects");
	private JLabel gameButton3 = new JLabel("     Particles");
	private JLabel gameButton4 = new JLabel("    Sierpinski");
	private JLabel gameButton5 = new JLabel("      Sudoku");
	private JLabel gameButton6 = new JLabel("      Pathfind");
	private JLabel gameButton7 = new JLabel("    Parallel U.");
	private JLabel gameButton8 = new JLabel("    Reflection");
	private JLabel gameButton9 = new JLabel(" Mouse Dodge");
	private JLabel gameButton10 = new JLabel("   Sidescroller");
	private JLabel gameButton11 = new JLabel("    Speedrun");
	private JLabel gameButton12 = new JLabel(" Pixel Collision");
	private JLabel gameButton13 = new JLabel("    Cloth Sim");
	private JLabel gameButton14 = new JLabel(" Gravity Vects");
	private JLabel gameButton15 = new JLabel("  Bloon Shoot");
	private JLabel gameButton16 = new JLabel("  Rand Battle");
	private JLabel gameButton17 = new JLabel(" Rand Growth");
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
		if (index != 0) {x.setFont(new Font ("", Font.BOLD, resolution/50));}
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
			case 0: Coordinates3D coordinates3D = new Coordinates3D(); coordinates3D.start(eventHandler);
			break;
			case 1: Insects insects = new Insects(); insects.start(eventHandler);
			break;
			case 2: Particles particles = new Particles(); particles.start(eventHandler);
			break;
			case 3: SelectionFrame sierpinski = new SelectionFrame(); sierpinski.start("Sierpinski", eventHandler);
			break;
			case 4: Sudoku sudoku = new Sudoku(); sudoku.start(eventHandler);
			break;
			case 5: SelectionFrame pathfind = new SelectionFrame(); pathfind.start("Pathfind", eventHandler);
			break;
			case 6: ParallelUniverses parallelUniverses = new ParallelUniverses();parallelUniverses.start(eventHandler);
			break;
			case 7: ReflectionDemo reflectionDemo = new ReflectionDemo(); reflectionDemo.start(eventHandler);
			break;
			case 8: MouseDodge mouseDodge = new MouseDodge(); mouseDodge.start(eventHandler);
			break;
			case 9: JumpAndRun jumpAndRun = new JumpAndRun(); jumpAndRun.start(eventHandler, false);
			break;
			case 10: JumpAndRun jumpAndRun2 = new JumpAndRun(); jumpAndRun2.start(eventHandler, true);
			break;
			case 11: PixelCollision pixelCollision = new PixelCollision(); pixelCollision.start(eventHandler);
			break;
			case 12: ClothSim clothSim = new ClothSim(); clothSim.start(eventHandler);
			break;
			case 13: GravityVectors gravityVectors = new GravityVectors(); gravityVectors.start(eventHandler);
			break;
			case 14: BloonShooting bloonShooting = new BloonShooting(); bloonShooting.start(eventHandler);
			break;
			case 15: RandBattle randBattle = new RandBattle(); randBattle.start(eventHandler);
			break;
			case 16: RandGrowth randGrowth = new RandGrowth(); randGrowth.start(eventHandler);
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
		
		
		titles[0][0] = "3D COORDINATES";
		titles[0][1] ="TYPE IN THE COORDINATES OF POINTS OF THE PLANE YOU WANT TO BE DISPLAYED";
		titles[0][2] ="CLICK THE TOP LEFT BUTTON TO TURN IT INTO A CUBE, THE TOP RIGHT TO SPIN";
		titles[0][3] ="CLICK THE BUTTONS AT THE BOTTOM TO REMOVE THE AXIS' OR TO MOVE THE PLANE";
		titles[0][4] ="RECOMMENDED COORDINATES: 12,0,0   0,0,0   0,12,0";
		
		titles[1][0] = "INSECTS";
		titles[1][1] ="OBSERVE AS THE INSECTS TRY TO SURVIVE IN THIS MAZE | R - RESTART";
		titles[1][2] ="YOU CAN SEE THE AMOUNT OF INSECTS IN THE TOP LEFT CORNER";
		titles[1][3] ="BLUE SQUARES ARE SOURCES OF WATER, RED SQUARES ARE FOOD. THE NUMBER OF";
		titles[1][4] ="INSECTS CHANGES IN RELATION TO HOW MANY INSECTS ARE INSIDE THESE SOURCES";
		titles[1][5] ="1 - LESS ANTS | 2 - MORE ANTS | 3 - LESS INCREASE PER PRESS | 4 - MORE";
		
		titles[2][0] = "PARTICLES";
		titles[2][1] ="PRESS DOWN YOUR MOUSE TO DRAW ON THE PARTICLES";
		titles[2][2] ="PRESS R TO RESET";
		
		titles[3][0] = "SIERPINSKI";
		titles[3][1] ="EVERY TURN THERE IS A RANDOM CHANCE FOR THE POINT TO MOVE TOWARDS ONE OF THE";
		titles[3][2] ="3 POINTS OF THE TRIANGLE. THEN IT LEAVES A POINT HALFWAY ON THE PATH TO THE";
		titles[3][3] ="CHOSEN POINT. REPEATING THIS WILL SLOWLY CREATE A SIERPINSKI TRIANGLE.";
		titles[3][4] ="HOLD DOWN YOUR MOUSE OVER A TRIANGLE POINT AND HOLD SHIFT TO MOVE IT";
		
		titles[4][0] = "SUDOKU";
		titles[4][1] ="USE YOUR MOUSE TO CLICK ON A BOX, THEN TYPE IN A NUMBER";
		titles[4][2] ="S -- SOLVE SUDOKU | R -- RESET";
		titles[4][3] ="+ -- INCREASE SIZE | - -- DECREASE SIZE";
		
		titles[5][0] = "PATHFIND";
		titles[5][1] ="USE W,A,S,D TO ESCAPE THE RED ENEMIES FOR AS MANY TURNS AS POSSIBLE";
		titles[5][2] ="LOAD AND SAVE LEVELS WITH THE MENU BAR | SHIFT + CLICK ON TILES TO EDIT THEM";
		titles[5][3] ="0 -- WIPE BOARD | R -- RESET BOARD";
		titles[5][4] ="T -- NEW BOARD | +/- -- CHANGE SIZE";
		
		titles[6][0] = "PARALLEL UNIVERSES";
		titles[6][1] ="THE RED POINT REPRESENTS YOUR POSTITION IN THE UNIVERSE";
		titles[6][2] ="THE GREEN POINT REPRESENTS YOUR POSITION IN THE PARALLEL UNIVERSES";
		titles[6][3] ="MOVE WITH W,A,S,D AND HOLD DOWN SHIFT BEFORE PRESSING THE KEYS TO MOVE FASTER";
		titles[6][4] ="HIDE THE PARALLEL UNIVERSES WITH E";
		
		titles[7][0] = "REFLECTION DEMO";
		titles[7][1] ="MOVE THE ORIGIN WITH YOUR MOUSE HELD DOWN AND ROTATE WITH THE MOUSE WHEEL";
		titles[7][2] ="1 - LESS REFLECTIONS  |  2 - MORE REFLECTIONS";
		titles[7][3] ="3 - LESS ROTATION SPEED  |  4 - MORE ROTATION SPEED";
		titles[7][4] ="5 - LOW ACCURACY | 6 - DEFAULT ACCURACY | 7 - ULTRA ACCURACY (DEMANDING)";
		
		titles[8][0] = "MOUSE DODGE";
		titles[8][1] = "MOVE YOUR MOUSE TO DODGE THE ENEMIES";
		titles[8][2] = "PRESS F TO SWITCH DARK MODE ON OR OFF";
		titles[8][3] = "OTHER CONTROLS ARE DISPLAYED INGAME";
		
		titles[9][0] = "2D SIDESCROLLER";
		titles[9][1] ="OUTRUN THE SCREEN AND DON'T FALL INTO THE LAVA";
		titles[9][2] ="USE W,A,S,D TO MOVE AND SPACE TO JUMP | ENTER FLYING MODE WITH F";
		titles[9][3] ="R -- RESTART | T -- GENERATE NEW MAP | ESC -- PAUSE";
		titles[9][4] ="CTRL + S -- SAVE | CTRL + L -- LOAD";
		
		titles[10][0] = "2D SPEEDRUN";
		titles[10][1] ="MOVE AS FAST AS YOU CAN";
		titles[10][2] ="USE W,A,S,D TO MOVE AND SPACE TO JUMP | ENTER FLYING MODE WITH F";
		titles[10][3] ="R -- RESTART | T -- GENERATE NEW MAP | ESC -- PAUSE";
		titles[10][4] ="CTRL + S -- SAVE | CTRL + L -- LOAD";
		
		titles[11][0] = "PIXEL COLLISION";
		titles[11][1] ="ANYTHING YOU DRAW ON SCREEN WILL BECOME A COLLISION OBJECT";
		titles[11][2] ="USE THE OBJECT MENU IN THE TOP LEFT TO DRAW AN OBJECT INTO THE RECTANGLE THAT";
		titles[11][3] ="APPEARS, SAVE IT AND THEN MOVE IT WITH W,A,S,D. USE SHIFT TO MOVE FASTER";
		titles[11][4] ="USE THE PIXEL MENU TO CHANGE THE SIZE OF THE PIXELS YOU DRAW ";
		titles[11][5] ="CHANGE SCREEN SIZE: + & - | FLY MODE: F | RESET: R | SHIFT CLICK TO ERASE";
		
		titles[12][0] = "CLOTH SIM";
		titles[12][1] ="CLICK ANYWHERE ON SCREEN TO CREATE A POINT, CTRL CLICK TO LOCK IT IN PLACE";
		titles[12][2] ="CLICK ON A POINT AGAIN TO SELECT IT, IF YOU HAVE TWO POINTS SELECTED, PRESS";
		titles[12][3] ="C TO CONNECT THEM. START OR STOP THE SIMULATION WITH S AND RESET WITH R";
		titles[12][4] ="MOVE POINT = PRESS DOWN AND MOVE | CUT POINT = HOLD SHIFT, PRESS DOWN AND MOVE";
		titles[12][5] ="OVER IT | DELETE LAST CONNECTION = CTRL Z | SAVE/LOAD LAYOUTS WITH THE MENUBAR";
		
		titles[13][0] = "GRAVITY VECTORS";
		titles[13][1] ="THIS IS NOT REALLY A GRAVITY OR MAGNETIC FIELD SIMULATION, BUT I COULD NOT";
		titles[13][2] ="FIND A PROPER NAME FOR IT. MOVE GRAVITY POINTS WITH THE MOUSE HELD DOWN";
		titles[13][3] ="1 TO DECREASE THE AMOUNT OF GRAVITY POINTS | 2 TO INCREASE";
		titles[13][4] ="+ TO INCREASE SCREEN SIZE | - TO DECREASE";
		titles[13][5] ="G TO ACTIVATE GRADIENT RENDERING | N TO TURN OFF ARROW VECTOR NORMALIZATION";
		
		titles[14][0] = "BALLOON SHOOTING";
		titles[14][1] ="I WANTED TO RECREATE SOME ASPECTS OF THE ORIGINAL BLOONS GAME IN JAVA WITH";
		titles[14][2] ="MY OWN SPRITE RENDERER. SHOOT THE BALLOONS USING THE SLINGSHOT WITH THE";
		titles[14][3] ="MOUSE HELD DOWN. RIGHT ARROW TO LOAD THE NEXT LEVEL, LEFT ARROW FOR THE";
		titles[14][4] ="PREVIOUS LEVEL. R TO RESTART THE LEVEL | G TO SHOW THE LEVEL GRID | USE";
		titles[14][5] ="SHIFT PRESS ON THE SLING TO MOVE THE ENTIRE THING, NOT JUST THE PROJECTILE";
		
		titles[15][0] = "RAND BATTLE";
		titles[15][1] = "NPCS WITH RANDOM SIZES, HP, DAMAGE, MOVEMENT SPEED AND PROJECTILE SPEED";
		titles[15][2] = "ARE GENERATED. THEY PICK A RANDOM TARGET AND TRY TO SHOOT IT UNTIL IT IS";
		titles[15][3] = "DEAD, THEN THEY PICK A NEW ONE. WHO WILL BE THE LAST SURVIVOR?";
		titles[15][4] = "R - RESTART | S - SHOW STATS | H - ONLY SHOW HEALTH";
		
		titles[16][0] = "RAND GROWTH";
		titles[16][1] = "ONCE YOU HAVE PRESSED THE START BUTTON, PRESS AND DRAG YOUR MOUSE ACROSS";
		titles[16][2] = "THE SCREEN TO SPAWN PIXELS AND WATCH THEM TRY TO SURVIVE AND GROW";
		titles[16][3] = "ACCORDING TO THE RULES YOU SET WITH THE UI INCLUDED IN THE PROGRAM";
		
		
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
	