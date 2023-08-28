package Main;

import java.util.ArrayList;
import bloonShoot.EJC_BloonShoot;
import bomb_sorting.EJC_BombSorting;
import cheeseBreeder.EJC_CheeseBreeder;
import clothSim.EJC_ClothSim;
import gravityVectors.EJC_GravityVectors;
import infdungeons.EJC_InfDungeons;
import insects.EJC_Insects;
import mouseDodge.EJC_MouseDodge;
import parallelUniverses.EJC_ParallelUniverses;
import particles.EJC_Particles;
import pathfindGame.EJC_Pathfind;
import pixelCollision.EJC_PixelCollision;
import randBattle.EJC_RandBattle;
import randGrowth.EJC_RandGrowth;
import reflectionDemo.EJC_ReflectionDemo;
import sidescroller.EJC_Sidescroller;
import sierpinski.EJC_Sierpinski;
import snakesAndLadders.EJC_SnakesAndLadders;
import sudoku.EJC_Sudoku;
import taxCollector.EJC_TaxCollector;

public class EJC_Factory
{
	private static final ArrayList<Class<? extends EJC_Interface>> games = new ArrayList<>();
	static
	{
		games.add(EJC_Insects.class); 
		games.add(EJC_Particles.class); 
		games.add(EJC_Sierpinski.class); 
		games.add(EJC_Sudoku.class);
		
		games.add(EJC_Pathfind.class); 
		games.add(EJC_ParallelUniverses.class); 
		games.add(EJC_ReflectionDemo.class); 
		games.add(EJC_MouseDodge.class);
		
		games.add(EJC_Sidescroller.class); 
		games.add(EJC_PixelCollision.class); 
		games.add(EJC_ClothSim.class); 
		games.add(EJC_GravityVectors.class);
		
		games.add(EJC_BloonShoot.class); 
		games.add(EJC_RandBattle.class); 
		games.add(EJC_RandGrowth.class); 
		games.add(EJC_SnakesAndLadders.class);
		
		games.add(EJC_BombSorting.class); 
		games.add(EJC_InfDungeons.class); 
		games.add(EJC_CheeseBreeder.class); 
		games.add(EJC_TaxCollector.class);
	}
    
    public static EJC_Interface buildGame(int index) 
    {
    	if (index >= games.size()) {return null;}
        try 
        {
			return games.get(index).getConstructor().newInstance();
		} 
        catch (Exception e) {System.out.println("FATAL ERROR: Failed to construct game instance");}
        return null;
    }
}
