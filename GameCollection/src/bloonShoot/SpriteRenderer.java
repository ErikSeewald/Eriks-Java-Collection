package bloonShoot;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class SpriteRenderer
{
	private static HashMap<String, BufferedImage> sprites = new HashMap<>();
	
	
	public static BufferedImage getSpriteImage(String objectID, Color[] colors, byte[] sprite, int pixelSize)
	{
		String key = objectID + "-" + pixelSize;
		BufferedImage image = sprites.get(key);
		
		if (image == null)
		{
			image = renderSprite(colors, sprite, pixelSize);
			sprites.put(key, image);
		}
		
		return image;
	}
	
	private static BufferedImage renderSprite(Color[] colors, byte[] sprite, int pixelSize)
	{
		BufferedImage image = new BufferedImage(pixelSize*16, pixelSize*16, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2D = image.createGraphics();
		
		short row = 0, column = 0;
		for (short i = 0; i < 256; i++)
		{
			if (column == 16) {row++; column = 0;}
			if (sprite[i] != 0)
			{
				short startCol = column;
				while (column < 15) //extend further while color is the same to draw a wide rectangle instead of multiple single squares
				{
					if (sprite[i] == sprite[i+1]) {i++; column++;}
					else {break;}
				}
				
				g2D.setPaint(colors[sprite[i]-1]);
				g2D.fillRect(startCol*pixelSize, row*pixelSize, pixelSize * (column - startCol + 1), pixelSize);
			}
			column++;
		}
		
		g2D.dispose();
		return image;
	}
}
