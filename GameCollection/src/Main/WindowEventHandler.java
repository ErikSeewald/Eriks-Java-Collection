package Main;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.URISyntaxException;

import ClothSim.ClothSim;
import Coordinates3D.Coordinates3DFrame;
import Insects.InsectsPanel;
import JumpAndRun.JumpAndRun;
import MouseDodge.MouseDodgePanel;
import ParallelUniverses.ParallelUniverses;
import Particles.ParticlesPanel;
import PathfindGame.Pathfind;
import PixelCollision.PixelCollision;
import Sierpinski.SierpinskiSlow;

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
			case "Pathfind": Pathfind.stop(); index = 5; System.out.println("huh"); break;
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
