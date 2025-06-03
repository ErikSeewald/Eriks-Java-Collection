package parallelUniverses;

public class Player 
{
	int[] location = {313,313};
	int[] relativeLocation = {6,6};
	
	int size = 4;
	
	public void move(int x, int y)
	{
		this.location[0]+=x;
    	this.location[1]+=y;
    	
    	//RELATIVE X
    	this.relativeLocation[0]+= x;
		
		if (this.relativeLocation[0] > PU_Panel.upperEdge) 
    	{
    		this.relativeLocation[0] = PU_Panel.lowerEdge;
    		this.location[0]+=this.size + 1;
    		//Since this.location has already been incremented by 1 and border size is 2, increase by 1 more
    	}
		
    	else if (this.relativeLocation[0] < PU_Panel.lowerEdge) 
    	{
    		this.relativeLocation[0] = PU_Panel.upperEdge;
    		this.location[0]-=this.size + 1;
    	}
		
		//RELATIVE Y
		this.relativeLocation[1]+= y;
		
		if (this.relativeLocation[1] > PU_Panel.upperEdge) 
    	{
			this.relativeLocation[1] = PU_Panel.lowerEdge; 
			this.location[1]+=this.size + 1;
		}
		
    	else if (this.relativeLocation[1] < PU_Panel.lowerEdge) 
    	{
    		this.relativeLocation[1] = PU_Panel.upperEdge;
    		this.location[1]-=this.size + 1;
    	}
	}
}