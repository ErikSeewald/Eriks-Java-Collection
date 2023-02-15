package sidescroller;

public enum Layouts 
{	
	/*    smallCol 	| mediumCol	|  bigCol	|  flyBlock	| lowTunnel	| highTunnel
	 *				|			|			|			|			|	||
	 *				|			|			|			|			|
	 *				|			|			|			|	||		|
	 * 				|			|			|	||		|	||		|	||
	 * 				|			|			|			|			|
	 * 				|			|	||		|			|			|
	 * 				|	||		|	||		|			|			|
	 *  	||		|	||		|	||		|			|	||		|
	 */
	
	empty(null,null),
	smallCol(new int[] {540, Layouts.cubeSize}, null),
	mediumCol(new int[] {510, Layouts.cubeSize*2}, null),
	bigCol(new int[] {480, Layouts.cubeSize*3}, null),
	flyBlock(new int[] {420, Layouts.cubeSize}, null),
	lowTunnel(new int[] {390, Layouts.cubeSize*2}, new int[] {540, Layouts.cubeSize}),
	highTunnel(new int[] {330, Layouts.cubeSize}, new int[] {420, Layouts.cubeSize});
	
	int[] hitBox1, hitBox2;
	
	public static final int cubeSize = 30;
	public static final int[] layoutChances = {50,60,70,85,90,95,100};
	//public static final int[] layoutChances = {5,45,55,80,90,95,100};
	
	Layouts(int[] hitBox1, int[] hitBox2)
	{this.hitBox1 = hitBox1; this.hitBox2 = hitBox2;}
}