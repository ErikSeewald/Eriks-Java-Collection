package perfectParty.gui;

import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.HashMap;
import javax.swing.JPanel;
import perfectParty.voters.Preference;

/**
 * Horizontal bar displaying the distribution of {@link Preference}s of voters for a specific {@link Policy}.
 */
public class DistributionBar extends JPanel
{
	private static final long serialVersionUID = -4279532222844423722L;
	private final HashMap<Preference, Long> preferences;
	private final long populationNum;

	public DistributionBar(HashMap<Preference, Long> preferences, long populationNum)
	{
		this.preferences = preferences;
		this.populationNum = populationNum;
		this.setPreferredSize(new Dimension(300, 30));
	}

	@Override
	public void paint(Graphics g)
	{
		super.paint(g);
		Graphics2D g2D = (Graphics2D) g;
		int width = getWidth();
		int height = getHeight();
		int currentX = 0;

		for (Preference preference : Preference.values())
		{
			long count = preferences.getOrDefault(preference, 0L);
			int segmentWidth = (int) Math.ceil((double) count / populationNum * width);

			g2D.setPaint(preference.color);
			g2D.fillRect(currentX, 0, segmentWidth, height);

			currentX += segmentWidth;
		}

		// BORDER
		g2D.setPaint(ElectionStyle.BORDER_COL);
		g2D.setStroke(new BasicStroke(3));
		g2D.drawRect(0, 0, width - 1, height -1);
	}
}