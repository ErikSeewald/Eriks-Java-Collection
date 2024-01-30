package juicePoet;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import ejcMain.EJC_Game;
import ejcMain.EJC_WindowEventHandler;
import ejcMain.util.EJC_GUI.EJC_MenuBar;

public class EJC_JuicePoet extends JFrame implements EJC_Game, ActionListener
{
	private static final long serialVersionUID = -8122486719778369279L;
	
	private JMenuItem poemItem = new JMenuItem("New poem");
	private JMenuItem glassItem = new JMenuItem("New glass");
	private JMenuItem[] actionItems = {poemItem, glassItem};
	
	private JuicePanel panel;
	
	@Override
	public void start(EJC_WindowEventHandler eventHandler) 
	{
		this.addWindowListener(eventHandler);
		this.setTitle("Juice Poet");
		this.setResizable(false);
		
		panel = new JuicePanel();
		this.add(panel);
		this.pack();
		
		//MENU BAR
		EJC_MenuBar menuBar = new EJC_MenuBar(this);
		menuBar.addEJCMenu("Actions", actionItems);
		
		for (JMenuItem item : actionItems)
		{
			item.addActionListener(this);
		}
		
		this.setVisible(true);
	}

	@Override
	public void stop() 
	{
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		Object source = e.getSource();
	
		if (source.equals(poemItem))
		{
			panel.addPoem();
		}
		
		if (source.equals(glassItem))
		{
			panel.addGlass();
		}
		
	}
	
}
