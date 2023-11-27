package musicalLogicGates.graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import javax.swing.JPanel;
import musicalLogicGates.circuit.CircuitManager;
import musicalLogicGates.gates.Gate;

public class CircuitPanel extends JPanel
{
	private static final long serialVersionUID = -1401186262381029504L;

	public static final int PANEL_HEIGHT = 800;
	public static final int PANEL_WIDTH = (int) (PANEL_HEIGHT * 1.4);
	
	private CircuitManager circuitManager;
	private MouseHandler mouseHandler;
	
	public CircuitPanel(CircuitManager circuitManager) 
	{
		if (circuitManager == null)
		{throw new IllegalArgumentException("circuitManager cannot be null!");}
		
		this.circuitManager = circuitManager;
		
		this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		
		mouseHandler = new MouseHandler(this, circuitManager);
		this.addMouseListener(mouseHandler.new ClickListener());
		this.addMouseMotionListener(mouseHandler.new DragListener());
		this.addMouseListener(mouseHandler.new ReleaseListener());
	}
	
	
	//---------------------------------------PAINT---------------------------------------
	
	private static final Color background_col = new Color(60,60,80);
	private static final Color AND_COLOR = new Color(180, 180, 255);
	private static final Color OR_COLOR = new Color(180, 255, 180);
	private static final Color XOR_COLOR = new Color(255, 180, 180);
	
	private static final Area not_circle_area = new Area(new Ellipse2D.Double(-40,0, 12, 12));
	private static final Area and_area = new Area(new Arc2D.Double(-40, 0, 80, 50, -90, 180, Arc2D.PIE));
	private static final Area nand_area; 
	private static final Area or_area;
	private static final Area nor_area;
	private static final Area xor_area;
	private static final Area xnor_area;
			
	static 
	{
		//NAND
		nand_area = new Area(and_area);
		nand_area.add(not_circle_area.createTransformedArea(new AffineTransform(1, 0, 0, 1, 79, 20)));
		
		//OR
		or_area = new Area(new Arc2D.Double(-40, 0, 80, 50, -90, 180, Arc2D.PIE));
		Shape mask = new Arc2D.Double(-40, 0, 50, 60, -90, 180, Arc2D.PIE);
		or_area.subtract(new Area(mask).createTransformedArea(new AffineTransform(1, 0, 0, 1, 0, -5)));
		
		//NOR
		nor_area = new Area(or_area);
		nor_area.add(not_circle_area.createTransformedArea(new AffineTransform(1, 0, 0, 1, 79, 20)));
		
		//XOR
		xor_area = new Area(or_area).createTransformedArea(new AffineTransform(1, 0, 0, 1, 10, 0));
		Shape xorArc = new Arc2D.Double(-5, 2, 15, 45, -90, 180, Arc2D.PIE);
		xor_area.add(new Area(xorArc));
		
		//XNOR
		xnor_area = new Area(xor_area);
		xnor_area.add(not_circle_area.createTransformedArea(new AffineTransform(1, 0, 0, 1, 88, 20)));
	}
	
	public void updateGraphics()
	{
		repaint();
	}
	
	@Override
	public void paint(Graphics g) 
	{
		Graphics2D g2D = (Graphics2D) g;
		
		//BACKGROUND
		g2D.setPaint(background_col);
		g2D.fillRect(0, 0, PANEL_WIDTH, PANEL_HEIGHT);
		
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		for (Gate gate : circuitManager.getGates())
		{
			drawGate(gate, g2D);
		}
	}
	
	//GATES
	public void drawGate(Gate gate, Graphics2D g2D) 
	{
		if (gate == null) 
		{throw new IllegalArgumentException("gate cannot be null");}
		
		switch (gate.getType()) 
		{
			case AND : drawANDGate(gate, g2D); break;
			case NAND : drawNANDGate(gate, g2D); break;
			case NOR : drawNORGate(gate, g2D); break;
			case NULL_GATE : break;
			case OR : drawORGate(gate, g2D); break;
			case XNOR : drawXNORGate(gate, g2D); break;
			case XOR : drawXORGate(gate, g2D); break;
			default : break;
		}
	}
	
	public void drawANDGate(Gate gate, Graphics2D g2D)
	{
		g2D.setPaint(AND_COLOR);
		g2D.fill(and_area.createTransformedArea(new AffineTransform(1, 0, 0, 1, gate.x, gate.y)));
	}
	
	public void drawNANDGate(Gate gate, Graphics2D g2D)
	{
		g2D.setPaint(AND_COLOR);
		g2D.fill(nand_area.createTransformedArea(new AffineTransform(1, 0, 0, 1, gate.x, gate.y)));
	}
	
	public void drawORGate(Gate gate, Graphics2D g2D)
	{
		g2D.setPaint(OR_COLOR);
		g2D.fill(or_area.createTransformedArea(new AffineTransform(1, 0, 0, 1, gate.x, gate.y)));
	}
	
	public void drawNORGate(Gate gate, Graphics2D g2D)
	{
		g2D.setPaint(OR_COLOR);
		g2D.fill(nor_area.createTransformedArea(new AffineTransform(1, 0, 0, 1, gate.x, gate.y)));
	}
	
	public void drawXORGate(Gate gate, Graphics2D g2D)
	{
		g2D.setPaint(XOR_COLOR);
		g2D.fill(xor_area.createTransformedArea(new AffineTransform(1, 0, 0, 1, gate.x, gate.y)));
	}
	
	public void drawXNORGate(Gate gate, Graphics2D g2D)
	{
		g2D.setPaint(XOR_COLOR);
		g2D.fill(xnor_area.createTransformedArea(new AffineTransform(1, 0, 0, 1, gate.x, gate.y)));
	}
}
