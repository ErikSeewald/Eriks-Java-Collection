package juicePoet.juice;

import java.awt.image.BufferedImage;

public class Juice
{
	public static final int texture_size = 100;
	private BufferedImage texture;
	
	Juice()
	{
		texture = new BufferedImage(texture_size, texture_size, BufferedImage.TYPE_INT_ARGB);
		for (int x = 0; x < texture_size; x++)
		{
			for (int y = 0; y < texture_size; y++)
			{
				texture.setRGB(x, y, 0xFF00FF00);
			}
		}
	}
	
	public BufferedImage getTexture()
	{
		return texture;
	}
}
