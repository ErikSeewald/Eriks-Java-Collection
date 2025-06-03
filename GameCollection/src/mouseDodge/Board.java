package mouseDodge;

public class Board
{
	int X,Y;
	int width, height;
	
	Board(int PANEL_SIZE)
	{this.setSize(PANEL_SIZE);}
	
	public void setSize(int PANEL_SIZE)
	{
		this.X = (int)(PANEL_SIZE/2.8);
		this.Y = (int)(PANEL_SIZE/7.5);
		this.width = PANEL_SIZE;		
		this.height = (int)(PANEL_SIZE/1.35);
	}
}