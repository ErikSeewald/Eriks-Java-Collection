package musicalLogicGates.graphics;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import javax.swing.JPanel;
import musicalLogicGates.circuit.CircuitManager;
import musicalLogicGates.gates.Gate;
import musicalLogicGates.gates.Gate.GateType;
import musicalLogicGates.gates.NullGate;

/**
 * Class extending {@link JPanel} to handle the visualization of a circuit.
 */
public class CircuitPanel extends JPanel
{
	private static final long serialVersionUID = -1401186262381029504L;

	public static final int PANEL_HEIGHT = 800;
	public static final int PANEL_WIDTH = (int) (PANEL_HEIGHT * 1.4);
	
	private CircuitManager circuitManager;
	private MouseHandler mouseHandler;
	
	/**
	 * Creates a new {@link CircuitPanel} with the given {@link CircuitManager}
	 * 
	 * @param circuitManager the {@link CircuitManager} for this {@link CircuitPanel}
	 * @throws IllegalArgumentException if the {@link CircuitManager} is {@literal null}
	 */
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
	
	//COLORS
	private static final Color background_col = new Color(60,60,80);
	private static final Color AND_COLOR = new Color(180, 180, 255);
	private static final Color OR_COLOR = new Color(180, 255, 180);
	private static final Color XOR_COLOR = new Color(255, 180, 180);
	private static final Color CONNECT_COLOR_OFF = new Color(20, 20, 30);
	private static final Color CONNECT_COLOR_ON = new Color(255, 255, 180);
	
	//OFFSET POSITIONS TO DETERMINE WHERE THE OUTPUT CONNECTOR OF SHOULD BE DRAWN FOR DIFFERENT GATES
	private static final int AND_OUT_POS_X = 39;
	private static final int NAND_OUT_POS_X = 50;
    private static final int XNOR_OUT_POS_X = 60;
    private static final int IN_OUT_POS_X = 15;
    private static final int NOT_OUT_POS_X = 44;

    public static final int OUT_POS_Y = 26;
    
    //Y OFFSET BETWEEN THE FIRST AND SECOND INPUT CONNECTOR OF A GATE 
    private static final int INPUT_1_2_OFFSET = 10;
    
    //AREAS USED TO DRAW GATES
	private static final Area not_circle_area = new Area(new Ellipse2D.Double(-40,0, 12, 12));
	private static final Area and_area = new Area(new Arc2D.Double(-40, 0, 80, 50, -90, 180, Arc2D.PIE));
	private static final Area nand_area; 
	private static final Area or_area;
	private static final Area nor_area;
	private static final Area xor_area;
	private static final Area xnor_area;
	private static final Area not_area;
			
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
		
		//NOT
		not_area = new Area(new Polygon(new int[] {0, 0, 40}, new int[] {0, 30, 15}, 3));
		not_area.add(not_circle_area.createTransformedArea(new AffineTransform(1, 0, 0, 1, 78, 9)));
	}
	
	/**
	 * Updates the graphics of the circuit.
	 */
	public void updateGraphics() {repaint();}
	
	@Override
	public void paint(Graphics g) 
	{
		Graphics2D g2D = (Graphics2D) g;
		
		//BACKGROUND
		g2D.setPaint(background_col);
		g2D.fillRect(0, 0, PANEL_WIDTH, PANEL_HEIGHT);
		
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		//CONNECTING MODE
        g2D.setStroke(new BasicStroke(3));
        g2D.setPaint(CONNECT_COLOR_OFF);
        if (mouseHandler.isConnecting())
        {
            Gate selected = mouseHandler.getSelectedGate();
                
            int outPosX = getOutPosX(selected.getType());
            int outY = getOutY(selected.getType(), selected.y);    
            
            g2D.drawLine(selected.x + outPosX, outY + OUT_POS_Y, mouseHandler.getMouseX(), mouseHandler.getMouseY());
        }
         
        //CONNECTIONS
        for (Gate gate : circuitManager.getGates())
        {
        	boolean isInput1 = true;
        	
            if (!(gate.getInput1() instanceof NullGate))
            {
                drawConnection(gate.getInput1(), gate, g2D, isInput1);
            }
            
            if (!(gate.getInput2() instanceof NullGate))
            {
            	isInput1 = false;
                drawConnection(gate.getInput2(), gate, g2D, isInput1);
            }
        }
		
        //GATES
		for (Gate gate : circuitManager.getGates())
		{
			drawGate(gate, g2D);
		}
	}
	
	//CONNECTIONS
	/**
	 * Draw a connection between the two given {@link Gates}.
	 * 
	 * @param out the out {@link Gate}
	 * @param in the in {@link Gate}
	 * @param g2D the {@link Graphics2D} context to render to
	 * @param isInput1 {@link boolean} representing whether the first or second input of a gate is being drawn
	 */
	private void drawConnection(Gate out, Gate in, Graphics2D g2D, boolean isInput1)
	{
		if (circuitManager.isPlaying())
		{g2D.setPaint(out.getPlayState() ? CONNECT_COLOR_ON : CONNECT_COLOR_OFF);}
		else
		{g2D.setPaint(out.output() ? CONNECT_COLOR_ON : CONNECT_COLOR_OFF);}
		
	    int outPosX = getOutPosX(out.getType());
	    int outY = getOutY(out.getType(), out.y);
	    int inY = isInput1 ? in.y + OUT_POS_Y - INPUT_1_2_OFFSET : in.y + OUT_POS_Y + INPUT_1_2_OFFSET;
	    
	    int centerX = (in.x - (out.x + outPosX)) / 2;
	    //OUT -> CENTER (out y)
	    g2D.drawLine(out.x + outPosX, outY + OUT_POS_Y, out.x + outPosX + centerX, outY + OUT_POS_Y);
	    
	    //CENTER (out y) -> CENTER (in y)
        g2D.drawLine(out.x + outPosX + centerX, outY + OUT_POS_Y, out.x + outPosX + centerX, inY);
        
        //CENTER (in y) -> IN
        int inX = getInX(in.getType(), in.x);
        g2D.drawLine(out.x + outPosX + centerX, inY, inX, inY);    
	}
	
	/**
	 * Returns the output y coordinate with an offset based on the {@link GateType} to render the connection at 
	 * the right place.
	 * 
	 * @param type the {@link GateType} to match the offset to
	 * @param y the original y coordinate
	 * @return the updated y coordinate
	 */
	private int getOutY(GateType type, int y)
	{
		return type.equals(GateType.IN) || type.equals(GateType.NOT) 
        		? y - 11 : y; 
	}
	
	/**
	 * Returns the input x coordinate with an offset based on the {@link GateType} to render the connection at 
	 * the right place.
	 * 
	 * @param type the {@link GateType} to match the offset to
	 * @param x the original x coordinate
	 * @return the updated x coordinate
	 */
	private int getInX(GateType type, int x)
	{
		return type == GateType.OR || type == GateType.NOR || type == GateType.OUT
        		? x + 8 : x;
	}
	
	/**
	 * Gets the correct x position offset for the output connector of the given {@link GateType}.
	 * 
	 * @param type the {@link GateType} to get the x position offset for
	 * @return x the x position offset
	 */
	private int getOutPosX(GateType type)
	{
        switch (type)
        {
            case AND : return AND_OUT_POS_X;
            case NAND : return NAND_OUT_POS_X;
            case OR : return AND_OUT_POS_X;
            case NOR : return NAND_OUT_POS_X;
            case XOR : return NAND_OUT_POS_X;
            case XNOR : return XNOR_OUT_POS_X;
			case NOT : return NOT_OUT_POS_X;
			case IN : return IN_OUT_POS_X;
			case OUT: return 0;
			case NULL_GATE : return 0;
            default : return 0;
        }
	}
	
	//GATES
	/**
	 * Draws the given {@link Gate} to the given {@link Graphics2D} context.
	 * 
	 * @param gate the {@link Gate} to render
	 * @param g2D the {@link Graphics2D} to render to
	 */
	private void drawGate(Gate gate, Graphics2D g2D) 
	{
		if (gate == null) 
		{throw new IllegalArgumentException("gate cannot be null");}
		
		//ANIMATION
		double sizeMultiplier = 1.0;
		if (gate.isAnimating()) 
		{
			gate.advanceAnimation();
			sizeMultiplier = 1.5;
		}
		
		//GATE TYPE
		switch (gate.getType()) 
		{
			case AND : drawANDGate(gate, g2D, sizeMultiplier); break;
			case NAND : drawNANDGate(gate, g2D, sizeMultiplier); break;
			case OR : drawORGate(gate, g2D, sizeMultiplier); break;
			case NOR : drawNORGate(gate, g2D, sizeMultiplier); break;
			case XOR : drawXORGate(gate, g2D, sizeMultiplier); break;
			case XNOR : drawXNORGate(gate, g2D, sizeMultiplier); break;
			case NOT : drawNOTGate(gate, g2D, sizeMultiplier); break;
			case IN : drawINGate(gate, g2D, sizeMultiplier); break;
			case OUT: drawOUTGate(gate, g2D, sizeMultiplier); break;
			case NULL_GATE : break;
			default : break;
		}
	}
	
	/**
	 * Draws a {@link AND} {@link Gate} to the given {@link Graphics2D} context.
	 * 
	 * @param gate the {@link Gate} to render
	 * @param g2D the {@link Graphics2D} context to render to
	 * @param sizeMultiplier {@link double} with which to multiply the {@link Gate}'s size
	 */
	private void drawANDGate(Gate gate, Graphics2D g2D, double sizeMultiplier)
	{
		g2D.setPaint(AND_COLOR);
		g2D.fill(and_area.createTransformedArea(
				new AffineTransform(sizeMultiplier, 0, 0, sizeMultiplier, 
						sizeMultiplier > 1.0 ? gate.x - 10 : gate.x, sizeMultiplier > 1.0 ? gate.y - 10 : gate.y)));
	}
	
	/**
	 * Draws a {@link NAND} {@link Gate} to the given {@link Graphics2D} context.
	 * 
	 * @param gate the {@link Gate} to render
	 * @param g2D the {@link Graphics2D} context to render to
	 * @param sizeMultiplier {@link double} with which to multiply the {@link Gate}'s size
	 */
	private void drawNANDGate(Gate gate, Graphics2D g2D, double sizeMultiplier)
	{
		g2D.setPaint(AND_COLOR);
		g2D.fill(nand_area.createTransformedArea(
				new AffineTransform(sizeMultiplier, 0, 0, sizeMultiplier, 
						sizeMultiplier > 1.0 ? gate.x - 10 : gate.x, sizeMultiplier > 1.0 ? gate.y - 10 : gate.y)));
	}
	
	/**
	 * Draws a {@link OR} {@link Gate} to the given {@link Graphics2D} context.
	 * 
	 * @param gate the {@link Gate} to render
	 * @param g2D the {@link Graphics2D} context to render to
	 * @param sizeMultiplier {@link double} with which to multiply the {@link Gate}'s size
	 */
	private void drawORGate(Gate gate, Graphics2D g2D, double sizeMultiplier)
	{
		g2D.setPaint(OR_COLOR);
		g2D.fill(or_area.createTransformedArea(
				new AffineTransform(sizeMultiplier, 0, 0, sizeMultiplier, 
						sizeMultiplier > 1.0 ? gate.x - 10 : gate.x, sizeMultiplier > 1.0 ? gate.y - 10 : gate.y)));
	}
	
	/**
	 * Draws a {@link NOR} {@link Gate} to the given {@link Graphics2D} context.
	 * 
	 * @param gate the {@link Gate} to render
	 * @param g2D the {@link Graphics2D} context to render to
	 * @param sizeMultiplier {@link double} with which to multiply the {@link Gate}'s size
	 */
	private void drawNORGate(Gate gate, Graphics2D g2D, double sizeMultiplier)
	{
		g2D.setPaint(OR_COLOR);
		g2D.fill(nor_area.createTransformedArea(
				new AffineTransform(sizeMultiplier, 0, 0, sizeMultiplier, 
						sizeMultiplier > 1.0 ? gate.x - 10 : gate.x, sizeMultiplier > 1.0 ? gate.y - 10 : gate.y)));
	}
	
	/**
	 * Draws a {@link XOR} {@link Gate} to the given {@link Graphics2D} context.
	 * 
	 * @param gate the {@link Gate} to render
	 * @param g2D the {@link Graphics2D} context to render to
	 * @param sizeMultiplier {@link double} with which to multiply the {@link Gate}'s size
	 */
	private void drawXORGate(Gate gate, Graphics2D g2D, double sizeMultiplier)
	{
		g2D.setPaint(XOR_COLOR);
		g2D.fill(xor_area.createTransformedArea(
				new AffineTransform(sizeMultiplier, 0, 0, sizeMultiplier, 
						sizeMultiplier > 1.0 ? gate.x - 10 : gate.x, sizeMultiplier > 1.0 ? gate.y - 10 : gate.y)));
	}
	
	/**
	 * Draws a {@link XNOR} {@link Gate} to the given {@link Graphics2D} context.
	 * 
	 * @param gate the {@link Gate} to render
	 * @param g2D the {@link Graphics2D} context to render to
	 * @param sizeMultiplier {@link double} with which to multiply the {@link Gate}'s size
	 */
	private void drawXNORGate(Gate gate, Graphics2D g2D, double sizeMultiplier)
	{
		g2D.setPaint(XOR_COLOR);
		g2D.fill(xnor_area.createTransformedArea(
				new AffineTransform(sizeMultiplier, 0, 0, sizeMultiplier, 
						sizeMultiplier > 1.0 ? gate.x - 10 : gate.x, sizeMultiplier > 1.0 ? gate.y - 10 : gate.y)));
	}
	
	/**
	 * Draws a {@link IN} {@link Gate} to the given {@link Graphics2D} context.
	 * 
	 * @param gate the {@link Gate} to render
	 * @param g2D the {@link Graphics2D} context to render to
	 * @param sizeMultiplier {@link double} with which to multiply the {@link Gate}'s size
	 */
	private void drawINGate(Gate gate, Graphics2D g2D, double sizeMultiplier)
	{
		if (circuitManager.isPlaying())
		{g2D.setPaint(gate.getPlayState() ? CONNECT_COLOR_ON : CONNECT_COLOR_OFF);}
		else
		{g2D.setPaint(gate.output() ? CONNECT_COLOR_ON : CONNECT_COLOR_OFF);}
		g2D.fillRect(sizeMultiplier > 1.0 ? gate.x - 10 : gate.x, sizeMultiplier > 1.0 ? gate.y - 10 : gate.y, 
				(int) (30 * sizeMultiplier), (int) (30 * sizeMultiplier));
	}
	
	/**
	 * Draws a {@link OUT} {@link Gate} to the given {@link Graphics2D} context.
	 * 
	 * @param gate the {@link Gate} to render
	 * @param g2D the {@link Graphics2D} context to render to
	 * @param sizeMultiplier {@link double} with which to multiply the {@link Gate}'s size
	 */
	private void drawOUTGate(Gate gate, Graphics2D g2D, double sizeMultiplier)
	{
		if (circuitManager.isPlaying())
		{g2D.setPaint(gate.getPlayState() ? CONNECT_COLOR_ON : CONNECT_COLOR_OFF);}
		else
		{g2D.setPaint(gate.output() ? CONNECT_COLOR_ON : CONNECT_COLOR_OFF);}
		g2D.fillOval(sizeMultiplier > 1.0 ? gate.x - 10 : gate.x, sizeMultiplier > 1.0 ? gate.y - 10 : gate.y, 
				(int) (30 * sizeMultiplier), (int) (30 * sizeMultiplier));
	}
	
	/**
	 * Draws a {@link NOT} {@link Gate} to the given {@link Graphics2D} context.
	 * 
	 * @param gate the {@link Gate} to render
	 * @param g2D the {@link Graphics2D} context to render to
	 * @param sizeMultiplier {@link double} with which to multiply the {@link Gate}'s size
	 */
	private void drawNOTGate(Gate gate, Graphics2D g2D, double sizeMultiplier)
	{
		if (circuitManager.isPlaying())
		{g2D.setPaint(gate.getPlayState() ? CONNECT_COLOR_ON : CONNECT_COLOR_OFF);}
		else
		{g2D.setPaint(gate.output() ? CONNECT_COLOR_ON : CONNECT_COLOR_OFF);}
		g2D.fill(not_area.createTransformedArea(
				new AffineTransform(sizeMultiplier, 0, 0, sizeMultiplier, 
						sizeMultiplier > 1.0 ? gate.x - 10 : gate.x, sizeMultiplier > 1.0 ? gate.y - 10 : gate.y)));
	}
}
