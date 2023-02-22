package bloonShoot.hittable;
import java.awt.Color;
import java.util.Random;

public class Balloon implements Hittable
{
	private final static int HITTABLE_ID = 1;
	
	//SPRITE
	private final static Color[] green_palette =
	{new Color(175,253,187), new Color(145,234,159), new Color(108,211,125), new Color(64,169,73)};
	
	private final static Color[] red_palette = 
	{new Color(253,175,187), new Color(234,145,159), new Color(211,108,125), new Color(169,64,73)};
	
	private final static Color[] yellow_palette =
	{new Color(255,233,167), new Color(240,220,139), new Color(220,200,100), new Color(191,167,68)};
	
	private final static Color[] blue_palette =
	{new Color(175,187,253), new Color(145,159,234), new Color(108,125,211), new Color(74,81,160)};
	
	private final static Color[] pop_palette =
	{new Color(212,224,255), new Color(34,36,44)};
	
	private Color[] colors = new Color[4];

	private static final byte[] SPRITE = 
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
	
	private static final byte[] POP_SPRITE = 
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
	
	private boolean POPPING;	
	private int popAnimationFrame;
	
	private int[] ORIGIN = new int[2];

	public Balloon(int[] origin)
	{initialize(origin);}
	
	private void initialize(int[] origin)
	{
		ORIGIN[0] = origin[0]; ORIGIN[1] = origin[1];
		setColorArray(new Random().nextInt(4));	
	}
		
	private void setColorArray(int num)
	{
		switch (num)
		{
			case 0: colors = green_palette; break;
			case 1: colors = red_palette; break;
			case 2: colors = yellow_palette; break;
			case 3: colors = blue_palette; break;
		}
	}
		
	public Color[] getColors()
	{return colors;}
	
	public Color[] getReactColors()
	{return pop_palette;}
	
	public int[] getOrigin()
	{return ORIGIN;}
	
	public byte[] getSprite()
	{return SPRITE;}
	
	public byte[] getReactSprite()
	{popAnimationFrame++; return POP_SPRITE; }
	
	public boolean isAlive()
	{return (popAnimationFrame < 3);}
	
	public boolean isReacting()
	{return POPPING;}
	
	public void hit()
	{POPPING = true;}

	@Override
	public int getHittableID() 
	{return HITTABLE_ID;}
}