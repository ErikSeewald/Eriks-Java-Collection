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

public class JumpAndRunPanel extends JPanel //implements ActionListener
{
	private static final long serialVersionUID = 6717353794276866444L;

	//PANEL
	private static final int PANEL_HEIGHT = 600;
	private static final int PANEL_WIDTH = 900;
	
	//MAP
	private byte[] gameMap = new byte[10000];
	private int[][] mapElementStartingLoc = new int[gameMap.length][2]; //lowest X and Y of the Map Element
	
	private static final int elementWidth = 30;
	private static final int elementsPerFrame = 30; 
	private static final int standardHeight = 60;	//used to calculate the Elements individual height
	private int[] mapElementHeight = new int[gameMap.length];
	
	private int mapStartingPoint = 0; //screen draws elements from mapStartingPoint to mapEndPoint	
	private int mapEndPoint = elementsPerFrame;
	
	int offset = 0;		//used for pushing the elements to the left -> scrolling
	int offsetSlot = offset /elementWidth;	// difference with prevoffsetSlot used to decrease or increase the two map starting points
	
	//ELEMENT
	private class Element
	{
		
	}
	
	private enum Layouts
	{
		smallCol,
		mediumCol,
		bigCol1,
		bigCol2,
		flyBlock,
		lowTunnel,
		highTunnel;
	}
	/*    smallCol 	| mediumCol	|  bigCol1	|  bigCol2	|  flyBlock	| lowTunnel	| highTunnel
	 *				|			|			|			|			|			|	||
	 *				|			|			|			|			|			|
	 *				|			|			|			|			|	||		|
	 * 				|			|			|			|	||		|	||		|	||
	 * 				|			|			|			|			|			|
	 * 				|			|	||		|	||		|			|			|
	 * 				|	||		|	||		|	||		|			|			|
	 *  	||		|	||		|	||		|	||		|			|	||		|
	 */
	
	private int[] layoutChances = new int[8]; //Element 0 means "No Element"
	private int hitElementNum;		//which of the 7 possible wall layouts needs to be checked for collision
	
	//PLAYER
	class Player
	{
		int x,y;
		int prevY;
		
		int slot; //which slot is the player in respect to the screen
		
		boolean beyondStartingPos = false;
		boolean onGround = false;
		boolean jumpAllowed = false;
		
		final int size = 30;
	}
	Player player = new Player();
	
	
	//SAVE FILES
	private int charStart;
	private int charEnd;
	
	//COLORS
	private static final Color backgroundColor = new Color(55,55,70);
	private static final Color elementColor = new Color(20,20,25);
	private static final Color playerColor = new Color(120,210,120);
	private static final Color lavaColor = new Color(200,50,50);
	
	//OTHERS
	boolean paused = false;
	private Random random = new Random();
	int scrollingSpeed;
	
	private class RestartOperations
	{
		final static boolean newMap = true;
		final static boolean restart = false;
		final static boolean fileLoad = true;
		final static boolean noFileLoad = false;
	}
	
	JumpAndRunPanel(int scrollingSpeed)
	{
		this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		this.scrollingSpeed = scrollingSpeed;
		
		//INITIALIZING LAYOUT CHANCES
		if (scrollingSpeed == 0)
		{
			layoutChances[0] = 5;
			layoutChances[1] = 45;
			layoutChances[2] = 55;
			layoutChances[3] = 65;
			layoutChances[4] = 80;
			layoutChances[5] = 90;
			layoutChances[6] = 95;
			layoutChances[7] = 100;
		}
		
		else
		{
			layoutChances[0] = 50;
			layoutChances[1] = 60;
			layoutChances[2] = 70;
			layoutChances[3] = 80;
			layoutChances[4] = 85;
			layoutChances[5] = 90;
			layoutChances[6] = 95;
			layoutChances[7] = 100;
		}
		
		start(RestartOperations.newMap, RestartOperations.noFileLoad);
	}
	
	public void start(boolean newMap, boolean fileLoad)
	{
		if (!fileLoad)
		{
			player.x = 0;
			player.y = 10;
			offset = 0;
			mapStartingPoint = 0;
			mapEndPoint = elementsPerFrame;
			player.beyondStartingPos = false;
		}
		
		player.slot = player.x / elementWidth;
		offsetSlot = offset /elementWidth;
			
		if (newMap)
		{makeMap(gameMap);}
		
		setMapValues();	
	}
	
	public void paint(Graphics g)
	{
		Graphics2D g2D = (Graphics2D) g;
		
		//BACKROUND
		g2D.setPaint(backgroundColor);
		g2D.fillRect(0, 0, PANEL_WIDTH, PANEL_HEIGHT);
		
		//ELEMENTS
		g2D.setPaint(elementColor);
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
		g2D.setPaint(playerColor);
		g2D.fillRect(player.x, player.y, player.size, player.size);
		
		//LAVA
		g2D.setPaint(lavaColor);
		g2D.fillRect(0, PANEL_HEIGHT-(standardHeight/2), PANEL_WIDTH, standardHeight/2);
		
		//UI
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2D.setFont(new Font("", Font.BOLD, PANEL_HEIGHT/10));
		
		String string = "";
		if (paused) {string = "PAUSED";}
		g2D.drawString(string, PANEL_WIDTH /38, PANEL_HEIGHT/6);
	}
	
	public void movePlayer(int x, int y)
	{
		player.prevY = player.y;
		player.y+= y;
		
		player.x+= (x - scrollingSpeed);
		
		player.slot = player.x / elementWidth;
		
		int prevOffestSlot = offsetSlot;
		
		if (scrollingSpeed == 0)
		{
			if (player.slot > 15)  //on the right of the screen -> scroll further
			{
				offset+=x;
				player.x-=x;		//keep the player at the same position relative to the screen
				player.beyondStartingPos = true;
			}
			
			else if (player.beyondStartingPos && player.slot < 10) //on the left of the screen -> scroll back
			{
				offset+=x;
				player.x-=x;
			}
			
			if (mapStartingPoint == 0) {player.beyondStartingPos = false;}
		}
		
		offsetSlot = offset /elementWidth;
		if (offsetSlot > prevOffestSlot) {mapEndPoint++; mapStartingPoint++;}
		else if (offsetSlot < prevOffestSlot) {mapEndPoint--; mapStartingPoint--;}
		
		//remove any changes to playerY of this round for better colision detection, then add it back
		player.y-=y;
		if (hitWall(x, x)) {player.x-=x;}		
		player.y+=y;
		
		if (hitWall(y, -1)) {player.y-=y;}		//hit bottom
		else if (hitWall(y, 0)) {player.y-=y;}	//hit top
		
		if (player.y - player.prevY == 0) {player.onGround = true;}
		else {player.onGround = false;}
		
		//DEATH CHECK
		if (player.y >= PANEL_HEIGHT-(standardHeight/2) || player.x < 0) {start(RestartOperations.restart, RestartOperations.noFileLoad);}

		repaint();
	}
	
	public boolean hitWall(int x, int factor)
	{
		int mapElement = 0;
		if (factor > 0) {mapElement = (player.x+offset+player.size-x)/elementWidth;} //element touching the right corner of the player
		if (factor < 0) {mapElement = (player.x+offset)/elementWidth;} //element touching the left corner of the player
		if (factor == 0) {mapElement = (player.x+offset+player.size-1)/elementWidth;} //element touching the right corner of the player but without horizontal movment
	
		if (mapElement < 0 || mapElement >= gameMap.length) {mapElement = 0;}
		if (gameMap[mapElement] == 0) {return false;} //dont try to calculate any elements past the specified array length
		
		hitElementNum = gameMap[mapElement];
		
		//CHECK NEXT ELEMENT
		int[] wallValues = getMapValues(hitElementNum);
		for (int i = 0; i < wallValues.length; i++)
		{
			if (i%2 == 1) {} //skip over every second iteration because we already check two values at once
			else if 
			(player.y > wallValues[i] && player.y < wallValues[i+1] 
			|| player.y+player.size > wallValues[i] && player.y+player.size < wallValues[i+1] 
			|| player.y == wallValues[i] && player.y+player.size == wallValues[i+1]) //detecting just a single block with the same size as the player
			{return true;}
		}	
		return false;	
	}
	
	//MAP
	public void makeMap(byte[] map)
	{
		for (int i = 1; i < map.length; i++)
		{
			
			int rand = random.nextInt(100);
			
			if (map[i-1] > 4 && random.nextBoolean()) {map[i] = (byte) (random.nextInt(2)+4);}
			//if the last one was floating, there is a 50% change the next one will be too	
			
			else if (rand < layoutChances[0]) {map[i] = 0;}
			else if (rand < layoutChances[1]) {map[i] = 1;}
			else if (rand < layoutChances[2]) {map[i] = 2;}
			else if (rand < layoutChances[3]) {map[i] = 3;}
			else if (rand < layoutChances[4]) {map[i] = 4;}
			else if (rand < layoutChances[5]) {map[i] = 5;}
			else if (rand < layoutChances[6]) {map[i] = 6;}
			else if (rand < layoutChances[7]) {map[i] = 7;}	
			
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
				
				fw.write("|"+player.x+","+player.y+","+ mapStartingPoint+ ","+ mapEndPoint+ ","+ offset+","+ player.beyondStartingPos+",");
				
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
				player.x = Integer.parseInt(text);
				
				text = setVariable(data);
				player.y = Integer.parseInt(text);
				
				text = setVariable(data);
				mapStartingPoint = Integer.parseInt(text);
				
				text = setVariable(data);
				mapEndPoint = Integer.parseInt(text);
				
				text = setVariable(data);
				offset = Integer.parseInt(text);
				
				player.beyondStartingPos = data[charEnd+2] == 't';
				
				start(RestartOperations.restart, RestartOperations.fileLoad);
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
}
