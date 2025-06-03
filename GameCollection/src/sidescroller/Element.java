package sidescroller;

public class Element 
{
	int x;
	int slot;
	Layouts layout;

	Element(byte type, int slot)
	{
		this.layout = Layouts.values()[type];
		this.slot = slot;
		this.x = this.slot * Layouts.cubeSize;
	}
}