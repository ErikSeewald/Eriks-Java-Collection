package ejcMain;

import java.util.ArrayList;

import automata.EJC_Automata;
import bloonShoot.EJC_BloonShoot;
import bombSorting.EJC_BombSorting;
import cheeseBreeder.EJC_CheeseBreeder;
import clothSim.EJC_ClothSim;
import gravityVectors.EJC_GravityVectors;
import infdungeons.EJC_InfDungeons;
import insects.EJC_Insects;
import juicePoet.EJC_JuicePoet;
import mouseDodge.EJC_MouseDodge;
import musicalLogicGates.EJC_MusicalLogicGates;
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
import taxCollector.EJC_TaxCollector;

public class EJC_GameHandler 
{
	public static int maxGameCount = 24;
	public static int getGameCount() {return games.size();}
	
	private static final ArrayList<Class<? extends EJC_Game>> games = new ArrayList<>();
	static
	{
		games.add(EJC_Insects.class); 
		games.add(EJC_Particles.class); 
		games.add(EJC_Sierpinski.class);
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
		games.add(EJC_Automata.class);
		
		games.add(EJC_MusicalLogicGates.class);
		games.add(EJC_JuicePoet.class);
	}
	
	private static EJC_WindowEventHandler eventHandler = new EJC_WindowEventHandler();
	private static boolean[] gameOpened = new boolean[24];
	
	public static void startGame(int index)
    {
    	if (index >= games.size() || gameOpened[index]) {return;}
        try 
        {
			games.get(index).getConstructor().newInstance().start(eventHandler);
			gameOpened[index] = true;
		} 
        catch (Exception e) {System.out.println("FATAL ERROR: Failed to construct game instance"); e.printStackTrace();}
    }
	
	public static void closeGame(EJC_Game game)
	{
		game.stop();
		gameOpened[games.indexOf(game.getClass())] = false;
	}
}