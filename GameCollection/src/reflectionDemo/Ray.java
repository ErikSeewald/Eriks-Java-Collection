package reflectionDemo;

public class Ray 
{
	private static final double PI = Math.PI;
	
	double[] origin = new double[2];
	private double angle;
	
	Ray(double x, double y, double angle)
	{
		this.origin[0] = x; this.origin[1] = y;
		this.angle = angle;
	}
	
	public double getAngle()
	{return angle;}
	
	public void setAngle(double angle)
	{
		this.angle = angle;
		this.toValidAngle();
	}
	
	public void rotate(double angle)
	{
		this.angle+=angle;
		this.toValidAngle();
	}
	
	private void toValidAngle()
	{
		while (this.angle < 0) {this.angle+=2*PI;}
		while (this.angle > 2*PI) {this.angle -=2*PI;}
	}
}