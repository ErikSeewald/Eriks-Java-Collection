package MouseDodge;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import javax.swing.JPanel;
import javax.swing.Timer;

public class MouseDodgePanel extends JPanel implements ActionListener{

	private static final long serialVersionUID = 9082922097976866954L;

	int PANEL_SIZE = 600;
	
	//PLAYER
	private class Player
	{
		double X = PANEL_SIZE, Y = PANEL_SIZE/2;
		int size = PANEL_SIZE/35;
		Color color = new Color (220,50,50);
	}
	private Player player;
	
	
	//ENEMIES
	private class Enemy
	{
		double X, Y;
		double goalX, goalY;
		double vecX, vecY; //normalized vectors for enemies to move along
		
		int size;
		int sizeDiv; //divider that decides the ratio of screen size to enemy size
		
		boolean fill;
		
		Enemy()
		{
			this.sizeDiv = random.nextInt(15)+15;
			
			this.fill = random.nextInt(4) == 1;
			
			this.size = PANEL_SIZE/this.sizeDiv;
			
			this.X = random.nextInt((int)(PANEL_SIZE*1.7));
			this.Y = random.nextInt(PANEL_SIZE);
			
			this.goalX = random.nextInt((int)(PANEL_SIZE*1.7));
			this.goalY = random.nextInt(PANEL_SIZE);
			
			generateVectors();
		}
		
		void generateVectors()
		{
			vecX = goalX - X;
			vecY = goalY - Y;
			
			double movelength = Math.sqrt(Math.pow(vecX, 2)+Math.pow(vecY, 2));
			vecX /= movelength*speed; 
			vecY /= movelength*speed;
		}
	}
	private static final int enemyCount = 25;
	private Enemy[] enemies = new Enemy[enemyCount];
	private double speed = 250/(double)PANEL_SIZE;
	
	
	//BOARD
	private class Board
	{
		int X,Y;
		int width, height;
		int endX, endY;
		
		Board()
		{
			X = (int)(PANEL_SIZE/2.8);
			Y = (int)(PANEL_SIZE/7.5);
			width = PANEL_SIZE;		
			height = (int)(PANEL_SIZE/1.35);
			
			endX = X + width;
			endY = Y + height;
		}
	}
	private Board board;
	
	private static Timer moveTimer;	//Timer for each frame (~60fps)
	private static Timer timer;		//Timer for time score 
	private Random random = new Random();
	
	private byte moveNum = 0;	//move counter to limit the amount of updates for the MouseAdapter			
	
	private boolean paused = true;
	boolean debug = false;
	private boolean finished;
	private boolean moveActive = false;		//is the player allowed to move
	boolean darkMode = true;
	
	private int time = 0;
	private int bestTime;
	
	MouseDodgePanel()
	{				
		DragListener dragListener = new DragListener();
		this.addMouseMotionListener(dragListener);
	
		player = new Player();
	
		moveTimer = new Timer(14, this);
		timer = new Timer(1000,this);
		
		start();
	}
	
	public void start()
	{
		try {loadTime();} catch (IOException e) {System.out.println("Load failed");}
		
		if (finished)
		{
			finished = false; 
			pause();
		}
		
		time = 0;
		
		for (int i = 0; i < enemyCount; i++)
		{enemies[i] = new Enemy();}
		
		this.setPreferredSize(new Dimension((int)(PANEL_SIZE*1.7),PANEL_SIZE));
		
		player.color= new Color (220,50,50);
		
		board = new Board();
		
		repaint();
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
		
		g2D.fillRect(board.X, board.Y, board.width,board.height);
		
		if (!darkMode) {g2D.setPaint(Color.GRAY);}
		else {g2D.setPaint(new Color(130,130,170));}
		
		g2D.setStroke(new BasicStroke(2));
		g2D.drawRect(board.X, board.Y, board.width,board.height);
		
		//player
		g2D.setPaint(player.color);
		if (finished) {g2D.setPaint(Color.GREEN);}
		g2D.fillRect((int)player.X, (int)player.Y, player.size, player.size);
		
		//enemies
		
		if (!darkMode) {g2D.setPaint(Color.GRAY);}
		else {g2D.setPaint(new Color(150,150,200));}
		
		for (Enemy enemy : enemies)
		{
			if (enemy.fill)
			{g2D.drawRect((int)enemy.X, (int)enemy.Y, enemy.size, enemy.size);}
			else 
			{g2D.fillRect((int)enemy.X, (int)enemy.Y, enemy.size, enemy.size);}
		}
		
		if (debug)
		{
			//Shows enemy goals
			g2D.setPaint(Color.GREEN);
			for (Enemy enemy : enemies)
			{g2D.fillRect((int)enemy.goalX,(int)enemy.goalY, 10, 10);}	
	
			g2D.setPaint(new Color(130,130,170));
			for (Enemy enemy : enemies)
			{g2D.drawLine((int) enemy.goalX,(int)enemy.goalY, (int)enemy.X, (int)enemy.Y);}
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
			g2D.setFont(new Font ("Dialog", Font.BOLD, PANEL_SIZE/11));
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
			g2D.setPaint(player.color);
			g2D.setFont(new Font ("Dialog", Font.BOLD, PANEL_SIZE/11));
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
		g2D.setFont(new Font ("Dialog", Font.BOLD, PANEL_SIZE/30));
		g2D.drawString("TIME", (int)(PANEL_SIZE*1.51), (int)(PANEL_SIZE/4.8));
		
		g2D.drawString(time+"s", (int)(PANEL_SIZE*1.51), (int)(PANEL_SIZE/3.9));
		
		g2D.drawString(bestTime+"s", (int)(PANEL_SIZE*1.51), (int)(PANEL_SIZE/2.7));
		
		g2D.drawString("BEST", (int)(PANEL_SIZE*1.51), (int)(PANEL_SIZE/3.1));
		
		g2D.setFont(new Font ("Dialog", Font.BOLD, PANEL_SIZE/35));
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
		for (Enemy enemy : enemies)
		{	
			//has the enemy reached its goal -> new goal
			if (enemy.X > enemy.goalX-(PANEL_SIZE/200) && enemy.X < enemy.goalX+(PANEL_SIZE/200)
				&& enemy.Y > enemy.goalY-(PANEL_SIZE/200) && enemy.Y < enemy.goalY+(PANEL_SIZE/200)) 
			{
				enemy.goalX = random.nextInt((int)(PANEL_SIZE*1.5));
				enemy.goalY = random.nextInt(PANEL_SIZE);
				enemy.generateVectors();
			}
			
			enemy.X+= enemy.vecX;
			enemy.Y+= enemy.vecY;
		}
		
		if (isInside(player.X, player.Y))
		{end();}		
		
		repaint();
	}
	
	public boolean isInside(double x, double y)
	{

		for (Enemy enemy : enemies)
		{
			if (enemyBounds(enemy.X, enemy.Y, enemy.size, enemy.size,x,y)) {return true;}
		}
		
		return false;
	}
	
	public boolean enemyBounds(double a, double b,  double a2,double b2,  double x, double y)
	{
		int size = player.size;
		
		boolean isInside = 
				(
						(x > a &&  x < a+a2 && y > b &&  y < b+b2)
					||	(x+size > a &&  x+size < a+a2 && y+size > b &&  y+size < b+b2)
					||	(x+size > a &&  x+size < a+a2 && y > b &&  y < b+b2)
					||	(x > a &&  x < a+a2 && y+size > b &&  y+size < b+b2)
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
		pause();
		
		finished = true;
		
		moveActive = false;
		
		if (time > bestTime)
		{
			bestTime = time;
			try {saveTime();} catch (IOException e) {System.out.println("Save failed");}
		}
		
		repaint();
	}
	
	
	private class DragListener extends MouseMotionAdapter
	{
		public void mouseDragged(MouseEvent e) 
	    {movement(e);}
		
		public void mouseMoved(MouseEvent e) 
	    {movement(e);}
		
		public void movement(MouseEvent e)
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
	    				
	    				if (locationCase != 1 && locationCase != 2) {player.X = x-player.size/2;}
	    				if (locationCase != 3 && locationCase != 2) {player.Y = y-player.size/2;}
	    				    				
	    			}			
	    	}
		}
	}
	
	public byte inBoard(int x, int y)
	{
		// 0 - None outside		1 - X outside	2 - Both outside	3 - Y outside
		
		byte locationCase = 0;
		
		boolean outsideX = (x < board.X || x > board.endX);
		boolean outsideY = (y < board.Y || y > board.endY);
		
		if (outsideX) {locationCase++;}
		if (outsideY) {locationCase++;}
		
		if (locationCase == 1) {if (outsideY) {locationCase= 3;}}
		
		return locationCase;
	}
	
	public void changeSize(int plus) 
	{
		int height = PANEL_SIZE;
		
		double[][] enemyLocRatio = new double[enemyCount][2];
		for (int i = 0; i < enemyCount; i++)
		{
			enemyLocRatio[i][0] = enemies[i].X / (PANEL_SIZE*1.7); 
			enemyLocRatio[i][1] =  enemies[i].Y / PANEL_SIZE;
		}
		
		double[] playerLocRatio = new double[2];
		playerLocRatio[0] = player.X / (PANEL_SIZE*1.7);
		playerLocRatio[1] = player.Y / PANEL_SIZE;
		
		PANEL_SIZE= (height+plus);
		player.size = PANEL_SIZE/30;
		
		speed = 250/(double)PANEL_SIZE;
		
		player.X = playerLocRatio[0] * PANEL_SIZE*1.7;
		player.Y = playerLocRatio[1] * PANEL_SIZE;
		
		for (int i = 0; i < enemyCount; i++)
		{
			enemies[i].size = PANEL_SIZE / enemies[i].sizeDiv;
			
			enemies[i].X = enemyLocRatio[i][0] * PANEL_SIZE*1.7; 
			enemies[i].Y = enemyLocRatio[i][1] * PANEL_SIZE;

			enemies[i].generateVectors();
		}
		
		board = new Board();
			
		repaint(); 
		this.setPreferredSize(new Dimension((int)(PANEL_SIZE*1.7),PANEL_SIZE));
		
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
	
	public static void stop()
	{moveTimer.stop(); timer.stop();}

}
