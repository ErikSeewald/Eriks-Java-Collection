package JumpAndRun;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
//import javax.swing.Timer;
//import java.awt.Font;
//import java.awt.RenderingHints;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;

public class JumpAndRunPanel extends JPanel //implements ActionListener
{
	private static final long serialVersionUID = 6717353794276866444L;

	int PANEL_HEIGHT = 600;
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
