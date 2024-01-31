package juicePoet;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import ejcMain.util.Draggable;
import juicePoet.glass.Glass;
import juicePoet.glass.GlassHandler;
import juicePoet.juice.Juicer;
import juicePoet.poem.Poem;
import juicePoet.poem.PoemHandler;

/**
 * Handles all mouse interactions for {@link EJC_JuicePoet}.
 */
public class MouseHandler 
{
	private Object selectedObject;
	private Object lastSelectedObject;
	
	private PoemHandler poemHandler;
	private GlassHandler juiceHandler;
	private JuicePanel panel;
	private Juicer juicer;
	
	MouseHandler(PoemHandler poemHandler, GlassHandler juiceHandler, Juicer juicer, JuicePanel panel)
	{
		this.poemHandler = poemHandler;
		this.juiceHandler = juiceHandler;
		this.panel = panel;
		this.juicer = juicer;
	}
	
	public Object getLastSelectedObject() {return lastSelectedObject;}

	/**
	 * Handles all mouse click actions for {@link EJC_JuicePoet}.
	 */
	public class ClickListener extends MouseAdapter
	{
		@Override
		public void mousePressed(MouseEvent e) 
		{
			int x = e.getX();
			int y = e.getY();
			
			//POEMS
			int buffer = Poem.WIDTH/2; 
			for (Poem poem: poemHandler.getPoems())
			{
				int poemX = poem.getX();
				int poemY = poem.getY();
				
				if (x > poemX - buffer && x < poemX + buffer && y > poemY - buffer && y < poemY + buffer)
				{
					selectedObject = poem;
					return;
				}
			}
			
			//GLASSES
			for (Glass glass: juiceHandler.getGlasses())
			{
				int glassX = glass.getX();
				int glassY = glass.getY();
				
				if (x > glassX - buffer && x < glassX + buffer && y > glassY - buffer && y < glassY + buffer)
				{
					selectedObject = glass;
					return;
				}
			}
		}
	}

	/**
	 * Handles all mouse drag actions for {@link EJC_JuicePoet}.
	 */
	public class DragListener extends MouseMotionAdapter
	{
		@Override
		public void mouseDragged(MouseEvent e) 
		{
			if (!(selectedObject instanceof Draggable)) {return;}
			
			Draggable dragObject = (Draggable) selectedObject;
			dragObject.dragTo(e.getX(), e.getY());
			
			panel.repaint();
		}
	}

	/**
	 * Handles all mouse release actions for {@link EJC_JuicePoet}.
	 */
	public class ReleaseListener extends MouseAdapter
	{
		@Override
		public void mouseReleased(MouseEvent e) 
		{
			if (!(selectedObject instanceof Draggable draggable)) {return;}
			
			int x = draggable.getX();
			int y = draggable.getY();
			
			int[] juicerCoords = juicer.getCoordinates();
			int juicerX = juicerCoords[0];
			int juicerY = juicerCoords[1];
			
			// INSIDE JUICER DROP BOX
			if (x > juicerX - Juicer.BOTTOM_WIDTH/8 && x < juicerX + Juicer.BOTTOM_WIDTH
					&& y > juicerY - Juicer.GLASS_HEIGHT && y < juicerY + Juicer.GLASS_HEIGHT/2)
			{
				if (draggable instanceof Poem poem)
				{
					juicer.juice(poem);
					poemHandler.removePoem(poem);
					panel.repaint();
				}
				
				else if (draggable instanceof Glass glass)
				{
					glass.fill(juicer.extractJuice());
					panel.repaint();
				}
			}
			
			lastSelectedObject = selectedObject;
			selectedObject = null;
		}
	}
}
