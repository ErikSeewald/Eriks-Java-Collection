package musicalLogicGates.circuit;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;

import musicalLogicGates.gates.Gate;
import musicalLogicGates.gates.Gate.GateType;

/**
 * Manages file saving and loading for circuits.
 */
public class FileManager 
{
	/** Structure:
	 * {GateType, x, y, input1 index in array, input2 index in array}, {...}, ... /
	 */
	
	/**
	 * Handles the file choose dialogue and file writing for saving a circuit.
	 * 
	 * @param gates the {@code List<Gate>} circuit to save
	 */
	public static void saveCircuit(List<Gate> gates)
	{
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File("C:\\Users\\Erik\\eclipse-workspace\\Eriks-Java-Collection\\GameCollection\\Assets\\Circuits"));
		
		if (fileChooser.showSaveDialog(null) != JFileChooser.APPROVE_OPTION) 
		{return;}
		File file = new File(fileChooser.getSelectedFile().getAbsolutePath());

		try 
		{
			FileWriter fw = new FileWriter(file,false);
			
			for (Gate gate : gates)
			{
				fw.write("{");
				
				fw.write(gate.getType().toString() + ",");
				fw.write(gate.x + ",");
				fw.write(gate.y + ",");
				
				int index;
				index = gates.indexOf(gate.getInput1());
				fw.write(index + ",");
				
				index = gates.indexOf(gate.getInput2());
				fw.write(index + ",");
				
				fw.write("}");
			}
			
			fw.write("/");
			fw.close();
		}
		catch (IOException e1) {e1.printStackTrace();}
	}
	
	/**
	 * Handles the file choose dialogue for loading a saved circuit. If successful, returns the loaded circuit as a
	 * {@code List<Gate>}.
	 * 
	 * @return the loaded {@code List<Gate>}
	 */
	public static List<Gate> loadCircuit()
	{
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File("C:\\Users\\Erik\\eclipse-workspace\\Eriks-Java-Collection\\GameCollection\\Assets\\Circuits"));
		
		if (fileChooser.showSaveDialog(null) != JFileChooser.APPROVE_OPTION) 
		{return List.of();}
		
		File file = new File(fileChooser.getSelectedFile().getAbsolutePath());

		try
		{	
			BufferedReader reader = new BufferedReader(new FileReader(file));
			List<Gate> gates = loadString(reader.readLine());
			reader.close();
			return gates;
		}
		catch (IOException e1) {e1.printStackTrace();}
		return List.of();
	}
	
	/**
	 * Creates a {@code List<Gate>} from a given save {@link String}, given that it is properly formatted.
	 * 
	 * @param line the {@link String} to load from
	 * @return the loaded {@code List<Gate>}
	 */
	public static List<Gate> loadString(String line)
	{
		List<Gate> gates = new ArrayList<>();

		int startOfList = 0;
		while (line.charAt(startOfList) != '{') {startOfList++;}
		
		int endOfList = startOfList;
		while (line.charAt(endOfList) != '/') {endOfList++;}
		
		//BUILD ALL ITEMS FIRST, THEN ASSIGN THEM THEIR INPUTS (BECAUSE OF USING ARRAY INDICES)
		int start = startOfList;
		int end = start;
		int gateIndex = 0;
		while (end < endOfList)
		{
			//TYPE
			while (start < endOfList && line.charAt(start) != '{') {start++;}
			if (start >= endOfList) {break;}
			start++;
			end = start;
			while (line.charAt(end) != ',') {end++;}
			String typeString = line.substring(start, end);
			gates.add(CircuitManager.createGate(GateType.valueOf(typeString)));
			
			//X
			end++;
			start = end;
			while (line.charAt(end) != ',') {end++;}
			int x = Integer.parseInt(line.substring(start, end));
			gates.get(gateIndex).x = x;
			
			//Y
			end++;
			start = end;
			while (line.charAt(end) != ',') {end++;}
			int y = Integer.parseInt(line.substring(start, end));
			gates.get(gateIndex).y = y;
			
			gateIndex++;
		}
		
		//ADD INPUT GATES BY INDEX
		start = startOfList;
		end = start;
		gateIndex = 0;
		while (end < endOfList)
		{
			while (start < endOfList && line.charAt(start) != '{') {start++;}
			if (start >= endOfList) {break;}
			
			//INPUT 1
			for (int i = 0; i < 3; i++) 
			{
				while (line.charAt(start) != ',') {start++;}
				start++;
			}
			end = start;
			while (line.charAt(end) != ',') {end++;}

			int inputIndex = Integer.parseInt(line.substring(start, end));
			if (inputIndex >= 0 && inputIndex < gates.size())
			{
				gates.get(gateIndex).setInput1(gates.get(inputIndex));
			}
			
			//INPUT 2
			end++;
			start = end;
			while (line.charAt(end) != ',') {end++;}
			inputIndex = Integer.parseInt(line.substring(start, end));
			if (inputIndex >= 0 && inputIndex < gates.size())
			{
				gates.get(gateIndex).setInput2(gates.get(inputIndex));
			}
			
			gateIndex++;
		}
		
		
		return gates;
	}
	
	//EXAMPLE CIRCUIT
	public static final String full_adder =
			"{IN,88,185,-1,-1,}{IN,47,269,-1,-1,}{IN,54,399,-1,-1,}{XOR,380,147,0,1,}{XOR,618,182,3,2,}"
			+ "{AND,513,427,3,2,}{AND,380,300,0,1,}{OR,828,260,6,5,}{OUT,1019,172,4,-1,}{OUT,1003,304,7,-1,}/";
}
