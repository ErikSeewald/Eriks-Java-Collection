package Main;

public enum GameTitles {
	
	Insects
	(
			new String[] 
			{
					"INSECTS",
					"Observe as the insects try to survive in this maze",
					"You can see the current amount of insects in the top left corner.",
					"Blue areas are sources of water, red ones are food. The number of insects",
					"changes each round in relation to how many are within these sources.",
					"Change rate rounds down. (0.01 -> 0  | -0.01 -> -1)",
			}
	),
	
	Particles
	(
			new String[] 
			{
					"PARTICLES",
					"Press down on your mouse to draw on the particles.",
					"Keys:",
					"R - Reset",
					"",
					"",
			}
	),
	
	Sierpinski
	(
			new String[] 
			{
					"SIERPINSKI",
					"Every turn there is a random chance for the point to move towards one of the",
					"3 points of the triangle. Then it leaves a new point halfway on the path to the",
					"chosen point. Repeating this will slowly create a sierpinski triangle.",
					"",
					"",
			}
	),
	
	Sudoku
	(
			new String[] 
			{
					"SUDOKU",
					"Use your mouse to click on a square, then type in a number.",
					"Keys:",
					"S -- Solve | R -- Reset",
					"+/- -- Change screen size",
					"",
			}
	),
	
	Pathfind
	(
			new String[] 
			{
					"PATHFIND",
					"Use W,A,S,D to escape the chasers for as many turns as possible.",
					"The chasers only move when you move and can break obstacles in 3 hits.",
					"Keys:",
					"R -- Reset board | T -- Generate new board",
					"+/- -- Change screen size | Use the menu bar to input a board seed.",
			}
	),
	
	ParralelUniverses
	(
			new String[] 
			{
					"PARALLEL UNIVERSES",
					"The red point represents your position in the universe.",
					"The green point represents your position in the parallel universes.",
					"Move with W,A,S,D and hold down shift before pressing the keys to move faster.",
					"Hide the parallel universes by pressing H",
					"",
			}
	),
	
	ReflectionDemo
	(
			new String[] 
			{
					"REFLECTION DEMO",
					"Move the ray origin with your mouse held down and rotate it with the mouse wheel.",
					"Keys:",
					"1 - Less reflections  |  2 - More reflections",
					"3 - Less rotation speed  |  4 - More rotation speed",
					"",
			}
	),
	
	MouseDodge
	(
			new String[] 
			{
					"MOUSE DODGE",
					"Move your mouse to dodge the enemies.",
					"Press F to switch dark mode on or off.",
					"Other controls are displayed ingame.",
					"",
					"",
			}
	),
	
	Sidescroller
	(
			new String[] 
			{
					"SIDESCROLLER",
					"Outrun the screen and don't fall into the lava. Grab on to ceilings by",
					"holding space. Use the menu bar to input a level seed.",
					"Keys:",
					"Use W,A,S,D to move and press space to jump.",
					"R -- Restart | T -- Generate new map | ESC -- Pause",
			}
	),
	
	PixelCollision
	(
			new String[] 
			{
					"PIXEL COLLISION",
					"Anything you drawn on screen will become a collision object.",
					"Use the Object Menu in the top left to drawn an object into the rectangle that",
					"appears, save it and then move it with W,A,S,D. Use shift to move faster.",
					"Use the Pixel Menu to change the size of the pixel grid",
					"+/- - Change screen size| F - Fly mode | R - Reset | Shift click - Erase pixel",
			}
	),
	
	ClothSim
	(
			new String[] 
			{
					"CLOTH SIM",
					"Click anywhere on screen to create a point, CTRL click to lock it in place.",
					"Click on a point again to select it. When you have two points selected, press",
					"C to connect them. Start or stop the simulation with S and reset it with R.",
					"Move point = Press mouse down and move | Cut point = Hold shift, press down and then move",
					"over it | Delete last connection = CTRL Z | Save/load layouts with the menu bar",
			}
	),
	
	GravityVectors
	(
			new String[] 
			{
					"GRAVITY VECTORS",
					"Move gravity points with the mouse held down",
					"Keys:",
					"1 - Add another gravity point | 2 - Remove last gravity point",
					"+/- - Change screen size",
					"G - Activate gradient rendering | N - Turn off arrow normalization",
			}
	),
	
	BalloonShooting
	(
			new String[] 
			{
					"BALLOON SHOOTING",
					"I wanted to recreate some aspects of the original bloons game in Java with",
					"my own sprite renderer. Shoot the balloons using the slingshot with the",
					"mouse held down. Press the right arrow key to load the next level,",
					"left for the previous level. R - Restart level | G - Show level grid",
					"Use shift press to move the entire slingshot, not just the projectile."
			}
	),
	
	RandBattle
	(
			new String[] 
			{
					"RAND BATTLE",
					"NPCs with random sizes, hp, damage, movement speed and projectile speed",
					"are generated. They pick a random target and try to shoot it until it is",
					"dead, then they pick a new one. Who will be victorious?",
					"Keys:",
					"R - Restart | S - Show stats | H - Only show Health",
			}
	),
	
	RandGrowth
	(
			new String[] 
			{
					"RAND GROWTH",
					"Once you have pressed the start button, press and move your mouse",
					"across the screen to spawn pixels and watch them try to survive and grow",
					"according to the rules you set with the UI included in the program.",
					"",
					"",
			}
	),
	
	SnakesAndLadders
	(
			new String[] 
			{
					"SNAKES AND LADDERS",
					"A classic game of Snakes And Ladders. Set the amount of players and press 'Start'.",
					"Once you have pressed on the roll button you can either move your player piece with",
					"the mouse, or you can press on Auto Move and have the piece move for you.",
					"Try to reach square 53!",
					"",
			}
	);

	String[] guide = new String[6];
	
	GameTitles(String[] str)
	{this.guide = str;}
}