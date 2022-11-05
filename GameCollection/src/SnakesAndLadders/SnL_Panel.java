package SnakesAndLadders;

import java.awt.Dimension;

import javax.swing.JPanel;

public class SnL_Panel extends JPanel
{
	private static final long serialVersionUID = 7284705418024953236L;
	
	private final int PANEL_WIDTH = 1200;
	private final int PANEL_HEIGHT = 780;
	
	SnL_Panel()
	{
		this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
	}

}
