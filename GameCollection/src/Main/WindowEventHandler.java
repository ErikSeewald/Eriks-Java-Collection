package Main;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.URISyntaxException;

import BloonShooting.BloonShooting;
import ClothSim.ClothSim;
import Insects.InsectsPanel;
import JumpAndRun.JumpAndRun;
import MouseDodge.MouseDodgePanel;
import ParallelUniverses.ParallelUniverses;
import Particles.ParticlesPanel;
import PathfindGame.Pathfind;
import PixelCollision.PixelCollision;
import RandBattle.RandBattle;
import RandGrowth.RandGrowth;
import Sierpinski.SierpinskiSlow;
import SnakesAndLadders.SnakesAndLadders;

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
			case "Insects": InsectsPanel.stop(); index = 0; break;
			case "Particles" : ParticlesPanel.stop();index = 1; break;
			case "Sierpinski": SierpinskiSlow.stop();index = 2; break;
			case "Sudoku": index = 3; break;
			case "Pathfind": Pathfind.stop(); index = 4; break;
			case "Parallel Universes": ParallelUniverses.stop(); index = 5; break;
			case "Reflection Demo": index = 6; break;
			case "Mouse Dodge": MouseDodgePanel.stop(); index = 7; break; 
			case "Sidescroller": JumpAndRun.stop(); index = 8; break;
			case "Speedrun": JumpAndRun.stop(); index = 9; break;
			case "Pixel Collision": PixelCollision.stop(); index = 10; break;
			case "Cloth Sim": ClothSim.stop(); index = 11; break;
			case "Gravity Vectors": index = 12; break;
			case "Bloon Shooting": BloonShooting.stop(); index = 13; break;
			case "Rand Battle": RandBattle.stop(); index = 14; break;
			case "Rand Growth": RandGrowth.stop(); index = 15; break;
			case "Snakes and Ladders": SnakesAndLadders.stop(); index = 16; break;
		}
		
		if (index != -1)
		{MainMenu.hasWindowOpen[index] = false;}
		
		try {MainMenu.restart();} 
		catch (IOException e1) {e1.printStackTrace();} catch (URISyntaxException e1) {e1.printStackTrace();}
	  }
	
}
