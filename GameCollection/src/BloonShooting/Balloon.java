package BloonShooting;

import java.awt.Color;
import java.util.Random;

public class Balloon implements Hittable
{
	private int TYPE;
	private boolean POPPING;
	
	private int popAnimationFrame;
	
	//SPRITE
	final static Color Green1 = new Color(175,253,187);
	final static Color Green2 = new Color(145,234,159);
	final static Color Green3 = new Color(108,211,125);
	final static Color Green4 = new Color(64,169,73);
	
	final static Color Red1 = new Color(253,175,187);
	final static Color Red2 = new Color(234,145,159);
	final static Color Red3 = new Color(211,108,125);
	final static Color Red4 = new Color(169,64,73);
	
	final static Color Yellow1 = new Color(255,233,167);
	final static Color Yellow2 = new Color(240,220,139);
	final static Color Yellow3 = new Color(220,200,100);
	final static Color Yellow4 = new Color(191,167,68);
	
	final static Color Blue1 = new Color(175,187,253);
	final static Color Blue2 = new Color(145,159,234);
	final static Color Blue3 = new Color(108,125,211);
	final static Color Blue4 = new Color(74,81,160);
	
	final static Color Pop1 = new Color(212,224,255);
	final static Color Pop2 = new Color(34,36,44);
	
	private Color[] colors = new Color[4];
		
	private int PIXEL_SIZE;

	static final byte[] SPRITE = 
	{
			0,0,0,0,0,0,4,4,4,4,0,0,0,0,0,0,
			0,0,0,0,4,4,4,3,3,4,4,4,0,0,0,0,
			0,0,0,4,4,3,3,3,2,3,3,4,4,0,0,0,
			0,0,0,4,3,3,2,1,1,2,3,3,4,0,0,0,
			0,0,0,4,3,2,1,1,1,1,2,3,4,0,0,0,
			0,0,4,4,3,2,1,1,1,1,1,3,4,4,0,0,
			0,0,4,3,3,2,2,1,1,1,1,2,3,4,0,0,
			0,0,4,3,3,2,2,2,1,1,1,2,3,4,0,0,
			0,0,4,4,3,3,2,2,1,1,2,3,4,4,0,0,
			0,0,4,4,3,3,2,2,2,1,3,3,4,4,0,0,
			0,0,0,4,3,3,3,2,2,2,3,3,4,0,0,0,
			0,0,0,4,4,3,3,2,2,3,3,4,4,0,0,0,
			0,0,0,0,4,3,3,3,3,3,3,4,0,0,0,0,
			0,0,0,0,0,4,4,3,3,4,4,0,0,0,0,0,
			0,0,0,0,0,0,0,4,4,0,0,0,0,0,0,0,
			0,0,0,0,0,0,0,0,4,4,0,0,0,0,0,0,
	};
	
	static final byte[] POP_SPRITE = 
	{
			2,2,0,0,0,0,0,2,2,0,0,0,0,0,2,2,
			2,1,2,2,0,0,2,1,1,2,0,0,2,2,1,2,
			0,2,1,1,2,2,1,1,1,1,2,2,1,1,2,0,
			0,2,1,1,1,1,1,1,2,1,1,1,1,1,2,0,
			0,0,2,1,2,1,1,2,1,1,1,2,1,2,0,0,
			0,0,2,1,1,2,2,1,1,2,2,1,1,2,0,0,
			0,2,1,1,1,2,1,1,1,1,2,1,1,1,2,0,
			2,1,1,2,1,1,1,2,2,1,1,2,1,1,1,2,
			2,1,1,1,2,1,1,2,2,1,1,1,2,1,1,2,
			0,2,1,1,1,2,1,1,1,1,2,1,1,1,2,0,
			0,0,2,1,1,2,2,1,1,2,2,1,1,2,0,0,
			0,0,2,1,2,1,1,1,2,1,1,2,1,2,0,0,
			0,2,1,1,1,1,1,2,1,1,1,1,1,1,2,0,
			0,2,1,1,2,2,1,1,1,1,2,2,1,1,2,0,
			2,1,2,2,0,0,2,1,1,2,0,0,2,2,1,2,
			2,2,0,0,0,0,0,2,2,0,0,0,0,0,2,2,
	};
		

	Balloon(int[] origin, int pixelSize)
	{initialize(origin); setPixelSize(pixelSize);}
		
	//COORDINATES & VECTORS
	private int[] ORIGIN = new int[2];
		
	//INITIALIZATION
	private void initialize(int[] origin)
	{
		ORIGIN[0] = origin[0]; ORIGIN[1] = origin[1];
		TYPE = new Random().nextInt(4);
		POPPING = false;
		popAnimationFrame = 0;
		setColorArray();	
	}
		
	//COLORS
	private void setColorArray()
	{
		switch (TYPE)
		{
			case 0: colors[0] = Green1; colors[1] = Green2; colors[2] = Green3; colors[3] = Green4;
			break;
			case 1: colors[0] = Red1; colors[1] = Red2; colors[2] = Red3; colors[3] = Red4;
			break;
			case 2: colors[0] = Yellow1; colors[1] = Yellow2; colors[2] = Yellow3; colors[3] = Yellow4;
			break;
			case 3: colors[0] = Blue1; colors[1] = Blue2; colors[2] = Blue3; colors[3] = Blue4;
		}
	}
		
	public Color[] getColors()
	{return colors;}
	
	public Color[] getPopColors()
	{return new Color[] {Pop1, Pop2};}
	
	//ORIGIN
	public int[] getOrigin()
	{return ORIGIN;}
	
	//PIXEL SIZE
	public int getPixelSize()
	{return PIXEL_SIZE;}
	
	public void setPixelSize(int pixelSize)
	{PIXEL_SIZE = pixelSize;}
	
	//SPRITE
	public byte[] getSprite()
	{return SPRITE;}
	
	public byte[] getPopSprite()
	{popAnimationFrame++; return POP_SPRITE; }
	
	//ALIVE
	public boolean isAlive()
	{return (popAnimationFrame < 15);}
	
	public boolean isPopping()
	{return POPPING;}
	
	public void kill()
	{POPPING = true;}
	
}
