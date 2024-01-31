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
		for (int x = 0; x < TEXTURE_SIZE; x++)
		{
			for (int y = 0; y < TEXTURE_SIZE; y++)
			{
				texture.setRGB(x, y, 0xFF00FF00);
			}
		}
	}
	
	public BufferedImage getTexture()
	{
		return texture;
	}
	
	/**
	 * Sets the pixel at the given coordinate to the given {@link Color}. Should only be used by
	 * the {@link Juicer}.
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
}