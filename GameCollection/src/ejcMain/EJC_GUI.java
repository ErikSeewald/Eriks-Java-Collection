package ejcMain;

import java.awt.Color;
import java.util.HashSet;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.Timer;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

public class EJC_GUI 
{
	public static final Color b_color_basic = new Color(170, 170, 210);
	public static final Color b_color_highlight = new Color(200, 200, 240);
	public static final Color b_color_pressed = new Color(215, 215, 255);
	
	public static class EJC_MenuBar extends JMenuBar
	{
		private static final long serialVersionUID = 7641308675977553836L;
		
		//TIMERS TO CONTROL
		private Timer[] timers;
		private boolean[] pausedByEJC_GUI; // only unpause timers that were paused by this class
		
		//KEY LIST TO CONTROL
		private HashSet<Integer> pressedKeys;
		
		private MenuListener menuListener;
		
		public EJC_MenuBar(JFrame frame)
		{
			this.setBackground(new Color (100,100,120));
			this.setBorder(BorderFactory.createLineBorder(new Color (115,115,135), 2));
			frame.setJMenuBar(this);
			
			menuListener = new MenuListener() 
			{
				@Override
				public void menuSelected(MenuEvent e) {pause();}

				@Override
				public void menuDeselected(MenuEvent e) {unpause();}

				@Override
				public void menuCanceled(MenuEvent e) {unpause();}
				
				private void pause()
				{
					if (pressedKeys != null) {pressedKeys.removeAll(pressedKeys);}
					
					if (timers == null) {return;}
					for (int i = 0; i < timers.length; i++)
					{
						if (timers[i] == null) {continue;}
						timers[i].stop();
						pausedByEJC_GUI[i] = true;
					}
				}
				
				private void unpause()
				{
					if (timers == null) {return;}
					for (int i = 0; i < timers.length; i++)
					{
						if (timers[i] == null || !pausedByEJC_GUI[i]) {continue;}
						timers[i].start();
						pausedByEJC_GUI[i] = false;
					}
				}
				
			};
		}
		
		public void addEJCMenu(String name, JMenuItem[] items)
		{
			JMenu menu = new JMenu(name);
			menu.setForeground(new Color (230,230,250));
			menu.setBorder(BorderFactory.createLineBorder(new Color (100,100,120)));
			menu.addMenuListener(menuListener);
			
			for (JMenuItem item : items)
			{menu.add(item);}
			
			this.add(menu);
		}
		
		/**
		 * Util function to let the JMenu automatically pause
		 * and unpause timers when a menu is clicked
		 * @param timers Array of timers to control
		 */
		public void setTimersToControl(Timer[] timers)
		{
			this.timers = timers;
			this.pausedByEJC_GUI = new boolean[timers.length];
		}
		
		
		/**
		 * Util function to let the JMenu automatically clear
		 * the list of pressed keys when a Menu is clicked
		 * @param pressedKeys HashSet of keys to control
		 */
		public void setKeyListToControl(HashSet<Integer> pressedKeys)
		{this.pressedKeys = pressedKeys;}
	}
}