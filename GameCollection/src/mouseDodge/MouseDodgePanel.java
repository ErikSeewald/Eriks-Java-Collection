package mouseDodge;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.JPanel;
import javax.swing.Timer;

public class MouseDodgePanel extends JPanel implements ActionListener{

	private static final long serialVersionUID = 9082922097976866954L;

	private int PANEL_HEIGHT = 600;
	private int PANEL_WIDTH = (int) (PANEL_HEIGHT * 1.7);
	
	//ENEMIES
	private static final int enemyCount = 25;
	private Enemy[] enemies = new Enemy[enemyCount];
	private double speed = 250/(double)PANEL_HEIGHT;
	
	//BOARD
	private Board board;
	
	//PLAYER
	private Player player;
		
	private Timer moveTimer;	//Timer for each frame (~60fps)
	private Timer timer;		//Timer for time score 
	public Random random = new Random();		
	
	private boolean paused = true;
	private boolean finished = false;
	boolean darkMode = true;
	
	MouseDodgePanel()
	{				
		this.setPreferredSize(new Dimension(PANEL_WIDTH,PANEL_HEIGHT));
		
		player = new Player(PANEL_HEIGHT);
		board = new Board(PANEL_HEIGHT);
		this.addMouseMotionListener(new DragListener(player, board));
	
		moveTimer = new Timer(14, this);
		timer = new Timer(1000,this);
		
		for (int i = 0; i < enemyCount; i++)
		{enemies[i] = new Enemy(random, speed, this);}
		start();
	}
	
	public int getPanelSize() {return PANEL_HEIGHT;}
	
	public void changeSize(int amount) 
	{	
		//SAVE RELATIVE POSITIONS BEFORE UPDATING
		double[][] enemyLocRatio = new double[enemyCount][2];
		for (int i = 0; i < enemyCount; i++)
		{
			enemyLocRatio[i][0] = enemies[i].X / PANEL_WIDTH; 
			enemyLocRatio[i][1] = enemies[i].Y / PANEL_HEIGHT;
		}
		double[] playerLocRatio = {player.X / PANEL_WIDTH, player.Y / PANEL_HEIGHT};
		
		//UPDATING
		PANEL_HEIGHT+= amount;
		PANEL_WIDTH = (int) (PANEL_HEIGHT * 1.7);
		this.setPreferredSize(new Dimension(PANEL_WIDTH,PANEL_HEIGHT));
		board.setSize(PANEL_HEIGHT);
		
		//PLAYER
		player.size = PANEL_HEIGHT/30;
		player.X = playerLocRatio[0] * PANEL_WIDTH;
		player.Y = playerLocRatio[1] * PANEL_HEIGHT;
		
		//ENEMIES
		speed = 250/(double)PANEL_HEIGHT;
		for (int i = 0; i < enemyCount; i++)
		{
			enemies[i].size = PANEL_HEIGHT / enemies[i].sizeDiv;
			enemies[i].X = enemyLocRatio[i][0] * PANEL_WIDTH; 
			enemies[i].Y = enemyLocRatio[i][1] * PANEL_HEIGHT;
			enemies[i].generateVectors(speed);
		}	
		repaint(); 
	}
	
	public void stop()
	{moveTimer.stop(); timer.stop(); moveTimer = null; timer = null; enemies = null;}
	
	//---------------------------------------GAMEPLAY---------------------------------------
	
	public void start()
	{
		if (paused) {return;}
		
		//PLAYER
		player.score.reset();
		
		if (finished)
		{
			finished = false; 
			player.setMoveAllowed(true);
		}
		
		//ENEMIES
		for (Enemy enemy : enemies)
		{enemy.initialize(random, speed);}
		
		//GAME
		board.setSize(PANEL_HEIGHT);		
		moveTimer.start();
		timer.start();
		repaint();
	}
	
	public void pause()
	{
		if (finished)
		{return;}
		
		if (paused)
		{
			player.setMoveAllowed(true);
			moveTimer.start();
			timer.start();
		}

		else 
		{
			player.setMoveAllowed(false);
			moveTimer.stop();
			timer.stop();
		}
		
		paused = !paused;
		repaint();
	}
	
	private void end()
	{	
		finished = true;
		player.setMoveAllowed(false);
		moveTimer.stop();
		timer.stop();
		
		player.score.finalize();
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == moveTimer)
		{update();}
		
		else {player.score.increase();}
	}
	
	private void update()
	{
		for (Enemy enemy : enemies)
		{enemy.move(random);}
		
		if (player.collisionCheck(enemies))
		{end();}
		
		repaint();
	}
	
	//---------------------------------------PAINT---------------------------------------
	
	private static final Color[] lightPalette = 
		{new Color(220,220,220), Color.LIGHT_GRAY, Color.GRAY, Color.GRAY, new Color (220,220,220)};
	
	private static final Color[] darkPalette = 
		{new Color(30,30,45), new Color(75,75,105), new Color(130,130,170), new Color(150,150,200), new Color(220,220,250)};
	
	public void paint (Graphics g)
	{
		Graphics2D g2D = (Graphics2D) g;
		Color[] palette = darkMode ? darkPalette : lightPalette;
		
		//BACKGROUND
		g2D.setPaint(palette[0]);
		g2D.fillRect(0,0,PANEL_WIDTH,PANEL_HEIGHT);
		
		//BOARD
		g2D.setPaint(palette[1]);
		g2D.fillRect(board.X, board.Y, board.width,board.height);
		
		g2D.setPaint(palette[2]);
		g2D.setStroke(new BasicStroke(2));
		g2D.drawRect(board.X, board.Y, board.width,board.height);
		
		//PLAYER
		g2D.setPaint(finished ? Color.green : Player.color);
		g2D.fillRect((int) player.X, (int) player.Y, player.size, player.size);
		
		//ENEMIES
		g2D.setPaint(palette[3]);
		
		for (Enemy enemy : enemies)
		{
			if (enemy.isFilled)
			{g2D.drawRect((int) enemy.X, (int) enemy.Y, enemy.size, enemy.size);}
			else
			{g2D.fillRect((int) enemy.X, (int) enemy.Y, enemy.size, enemy.size);}
		}
		
		//UI
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		paintMainUI(g2D, palette);
		paintUIBoards(g2D, palette);
	}
	
	private void paintMainUI(Graphics2D g2D, Color[] palette)
	{	
		if (paused || finished)
		{
			g2D.setPaint(palette[4]);
			g2D.fillRect((int) (PANEL_HEIGHT/1.55), PANEL_HEIGHT/55, (int) (PANEL_HEIGHT/2.35), PANEL_HEIGHT/10);

			g2D.setPaint(palette[2]);
			g2D.drawRect((int)(PANEL_HEIGHT/1.55), PANEL_HEIGHT/55, (int)(PANEL_HEIGHT/2.35), PANEL_HEIGHT/10);
			
			g2D.setFont(new Font ("Dialog", Font.BOLD, PANEL_HEIGHT/11));
			
			if (finished)
			{
				g2D.setPaint(Player.color);
				g2D.drawString("FINISHED", (int) (PANEL_HEIGHT/1.55), PANEL_HEIGHT/10);
				return;
			}
			
			if (darkMode) {g2D.setPaint(new Color(110,110,150));}
			g2D.drawString("PAUSED", (int) (PANEL_HEIGHT/1.48), PANEL_HEIGHT/10);
		}
	}
	
	private void paintUIBoards(Graphics2D g2D, Color[] palette)
	{
		//BOARDS
		g2D.setPaint(palette[4]);
		g2D.fillRect((int)(PANEL_HEIGHT*1.5), PANEL_HEIGHT/6, PANEL_HEIGHT/6, PANEL_HEIGHT/4);
		g2D.fillRect(PANEL_HEIGHT/20, PANEL_HEIGHT/4, PANEL_HEIGHT/4, PANEL_HEIGHT/2 );

		//OUTLINE
		g2D.setPaint(palette[2]);
		g2D.drawRect(PANEL_HEIGHT/20, PANEL_HEIGHT/4, PANEL_HEIGHT/4, PANEL_HEIGHT/2 );
		g2D.drawRect((int)(PANEL_HEIGHT*1.5), PANEL_HEIGHT/6, PANEL_HEIGHT/6, PANEL_HEIGHT/4);

		//TEXT
		if (darkMode) {g2D.setPaint(new Color(110,110,150));}
		
		g2D.setFont(new Font ("Dialog", Font.BOLD, PANEL_HEIGHT/30));
		g2D.drawString("TIME", (int)(PANEL_HEIGHT*1.51), (int)(PANEL_HEIGHT/4.8));
		g2D.drawString(player.score.getTime()+"s", (int)(PANEL_HEIGHT*1.51), (int)(PANEL_HEIGHT/3.9));
		g2D.drawString(player.score.getBestTime()+"s", (int)(PANEL_HEIGHT*1.51), (int)(PANEL_HEIGHT/2.7));
		g2D.drawString("BEST", (int)(PANEL_HEIGHT*1.51), (int)(PANEL_HEIGHT/3.1));

		g2D.setFont(new Font ("Dialog", Font.BOLD, PANEL_HEIGHT/35));
		g2D.drawString("AVOID THE", PANEL_HEIGHT/11, (int)(PANEL_HEIGHT/3.4));
		g2D.drawString("GREY SQUARES", PANEL_HEIGHT/16, PANEL_HEIGHT/3);

		g2D.drawString("ESC - PAUSE", PANEL_HEIGHT/15, (int)(PANEL_HEIGHT/2.28));
		g2D.drawString("R - RESTART", PANEL_HEIGHT/15, (int)(PANEL_HEIGHT/2.1));
		g2D.drawString("D - DARK", PANEL_HEIGHT/15, (int)(PANEL_HEIGHT/1.95));

		g2D.drawString("RESOLUTION", PANEL_HEIGHT/12, (int)(PANEL_HEIGHT/1.6));
		g2D.drawString("+ INCREASE", PANEL_HEIGHT/15, (int)(PANEL_HEIGHT/1.48));
		g2D.drawString("- DECREASE", PANEL_HEIGHT/15, (int)(PANEL_HEIGHT/1.4));
	}
}