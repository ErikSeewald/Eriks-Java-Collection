package PathfindGame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import Main.MainMenu;
import Main.WindowEventHandler;


public class Pathfind extends JFrame implements ActionListener
{
	private static final long serialVersionUID = 386670748457681736L;

	private JMenuItem loadItem;
	private JMenuItem saveItem;
	
	private PathfindNormal panel;
	
	public Pathfind()
	{			
		this.setIconImage(MainMenu.img.getImage());
		this.setTitle("Pathfind");
		panel = new PathfindNormal();
		
		this.setResizable(false);
		
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		
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
	
	public void start(WindowEventHandler eventHandler)
	{this.addWindowListener(eventHandler);}

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
					chaser = panel.getChasers();
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
}
