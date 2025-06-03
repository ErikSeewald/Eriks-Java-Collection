package reflectionDemo;

public class ReflectionAlgorithm
{
	private static final double PI = Math.PI;
	private static final double DEG90 = PI/2;
	private static final double DEG270 = 3*DEG90;
	
	public static void rayCalculation(Ray prevRay, Ray ray, int PANEL_SIZE)
	{	
		double rayAngle = prevRay.getAngle();
		double rayX, rayY;
		double prevX = prevRay.origin[0], prevY = prevRay.origin[1];
		
		//CHECK HORIZONTAL LINES
		rayY = (rayAngle > PI) ? 0 : PANEL_SIZE; //looking up or down
		// tan = opposite/adjacent -> Tan a * Adjacent = Opposite -> aTan * Opposite = Adjacent -> aTan * rayY = rayX 
		rayX = prevX + (prevY-rayY)* (-1/Math.tan(rayAngle));
		
		double distHor = distance(prevX,prevY,rayX,rayY);
		double horX = rayX, horY = rayY;
		
		//CHECK VERTICAL LINES
		rayX = (rayAngle > DEG90 && rayAngle < DEG270) ? 0 : PANEL_SIZE; //looking left or right
		rayY = prevY + (prevX-rayX)* (-Math.tan(rayAngle));
		
		double distVer = distance(prevX,prevY,rayX,rayY);
			
		//CHECK WHICH ONE HIT FIRST
		if (distHor < distVer)
		{
			rayX = horX; rayY = horY;
			ray.setAngle(2*PI - rayAngle); //reflect along x axis
		}
		else 
		{ray.setAngle(PI - rayAngle);} //reflect along y axis
	
		ray.origin[0] = rayX;
		ray.origin[1] = rayY;
	}
	
	public static double distance(double ax, double ay, double bx, double by)
	{
		double v1 = bx-ax, v2 = by-ay;
		return ( Math.sqrt(v1*v1 + v2*v2) );
	}
}