package randBattle;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JPanel;

import ejcMain.EJC_Util;

public class RB_Panel extends JPanel
{
	private static final long serialVersionUID = -8706000019243206523L;
	
	public static final int PANEL_WIDTH = 1320;
	public static final int PANEL_HEIGHT = (int) (PANEL_WIDTH *0.6);
	
	private boolean showStats = false;
	private boolean onlyShowHealth = false;
	
	private BattleHandler battleHandler;
	
	RB_Panel()
	{
		this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		battleHandler = new BattleHandler(this, PANEL_WIDTH, PANEL_HEIGHT);
	}
	
	public void stop()
	{battleHandler.stop();}
	
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
		
		//Fighters
		ArrayList<Fighter> fighters = battleHandler.getNPCs();
		for (Fighter f : fighters)
		{
			if (!f.isAlive) {continue;}
			
			g2D.setColor(f.damageFrames == 0 ? npc_col : damage_col);
			g2D.fillRect((int) f.x, (int) f.y, f.size, f.size);
			
			g2D.setColor(Color.red);
			g2D.fillRect((int) f.projectile_x+2, (int) f.projectile_y+2, 4, 4);
		}
		
		//STATS
		if (!showStats && !onlyShowHealth) {return;}
		
		g2D.setPaint(Color.white);
		g2D.setFont(new Font("", Font.BOLD, 9));
		for (Fighter f : fighters)
		{
			if (!f.isAlive) {continue;}
			
			g2D.drawString(f.health+ "hp", (int) f.x, (int) f.y);

			if (onlyShowHealth) {continue;}
			
			g2D.drawString(f.damage+ "dmg", (int) f.x, (int) f.y-10);
			g2D.drawString(EJC_Util.round(f.moveSpeed,2)+ "speed", (int) f.x, (int) f.y-20);
			g2D.drawString(EJC_Util.round(f.projectileSpeed,2)+ "dmg speed", (int) f.x, (int) f.y-30);
		}	
	}
}