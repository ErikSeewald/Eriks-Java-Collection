# Eriks-Java-Collection
This is a collection of my favorite hobby java projects. The main intention here is to experiment with different computer science concepts.
The main menu provides a small explanation of each project on mouse hover and can be scaled with +/-.

## Notes
- A lot of these projects are quite old as they were my very first programming projects.
- Some of the newer ones are still quite nice, but may experience issues on Windows 11. I only tested them on Windows 10 and Linux (X11 Graphics).
- I recommend [CheeseBreeder](#cheesebreeder), [MusicalGates](#musicalgates), [Automata](#automata), and [TaxCollector](#taxcollector)

## Architecture
- A bunch of self-contained packages with their own setups. Each with their own implementation of [EJC_Game](/GameCollection/src/ejcMain/EJC_Game.java) that calls on some sort of JPanel for rendering.
- The [ejcMain](/GameCollection/src/ejcMain/) package contains the glue that binds these projects together. Just a little main menu and some interfaces that are used across the application. The [EJC_GameHandler](/GameCollection/src/ejcMain/EJC_GameHandler.java) statically stores, constructs, and closes the game classes, contolled by the [MainMenu](/GameCollection/src/ejcMain/menu/MainMenu.java).

## Project descriptions
These descriptions are displayed when hovering over the games in the main menu.

#### Insects
    Observe as the insects try to survive in this maze.
    You can see the current amount of insects in the top left corner.
    Blue areas are sources of water, red ones are food. The number of insects
    changes each round in relation to how many are within these sources.
    Change rate rounds down. (0.01 -> 0  | -0.01 -> -1)

#### Sierpinski
    Every turn there is a random chance for the point to move towards one of the
    3 points of the triangle. Then it leaves a new point halfway on the path to the
    chosen point. Repeating this will slowly create a sierpinski triangle.

#### Pathfind
    Use W,A,S,D to escape the chasers for as many turns as possible.
    The chasers only move when you move and can break obstacles in 3 hits.
    Keys:
    R -- Reset board | T -- Generate new board
    +/- to change screen size | Use the menu bar to input a board seed.

#### ParallelUniverses
    The red point represents your position in the universe.
    The green point represents your position in the parallel universes.
    Move with W,A,S,D and hold down shift before pressing the keys to move faster.
    Hide the parallel universes by pressing H.

#### ReflectionDemo
    Move the ray origin with your mouse held down and rotate it with the mouse wheel.
    Keys:
    1 - Less reflections  |  2 - More reflections
    3 - Less rotation speed  |  4 - More rotation speed

#### MouseDodge
    Move your mouse to dodge the enemies.
    Controls are displayed ingame.

#### Sidescroller
    Outrun the screen and don't fall into the lava. Grab on to ceilings by
    holding space. Use the menu bar to input a level seed.
    Keys:
    Use W,A,S,D to move and press space to jump.
    R -- Restart | T -- Generate new map | ESC -- Pause

#### PixelCollision
    Anything you draw on screen will become a collision object.
    Use the Object Menu in the top left to draw an object into the rectangle,
    save it and then move it with W,A,S,D. Use shift to move faster.
    Use the Pixel Menu to change the size of the pixel grid.
    +/- to change screen size | F - Fly mode | R - Reset | Shift click - Erase pixel

#### ClothSim
    Click anywhere on screen to create a point, CTRL click to lock it in place.
    Click on a point again to select it. When you have two points selected, press
    C to connect them. Start or stop the simulation with S and reset it with R.
    Move point = Press mouse down and move | Cut point = Move over it with shift and
    mouse pressed | Delete last connection = CTRL Z | Save/load layouts - menu bar

#### GravityVectors
    Move gravity points with the mouse held down
    Keys:
    1 - Add another gravity point | 2 - Remove last gravity point
    +/- to change screen size
    G - Activate gradient rendering | N - Turn off arrow normalization

#### BalloonShooting
    I wanted to recreate aspects of the original 'Bloons' while trying to make
    my own sprite renderer. Shoot the balloons using the slingshot with the
    mouse held down. Press the right arrow key to load the next level,
    left for the previous level. R - Restart level | G - Show level grid
    +/- to change resolution

#### RandBattle
    Fighters with random sizes, hp, damage, movement speed and projectile speed
    are generated. They pick a random target and try to shoot it until it is
    dead, then they pick a new one. After some time a new batch is born
    with the genes of the remaining fighters and a bit of mutation. The wait time
    for new batches increases each time. S - Show stats | H - Only show health

#### RandGrowth
    Once you have pressed the start button, press and move your mouse
    across the screen to spawn pixels and watch them try to survive and grow
    according to the rules you set with the UI included in the program.

#### SnakesAndLadders
    A classic game of Snakes And Ladders. Set the amount of players and press 'Start'.
    Once you have pressed on the roll button you can either move your player piece with
    the mouse or you can press on 'Auto Move' and have the piece move for you.
    Try to reach square 53!

#### BombSorting
    This is a minigame inspired by 'Sort or Splode' from Mario 64 DS.
    Sort the bombs by their colors before their countdown reaches zero.
    Move them by dragging your mouse.
    +/- to change screen size

#### InfDungeons
    This is a small dungeon crawler with randomly generated rooms.
    Press SPACE to use your sword. Press E to interact with doors, chests and keys.
    Press Q to drop a bomb. You can see your item counts at the top of the window.
    Blue = open door | Orange = locked door | Purple = chest
    +/- to change screen size | ENTER to enter/exit Debug Mode

#### CheeseBreeder
    Breed the default variants of cheese together to create new types of cheese
    with properties genetically inherited from their parents.
    Use the menu bar to select from a variety of elemental cheeses to breed with.
    Once you have spawned two cheeses with the menu and selected them both by clicking
    on them with your mouse, press b to breed them together. Press r to reset all.

#### TaxCollector
    Run around the map with w,a,s,d and collect tax from homes while evading obstacles.
    In the top right you can see the money you are currently holding and the funds that
    the IRS has available. Do not let the IRS run out of funds! Return the collected tax
    to the IRS Building. Any time you are hit by a car, you lose a lot of money!
    Press e to collect/return, r to restart, g to show the grid and t to pause the timer.

#### Automata
    WARNING: POTENTIAL FLASHING LIGHTS
    An implementation of Slackermanz's Cellular Automata concept.
    Keys: R - Randomize game rules | S - Change cell size (And reset cell grid)
    Click anywhere on the screen to spawn a small patch of active cells. Try randomizing
    rules and repeatedly changing cell size if nothing interesting is happening.

#### MusicalGates
    Create logic gate circuits and make them play 'music'. Add gates with the button bar.
    MOUSE CONTROLS: DRAG - Move gate | SHIFT+DRAG - Connect gates | ALT+CLICK
    - Delete gate | CNTRL+CLICK - Switch state of 'IN' gates (instant in edit mode,
    animated in 'playing' mode). In 'playing' mode (PLAY button) you can randomize the
    sounds of the gates with another button. You can also save and load existing circuits.

#### JuicePoet
    Use the 'Actions' tab in the menu bar to write a poem. If you drag the poem 
    onto the juicer with your mouse, the juicer will extract the juice from your poem
    using an algorithm that takes the text string as input. You can then use the
    menu bar to get a glass and fill it with the juice by dragging the glass onto 
    the juicer. Use the DEL key to delete a glass.

#### PerfectParty
    Compete against the cpu party in assigning policy points to randomly generated
    policies. Use the voters preferences to make smart choices and win the election.
    Each round introduces more policies and voters. How long can you keep up?
    For further explanation of controls and rules, use the 'Info' tab in the game's 
    menu bar. This game is hard but not impossible.