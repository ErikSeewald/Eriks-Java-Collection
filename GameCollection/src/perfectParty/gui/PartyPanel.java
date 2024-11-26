package perfectParty.gui;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PartyPanel extends JPanel
{
	private static final long serialVersionUID = 5227466209577033023L;

	public PartyPanel()
	{
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(Color.LIGHT_GRAY);
        headerPanel.setPreferredSize(new Dimension(0, 100));
        headerPanel.add(new JLabel("Party"));
        this.add(headerPanel);

        for (int i = 0; i < 10; i++) {
            JPanel contentPanel = new JPanel();
            contentPanel.setBackground(i % 2 == 0 ? Color.WHITE : Color.CYAN);
            contentPanel.setPreferredSize(new Dimension(0, 50));
            contentPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            contentPanel.add(new JLabel("Content " + (i + 1)));
            this.add(contentPanel);
        }
	}
}
