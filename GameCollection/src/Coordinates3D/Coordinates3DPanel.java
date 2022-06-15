package Coordinates3D;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

public class Coordinates3DPanel extends JPanel 
{

	private static final long serialVersionUID = -3003036693212844492L;
	
	int pushX = 0; 
	int pushY = 0; //Integers zum Verschieben der Striche auf den Achsen
	int pushZ = 0;
	
	int[] A = new int [3];
	int[] B = new int [3]; 		//Koordinaten der Punkte
	int[] C = new int [3];
	int[] D = new int [3];
	

	int[] A1 = new int [2];
	int[] B1 = new int [2]; 	//Punkte in Pixelkoordinaten
	int[] C1 = new int [2];
	int[] D1 = new int [2];
		
	
	int[] W = new int [3];
	int[] X = new int [3]; 		//Punkte zur verlängerung der Ebene
	int[] Y = new int [3];
	int[] Z = new int [3];
	
	int[] W1 = new int [2];
	int[] X1 = new int [2]; 		
	int[] Y1 = new int [2];
	int[] Z1 = new int [2];
	
	int[] n = new int[3]; 		//Nomale
	
	int max; //Punkt mit größstem anliegendem Winkel
	
	boolean ext = false; //Verlängerung der Ebene in ein Quader
	
	int E; // Wert D für Koordinatenform der Ebene
	
	int rotate = 0;	//Wert, um verschiedene Phasen der Drehbewegung der Ebene zu bestimmen
	
	boolean hide = false;	//Boolean für das verstecken der Achsen

	
	Coordinates3DPanel(int[] a, int[]b, int[]c)
	{
		
		this.A = a;
		this.B = b;
		this.C = c;	
		
		this.setBounds(50,50,500,500);
		
		this.calculation();
		
	}
	
	public void calculation() 
	{
		
		//Berechnung von Punkt D
		
		//Vekoren Berechnen
		
		int[] CB = new int[3];
		CB[0] = B[0]-C[0]; CB[1] = B[1]-C[1]; CB[2] = B[2]-C[2]; //CB
		
		int[] BC = new int[3];
		BC[0] = C[0]-B[0]; BC[1] = C[1]-B[1]; BC[2] = C[2]-B[2]; //BC
		
		
		int[] CA = new int[3];
		CA[0] = A[0]-C[0]; CA[1] = A[1]-C[1]; CA[2] = A[2]-C[2]; //CA
		
		int[] AC = new int[3];
		AC[0] = C[0]-A[0]; AC[1] = C[1]-A[1]; AC[2] = C[2]-A[2]; //AC
		
		
		int[] BA = new int[3];
		BA[0] = A[0]-B[0]; BA[1] = A[1]-B[1]; BA[2] = A[2]-B[2]; //BA
		
		int[] AB = new int[3];
		AB[0] = B[0]-A[0]; AB[1] = B[1]-A[1]; AB[2] = B[2]-A[2]; //AB
		
		//Punkt mit größtem Winkel finden
		
		int skalar1 = AB[0] * AC[0] + AB[1] * AC[1] + AB[2] * AC[2];
		double u1 = Math.sqrt(Math.pow(AB[0], 2) + Math.pow(AB[1], 2) + Math.pow(AB[2], 2)); //Punkt A
		double v1 = Math.sqrt(Math.pow(AC[0], 2) + Math.pow(AC[1], 2) + Math.pow(AC[2], 2));
		
		double alpha1 = (Math.acos(skalar1 / (u1*v1)))*180/3.1416; // durch Pi
		
		
		int skalar2 = BA[0] * BC[0] + BA[1] * BC[1] + BA[2] * BC[2];
		double u2 = Math.sqrt(Math.pow(BA[0], 2) + Math.pow(BA[1], 2) + Math.pow(BA[2], 2)); //Punkt B
		double v2 = Math.sqrt(Math.pow(BC[0], 2) + Math.pow(BC[1], 2) + Math.pow(BC[2], 2));
		
		double alpha2 = (Math.acos(skalar2 / (u2*v2))*180/3.1416);;
		
	
		int skalar3 = CB[0] * CA[0] + CB[1] * CA[1] + CB[2] * CA[2];
		double u3 = Math.sqrt(Math.pow(CB[0], 2) + Math.pow(CB[1], 2) + Math.pow(CB[2], 2)); //Punkt C
		double v3 = Math.sqrt(Math.pow(CA[0], 2) + Math.pow(CA[1], 2) + Math.pow(CA[2], 2));
		
		double alpha3 = (Math.acos(skalar3 / (u3*v3)))*180/3.1416;
		
		if (alpha1 > alpha2 && alpha1 > alpha3) {max=1;} 
		else if (alpha2 > alpha1 && alpha2 > alpha3) {max=2;} //max 1 -> Punkt A usw.
		else {max=3;}
		
		
		//Den am Punkt mit größtem Winkel unbeteiligten Vektor an diesem ansetzen, um Punkt D zu berechnen
		
		switch (max) 
		{
		case 1: D[0] = AB[0] + C[0]; D[1] = AB[1] + C[1]; D[2] = AB[2] + C[2];
		break;
		case 2: D[0] = BC[0] + A[0]; D[1] = BC[1] + A[1]; D[2] = BC[2] + A[2]; 
		break;
		case 3: D[0] = CA[0] + B[0]; D[1] = CA[1] + B[1]; D[2] = CA[2] + B[2]; 
		break;
		}
		
		//Normalvektor = Kreuzprodukt AB & AC
		//int[] n = new int[3];
		
		n[0] = (AB[1] * AC[2]) - (AB[2] * AC[1]);
		n[1] = (AB[2] * AC[0]) - (AB[0] * AC[2]); 
		n[2] = (AB[0] * AC[1]) - (AB[1] * AC[0]);
		
		//Größter Gemeinsame Teiler zum kürzen:
		
		int i;
		for (i = 1000; i>=1; i--) 
		{if((n[0]%i==0) && (n[1]%i==0) && (n[2]%i==0)) {break;}}
		
		
		n[0]= n[0]/i;
		n[1]= n[1]/i;
		n[2]= n[2]/i;
		
		
		//D von Ebene E
		
		E = (n[0]*A[0])+(n[1]*A[1])+(n[2]*A[2]);
					
		//Verlängerung in ein Quader
		
		W[0] = A[0] + (n[0]*5);
		W[1] = A[1] + (n[1]*5);
		W[2] = A[2] + (n[2]*5);
		
		X[0] = B[0] + (n[0]*5);
		X[1] = B[1] + (n[1]*5);
		X[2] = B[2] + (n[2]*5);
		
		Y[0] = C[0] + (n[0]*5);
		Y[1] = C[1] + (n[1]*5);
		Y[2] = C[2] + (n[2]*5);
		
		Z[0] = D[0] + (n[0]*5);
		Z[1] = D[1] + (n[1]*5);
		Z[2] = D[2] + (n[2]*5);
		
		//Umwandlung in Pixelkoordinaten (X250,Y250 ist (0/0/0), steigender Y Wert -> Verschiebung nach unten
		
		A1[0] = (250-(A[0]*5)) + (A[1]*10); //X Achse geht in x und y Richtung, also bei beiden Einfügen
		A1[1] = (250+(A[0]*5)) - (A[2]*10);	
		
		B1[0] = (250-(B[0]*5)) + (B[1]*10); 
		B1[1] = (250+(B[0]*5)) - (B[2]*10);
		
		C1[0] = (250-(C[0]*5)) + (C[1]*10); 
		C1[1] = (250+(C[0]*5)) - (C[2]*10);
		
		D1[0] = (250-(D[0]*5)) + (D[1]*10); 
		D1[1] = (250+(D[0]*5)) - (D[2]*10);
		
		
		W1[0] = (250-(W[0]*5)) + (W[1]*10); 
		W1[1] = (250+(W[0]*5)) - (W[2]*10);
		
		X1[0] = (250-(X[0]*5)) + (X[1]*10); 
		X1[1] = (250+(X[0]*5)) - (X[2]*10);
		
		Y1[0] = (250-(Y[0]*5)) + (Y[1]*10); 
		Y1[1] = (250+(Y[0]*5)) - (Y[2]*10);
		
		Z1[0] = (250-(Z[0]*5)) + (Z[1]*10); 
		Z1[1] = (250+(Z[0]*5)) - (Z[2]*10); 
		
	} 
		
	
	//Einzeichnen
	
	public void paint(Graphics g) 
	{
		
		super.paint(g);
		
		Graphics2D g2D = (Graphics2D) g;
		
		g2D.setStroke(new BasicStroke(2));
		
		if (hide==false) 
		{ //Verstecken der Achsen
		
			g2D.drawLine(450, 50, 50, 450); //X-Achse
			g2D.drawLine(0, 250, 500, 250); //Y-Achse
			g2D.drawLine(250, 0, 250, 500); //Z-Achse
		
			g2D.setStroke(new BasicStroke(1));
				
			for (int i = 0; i < 39; i++) //X-Achse Nummern
			{	
			pushX = pushX +5;
			g2D.drawLine(450-pushX,45+pushX ,450-pushX,55+pushX); 	//Negativ
			g2D.drawLine(250-pushX,245+pushX ,250-pushX,255+pushX); //Positiv
			}
		
		
			for (int i = 0; i < 49; i++) //Y-Achse Nummern
			{										
			pushY = pushY +10;
			g2D.drawLine(pushY, 253,pushY, 247);
			}
		
			for (int i = 0; i < 49; i++) //Z-Achse Nummern
			{												
			pushZ = pushZ +10;
			g2D.drawLine(253,pushZ ,247,pushZ);
			}
	
			g2D.setFont(new Font("", Font.PLAIN, 20));
			g2D.drawString("x", 60, 460);
			g2D.drawString("y", 490, 240);				//Namen der Achsen
			g2D.drawString("z", 258, 10);
		
		
		}
		
		g2D.setStroke(new BasicStroke(3)); 
		g2D.setPaint(Color.GRAY);
	
		//Zeichnen nur der äußeren Verbindungslinien (Für jede Lage des größten Winkels anders)
	
		switch (max) 
		{ 
		case 1: g2D.drawLine(A1[0],A1[1],B1[0],B1[1]); g2D.drawLine(A1[0],A1[1],C1[0],C1[1]); //AB,AC,BD,CD
				g2D.drawLine(C1[0],C1[1],D1[0],D1[1]); g2D.drawLine(B1[0],B1[1],D1[0],D1[1]);
				if (ext==true) { //Verlängerung auf ein Quader
				g2D.drawLine(W1[0],W1[1], X1[0], X1[1]); g2D.drawLine(W1[0],W1[1], Y1[0], Y1[1]);
				g2D.drawLine(Y1[0],Y1[1], Z1[0], Z1[1]);g2D.drawLine(Z1[0],Z1[1], X1[0], X1[1]);}
		break;
		case 2: g2D.drawLine(A1[0],A1[1],B1[0],B1[1]); g2D.drawLine(A1[0],A1[1],D1[0],D1[1]); //AB,AD,BC,CD
				g2D.drawLine(C1[0],C1[1],D1[0],D1[1]); g2D.drawLine(B1[0],B1[1],C1[0],C1[1]);
				if (ext==true) {
				g2D.drawLine(W1[0],W1[1], X1[0], X1[1]);g2D.drawLine(W1[0],W1[1], Z1[0], Z1[1]);
				g2D.drawLine(Y1[0],Y1[1], Z1[0], Z1[1]); g2D.drawLine(X1[0],X1[1], Y1[0], Y1[1]);}
		break;
		case 3: g2D.drawLine(B1[0],B1[1],D1[0],D1[1]); g2D.drawLine(A1[0],A1[1],C1[0],C1[1]); //AC,AD,BC,BD
				g2D.drawLine(B1[0],B1[1],C1[0],C1[1]); g2D.drawLine(A1[0],A1[1],D1[0],D1[1]);
				if (ext==true) {
				g2D.drawLine(X1[0],X1[1], Z1[0], Z1[1]);g2D.drawLine(W1[0],W1[1], Y1[0], Y1[1]);
				g2D.drawLine(X1[0],X1[1], Y1[0], Y1[1]); g2D.drawLine(W1[0],W1[1], Z1[0], Z1[1]);}
		break;
	
		}
	
		if (ext==true) 
		{
			g2D.drawLine(A1[0],A1[1], W1[0], W1[1]); //W
			g2D.drawLine(B1[0],B1[1], X1[0], X1[1]); //X
			g2D.drawLine(C1[0],C1[1], Y1[0], Y1[1]); //Y
			g2D.drawLine(D1[0],D1[1], Z1[0], Z1[1]); //Z
		}
	
		g2D.setFont(new Font("Liberation Mono", Font.BOLD,18));
	
		g2D.setPaint(new Color (220,20,20));
		g2D.drawString("A", A1[0]-2, A1[1]);
		g2D.drawString("B", B1[0]-2, B1[1]); //Zeichnen der Punkt Namen
		g2D.drawString("C", C1[0]-2, C1[1]);
		g2D.drawString("D", D1[0]-2, D1[1]);

	
		pushX = 0; 
		pushY = 0; //Reset
		pushZ = 0;
	}
	
	
	public void extend() 
	{
		if (ext == false) {ext = true;}		//Wechsel zwischen Verlängerung auf Quader und normaler Ebene
		else {ext = false;}
		
		repaint();	
	}
	
	public void move() 
	{ 	
		//Kein D, weil es jedes mal eh neu berechnet wird
		if (rotate<12) 		{C[0]++; A[0]--; B[1]++; rotate++;}		
		else if (rotate<24) {B[0]++; C[1]--; A[1]++; rotate++;}
		else if (rotate<36) {A[0]++; B[1]--; C[0]--;rotate++;}	
		else if (rotate<48) {A[1]--; B[0]--; C[1]++; rotate++;}	
		else {rotate=0;}
	
		this.calculation();	repaint();	
	}

	public void movenum(int t) 
	{
	
		switch (t) 
		{
		case 1: A[0]++;B[0]++;C[0]++;		//1-3 XYZ positiv Bewegung, 4-6 negativ
		break;
		case 2: A[1]++;B[1]++;C[1]++;
		break;
		case 3: A[2]++;B[2]++;C[2]++;
		break;
		case 4: A[0]--;B[0]--;C[0]--;
		break;
		case 5: A[1]--;B[1]--;C[1]--;
		break;
		case 6: A[2]--;B[2]--;C[2]--;
		break;
		}
	
		this.calculation();	repaint();
	}
	
	public int[][] getValues() 
	{
	
		int[][] R = new int[6][3];
		R[0][0] = A[0];
		R[0][1] = A[1];		//A
		R[0][2] = A[2];
	
		R[1][0] = B[0];
		R[1][1] = B[1];		//B
		R[1][2] = B[2];
	
		R[2][0] = C[0];
		R[2][1] = C[1];		//C
		R[2][2] = C[2];
	
		R[3][0] = D[0];
		R[3][1] = D[1];		//D
		R[3][2] = D[2];
	
		R[4][0] = n[0]; 
		R[4][1] = n[1];		//n
		R[4][2] = n[2];
	
	
		R[5][0] = E;		//D von Ebene E
	
		return R;	
	}
	
	public void hide(boolean h) 
	{this.hide = h; repaint();}		//Empfangen des Booleans zum Verstecken der Achsen		
	
}
