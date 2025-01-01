package perfectParty.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;

import javax.swing.BoundedRangeModel;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicSplitPaneUI;

import ejcMain.util.EJC_GUI;

/**
 * General style specifications for the {@link EJC_PerfectParty} GUI.
 */
public class ElectionStyle
{
	public static final Color BACKGROUND_COL = new Color(50, 50, 70);
	public static final Color BRIGHTER_BACKGROUND_COL = new Color(80, 80, 100);
	
	public static final Color HEADER_COL = new Color(220, 220, 240);
	public static final Color HEADER_COL_DARKER = new Color(180, 180, 200);
	
	public static final Color BORDER_COL = new Color(110, 110, 120);
	
	public static final Color WINNER_COL = new Color(120, 240, 140);
	
	/**
	 * Builds and returns a header {@link JPanel} for the given parent {@link JPanel}.
	 * Contains a {@link JLabel} header with the given header text.
	 */
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
	
	/**
	 * Builds and returns a sub header {@link JPanel} for the given parent {@link JPanel}.
	 * Formats the given {@link JLabel} and adds it to the sub header {@link JPanel}.
	 */
	public static JPanel buildSubHeaderPanel(JPanel parent, JLabel label)
	{
		JPanel subHeaderPanel = new JPanel();
		subHeaderPanel.setBackground(ElectionStyle.BRIGHTER_BACKGROUND_COL);
		subHeaderPanel.setLayout(new BorderLayout());

		subHeaderPanel.setPreferredSize(new Dimension(parent.getWidth(), 60));
		subHeaderPanel.setMinimumSize(new Dimension(0, 60));
		subHeaderPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
		
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setForeground(ElectionStyle.HEADER_COL);
		label.setFont(new Font("", Font.BOLD, 16));
		subHeaderPanel.add(label, BorderLayout.CENTER);
		
		return subHeaderPanel;
	}
	
	/**
	 * Wraps the two given panels in {@link JScrollPane}s with synchronized scroll bars and then adds them to the given
	 * (empty but already instantiated) {@link JSplitPane}.
	 */
	public static void buildSplitScrollPane(JSplitPane splitPane, JPanel panelA, JPanel panelB)
	{
		// WRAP PANELS IN SCROLL PANES
		JScrollPane scrollPaneA = new JScrollPane(panelA);
		JScrollPane scrollPaneB = new JScrollPane(panelB);

		// HIDE LEFT SCROLLBAR AND STYLE RIGHT SCROLLBAR
		scrollPaneA.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		scrollPaneB.getVerticalScrollBar().setUI(new EJC_GUI.EJCScrollBar());

		// SYNCHRONIZE SCROLLBARS
		BoundedRangeModel modelA = scrollPaneA.getVerticalScrollBar().getModel();
		BoundedRangeModel modelB = scrollPaneB.getVerticalScrollBar().getModel();

		modelA.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				modelB.setValue(modelA.getValue());
			}
		});

		modelB.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				modelA.setValue(modelB.getValue());
			}
		});

		// DIVIDE FRAME USING SPLITPANE
		splitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		splitPane.setLeftComponent(scrollPaneA);
		splitPane.setRightComponent(scrollPaneB);
		splitPane.setUI(new BasicSplitPaneUI());
	}
	
	/**
	 * Builds and returns a {@link GridBagConstraints} instance for policy panels in the 
	 * PerfectParty presentation style.
	 */
	public static GridBagConstraints buildPolicyConstraints()
	{
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.weightx = 1.0; // Stretch horizontally
        constraints.anchor = GridBagConstraints.WEST; // Left-align
        return constraints;
	}
}
