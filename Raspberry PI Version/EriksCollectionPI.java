import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.util.Random;
import javax.swing.Timer;
import javax.swing.JPanel;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.BasicStroke;
import java.awt.Point;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import java.text.DecimalFormat;
import java.awt.RadialGradientPaint;


public class EriksCollection 
{
	public static void main(String[] args) 
	{
		MainMenu menu = new MainMenu();
		
		menu.addKeyListener(new KeyListener()
		{
			@Override
			public void keyTyped(KeyEvent e) 
			{
				if (e.getKeyChar() == '+') {menu.changeSize(10);}
				else if (e.getKeyChar() == '-') {menu.changeSize(-10);}
			}

			@Override
			public void keyPressed(KeyEvent e) {}

			@Override
			public void keyReleased(KeyEvent e) {}
			
		});
	}
}

public class MainMenu extends JFrame implements MouseListener
{
	private static final long serialVersionUID = 1454253443346436L;

	private int resolution = 400;
	
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
	private JLabel gameButton15 = new JLabel("Bloon Shooting");
	private JLabel gameButton16 = new JLabel("");
	private JLabel gameButton17 = new JLabel("");
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
	
	boolean insideButton = false;
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
	
	//public static final ImageIcon img = new ImageIcon("src\\Main\\logo.jpg");
	
	MainMenu()
	{
		eventHandler = new WindowEventHandler();
		
		//this.setIconImage(img.getImage());
		
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
		{if (button != gameButtons[i] && button.getText() != "") {index++;} else {break;}}
		
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
		insideButton = true;
		
		JLabel button = (JLabel)e.getSource();
		buttonAnimation(button, (buttonSizeX /25));
		button.setBackground(buttonColor2);
		
		int index = 0;
		for (int i = 0; i < gameButtons.length; i++)
		{if (button != gameButtons[i] && button.getText() != "") {index++;} else {break;}}
		
		changeInformation(index);
		
	}

	@Override
	public void mouseExited(MouseEvent e) 
	{
		insideButton = false;
		
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
		
		//final String javaBin = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
		//final File currentJar = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI());

		/* is it a jar file? */
		//if(!currentJar.getName().endsWith(".jar"))
	   // return;

		/* Build command: java -jar application.jar */
		//final ArrayList<String> command = new ArrayList<String>();
		//command.add(javaBin);
	  	//command.add("-jar");
	  	//command.add(currentJar.getPath());

	  	//final ProcessBuilder builder = new ProcessBuilder(command);
	  	//builder.start();
	  	System.exit(0);
	  
	}
}


public class Coordinates3D 
{
	
	public void start(WindowEventHandler eventHandler) 
	{
		
		int[] a = getResults("A");
		
		int[] b = getResults("B");

		int[] c = getResults("C");
		
		new Coordinates3DFrame(a,b,c,eventHandler);	

	}
	
	public static int[] getResults(String y)
	{
		
		int[] x = new int[3];
		char t = ' ';
		
		for (int i = 0; i < 3; i++) //3 Durchgänge -> x,y,z Wert
		{ 	
			
			switch (i) //Wechsel der Anzeige zum momentan verlangten Wert
			{case 0: t = 'x';break;case 1: t = 'y';break;case 2: t = 'z';break;} 
			
			while (true) 
			{
				try 
				{	//Y ist der Punkt, t ist x,y oder z
					x[i] = Integer.parseInt(JOptionPane.showInputDialog(y+" - "+t+" von -25 bis 25")); 
					if (x[i] >=-25 && x[i] <= 25) {break;}
					else {JOptionPane.showMessageDialog(null, "-25 bis 25");}
				}
				
				catch (NumberFormatException f) {return x;}
			}	
		}
				
		return x;
	}
}

public class Coordinates3DFrame extends JFrame implements ActionListener,MouseListener
{
	private static final long serialVersionUID = -1790901160968965409L;

	Coordinates3DPanel panel; 	//Grafikpanel
	
	JLabel extend;		//Ebene zu Quader    Button
	JLabel move;	//Ebenen Bewegung    Button
	JLabel hide;	//Achsen verstecken  Button
	
	JLabel Xb;		//X Bewegung+  Button
	JLabel Yb;		//Y Bewegung+  Button
	JLabel Zb;		//Z Bewegung+  Button
	JLabel Xb1;		//X Bewegung-  Button
	JLabel Yb1;		//Y Bewegung-  Button
	JLabel Zb1;		//Z Bewegung-  Button
	
	JLabel AL;		//Punkt A Daten Display
	JLabel BL;		//Punkt B Daten Display
	JLabel CL;		//Punkt C Daten Display
	JLabel DL;		//Punkt D Daten Display
	
	JLabel NL;		//Ebene E Daten Display
	
    static Timer timer;
	
	int[][] R = new int[5][3];	//2D Array für Austausch von Daten zwischen MyFrame und MyPanel
	
	boolean rotating = false;	//Boolean zum stoppen der Drehbewegung
	
	boolean inside = false;		//Boolean check, ob die Maus gerade in einem Button ist
	
	Color buttonc;				//Dunkle Buttonfarbe
	Color buttond;				//Mittlere Buttonfarbe
	Color buttone;				//Helle Buttonfarbe
	Color border;				//Border Farbe
	
	boolean first = true;		//Booleans zum wechsel zwischen Achsen sichtbar und unsichtbar
	boolean hidden = false;
	
	Coordinates3DFrame(int[] a, int[]b, int[]c, WindowEventHandler eventHandler)
	{
		
		timer = new Timer(125, this);
		timer.addActionListener(this);
		
		this.addWindowListener(eventHandler);
		this.setTitle("3D Coordinates");
		
		panel = new Coordinates3DPanel(a,b,c); //Weitergabe der Daten der 3 Punkte
		
		buttonc = new Color(170,170,190);
		buttond = new Color(200,200,220);
		buttone = new Color(220,220,240);
		border = new Color(100,100,120);
		
		extend = new JLabel("  Quader");
		extend.setBounds(10,5,70,40);
		setBasics(extend);
		
		move = new JLabel("       ?");
		move.setBounds(500,5,70,40);
		setBasics(move);
		
		hide = new JLabel("  Achsen");
		hide.setBounds(10,553,70,40);
		setBasics(hide);
		
		Xb = new JLabel("    X+");
		Xb.setBounds(210, 553,55,40);
		setBasics(Xb);
		
		Xb1 = new JLabel("    X-");
		Xb1.setBounds(270,553,55,40);
		setBasics(Xb1);
		
		Yb = new JLabel("    Y+");
		Yb.setBounds(340,553,55,40);
		setBasics(Yb);
		
		Yb1 = new JLabel("    Y-");
		Yb1.setBounds(400,553,55,40);
		setBasics(Yb1);
		
		Zb = new JLabel("    Z+");
		Zb.setBounds(470,553,55,40);
		setBasics(Zb);
		
		Zb1 = new JLabel("    Z-");
		Zb1.setBounds(530,553,55,40);
		setBasics(Zb1);
		
		AL = new JLabel();
		AL.setBounds(100,14,85,15);
		
		BL = new JLabel();
		BL.setBounds(100,34,85,15);
		
		CL = new JLabel();
		CL.setBounds(185,14,85,15);
		
		DL = new JLabel();
		DL.setBounds(185,34,85,15);
		
		NL = new JLabel();
		NL.setBounds(270,21,250,15);
		
		setValues();
		
		this.setSize(615,635);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setLayout(null);
		
		this.add(AL);
		this.add(BL);
		this.add(CL);
		this.add(DL);
		this.add(NL);
		this.add(panel);
		
		this.setVisible(true);
	}

	public static void stop()
	{if (timer.isRunning()) {timer.stop();}}
	
	public void setBasics(JLabel x)
	{
		x.setFont(new Font ("", Font.PLAIN, 15));
		x.setBackground(buttonc);
		x.setOpaque(true);
		x.setBorder(BorderFactory.createLineBorder(border, 2));
		x.addMouseListener(this);
		this.add(x);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		
		if (e.getSource()==timer) 
		{
			panel.move();				//Timer event zur animation der Ebenenbewegung
			setValues();
		}
	
	}
	
	public void setValues() 
	{
		
		R = panel.getValues();
		AL.setText("A ("+R[0][0]+"|"+R[0][1]+"|"+R[0][2]+")");
		BL.setText("B ("+R[1][0]+"|"+R[1][1]+"|"+R[1][2]+")");
		CL.setText("C ("+R[2][0]+"|"+R[2][1]+"|"+R[2][2]+")");		//Gibt die neuen Werte an die Displays weiter
		DL.setText("D ("+R[3][0]+"|"+R[3][1]+"|"+R[3][2]+")");
		char second = '+';char third = '+';
		if (R[4][1]<0) {second = '-';}
		if (R[4][2]<0) {third = '-';}
		NL.setText("Ebene E: "+R[4][0]+"x "+ second + " "+Math.abs(R[4][1])+"y "+third+" "+Math.abs(R[4][2])+"z = "+R[5][0]);	
		
	}

	@Override
	public void mouseClicked(MouseEvent e) 
	{ 
		
		
		if (e.getSource()==hide) 
		{
			if (first) 
			{hidden = true; first=false;}
				
			else {hidden=false;first=true;}		//Schalter zwischen versteckten und sichtbaren Achsen
		
			panel.hide(hidden);
			hide.setBounds(10,553,70,40);	//Reset der Buttongröße nach dem Klick
	
			if (inside == true) 
			{hide.setBackground(buttond);} //Reset der Buttonfarbe auf Mittel nach dem Klick
			else  
			{hide.setBackground(buttonc);} //Falls die Maus den Button während dem Drücken verlassen hat, Reset auf Dunkel
		}

		
		if (e.getSource()==extend) 
		{	
			extend.setBounds(10,5,70,40);	
			panel.extend();
	
			if (inside == true) 
			{extend.setBackground(buttond);}
			else  
			{extend.setBackground(buttonc);}
		}
		
		
		else if (e.getSource()==move) 
		{	
			move.setBounds(500,5,70,40);
		
			if(rotating) 
			{timer.stop();rotating=false;}
			else 
			{timer.start(); rotating = true;}
		
			if (inside == true) 
			{move.setBackground(buttond);}
			else  
			{move.setBackground(buttonc);}
		}
		
		
		else if (e.getSource()==Xb) 
		{	
			Xb.setBounds(210, 553,55,40);	
			panel.movenum(1);
			setValues();
			
			if (inside == true) 
			{Xb.setBackground(buttond);}
			else  
			{Xb.setBackground(buttonc);}
		}
		
		
		else if (e.getSource()==Xb1) 
		{	
			Xb1.setBounds(270,553,55,40);	
			panel.movenum(4);
			setValues();
			
			if (inside == true) 
			{Xb1.setBackground(buttond);}
			else  
			{Xb1.setBackground(buttonc);}
		}
		
		
		else if (e.getSource()==Yb) 
		{	
			Yb.setBounds(340,553,55,40);	
			panel.movenum(2);
			setValues();
				
			if (inside == true) 
			{Yb.setBackground(buttond);}
			else  
			{Yb.setBackground(buttonc);}
		}
			
			
		else if (e.getSource()==Yb1) 
		{	
			Yb1.setBounds(400,553,55,40);	
			panel.movenum(5);
			setValues();
				
			if (inside == true) 
			{Yb1.setBackground(buttond);}
			else  
			{Yb1.setBackground(buttonc);}
		}
		
		
		else if (e.getSource()==Zb) 
		{	
			Zb.setBounds(470,553,55,40);	
			panel.movenum(3);
			setValues();
				
			if (inside == true) 
			{Zb.setBackground(buttond);}
			else  
			{Zb.setBackground(buttonc);}
		}
			
			
		else if (e.getSource()==Zb1) 
		{	
			Zb1.setBounds(530,553,55,40);	
			panel.movenum(6);
			setValues();
				
			if (inside == true) 
			{Zb1.setBackground(buttond);}
			else  
			{Zb1.setBackground(buttonc);}
		}
	}
		
	@Override
	public void mousePressed(MouseEvent e) 
	{
		
		if (e.getSource()==hide) //Button verkeinern während click
		{
		hide.setBackground(buttone);
		hide.setBounds(11,554,68,38);
		} 
		
		else if (e.getSource()==extend) 
		{
		extend.setBackground(buttone);
		extend.setBounds(11,6,68,38);
		}
		
		else if (e.getSource()==move) 
		{
		move.setBackground(buttone);
		move.setBounds(501,6,68,38);
		}
		
		else if (e.getSource()==Xb) 
		{
		Xb.setBackground(buttone);
		Xb.setBounds(211, 554,53,38);
		}
		
		else if (e.getSource()==Xb1) 
		{
		Xb1.setBackground(buttone);
		Xb1.setBounds(271,554,53,38);
		}
		
		else if (e.getSource()==Yb) 
		{
		Yb.setBackground(buttone);
		Yb.setBounds(341,554,53,38);
		}
		
		else if (e.getSource()==Yb1) 
		{
		Yb1.setBackground(buttone);
		Yb1.setBounds(401,554,53,38);
		}
		
		else if (e.getSource()==Zb) 
		{
		Zb.setBackground(buttone);
		Zb.setBounds(471,554,53,38);
		}
		
		else if (e.getSource()==Zb1) 
		{
		Zb1.setBackground(buttone);
		Zb1.setBounds(531,554,53,38);
		}
	
	}

	@Override
	public void mouseReleased(MouseEvent e) {}	
	
	@Override
	public void mouseEntered(MouseEvent e) 
	{
		
		if (e.getSource()==hide)  //Button vergrößern beim Entern
		{
			hide.setBackground(buttond); 
			hide.setBounds(8,551,73,43);
		}
		
			else if (e.getSource()==extend) 
			{
			extend.setBackground(buttond);
			extend.setBounds(8,3,73,43);
			}
			
			else if (e.getSource()==move) 
			{
			move.setBackground(buttond);
			move.setBounds(498,3,73,43);
			}
			
			else if (e.getSource()==Xb) 
			{
			Xb.setBackground(buttond);
			Xb.setBounds(208, 551,58,43);
			}
			
			else if (e.getSource()==Xb1) 
			{
			Xb1.setBackground(buttond);
			Xb1.setBounds(268,551,58,43);
			}
			
			else if (e.getSource()==Yb) 
			{
			Yb.setBackground(buttond);
			Yb.setBounds(338,551,58,43);
			}
			
			else if (e.getSource()==Yb1) 
			{
			Yb1.setBackground(buttond);
			Yb1.setBounds(398,551,58,43);
			}
			
			else if (e.getSource()==Zb) 
			{
			Zb.setBackground(buttond);
			Zb.setBounds(468,551,58,43);
			}
			
			else if (e.getSource()==Zb1) 
			{
			Zb1.setBackground(buttond);
			Zb1.setBounds(528,551,58,43);
			}
		
		inside = true;
	}

	@Override
	public void mouseExited(MouseEvent e) 
	{
		
		if (e.getSource()==hide) //Beim Verlassen die Buttongröße wieder resetten
		{
			hide.setBackground(buttonc);
			hide .setBounds(10,553,70,40);
		} 
		
			else if (e.getSource()==extend) 
			{
			extend.setBackground(buttonc);
			extend.setBounds(10,5,70,40);
			}
			
			else if (e.getSource()==move) 
			{
			move.setBackground(buttonc);
			move.setBounds(500,5,70,40);
			}
			
			else if (e.getSource()==Xb) 
			{
			Xb.setBackground(buttonc);
			Xb.setBounds(210, 553,55,40);
			}
			
			else if (e.getSource()==Xb1) 
			{
			Xb1.setBackground(buttonc);
			Xb1.setBounds(270,553,55,40);
			}
			
			else if (e.getSource()==Yb) 
			{
			Yb.setBackground(buttonc);
			Yb.setBounds(340,553,55,40);
			}
			
			else if (e.getSource()==Yb1) 
			{
			Yb1.setBackground(buttonc);
			Yb1.setBounds(400,553,55,40);
			}
			
			else if (e.getSource()==Zb) 
			{
			Zb.setBackground(buttonc);
			Zb.setBounds(470,553,55,40);
			}
			
			else if (e.getSource()==Zb1) 
			{
			Zb1.setBackground(buttonc);
			Zb1.setBounds(530,553,55,40);
			}
		
		inside = false;
	}
}

public class Coordinates3DPanel extends JPanel 
{

	private static final long serialVersionUID = -3003036693212844492L;
	
	int pushX = 0; 
	int pushY = 0; //Integers zum Verschieben der Striche auf den Achsen
	int pushZ = 0;
	
	int[] A = new int [3];
	int[] B = new int [3]; 		//Koordinaten der Punkte
	int[] C = new int [3];
	int[] D = new int [3];
	

	int[] A1 = new int [2];
	int[] B1 = new int [2]; 	//Punkte in Pixelkoordinaten
	int[] C1 = new int [2];
	int[] D1 = new int [2];
		
	
	int[] W = new int [3];
	int[] X = new int [3]; 		//Punkte zur verlängerung der Ebene
	int[] Y = new int [3];
	int[] Z = new int [3];
	
	int[] W1 = new int [2];
	int[] X1 = new int [2]; 		
	int[] Y1 = new int [2];
	int[] Z1 = new int [2];
	
	int[] n = new int[3]; 		//Nomale
	
	int max; //Punkt mit größstem anliegendem Winkel
	
	boolean ext = false; //Verlängerung der Ebene in ein Quader
	
	int E; // Wert D für Koordinatenform der Ebene
	
	int rotate = 0;	//Wert, um verschiedene Phasen der Drehbewegung der Ebene zu bestimmen
	
	boolean hide = false;	//Boolean für das verstecken der Achsen

	
	Coordinates3DPanel(int[] a, int[]b, int[]c)
	{
		
		this.A = a;
		this.B = b;
		this.C = c;	
		
		this.setBounds(50,50,500,500);
		
		this.calculation();
		
	}
	
	public void calculation() 
	{
		
		//Berechnung von Punkt D
		
		//Vekoren Berechnen
		
		int[] CB = new int[3];
		CB[0] = B[0]-C[0]; CB[1] = B[1]-C[1]; CB[2] = B[2]-C[2]; //CB
		
		int[] BC = new int[3];
		BC[0] = C[0]-B[0]; BC[1] = C[1]-B[1]; BC[2] = C[2]-B[2]; //BC
		
		
		int[] CA = new int[3];
		CA[0] = A[0]-C[0]; CA[1] = A[1]-C[1]; CA[2] = A[2]-C[2]; //CA
		
		int[] AC = new int[3];
		AC[0] = C[0]-A[0]; AC[1] = C[1]-A[1]; AC[2] = C[2]-A[2]; //AC
		
		
		int[] BA = new int[3];
		BA[0] = A[0]-B[0]; BA[1] = A[1]-B[1]; BA[2] = A[2]-B[2]; //BA
		
		int[] AB = new int[3];
		AB[0] = B[0]-A[0]; AB[1] = B[1]-A[1]; AB[2] = B[2]-A[2]; //AB
		
		//Punkt mit größtem Winkel finden
		
		int skalar1 = AB[0] * AC[0] + AB[1] * AC[1] + AB[2] * AC[2];
		double u1 = Math.sqrt(Math.pow(AB[0], 2) + Math.pow(AB[1], 2) + Math.pow(AB[2], 2)); //Punkt A
		double v1 = Math.sqrt(Math.pow(AC[0], 2) + Math.pow(AC[1], 2) + Math.pow(AC[2], 2));
		
		double alpha1 = (Math.acos(skalar1 / (u1*v1)))*180/3.1416; // durch Pi
		
		
		int skalar2 = BA[0] * BC[0] + BA[1] * BC[1] + BA[2] * BC[2];
		double u2 = Math.sqrt(Math.pow(BA[0], 2) + Math.pow(BA[1], 2) + Math.pow(BA[2], 2)); //Punkt B
		double v2 = Math.sqrt(Math.pow(BC[0], 2) + Math.pow(BC[1], 2) + Math.pow(BC[2], 2));
		
		double alpha2 = (Math.acos(skalar2 / (u2*v2))*180/3.1416);;
		
	
		int skalar3 = CB[0] * CA[0] + CB[1] * CA[1] + CB[2] * CA[2];
		double u3 = Math.sqrt(Math.pow(CB[0], 2) + Math.pow(CB[1], 2) + Math.pow(CB[2], 2)); //Punkt C
		double v3 = Math.sqrt(Math.pow(CA[0], 2) + Math.pow(CA[1], 2) + Math.pow(CA[2], 2));
		
		double alpha3 = (Math.acos(skalar3 / (u3*v3)))*180/3.1416;
		
		if (alpha1 > alpha2 && alpha1 > alpha3) {max=1;} 
		else if (alpha2 > alpha1 && alpha2 > alpha3) {max=2;} //max 1 -> Punkt A usw.
		else {max=3;}
		
		
		//Den am Punkt mit größtem Winkel unbeteiligten Vektor an diesem ansetzen, um Punkt D zu berechnen
		
		switch (max) 
		{
		case 1: D[0] = AB[0] + C[0]; D[1] = AB[1] + C[1]; D[2] = AB[2] + C[2];
		break;
		case 2: D[0] = BC[0] + A[0]; D[1] = BC[1] + A[1]; D[2] = BC[2] + A[2]; 
		break;
		case 3: D[0] = CA[0] + B[0]; D[1] = CA[1] + B[1]; D[2] = CA[2] + B[2]; 
		break;
		}
		
		//Normalvektor = Kreuzprodukt AB & AC
		//int[] n = new int[3];
		
		n[0] = (AB[1] * AC[2]) - (AB[2] * AC[1]);
		n[1] = (AB[2] * AC[0]) - (AB[0] * AC[2]); 
		n[2] = (AB[0] * AC[1]) - (AB[1] * AC[0]);
		
		//Größter Gemeinsame Teiler zum kürzen:
		
		int i;
		for (i = 1000; i>=1; i--) 
		{if((n[0]%i==0) && (n[1]%i==0) && (n[2]%i==0)) {break;}}
		
		
		n[0]= n[0]/i;
		n[1]= n[1]/i;
		n[2]= n[2]/i;
		
		
		//D von Ebene E
		
		E = (n[0]*A[0])+(n[1]*A[1])+(n[2]*A[2]);
					
		//Verlängerung in ein Quader
		
		W[0] = A[0] + (n[0]*5);
		W[1] = A[1] + (n[1]*5);
		W[2] = A[2] + (n[2]*5);
		
		X[0] = B[0] + (n[0]*5);
		X[1] = B[1] + (n[1]*5);
		X[2] = B[2] + (n[2]*5);
		
		Y[0] = C[0] + (n[0]*5);
		Y[1] = C[1] + (n[1]*5);
		Y[2] = C[2] + (n[2]*5);
		
		Z[0] = D[0] + (n[0]*5);
		Z[1] = D[1] + (n[1]*5);
		Z[2] = D[2] + (n[2]*5);
		
		//Umwandlung in Pixelkoordinaten (X250,Y250 ist (0/0/0), steigender Y Wert -> Verschiebung nach unten
		
		A1[0] = (250-(A[0]*5)) + (A[1]*10); //X Achse geht in x und y Richtung, also bei beiden Einfügen
		A1[1] = (250+(A[0]*5)) - (A[2]*10);	
		
		B1[0] = (250-(B[0]*5)) + (B[1]*10); 
		B1[1] = (250+(B[0]*5)) - (B[2]*10);
		
		C1[0] = (250-(C[0]*5)) + (C[1]*10); 
		C1[1] = (250+(C[0]*5)) - (C[2]*10);
		
		D1[0] = (250-(D[0]*5)) + (D[1]*10); 
		D1[1] = (250+(D[0]*5)) - (D[2]*10);
		
		
		W1[0] = (250-(W[0]*5)) + (W[1]*10); 
		W1[1] = (250+(W[0]*5)) - (W[2]*10);
		
		X1[0] = (250-(X[0]*5)) + (X[1]*10); 
		X1[1] = (250+(X[0]*5)) - (X[2]*10);
		
		Y1[0] = (250-(Y[0]*5)) + (Y[1]*10); 
		Y1[1] = (250+(Y[0]*5)) - (Y[2]*10);
		
		Z1[0] = (250-(Z[0]*5)) + (Z[1]*10); 
		Z1[1] = (250+(Z[0]*5)) - (Z[2]*10); 
		
	} 
		
	
	//Einzeichnen
	
	public void paint(Graphics g) 
	{
		
		super.paint(g);
		
		Graphics2D g2D = (Graphics2D) g;
		
		g2D.setStroke(new BasicStroke(2));
		
		if (hide==false) 
		{ //Verstecken der Achsen
		
			g2D.drawLine(450, 50, 50, 450); //X-Achse
			g2D.drawLine(0, 250, 500, 250); //Y-Achse
			g2D.drawLine(250, 0, 250, 500); //Z-Achse
		
			g2D.setStroke(new BasicStroke(1));
				
			for (int i = 0; i < 39; i++) //X-Achse Nummern
			{	
			pushX = pushX +5;
			g2D.drawLine(450-pushX,45+pushX ,450-pushX,55+pushX); 	//Negativ
			g2D.drawLine(250-pushX,245+pushX ,250-pushX,255+pushX); //Positiv
			}
		
		
			for (int i = 0; i < 49; i++) //Y-Achse Nummern
			{										
			pushY = pushY +10;
			g2D.drawLine(pushY, 253,pushY, 247);
			}
		
			for (int i = 0; i < 49; i++) //Z-Achse Nummern
			{												
			pushZ = pushZ +10;
			g2D.drawLine(253,pushZ ,247,pushZ);
			}
	
			g2D.setFont(new Font("", Font.PLAIN, 20));
			g2D.drawString("x", 60, 460);
			g2D.drawString("y", 490, 240);				//Namen der Achsen
			g2D.drawString("z", 258, 10);
		
		
		}
		
		g2D.setStroke(new BasicStroke(3)); 
		g2D.setPaint(Color.GRAY);
	
		//Zeichnen nur der äußeren Verbindungslinien (Für jede Lage des größten Winkels anders)
	
		switch (max) 
		{ 
		case 1: g2D.drawLine(A1[0],A1[1],B1[0],B1[1]); g2D.drawLine(A1[0],A1[1],C1[0],C1[1]); //AB,AC,BD,CD
				g2D.drawLine(C1[0],C1[1],D1[0],D1[1]); g2D.drawLine(B1[0],B1[1],D1[0],D1[1]);
				if (ext==true) { //Verlängerung auf ein Quader
				g2D.drawLine(W1[0],W1[1], X1[0], X1[1]); g2D.drawLine(W1[0],W1[1], Y1[0], Y1[1]);
				g2D.drawLine(Y1[0],Y1[1], Z1[0], Z1[1]);g2D.drawLine(Z1[0],Z1[1], X1[0], X1[1]);}
		break;
		case 2: g2D.drawLine(A1[0],A1[1],B1[0],B1[1]); g2D.drawLine(A1[0],A1[1],D1[0],D1[1]); //AB,AD,BC,CD
				g2D.drawLine(C1[0],C1[1],D1[0],D1[1]); g2D.drawLine(B1[0],B1[1],C1[0],C1[1]);
				if (ext==true) {
				g2D.drawLine(W1[0],W1[1], X1[0], X1[1]);g2D.drawLine(W1[0],W1[1], Z1[0], Z1[1]);
				g2D.drawLine(Y1[0],Y1[1], Z1[0], Z1[1]); g2D.drawLine(X1[0],X1[1], Y1[0], Y1[1]);}
		break;
		case 3: g2D.drawLine(B1[0],B1[1],D1[0],D1[1]); g2D.drawLine(A1[0],A1[1],C1[0],C1[1]); //AC,AD,BC,BD
				g2D.drawLine(B1[0],B1[1],C1[0],C1[1]); g2D.drawLine(A1[0],A1[1],D1[0],D1[1]);
				if (ext==true) {
				g2D.drawLine(X1[0],X1[1], Z1[0], Z1[1]);g2D.drawLine(W1[0],W1[1], Y1[0], Y1[1]);
				g2D.drawLine(X1[0],X1[1], Y1[0], Y1[1]); g2D.drawLine(W1[0],W1[1], Z1[0], Z1[1]);}
		break;
	
		}
	
		if (ext==true) 
		{
			g2D.drawLine(A1[0],A1[1], W1[0], W1[1]); //W
			g2D.drawLine(B1[0],B1[1], X1[0], X1[1]); //X
			g2D.drawLine(C1[0],C1[1], Y1[0], Y1[1]); //Y
			g2D.drawLine(D1[0],D1[1], Z1[0], Z1[1]); //Z
		}
	
		g2D.setFont(new Font("Liberation Mono", Font.BOLD,18));
	
		g2D.setPaint(new Color (220,20,20));
		g2D.drawString("A", A1[0]-2, A1[1]);
		g2D.drawString("B", B1[0]-2, B1[1]); //Zeichnen der Punkt Namen
		g2D.drawString("C", C1[0]-2, C1[1]);
		g2D.drawString("D", D1[0]-2, D1[1]);

	
		pushX = 0; 
		pushY = 0; //Reset
		pushZ = 0;
	}
	
	
	public void extend() 
	{
		if (ext == false) {ext = true;}		//Wechsel zwischen Verlängerung auf Quader und normaler Ebene
		else {ext = false;}
		
		repaint();	
	}
	
	public void move() 
	{ 	
		//Kein D, weil es jedes mal eh neu berechnet wird
		if (rotate<12) 		{C[0]++; A[0]--; B[1]++; rotate++;}		
		else if (rotate<24) {B[0]++; C[1]--; A[1]++; rotate++;}
		else if (rotate<36) {A[0]++; B[1]--; C[0]--;rotate++;}	
		else if (rotate<48) {A[1]--; B[0]--; C[1]++; rotate++;}	
		else {rotate=0;}
	
		this.calculation();	repaint();	
	}

	public void movenum(int t) 
	{
	
		switch (t) 
		{
		case 1: A[0]++;B[0]++;C[0]++;		//1-3 XYZ positiv Bewegung, 4-6 negativ
		break;
		case 2: A[1]++;B[1]++;C[1]++;
		break;
		case 3: A[2]++;B[2]++;C[2]++;
		break;
		case 4: A[0]--;B[0]--;C[0]--;
		break;
		case 5: A[1]--;B[1]--;C[1]--;
		break;
		case 6: A[2]--;B[2]--;C[2]--;
		break;
		}
	
		this.calculation();	repaint();
	}
	
	public int[][] getValues() 
	{
	
		int[][] R = new int[6][3];
		R[0][0] = A[0];
		R[0][1] = A[1];		//A
		R[0][2] = A[2];
	
		R[1][0] = B[0];
		R[1][1] = B[1];		//B
		R[1][2] = B[2];
	
		R[2][0] = C[0];
		R[2][1] = C[1];		//C
		R[2][2] = C[2];
	
		R[3][0] = D[0];
		R[3][1] = D[1];		//D
		R[3][2] = D[2];
	
		R[4][0] = n[0]; 
		R[4][1] = n[1];		//n
		R[4][2] = n[2];
	
	
		R[5][0] = E;		//D von Ebene E
	
		return R;	
	}
	
	public void hide(boolean h) 
	{this.hide = h; repaint();}		//Empfangen des Booleans zum Verstecken der Achsen		
	
}

public class Insects
{
	InsectsPanel panel;
	JFrame frame;
	
	Insects()
	{	
		panel = new InsectsPanel();
		
		frame = new JFrame("Insects");
		
		frame.setResizable(false);
		frame.add(panel);
		frame.pack();
		
		frame.addKeyListener(new KeyListener() 
		{
			@Override
			public void keyTyped(KeyEvent e) 
			{	
				if (e.getKeyChar() == 'r') {panel.start();}
				else if (e.getKeyChar() == '1') {panel.changeAntAmount(false);}
				else if (e.getKeyChar() == '2') {panel.changeAntAmount(true);}
				else if (e.getKeyChar() == '3') {panel.changeAddAmount(false);}
				else if (e.getKeyChar() == '4') {panel.changeAddAmount(true);}
			}
			@Override
			public void keyPressed(KeyEvent e) {}
			@Override
			public void keyReleased(KeyEvent e) {}		
		});

		frame.setVisible(true);	
	}
	
	public void start(WindowEventHandler eventHandler)
	{
		frame.addWindowListener(eventHandler);
	}
}


public class InsectsPanel extends JPanel implements ActionListener
{
	private static final long serialVersionUID = 4974460083826443803L;
	
	static Timer timer; 	//movement
	Timer timer2;			//recolor
	static Timer survive;	//increasing or decreasing amount of ants
	
	
	int food = 0;	//how many are in which area
	int water = 0;
	
	int antAmount = 1000; //amount of ants at start
	double lengthvar = 100 / (double)antAmount;
	
	int antBuffer = 50000;
	
	int[] x = new int[antAmount+antBuffer];	//ants x and y numbers, the added number is how far the ants can increase
	int[] y = new int[antAmount+antBuffer];
	
	Color color1;	//changing ant color
	
	
	int[][] ants = {x, y};	//current ant locations
	int[][] prev = new int[2][antAmount+antBuffer];	//previous ant locations for resetting at walls
	
	
	Random random;
	
	int[] size;
	
	int wallamount = 11;
	int[][] walls = new int [wallamount][4];
	
	int changeAmount = 10;		//amount by which antAmount is changed in changeAntAmount()
	
	InsectsPanel()
	{
	
		walls[0][0] = 245; walls[0][1] = 250; walls[0][2] = 245; walls[0][3] = 300;
		walls[1][0] = 245; walls[1][1] = 0; walls[1][2] = 245; walls[1][3] = 220;
		walls[2][0] = 0; walls[2][1] = 305; walls[2][2] = 245; walls[2][3] = 305;
		walls[3][0] = 200; walls[3][1] = 310; walls[3][2] = 200; walls[3][3] = 500;
		walls[4][0] = 200; walls[4][1] = 530; walls[4][2] = 500; walls[4][3] = 530;
		walls[5][0] = 250; walls[5][1] = 130; walls[5][2] = 500; walls[5][3] = 130;
		walls[6][0] = 530; walls[6][1] = 60; walls[6][2] = 530; walls[6][3] = 500;
		walls[7][0] = 100; walls[7][1] = 150; walls[7][2] = 100; walls[7][3] = 600;
		walls[8][0] = 100; walls[8][1] = 630; walls[8][2] = 600; walls[8][3] = 630;
		walls[9][0] = 600; walls[9][1] = 160; walls[9][2] = 600; walls[9][3] = 630;
		walls[10][0] = 50; walls[10][1] = 80; walls[10][2] = 450; walls[10][3] = 80;
		
		random = new Random();
		
		timer = new Timer(50,this); //movement
		timer.start();
		survive = new Timer(2500,this);
		survive.setInitialDelay(10000000/antAmount);
		survive.start();
		
		start();
			
	}
	
	public static void stop()
	{timer.stop(); survive.stop();}
	
	public void start()
	{
		for (int i = 0; i < x.length; i++)//setting the ants starting points
		{ 
			x[i]=325;y[i]=325;
		}

		color1 = new Color(random.nextInt(135)+120,random.nextInt(135)+120,random.nextInt(135)+120);
		
		this.setPreferredSize(new Dimension(650,650));
		this.setLayout(null);
		
		size = new int[antAmount+50000];
		
		for (int i = 0; i <antAmount+50000; i++) 
		{
		size[i] = random.nextInt(4)+2;
		}	
	}

	public boolean isInside(int x, int y, int size)
	{
		for (int i = 0; i < wallamount; i++)
		{
			if (wallBounds(walls[i][0], walls[i][1], walls[i][2], walls[i][3],x,y,size)) {return true;}
		}
		return false;
	}
	
	public boolean wallBounds(int a, int b,  int a2,int b2,  int x, int y, int size)
	{
		if((x > a-6 &&  x < a2+6 && y > b-6 &&  y < b2+6) 
		|| (x+size > a-5 &&  x+size < a2+5 && y+size > b-5 &&  y+size < b2+5))
		{return true;}
		
		else return false;	
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
	
		if (e.getSource()==timer) 
		{
			for (int i = antAmount-1; i >=0; i--) 
			{
		
				prev[0][i] = ants[0][i]; //setting the new previous position
				prev[1][i] = ants[1][i];	
				
				switch (random.nextInt(5)) //chooses from 5 different kinds of movements, at 10 different speeds
				{ 
				case 0:
				break;
				case 1: ants[0][i]+=random.nextInt(10); 
				break;
				case 2: ants[0][i]-=random.nextInt(10);
				break;
				case 3: ants[1][i]+=random.nextInt(10);
				break;
				case 4: ants[1][i]-=random.nextInt(10);
				break;
				}
	
				//wall detection
				if (ants[0][i]>0 && ants[0][i]<650 && ants[1][i]>0 && ants[1][i]<650)  // Is inside panel
				{
					if (isInside(ants[0][i], ants[1][i], size[i]))
					{ants[0][i]= prev[0][i]; ants[1][i]=prev[1][i];} //resetting if at wall
				}
	
				else {ants[0][i]= prev[0][i];ants[1][i]=prev[1][i];} //resetting if outside frame
	
				
			}
			repaint();
		}
	
		else if (e.getSource()==survive) 
		{
		
			for (int i = 0; i < antAmount-1; i++)
			{
			//add number of ants in each area
			if ((ants[0][i] > 0 && ants[0][i] < 100 && ants[1][i] > 145 && ants[1][i] < 605)) {food++;}
			else if ((ants[0][i] > 45 && ants[0][i] < 245 && ants[1][i] > 0 && ants[1][i] < 80)
			||ants[0][i] > 600 && ants[0][i] < 650 && ants[1][i] > 155 && ants[1][i] < 635) {water++;}
			}
	
			if ((int) (antAmount - (0.001*(((antAmount-food-water)/(antAmount/10)) - (food/2 + water)))) >= x.length) {return;}
			antAmount = (int) (antAmount - (0.001*(((antAmount-food-water)/(antAmount/10)) - (food/2 + water))));
		}
	//decreases ant amount by 0.001 times the ants not in resource areas divided by the length/10 minus 1/2 the
	//ant in foot and the ants in water
	}
	
	
	public void paint(Graphics g) 
	{
		
		Graphics2D g2D = (Graphics2D) g;
		
		//super.paint(g2D);
		
		g2D.setPaint(new Color (60,60,75));
		g2D.fillRect(0, 0, 650, 650); //background
		
		g2D.setPaint(new Color (100,70,70)); //food
		g2D.fillRect(0, 145, 100, 460);
		
		g2D.setPaint(new Color (80,80,110)); //water
		g2D.fillRect(600, 155, 50, 480);
		g2D.fillRect(45, 0, 200, 80);
		
		g2D.setPaint(new Color (80,80,90));		//gray area
		g2D.fillRect(250, 135, 278, 175);
		g2D.fillRect(205, 310,320, 215); 
		
		g2D.setPaint(new Color (40,40,40));	
		g2D.setStroke(new BasicStroke(10));
		
		for (int i = 0; i < wallamount; i++)
		{
			g2D.drawLine(walls[i][0],walls[i][1],walls[i][2],walls[i][3]);	//walls
		}
		
		lengthvar = 100 / (double)antAmount;
		
		for (int i = 0; i <=antAmount-1; i++) //drawing each ant
		{ 
		g2D.setPaint(new Color ((int) (color1.getRed()-(lengthvar*i)),(int) (color1.getGreen()-(lengthvar*i)),(int) (color1.getBlue()-(lengthvar*i))));
		g2D.fillRect(ants[0][i], ants[1][i], size[i], size[i]);
		}
		
		g2D.setPaint(Color.LIGHT_GRAY);
		g2D.fillRect(3, 4, 38, 15); //Counter Background
		
		g2D.setPaint(Color.BLACK);
		g2D.drawString(""+antAmount,5,15); //Counter
		
		
	}	
	
	
	public void changeAntAmount(boolean increase)
	{
		if (increase && antAmount+ changeAmount < x.length)
		{antAmount+=changeAmount;}
		else
		{antAmount-=changeAmount;}
	}
	
	public void changeAddAmount(boolean increase)
	{
		if (increase)
		{changeAmount*=2;}
		else if (changeAmount / 2 >= 1)
		{changeAmount/=2;}
	}
}

public class JumpAndRun 
{
	int fallingSpeed = 5;
	int timeUntilFall = 0;
	
	static Timer timer;			//decides the framerate (at 14 -> around 60fps)

	int scrollingSpeed;
	
	public void start(WindowEventHandler eventHandler, boolean fastMode) 
	{
		
		JFrame frame = new JFrame("Sidescroller");
		if (fastMode) {frame.setTitle("Speedrun");}
		
		if (fastMode) 
		{scrollingSpeed = 0;}
		else
		{scrollingSpeed = 2;}
		
		JumpAndRunPanel panel = new JumpAndRunPanel(scrollingSpeed);
		
		frame.addWindowListener(eventHandler);
		
		frame.add(panel);
		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true);
		
		ArrayList<Integer> pressedKeys = new ArrayList<>();
		
		timer = new Timer(14, new ActionListener() 
		{	@Override
			public void actionPerformed(ActionEvent e) 
			{
				int x = 0;
				int y = 0;
				
				if (pressedKeys.contains(68)) //D
				{if (fastMode) {x = 10;} else {x = 5;}} 	
				
				if (pressedKeys.contains(65)) //A
				{if (fastMode) {x = -10;} else {x = -5;}}
				
				if (panel.flyMode)
				{
					if (pressedKeys.contains(87)) {y = -5;} 	//W
					if (pressedKeys.contains(83)) {y = 5;} 		//S
					fallingSpeed = 0;
				}
				
				else 
				{
					if (pressedKeys.contains(32) && panel.jumpAllowed) //SPACE
					{
						fallingSpeed = -5; panel.jumpAllowed = false; 
						timeUntilFall = 35;	//countdown until player begins falling again starts
					}
					 
					
					if (timeUntilFall > 0) {timeUntilFall--;}	//counting down
					
					if (timeUntilFall == 10) {fallingSpeed = 1;}	//for a smoother transition in fallingSpeed
					else if (timeUntilFall == 0) {fallingSpeed = 5;}
					
					if (panel.onGround) {panel.jumpAllowed = true;}
				}
				
				panel.offset+=panel.scrollingSpeed;
				panel.movePlayer(x,y,fallingSpeed);	
			}	
		});
		
		timer.start();
		
		frame.addKeyListener(new KeyListener() 
		{
			@Override
			public void keyTyped(KeyEvent e) 
			{}
			
			@Override
			public void keyPressed(KeyEvent e) 
			{	
				int code = e.getKeyCode();
				
//				if (code ==521) {if (panel.changeSize(10)) {frame.pack();}}		//+
//				else if (code ==45) {if (panel.changeSize(-10)) {frame.pack();}}	//-
				
				if (!(pressedKeys.contains(code))) {pressedKeys.add(code);}
				
				if (code == 70) {panel.flyMode = !panel.flyMode;}	//F
				
				else if (code == 82) {panel.start(false,false);} //R Restart
				else if (code == 84) {panel.start(true,false);} //T	Restart + new map
				
				//CTRL S
				else if (code == 83 && e.isControlDown()) 
				{if (!panel.paused) {pause();} panel.saveMap(); pressedKeys.remove(pressedKeys.indexOf(83));} //otherwise S stays pressed
				
				//CTRL L
				else if (code == 76 && e.isControlDown()) {if (!panel.paused) {pause();} panel.loadMap(); frame.pack();} 
				
				else if (code == 27) //ESC
				{pause();} 
				
			}
			@Override
			public void keyReleased(KeyEvent e) 
			{
				int x = pressedKeys.indexOf(e.getKeyCode());
				pressedKeys.remove(x);
			}	
			
			public void pause()
			{panel.paused = !panel.paused; panel.repaint(); if (timer.isRunning()) {timer.stop();} else {timer.start();}}
		});	
	}
	
	public static void stop()
	{ if (timer.isRunning()) {timer.stop();}}
}

public class JumpAndRunPanel extends JPanel //implements ActionListener
{
	private static final long serialVersionUID = 6717353794276866444L;

	int PANEL_HEIGHT = 400;
	int PANEL_WIDTH = (int) (PANEL_HEIGHT *1.5);
	
	byte[] gameMap = new byte[10000];
	int[][] mapElementStartingLoc = new int[gameMap.length][2]; //lowest X and Y of the Map Element
	
	int elementWidth = PANEL_HEIGHT /20;
	int elementsPerFrame = PANEL_WIDTH / elementWidth; //30
	int standardHeight = PANEL_HEIGHT /10;	//used to calculate the Elements individual height
	int[] mapElementHeight = new int[gameMap.length];
	
	//screen draws elements from mapStartingPoint to mapEndPoint
	int mapStartingPoint = 0;			
	int mapEndPoint = elementsPerFrame;
	
	int playerX;
	int playerY;
	int playerSize = PANEL_HEIGHT /20;
	
	int playerSlot;	//which slot is the player in respect to the screen
	int scoreSlot;	//which absolute element slot is the player in (used for the score -> how far have you gotten)
	
	int offset = 0;		//used for pushing the elements to the left -> scrolling
	int offsetSlot = offset /elementWidth;	// difference with prevoffsetSlot used to decrease or increase the two map starting points
	
	boolean beyondStartingPos = false;	//has the player moved beyond the starting position (should the screen scroll with them now?)
	//irrelevant for autoscrolling mode
	
	boolean flyMode = false;
	
	//boolean warning = false;
	
	boolean paused = false;
	
	//Timer timer = new Timer(1000, this);
	
	Random random = new Random();
	
	//indices for retrieving data out of textfile
	int charStart;
	int charEnd;
	
	int prevPlayerY;	//difference with playerY used to check if player is "on the ground" (not moving) - allows for sticking to roofs too
	boolean onGround = false;
	boolean jumpAllowed = false;
	
	int hitWallNum;		//which of the 7 possible wall layouts needs to be checked for collision
	
	int scrollingSpeed;
	
	//RNG values for the 7 wall layouts
	int chance0;
	int chance1;
	int chance2;
	int chance3;
	int chance4;
	int chance5;
	int chance6;
	int chance7;
	
	JumpAndRunPanel(int scrollingSpeed)
	{
		this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		
		this.scrollingSpeed = scrollingSpeed;
		
		if (scrollingSpeed == 0)
		{
			chance0 = 5;
			chance1 = 45;
			chance2 = 55;
			chance3 = 65;
			chance4 = 80;
			chance5 = 90;
			chance6 = 95;
			chance7 = 100;
		}
		
		else
		{
			chance0 = 50;
			chance1 = 60;
			chance2 = 70;
			chance3 = 80;
			chance4 = 85;
			chance5 = 90;
			chance6 = 95;
			chance7 = 100;
		}
		
		start(true,false);
	}
	
	public void start(boolean fullReset, boolean load)
	{
		
		if (!load) //normal respawn
		{
			playerX = 0;
			playerY = 10;
			offset = 0;
			mapStartingPoint = 0;
			mapEndPoint = elementsPerFrame;
			beyondStartingPos = false;
		}
		
		playerSlot = playerX / elementWidth;
		offsetSlot = offset /elementWidth;
			
		if (fullReset)
		{
			makeMap(gameMap);
		}
		
		//this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
	
		setMapValues();
		
	}
	
	public void movePlayer(int x, int y, int fallingSpeed)
	{
	
		prevPlayerY = playerY;
		playerY+= (y + fallingSpeed);
		
		playerX+= (x - scrollingSpeed);
		
		playerSlot = playerX / elementWidth;
		
		int prevOffestSlot = offsetSlot;
		
		if (scrollingSpeed == 0)
		{
			if (playerSlot > 15)  //on the right of the screen -> scroll further
			{
				offset+=x;
				playerX-=x;		//keep the player at the same position relative to the screen
				beyondStartingPos = true;
			}
			
			else if (beyondStartingPos && playerSlot < 10) //on the left of the screen -> scroll back
			{
				offset+=x;
				playerX-=x;
			}
			
			if (mapStartingPoint == 0) {beyondStartingPos = false;}
		}
		
		offsetSlot = offset /elementWidth;
		if (offsetSlot > prevOffestSlot) {mapEndPoint++; mapStartingPoint++;}
		else if (offsetSlot < prevOffestSlot) {mapEndPoint--; mapStartingPoint--;}
		
		//remove any changes to playerY of this round for better colision detection, then add it back
		playerY-=(y + fallingSpeed);
		if (hitWall(x, x)) {playerX-=x;}		
		playerY+=(y + fallingSpeed);
		
		if (hitWall(y, -1)) {playerY-=(y + fallingSpeed);}		//hit bottom
		else if (hitWall(y, 0)) {playerY-=(y + fallingSpeed);}	//hit top
		
		if (playerY - prevPlayerY == 0) {onGround = true;}
		else {onGround = false;}
		
		//DEATH CHECK
		if (playerY >= PANEL_HEIGHT-(standardHeight/2) || playerX < 0) {start(false,false);}
		
		scoreSlot = mapStartingPoint + playerSlot;

		repaint();
	}
	
	public boolean hitWall(int x, int factor)
	{
		int mapElement = 0;
		if (factor > 0) {mapElement = (playerX+offset+playerSize-x)/elementWidth;} //element touching the right corner of the player
		if (factor < 0) {mapElement = (playerX+offset)/elementWidth;} //element touching the left corner of the player
		if (factor == 0) {mapElement = (playerX+offset+playerSize-1)/elementWidth;} //element touching the right corner of the player but without horizontal movment
	
		if (mapElement < 0 || mapElement >= gameMap.length) {mapElement = 0;}
		if (gameMap[mapElement] == 0) {return false;} //dont try to calculate any elements past the specified array length
		
		hitWallNum = gameMap[mapElement];
		
		//CHECK NEXT ELEMENT
		int[] wallValues = getMapValues(hitWallNum);
		for (int i = 0; i < wallValues.length; i++)
		{
			if (i%2 == 1) {} //skip over every second iteration because we already check two values at once
			else if 
			(playerY > wallValues[i] && playerY < wallValues[i+1] 
			|| playerY+playerSize > wallValues[i] && playerY+playerSize < wallValues[i+1] 
			|| playerY == wallValues[i] && playerY+playerSize == wallValues[i+1]) //detecting just a single block with the same size as the player
			{return true;}
		}	
		return false;	
	}
	
	public void paint(Graphics g)
	{
		Graphics2D g2D = (Graphics2D) g;
		super.paint(g2D);
		
		//background
		g2D.setPaint(Color.DARK_GRAY);
		g2D.fillRect(0, 0, PANEL_WIDTH, PANEL_HEIGHT);
		
		g2D.setPaint(new Color (20,20,20));
		
		for (int i = mapStartingPoint; i <= mapEndPoint; i++)
		{
			if (i < gameMap.length) 
			{
				//element separation lines and numbers
				g2D.drawString(""+i, mapElementStartingLoc[i][0]+2-offset, 20);
				g2D.drawLine(elementWidth*i-offset, 0, elementWidth*i-offset, PANEL_HEIGHT);
				
				//drawing the main element
				if (gameMap[i] != 0)
				{g2D.fillRect(mapElementStartingLoc[i][0]-offset,mapElementStartingLoc[i][1], elementWidth, mapElementHeight[i]);}
			
				//drawing the secondary elements for 6 and 7
				if (gameMap[i] == 6)
				{g2D.fillRect(mapElementStartingLoc[i][0]-offset, PANEL_HEIGHT-standardHeight,  elementWidth, mapElementHeight[i]);}
				else if (gameMap[i] == 7)
				{g2D.fillRect(mapElementStartingLoc[i][0]-offset, PANEL_HEIGHT-(int)(standardHeight*0.5) - (PANEL_HEIGHT/4),  elementWidth, mapElementHeight[i]);}
			}
		}
		
		//PLAYER
		g2D.setPaint(new Color(120,200,120));
		g2D.fillRect(playerX, playerY, playerSize, playerSize);
		
		if (!flyMode) {g2D.setPaint(new Color(200,50,50));}
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2D.setFont(new Font("", Font.BOLD, PANEL_HEIGHT/10));
		
		String string = "";
		if (paused) {string = "PAUSED";}
		g2D.drawString(string, PANEL_WIDTH /38, PANEL_HEIGHT/6);
		
		//LAVA
		g2D.fillRect(0, PANEL_HEIGHT-(standardHeight/2), PANEL_WIDTH, standardHeight/2);
		
//		if (warning) 
//		{
//			g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//			g2D.setFont(new Font("", Font.BOLD, PANEL_HEIGHT/24));
//			g2D.drawString("YOU CAN ONLY CHANGE RESOLUTION AT THE START OF THE LEVEL", PANEL_WIDTH /38, PANEL_HEIGHT/3);
//		}
	}
	
	public void makeMap(byte[] map)
	{
		for (int i = 1; i < map.length; i++)
		{
			
			int rand = random.nextInt(100);
			
			if (map[i-1] > 4 && random.nextBoolean()) {map[i] = (byte) (random.nextInt(2)+4);}
			//if the last one was floating, there is a 50% change the next one will be too	
			
			else if (rand < chance0) {map[i] = 0;}
			else if (rand < chance1) {map[i] = 1;}
			else if (rand < chance2) {map[i] = 2;}
			else if (rand < chance3) {map[i] = 3;}
			else if (rand < chance4) {map[i] = 4;}
			else if (rand < chance5) {map[i] = 5;}
			else if (rand < chance6) {map[i] = 6;}
			else if (rand < chance7) {map[i] = 7;}	
			
		}
	}
	
	public void setMapValues()
	{
		for (int i = 0; i < gameMap.length; i++)
		{
			double heightFactor = 2;
			int extraHeight = 0;
			
			switch (gameMap[i])
			{
				case 1: heightFactor = 1;
				break;	
				case 2: heightFactor = 1.5;
				break;
				case 5: extraHeight = PANEL_HEIGHT /4; heightFactor = 0.5;
				break;
				case 6: extraHeight = PANEL_HEIGHT /4; heightFactor = 1;
				break;
				case 7: extraHeight = (int)(PANEL_HEIGHT /2.5); heightFactor = 0.5;
				break;
			}
			
			mapElementStartingLoc[i][0] = elementWidth*i;
			mapElementStartingLoc[i][1] = PANEL_HEIGHT-(int)(standardHeight*heightFactor) - extraHeight;
			
			mapElementHeight[i] = (int)(standardHeight*heightFactor);
			
		}
	}
	
	public int[] getMapValues(int wallNum)
	{
		if (wallNum ==1) 
		{
			int[] wallValues1 = {PANEL_HEIGHT-(int)(standardHeight), 	PANEL_HEIGHT,};	
			return wallValues1;
		}
		if (wallNum ==2) 
		{
			int[] wallValues2 = {PANEL_HEIGHT-(int)(standardHeight*1.5), 	PANEL_HEIGHT,};	
			return wallValues2;
		}
		if (wallNum < 5) 
		{
			int[] wallValues3 = {PANEL_HEIGHT-(int)(standardHeight*2), 	PANEL_HEIGHT,};	
			return wallValues3;
		}
		
		if (wallNum == 5) 
		{
			int[] wallValues5 = {PANEL_HEIGHT-(int)(standardHeight*0.5) - (PANEL_HEIGHT /4), PANEL_HEIGHT - (PANEL_HEIGHT /4)};
			return wallValues5;
		}
		
		if (wallNum == 6)
		{
			int[] wallValues6 = 
				{
						PANEL_HEIGHT-standardHeight - (PANEL_HEIGHT /4),
						PANEL_HEIGHT - (PANEL_HEIGHT /4),
						PANEL_HEIGHT-standardHeight, PANEL_HEIGHT
				};
			return wallValues6;
		}
		
		if (wallNum == 7)
		{
			int[] wallValues7 =
			{
				PANEL_HEIGHT-(int)(standardHeight*0.5) - (int)(PANEL_HEIGHT /2.5),
				PANEL_HEIGHT- (int)(PANEL_HEIGHT /2.5),
				PANEL_HEIGHT-(int)(standardHeight*0.5) - (PANEL_HEIGHT /4), 
				PANEL_HEIGHT - (PANEL_HEIGHT /4)
			};
			return wallValues7;
		}
		return null;
	}
	
	public void saveMap()
	{
		JFileChooser fileChooser = new JFileChooser();
		
		fileChooser.setCurrentDirectory(new File("C:\\Users\\Erik\\eclipse-workspace\\GameCollection\\JnRLevels"));
		
		int response = fileChooser.showSaveDialog(null); //select file to save
		
		File file = null;
		
		if (response == JFileChooser.APPROVE_OPTION) 
		{
			file = new File(fileChooser.getSelectedFile().getAbsolutePath());
		
			try 
			{
				FileWriter fw = new FileWriter(file,false);
				
				for (int i = 0; i < gameMap.length; i++)
				{
					fw.write(""+gameMap[i]);
				}
				
				//fw.write("|"+playerX+","+playerY+","+ mapStartingPoint+ ","+ mapEndPoint+ ","+ offset+","+ PANEL_HEIGHT+ ","+ beyondStartingPos+",");
				fw.write("|"+playerX+","+playerY+","+ mapStartingPoint+ ","+ mapEndPoint+ ","+ offset+","+ beyondStartingPos+",");
				
				fw.close();
			} catch (IOException e1) {e1.printStackTrace();}
		}
	}
	
	public void loadMap()
	{
		JFileChooser fileChooser = new JFileChooser();
		
		fileChooser.setCurrentDirectory(new File("C:\\Users\\Erik\\eclipse-workspace\\GameCollection\\JnRLevels"));
		
		int response = fileChooser.showOpenDialog(null); //select file to open
		
		if (response == JFileChooser.APPROVE_OPTION) 
		{
			File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
			
			try
			{
			
				BufferedReader reader = null;
				reader = new BufferedReader(new FileReader(file));
		
				char[] data = new char[gameMap.length+100];
				reader.read(data);
				
				reader.close();
				
				for (int i = 0; i < gameMap.length; i++)
				{gameMap[i] = (byte) (data[i] - '0');} //gives byte value of char
				 
				charStart = gameMap.length+1;
				charEnd = charStart-2;
				
				String text = setVariable(data);
				playerX = Integer.parseInt(text);
				
				text = setVariable(data);
				playerY = Integer.parseInt(text);
				
				text = setVariable(data);
				mapStartingPoint = Integer.parseInt(text);
				
				text = setVariable(data);
				mapEndPoint = Integer.parseInt(text);
				
				text = setVariable(data);
				offset = Integer.parseInt(text);
				
//				text = setVariable(data);
//				PANEL_HEIGHT = Integer.parseInt(text);
//				PANEL_WIDTH = (int)(PANEL_HEIGHT*1.5);
//				standardHeight = PANEL_HEIGHT /10;
//				elementWidth = PANEL_HEIGHT /20;
//				playerSize = PANEL_HEIGHT/20;
				
				beyondStartingPos = data[charEnd+2] == 't';
				
				start(false, true);
				repaint();
			
			} 	catch (IOException e1) {e1.printStackTrace();}
		}
	}
	
	public String setVariable(char[] data)
	{
		charEnd+=2;
		charStart = charEnd;
		
		while (data[charEnd+1] != ',')
		{charEnd++;}
		
		String text = "";
		
		for (int t = charStart; t <= charEnd; t++)
		{text+=data[t];}
		
		return text;
	}
	
//	public boolean changeSize(int amount)
//	{
//		if (beyondStartingPos) {warning = true; timer.start(); return false;}
//		
//		PANEL_HEIGHT+= amount;
//		PANEL_WIDTH = (int) (PANEL_HEIGHT *1.5);
//		standardHeight = PANEL_HEIGHT /10;
//		elementWidth = PANEL_HEIGHT /20;
//		playerSize = PANEL_HEIGHT/20;
//	
//		setMapValues();
//		
//		this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
//		repaint();
//		
//		return true;
//	}

//	@Override
//	public void actionPerformed(ActionEvent e) 
//	{
//		warning = false;
//		timer.stop();
//	}
}


public class MouseDodge 
{
	public void start(WindowEventHandler eventHandler) 
	{
		
		JFrame frame = new JFrame("Mouse Dodge");
		MouseDodgePanel panel = new MouseDodgePanel();
		
		frame.setResizable(false);

		frame.addWindowListener(eventHandler);
		
		frame.addKeyListener(new KeyListener() 
		{
			@Override
			public void keyTyped(KeyEvent e) 
			{	
				char key = e.getKeyChar();
				
				if (key == '+') {panel.changeSize(10); frame.pack();}
				else if (key == '-') {panel.changeSize(-10); frame.pack();}
				else if (key == 'r') {panel.start();}
				else if (key == '') {panel.pause();} //ESC Key
				else if (key == 'd') {panel.debug = !panel.debug; panel.repaint();}
				else if (key == 'f') {panel.darkMode = !panel.darkMode; panel.repaint();}
				
			}
			@Override
			public void keyPressed(KeyEvent e) {}
			@Override
			public void keyReleased(KeyEvent e) {}		
		});
		
		frame.add(panel);
		frame.pack();
		
		frame.setVisible(true);
	}
}

public class MouseDodgePanel extends JPanel implements ActionListener{

	private static final long serialVersionUID = 9082922097976866954L;

	int PANEL_SIZE = 400;
	
	double[] player = {PANEL_SIZE/1,PANEL_SIZE/2};		//player coordinates
	int playerSize = PANEL_SIZE/35;					
	Color playerColor = new Color (220,50,50);
	
	int enemyCount = 25;
	double[][] enemies = new double[enemyCount][4];		//enemy coordinates
	double[][] enemyGoals = new double [enemyCount][2];	//enemy vector goals
	int[] enemySize = new int[enemyCount];
	int[] sizeDivider = new int[enemyCount];		//divider that decides the ratio of screen size to enemy size
	boolean[] fillOrDraw = new boolean[enemyCount];	//g2D.fillRect or g2D.drawRect
	double speedMultiplier = 250/(double)PANEL_SIZE; //enemy vector multiplier as a ratio of screen size
	
	double[] boardData = new double[4];					//data of the centered grey board
	double endPointX;									//Max values of board coordinates
	double endPointY;
	
	static Timer moveTimer;								//Timer for each frame (~60fps)
	static Timer timer;									//Timer for time score 
	Random random = new Random();
	
	byte moveNum = 0;								//move counter to limit the amount of updates for the MouseAdapter			
	
	boolean paused;									//is the game paused
	
	boolean debug = false;							//is the game in debug mode
	
	boolean finished;								//has the round ended
	
	boolean moveActive = false;						//is the player allowed to move

	int time = 0;									//time score
	
	int bestTime;									//high score
	
	boolean darkMode = true;
	
	MouseDodgePanel()
	{				
		DragListener dragListener = new DragListener();
		this.addMouseMotionListener(dragListener);
		
		moveTimer = new Timer(14, this);
		timer = new Timer(1000,this);
		
		start();
	}
	
	public void start()
	{
		
		try {loadTime();} catch (IOException e) {System.out.println("Load failed");}
		
		finished = false;
		paused = true;
		time = 0;
		
		for (int i = 0; i < enemyCount; i++)
		{ 
			sizeDivider[i] = random.nextInt(15)+15;
			
			if (random.nextInt(4) == 1)				//only one in 4 should not be filled out
			{fillOrDraw[i] = true;}
			else {fillOrDraw[i] = false;}
			
			enemySize[i] = PANEL_SIZE/sizeDivider[i];
			
			//0 - starting X | 1 - starting Y | 2 - X size | 3 - Y size
			enemies[i][0] = random.nextInt((int)(PANEL_SIZE*1.7)); enemies[i][1] = random.nextInt(PANEL_SIZE);
			enemies[i][2] = enemySize[i]; enemies[i][3] = enemySize[i];
			
			enemyGoals[i][0] = random.nextInt((int)(PANEL_SIZE*1.7));
			enemyGoals[i][1] = random.nextInt(PANEL_SIZE);
			
		}
		
		this.setPreferredSize(new Dimension((int)(PANEL_SIZE*1.7),PANEL_SIZE));
		
		calcBoardData();
		
		playerColor = new Color (220,50,50);
		
		repaint();
	}
	
	public static void stop()
	{if (moveTimer.isRunning()) {moveTimer.stop();} if (timer.isRunning()) {timer.stop();}}
	
	public void calcBoardData()
	{
		boardData[0] = (int)(PANEL_SIZE/2.8); 		//starting X
		boardData[1] = (int)(PANEL_SIZE/7.5);		//starting Y
		boardData[2] = PANEL_SIZE;					//X Size
		boardData[3] = (int)(PANEL_SIZE/1.35);		//Y Size
		
		endPointX = boardData[0] + boardData[2];
		endPointY = boardData[1] + boardData[3];
	}
	
	public void paint (Graphics g)
	{
		Graphics2D g2D = (Graphics2D) g;
		super.paint(g);
		
		if (darkMode) 
		{
			g2D.setPaint(new Color(30,30,45));
			g2D.fillRect(0,0,(int)(PANEL_SIZE*1.7),PANEL_SIZE);
		}
		
		//game board
		if (!darkMode) {g2D.setPaint(Color.LIGHT_GRAY);}
		else {g2D.setPaint(new Color(75,75,105));}
		
		g2D.fillRect((int)boardData[0],(int)boardData[1],(int)boardData[2],(int)boardData[3]);
		
		if (!darkMode) {g2D.setPaint(Color.GRAY);}
		else {g2D.setPaint(new Color(130,130,170));}
		
		g2D.setStroke(new BasicStroke(2));
		g2D.drawRect((int)boardData[0],(int)boardData[1],(int)boardData[2],(int)boardData[3]);
		
		//player
		g2D.setPaint(playerColor);
		if (finished) {g2D.setPaint(Color.GREEN);}
		g2D.fillRect((int)player[0], (int)player[1], playerSize, playerSize);
		
		//enemies
		
		if (!darkMode) {g2D.setPaint(Color.GRAY);}
		else {g2D.setPaint(new Color(150,150,200));}
		
		for (int i = 0; i < enemyCount; i++)
		{
			if (fillOrDraw[i])
			{g2D.drawRect((int)enemies[i][0], (int)enemies[i][1], (int)enemies[i][2], (int)enemies[i][3]);}
			else {g2D.fillRect((int)enemies[i][0], (int)enemies[i][1], (int)enemies[i][2], (int)enemies[i][3]);}
		}
		
		if (debug)
		{
			//Shows enemy goals
			g2D.setPaint(Color.GREEN);
			for (int i = 0; i < enemyCount; i++)
			{g2D.fillRect((int)enemyGoals[i][0],(int)enemyGoals[i][1], 10, 10);}	
	
			g2D.setPaint(new Color(130,130,170));
			for (int i = 0; i < enemyCount; i++)
			{g2D.drawLine((int)enemyGoals[i][0],(int)enemyGoals[i][1], (int)enemies[i][0], (int)enemies[i][1]);}
		}	
		
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		if (paused)
		{
			if (!darkMode) {g2D.setPaint(new Color (220,220,220));}
			else {g2D.setPaint(new Color(220,220,250));}
			g2D.fillRect((int)(PANEL_SIZE/1.55), PANEL_SIZE/55, (int)(PANEL_SIZE/2.35), PANEL_SIZE/10);
			
			if (!darkMode) {g2D.setPaint(Color.GRAY);}
			else {g2D.setPaint(new Color(130,130,170));}
			g2D.drawRect((int)(PANEL_SIZE/1.55), PANEL_SIZE/55, (int)(PANEL_SIZE/2.35), PANEL_SIZE/10);
			if (darkMode) {g2D.setPaint(new Color(110,110,150));}
			g2D.setFont(new Font (null, Font.BOLD, PANEL_SIZE/11));
			g2D.drawString("PAUSED", (int)(PANEL_SIZE/1.48), PANEL_SIZE/10);
		}
		
		if (finished)
		{
			if (!darkMode) {g2D.setPaint(new Color (220,220,220));}
			else {g2D.setPaint(new Color(200,200,230));}
			g2D.fillRect((int)(PANEL_SIZE/1.55), PANEL_SIZE/55, (int)(PANEL_SIZE/2.3), PANEL_SIZE/10);
			
			if (!darkMode) {g2D.setPaint(Color.GRAY);}
			else {g2D.setPaint(new Color(130,130,170));}
			g2D.drawRect((int)(PANEL_SIZE/1.55), PANEL_SIZE/55, (int)(PANEL_SIZE/2.3), PANEL_SIZE/10);
			g2D.setPaint(playerColor);
			g2D.setFont(new Font (null, Font.BOLD, PANEL_SIZE/11));
			g2D.drawString("FINISHED", (int)(PANEL_SIZE/1.53), PANEL_SIZE/10);
		}
		
		if (!darkMode) {g2D.setPaint(new Color (220,220,220));}
		else {g2D.setPaint(new Color(200,200,230));}
		
		//UI boards
		g2D.fillRect((int)(PANEL_SIZE*1.5), PANEL_SIZE/6, PANEL_SIZE/6, PANEL_SIZE/4);
		g2D.fillRect(PANEL_SIZE/20, PANEL_SIZE/4, PANEL_SIZE/4, PANEL_SIZE/2 );
		
		if (!darkMode) {g2D.setPaint(Color.GRAY);}
		else {g2D.setPaint(new Color(130,130,170));}
		g2D.drawRect(PANEL_SIZE/20, PANEL_SIZE/4, PANEL_SIZE/4, PANEL_SIZE/2 );
		g2D.drawRect((int)(PANEL_SIZE*1.5), PANEL_SIZE/6, PANEL_SIZE/6, PANEL_SIZE/4);

		if (darkMode) {g2D.setPaint(new Color(110,110,150));}
		g2D.setFont(new Font (null, Font.BOLD, PANEL_SIZE/30));
		g2D.drawString("TIME", (int)(PANEL_SIZE*1.51), (int)(PANEL_SIZE/4.8));
		
		g2D.drawString(time+"s", (int)(PANEL_SIZE*1.51), (int)(PANEL_SIZE/3.9));
		
		g2D.drawString(bestTime+"s", (int)(PANEL_SIZE*1.51), (int)(PANEL_SIZE/2.7));
		
		g2D.drawString("BEST", (int)(PANEL_SIZE*1.51), (int)(PANEL_SIZE/3.1));
		
		g2D.setFont(new Font (null, Font.BOLD, PANEL_SIZE/35));
		g2D.drawString("AVOID THE", PANEL_SIZE/11, (int)(PANEL_SIZE/3.4));
		g2D.drawString("GREY SQUARES", PANEL_SIZE/16, PANEL_SIZE/3);
		
		g2D.drawString("ESC - PAUSE", PANEL_SIZE/15, (int)(PANEL_SIZE/2.28));
		g2D.drawString("R - RESTART", PANEL_SIZE/15, (int)(PANEL_SIZE/2.1));
		g2D.drawString("D - DEBUG", PANEL_SIZE/15, (int)(PANEL_SIZE/1.95));
		
		g2D.drawString("RESOLUTION", PANEL_SIZE/12, (int)(PANEL_SIZE/1.6));
		g2D.drawString("+ INCREASE", PANEL_SIZE/15, (int)(PANEL_SIZE/1.48));
		g2D.drawString("- DECREASE", PANEL_SIZE/15, (int)(PANEL_SIZE/1.4));
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == moveTimer)
		{move();}
		
		else {time++;}
	}
	
	public void move()
	{
		for (int i = 0; i < enemyCount; i++)
		{
			double[] v = new double[2];
			v[0] = enemyGoals[i][0] - enemies[i][0];
			v[1] = enemyGoals[i][1] - enemies[i][1];
		
			double movelength = Math.sqrt(Math.pow(v[0], 2)+Math.pow(v[1], 2));
			
			//has the enemy reached its goal -> new goal
			if (Math.abs(v[0]) < (PANEL_SIZE/200) && Math.abs(v[1]) < (PANEL_SIZE/200)) 
			{
				enemyGoals[i][0] = random.nextInt((int)(PANEL_SIZE*1.5));
				enemyGoals[i][1] = random.nextInt(PANEL_SIZE);
			}
			
			v[0] /= movelength*speedMultiplier; v[1] /= movelength*speedMultiplier;
			
			enemies[i][0]+= v[0];
			enemies[i][1]+= v[1];
			
		}
		
		if (isInside(player[0],player[1]))
		{end();}		
		
		repaint();
	}

	
	public boolean isInside(double x, double y)
	{

		for (int i = 0; i < enemyCount; i++)
		{
			if (enemyBounds(enemies[i][0], enemies[i][1], enemies[i][2], enemies[i][3],x,y)) {return true;}
		}
		
		return false;
	}
	
	public boolean enemyBounds(double a, double b,  double a2,double b2,  double x, double y)
	{
		boolean isInside = 
				(
						(x > a &&  x < a+a2 && y > b &&  y < b+b2)
					||	(x+playerSize > a &&  x+playerSize < a+a2 && y+playerSize > b &&  y+playerSize < b+b2)
					||	(x+playerSize > a &&  x+playerSize < a+a2 && y > b &&  y < b+b2)
					||	(x > a &&  x < a+a2 && y+playerSize > b &&  y+playerSize < b+b2)
				);
		
		return isInside;
	}
	
	public void pause()
	{
		if (!finished)
		{	
			if (paused)
			{moveActive = true;moveTimer.start();timer.start();paused = false;}
			
			else {moveActive = false; moveTimer.stop(); timer.stop(); paused = true;}
			
			repaint();
		}
		
	}
	
	public void end()
	{
		finished = true;
		
		moveActive = false;
		
		moveTimer.stop();
		
		if (time > bestTime)
		{
			bestTime = time;
			try {saveTime();} catch (IOException e) {System.out.println("Save failed");}
		}
		
		repaint();
	}
	
	private class DragListener extends MouseMotionAdapter
	{
	    public void mouseMoved(MouseEvent e) 
	    {     	
	    	if (moveActive)
	    	{
	    		moveNum++;
	    		
	    			if (moveNum == 2)
	    			{
	    				moveNum = 0;
	    				
	    				int x = e.getX();
	    				int y = e.getY();
	    				
	    				int locationCase = inBoard(x,y);
	    				
	    				if (locationCase != 1 && locationCase != 2) {player[0] = x-playerSize/2;}
	    				if (locationCase != 3 && locationCase != 2) {player[1] = y-playerSize/2;}
	    				    				
	    			}			
	    	}
	    	
	    }
	}
	
	public byte inBoard(int x, int y)
	{
		// 0 - None outside		1 - X outside	2 - Both outside	3 - Y outside
		
		byte locationCase = 0;
		
		boolean outsideX = (x < boardData[0] || x > endPointX);
		boolean outsideY = (y < boardData[1] || y > endPointY);
		
		if (outsideX) {locationCase++;}
		if (outsideY) {locationCase++;}
		
		if (locationCase == 1) {if (outsideY) {locationCase= 3;}}
		
		return locationCase;
	}
	
	public void changeSize(int c) 
	{
		double[][] enemyLocRatio = new double[enemyCount][2];
		for (int i = 0; i < enemyCount; i++)
		{
			enemyLocRatio[i][0] = enemies[i][0] / (PANEL_SIZE*1.7); 
			enemyLocRatio[i][1] = enemies[i][1] / PANEL_SIZE;
		}
		
		double[] playerLocRatio = new double[2];
		playerLocRatio[0] = player[0] / (PANEL_SIZE*1.7);
		playerLocRatio[1] = player[1] / PANEL_SIZE;
		
		PANEL_SIZE+=c;
		playerSize = PANEL_SIZE/30;
		
		speedMultiplier = 250/(double)PANEL_SIZE;
		
		player[0] = playerLocRatio[0] * PANEL_SIZE*1.7;
		player[1] = playerLocRatio[1] * PANEL_SIZE;
		
		for (int i = 0; i < enemyCount; i++)
		{
			enemySize[i] = PANEL_SIZE/sizeDivider[i];
			
			enemies[i][0] = enemyLocRatio[i][0] * PANEL_SIZE*1.7; 
			enemies[i][1] = enemyLocRatio[i][1] * PANEL_SIZE;
			enemies[i][2] = enemySize[i];enemies[i][3] = enemySize[i];
		}
		
		
		calcBoardData();
			
		this.setPreferredSize(new Dimension((int)(PANEL_SIZE*1.7),PANEL_SIZE));
		repaint(); 
	}
	
	public void saveTime() throws IOException
	{
		FileWriter fw = new FileWriter("Highscore.txt",false);
		fw.write(""+bestTime);
		fw.close();
	}
	
	public void loadTime() throws IOException
	{
		BufferedReader reader = new BufferedReader(new FileReader("Highscore.txt"));
		String text = reader.readLine();	
		reader.close();
		
		if (text != null)
		{bestTime = Integer.parseInt(text);}	
	}
}

public class ParallelUniverses 
{
	//pressedKeys List because keyPressed() has an annoying delay before it registers a key as held down
	static ArrayList<Integer> pressedKeys = new ArrayList<>();
	
	static ParallelUniversesPanel panel;
	
	static Timer timer = new Timer(25, new ActionListener()
	{	@Override
		public void actionPerformed(ActionEvent e) 
		{
			int x = 0;
			int y = 0;	
			
			boolean move = false;
			int loop = 1;
			
			if (pressedKeys.contains(68)) {x = 1;  move = true;} 	//D
			if (pressedKeys.contains(65)) {x = -1; move = true;} 	//A
			if (pressedKeys.contains(87)) {y = -1; move = true;} 	//W
			if (pressedKeys.contains(83)) {y = 1;  move = true;} 	//S
			
			if (pressedKeys.contains(16)) {loop = 4;}	//SHIFT
		
			if(move) //don't do an unnecessary calculation
			{
				//handle speed thorugh loops instead of increasing x  or y because we need to calculate 1 pixel at a time
				for (int i = 0; i < loop; i++) 
				{panel.move(x,y);} 
				panel.repaint();
			}
		}	
	});
	
	public void start(WindowEventHandler eventHandler) 
	{
		JFrame frame = new JFrame("Parallel Universes");
		//frame.setIconImage(MainMenu.img.getImage());
		panel = new ParallelUniversesPanel();
		
		timer.start();
		
		frame.addWindowListener(eventHandler);
		frame.setResizable(true);
		frame.add(panel);
		frame.pack();
		
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
				
				if (e.getKeyCode() == 69) {panel.hideUniverses();} //E
			
			}
			@Override
			public void keyReleased(KeyEvent e) 
			{
				int x = pressedKeys.indexOf(e.getKeyCode());
				pressedKeys.remove(x);
			}		
		});	
		frame.setVisible(true);
	}
	
	public static void stop()
	{if (timer.isRunning()) {timer.stop();}}
}


public class ParallelUniversesPanel extends JPanel
{
	private static final long serialVersionUID = 9082922097976866954L;
	
	int PANEL_SIZE = 623;
	
	int[] location = {313,313};			//LOCATION ON THE ENTIRE
	int[] relativeLocation = {6,6};		//LOCATION WITHIN THE MAIN UNIVERSE
	

	int borderSize = 2; //BORDER BETWEEN UNIVERSES
	
	// 64 --> BITS FOR THE RELATIVELOCATION (WHEN SHOULD AN OVERFLOW OCCUR)
	//--> LOWER BOUND = -32 , UPPER BOUND = 31
	//OFFSET +3 BECAUSE OF PLAYERSIZE - 1/2 BORDERSIZE = 3 (SEE MOVEMENT CALCULATION FOR FURTHER EXPLANATION)
	int upperEdge = 34;
	int lowerEdge = -29;
		
	int playerSize = 4;
	int halfPlayerSize = 2;
	
	int constantAdd = 307;		//CONSTANT THAT NEEDS TO BE ADDED TO THE RELATIVELOCATION TO MAKE IT ABSOLUTE FOR DRAW()

	int universesPerRow = 9;
	
	//OFFSETS NEEDED FOR THE PARALLELUNIVERSES TO LINE UP WITH THE MAIN UNIVERSE PROPERLY
	int outlineOffset = 1;			
	int rect1Offset = outlineOffset + 15;
	int rect2Offset = outlineOffset + 35;
	
	int rect1Size = 25;
	int rect2Size = 15;
	
	int universeSize = 69;
	
	boolean universesVisible = true;
	
	ParallelUniversesPanel()
	{this.setPreferredSize(new Dimension (PANEL_SIZE,PANEL_SIZE));}
	
	public void paint (Graphics g)
	{
		Graphics2D g2D = (Graphics2D) g;
		super.paint(g);
		
		//BACKGROUND
		g2D.setPaint(new Color (80,80,80)); //DARK GRAY
		g2D.fillRect(0, 0, PANEL_SIZE, PANEL_SIZE);
	
		//SETTINGS FOR THE UNIVERSES
		g2D.setPaint(new Color (180,180,180)); //GRAY
		g2D.setStroke(new BasicStroke(borderSize));
		
		if (universesVisible)
		{
			for (int i = 0; i < universesPerRow; i++)
			{
				for (int j = 0; j < universesPerRow; j++)
				{
					if (!(i == 4 && j == 4))
					{
						g2D.drawRect(outlineOffset+(j*(universeSize)), outlineOffset+(i*(universeSize)), universeSize, universeSize);
						g2D.drawRect(rect1Offset+(j*(universeSize)), rect1Offset+(i*(universeSize)), rect1Size, rect1Size);
						g2D.drawRect(rect2Offset+(j*(universeSize)), rect2Offset+(i*(universeSize)), rect2Size, rect2Size);
					}
				}
			}
		}
		
		//ORIGINAL UNIVERSE
		g2D.setPaint(new Color (50,170,250)); //BLUE-ISH
		g2D.drawRect(rect1Offset+(4*(universeSize)), rect1Offset+(4*(universeSize)), rect1Size, rect1Size);
		g2D.drawRect(rect2Offset+(4*(universeSize)), rect2Offset+(4*(universeSize)), rect2Size, rect2Size);
		g2D.drawRect(outlineOffset+(4*(universeSize)), outlineOffset+(4*(universeSize)), universeSize, universeSize);
		
		
		//DRAW PLAYER LOCATION
		g2D.setPaint(new Color (50,250,140)); //GREEN-ISH
		g2D.fillRect(location[0], location[1], playerSize, playerSize);
		
		//DRAW RELATIVE PLAYER LOCATION
		g2D.setPaint(new Color (255,80,80)); //RED
		g2D.fillRect(relativeLocation[0]+constantAdd, relativeLocation[1]+constantAdd, playerSize, playerSize);
		
	}
	
	public void hideUniverses()
	{
		universesVisible = !universesVisible;
		repaint();
	}
	
	public void move(int x, int y)
	{
		location[0]+=x;
    	location[1]+=y;
    	
    	//SIMULATE THE INTEGER OVERFLOWS
    	relativeLocation[0]+= x;
		
		if (relativeLocation[0] > upperEdge) 
    	{
    		relativeLocation[0] = lowerEdge; 
    		location[0]+=playerSize + 1;
    		//TO GO THROUGH THE BORDER TO THE RIGHT, FIRST PUT THE COORDINATE (LEFT CORNER) TO WHERE THE 
    		//RIGHT CORNER WAS WITH +PLAYERSIZE, THEN, SINCE LOCATION HAS ALREADY BEEN INCREMENTED BY 1  
    		//AND BORDERSIZE IS 2, INCREASE ONLY BY 1 MORE (1/2 BORDERSIZE) TO GO  THROUGH THE WALL
    	}
		
    	else if (relativeLocation[0] < lowerEdge) 
    	{
    		relativeLocation[0] = upperEdge;
    		location[0]-=playerSize + 1;
    		//TO GO THROUGH THE BORDER TO THE LEFT, FIRST PUT THE RIGHT CORNER TO WHERE THE 
    		//LEFT CORNER WAS WITH -PLAYERSIZE, THEN, SINCE LOCATION HAS ALREADY BEEN DECREMENTED BY 1 
    		//AND BORDERSIZE IS 2, DECREASE ONLY BY 1 MORE (1/2 BORDERSIZE) TO GO THROUGH THE WALL
    	}
		
		//SAME THING BUT FOR Y COORDINATES
		relativeLocation[1]+= y;
		
		if (relativeLocation[1] > upperEdge) 
    	{relativeLocation[1] = lowerEdge; location[1]+=playerSize + 1;}
    	else if (relativeLocation[1] < lowerEdge) 
    	{relativeLocation[1] = upperEdge;location[1]-=playerSize + 1;}
		
	}
}

public class Particles extends JFrame
{
	private static final long serialVersionUID = -1650331621506133111L;
	
	ParticlesPanel panel;
	
	Particles(){
		
		panel = new ParticlesPanel();
		
		this.setTitle("Particles");
		
		this.addKeyListener(new KeyListener() 
		{

			@Override
			public void keyTyped(KeyEvent e) 
			{
				if (e.getKeyChar() == 'r') {panel.reset();}
			}

			@Override
			public void keyPressed(KeyEvent e) {}
			@Override
			public void keyReleased(KeyEvent e) {}
			
		});
		
		this.setResizable(false);
		this.add(panel);
		this.pack();
		
		this.setVisible(true);
		
	}
	
	public void start(WindowEventHandler eventHandler)
	{
		this.addWindowListener(eventHandler);
	}
	
}

public class ParticlesPanel extends JPanel implements ActionListener{
	private static final long serialVersionUID = 6395869615896081681L;
		
	int xloc = 0; 		//x location of the mouse
	int prevloc = 0; 	//previous x location of the mouse
	int yloc = 0;		//y location of the mouse
	int size = 20;		//size of mouse influence
	
	
	int length = 6000;	//length of array of squares
	
	int[][] sqloc = new int [2][length]; //location of squares 0-x 1-y
	
	int[] sqsizeX = new int [length];		//x sizes of squares
	int[] sqsizeY = new int [length];		//y sizes of squares
	
	double [] dist = new double [length];	//distance of squares from the center
	
	boolean[] colorbool = new boolean[length];	//which ever color of two the square will have
	
	Color[] colors = new Color[length]; //square colors
	
	static Timer timer; 		//timer for square movement
	static Timer timer2;		//timer for color palette change
	
	Random random;	
	
	//starting color palette
	Color color1 = (new Color (170,170,245)); 
	Color color2 = (new Color (245,170,245));
	
	
	ParticlesPanel(){
		
		random = new Random();
		
		for (int i = 0; i < length-1; i++) {
		sqloc[0][i] = random.nextInt(640);
		sqloc[1][i] = random.nextInt(640);
		
		sqsizeX[i] = random.nextInt(9)+5;
		sqsizeY[i] = random.nextInt(9)+3;
		
		dist[i] = Math.abs(sqloc[0][i]-325) + Math.abs(sqloc[1][i]-325); //distance from center
		
		colorbool[i] = random.nextBoolean();
		
		if (colorbool[i]) {	//switch between the two colors, then caculates brightness by distance
		colors[i] = (new Color ((int) (Math.abs(color1.getRed()-(dist[i] / 5))),(int) (Math.abs(color1.getGreen()-(dist[i] / 5))),(int) (Math.abs(color1.getBlue()-(dist[i] / 5)))));
		} else {
		colors[i] = (new Color ((int) (Math.abs(color2.getRed()-(dist[i] / 5))),(int) (Math.abs(color2.getGreen()-(dist[i] / 5))),(int) (Math.abs(color2.getBlue()-(dist[i] / 5)))));	
		}
		
		}
		
		DragListener dragListener = new DragListener(); 
		
		timer = new Timer(50, this);
		timer.start();
		
		timer2 = new Timer(5000, this);
		timer2.start();
		          
		this.addMouseMotionListener(dragListener);
		
		this.setPreferredSize(new Dimension(650,650));
		this.setLayout(null);
		
	}
	
	public static void stop()
	{timer.stop(); timer2.stop();}
	
	public void reset()
	{
		for (int i = 0; i < length-1; i++) {
			sqloc[0][i] = random.nextInt(640);
			sqloc[1][i] = random.nextInt(640);
			
			sqsizeX[i] = random.nextInt(9)+5;
			sqsizeY[i] = random.nextInt(9)+3;
			
			dist[i] = Math.abs(sqloc[0][i]-325) + Math.abs(sqloc[1][i]-325); //distance from center
			
			colorbool[i] = random.nextBoolean();
			
			if (colorbool[i]) {	//switch between the two colors, then caculates brightness by distance
			colors[i] = (new Color ((int) (Math.abs(color1.getRed()-(dist[i] / 5))),(int) (Math.abs(color1.getGreen()-(dist[i] / 5))),(int) (Math.abs(color1.getBlue()-(dist[i] / 5)))));
			} else {
			colors[i] = (new Color ((int) (Math.abs(color2.getRed()-(dist[i] / 5))),(int) (Math.abs(color2.getGreen()-(dist[i] / 5))),(int) (Math.abs(color2.getBlue()-(dist[i] / 5)))));	
			}
			
			}
	}
	
	public void paint(Graphics g) {
		
		Graphics2D g2D = (Graphics2D) g;
	
		g2D.setPaint(new Color(60,60,95));
		g2D.fillRect(0, 0, 650, 650); 		//background
		
		for (int i = 0; i < length-1; i++) {	//all squares
					
		//g2D.setPaint(new Color ((int) (color1.getRed()-(lengthvar*i)),(int) (color1.getGreen()-(lengthvar*i)),(int) (color1.getBlue()-(lengthvar*i))));
			
		g2D.setPaint(colors[i]);
		
		g2D.fillRect(sqloc[0][i], sqloc[1][i], sqsizeX[i], sqsizeY[i]); 
		}
		
		
	}
	
	
	
	private class DragListener extends MouseMotionAdapter{
	    public void mouseDragged(MouseEvent e) { 
	    	
	    		Point currentPt = e.getPoint();
	               xloc = (int)currentPt.getX();
	               yloc = (int)currentPt.getY();
	               
	               for (int i = 0; i < length-1; i++) { //pushes squares to the side if they are inside mouse obj
	            	   if (sqloc[0][i] > xloc-16 && sqloc[0][i] < xloc+size-12 && sqloc[1][i] > yloc-16 && sqloc[1][i] < yloc+size-12)	
	       			{	if (prevloc < xloc) {sqloc[0][i]+=10; sqloc[1][i]+=10;}
	       				else {sqloc[0][i]-=10; sqloc[1][i]-=10;}	
	       			}
	               }
	               prevloc = xloc;
	              repaint();    
	         }
	    }

	@Override
	public void actionPerformed(ActionEvent e) {
	
	if (e.getSource()==timer) {
		
		for (int i = 0; i < length-1; i++) {	
	
				switch (random.nextInt(4)) {	//moves squares randomly
	
				case 0: sqloc[0][i]+=random.nextInt(2);
				break;
				case 1: sqloc[0][i]-=random.nextInt(2);
				break;
				case 2: sqloc[1][i]+=random.nextInt(2);
				break;
				case 3: sqloc[1][i]-=random.nextInt(2);
				break;
				}
		
		}

		repaint();
	}
	
	else {
		switch (random.nextInt(2)) {	//changes colors
		
		case 0: color1 = (new Color (170,245,245)); color2 = (new Color (170,170,245));
		break;
		case 1: color1 = (new Color (170,170,245)); color2 = (new Color (245,170,245));
		break;
		}
		for (int i = 0; i < length-1; i++) {
		if (colorbool[i]) {
			colors[i] = (new Color ((int) (Math.abs(color1.getRed()-(dist[i] / 5))),(int) (Math.abs(color1.getGreen()-(dist[i] / 5))),(int) (Math.abs(color1.getBlue()-(dist[i] / 5)))));
			} else {
			colors[i] = (new Color ((int) (Math.abs(color2.getRed()-(dist[i] / 5))),(int) (Math.abs(color2.getGreen()-(dist[i] / 5))),(int) (Math.abs(color2.getBlue()-(dist[i] / 5)))));	
			}
		}
	}
	
  }
	

}

public class Pathfind extends JFrame implements ActionListener
{
	private static final long serialVersionUID = 386670748457681736L;

	JMenuItem loadItem;
	JMenuItem saveItem;
	
	PathfindPanel panel;
	
	static private int gamemode;
	
	public Pathfind(WindowEventHandler eventHandler, int gamemode)
	{			
		Pathfind.gamemode = gamemode;
		//this.setIconImage(MainMenu.img.getImage());
		this.addWindowListener(eventHandler);
		this.setTitle("Pathfind");
		
		switch(gamemode)
		{
		case 1: panel = new PathfindNormal(); break;
		case 2: panel = new PathfindTrail();break;
		case 3: panel = new PathfindDemo();break;
		}
		
		this.setResizable(false);
		
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu =new JMenu("File");
		
		loadItem = new JMenuItem("Load");
		saveItem = new JMenuItem("Save");
		
		loadItem.addActionListener(this);
		saveItem.addActionListener(this);
		
		fileMenu.add(loadItem);
		fileMenu.add(saveItem);
		
		menuBar.add(fileMenu);
		this.setJMenuBar(menuBar);
		
		this.addKeyListener(new KeyListener() 
		{
			@Override
			public void keyTyped(KeyEvent e) 
			{	
				char key = e.getKeyChar();
				
				if (key == '+') {panel.changeSize(10); pack();}
				else if (key == '-') {panel.changeSize(-10); pack();}
				else if (key == '0') {panel.wipeBoard();}
				else if (key == 'r') {panel.start(false);} //restart
				else if (key == 't') {panel.start(true);}  //restart + new map
					
				else if (key == 'a' || key == 's' || key == 'd' || key == 'w')
				{panel.movePlayer(key);}
				
			}
			@Override
			public void keyPressed(KeyEvent e) {}
			@Override
			public void keyReleased(KeyEvent e) {}		
		});
		
		this.add(panel);
		this.pack();
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{	
		if (e.getSource() == loadItem)
		{
			JFileChooser fileChooser = new JFileChooser();
			
			fileChooser.setCurrentDirectory(new File(""));
			
			int response = fileChooser.showOpenDialog(null); //select file to open
			
			if (response == JFileChooser.APPROVE_OPTION) 
			{
				File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
				
				try
				{
					BufferedReader reader = null;
					reader = new BufferedReader(new FileReader(file));
			
					String text = reader.readLine();
					
					reader.close();
					
					int[] sizes = new int[2];
					int start = 0;
					int end = 0;
					while (text.charAt(end) != ';') {end++;}
					sizes[0] = Integer.parseInt(text.substring(start,end));
					
					end++;
					start = end;
					while (text.charAt(end) != ';') {end++;}
					sizes[1] = Integer.parseInt(text.substring(start,end));
					
					int[][] board = new int[sizes[1]][sizes[1]];
					end++;
					for (int i = 0; i < sizes[1]; i++)
					{
						for (int j = 0; j < sizes[1]; j++)
						{
							start = end;
							end++;
							board[i][j] = Integer.parseInt(text.substring(start,end));
						}
					}
					
					end--;
					int[][] chaser = new int[2][sizes[0]];
					int lastcomma = end;
					for (int i = 0; i < sizes[0]; i++)
					{
						end++;
						lastcomma++;
						start = end;
						while (text.charAt(end) != ';') {end++;}
						while (text.charAt(lastcomma) != ',') {lastcomma++;}
						chaser[0][i] = Integer.parseInt(text.substring(start,lastcomma));
						chaser[1][i] = Integer.parseInt(text.substring(lastcomma+1,end));
					}
					
					int[] player = new int[3];
					
					end++;
					start = end;
					while (text.charAt(end) != ';') {end++;}
					player[0] = Integer.parseInt(text.substring(start,end));
					
					end++;
					start = end;
					while (text.charAt(end) != ';') {end++;}
					player[1] = Integer.parseInt(text.substring(start,end));
					
					end++;
					start = end;
					while (text.charAt(end) != ';') {end++;}
					player[2] = Integer.parseInt(text.substring(start,end));
					
					panel.load(sizes, board, chaser, player);
				
				} 	catch (IOException e1) {e1.printStackTrace();}
			}
			
		}
		
		else if (e.getSource() == saveItem)
		{
			JFileChooser fileChooser = new JFileChooser();
			
			fileChooser.setCurrentDirectory(new File(""));
			
			int response = fileChooser.showSaveDialog(null); //select file to save
			
			File file = null;
			
			if (response == JFileChooser.APPROVE_OPTION) 
			{
				file = new File(fileChooser.getSelectedFile().getAbsolutePath());
			
				try 
				{
					int[][] board = null;
					int[][] chaser = null;
					int[] sizes = null; //0 is chasercount, 1 is GRID_SIZE
					int[] player = null; //0 is column, 1 is Row, 2 is counter
					
					board = panel.getBoard();
					chaser = panel.getChaser();
					sizes = panel.getSizes(); 
					player = panel.getPlayer();
			
					FileWriter fw = new FileWriter(file,true);
						
					fw.write(sizes[0]+";"+sizes[1]+";");
						
					for (int i = 0; i < sizes[1]; i++)
					{
						for (int j = 0; j < sizes[1]; j++)
						{
							fw.write(board[i][j]+"");
						}
					}
						
					for (int i = 0; i < sizes[0]; i++)
					{
						fw.write(chaser[0][i]+","+ chaser[1][i]+";");
					}
						
					fw.write(player[0]+";"+ player[1]+";"+ player[2]+";");
						
					fw.close();
				}			
				 catch (IOException e1) {e1.printStackTrace();}
				 	
				}
			}
		}
	
	public static void stop()
	{if (gamemode == 3) {if (PathfindDemo.timer.isRunning()) {PathfindDemo.timer.stop();}}}
}

public class PathfindDemo extends PathfindPanel implements ActionListener
{
	private static final long serialVersionUID = 3986470365499168687L;
	
	static int GRID_SIZE = 30;
	int PANEL_SIZE = 410;
	int BOX_SIZE = (PANEL_SIZE /GRID_SIZE)+1;
	
	
	int[][] board = new int[GRID_SIZE][GRID_SIZE];
	
	int chasercount = 50;
	int[][] chaser = new int [2][chasercount];
	int[] randommove = new int [chasercount]; 
		
	int playerColumn = -1;
	int playerRow= -1;
	
	static Timer timer;
	
	int step = -1;
	
	boolean movestate = false;
	
	Random random = new Random();
	
	Color halfred = new Color(200,50,50);
	
	
	PathfindDemo()
	{
		this.setPreferredSize(new Dimension (PANEL_SIZE,PANEL_SIZE));
		
		ClickListener clickListener = new ClickListener();
		this.addMouseListener(clickListener);
		
		start(true);
		
		timer = new Timer(50, this);
	}
	
	public static void stop()
	{if (timer.isRunning()) {timer.stop();}}
	
	public void movePlayer(char x) {}
	
	public int[][] getBoard()
	{return board;}
	
	public int[][] getChaser()
	{return chaser;}
	
	public int[] getSizes()
	{int[] sizes = {chasercount, GRID_SIZE}; return sizes;}
	
	public int[] getPlayer()
	{int[] player = {playerColumn, playerRow, 0}; return player;}
	
	public void load(int[]sizes, int[][]loadboard, int[][]loadchaser, int[] loadplayer)
	{
		GRID_SIZE = sizes[1];
		chasercount = sizes[0];
		
		for (int i = 0; i < GRID_SIZE; i++)
			{
				for (int j = 0; j < GRID_SIZE; j++)
				{
					board[i][j] = loadboard[i][j];
				}
			}
		
		for (int i = 0; i < chasercount; i++)
		{
			chaser[0][i] = loadchaser[0][i];
			chaser[1][i] = loadchaser[1][i];
			randommove[i] = 0;
		}
		
		playerColumn = loadplayer[0];;
		playerRow = loadplayer[1];
		
		BOX_SIZE = (PANEL_SIZE /GRID_SIZE)+1;
		repaint();
	}
	
	public void start(boolean fullReset)
	{
		if (fullReset)
		{
			for (int i = 0; i < GRID_SIZE; i++)
			{
				for (int j = 0; j < GRID_SIZE; j++)
				{
					if (random.nextInt(8) == 1) {board[i][j] = 1;}
					else {board[i][j] = 0;}	
				}
			}
		}
		
		for (int i = 0; i < chasercount; i++)
		{
			chaser[0][i] = random.nextInt(GRID_SIZE);
			chaser[1][i] = random.nextInt(GRID_SIZE);
			randommove[i] = 0;
		}			
		
		repaint();
	}
	
	private class ClickListener extends MouseAdapter
	{
	    public void mousePressed(MouseEvent e) 
	    {  
	    	int column = e.getX() / BOX_SIZE;
	    	int row = e.getY() / BOX_SIZE;
	    	
	    	if (e.isShiftDown())
	    	{
	    		if (board[row][column] == 0) {board[row][column] = 1;}
	    		else {board[row][column] = 0;}
	    	}
	    	
	    	else
	    	{
	    		if (!timer.isRunning()) {timer.start();}
	    		
	    		playerColumn = column;
	    		playerRow = row;
	    	}
	    	
	    	repaint();
	    }
	}
	
	public void changeSize(int c) 
	{
		PANEL_SIZE+=c; BOX_SIZE = (PANEL_SIZE /GRID_SIZE)+1; 
		repaint(); 
		this.setPreferredSize(new Dimension(PANEL_SIZE,PANEL_SIZE));
	}
	
	public void paint(Graphics g)
	{
		Graphics2D g2D = (Graphics2D) g;
		super.paint(g2D);
		
		g2D.setPaint(Color.DARK_GRAY);
		
		for (int row = 0; row < GRID_SIZE; row++)
		{
			for (int column = 0; column < GRID_SIZE; column++)
			{	
				if (board[row][column] != 0) 
				{g2D.fillRect(column * BOX_SIZE, row * BOX_SIZE, BOX_SIZE, BOX_SIZE);}
				
				g2D.drawRect(column * BOX_SIZE, row * BOX_SIZE, BOX_SIZE, BOX_SIZE); //Grid	
			}
		}
		
		g2D.setPaint(halfred);
		g2D.fillRect(playerColumn * BOX_SIZE, playerRow * BOX_SIZE, BOX_SIZE, BOX_SIZE);
		
		g2D.setPaint(new Color (0, 140, 250));
		for (int i = 0; i < chasercount; i++)
		{g2D.fillRect(chaser[0][i] * BOX_SIZE, chaser[1][i] * BOX_SIZE, BOX_SIZE, BOX_SIZE);}
	}
	
	public void nextStep(int chasernum)
	{
		if (chaser[0][chasernum] == playerColumn && chaser[1][chasernum] == playerRow)
		{return;}
		
		if (randommove[chasernum] == 0)
		{
			if (random.nextBoolean())
			{	
				if (directionMove(0,chasernum)) {return;}	
				if (directionMove(1,chasernum)) {return;}	
			}
			
			else 
			{
				if (directionMove(1,chasernum)) {return;}	
				if (directionMove(0,chasernum)) {return;}	
			}
		}
		
		randommove[chasernum]++;
		
		if (randommove[chasernum] < 3)
		{
			switch (random.nextInt(4))
			{
				case 0: if (isValidMove(chaser[0][chasernum]+1, chaser[1][chasernum]))  {chaser[0][chasernum]++; break;}
				case 1: if (isValidMove(chaser[0][chasernum]-1, chaser[1][chasernum])) {chaser[0][chasernum]--; break;}
				case 2: if (isValidMove(chaser[0][chasernum], chaser[1][chasernum]+1))  {chaser[1][chasernum]++; break;}
				case 3: if (isValidMove(chaser[0][chasernum], chaser[1][chasernum]-1)) {chaser[1][chasernum]--; break;}
			}
		}
	
		else {randommove[chasernum] = 0;}
	}
	
	public boolean isValidMove(int column, int row)
	{
		if (column > -1 && column < GRID_SIZE &&  row > -1 && row < GRID_SIZE)
		{return (board[row][column] == 0);}
		return false;
	}

	public boolean directionMove(int direction, int chasernum)
	{
		int[] rowcol = {playerColumn, playerRow};
		
		if (rowcol[direction] > chaser[direction][chasernum])
		{
			if (board[chaser[1][chasernum] + 1 + (direction-1)] [chaser[0][chasernum]+ Math.abs(direction-1)] == 0) 
			{chaser[direction][chasernum]++; return true;}
		}
		
		if (rowcol[direction] < chaser[direction][chasernum])
		{
			if (board[chaser[1][chasernum]- (1 + (direction-1))] [chaser[0][chasernum]- Math.abs(direction-1)] == 0) 
			{chaser[direction][chasernum]--; return true;}
		}
		
		return false;
	}
	
	public void oneMove()
	{	
		for (int i = 0; i < chasercount; i++)
		{nextStep(i);}
		
		repaint();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{oneMove();}
	
	public void wipeBoard()
	{
		for (int i = 0; i < GRID_SIZE; i++)
		{
			for (int j = 0; j < GRID_SIZE; j++)
			{
				board[i][j] = 0;
			}
		}
		
		repaint();
	}
}

public class PathfindNormal extends PathfindPanel implements ActionListener
{
	private static final long serialVersionUID = 3986470365499168687L;
	
	static int GRID_SIZE = 22;
	int PANEL_SIZE = 410;
	int BOX_SIZE = (PANEL_SIZE /GRID_SIZE)+1;
	
	int[][] board = new int[GRID_SIZE][GRID_SIZE];
	
	int chasercount = 10;
	int[][] chaser = new int[2][chasercount];
	
	int[] randommove = new int [chasercount]; 
	//how many times has the chaser needed to make a random move because something is blocking the direct path

	int playerColumn;
	int playerRow;
	
	int counter = 0;	//how many player moves
	
	Timer timer;
	
	Random random = new Random();
	
	Color halfred = new Color(200,50,50);
	
	boolean moveDone = true; 	//is the chaser move done? -> Player move ready
	
	boolean finished = false;	//have the chasers caught the player?
	
	//numbers used to push the counter coordinates on the two axis depending on the digit count
	int counterpush1;
	double counterpush2;
	
	
	PathfindNormal()
	{
		this.setPreferredSize(new Dimension (PANEL_SIZE,PANEL_SIZE));
		
		ClickListener clickListener = new ClickListener();
		this.addMouseListener(clickListener);
		
		timer = new Timer(100, this);
		
		start(true);
	}
	
	public int[][] getBoard()
	{return board;}
	
	public int[][] getChaser()
	{return chaser;}
	
	public int[] getSizes()
	{int[] sizes = {chasercount, GRID_SIZE}; return sizes;}
	
	public int[] getPlayer()
	{int[] player = {playerColumn, playerRow, counter}; return player;}
	
	public void start(boolean fullReset)
	{
		if (fullReset)
		{
			for (int i = 0; i < GRID_SIZE; i++)
			{
				for (int j = 0; j < GRID_SIZE; j++)
				{
					if (random.nextInt(4) == 1) {board[i][j] = 1;}
					else {board[i][j] = 0;}	
				}
			}
		}
		
		for (int i = 0; i < chasercount; i++)
		{
			chaser[0][i] = random.nextInt(GRID_SIZE);
			chaser[1][i] = random.nextInt(GRID_SIZE);
			randommove[i] = 0;
		}
		
		finished = false;
		counter = 0;
		
		playerColumn = random.nextInt(GRID_SIZE);
		playerRow = random.nextInt(GRID_SIZE);
		
		repaint();
	}
	
	public void load(int[]sizes, int[][]loadboard, int[][]loadchaser, int[] loadplayer)
	{
		GRID_SIZE = sizes[1];
		chasercount = sizes[0];
		
		for (int i = 0; i < GRID_SIZE; i++)
			{
				for (int j = 0; j < GRID_SIZE; j++)
				{
					board[i][j] = loadboard[i][j];
				}
			}
		
		for (int i = 0; i < chasercount; i++)
		{
			chaser[0][i] = loadchaser[0][i];
			chaser[1][i] = loadchaser[1][i];
			randommove[i] = 0;
		}
		
		finished = false;
		counter = loadplayer[2];
		
		playerColumn = loadplayer[0];;
		playerRow = loadplayer[1];
		
		BOX_SIZE = (PANEL_SIZE /GRID_SIZE)+1;
		repaint();
	}
	
	private class ClickListener extends MouseAdapter
	{
	    public void mousePressed(MouseEvent e) 
	    {  
	    	if (!finished)
	    	{
	    		int column = e.getX() / BOX_SIZE;
	    		int row = e.getY() / BOX_SIZE;
	    	
	    		if (e.isShiftDown())
	    		{
	    			if (board[row][column] == 0) {board[row][column] = 1;}
	    			else {board[row][column] = 0;}
	    		}
	    	
	    		else
	    		{
	    			playerColumn = column;
	    			playerRow = row;
	    		}
	    	
	    		repaint();
	    	}
	    }
	}
		
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		timer.stop();
		oneMove();
	}
	
	public void oneMove()
	{	
		for (int i = 0; i < chasercount; i++)
		{nextStep(i);}
		
		repaint();
		
		moveDone = true;
	}
	
	public void nextStep(int chasernum)
	{
			
		if (randommove[chasernum] == 0)
		{
			if (random.nextBoolean())
			{	
				if (directionMove(0,chasernum)) {return;}	
				if (directionMove(1,chasernum)) {return;}	
			}
			
			else 
			{
				if (directionMove(1,chasernum)) {return;}	
				if (directionMove(0,chasernum)) {return;}	
			}
		}
		
		randommove[chasernum]++;
		
		if (randommove[chasernum] < 3)
		{
			switch (random.nextInt(4))
			{
				case 0: if (isValidMove(chaser[0][chasernum]+1, chaser[1][chasernum]))  {chaser[0][chasernum]++; break;}
				case 1: if (isValidMove(chaser[0][chasernum]-1, chaser[1][chasernum])) {chaser[0][chasernum]--; break;}
				case 2: if (isValidMove(chaser[0][chasernum], chaser[1][chasernum]+1))  {chaser[1][chasernum]++; break;}
				case 3: if (isValidMove(chaser[0][chasernum], chaser[1][chasernum]-1)) {chaser[1][chasernum]--; break;}
			}
		}
	
		else {randommove[chasernum] = 0;}
	}
	
	public boolean isValidMove(int column, int row)
	{
		if (column > -1 && column < GRID_SIZE &&  row > -1 && row < GRID_SIZE)
		{return (board[row][column] == 0);}
		return false;
	}
	
	public boolean directionMove(int direction, int chasernum)
	{
		int[] rowcol = {playerColumn, playerRow};
		
		if (rowcol[direction] > chaser[direction][chasernum])
		{
			if (board[chaser[1][chasernum] + 1 + (direction-1)] [chaser[0][chasernum]+ Math.abs(direction-1)] == 0) 
			{chaser[direction][chasernum]++; return true;}
		}
		
		if (rowcol[direction] < chaser[direction][chasernum])
		{
			if (board[chaser[1][chasernum]- (1 + (direction-1))] [chaser[0][chasernum]- Math.abs(direction-1)] == 0) 
			{chaser[direction][chasernum]--; return true;}
		}
		
		return false;
	}
	
	public void wipeBoard()
	{
		if (!finished)
		{
			for (int i = 0; i < GRID_SIZE; i++)
			{
				for (int j = 0; j < GRID_SIZE; j++)
				{board[i][j] = 0;}
			}
		
			repaint();
		}
	}
	
	public void movePlayer(char key)
	{
		if (moveDone && !finished)
		{
			switch (key)
			{
				case 'a': if (isValidMove(playerColumn-1, playerRow)) {playerColumn--;} else {return;}
				break;
				case 's': if (isValidMove(playerColumn, playerRow+1)) {playerRow++;} else {return;}
				break;
				case 'd': if (isValidMove(playerColumn+1, playerRow)) {playerColumn++;} else {return;}
				break;
				case 'w': if (isValidMove(playerColumn, playerRow-1)) {playerRow--;} else {return;}
				break;
			}
			
			counter++;
		
			repaint();

			timer.start(); 
			moveDone = false;
		}
	}
	
	public void paint(Graphics g)
	{
		for (int i = 0; i < chasercount; i++)
		{
			if (chaser[0][i] == playerColumn && chaser[1][i] == playerRow) 
			{finished = true;}
		}
		
		Graphics2D g2D = (Graphics2D) g;
		super.paint(g2D);
		
		g2D.setPaint(Color.DARK_GRAY);
		
		for (int row = 0; row < GRID_SIZE; row++)
		{
			for (int column = 0; column < GRID_SIZE; column++)
			{	
				if (board[row][column] != 0) 
				{g2D.fillRect(column * BOX_SIZE, row * BOX_SIZE, BOX_SIZE, BOX_SIZE);}
				
				g2D.drawRect(column * BOX_SIZE, row * BOX_SIZE, BOX_SIZE, BOX_SIZE); //Grid	
			}
		}
		
		g2D.setPaint(new Color (0, 140, 250));
		g2D.fillRect(playerColumn * BOX_SIZE, playerRow * BOX_SIZE, BOX_SIZE, BOX_SIZE);
		
		if (!finished) {g2D.setPaint(halfred.brighter());}
		else {g2D.setPaint(new Color (50, 250, 50));}
		
		for (int i = 0; i < chasercount; i++)
		{g2D.fillRect(chaser[0][i] * BOX_SIZE, chaser[1][i] * BOX_SIZE, BOX_SIZE, BOX_SIZE);}
		
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		if (!finished)
		{
			g2D.setPaint(Color.DARK_GRAY);
			g2D.setFont(new Font (null, Font.PLAIN, PANEL_SIZE/40));
			if (counter >= 100) {counterpush1 = 350;}
			else if (counter >= 10) {counterpush1 = 110;}
			else {counterpush1 = 65;}
			g2D.drawString
			(""+counter, playerColumn * BOX_SIZE + (PANEL_SIZE/counterpush1), playerRow * BOX_SIZE + (PANEL_SIZE/30));
		}
		
		else 
		{
			g2D.setPaint(halfred);
			g2D.setFont(new Font (null, Font.PLAIN, PANEL_SIZE/2));
			if (counter >= 100) {counterpush2 = 11.6;}
			else if (counter >= 10) {counterpush2 = 4.7;}
			else {counterpush2 = 2.7;}
			g2D.drawString
			(""+counter, (int)(GRID_SIZE/counterpush2 * BOX_SIZE), (int)(GRID_SIZE/1.5 * BOX_SIZE));
		}
		
		
	}
	
	public void changeSize(int c) 
	{
		PANEL_SIZE+=c; BOX_SIZE = (PANEL_SIZE /GRID_SIZE)+1; 
		repaint(); 
		this.setPreferredSize(new Dimension(PANEL_SIZE,PANEL_SIZE));
	}
}

public class PathfindPanel extends JPanel
{
	private static final long serialVersionUID = 656163063666531513L;
	
	public void changeSize(int amount)
	{}
	
	public void wipeBoard()
	{}
	
	public void start(boolean fullRestart)
	{}
	
	public void movePlayer(char key)
	{}
	
	public int[][] getBoard()
	{return null;}
	
	public int[][] getChaser()
	{return null;}
	
	public int[] getSizes()
	{return null;}
	
	public int[] getPlayer()
	{return null;}
	
	public void load(int[]sizes, int[][] board, int[][] chaser, int[] player)
	{}

}

public class PathfindTrail extends PathfindPanel implements ActionListener
{
	private static final long serialVersionUID = 3986470365499168687L;
	
	static int GRID_SIZE = 22;
	int PANEL_SIZE = 410;
	int BOX_SIZE = (PANEL_SIZE /GRID_SIZE)+1;
	
	int[][] board = new int[GRID_SIZE][GRID_SIZE];
	
	int chasercount = 3;
	int[][] chaser = new int[2][chasercount];
	
	int[] randommove = new int [chasercount]; 
	//how many times has the chaser needed to make a random move because something is blocking the direct path

	int playerColumn;
	int playerRow;
	
	int[][] trail = new int[2][GRID_SIZE*GRID_SIZE];
	
	int counter = 0;	//how many player moves
	
	Timer timer;
	
	Random random = new Random();
	
	Color halfred = new Color(200,50,50);
	
	boolean moveDone = true; 	//is the chaser move done? -> Player move ready
	
	boolean finished = false;	//have the chasers caught the player?
	
	//numbers used to push the counter coordinates on the two axis depending on the digit count
	int counterpush1;
	double counterpush2;
	
	
	PathfindTrail()
	{
		this.setPreferredSize(new Dimension (PANEL_SIZE,PANEL_SIZE));
		
		ClickListener clickListener = new ClickListener();
		this.addMouseListener(clickListener);
		
		timer = new Timer(100, this);
		
		start(true);
	}
	
	public int[][] getBoard()
	{return board;}
	
	public int[][] getChaser()
	{return chaser;}
	
	public int[] getSizes()
	{int[] sizes = {chasercount, GRID_SIZE}; return sizes;}
	
	public int[] getPlayer()
	{int[] player = {playerColumn, playerRow, counter}; return player;}
	
	public void load(int[]sizes, int[][]loadboard, int[][]loadchaser, int[] loadplayer)
	{
		GRID_SIZE = sizes[1];
		chasercount = sizes[0];
		
		for (int i = 0; i < GRID_SIZE; i++)
			{
				for (int j = 0; j < GRID_SIZE; j++)
				{
					board[i][j] = loadboard[i][j];
				}
			}
		
		for (int i = 0; i < chasercount; i++)
		{
			chaser[0][i] = loadchaser[0][i];
			chaser[1][i] = loadchaser[1][i];
			randommove[i] = 0;
		}
		
		finished = false;
		counter = loadplayer[2];
		
		playerColumn = loadplayer[0];;
		playerRow = loadplayer[1];
		
		BOX_SIZE = (PANEL_SIZE /GRID_SIZE)+1;
		repaint();
	}
	
	public void start(boolean fullReset)
	{
		if (fullReset)
		{
			for (int i = 0; i < GRID_SIZE; i++)
			{
				for (int j = 0; j < GRID_SIZE; j++)
				{
					if (random.nextInt(4) == 1) {board[i][j] = 1;}
					else {board[i][j] = 0;}	
				}
			}
		}
		
		for (int i = 0; i < chasercount; i++)
		{
			chaser[0][i] = random.nextInt(GRID_SIZE);
			chaser[1][i] = random.nextInt(GRID_SIZE);
			randommove[i] = 0;
		}
		
		for (int i = 0; i < GRID_SIZE*GRID_SIZE; i++)
		{
			trail[0][i] = -1;
			trail[1][i] = -1;
		}
		
		finished = false;
		counter = 0;
		
		playerColumn = random.nextInt(GRID_SIZE);
		playerRow = random.nextInt(GRID_SIZE);
		
		repaint();
	}
	
	private class ClickListener extends MouseAdapter
	{
	    public void mousePressed(MouseEvent e) 
	    {  
	    	if (!finished)
	    	{
	    		int column = e.getX() / BOX_SIZE;
	    		int row = e.getY() / BOX_SIZE;
	    	
	    		if (e.isShiftDown())
	    		{
	    			if (board[row][column] == 0) {board[row][column] = 1;}
	    			else {board[row][column] = 0;}
	    		}
	    	
	    		else
	    		{
	    			playerColumn = column;
	    			playerRow = row;
	    		}
	    	
	    		repaint();
	    	}
	    }
	}
		
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		timer.stop();
		oneMove();
	}
	
	public void oneMove()
	{	
		for (int i = 0; i < chasercount; i++)
		{nextStep(i);}
		
		repaint();
		
		moveDone = true;
	}
	
	public void nextStep(int chasernum)
	{
			
		if (randommove[chasernum] == 0)
		{
			if (random.nextBoolean())
			{	
				if (directionMove(0,chasernum)) {return;}	
				if (directionMove(1,chasernum)) {return;}	
			}
			
			else 
			{
				if (directionMove(1,chasernum)) {return;}	
				if (directionMove(0,chasernum)) {return;}	
			}
		}
		
		randommove[chasernum]++;
		
		if (randommove[chasernum] < 3)
		{
			switch (random.nextInt(4))
			{
				case 0: if (isValidMove(chaser[0][chasernum]+1, chaser[1][chasernum]))  {chaser[0][chasernum]++; break;}
				case 1: if (isValidMove(chaser[0][chasernum]-1, chaser[1][chasernum])) {chaser[0][chasernum]--; break;}
				case 2: if (isValidMove(chaser[0][chasernum], chaser[1][chasernum]+1))  {chaser[1][chasernum]++; break;}
				case 3: if (isValidMove(chaser[0][chasernum], chaser[1][chasernum]-1)) {chaser[1][chasernum]--; break;}
			}
		}
	
		else {randommove[chasernum] = 0;}
	}
	
	public boolean isValidMove(int column, int row)
	{
		if (column > -1 && column < GRID_SIZE &&  row > -1 && row < GRID_SIZE)
		{return (board[row][column] == 0);}
		return false;
	}
	
	public boolean directionMove(int direction, int chasernum)
	{
		int[] rowcol = {playerColumn, playerRow};
		
		if (rowcol[direction] > chaser[direction][chasernum])
		{
			if (board[chaser[1][chasernum] + 1 + (direction-1)] [chaser[0][chasernum]+ Math.abs(direction-1)] == 0) 
			{chaser[direction][chasernum]++; return true;}
		}
		
		if (rowcol[direction] < chaser[direction][chasernum])
		{
			if (board[chaser[1][chasernum]- (1 + (direction-1))] [chaser[0][chasernum]- Math.abs(direction-1)] == 0) 
			{chaser[direction][chasernum]--; return true;}
		}
		
		return false;
	}
	
	public void wipeBoard()
	{
		if (!finished)
		{
			for (int i = 0; i < GRID_SIZE; i++)
			{
				for (int j = 0; j < GRID_SIZE; j++)
				{board[i][j] = 0;}
			}
		
			repaint();
		}
	}
	
	public void movePlayer(char key)
	{
		trail[0][counter] = playerColumn;
		trail[1][counter] = playerRow;
		
		if (moveDone && !finished)
		{
			switch (key)
			{
				case 'a': if (isValidMove(playerColumn-1, playerRow)) {playerColumn--;} else {return;}
				break;
				case 's': if (isValidMove(playerColumn, playerRow+1)) {playerRow++;} else {return;}
				break;
				case 'd': if (isValidMove(playerColumn+1, playerRow)) {playerColumn++;} else {return;}
				break;
				case 'w': if (isValidMove(playerColumn, playerRow-1)) {playerRow--;} else {return;}
				break;
			}
			
			counter++;
		
			repaint();

			timer.start(); 
			moveDone = false;
		}
	}
	
	public void paint(Graphics g)
	{
		for (int i = 0; i < chasercount; i++)
		{
			if (chaser[0][i] == playerColumn && chaser[1][i] == playerRow) 
			{finished = true;}
		}

		
		Graphics2D g2D = (Graphics2D) g;
		super.paint(g2D);
		
		g2D.setPaint(Color.DARK_GRAY);
		
		for (int row = 0; row < GRID_SIZE; row++)
		{
			for (int column = 0; column < GRID_SIZE; column++)
			{	
				if (board[row][column] != 0) 
				{g2D.fillRect(column * BOX_SIZE, row * BOX_SIZE, BOX_SIZE, BOX_SIZE);}
				
				g2D.drawRect(column * BOX_SIZE, row * BOX_SIZE, BOX_SIZE, BOX_SIZE); //Grid	
			}
		}
		
		g2D.setPaint(new Color (0, 140, 250));
		
		for (int i = 0; i < GRID_SIZE*GRID_SIZE; i++)
		{
			if (trail[0][i] == -1) {break;}
			g2D.fillRect(trail[0][i] * BOX_SIZE, trail[1][i] * BOX_SIZE, BOX_SIZE, BOX_SIZE); 
			if (trail[0][i] == playerColumn && trail[1][i] == playerRow) {finished = true;}
		}
		
		g2D.fillRect(playerColumn * BOX_SIZE, playerRow * BOX_SIZE, BOX_SIZE, BOX_SIZE);
		
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		if (!finished) {g2D.setPaint(halfred.brighter());}
		else {g2D.setPaint(new Color (50, 250, 50));}
		
		for (int i = 0; i < chasercount; i++)
		{g2D.fillRect(chaser[0][i] * BOX_SIZE, chaser[1][i] * BOX_SIZE, BOX_SIZE, BOX_SIZE);}
		
		
		if (!finished)
		{
			g2D.setPaint(Color.DARK_GRAY);
			g2D.setFont(new Font (null, Font.PLAIN, PANEL_SIZE/40));
			if (counter >= 100) {counterpush1 = 350;}
			else if (counter >= 10) {counterpush1 = 110;}
			else {counterpush1 = 65;}
			g2D.drawString
			(""+counter, playerColumn * BOX_SIZE + (PANEL_SIZE/counterpush1), playerRow * BOX_SIZE + (PANEL_SIZE/30));
		}
		
		else 
		{
			g2D.setPaint(halfred);
			g2D.setFont(new Font (null, Font.PLAIN, PANEL_SIZE/2));
			if (counter >= 100) {counterpush2 = 11.6;}
			else if (counter >= 10) {counterpush2 = 4.7;}
			else {counterpush2 = 2.7;}
			g2D.drawString
			(""+counter, (int)(GRID_SIZE/counterpush2 * BOX_SIZE), (int)(GRID_SIZE/1.5 * BOX_SIZE));
		}
		
		
	}
	
	public void changeSize(int c) 
	{
		PANEL_SIZE+=c; BOX_SIZE = (PANEL_SIZE /GRID_SIZE)+1; 
		repaint(); 
		this.setPreferredSize(new Dimension(PANEL_SIZE,PANEL_SIZE));
	}
}

public class ReflectionDemo extends JFrame
{
private static final long serialVersionUID = 1L;
	
	ReflectionPanel panel;
	
	//RayPanel panel;
	
	ReflectionDemo()
	{

		panel = new ReflectionPanel();
		//panel = new RayPanel();
		
		this.setTitle("Reflection Demo");
		this.setResizable(false);
		this.add(panel);
		this.pack();
		
		this.addKeyListener(new KeyListener() 
		{
			@Override
			public void keyTyped(KeyEvent e) 
			{	
				char key = e.getKeyChar();
				
				if (key == '1') {panel.changeReflectCount(false);}
				else if (key == '2') {panel.changeReflectCount(true);}
				else if (key == '3') {panel.changeRotationSpeed(false);}
				else if (key == '4') {panel.changeRotationSpeed(true);}
				
				else if (key == '+') {panel.changeSize(10); pack();}
				else if (key == '-') {panel.changeSize(-10); pack();}
				
			}
			@Override
			public void keyPressed(KeyEvent e) {}
			@Override
			public void keyReleased(KeyEvent e) {}		
		});

		this.setVisible(true);
		
	}
	
	public void start(WindowEventHandler eventHandler)
	{
		this.addWindowListener(eventHandler);
	}
}


public class ReflectionPanel extends JPanel implements MouseWheelListener 
{
	private static final long serialVersionUID = 2L;
	
	int[] rayOrigin = {300,300};
	
	int reflectcount = 1;
	double rotationSpeed = 0.01;
	
	int[][] reflectOrigin = new int[2][50];
	
	int panelSize = 400;
	int expectedLength = panelSize*10;
	
	double[][] rayVector = new double[2][50];
	
	int[][] rayEnd = new int[2][50];
	
	double rayLength = 0;
	
	double angle = 0;
	
	Point Corner;
	Point prevPt;
	
	int R = 255;
	int G = 0;
	int B = 0;
	
	ReflectionPanel()
	{
		
		Corner = new Point(300,300);
		ClickListener clickListener = new ClickListener();
		DragListener dragListener = new DragListener();
		ReleaseListener releaseListener = new ReleaseListener();
		
		this.addMouseWheelListener(this);
		          
		this.addMouseListener(clickListener);
		this.addMouseMotionListener(dragListener);
		this.addMouseListener(releaseListener);
		
		this.setPreferredSize(new Dimension(panelSize,panelSize));
		this.setLayout(null);
		
		rayVector[0][0] = expectedLength;
		rayVector[1][0] = 0;
		
		this.rayCalculation(true);
		
		
		
	}
	
	public void changeReflectCount(boolean increase)
	{
		if (increase) {if (reflectcount < 33) {reflectcount++;}}
			
		else {reflectcount--;}
			
		this.rayCalculation(false);
		repaint();	
		
		if (reflectcount < 1) {reflectcount = 1;}
	}
	
	public void changeRotationSpeed(boolean increase)
	{
		if (increase) {rotationSpeed*=2;}
		else {rotationSpeed/=2;}
	}
	
	public void rayCalculation(boolean angleChange)
	{
		
			if (angleChange)
			{
				rayVector[0][0] = rayVector[0][0]*Math.cos(angle)-rayVector[1][0]*Math.sin(angle);
				rayVector[1][0] = rayVector[0][0]*Math.sin(angle)+rayVector[1][0]*Math.cos(angle);
		
			}
			
			rayEnd[0][0] = (int)(rayOrigin[0] + rayVector[0][0]);
			rayEnd[1][0] = (int)(rayOrigin[1] + rayVector[1][0]);
		
		rayLength = Math.sqrt(Math.pow(rayVector[0][0], 2) + Math.pow(rayVector[1][0], 2));
		
		if (rayLength < expectedLength-10)
		{
			double i = 1;
			while (rayLength < expectedLength)
			{
				i+=0.005;
				rayVector[0][0]*=i;
				rayVector[1][0]*=i;
				rayLength = Math.sqrt(Math.pow(rayVector[0][0], 2) + Math.pow(rayVector[1][0], 2));
			}
			
		}
		
		for (int i = 0; i < reflectcount-1; i++)
		{makeRay(i);}
		
	}
	
	public void makeRay(int raynum)
	{
		double i = 0;
		double j = 0;
		
		if (raynum == 0)
		{
			if (rayVector[0][raynum] > 0) {i = hitRight(raynum, rayOrigin[0]);}
			else {i = hitLeft(raynum, rayOrigin[0]);}
			
			if (rayVector[1][raynum] < 0) {j = hitTop(raynum, rayOrigin[1]);}
			else {j = hitBottom(raynum, rayOrigin[1]);}
		}
		
		else
		{
			if (rayVector[0][raynum] > 0) {i = hitRight(raynum, reflectOrigin[0][raynum-1]);}
			else {i = hitLeft(raynum, reflectOrigin[0][raynum-1]);}
		
			if (rayVector[1][raynum] < 0) {j = hitTop(raynum, reflectOrigin[1][raynum-1]);}
			else {j = hitBottom(raynum, reflectOrigin[1][raynum-1]);}
		}

		double divider = 0;
		
		if (i > j) {divider = i;}
		else {divider = j;}
		
		
		if (raynum == 0)
		{
			reflectOrigin[0][raynum] = (int)(rayOrigin[0] + (rayVector[0][raynum] / divider));
			reflectOrigin[1][raynum] = (int)(rayOrigin[1] + (rayVector[1][raynum] / divider));
		}
		
		else 
		{
			reflectOrigin[0][raynum] = (int)(reflectOrigin[0][raynum-1] + (rayVector[0][raynum] / divider));
			reflectOrigin[1][raynum] = (int)(reflectOrigin[1][raynum-1] + (rayVector[1][raynum] / divider));
		}
		
		rayVector[0][raynum+1] = rayVector[0][raynum];
		rayVector[1][raynum+1] = rayVector[1][raynum];
		
		if (i > j)
		{
			rayVector[0][raynum+1] = -rayVector[0][raynum+1];
		}
		
		else
		{
			rayVector[1][raynum+1] = -rayVector[1][raynum+1];
		}
		
		rayEnd[0][raynum+1] = (int)(reflectOrigin[0][raynum] + rayVector[0][raynum+1]);
		rayEnd[1][raynum+1] = (int)(reflectOrigin[1][raynum] + rayVector[1][raynum+1]);
		
	}
	
	public double hitBottom(int raynum, int originY)
	{
		double i = 2.0;
		
		while (i < 500)
		{
			i+=0.01;
			
			int test = (int)(originY + (rayVector[1][raynum] / i));
					
			if (test == panelSize) {return i;}
			
		}
		
		i = 0; return i;
	}
	
	public double hitRight(int raynum, int originX)
	{
		double i = 2.0;
		
		while (i < 500)
		{
			i+=0.01;
			
			int test = (int)(originX + (rayVector[0][raynum] / i));
					
			if (test == panelSize) {return i;}
			
		}
		
		i = 0; return i;
	}
	
	public double hitLeft(int raynum, int originX)
	{
		double i = 2.0;
		
		while (i < 500)
		{
			i+=0.01;
			
			int test = (int)(originX + (rayVector[0][raynum] / i));
					
			if (test == 0) {return i;}
			
		}
		
		i = 0; return i;
	}
	
	public double hitTop(int raynum, int originY)
	{
		double i = 2.0;
		
		while (i < 500)
		{
			i+=0.01;
			
			int test = (int)(originY + (rayVector[1][raynum] / i));
					
			if (test == 0) {return i;}
			
		}
		
		i = 0; return i;
	}
	
	public void paint (Graphics g) 
	{
		
		Graphics2D g2D = (Graphics2D) g;
		
		super.paint(g2D);
		
		g2D.fillRect(0, 0, panelSize, panelSize);
			
		g2D.setStroke(new BasicStroke(3));
		g2D.setPaint(new Color(100,250,0));
		
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g2D.drawLine(rayOrigin[0], rayOrigin[1], rayEnd[0][0], rayEnd[1][0]);
		
		for (int i = 0; i < reflectcount-1; i++)
		{
			
			//G= i*8;
			//g2D.setPaint(new Color(R,G,B));
			B= i*8;
			g2D.setPaint(new Color(100,250,B));
			
			g2D.drawLine(reflectOrigin[0][i], reflectOrigin[1][i], rayEnd[0][i+1], rayEnd[1][i+1]);
		}
		
		
		
		
		g2D.setPaint(Color.GRAY);
		g2D.fillOval(rayOrigin[0]-8, rayOrigin[1]-8, 15, 15);
		
		
		
		
	}
	
	public void changeSize(int c) 
	{
		panelSize+=c;
		repaint();
		this.setPreferredSize(new Dimension(panelSize,panelSize));
	}

	public void mouseWheelMoved(MouseWheelEvent e) 
	{
		if (e.getWheelRotation() > 0) 
	    {angle = rotationSpeed;} 
	    else  
	    {angle = -rotationSpeed;}
	    
	    rayCalculation(true);
	    repaint();
	}
	
	private class ClickListener extends MouseAdapter
	{
	    public void mousePressed(MouseEvent e) 
	    {  
	    		
	    		prevPt = e.getPoint(); 
	    		Point currentPt = e.getPoint();    
                Corner.translate(
                        (int)(currentPt.getX() - prevPt.getX()),
                        (int)(currentPt.getY() - prevPt.getY())
                );     
                prevPt = currentPt;
               rayOrigin[0] = (int)currentPt.getX();
               rayOrigin[1] = (int)currentPt.getY();
               rayCalculation(false);
               repaint();
	    }
	}
	
	private class DragListener extends MouseMotionAdapter
	{
	    public void mouseDragged(MouseEvent e) 
	    { 
	    			
	    			Point currentPt = e.getPoint();    
	                Corner.translate(
	                        (int)(currentPt.getX() - prevPt.getX()),
	                        (int)(currentPt.getY() - prevPt.getY())
	                );     
	                prevPt = currentPt;
	               rayOrigin[0] = (int)currentPt.getX();
	               rayOrigin[1] = (int)currentPt.getY();
	               rayCalculation(false);
	               repaint();
	    }
	}
	
	
	private class ReleaseListener extends MouseAdapter
	{
	    public void mouseReleased(MouseEvent e) {}
	}
}

public class SelectionFrame 
{
	String gameName;
	
	public void start(String gameName, WindowEventHandler eventHandler)
	{
		this.gameName = gameName;
		
		JFrame selection = new JFrame("Selection");
		//selection.setIconImage(MainMenu.img.getImage());
		JLabel headline = new JLabel("PICK A GAMEMODE BY PRESSING THE CORRESPONDING KEY");
		headline.setForeground(new Color(220,220,240));
		JLabel mode1 = new JLabel();
		mode1.setForeground(new Color(220,220,240));
		JLabel mode2 = new JLabel();
		mode2.setForeground(new Color(220,220,240));
		JLabel mode3 = new JLabel();
		mode3.setForeground(new Color(220,220,240));
		selection.getContentPane().setBackground(new Color(50,50,70));
		selection.setLayout(null);
		headline.setBounds(20,5,500,40);
		mode1.setBounds(20,40,500,40);
		mode2.setBounds(20,70,500,40);
		mode3.setBounds(20,100,500,40);
		selection.add(headline);
		selection.add(mode1);
		selection.add(mode2);
		selection.add(mode3);
		selection.setSize(500, 200);
		selection.setVisible(true);
		
		switch(gameName)
		{
			case "Pathfind":  mode1.setText("1 - NORMAL"); mode2.setText("2 - TRAIL"); mode3.setText("3 - DEMO");
			break;
			case "Sierpinski": mode1.setText("1 - SLOW"); mode2.setText("2 - NORMAL"); mode3.setText("3 - FAST");
			break;
			
		}
		
		selection.addKeyListener(new KeyListener() 
		{
			@Override
			public void keyTyped(KeyEvent e) 
			{	
				char key = e.getKeyChar();
				
				int mode = key - '0'; //char to int
				
				switch (gameName)
				{
					case "Pathfind": new Pathfind(eventHandler, mode); selection.dispatchEvent(new WindowEvent(selection, WindowEvent.WINDOW_CLOSING));
					break;
					case "Sierpinski": new Sierpinski(eventHandler, mode); selection.dispatchEvent(new WindowEvent(selection, WindowEvent.WINDOW_CLOSING));
					break;
				}
					
			}
			@Override
			public void keyPressed(KeyEvent e) {}
			@Override
			public void keyReleased(KeyEvent e) {}		
		});
	}
}

public class Sierpinski{
	
	SierpinskiSlow slowPanel;
	SierpinskiFast fastPanel;
	
	int gamemode = 0;
	
	WindowEventHandler eventHandler;
	
	Sierpinski(WindowEventHandler eventHandler, int gamemode)
	{
		this.gamemode = gamemode;
		this.eventHandler = eventHandler;
		
		start();
	}	
	
	public void start()
	{
		JFrame frame = new JFrame("Sierpinski");
		
		frame.setResizable(false);
		frame.addWindowListener(eventHandler);
		
		switch (gamemode)
		{
		case 1: slowPanel = new SierpinskiSlow(true); frame.add(slowPanel); break;
		case 2: slowPanel = new SierpinskiSlow(false); frame.add(slowPanel); break;
		case 3: fastPanel = new SierpinskiFast(); frame.add(fastPanel); break;
		}
		
		frame.pack();

		frame.setVisible(true);
	}
}
public class SierpinskiFast extends JPanel{
	private static final long serialVersionUID = 9082922097976866954L;
	
	int[] P = new int[2];
	int[] v = new int[2];
	int[] nextP = new int[2];
	
	int[][] points = new int[2][100000];
	int pointIndex = 0;
	
	Point ACorner;
	Point BCorner;
	Point CCorner;
	
	Point prevPt;
	int dragwhich;
	
	Random random;
	
	boolean moving = false;
	
	Thread t1;
	
	SierpinskiFast(){
		
		random = new Random();
		
		ClickListener clickListener = new ClickListener();
		DragListener dragListener = new DragListener();
		ReleaseListener releaseListener = new ReleaseListener();
		this.addMouseListener(releaseListener);
		this.addMouseListener(clickListener);
		this.addMouseMotionListener(dragListener);
		
		this.setPreferredSize(new Dimension(600,600));
		this.setLayout(null);
		
		ACorner = new Point(10,550);
		BCorner = new Point(300,10);
		CCorner = new Point(590,550);
			
		P[0] = 550;
		P[1] = 550;
		
		t1 = new Thread(new Runnable() 
		{
		    @Override
		    public void run() 
		    {
		    	while (true) 
		    	{
		    		rollDice();
		    		try {Thread.sleep(1);} catch (InterruptedException e) {e.printStackTrace();}
		    	}
		    }	 		    		
		});
		
		t1.start();
	
	}
	
	
	public void paint(Graphics g) {
		
		Graphics2D g2D = (Graphics2D) g;
		
		super.paint(g2D);
		
		g2D.fillRect((int)ACorner.getX(),(int)ACorner.getY(), 5, 5);
		g2D.fillRect((int)BCorner.getX(),(int)BCorner.getY(), 5, 5);
		g2D.fillRect((int)CCorner.getX(),(int)CCorner.getY(), 5, 5);
		
		g2D.fillRect(P[0], P[1], 3, 3);
		
		for (int i = 0; i < pointIndex; i++)
		{
			g2D.fillRect(points[0][i], points[1][i], 1, 1);
		}
	}
	
	public void rollDice() 
	{
	
	if (points == null) {return;}
		
	switch (random.nextInt(3)) 
	{
	case 0: nextP[0] = (int)ACorner.getX(); nextP[1] = (int)ACorner.getY();
	break;
	case 1: nextP[0] = (int)BCorner.getX(); nextP[1] = (int)BCorner.getY();
	break;
	case 2: nextP[0] = (int)CCorner.getX(); nextP[1] = (int)CCorner.getY();
	break;
	}	
	
	//Vektor
	v[0] = (int)(nextP[0] - P[0])/2;
	v[1] = (int)(nextP[1] - P[1])/2;
	
	//New P Position
	P[0]+= v[0];
	P[1]+= v[1];
	
	points[0][pointIndex] = P[0];
	points[1][pointIndex] = P[1];
	
	pointIndex++;
	
	repaint();
		
	}
	
	private class ClickListener extends MouseAdapter{
	    public void mousePressed(MouseEvent e) {  
	   
	    	prevPt = e.getPoint(); 
	           if ((e.getPoint().getX() > ACorner.getX()) && 
	              (e.getPoint().getX() < (ACorner.getX() + 50)) &&
	              (e.getPoint().getY() > ACorner.getY()) &&
	              (e.getPoint().getY() < (ACorner.getY() + 50))){dragwhich = 0;}
	           else if ((e.getPoint().getX() > BCorner.getX()) && 
	 	              (e.getPoint().getX() < (BCorner.getX() + 50)) &&
		              (e.getPoint().getY() > BCorner.getY()) &&
		              (e.getPoint().getY() < (BCorner.getY() + 50))){dragwhich = 1;}
	           else if ((e.getPoint().getX() > CCorner.getX()) && 
	 	              (e.getPoint().getX() < (CCorner.getX() + 50)) &&
		              (e.getPoint().getY() > CCorner.getY()) &&
		              (e.getPoint().getY() < (CCorner.getY() + 50))){dragwhich = 2;}
	    }
	}
	
	private class DragListener extends MouseMotionAdapter{
	    public void mouseDragged(MouseEvent e) { 
	           	
	    	if (e.isShiftDown()) {	
			
	    	points = null;
	    	System.gc();
	    	points = new int[2][100000];
	    		
	    	moving = true;
	    	
	    	Point currentPt = e.getPoint(); 

	    	if(dragwhich == 0){	
	        ACorner.translate((int)(currentPt.getX() - prevPt.getX()), (int)(currentPt.getY() - prevPt.getY()));          
	    	}
	    	else if (dragwhich == 1){	
		    BCorner.translate((int)(currentPt.getX() - prevPt.getX()), (int)(currentPt.getY() - prevPt.getY()));          
		    }
	    	else if (dragwhich == 2){	
			CCorner.translate((int)(currentPt.getX() - prevPt.getX()), (int)(currentPt.getY() - prevPt.getY()));          
			}
	                
	                prevPt = currentPt;
	                
	                repaint();
	                
	    			}
	            }
	    }
	
	private class ReleaseListener extends MouseAdapter
	{
	    public void mouseReleased(MouseEvent e) 
	    {moving = false;}
	}
}
 
public class SierpinskiSlow extends JPanel implements ActionListener{
	private static final long serialVersionUID = 9082922097976866954L;
	
	int[] P = new int[2];
	int[] v = new int[2];
	int[] nextP = new int[2];
	int[] prevP = new int [2];
	
	int[][] allP = new int [2][10000];
	int numP = 0;
	
	
	Point ACorner;
	Point BCorner;
	Point CCorner;
	
	Point prevPt;
	int dragwhich;
	
	Random random;
	
	boolean moving = false;
	
	Thread t1;
	
	static Timer timer1;
	static Timer timer2;
	
	SierpinskiSlow(boolean slowMode){
		
		random = new Random();
		
		P[0] = 550;
		P[1] = 550;
		
		prevP[0] = P[0];
		prevP[1] = P[1];
		nextP[0] = P[0];
		nextP[1] = P[1];
		
		ClickListener clickListener = new ClickListener();
		DragListener dragListener = new DragListener();
		ReleaseListener releaseListener = new ReleaseListener();
		this.addMouseListener(releaseListener);
		this.addMouseListener(clickListener);
		this.addMouseMotionListener(dragListener);
		
		this.setPreferredSize(new Dimension(400,400));
		this.setLayout(null);
		
		ACorner = new Point(10,350);
		BCorner = new Point(200,10);
		CCorner = new Point(390,350);
			
		if (slowMode)
		{
			timer1 = new Timer(300, this);
			timer1.start();
		}
		
		else
		{
			timer2 = new Timer(50, this);
			timer2.start();
		}
	
	}
	
	public static void stop()
	{if (timer1 != null) {timer1.stop();} if (timer2 != null) {timer2.stop();}}
	
	
	public void paint(Graphics g) {
		
		Graphics2D g2D = (Graphics2D) g;
	
		if (moving) {super.paint(g);}
		
		super.paint(g);
		
		g2D.fillRect((int)ACorner.getX(),(int)ACorner.getY(), 5, 5);
		g2D.fillRect((int)BCorner.getX(),(int)BCorner.getY(), 5, 5);
		g2D.fillRect((int)CCorner.getX(),(int)CCorner.getY(), 5, 5);
		
		g2D.setStroke(new BasicStroke(3));
		
		g2D.setPaint(Color.cyan);
		g2D.drawLine(prevP[0], prevP[1], P[0], P[1]);
		g2D.setPaint(Color.red);
		g2D.drawLine(P[0], P[1], nextP[0], nextP[1]);
		
		
		
		
		for (int i = 0; i < numP; i++) 
		{
			g2D.setPaint(Color.DARK_GRAY);
			g2D.fillRect(allP[0][i]-2, allP[1][i]-2, 5, 5); 
			g2D.setPaint(Color.GRAY);
			g2D.fillRect(allP[0][i+1]-2, allP[1][i+1]-2, 5, 5);
		}
		
	}
	
	public void rollDice() 
	{
	
	switch (random.nextInt(3)) 
	{
	case 0: nextP[0] = (int)ACorner.getX(); nextP[1] = (int)ACorner.getY();
	break;
	case 1: nextP[0] = (int)BCorner.getX(); nextP[1] = (int)BCorner.getY();
	break;
	case 2: nextP[0] = (int)CCorner.getX(); nextP[1] = (int)CCorner.getY();
	break;
	}	
	
	//Vektor
	v[0] = (int)(nextP[0] - P[0])/2;
	v[1] = (int)(nextP[1] - P[1])/2;
	
	prevP[0] = P[0];
	prevP[1] = P[1];
	
	//New P Position
	P[0] += v[0];
	P[1] += v[1];
	
	numP++;
	allP[0][numP]=P[0];
	allP[1][numP]=P[1];
	
	repaint();
		
	}
	
	private class ClickListener extends MouseAdapter{
	    public void mousePressed(MouseEvent e) {  
	    	
	    	prevPt = e.getPoint(); 
	           if ((e.getPoint().getX() > ACorner.getX()) && 
	              (e.getPoint().getX() < (ACorner.getX() + 50)) &&
	              (e.getPoint().getY() > ACorner.getY()) &&
	              (e.getPoint().getY() < (ACorner.getY() + 50))){dragwhich = 0;}
	           else if ((e.getPoint().getX() > BCorner.getX()) && 
	 	              (e.getPoint().getX() < (BCorner.getX() + 50)) &&
		              (e.getPoint().getY() > BCorner.getY()) &&
		              (e.getPoint().getY() < (BCorner.getY() + 50))){dragwhich = 1;}
	           else if ((e.getPoint().getX() > CCorner.getX()) && 
	 	              (e.getPoint().getX() < (CCorner.getX() + 50)) &&
		              (e.getPoint().getY() > CCorner.getY()) &&
		              (e.getPoint().getY() < (CCorner.getY() + 50))){dragwhich = 2;}
	    }
	}
	
	private class DragListener extends MouseMotionAdapter{
	    public void mouseDragged(MouseEvent e) { 
	           	
	    	if (e.isShiftDown()) {	
			
	    	moving = true;
	    	
	    	Point currentPt = e.getPoint(); 

	    	if(dragwhich == 0){	
	        ACorner.translate((int)(currentPt.getX() - prevPt.getX()), (int)(currentPt.getY() - prevPt.getY()));          
	    	}
	    	else if (dragwhich == 1){	
		    BCorner.translate((int)(currentPt.getX() - prevPt.getX()), (int)(currentPt.getY() - prevPt.getY()));          
		    }
	    	else if (dragwhich == 2){	
			CCorner.translate((int)(currentPt.getX() - prevPt.getX()), (int)(currentPt.getY() - prevPt.getY()));          
			}
	                
	                prevPt = currentPt;
	                
	                repaint();
	                
	    			}
	            }
	    }
	
	private class ReleaseListener extends MouseAdapter
	{
	    public void mouseReleased(MouseEvent e) 
	    {  	
	    moving = false;
	    }
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
	
	rollDice();
		
	}
}

public class Sudoku 
{
	public void start(WindowEventHandler eventHandler) 
	{
		
		JFrame frame = new JFrame("Sudoku");
		frame.setResizable(false);
		
		SudokuPanel panel = new SudokuPanel();
	
		frame.addWindowListener(eventHandler);
		frame.addKeyListener(new KeyListener() 
		{
			@Override
			public void keyTyped(KeyEvent e) 
			{	
				if (e.getKeyChar() == 's') {panel.sudoku();}
				else if (e.getKeyChar() == '+') {panel.changeSize(10); frame.pack();}
				else if (e.getKeyChar() == '-') {panel.changeSize(-10); frame.pack();}
				else if (e.getKeyChar() == 'r') {panel.reset();}
				
				else 
				{
						int x =  e.getKeyChar() - '0'; // gives the int value
						try{panel.changeValue(x);} catch (Exception ex) {}
				}
			}
			@Override
			public void keyPressed(KeyEvent e) {}
			@Override
			public void keyReleased(KeyEvent e) {}		
		});
		
		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
	}

}

public class SudokuPanel extends JPanel
{
	private static final long serialVersionUID = 3986470365499168687L;
	
	static final int GRID_SIZE = 9;
	int PANEL_SIZE = 400;
	int BOX_SIZE = (PANEL_SIZE /GRID_SIZE)+1;
	
	
	int[][] board = new int [GRID_SIZE][GRID_SIZE];			//row, column
	int oldboard[][] = new int [GRID_SIZE][GRID_SIZE];		//state of board before "solve" method
		
	/*	column	 0 1 2 3 4 5 6 7 8	  
	 								   row
				{7,0,2,0,5,0,6,0,0},  	0
				{0,0,0,0,0,3,0,0,0},	1
				{1,0,0,0,0,9,5,0,0},	2
				{8,0,0,0,0,0,0,9,0},	3
				{0,4,3,0,0,0,7,5,0},	4
				{0,9,0,0,0,0,0,0,8},	5
				{0,0,9,7,0,0,0,0,5},	6
				{0,0,0,2,0,0,0,0,0},	7
				{0,0,7,0,4,0,2,0,3},	8 */
		
	int activeColumn = -1;						//column that has been clicked on - by default outside of screen
	int activeRow = -1;
	
	long startTime;								//used to check if "solve" is taking too long
	
	SudokuPanel()
	{
		this.setPreferredSize(new Dimension (PANEL_SIZE,PANEL_SIZE));	
		
		ClickListener clickListener = new ClickListener();
		this.addMouseListener(clickListener);
	}
	
	
	private class ClickListener extends MouseAdapter
	{
	    public void mousePressed(MouseEvent e) 
	    {  
	    	activeColumn = e.getX() / BOX_SIZE;
	    	activeRow = e.getY() / BOX_SIZE;
	    	repaint();
	    }
	}
	
	public void reset()
	{
		for (int i = 0; i < GRID_SIZE; i++)
		{for (int j = 0; j < GRID_SIZE; j++){board[i][j] = 0; oldboard[i][j] = 0;}}
		repaint();
	}
	
	public void changeValue(int x)
	{
		if (x >= 0 && x <=GRID_SIZE)
		{
			board[activeRow][activeColumn] = x;
			oldboard[activeRow][activeColumn] = x;
		}
		
		activeColumn = -1;
    	activeRow = -1;
    	repaint();
	}
	
	public void changeSize(int c) 
	{
		PANEL_SIZE+=c; BOX_SIZE = (PANEL_SIZE /GRID_SIZE)+1; 
		repaint(); 
		this.setPreferredSize(new Dimension(PANEL_SIZE,PANEL_SIZE));
	}
	
	public void paint(Graphics g)
	{
		Graphics2D g2D = (Graphics2D) g;
		
		super.paint(g2D);
			
		g2D.setFont(new Font ("Arial", Font.PLAIN, PANEL_SIZE / 14));
		
		int columnpush = 0;
		int rowpush = 0; 
		
		for (int row = 0; row < GRID_SIZE; row++)
		{
			for (int column = 0; column < GRID_SIZE; column++)
			{	
				if (oldboard[row][column] != 0) 
				{
					g2D.setPaint(Color.GRAY);
					g2D.fillRect(column * BOX_SIZE, row * BOX_SIZE, BOX_SIZE, BOX_SIZE);
					g2D.setPaint(Color.DARK_GRAY);
				}
				
				g2D.drawRect(column * BOX_SIZE, row * BOX_SIZE, BOX_SIZE, BOX_SIZE); //Grid
		
				g2D.fillRect(activeColumn * BOX_SIZE, activeRow * BOX_SIZE, BOX_SIZE, BOX_SIZE);
				
				g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				
				if (board[row][column] != 0) 
				{
					g2D.drawString(""+board[row][column], (PANEL_SIZE/28)+columnpush, (PANEL_SIZE/13)+2+rowpush);
				}

				g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
				
				columnpush+= BOX_SIZE;
			}
			columnpush = 0;
			rowpush+= BOX_SIZE;
		}
		
		g2D.setStroke(new BasicStroke(PANEL_SIZE/150)); //Large Box Grid
		for (int i = 1; i <= 2; i++) 
		{
		g2D.drawLine(0, BOX_SIZE*3*i, BOX_SIZE*9, BOX_SIZE*3*i); 	//horizontal lines
		g2D.drawLine(BOX_SIZE*3*i, 0, BOX_SIZE*3*i, BOX_SIZE*9);	//vertica lines
		}
		
	}
	
	public void sudoku() {startTime = System.currentTimeMillis(); solve(board); repaint();}
	
	private boolean solve(int[][] board)
	{
		
		if ((System.currentTimeMillis()-startTime)>500) {reset(); return false;}
		
		for (int row = 0; row < GRID_SIZE; row++)
		{
			for (int column = 0; column < GRID_SIZE; column++)
			{
				if (board[row][column] == 0)
				{
					for (int number = 1; number <= GRID_SIZE; number++)
					{
						if (isValidPlacement(board,number,row,column))
						{
							board[row][column] = number; 
							
							if (solve(board)) {return true;} //check if it can solve board with current inputs recursively
							else {board[row][column] = 0;} //backtrack if cannot solve rest of board
						}
					}
					return false; //if current position is not solvable
				}
			}
		}
		return true; //final return
	}
	
	private static boolean isValidPlacement(int[][] board, int number, int row, int column)
	{
		return !isNumberInRow(board,number,row) && !isNumberInColumn(board,number,column)
				&& !isNumberInBox(board, number,row,column);
	}

	
	private static boolean isNumberInRow(int[][] board, int number, int row)
	{
		for (int i = 0; i < GRID_SIZE; i++)
		{if(board[row][i] == number) {return true;}}
		return false;
	}
	
	
	private static boolean isNumberInColumn(int[][] board, int number, int column)
	{
		for (int i = 0; i < GRID_SIZE; i++)
		{if(board[i][column] == number) {return true;}}
		return false;
	}
	
	
	private static boolean isNumberInBox(int[][] board, int number, int row, int column)
	{
		int localBoxRow = row - row % 3;
		int localBoxColumn = column - column % 3;
		
		for (int i = localBoxRow; i < localBoxRow + 3; i++)
		{
			for (int j = localBoxColumn; j < localBoxColumn + 3; j++)
			{if(board[i][j] == number) {return true;}}
		}
		return false;	
	}

}

public class VectorChasers 
{
	public void start(WindowEventHandler eventHandler)
	{
		JFrame frame = new JFrame("Vector Chasers");
		VectorChasersPanel panel = new VectorChasersPanel();
		
		frame.addWindowListener(eventHandler);
		frame.setResizable(false);
		
		frame.addKeyListener(new KeyListener() 
		{

			@Override
			public void keyTyped(KeyEvent e) 
			{
				if (e.getKeyChar() == 'r') {panel.reset();}
			}

			@Override
			public void keyPressed(KeyEvent e) {}
			@Override
			public void keyReleased(KeyEvent e) {}
			
		});
		
		frame.add(panel);
		frame.pack();
		
		frame.setVisible(true);
	}
	
}

public class VectorChasersPanel extends JPanel implements ActionListener{

	private static final long serialVersionUID = 9082922097976866954L;

	Point prevPt;
	boolean dragValid;
	
	int[] prey = {300,300};
	
	int total = 10000;
	int[][] chaser = new int[total][2];
	double[] speed = new double[total];
	
	int size = 20;
	
	static Timer timer;
	Random random;
	
	boolean dragPaint = false;
	
	VectorChasersPanel()
	{
		this.setPreferredSize(new Dimension (900,700));
		
		ClickListener clickListener = new ClickListener();
		DragListener dragListener = new DragListener();
		this.addMouseListener(clickListener);
		this.addMouseMotionListener(dragListener);
		
		random = new Random();
		
		for (int i = 0; i < total-1; i++)
		{
			chaser[i][0] = random.nextInt(1500)+5; chaser[i][1] = random.nextInt(1500)+5;
			speed[i] = random.nextDouble(0.3)+0.1;
		}
		
		timer = new Timer(15, this);
		
	}
	
	public static void stop()
	{timer.stop();}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		move();
	}
	
	public void reset()
	{
		for (int i = 0; i < total-1; i++)
		{
			chaser[i][0] = random.nextInt(1500)+5; chaser[i][1] = random.nextInt(1500)+5;
			speed[i] = random.nextDouble(0.3)+0.1;
		}
	}
	
	
	public void move()
	{
		for (int i = 0; i < total-1; i++)
		{
			int[] v = new int[2];
			v[0] = prey[0]+5 - chaser[i][0];
			v[1] = prey[1]+5 - chaser[i][1];
		
			double movelength = Math.sqrt(Math.pow(v[0], 2)+Math.pow(v[1], 2));
			
			v[0] /= movelength*speed[i]; v[1] /= movelength*speed[i];
		
			chaser[i][0]+= v[0];
			chaser[i][1]+= v[1];
		}
		repaint();
	}
	
	
	public void paint (Graphics g)
	{
		Graphics2D g2D = (Graphics2D) g;
		
		if (!dragPaint) 
		{super.paint(g);}
		
		
		if (!dragPaint) 
		{
			g2D.setPaint(Color.DARK_GRAY);
			g2D.fillRect(0, 0, 900, 700);
			
			g2D.setPaint(new Color (100,200,255));
			
			
			for (int i = 0; i < total-1; i++)
			{
			g2D.fillRect(chaser[i][0], chaser[i][1], 4, 4);
			}
		}
		
		g2D.setPaint(new Color (250,250,250));
		g2D.fillOval(prey[0], prey[1], size, size);
		
		dragPaint = false;
		
	}
	
	
	private class ClickListener extends MouseAdapter
	{
	    public void mousePressed(MouseEvent e) 
	    {  	
	    		prevPt = e.getPoint(); 
	            dragValid = (e.getPoint().getX() > prey[0]) && 
	                    (e.getPoint().getX() < (prey[0] + size)) &&
	                    (e.getPoint().getY() > prey[1]) &&
	                    (e.getPoint().getY() < (prey[1] + size));
	            
	            if (!timer.isRunning() && dragValid) {timer.start();}
	    }
	}
	
	private class DragListener extends MouseMotionAdapter
	{
	    public void mouseDragged(MouseEvent e) 
	    { 
	            
	    	if(dragValid)
	    	{
	    		
	    	Point currentPt = e.getPoint();    
	               
	    	prey[0] = (int)(currentPt.getX()-15);
	    	prey[1] = (int)(currentPt.getY()-15);
	               
	        prevPt = currentPt;
	        
	        dragPaint = true;
	        repaint();
	    	}
	    }
	}
}

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
		//frame.setIconImage(MainMenu.img.getImage());
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

public class PixelCollisionPanel extends JPanel
{
	private static final long serialVersionUID = 9082942097976766954L;
	
	int PANEL_SIZE = 400;
	int PIXEL_SIZE = 3;
	int pixelsPerRow = PANEL_SIZE/PIXEL_SIZE;
	
	int[][] moveableObjects = new int[2][1];
	int objectAmount = 1;
	
	int activeGRIDX = -1;	//column that has been clicked on - by default outside of screen
	int activeGRIDY = -1;
	
	boolean flyMode = false;
	boolean creationMode = false;
	boolean collision = false;
	
	byte[][] grid = new byte[pixelsPerRow][pixelsPerRow]; //actual grid on screen
	
	int creationGridLength = pixelsPerRow/3;	//grid where user can draw their object
		
	PixelCollisionPanel()
	{				
		this.setPreferredSize(new Dimension (PANEL_SIZE,PANEL_SIZE));
		
		moveableObjects[0][0] = PIXEL_SIZE;
		moveableObjects[1][0] = PIXEL_SIZE;
		
		DragListener dragListener = new DragListener();
		this.addMouseMotionListener(dragListener);
	}
	
	public void start()
	{
		pixelsPerRow = PANEL_SIZE/PIXEL_SIZE;
		creationGridLength = pixelsPerRow/3;
		
		//memory management and also fitting the array sizes to the new values
		grid = null;
		System.gc();
		grid = new byte[pixelsPerRow][pixelsPerRow];
		
		repaint();
	}
	
	public void newObject()
	{
		creationMode = true; //for paint method (drawing the rectangle around the space for user input)
		
		//get rid of any filled out pixels within the area for user input (offset the grid by 2 for better look)
		for (int i = 0; i < creationGridLength+2; i++)
		{
			for (int j = 0; j < creationGridLength+2; j++)
			{grid[i+2][j+2] = 0;}
		}
		repaint();
	}
	
	public void saveObject()
	{
		creationMode = false;
		
		//count the amount of pixels
		objectAmount = 0;
		for (int i = 2; i < creationGridLength+4; i++)
		{
			for (int j = 2; j < creationGridLength+4; j++)
			{
				if (grid[i][j] != 0)
				{objectAmount++;}
			}
		}
		
		//reconstruct the array with the right size
		moveableObjects = null;
		System.gc();
		moveableObjects = new int[2][objectAmount];
		
		//project the objects indices into screen coordinates
		int index = 0;
		for (int x = 2; x < creationGridLength+4; x++)
		{
			for (int y = 2; y < creationGridLength+4; y++)
			{
				if (grid[x][y] != 0)
				{
					moveableObjects[0][index] = x*PIXEL_SIZE;
					moveableObjects[1][index] = y*PIXEL_SIZE;
					index++;
				}
			}
		}
		
		//now get rid of the drawn pixels
		for (int i = 2; i < creationGridLength+4; i++)
		{
			for (int j = 2; j < creationGridLength+4; j++)
			{grid[i+2][j+2] = 0;}
		}
		repaint();
	}
	
	public void paint (Graphics g)
	{
		Graphics2D g2D = (Graphics2D) g;
		super.paint(g);
		
		//background
		g2D.setPaint(new Color (50,50,60));
		g2D.fillRect(0, 0, PANEL_SIZE, PANEL_SIZE);
		
		if (creationMode)
		{
			g2D.setPaint(new Color (50,250,150));
			g2D.setStroke(new BasicStroke(2));
			g2D.drawRect(2*PIXEL_SIZE, 2*PIXEL_SIZE, (creationGridLength+2)*PIXEL_SIZE, (creationGridLength+2)*PIXEL_SIZE);
		}
		
		//filling in the pixels
		g2D.setPaint(new Color (50,180,250));
		for (int i = 0; i < pixelsPerRow; i++)
		{
			for (int j = 0; j < pixelsPerRow; j++)
			{
				if (grid[i][j] != 0)
				{
					g2D.fillRect(i*PIXEL_SIZE, j*PIXEL_SIZE, PIXEL_SIZE, PIXEL_SIZE);
				}
			}
		}
		
		//drawing the moveableObjects
		g2D.setPaint(Color.red);
		for (int i = 0; i < objectAmount; i++)
		{g2D.fillRect(moveableObjects[0][i], moveableObjects[1][i], PIXEL_SIZE, PIXEL_SIZE);}
	}

	private class DragListener extends MouseMotionAdapter
	{
	    public void mouseDragged(MouseEvent e) 
	    {  
	    	int x = e.getX();
	    	int y = e.getY();
	    	
	    	//make sure the event occurred on screenspace
	    	if (x > PANEL_SIZE-PIXEL_SIZE || x < 0 || y > PANEL_SIZE-PIXEL_SIZE || y < 0) {return;}
	    	
	    	activeGRIDX = x / PIXEL_SIZE;
	    	activeGRIDY = y / PIXEL_SIZE;

	    	if (e.isShiftDown()) 
			{grid[activeGRIDX][activeGRIDY] = 0;}
			else 
			{grid[activeGRIDX][activeGRIDY] = 1;}
	    	
	    	repaint();
	    }
	}
	
	public void move (int x, int y, int fallingSpeed)
	{
		//grid indices of the objects
		int objectGRIDX;
		int objectGRIDY;
		
		for (int i = 0; i < objectAmount; i++)
		{
			moveableObjects[0][i]+=x; 
			moveableObjects[1][i]+=y;
			
			collision = false;

			//make sure no object exits screenspace + a few pixels buffer
			if (moveableObjects[0][i] <= 0 || moveableObjects[1][i] <= 0 || moveableObjects[0][i] >= PANEL_SIZE-PIXEL_SIZE*2 || moveableObjects[1][i] >= PANEL_SIZE-PIXEL_SIZE*2)
			{abortMove(x,y, i); collision = true;}
			
			objectGRIDX = moveableObjects[0][i] / PIXEL_SIZE;
			objectGRIDY = moveableObjects[1][i] / PIXEL_SIZE;
		
			if (grid[objectGRIDX][objectGRIDY] != 0)
			{abortMove(x,y, i); collision = true;}
				
			//THE FOLLOWING NEEDS TO BE CHECKED THOUGH IT BECOMES LESS IMPORTANT THE MORE OBJECTS
			//THERE ARE, BUT SINCE PERFORMANCE IS SO GOOD, CHECK IT EVERY TIME
			
			int xCheck = 1;
			int yCheck = 1;
			
			if (x > 0) {xCheck = -1;} 
			if (y > 0) {yCheck = -1;}
				
			//DO THIS TO CHECK THE TWO PIXELS THAT ARE BETWEEN PREV AND CURRENT POSITION
			//DURING DIAGONAL MOVEMENT
			/* 0 IS PREVIOUS, P IS CURRENT POSITION
			* CASES:
			* 		[]P		 []0	 0[]	  P[]
			* 		0[]		 P[]	 []P	  []0
			* 		x+ y-	x- y+	x+ y+	 x- y-
			*  ========================================= --> INVERSE X AND Y
			*  	 [x-]P	    [y-]0	  0[y-]	 	P[x+]
			* 	   0[y+]	  P[x+]	 [x-]P    [y+]0
			*  =========================================  
			*/
				
			if ((grid[objectGRIDX+xCheck][objectGRIDY] != 0) && (grid[objectGRIDX][objectGRIDY+yCheck] != 0))
			{abortMove(x,y,i); collision = true;}
			
			if (collision) {break;} //no need to do more calculations if we about this rounds movement anyway	
		}
		
		//do the checks for falling movement after so we can abort fall movement separately
		if (fallingSpeed != 0)
		{
			for (int i = 0; i < objectAmount; i++)
			{
				//make sure it's in screenspace
				if (moveableObjects[1][i]+fallingSpeed < PANEL_SIZE-PIXEL_SIZE && moveableObjects[1][i]+fallingSpeed >0)
				{
					moveableObjects[1][i]+=fallingSpeed;
					objectGRIDX = moveableObjects[0][i] / PIXEL_SIZE;
					objectGRIDY = moveableObjects[1][i] / PIXEL_SIZE;
					
					if (grid[objectGRIDX][objectGRIDY] != 0) 
					{abortFall(fallingSpeed, i); collision = true; break;}
				}
			}
		}
		
		repaint();	
	}
	
	//don't need to abort beyond index, because those objects haven't been moved yet
	public void abortFall(int fallingSpeed, int index)
	{
		for (int i = 0; i <= index; i++) 
		{moveableObjects[1][i]-=fallingSpeed;}
	}
	
	public void abortMove(int x, int y, int index)
	{
		for (int i = 0; i <= index; i++)
		{moveableObjects[0][i]-=x; moveableObjects[1][i]-=y;}
	}
	
	public void changeSize(int amount)
	{
		PANEL_SIZE+=amount;
		pixelsPerRow = PANEL_SIZE/PIXEL_SIZE;
		
		grid = null;
		System.gc();
		grid = new byte[pixelsPerRow][pixelsPerRow];
		
		this.setPreferredSize(new Dimension (PANEL_SIZE,PANEL_SIZE));
		repaint();
	}
}

public class ClothSim extends JFrame
{
	private static final long serialVersionUID = -1946430738048947884L;
	
	ClothSimPanel panel;
	
	static Timer timer;
	
	ActionListener actionListener;
	
	JMenuItem fileLoad  = new JMenuItem("Load");
	JMenuItem fileSave  = new JMenuItem("Save");

	public ClothSim()
	{

		panel = new ClothSimPanel();
		//this.setIconImage(MainMenu.img.getImage());
		this.add(panel);
		this.pack();
		this.setTitle("Cloth Sim");
		
		timer = new Timer(30, new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{panel.simulation();}	
		});
		
		this.addKeyListener(new KeyListener()
		{
			@Override
			public void keyPressed(KeyEvent e) 
			{
				int code = e.getKeyCode();
				
				if (code == 67) {panel.connect(true);}	//C -> CONNECT
				else if (code == 83) {if (timer.isRunning()) {timer.stop();} else {timer.start();}}	//S -> START
				else if (code == 82) {panel.restart();} //R -> RESTART
				else if (code == 90 && e.isControlDown()) {panel.removeLastConnector();} //CTRL Z (APPLIES TO CONNECTORS ONLY)	
			}
			@Override
			public void keyTyped(KeyEvent e) {}
			@Override
			public void keyReleased(KeyEvent e) {}
		});
		
		actionListener = new ActionListener()
		{	@Override
			public void actionPerformed(ActionEvent e) 
			{
				if (e.getSource() == fileSave)
				{saveLayout();}
				else if  (e.getSource() == fileLoad)
				{loadLayout();}
			}
		};
		
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu= new JMenu("Files");
		
		setItemBasics(fileLoad, fileMenu);
		setItemBasics(fileSave, fileMenu);
		
		fileMenu.setForeground(new Color (230,230,250));
		fileMenu.setBorder(BorderFactory.createLineBorder(new Color (100,100,120)));
		menuBar.add(fileMenu);
		
		menuBar.setBackground(new Color (100,100,120));
		menuBar.setBorder(BorderFactory.createLineBorder(new Color (115,115,135), 2));
		this.setJMenuBar(menuBar);
		
		this.setVisible(true);
	}
	
	public void setItemBasics(JMenuItem item, JMenu menu)
	{item.addActionListener(actionListener); menu.add(item);}
	
	public void saveLayout()
	{
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File("C:\\Users\\Erik\\eclipse-workspace\\GameCollection\\ClothLayouts"));
		
		int response = fileChooser.showSaveDialog(null);
		
		File file = null;
		
		if (response == JFileChooser.APPROVE_OPTION) 
		{
			file = new File(fileChooser.getSelectedFile().getAbsolutePath());
		
			try 
			{
				FileWriter fw = new FileWriter(file,false);
				
				fw.write(""+panel.pointAmount+"|");
				
				DecimalFormat df = new DecimalFormat("#.##");   //ROUND THE DOUBLES TO TWO DECIMAL PLACES    
				
				for (int i = 0; i < panel.pointAmount; i++)
				{
					double x = panel.points[i].positionX;
					double y = panel.points[i].positionY;
					
					//"X,Y,ISLOCKED;"
					fw.write(Double.valueOf(df.format(x))+","+ Double.valueOf(df.format(y))+ ","+ panel.points[i].isLocked+";");
				}
				
				fw.write("|"+panel.connectorAmount+"|");
				
				for (int i = 0; i < panel.connectorAmount; i++)
				{
					//"I1,I2,ISALIVE;"
					fw.write(panel.connectors[i].pointA.index+","+ panel.connectors[i].pointB.index + "," + panel.connectors[i].isAlive+";");
				}
				
				fw.close();
			}
			catch (IOException e1) {e1.printStackTrace();}
		}
	}
	
	public void loadLayout()
	{
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File("C:\\Users\\Erik\\eclipse-workspace\\GameCollection\\ClothLayouts"));
		
		int response = fileChooser.showOpenDialog(null);
		
		if (response == JFileChooser.APPROVE_OPTION) 
		{
			File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
			
			try
			{
				BufferedReader reader = null;
				reader = new BufferedReader(new FileReader(file));
		
				String text = reader.readLine();
				
				panel.prevSelectedPoint = -1;
				panel.selectedPoint = -1;
				
				int start = 0;
				int end = 0;
				while (text.charAt(end) != '|') {end++;}
				
				panel.pointAmount = Integer.parseInt(text.substring(start,end));
				
				for (int i = 0; i < panel.pointAmount; i++)
				{
					panel.newPoint(i);
					
					end++;
					start = end;
					while (text.charAt(end) != ',') {end++;}
					panel.points[i].positionX = Float.parseFloat(text.substring(start,end));
					panel.points[i].prevPositionX = panel.points[i].positionX;
					
					end++;
					start = end;
					while (text.charAt(end) != ',') {end++;}
					panel.points[i].positionY = Float.parseFloat(text.substring(start,end));
					panel.points[i].prevPositionY = panel.points[i].positionY;
					
					end++;
					start = end;
					while (text.charAt(end) != 'f' && text.charAt(end) != 't') {end++;}
					panel.points[i].isLocked = text.charAt(end) == 't';
					
					while (text.charAt(end) != ';') {end++;}
				}
				
				panel.connectorAmount = 0;
				
				end+=2;
				start = end;
				while (text.charAt(end) != '|') {end++;}
				int iterationCount = Integer.parseInt(text.substring(start,end));
				
				for (int i = 0; i < iterationCount; i++)
				{
					end++;
					start = end;
					while (text.charAt(end) != ',') {end++;}
					panel.prevSelectedPoint = Integer.parseInt(text.substring(start,end));
					
					end++;
					start = end;
					while (text.charAt(end) != ',') {end++;}
					panel.selectedPoint = Integer.parseInt(text.substring(start,end));
					
					panel.connect(false); //FALSE -> CONNECT WITHOUT REPAINTING
					
					end++;
					start = end;
					while (text.charAt(end) != 'f' && text.charAt(end) != 't') {end++;}
					panel.connectors[i].isAlive = text.charAt(end) == 't';
					
					while (text.charAt(end) != ';') {end++;}
				}
				
				System.gc();
				panel.repaint();
				
				reader.close();
			}
			catch (IOException e1) {e1.printStackTrace();}
		}
	}
	
	public void start(WindowEventHandler eventHandler)
	{this.addWindowListener(eventHandler);}
	
	public static void stop()
	{if (timer.isRunning()) {timer.stop();}}
}

public class ClothSimPanel extends JPanel
{
	private static final long serialVersionUID = -5643933341241043804L;
	
	int PANEL_SIZEY = 400;
	int PANEL_SIZEX = (int)(PANEL_SIZEY *1.6);
	
	int pointAmount = 0;	//HOW MANY OF THE 200 PLACES IN THE ARRAY ARE IN USE
	Point[] points = new Point[200];
	
	int connectorAmount = 0;
	Connector[] connectors = new Connector[300];
	
	int pointSize = 10;
	
	int prevSelectedPoint;
	int selectedPoint;
	
	int sizeBuffer = 10; //PREVENT SPAWNING POINTS TOO CLOSE TOGETHER
	
	boolean mouseHeld = false;
	
	ClothSimPanel()
	{
		this.setPreferredSize(new Dimension(PANEL_SIZEX,PANEL_SIZEY));
		
		ClickListener clickListener = new ClickListener();
		DragListener dragListener = new DragListener();
		ReleaseListener releaseListener = new ReleaseListener();
		
		this.addMouseListener(clickListener);
		this.addMouseMotionListener(dragListener);
		this.addMouseListener(releaseListener);
	}
	
	public void paint(Graphics g)
	{
		Graphics2D g2D = (Graphics2D) g;
		super.paint(g2D);
		
		//BACKGROUND
		g2D.setPaint(new Color(50,50,60));
		g2D.fillRect(0, 0,this.getWidth(), this.getHeight());
		
		if (pointAmount == 0) {return;}
	
		//POINTS
		g2D.setPaint(new Color(230,230,235));
		for (int i = 0; i < pointAmount; i++)
		{
			if (points[i].isLocked) 
			{g2D.setPaint(new Color(255,150,150));}
			else 
			{g2D.setPaint(new Color(230,230,235));}
			
			g2D.fillRect((int)points[i].positionX-pointSize/2, (int)points[i].positionY-pointSize/2, pointSize, pointSize);	
		}
		
		//SELECTED POINTS
		g2D.setPaint(new Color(150,255,150));
		if (selectedPoint != -1)
		{
			if (!points[selectedPoint].isLocked)
			{g2D.fillRect((int)points[selectedPoint].positionX-pointSize/2, (int)points[selectedPoint].positionY-pointSize/2, pointSize, pointSize);}
		}
		
		if (prevSelectedPoint != -1)
		{
			if (!points[prevSelectedPoint].isLocked)
			{g2D.fillRect((int)points[prevSelectedPoint].positionX-pointSize/2, (int)points[prevSelectedPoint].positionY-pointSize/2, pointSize, pointSize);}
		}
		
		//CONNECTORS
		g2D.setPaint(new Color(230,230,235));
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		for (int i = 0; i < connectorAmount; i++)
		{
			if (connectors[i].isAlive)
			{
				g2D.drawLine
				((int)connectors[i].pointA.positionX, (int)connectors[i].pointA.positionY, 
				(int)connectors[i].pointB.positionX, (int)connectors[i].pointB.positionY);
			}
		}
	}

	public class Point
	{
		float positionX;
		float positionY;
		
		float prevPositionX;
		float prevPositionY;
		
		boolean isLocked;
		
		int index;
		
		Point(int index)
		{this.index = index;}
	}
	
	public class Connector
	{
		Point pointA;
		Point pointB;
		
		float length;
		
		boolean isAlive = true;
	}
	
	public void simulation()
	{

		for (int i = 0; i < pointAmount; i++)
		{
			if (!points[i].isLocked)
			{	
				float positionXbefore = points[i].positionX;
				float positionYbefore = points[i].positionY;
				
				//VELOCITY
				points[i].positionX+= points[i].positionX - points[i].prevPositionX;
				points[i].positionY+= points[i].positionY - points[i].prevPositionY;
				
				points[i].positionY+= 1; //GRAVITY
				
				points[i].prevPositionX = positionXbefore;
				points[i].prevPositionY = positionYbefore;
				
				//FLOOR COLLISION
				if (points[i].positionY > this.getHeight()-pointSize/2) {points[i].positionY = this.getHeight()-pointSize/2;}
			}
		}
	
		for (int i = 0; i < connectorAmount; i++)
		{	
			if (connectors[i].isAlive)
			{
				float[] connectorCenter = {(connectors[i].pointA.positionX + connectors[i].pointB.positionX) / 2, 
										  (connectors[i].pointA.positionY + connectors[i].pointB.positionY) / 2};
					
				float vectorX = connectors[i].pointA.positionX - connectors[i].pointB.positionX;
				float vectorY = connectors[i].pointA.positionY - connectors[i].pointB.positionY;
						
				float[] connectorVector = normalize(vectorX, vectorY);
					
				//KEEP UP THE CONNECTORS LENGTH BY MOVING THE POINTS HALFWAY IT'S LENGTH IN THE CORRECT DIRECTION FROM IT'S CENTER	
				if (!connectors[i].pointA.isLocked)
				{
					connectors[i].pointA.positionX =  connectorCenter[0] + connectorVector[0] * connectors[i].length/2;
					connectors[i].pointA.positionY =  connectorCenter[1] + connectorVector[1] * connectors[i].length/2;
				}
						
				if (!connectors[i].pointB.isLocked)
				{
					connectors[i].pointB.positionX =  connectorCenter[0] - connectorVector[0] * connectors[i].length/2;
					connectors[i].pointB.positionY =  connectorCenter[1] - connectorVector[1] * connectors[i].length/2;
				}	
			}
		}
		repaint();
	}
	
	public float[] normalize(float x , float y)
	{	
		float[] result = new float[2];
		float length = (float)Math.sqrt(Math.pow(x, 2)+Math.pow(y, 2));
		
		result[0] = x/length;
		result[1] = y/length;
		
		return result;
	}
	
	public void connect(boolean repaint)
	{
		if (selectedPoint == -1 || prevSelectedPoint == -1 || pointAmount < 1 || selectedPoint == prevSelectedPoint) {return;}
		
		connectorAmount++;
		
		int cIndex = connectorAmount-1;
    	connectors[cIndex] = new Connector();
		
		connectors[cIndex].pointA = points[prevSelectedPoint];
		connectors[cIndex].pointB = points[selectedPoint];
		
		connectors[cIndex].length = (float)Math.sqrt
		(Math.pow(connectors[cIndex].pointA.positionX - connectors[cIndex].pointB.positionX, 2) 
		+ Math.pow(connectors[cIndex].pointA.positionY - connectors[cIndex].pointB.positionY, 2));
		
		if (repaint) {repaint();}
	}
	
	public void newPoint(int index)
	{points[index] = new Point(index);}
	
	public void removeLastConnector()
	{connectorAmount--; repaint();}
	
	public void lockPoint()
	{
		if (selectedPoint == -1 || pointAmount < 1) {return;}
		points[selectedPoint].isLocked = !points[selectedPoint].isLocked;
		repaint();
	}
	
	private class ClickListener extends MouseAdapter
	{
	    public void mousePressed(MouseEvent e) 
	    {	
	    	prevSelectedPoint = selectedPoint;
			selectedPoint = getPointIndex(e.getX(), e.getY());
			
			if (e.isControlDown()) {lockPoint(); return;}
		    		
		    if (selectedPoint == -1) //= NULL -> ADD POINT THERE
		    {
		    	pointAmount++;
				    	
				int pIndex = pointAmount-1;
				points[pIndex] = new Point(pIndex);
				    
				points[pIndex].positionX = e.getX();
				points[pIndex].positionY = e.getY();
						
				points[pIndex].prevPositionX = points[pIndex].positionX;
				points[pIndex].prevPositionY = points[pIndex].positionY;
		    }	
	    	repaint();	
	    }
	}
	
	private class DragListener extends MouseMotionAdapter
	{
	    public void mouseDragged(MouseEvent e) 
	    {
	    	if (!mouseHeld || e.isShiftDown()) //ONLY CHANGE THE SELECTED POINT WHEN CUTTING OR WHEN JUST STARTING TO PRESS
	    	{selectedPoint = getPointIndex(e.getX(), e.getY());}
	    	
    		if (selectedPoint == -1 || pointAmount < 1) {return;}
	    	
	    	if (!e.isShiftDown()) //MOVE HELD POINT
	    	{
	    		points[selectedPoint].positionX = e.getX();
	    		points[selectedPoint].positionY = e.getY();
	    	}
	    	
	    	else //"CUT" POINT BY MOVING IT OFF SCREEN AND KILLING IT'S CONNECTOR
	    	{
	    		points[selectedPoint].positionX = -10;
	    		points[selectedPoint].positionY = -10;
	    		
	    		for (int i = 0; i < connectorAmount; i++)
	    		{
	    			if (connectors[i].pointA == points[selectedPoint]) {connectors[i].isAlive = false;}
	    			else if (connectors[i].pointB == points[selectedPoint]) {connectors[i].isAlive = false;}
	    		}
	    	}
	    	
	    	mouseHeld = true;
	    	repaint();
	    }
	}
	
	private class ReleaseListener extends MouseAdapter	
	{
	    public void mouseReleased(MouseEvent e) 
	    {mouseHeld = false;}
	}
	
	public int getPointIndex(int x, int y)
	{
		int index = 0;
		
		x+= pointSize/2;
		y+= pointSize/2;
		
		for (int i = 0; i < pointAmount; i++)
		{
			if (x > points[i].positionX-sizeBuffer && x < points[i].positionX+pointSize+sizeBuffer &&
				y > points[i].positionY-sizeBuffer && y < points[i].positionY+pointSize+sizeBuffer)
			{return index;}
			index++;
		}
		return -1; //NO POINT FOUND
	}
	
	public void restart()
	{
		pointAmount = 0;
		connectorAmount = 0;
		prevSelectedPoint = -1;
		selectedPoint = -1;
		
		System.gc();
		repaint();
	}
}

public class GravityVectors extends JFrame
{
	private static final long serialVersionUID = -7941120893843254800L;

	public void start(WindowEventHandler eventHandler) 
	{
		GravityVectorsPanel panel = new GravityVectorsPanel();
		
		this.addWindowListener(eventHandler);
		//this.setIconImage(MainMenu.img.getImage());
		this.setTitle("Gravity Vectors");
	
		this.addKeyListener(new KeyListener()
		{
			@Override
			public void keyPressed(KeyEvent e) 
			{
				int code = e.getKeyCode();
				
				if (code == 521) {panel.changeSize(30); pack();} //+
				else if (code == 45) {panel.changeSize(-30); pack();} //-
				else if (code == 49) {panel.changePPOINT_COUNT(-1);} //1
				else if (code == 50) {panel.changePPOINT_COUNT(1);} //2
				else if (code == 71) {panel.gradientMode = !panel.gradientMode; panel.repaint();} //G     
				else if (code == 78) {panel.switchArrowNormalization(); panel.repaint();} //N              
			}
			
			@Override
			public void keyTyped(KeyEvent e) 
			{}
			@Override
			public void keyReleased(KeyEvent e) {}
			
		});
		
		this.setResizable(false);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    
		this.add(panel);
		this.pack();
		this.setVisible(true);
	}
}

public class GravityVectorsPanel extends JPanel
{
	private static final long serialVersionUID = 3933829351442493815L;
	
	private int PANEL_WIDTH = 600;
	private int PANEL_HEIGHT = (int) (PANEL_WIDTH*(0.66));
	
	//ARROW VARIABLES
	final private int APOINT_SIZE = 6;
	final private int ARROW_LENGTH = 12;
	final private int APOINT_DISTANCE = APOINT_SIZE * 5; //distance between arrows
	private int APOINT_COUNT_X = 30; //how many arrows on x axis
	private int APOINT_COUNT_Y = APOINT_COUNT_X * 2/3; //how many arrows on y axis
	private ArrowPoint[] arrowPoints;
	
	//PULL/GRAVITY POINT VARIABLES
	final private int PPOINT_SIZE = 12;
	private int PPOINT_COUNT = 1;
	private int selectedPPOINT = 0;
	private PullPoint[] pullPoints = {new PullPoint()};
	
	//COLORS
	final private Color BACKGROUND_COLOR = new Color(50,50,70);
	final private Color ARROW_COLOR = new Color(180,180,200);
	final private Color PPOINT_COLOR = new Color(255,80,80);
	
	//GRADIENT MODE
	boolean gradientMode = false;
	final private float[] gradientFractions = {0.0f, 1.0f};
	final private Color[] gradientColors = {PPOINT_COLOR, Color.green};
	
	boolean validDrag = false;
	private boolean normalizeVector = true;
	
	GravityVectorsPanel()
	{
		this.setPreferredSize(new Dimension(PANEL_WIDTH,PANEL_HEIGHT));
		
		ClickListener clickListener = new ClickListener();
		DragListener dragListener = new DragListener();
		this.addMouseListener(clickListener);
		this.addMouseMotionListener(dragListener);
		
		start();
	}
	
	public void start()
	{
		//CLEAR OLD ARRAY FROM MEMORY, SET UP NEW ONE WITH ADJUSTED SIZE
		arrowPoints = null;
		System.gc();
		arrowPoints = new ArrowPoint[APOINT_COUNT_X * APOINT_COUNT_Y];
		
		//ARROWPOINTS
		int index = 0;
		for (int i = 0; i < APOINT_COUNT_X; i++)
		{
			for (int j = 0; j < APOINT_COUNT_Y; j++)
			{
				arrowPoints[index] = new ArrowPoint(i*APOINT_DISTANCE+(APOINT_DISTANCE / 3), j*APOINT_DISTANCE+(APOINT_DISTANCE / 3));
				index++;
			}
		}
		
		simulate(); repaint();
	}
	
	private class ArrowPoint
	{
		ArrowPoint(int locX, int locY)
		{this.locX = locX; this.locY = locY; arrowX = locX + 15;  arrowY = locY;}
		
		//LOCATIONS OF THE ACTUAL POINTS
		int locX;
		int locY;
		
		//LOCATIONS OF THE ENDS OF THE ARROWS
		int arrowX;
		int arrowY;
	}
	
	private class PullPoint
	{
		int locX = 30;
		int locY = 30;
	}
	
	private void simulate()
	{
		for (ArrowPoint point : arrowPoints)
		{
			double[] vectorsX = new double[PPOINT_COUNT];
			double[] vectorsY = new double[PPOINT_COUNT];
			
			for (int i = 0; i < PPOINT_COUNT; i++)
			{
				vectorsX[i] = pullPoints[i].locX - point.locX;
				vectorsY[i] = pullPoints[i].locY - point.locY;
			}
			
			double[] distances = new double[PPOINT_COUNT];
			
			for (int i = 0; i < PPOINT_COUNT; i++)
			{
				distances[i] = Math.sqrt(Math.pow((vectorsX[i]),2) + Math.pow((vectorsY[i]),2));
			}
			
			//HAVE THE PPOINTS EFFECT THE ARROWS EXPONENTIALLY MORE, THE SMALLER THE DISTANCE BETWEEN THEM IS (0.99^distance)
			//-> HIGHER DISTANCE = SMALLER NUMBER WITH WHICH THE VECTOR WILL END UP BEING MULTIPLIED
			double[] strengths = new double[PPOINT_COUNT];
			for (int i = 0; i < PPOINT_COUNT; i++)
			{strengths[i] = Math.pow(0.99, distances[i]);}
			
			//NORMALIZE VECTORS SO "STRENGTHS" CAN ACTUALLY AFFECT THE VECTORS PROPORTIONALLY
			for (int i = 0; i < PPOINT_COUNT; i++)
			{
				double[] temp = normalize(vectorsX[i], vectorsY[i], distances[i]);
				vectorsX[i] = temp[0];
				vectorsY[i] = temp[1];
			}
			
			double arrowVectX = 0;
			double arrowVectY = 0;
			
			//COMBINE ALL THE VECTORS MULTIPLIED BY THEIR STRENGTH
			for (int i = 0; i < PPOINT_COUNT; i++)
			{
				arrowVectX+= vectorsX[i] * strengths[i];
				arrowVectY+= vectorsY[i] * strengths[i];
			}
			
			//NORMALIZE ONE FINAL TIME TO MULTIPLY WITH ARROW_LENGTH
			if (normalizeVector)
			{
				double length = Math.sqrt(Math.pow(arrowVectX, 2)+Math.pow(arrowVectY, 2));
				double[] temp = normalize(arrowVectX, arrowVectY, length);
				arrowVectX = temp[0];
				arrowVectY = temp[1];
			}
			
			point.arrowX = (int) (point.locX + arrowVectX * ARROW_LENGTH);
			point.arrowY = (int) (point.locY + arrowVectY * ARROW_LENGTH);
		}
	}
	
	public void switchArrowNormalization()
	{
		normalizeVector = !normalizeVector;
		simulate();
		repaint();
	}
	
	public double[] normalize(double x , double y, double length)
	{	
		double[] result = new double[2];
		
		result[0] = x/length;
		result[1] = y/length;
		
		return result;
	}
	 
	public void changeSize(int amount)
	{
		PANEL_WIDTH += amount;
		PANEL_HEIGHT = (int) (PANEL_WIDTH*(0.66));
		this.setPreferredSize(new Dimension(PANEL_WIDTH,PANEL_HEIGHT));
		
		APOINT_COUNT_X++;
		APOINT_COUNT_Y = APOINT_COUNT_X * 2/3;
		
		start();
	}
	
	public void changePPOINT_COUNT(int amount)
	{
		selectedPPOINT = 0; //make sure we don't keep having a point selected that is about to get deleted
		
		if (PPOINT_COUNT + amount < 1) {return;} //don't go below 1 PPOINT
		
		//save the locations so they can be reset after clearing the old array
		int[][] pullPointLocations = new int[PPOINT_COUNT][2];	
		for (int i = 0; i < PPOINT_COUNT; i++)
		{
			pullPointLocations[i][0] = pullPoints[i].locX;
			pullPointLocations[i][1] = pullPoints[i].locY;
		}
		
		PPOINT_COUNT+= amount;
				
		pullPoints = null;
		System.gc();
		pullPoints = new PullPoint[PPOINT_COUNT];
		
		//RESET TO OLD LOCATIONS
		//IF POINTS DECREASED, GRAB OLD LOCATIONS OF ALL POINTS, IF INCREASED, GRAB LOCATIONS OF ALL POINTS -1,
		//BECAUSE THE NEWEST DOES NOT HAVE AN OLD LOCATION
		int incOrdecr = -1;
		if (amount < 0) {incOrdecr = 0;}
		
		for (int i = 0; i < PPOINT_COUNT + incOrdecr; i++)
		{
			pullPoints[i] = new PullPoint();
			pullPoints[i].locX = pullPointLocations[i][0];
			pullPoints[i].locY = pullPointLocations[i][1];
		}
		
		//set new PullPoint, if there is one
		if (amount > 0)
		{pullPoints[PPOINT_COUNT-1] = new PullPoint();}
		
		simulate(); repaint();
	}	
	
	public void paint(Graphics g)
	{
		Graphics2D g2D = (Graphics2D) g;
		super.paint(g2D);
		
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		//BACKGROUND
		g2D.setPaint(BACKGROUND_COLOR);
		g2D.fillRect(0, 0, PANEL_WIDTH, PANEL_HEIGHT);
		
		//ARROW POINTS
		if (gradientMode)
		{
			RadialGradientPaint distFromCenter = 
			new RadialGradientPaint(pullPoints[selectedPPOINT].locX, pullPoints[selectedPPOINT].locY, 500, gradientFractions, gradientColors);
			g2D.setPaint(distFromCenter);
		}
		
		else 
		{g2D.setPaint(ARROW_COLOR);}
		
		for (int i = 0; i < arrowPoints.length; i++)
		{
			//prevent trying to draw line when the mouse is exactly on the point
			if (arrowPoints[i].arrowX != 0) 
			{g2D.drawLine(arrowPoints[i].locX, arrowPoints[i].locY, arrowPoints[i].arrowX, arrowPoints[i].arrowY);}
			
			g2D.drawRect(arrowPoints[i].arrowX-1, arrowPoints[i].arrowY-1, 2, 2);
		}
		
		//PULL POINTS
		g2D.setPaint(PPOINT_COLOR);
		for (int i = 0; i < PPOINT_COUNT; i++)
		{g2D.fillOval(pullPoints[i].locX-6, pullPoints[i].locY-6, PPOINT_SIZE, PPOINT_SIZE);}
		
	}
	
	private class ClickListener extends MouseAdapter
	{
	    public void mousePressed(MouseEvent e)
	    {selectGravityPoint(e.getX(), e.getY());} 
	}
	
	private class DragListener extends MouseMotionAdapter
	{
	    public void mouseDragged(MouseEvent e) 
	    {moveGravityPoint(e.getX(), e.getY()); simulate(); repaint();}  
	}
	
	private void selectGravityPoint(int X, int Y)
	{	
		validDrag = false;
		
		for (int i = 0; i < PPOINT_COUNT; i++)
		{
			if 
			(
				X > pullPoints[i].locX-10 && X < pullPoints[i].locX+10 &&
				Y > pullPoints[i].locY-10 && Y < pullPoints[i].locY+10 
			)
			{selectedPPOINT = i; validDrag = true; break;}
		}
	}
	
	private void moveGravityPoint(int X, int Y)
	{
		if (validDrag)
		{
			pullPoints[selectedPPOINT].locX = X;
			pullPoints[selectedPPOINT].locY = Y;
			
			repaint();
		}
	}
}

public class BloonShooting extends JFrame
{
	private static final long serialVersionUID = -1952542729679771029L;
	
	BlS_Panel panel;

	public BloonShooting()
	{
		panel = new BlS_Panel();
		//this.setIconImage(MainMenu.img.getImage());
		this.add(panel);
		this.pack();
		this.setTitle("Bloon Shooting");
		
		this.addKeyListener(new KeyListener() 
		{
			@Override
			public void keyPressed(KeyEvent e) 
			{
				int code = e.getKeyCode();
				
				if (code == 71) {panel.changeGridVisibility();} //G
				else if (code == 45) {panel.changeSize(-10); pack();} //-
				else if (code == 521) {panel.changeSize(10); pack();} //+
				
			}

			@Override
			public void keyReleased(KeyEvent e) {}
			@Override
			public void keyTyped(KeyEvent e) {}
			
		});
		
		this.setResizable(false);
		this.setVisible(true);
	}
	
	public void start(WindowEventHandler eventHandler)
	{this.addWindowListener(eventHandler);}
}

public class BlS_Panel extends JPanel implements ActionListener
{
	private static final long serialVersionUID = 5219711456361037203L;
	
	private int PANEL_WIDTH = 600;
	private int PANEL_HEIGHT = (int) (PANEL_WIDTH *0.6);
	
	private final Color BACKGROUND = new Color(50,50,60);
	
	//SLINGSHOT
	private Slingshot slingshot = new Slingshot();
	private Color[] slingshotColors = {Slingshot.Color1,Slingshot.Color2};
	private boolean shiftMove = false;
	
	//PROJECTILE
	private Projectile projectile = new Projectile();
	private Color[] projectileColors = {Projectile.Color1,Projectile.Color2,Projectile.Color3,Projectile.Color4};
	
	//MAP & GRID
	private boolean gridVisible = false;
	private final int CELL_COUNT_X = 44;
	private final int CELL_COUNT_Y = 26;
	private int CELL_SIZE = PANEL_WIDTH/CELL_COUNT_X;
	
	//SIMULATION
	private final Timer shootTimer = new Timer(15,this);

	BlS_Panel()
	{
		this.setPreferredSize(new Dimension( PANEL_WIDTH, PANEL_HEIGHT));
		
		ClickListener clickListener = new ClickListener();
		DragListener dragListener = new DragListener();
		ReleaseListener releaseListener = new ReleaseListener();
		
		this.addMouseListener(clickListener);
		this.addMouseMotionListener(dragListener);
		this.addMouseListener(releaseListener);
		
		slingshot.initialize(PANEL_WIDTH, PANEL_HEIGHT, PANEL_WIDTH/250);
		projectile.setPixelSize(PANEL_WIDTH/450); projectile.initialize(slingshot.getPullPoint());
	}
	
	public void paint(Graphics g)
	{
		Graphics2D g2D = (Graphics2D) g;
		super.paint(g);
		
		//BACKGROUND
		g2D.setPaint(BACKGROUND);
		g2D.fillRect(0, 0, PANEL_WIDTH, PANEL_HEIGHT);
		
		//GRID
		if (gridVisible)
		{
			g2D.setPaint(Color.LIGHT_GRAY);
			for (int i = 0; i < CELL_COUNT_X; i++)
			{g2D.drawLine(i*CELL_SIZE, 0, i*CELL_SIZE, PANEL_HEIGHT);}
			for (int i = 0; i < CELL_COUNT_Y; i++)
			{g2D.drawLine(0, i*CELL_SIZE, PANEL_WIDTH, i*CELL_SIZE);}
		}
		
		//SLINGSHOT
		paintSprite(slingshotColors, Slingshot.SPRITE, slingshot.getOrigin(), slingshot.getPixelSize(), g2D);
		
		//SLINGSHOT BAND
		g2D.setStroke(new BasicStroke(PANEL_WIDTH/150));
		g2D.setPaint(Slingshot.SlingColor);
		int[] paintOrigin = slingshot.getPaintOrigin();
		int[] pullPoint = slingshot.getPullPoint();
		g2D.drawLine(paintOrigin[0], paintOrigin[1], pullPoint[0], pullPoint[1]);
		g2D.drawLine(paintOrigin[2], paintOrigin[1], pullPoint[0], pullPoint[1]);
		
		//PROJECTILE
		paintSprite(projectileColors, Projectile.SPRITE, projectile.getOrigin(), projectile.getPixelSize(), g2D);
	}
	
	private void paintSprite(Color[] colors, byte[] sprite, int [] origin, int pixelSize, Graphics2D g2D)
	{
		int row = 0, column = 0;
		
		for (int i = 0; i < 256; i++)
		{
			if (column == 16) {row++; column = 0;}
			
			if (sprite[i] != 0)
			{
				g2D.setPaint(colors[sprite[i]-1]);
				g2D.fillRect(origin[0] + column*pixelSize, origin[1]+ row*pixelSize, pixelSize, pixelSize);
			}
			column++;
		}
	}
	
	private class ClickListener extends MouseAdapter
	{
		public void mousePressed(MouseEvent e) 
		{
			if(!shootTimer.isRunning())
			slingshot.setDragValid(e.getX(), e.getY()); //CHECK IF MOUSE IS INSIDE DRAG AREA
		}
	}
	   
	private class DragListener extends MouseMotionAdapter
	{
		public void mouseDragged(MouseEvent e) 
		{
			//DON'T MOVE IF NOT INSIDE DRAG AREA OR IF A SHOT IS STILL OCCURRING
			if (!slingshot.isDragValid() || shootTimer.isRunning()) {return;} 
			
			//MOVE WHOLE SLINGSHOT
			if (e.isShiftDown()) 
			{slingshot.initOnNewCoords(e.getX(), e.getY()); shiftMove = true;}
			
			//MOVE SLINGSHOT BAND
			else 
			{slingshot.setPullPoint(e.getX(), e.getY());}
			
			projectile.setNewOrigin(slingshot.getPullPoint());
			repaint();
		}
	}
	
	private class ReleaseListener extends MouseAdapter
	{
		public void mouseReleased(MouseEvent e) 
		{
			//DON'T DO IF NOT INSIDE DRAG AREA OR IF A SHOT IS STILL OCCURRING
			if (!slingshot.isDragValid() || shootTimer.isRunning()) {return;}
			if (shiftMove) {shiftMove = false; return;} //IF WHOLE SLING IS BEING MOVED, DON'T RELEASE PROJECTILE
			
			slingshot.setReturnVect(); slingshot.slingReturnRounds = 0;
			projectile.setSpeed(slingshot.getReturnVect());
			shootTimer.start();
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{	
		if (slingshot.moveSling()) //MOVES SLINGSHOT WHILE ALSO CHECKING IF THE SLINGSHOT HAS STOPPED MOVING
		{projectile.setNewOrigin(slingshot.getPullPoint());}
		
		else
		{
			if (!projectile.fly(PANEL_HEIGHT)) //MOVES PROJECTILE WHILE ALSO CHECKING IF THE PROJECTILE HAS HIT THE FLOOR
			{shootTimer.stop(); projectile.initialize(slingshot.getPullPoint());} //RESET PROJECTILE
		} 
		repaint();
	}
	
	public void changeGridVisibility()
	{gridVisible = !gridVisible; repaint();}
	
	public void changeSize(int amount)
	{
		//PANEL
		PANEL_WIDTH+=amount;
		PANEL_HEIGHT = (int) (PANEL_WIDTH *0.6);
		this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		
		//GRID
		CELL_SIZE = PANEL_WIDTH/CELL_COUNT_X;
		
		//OBJECTS
		slingshot.initialize(PANEL_WIDTH, PANEL_HEIGHT, PANEL_WIDTH/250);
		projectile.setPixelSize(PANEL_WIDTH/450); projectile.initialize(slingshot.getPullPoint());
		
		repaint();
	}

}

public class Slingshot 
{
	//SPRITE
	final static Color Color1 = new Color(110,125,170);
	final static Color Color2 = new Color(95,110,160);
	final static Color SlingColor = new Color(75,75,110);
	
	private int PIXEL_SIZE;
	
	static final byte[] SPRITE = 
	{
			1,2,2,0,0,0,0,0,0,0,0,0,0,0,1,1,
			1,1,1,2,0,0,0,0,0,0,0,0,0,1,1,2,
			0,1,1,1,2,0,0,0,0,0,0,0,1,1,2,2,
			0,0,1,1,1,2,0,0,0,0,0,1,1,2,2,0,
			0,0,0,1,1,1,2,0,0,0,0,1,2,2,0,0,
			0,0,0,0,1,1,2,2,0,0,1,1,2,0,0,0,
			0,0,0,0,1,1,2,2,0,0,1,1,2,0,0,0,
			0,0,0,0,0,1,1,1,2,1,1,2,2,0,0,0,
			0,0,0,0,0,0,1,1,2,1,1,2,0,0,0,0,
			0,0,0,0,0,0,1,1,1,1,2,2,0,0,0,0,
			0,0,0,0,0,0,0,1,1,1,2,0,0,0,0,0,
			0,0,0,0,0,0,0,1,1,1,2,0,0,0,0,0,
			0,0,0,0,0,0,0,1,1,1,2,0,0,0,0,0,
			0,0,0,0,0,0,2,1,1,1,2,0,0,0,0,0,
			0,0,0,0,0,2,1,1,1,1,1,2,0,0,0,0,
			0,0,0,0,2,2,2,2,2,2,2,2,2,0,0,0,
	};
		
	//COORDINATES
	private int[] ORIGIN = new int[2];
	
	private int[] PULLPOINT = new int[2]; //WHERE IS THE PULLPOINT CURRENTLY
	private int[] PULLPOINT_ORIGIN = new int[2];
	
	private float[] returnVect = new float[2];
	
	//CONDITIONS
	private boolean dragValid = false;
	
	int slingReturnRounds = 0;

	
	//INITIALIZATION
	public void initialize(int PANEL_WIDTH, int PANEL_HEIGHT, int size)
	{setPixelSize(size); initOnNewCoords(PANEL_WIDTH/10, PANEL_HEIGHT - (PANEL_HEIGHT/4));}
		
	public void initOnNewCoords(int x, int y)
	{
		ORIGIN[0] = x; ORIGIN[1] = y;
			
		PULLPOINT_ORIGIN[0] = ORIGIN[0] + PIXEL_SIZE*8;
		PULLPOINT_ORIGIN[1] = ORIGIN[1] + PIXEL_SIZE*8;
			
		PULLPOINT[0] = PULLPOINT_ORIGIN[0];
		PULLPOINT[1] = PULLPOINT_ORIGIN[1];
	}
	
	//MOVEMENT
	public boolean moveSling()
	{
		if (slingReturnRounds > 4) {return false;} //SLING HAS REACHED ORIGIN -> RETURN NO LONGER MOVING
		
		PULLPOINT[0]+= returnVect[0]/5;
		PULLPOINT[1]+= returnVect[1]/5;
		
		slingReturnRounds++;
		
		return true; //-> STILL MOVING
	}
	
	//RETURN VECTOR
	public void setReturnVect()
	{returnVect[0] = PULLPOINT_ORIGIN[0] - PULLPOINT[0]; returnVect[1] = PULLPOINT_ORIGIN[1] - PULLPOINT[1];}
	
	public float[] getReturnVect()
	{return returnVect;}
	
	
	//PULL POINT
	public void setPullPoint(int x, int y)
	{PULLPOINT[0] = x;PULLPOINT[1] = y;}
	
	public int[] getPullPoint()
	{return PULLPOINT;}
	
	//DRAG VALID
	public void setDragValid(int x, int y)
	{dragValid = (x > PULLPOINT[0]-10 && x < PULLPOINT[0]+10 && y > PULLPOINT[1]-10 && y < PULLPOINT[1]+10);}
	
	public boolean isDragValid()
	{return dragValid;}
	
	//SIZE
	public void setPixelSize(int size)
	{PIXEL_SIZE = size;}
	
	public int getPixelSize()
	{return PIXEL_SIZE;}
	
	//ORIGIN
	public int[] getOrigin()
	{return ORIGIN;}
	
	public int[] getPaintOrigin()
	{return new int[] {ORIGIN[0] + PIXEL_SIZE, ORIGIN[1] + PIXEL_SIZE, ORIGIN[0] + PIXEL_SIZE*14};}
}

public class Projectile 
{
	//SPRITE
	final static Color Color1 = new Color(145,170,234);
	final static Color Color2 = new Color(138,154,194);
	final static Color Color3 = new Color(94,111,157);
	final static Color Color4 = new Color(89,101,133);
	
	private int PIXEL_SIZE;

	static final byte[] SPRITE = 
	{
			0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
			0,0,0,0,0,0,0,3,3,0,0,0,0,0,0,0,
			0,0,0,0,0,0,2,2,2,2,0,0,0,0,0,0,
			0,0,0,3,2,0,2,4,4,2,0,2,3,0,0,0,
			0,0,0,2,2,2,2,3,3,2,2,2,2,0,0,0,
			0,0,0,0,2,2,1,3,3,1,2,2,0,0,0,0,
			0,0,2,2,2,1,3,3,3,3,1,2,2,2,0,0,
			0,3,2,4,3,3,3,4,4,3,3,3,4,2,3,0,
			0,3,2,4,3,3,3,4,4,3,3,3,4,2,3,0,
			0,0,2,2,2,1,3,3,3,3,1,2,2,2,0,0,
			0,0,0,0,2,2,1,3,3,1,2,2,0,0,0,0,
			0,0,0,2,2,2,2,3,3,2,2,2,2,0,0,0,
			0,0,0,3,2,0,2,4,4,2,0,2,3,0,0,0,
			0,0,0,0,0,0,2,2,2,2,0,0,0,0,0,0,
			0,0,0,0,0,0,0,3,3,0,0,0,0,0,0,0,
			0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
	};
	
	
	//COORDINATES & VECTORS
	private int[] ORIGIN = new int[2];
	
	private float[] SPEED = new float[2];
	
	//INITIALIZATION
	public void initialize(int[] origin)
	{ORIGIN[0] = origin[0] - PIXEL_SIZE*8; ORIGIN[1] = origin[1] - PIXEL_SIZE*8;}
	
	//MOVEMENT
	public boolean fly(int PANEL_HEIGHT)
	{
		if (ORIGIN[1] > PANEL_HEIGHT) {return false;} //HAS HIT FLOOR
		
		ORIGIN[0]+= SPEED[0]; ORIGIN[1]+= SPEED[1]; 
		
		if (SPEED[0] > 0) {SPEED[0]-= 0.1;} //SLOW DOWN HORIZONTAL SPEED
		SPEED[1]+= 0.5; //GRAVITY
		
		return true;
	}
	
	public void setSpeed(float[] vector)
	{SPEED[0] = vector[0]/5; SPEED[1] = vector[1]/5;}
	
	
	//ORIGIN
	public int[] getOrigin()
	{return ORIGIN;}
	
	public void setNewOrigin(int[] origin)
	{ORIGIN[0] = origin[0] - PIXEL_SIZE*8; ORIGIN[1] = origin[1] - PIXEL_SIZE*8;}
	
	//SIZE
	public void setPixelSize(int size)
	{PIXEL_SIZE = size;}
		
	public int getPixelSize()
	{return PIXEL_SIZE;}
}


public class WindowEventHandler extends WindowAdapter
{
	public void windowClosing(WindowEvent evt) 
	  { 
		String source = evt.getWindow().toString();
		
		int start = 50;
		while (start < 1000)
		{start++; if (source.charAt(start-2) == 'e' && source.charAt(start-1) == '=' ) {break;}}
		
		int end = start;
		while (end < 1000)
		{end++; if (source.charAt(end) == ',') {break;}}
		
		String title = source.substring(start, end);
		
		int index = -1;
		
		switch(title)
		{
			case "3D Coordinates": Coordinates3DFrame.stop(); index = 0; break;	
			case "Insects": InsectsPanel.stop(); index = 1; break;
			case "Particles" : ParticlesPanel.stop();index = 2; break;
			case "Sierpinski": SierpinskiSlow.stop();index = 3; break;
			case "Sudoku": index = 4; break;
			case "Pathfind": Pathfind.stop(); index = 5; break;
			case "Parallel Universes": ParallelUniverses.stop(); index = 6; break;
			case "Reflection Demo": index = 7; break;
			case "Mouse Dodge": MouseDodgePanel.stop(); index = 8; break; 
			case "Sidescroller": JumpAndRun.stop(); index = 9; break;
			case "Speedrun": JumpAndRun.stop(); index = 10; break;
			case "Pixel Collision": PixelCollision.stop(); index = 11; break;
			case "Cloth Sim": ClothSim.stop(); index = 12; break;
			case "Gravity Vectors": index = 13; break;
			case "Bloon Shooting": index = 14; break;
		}
		
		if (index != -1)
		{MainMenu.hasWindowOpen[index] = false;}
		
		try {MainMenu.restart();} 
		catch (IOException e1) {e1.printStackTrace();} catch (URISyntaxException e1) {e1.printStackTrace();}
	  }
	
}
