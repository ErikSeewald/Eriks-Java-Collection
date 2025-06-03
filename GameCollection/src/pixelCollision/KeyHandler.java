package pixelCollision;
import java.util.HashSet;

public class KeyHandler 
{
	private HashSet<Integer> pressedKeys;
	private PixelCollisionPanel panel;
	
	private int fallingSpeed;
	private int timeUntilFall;
	private boolean jumpAllowed = true;
	private boolean flyMode = false;
	
	private MovableObject movObj;
		
	KeyHandler(HashSet<Integer> pressedKeys, PixelCollisionPanel panel)
	{this.pressedKeys = pressedKeys; this.panel = panel; this.movObj = panel.getMovableObject();}
	
	public void flyMode() {flyMode = !flyMode;}
	
	public void update()
	{
		int x = 0, PIXEL_SIZE = panel.getPixelSize();
		
		if (pressedKeys.contains(68)) {x = PIXEL_SIZE;} 	//D
		if (pressedKeys.contains(65)) {x = -PIXEL_SIZE;} 	//A
		
		int[] vec = flyMode ? flyModeKeys(x, PIXEL_SIZE) : normalModeKeys(x, PIXEL_SIZE);
		
		int speed = (5 - PIXEL_SIZE)+1;
		for (int i = 0; i < speed; i++)
		{movObj.move(vec[0],vec[1],vec[2]);}
		
		panel.repaint();
	}
	
	private int[] flyModeKeys(int x, int PIXEL_SIZE)
	{
		int y = 0;	
		if (pressedKeys.contains(87)) {y = -PIXEL_SIZE;} 		//W
		if (pressedKeys.contains(83)) {y = PIXEL_SIZE;} 		//S
	
		return new int[] {x,y,0};
	}
	
	private int[] normalModeKeys(int x, int PIXEL_SIZE)
	{
		int y = 0;
		
		if (pressedKeys.contains(32) && jumpAllowed) //SPACE
		{
			fallingSpeed = -PIXEL_SIZE; 
			jumpAllowed = false; timeUntilFall = 15; 
		}
		 
		if (timeUntilFall > 0) {timeUntilFall--;}
		if (timeUntilFall == 5) {fallingSpeed = 0;}	//for a smoother transition in fallingSpeed
		else if (timeUntilFall == 0) {fallingSpeed = PIXEL_SIZE;}
		
		if (movObj.collision) {jumpAllowed = true;}
		
		return new int[] {x,y,fallingSpeed};
	}
}