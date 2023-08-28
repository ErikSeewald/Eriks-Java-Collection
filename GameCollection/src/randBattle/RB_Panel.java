package randBattle;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

import Main.EJC_Util;

public class RB_Panel extends JPanel
{
	private static final long serialVersionUID = -8706000019243206523L;
	
	private int PANEL_WIDTH = 1320;
	private int PANEL_HEIGHT = (int) (PANEL_WIDTH *0.6);
	
	private boolean showStats = false;
	private boolean onlyShowHealth = false;
	
	private BattleHandler battleHandler;
	
	RB_Panel()
	{
		this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		battleHandler = new BattleHandler(this, PANEL_WIDTH, PANEL_HEIGHT);
		start();
	}
	
	public void start()
	{
		battleHandler.start();
		repaint();
	}
	
	public void stopTimer()
	{battleHandler.stopTimer();}
	
	public void showStats()
	{showStats = !showStats;}
	
	public void onlyShowHealth()
	{onlyShowHealth = !onlyShowHealth;}
	
	//---------------------------------------PAINT---------------------------------------
	
	private static final Color background_col = new Color(50,50,60);
	private static final Color npc_col = new Color(100,100, 145);
	private static final Color damage_col = new Color (220, 100, 100);
	
	public void paint(Graphics g)
	{
		Graphics2D g2D = (Graphics2D) g;
		
		//Background
		g2D.setPaint(background_col);
		g2D.fillRect(0, 0, PANEL_WIDTH, PANEL_HEIGHT);
		
		//NPCs
		NPC[] NPCs = battleHandler.getNPCs();
		for (NPC npc : NPCs)
		{
			if (!npc.isAlive) {continue;}
			
			g2D.setColor(npc.damageFrames == 0 ? npc_col : damage_col);
			g2D.fillRect((int) npc.x, (int) npc.y, npc.SIZE, npc.SIZE);
			
			g2D.setColor(Color.red);
			g2D.fillRect((int) npc.PROJECTILE_X+2, (int) npc.PROJECTILE_Y+2, 4, 4);
		}
		
		if (battleHandler.hasFinished()) 
		{
			g2D.setFont(new Font("", Font.BOLD, 144));
			g2D.drawString("FINISHED", PANEL_WIDTH/4, PANEL_HEIGHT/4);
		}
		
		//STATS
		if (!showStats && !onlyShowHealth) {return;}
		
		g2D.setPaint(Color.white);
		g2D.setFont(new Font("", Font.BOLD, 9));
		for (NPC npc : NPCs)
		{
			if (!npc.isAlive) {continue;}
			
			g2D.drawString(npc.HEALTH+ "hp", (int) npc.x, (int) npc.y);

			if (onlyShowHealth) {continue;}
			
			g2D.drawString(npc.DAMAGE+ "dmg", (int) npc.x, (int) npc.y-10);
			g2D.drawString(EJC_Util.round(npc.MOVE_SPEED,2)+ "speed", (int) npc.x, (int) npc.y-20);
			g2D.drawString(EJC_Util.round(npc.PROJECTILE_SPEED,2)+ "dmg speed", (int) npc.x, (int) npc.y-30);
		}	
	}
}