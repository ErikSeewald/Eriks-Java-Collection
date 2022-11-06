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
			g2D.setPaint(playerColor);
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
	
	public void changeSize(int height, int plus) 
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
		
		PANEL_SIZE= (height+plus);
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
