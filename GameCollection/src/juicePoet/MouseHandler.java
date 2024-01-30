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

public class MouseHandler 
{
	private Object selectedObject;
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

	public class ClickListener extends MouseAdapter
	{
		@Override
		public void mousePressed(MouseEvent e) 
		{
			int x = e.getX();
			int y = e.getY();
			
			//POEMS
			for (Poem poem: poemHandler.getPoems())
			{
				if (x > poem.x - 60 && x < poem.x + 60 && y > poem.y - 60 && y < poem.y + 60)
				{
					selectedObject = poem;
					return;
				}
			}
			
			//GLASSES
			for (Glass glass: juiceHandler.getGlasses())
			{
				if (x > glass.x - 60 && x < glass.x + 60 && y > glass.y  -60 && y < glass.y + 60)
				{
					selectedObject = glass;
					return;
				}
			}
		}
	}

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

	public class ReleaseListener extends MouseAdapter
	{
		@Override
		public void mouseReleased(MouseEvent e) 
		{
			if (selectedObject == null) {return;}
			
			if (selectedObject instanceof Poem poem) // creates poem object from selectedObject
			{
				int[] coordinates = juicer.getCoordinates();
				int juicerX = coordinates[0];
				int juicerY = coordinates[1];
				
				if (poem.x > juicerX - 20 && poem.x < juicerX + 160 
						&& poem.y > juicerY - 100 && poem.y < juicerY + 80)
				{
					juicer.juice(poem);
					poemHandler.removePoem(poem);
					panel.repaint();
				}
			}
			
			else if (selectedObject instanceof Glass glass) // creates poem object from selectedObject
			{
				int[] coordinates = juicer.getCoordinates();
				int juicerX = coordinates[0];
				int juicerY = coordinates[1];
				
				if (!juicer.isEmpty() && glass.x > juicerX - 20 && glass.x < juicerX + 160 
						&& glass.y > juicerY - 100 && glass.y < juicerY)
				{
					glass.fill(juicer.extractJuice());
					panel.repaint();
				}
			}
	
			selectedObject = null;
		}
	}
}
