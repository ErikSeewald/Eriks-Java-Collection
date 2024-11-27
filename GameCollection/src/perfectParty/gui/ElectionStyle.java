package perfectParty.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class ElectionStyle
{
	public static final Color BACKGROUND_COL = new Color(50, 50, 70);
	public static final Color HEADER_COL = new Color(220, 220, 240);
	public static final Color HEADER_COL_DARKER = new Color(180, 180, 200);
	
	public static JPanel buildHeaderPanel(JPanel parent, String headerText)
	{
		JPanel headerPanel = new JPanel();
		headerPanel.setBackground(ElectionStyle.BACKGROUND_COL);
		headerPanel.setLayout(new BorderLayout());

		headerPanel.setPreferredSize(new Dimension(parent.getWidth(), 80));
		headerPanel.setMinimumSize(new Dimension(0, 80));
		headerPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
		
		JLabel headerLabel = new JLabel(headerText, SwingConstants.CENTER);
		headerLabel.setForeground(ElectionStyle.HEADER_COL);
		headerLabel.setFont(new Font("", Font.BOLD, 22));
		headerPanel.add(headerLabel, BorderLayout.CENTER);
		
		return headerPanel;
	}
}
