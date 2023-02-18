package pixelCollision;

public class MovableObject 
{
	int pixelCount;
	int[] pixelsX, pixelsY;
	
	boolean collision = false;
	
	private PixelCollisionPanel panel;
	
	MovableObject(PixelCollisionPanel panel)
	{
		this.panel = panel;
		this.pixelCount = 1; 
		pixelsX = new int[] {panel.getPixelSize()};
		pixelsY = new int[] {panel.getPixelSize()};
	}
	
	private void initPixels(int pixelCount)
	{
		this.pixelCount = pixelCount;
		pixelsX = null; pixelsY = null; System.gc();
		pixelsX = new int[pixelCount];
		pixelsY = new int[pixelCount];
	}
	
	public void save()
	{
		int creationGridSize = panel.getCreationGridSize();
		int PIXEL_SIZE = panel.getPixelSize();
		
		//count the amount of pixels
		int pixelCount = 0;
		for (int x = 2; x < creationGridSize+2; x++)
		{
			for (int y = 2; y < creationGridSize+2; y++)
			{
				if (panel.isPixelSolid(x, y)) {pixelCount++;}
			}
		}
		initPixels(pixelCount);

		//project the pixels into screen coordinates
		int index = 0;
		for (int x = 2; x < creationGridSize+2; x++)
		{
			for (int y = 2; y < creationGridSize+2; y++)
			{
				if (!panel.isPixelSolid(x, y))
				{continue;}
				
				pixelsX[index] = x*PIXEL_SIZE;
				pixelsY[index] = y*PIXEL_SIZE;
				index++;
			} 
		}
	}
	
	public void move(int x, int y, int fallingSpeed)
	{	
		int PANEL_SIZE = panel.getPanelSize(), PIXEL_SIZE = panel.getPixelSize();
		
		collision = false;
		for (int i = 0; i < pixelCount; i++)
		{
			movePixel(i, PANEL_SIZE, PIXEL_SIZE, x, y);
			if (collision) {break;}
		}
		
		//FALL AFTER INITIAL MOVEMENT
		if (fallingSpeed == 0)
		{return;}
		
		for (int i = 0; i < pixelCount; i++)
		{
			if (pixelsY[i]+fallingSpeed < PANEL_SIZE-PIXEL_SIZE && pixelsY[i]+fallingSpeed > 0) //for squish effect at floor/ceiling
			{
				pixelsY[i]+=fallingSpeed;
				if (panel.isPixelSolid(pixelsX[i] / PIXEL_SIZE, pixelsY[i] / PIXEL_SIZE))
				{abortAllMoves(0, fallingSpeed, i); break;}
			}
		}
	}
	
	private void movePixel(int i, int PANEL_SIZE, int PIXEL_SIZE, int x, int y)
	{	
		pixelsX[i]+=x; 
		pixelsY[i]+=y;

		//make sure no pixel exits screen + a few pixels buffer
		if (pixelsX[i] <= 0 || pixelsY[i] <= 0 || pixelsX[i] >= PANEL_SIZE-PIXEL_SIZE*2 || pixelsY[i] >= PANEL_SIZE-PIXEL_SIZE*2)
		{abortAllMoves(x,y, i); return;}
		
		int gridX = pixelsX[i] / PIXEL_SIZE, gridY = pixelsY[i] / PIXEL_SIZE;
		if (panel.isPixelSolid(gridX, gridY))
		{abortAllMoves(x,y, i); return;}
		
		//DO THIS TO CHECK THE TWO PIXELS THAT ARE BETWEEN PREV AND CURRENT POSITION DURING DIAGONAL MOVEMENT
		int xCheck = x > 0 ? -1 : 1, yCheck = y > 0 ? -1 : 1;
		
		/*  0 IS PREVIOUS, P IS CURRENT POSITION
		* 		[]P		 []0	 0[]	  P[]
		* 		0[]		 P[]	 []P	  []0
		* 		x+ y-	x- y+	x+ y+	 x- y-
		*  ========================================= => INVERT X AND Y
		*  	 [x-]P	    [y-]0	  0[y-]	 	P[x+]
		* 	   0[y+]	  P[x+]	 [x-]P    [y+]0
		*  =========================================  */
		
		if (panel.isPixelSolid(gridX + xCheck, gridY) && panel.isPixelSolid(gridX, gridY+ yCheck))
		{abortAllMoves(x,y,i);}
	}
	
	private void abortAllMoves(int x, int y, int index)
	{
		for (int i = 0; i <= index; i++)
		{pixelsX[i]-=x; pixelsY[i]-=y;}
		collision = true;
	}
}