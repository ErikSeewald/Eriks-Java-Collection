package Coordinates3D;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;

import Main.MainMenu;
import Main.WindowEventHandler;

public class Coordinates3DFrame extends JFrame implements ActionListener,MouseListener
{
	private static final long serialVersionUID = -1790901160968965409L;

	Coordinates3DPanel panel; 	//Grafikpanel
	
	JLabel extend;		//Ebene zu Quader    Button
	JLabel move;	//Ebenen Bewegung    Button
	JLabel hide;	//Achsen verstecken  Button
	
	JLabel Xb;		//X Bewegung+  Button
	JLabel Yb;		//Y Bewegung+  Button
	JLabel Zb;		//Z Bewegung+  Button
	JLabel Xb1;		//X Bewegung-  Button
	JLabel Yb1;		//Y Bewegung-  Button
	JLabel Zb1;		//Z Bewegung-  Button
	
	JLabel AL;		//Punkt A Daten Display
	JLabel BL;		//Punkt B Daten Display
	JLabel CL;		//Punkt C Daten Display
	JLabel DL;		//Punkt D Daten Display
	
	JLabel NL;		//Ebene E Daten Display
	
    static Timer timer;
	
	int[][] R = new int[5][3];	//2D Array für Austausch von Daten zwischen MyFrame und MyPanel
	
	boolean rotating = false;	//Boolean zum stoppen der Drehbewegung
	
	boolean inside = false;		//Boolean check, ob die Maus gerade in einem Button ist
	
	Color buttonc;				//Dunkle Buttonfarbe
	Color buttond;				//Mittlere Buttonfarbe
	Color buttone;				//Helle Buttonfarbe
	Color border;				//Border Farbe
	
	boolean first = true;		//Booleans zum wechsel zwischen Achsen sichtbar und unsichtbar
	boolean hidden = false;
	
	Coordinates3DFrame(int[] a, int[]b, int[]c, WindowEventHandler eventHandler)
	{
		
		timer = new Timer(125, this);
		timer.addActionListener(this);
		
		this.addWindowListener(eventHandler);
		this.setIconImage(MainMenu.img.getImage());
		this.setTitle("3D Coordinates");
		
		panel = new Coordinates3DPanel(a,b,c); //Weitergabe der Daten der 3 Punkte
		
		buttonc = new Color(170,170,190);
		buttond = new Color(200,200,220);
		buttone = new Color(220,220,240);
		border = new Color(100,100,120);
		
		extend = new JLabel("  Quader");
		extend.setBounds(10,5,70,40);
		setBasics(extend);
		
		move = new JLabel("       ?");
		move.setBounds(500,5,70,40);
		setBasics(move);
		
		hide = new JLabel("  Achsen");
		hide.setBounds(10,553,70,40);
		setBasics(hide);
		
		Xb = new JLabel("    X+");
		Xb.setBounds(210, 553,55,40);
		setBasics(Xb);
		
		Xb1 = new JLabel("    X-");
		Xb1.setBounds(270,553,55,40);
		setBasics(Xb1);
		
		Yb = new JLabel("    Y+");
		Yb.setBounds(340,553,55,40);
		setBasics(Yb);
		
		Yb1 = new JLabel("    Y-");
		Yb1.setBounds(400,553,55,40);
		setBasics(Yb1);
		
		Zb = new JLabel("    Z+");
		Zb.setBounds(470,553,55,40);
		setBasics(Zb);
		
		Zb1 = new JLabel("    Z-");
		Zb1.setBounds(530,553,55,40);
		setBasics(Zb1);
		
		AL = new JLabel();
		AL.setBounds(100,14,85,15);
		
		BL = new JLabel();
		BL.setBounds(100,34,85,15);
		
		CL = new JLabel();
		CL.setBounds(185,14,85,15);
		
		DL = new JLabel();
		DL.setBounds(185,34,85,15);
		
		NL = new JLabel();
		NL.setBounds(270,21,250,15);
		
		setValues();
		
		this.setSize(615,635);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setLayout(null);
		
		this.add(AL);
		this.add(BL);
		this.add(CL);
		this.add(DL);
		this.add(NL);
		this.add(panel);
		
		this.setVisible(true);
	}

	public static void stop()
	{if (timer.isRunning()) {timer.stop();}}
	
	public void setBasics(JLabel x)
	{
		x.setFont(new Font ("", Font.PLAIN, 15));
		x.setBackground(buttonc);
		x.setOpaque(true);
		x.setBorder(BorderFactory.createLineBorder(border, 2));
		x.addMouseListener(this);
		this.add(x);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		
		if (e.getSource()==timer) 
		{
			panel.move();				//Timer event zur animation der Ebenenbewegung
			setValues();
		}
	
	}
	
	public void setValues() 
	{
		
		R = panel.getValues();
		AL.setText("A ("+R[0][0]+"|"+R[0][1]+"|"+R[0][2]+")");
		BL.setText("B ("+R[1][0]+"|"+R[1][1]+"|"+R[1][2]+")");
		CL.setText("C ("+R[2][0]+"|"+R[2][1]+"|"+R[2][2]+")");		//Gibt die neuen Werte an die Displays weiter
		DL.setText("D ("+R[3][0]+"|"+R[3][1]+"|"+R[3][2]+")");
		char second = '+';char third = '+';
		if (R[4][1]<0) {second = '-';}
		if (R[4][2]<0) {third = '-';}
		NL.setText("Ebene E: "+R[4][0]+"x "+ second + " "+Math.abs(R[4][1])+"y "+third+" "+Math.abs(R[4][2])+"z = "+R[5][0]);	
		
	}

	@Override
	public void mouseClicked(MouseEvent e) 
	{ 
		
		
		if (e.getSource()==hide) 
		{
			if (first) 
			{hidden = true; first=false;}
				
			else {hidden=false;first=true;}		//Schalter zwischen versteckten und sichtbaren Achsen
		
			panel.hide(hidden);
			hide.setBounds(10,553,70,40);	//Reset der Buttongröße nach dem Klick
	
			if (inside == true) 
			{hide.setBackground(buttond);} //Reset der Buttonfarbe auf Mittel nach dem Klick
			else  
			{hide.setBackground(buttonc);} //Falls die Maus den Button während dem Drücken verlassen hat, Reset auf Dunkel
		}

		
		if (e.getSource()==extend) 
		{	
			extend.setBounds(10,5,70,40);	
			panel.extend();
	
			if (inside == true) 
			{extend.setBackground(buttond);}
			else  
			{extend.setBackground(buttonc);}
		}
		
		
		else if (e.getSource()==move) 
		{	
			move.setBounds(500,5,70,40);
		
			if(rotating) 
			{timer.stop();rotating=false;}
			else 
			{timer.start(); rotating = true;}
		
			if (inside == true) 
			{move.setBackground(buttond);}
			else  
			{move.setBackground(buttonc);}
		}
		
		
		else if (e.getSource()==Xb) 
		{	
			Xb.setBounds(210, 553,55,40);	
			panel.movenum(1);
			setValues();
			
			if (inside == true) 
			{Xb.setBackground(buttond);}
			else  
			{Xb.setBackground(buttonc);}
		}
		
		
		else if (e.getSource()==Xb1) 
		{	
			Xb1.setBounds(270,553,55,40);	
			panel.movenum(4);
			setValues();
			
			if (inside == true) 
			{Xb1.setBackground(buttond);}
			else  
			{Xb1.setBackground(buttonc);}
		}
		
		
		else if (e.getSource()==Yb) 
		{	
			Yb.setBounds(340,553,55,40);	
			panel.movenum(2);
			setValues();
				
			if (inside == true) 
			{Yb.setBackground(buttond);}
			else  
			{Yb.setBackground(buttonc);}
		}
			
			
		else if (e.getSource()==Yb1) 
		{	
			Yb1.setBounds(400,553,55,40);	
			panel.movenum(5);
			setValues();
				
			if (inside == true) 
			{Yb1.setBackground(buttond);}
			else  
			{Yb1.setBackground(buttonc);}
		}
		
		
		else if (e.getSource()==Zb) 
		{	
			Zb.setBounds(470,553,55,40);	
			panel.movenum(3);
			setValues();
				
			if (inside == true) 
			{Zb.setBackground(buttond);}
			else  
			{Zb.setBackground(buttonc);}
		}
			
			
		else if (e.getSource()==Zb1) 
		{	
			Zb1.setBounds(530,553,55,40);	
			panel.movenum(6);
			setValues();
				
			if (inside == true) 
			{Zb1.setBackground(buttond);}
			else  
			{Zb1.setBackground(buttonc);}
		}
	}
		
	@Override
	public void mousePressed(MouseEvent e) 
	{
		
		if (e.getSource()==hide) //Button verkeinern während click
		{
		hide.setBackground(buttone);
		hide.setBounds(11,554,68,38);
		} 
		
		else if (e.getSource()==extend) 
		{
		extend.setBackground(buttone);
		extend.setBounds(11,6,68,38);
		}
		
		else if (e.getSource()==move) 
		{
		move.setBackground(buttone);
		move.setBounds(501,6,68,38);
		}
		
		else if (e.getSource()==Xb) 
		{
		Xb.setBackground(buttone);
		Xb.setBounds(211, 554,53,38);
		}
		
		else if (e.getSource()==Xb1) 
		{
		Xb1.setBackground(buttone);
		Xb1.setBounds(271,554,53,38);
		}
		
		else if (e.getSource()==Yb) 
		{
		Yb.setBackground(buttone);
		Yb.setBounds(341,554,53,38);
		}
		
		else if (e.getSource()==Yb1) 
		{
		Yb1.setBackground(buttone);
		Yb1.setBounds(401,554,53,38);
		}
		
		else if (e.getSource()==Zb) 
		{
		Zb.setBackground(buttone);
		Zb.setBounds(471,554,53,38);
		}
		
		else if (e.getSource()==Zb1) 
		{
		Zb1.setBackground(buttone);
		Zb1.setBounds(531,554,53,38);
		}
	
	}

	@Override
	public void mouseReleased(MouseEvent e) {}	
	
	@Override
	public void mouseEntered(MouseEvent e) 
	{
		
		if (e.getSource()==hide)  //Button vergrößern beim Entern
		{
			hide.setBackground(buttond); 
			hide.setBounds(8,551,73,43);
		}
		
			else if (e.getSource()==extend) 
			{
			extend.setBackground(buttond);
			extend.setBounds(8,3,73,43);
			}
			
			else if (e.getSource()==move) 
			{
			move.setBackground(buttond);
			move.setBounds(498,3,73,43);
			}
			
			else if (e.getSource()==Xb) 
			{
			Xb.setBackground(buttond);
			Xb.setBounds(208, 551,58,43);
			}
			
			else if (e.getSource()==Xb1) 
			{
			Xb1.setBackground(buttond);
			Xb1.setBounds(268,551,58,43);
			}
			
			else if (e.getSource()==Yb) 
			{
			Yb.setBackground(buttond);
			Yb.setBounds(338,551,58,43);
			}
			
			else if (e.getSource()==Yb1) 
			{
			Yb1.setBackground(buttond);
			Yb1.setBounds(398,551,58,43);
			}
			
			else if (e.getSource()==Zb) 
			{
			Zb.setBackground(buttond);
			Zb.setBounds(468,551,58,43);
			}
			
			else if (e.getSource()==Zb1) 
			{
			Zb1.setBackground(buttond);
			Zb1.setBounds(528,551,58,43);
			}
		
		inside = true;
	}

	@Override
	public void mouseExited(MouseEvent e) 
	{
		
		if (e.getSource()==hide) //Beim Verlassen die Buttongröße wieder resetten
		{
			hide.setBackground(buttonc);
			hide .setBounds(10,553,70,40);
		} 
		
			else if (e.getSource()==extend) 
			{
			extend.setBackground(buttonc);
			extend.setBounds(10,5,70,40);
			}
			
			else if (e.getSource()==move) 
			{
			move.setBackground(buttonc);
			move.setBounds(500,5,70,40);
			}
			
			else if (e.getSource()==Xb) 
			{
			Xb.setBackground(buttonc);
			Xb.setBounds(210, 553,55,40);
			}
			
			else if (e.getSource()==Xb1) 
			{
			Xb1.setBackground(buttonc);
			Xb1.setBounds(270,553,55,40);
			}
			
			else if (e.getSource()==Yb) 
			{
			Yb.setBackground(buttonc);
			Yb.setBounds(340,553,55,40);
			}
			
			else if (e.getSource()==Yb1) 
			{
			Yb1.setBackground(buttonc);
			Yb1.setBounds(400,553,55,40);
			}
			
			else if (e.getSource()==Zb) 
			{
			Zb.setBackground(buttonc);
			Zb.setBounds(470,553,55,40);
			}
			
			else if (e.getSource()==Zb1) 
			{
			Zb1.setBackground(buttonc);
			Zb1.setBounds(530,553,55,40);
			}
		
		inside = false;
	}
}
