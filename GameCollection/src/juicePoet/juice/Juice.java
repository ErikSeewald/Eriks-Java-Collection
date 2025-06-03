package juicePoet.juice;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 * Class representing juice. Holds a juice texture of size {@code TEXTURE_SIZE}.
 */
public class Juice
{
	public static final int TEXTURE_SIZE = 100;
	private final BufferedImage texture;
	
	Juice()
	{
		texture = new BufferedImage(TEXTURE_SIZE, TEXTURE_SIZE, BufferedImage.TYPE_INT_ARGB);
	}
	
	public BufferedImage getTexture()
	{
		return texture;
	}
	
	/**
	 * Sets the pixel at the given coordinate to the given {@link Color}. Should only be used by
	 * the {@link JuiceAlgorithm}.
	 * 
	 * @param x the x coordinate of the pixel
	 * @param y the y coordinate of the pixel
	 * @param color the {@link Color} to set the pixel to
	 */
	protected void setTexturePixel(int x, int y, Color color)
	{
		if (x > TEXTURE_SIZE || x < 0 || y > TEXTURE_SIZE || y < 0)
		{
			throw new IllegalArgumentException
			("Pixel x-" + x + " y-" + y + "out of bounds for TEXTURE_SIZE: " + TEXTURE_SIZE);
		}
		
		texture.setRGB(x, y, color.getRGB());
	}
	
	/**
	 * Fills the entire texture with the given {@link Color}. Should only be used by
	 * the {@link JuiceAlgorithm}.
	 * 
	 * @param color the {@link Color} to fill the texture with
	 */
	protected void fillTexture(Color color)
	{
		for (int x = 0; x < Juice.TEXTURE_SIZE; x++)
		{
			for (int y = 0; y < Juice.TEXTURE_SIZE; y++)
			{
				this.setTexturePixel(x, y, color);
			}
		}
	}
}