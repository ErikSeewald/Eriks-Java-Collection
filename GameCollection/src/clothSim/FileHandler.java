package clothSim;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;

import javax.swing.JFileChooser;

public class FileHandler 
{	
	public static void saveLayout(VerletSimulation sim)
	{
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File("C:\\Users\\Erik\\eclipse-workspace\\Eriks-Java-Collection\\GameCollection\\Assets\\ClothLayouts"));
		
		if (fileChooser.showSaveDialog(null) != JFileChooser.APPROVE_OPTION) 
		{return;}
		File file = new File(fileChooser.getSelectedFile().getAbsolutePath());

		try 
		{
			FileWriter fw = new FileWriter(file,false);

			//POINTS
			fw.write(""+sim.points.size()+"|");

			DecimalFormat df = new DecimalFormat("#.##");   //ROUND THE DOUBLES TO TWO DECIMAL PLACES    
			for (Point point : sim.points)
			{
				//"X,Y,ISLOCKED;"
				String isLocked = point.isLocked ? "t" : "f";
				fw.write(Double.valueOf(df.format(point.x))+","+ Double.valueOf(df.format(point.y))+ ","+ isLocked+";");
			}

			//CONNECTORS
			fw.write("|"+sim.connectors.size()+"|");

			for (Connector connector : sim.connectors)
			{
				int aIndex = sim.points.indexOf(connector.pointA);
				int bIndex = sim.points.indexOf(connector.pointB);

				//"I1,I2"
				fw.write(aIndex+","+ bIndex + ",;"); //",; for compatibility with old format
			}

			fw.close();
		}
		catch (IOException e1) {e1.printStackTrace();}
	}
	
	public static void loadLayout(VerletSimulation sim)
	{
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File("C:\\Users\\Erik\\eclipse-workspace\\Eriks-Java-Collection\\GameCollection\\Assets\\ClothLayouts"));
		
		if (fileChooser.showSaveDialog(null) != JFileChooser.APPROVE_OPTION) 
		{return;}
		File file = new File(fileChooser.getSelectedFile().getAbsolutePath());

		try
		{	
			BufferedReader reader = new BufferedReader(new FileReader(file));
			loadString(reader.readLine(), sim);
			reader.close();
		}
		catch (IOException e1) {e1.printStackTrace();}
	}

	public static void loadString(String line, VerletSimulation sim)
	{
		sim.restart();
		
		//POINTS
		int start = 0, end = 0;
		while (line.charAt(end) != '|') {end++;}

		int pointAmount = Integer.parseInt(line.substring(start,end));

		for (int i = 0; i < pointAmount; i++)
		{
			end++;
			start = end;
			while (line.charAt(end) != ',') {end++;}
			float x = Float.parseFloat(line.substring(start,end));

			end++;
			start = end;
			while (line.charAt(end) != ',') {end++;}
			float y = Float.parseFloat(line.substring(start,end));

			Point point = new Point(x, y);

			end++;
			start = end;
			while (line.charAt(end) != 'f' && line.charAt(end) != 't') {end++;}
			point.isLocked = line.charAt(end) == 't';

			sim.points.add(point);

			while (line.charAt(end) != ';') {end++;}
		}

		//CONNECTORS
		end+=2;
		start = end;
		while (line.charAt(end) != '|') {end++;}
		int connectorCount = Integer.parseInt(line.substring(start,end));

		for (int i = 0; i < connectorCount; i++)
		{
			end++;
			start = end;
			while (line.charAt(end) != ',') {end++;}
			sim.prevSelectedPoint = sim.points.get(Integer.parseInt(line.substring(start,end)));

			end++;
			start = end;
			while (line.charAt(end) != ',') {end++;}
			sim.selectedPoint = sim.points.get(Integer.parseInt(line.substring(start,end)));

			sim.connect();

			while (line.charAt(end) != ';') {end++;}
		}

		System.gc();
	}
			
	public static final String example_layout =
	"12|480.0,123.0,t;444.0,122.0,f;411.0,122.0,f;380.0,121.0,f;350.0,120.0,f;324.0,119.0,f;297.0,118.0,"
	+ "f;272.0,117.0,f;245.0,116.0,f;202.0,86.0,f;150.0,114.0,f;200.0,140.0,f;|14|0,1,;1,2,;2,3,;3,4,;4,5,"
	+ ";5,6,;6,7,;7,8,;8,9,;9,10,;10,11,;11,8,;11,9,;10,8,;";
}