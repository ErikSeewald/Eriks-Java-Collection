package perfectParty.gui;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;

/**
 * Class handling the game info GUI
 */
public class InfoDialogues
{
	public static enum InfoType
	{
		RULES (new String[] {
		"The goal is to win each election cycle against the automatic cpu party by assigning the optimal "
		+ "amount of policy points. Policy points represent the amount of resources a party dedicates to a policy. "
		+ "The amount of available policy points is shown in the GUI. Both it and the total amount of policies grows "
		+ "by 1 each round. If you win the election by receiving more votes than the cpu party, you get to move on to "
		+ "the next round. If you lose, you will need to restart.",
		
		"The voters all have a preference regarding each policy ranging from very positive (++) to neutal (=) to "
		+ "very negative (--). This preference is visualized in the GUI. Voters score each party in regards to  "
		+ "policy point assigments. Points are scored proportionally to preference. Therefore a positive "
		+ "preference means the voter wants to see parties invest resources in the policy, a neutral preference means "
		+ "they do not care, and a negative preference means they are against any resources being invested in the "
		+ "policy.",
		
		"Additionally, if a voter has a positive preference towards a policy, they will also score a party negatively "
		+ "for not investing any points in it. Investing all points in the most popular policy might not always be "
		+ "worth it. It is also worth considering that voters might agree with the majority in regards to one policy "
		+ "while disagreeing with the majority on another.",
		
		"The cpu party uses a strong but beatable algorithm to assign its points based on voter preference. For every "
		+ "round the cpu has the same amount of policy points available to it as you do. However, if a voter scores "
		+ "both parties the same, they will always vote for the player. Therefore you might see a victory with a huge "
		+ "margin even if you assigned the points exactly like the cpu did. The choices of the cpu are hidden until "
		+ "you run the election. Then you will be shown a comparison of how the player party and cpu party assigned "
		+ "points (along with the voter preference graph for reference).",
		
		"As the size of the population grows with each election, their preference distribution towards policies will "
		+ "also change every time."
		}), 
		
		CONTROLS (new String[] {
		"Left click a policy with your mouse to assign points to it. Right click with your mouse to "
		+ "refund these points.",
		
		" You can resize the window or move the dividing bar between party and voter panels if need be. Once you "
		+ "reach a round in which there are too many policies to display on screen at once, you can use your mouse "
		+ "to scroll through them.",
				
		"The 'actions' tab in the menu bar: 'Run election' takes control away from you, calculates the result and "
		+ "displays it. 'Next round' can be used after running the election only if you have won. 'Restart' can "
		+ "always be used to restart the game.",
		}); 
		
		
		protected final String[] DIALOGUES;
		InfoType(String[] dialogues)
		{
			this.DIALOGUES = dialogues;
		}
	}
	
	public static boolean displayingInfo = false;
	
	private static final String[] DIALOGUE_OPTIONS = {"Previous", "Next"};
	
	public static void displayInfo(InfoType type)
	{
		if (displayingInfo) {return;}
		
		// Due to JOptionPane weirdness this is the best way to style the buttons.
		// It does have the side effect of changing the style of all other buttons, but since the pane dialogue
		// prevents the user from interacting with any other windows of the program until it is closed
		// there is no need to worry. Just revert the change at the end of the function :)
		UIManager.put("Button.background", ElectionStyle.HEADER_COL);
		UIManager.put("Button.foreground", ElectionStyle.BACKGROUND_COL);
		UIManager.put("Button.border", new LineBorder(ElectionStyle.BRIGHTER_BACKGROUND_COL, 2));

		displayingInfo = true;
		int curIndex = 0;
		
		while (true)
		{
			int choice = JOptionPane.showOptionDialog(
					null, 
					"<html><body style='width: 400px;'>" + type.DIALOGUES[curIndex] + "</body></html>", 
					type.toString(), 
					JOptionPane.DEFAULT_OPTION,
					JOptionPane.PLAIN_MESSAGE, 
					null, 
					DIALOGUE_OPTIONS, 
					DIALOGUE_OPTIONS[1]
			);

			if (choice == 0)
			{
				if (curIndex > 0) {curIndex--;}
			} 
			
			else if (choice == 1)
			{
				if (curIndex < type.DIALOGUES.length - 1) {curIndex++;}
				else {break;}
			} 
			
			else {break;}
		}
		
		// RESET STYLE CHANGES
		UIManager.put("Button.background", null);
		UIManager.put("Button.foreground", null);
		UIManager.put("Button.border", null);
		
		displayingInfo = false;
	}
}