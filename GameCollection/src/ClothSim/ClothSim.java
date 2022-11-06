package ClothSim;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.Timer;

import Main.MainMenu;
import Main.WindowEventHandler;

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
		this.setIconImage(MainMenu.img.getImage());
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
	{timer.stop();}
}
