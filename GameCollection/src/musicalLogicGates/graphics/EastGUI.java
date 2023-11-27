package musicalLogicGates.graphics;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class EastGUI extends JPanel
{
	private static final long serialVersionUID = -587643554501823550L;
	private static final int PANEL_WIDTH = 200;

	//private static final Color textColor = new Color(90, 90, 110);
	private static final Color borderColor = new Color(90,90,120);
	
	public EastGUI()
	{
		this.setPreferredSize(new Dimension(PANEL_WIDTH, CircuitPanel.PANEL_HEIGHT));
		this.setBackground(new Color(120,120,150));
		this.setBorder(BorderFactory.createLineBorder(borderColor, 8));

		this.setLayout(null);
	}
	
//	private void setLabelSettings(JLabel label, int a, int b, int c, int d)
//	{
//		label.setBounds(a,b,c,d);
//		label.setForeground(textColor);
//		label.setFont(new Font("", Font.BOLD, 20));
//		this.add(label);
//	}
//	
//	private void setButtonSettings(JLabel button, int a, int b, int c, int d)
//	{
//		button.setBounds(a,b,c,d);
//		button.setBackground(EJC_GUI.b_color_basic);
//		button.setForeground(textColor);
//		button.setOpaque(true);
//		button.setBorder(BorderFactory.createLineBorder(borderColor, 3));
//		button.setFont(new Font("", Font.BOLD, 30));
//		button.addMouseListener(this);
//		this.add(button);
//	}

}
