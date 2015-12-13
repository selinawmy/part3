import java.util.Random;
public class Routing {
	public static Constants constant = new Constants ();
	public static int[] UpdateCost()
	{
		int[] CostList= new int [10];
		//int[] CostList = {1,6,10,2,9,10,2,10,10,2};
		int[] fixCostList = {51, 68, 44, 82, 43, 97, 37, 86, 92, 39};
		Random random = new Random();
		for (int i = 0; i < 10; i ++)
		{
			//CostList[i]=fixCostList[i];
			CostList[i]= random.nextInt(10)+1;
		}
        return CostList;
	}
	
	public static int [][][] Dijkstra (int[] CostList)
	{
		int[][][] RoutingTable = new int[4][8][3];
		// Find R1 R2 R3 R4
		for(int i = 0; i <=1; i++)
		{
			for(int j = 0; j<=3; j++)
			{
				       RoutingTable[i][j][0]=(int) Double.POSITIVE_INFINITY;
				       RoutingTable[i+2][j][0]=(int) Double.POSITIVE_INFINITY;
			}
 			for(int j = 4; j<=7; j++)
			{
				if(((i-1.5)*(j-5.5))>0) // Group1 R1 R2; Group2 R3 R4; If they are in the same group then
					{
					RoutingTable[i][j][0]=(int) Double.POSITIVE_INFINITY;
					RoutingTable[i+2][j+2][0]=(int) Double.POSITIVE_INFINITY;
					}
				else
				{
					RoutingTable[i][j][0]=CostList[j-4-2*i]; //j-4-2*i is nothing but a self-designed index table to find cost
					RoutingTable[i][j][1]=j; //Store the Rx
					RoutingTable[j-4][i+4][0]=CostList[j-4-2*i]; // it is a 4X4 symmetric matrix
					RoutingTable[j-4][i+4][1]=i+4; //Store the Rx
				}
			}
		}
		// Find R1 R2 ----  C D
      for(int i=0; i<=3; i++)
      {
    	  int Offset = 0; // Find R1 R2 ----  C D
    	  if (i>1)
    		    Offset=2; // Find R3 R4 ----  A B
    	  // Find C or A 
    	       if((RoutingTable[i][7-Offset][0]+CostList[(int) (8-1.5*Offset)]+CostList[(int) (7-1.5*Offset)]) 
    			        >=RoutingTable[i][6-Offset][0]+CostList[(int) (7-1.5*Offset)] ) 
    	       {
    	    	    RoutingTable[i][2-Offset][0] = RoutingTable[i][6-Offset][0]+CostList[(int) (7-1.5*Offset)];
    		        RoutingTable[i][2-Offset][1] = RoutingTable[i][6-Offset][1];
    	       }
    	      else
    	       {
    	    	     RoutingTable[i][2-Offset][0] = RoutingTable[i][7-Offset][0]+CostList[(int) (8-1.5*Offset)]+CostList[(int) (7-1.5*Offset)];
    		         RoutingTable[i][2-Offset][1] = RoutingTable[i][7-Offset][1];
    	       }
    	    	// Find D or B 
    	       if((RoutingTable[i][6-Offset][0]+CostList[(int) (8-1.5*Offset)]+CostList[(int) (9-1.5*Offset)]) 
    			        >=RoutingTable[i][7-Offset][0]+CostList[(int) (9-1.5*Offset)] ) 
    	       {
    	    	    RoutingTable[i][3-Offset][0] = RoutingTable[i][7-Offset][0]+CostList[(int) (9-1.5*Offset)]; 
    		        RoutingTable[i][3-Offset][1] = RoutingTable[i][7-Offset][1];
    	       }
    	      else
    	       {
    	    	     RoutingTable[i][3-Offset][0] = RoutingTable[i][6-Offset][0]+CostList[(int) (8-1.5*Offset)]+CostList[(int) (9-1.5*Offset)]; 
    		         RoutingTable[i][3-Offset][1] = RoutingTable[i][6-Offset][1];
    	       }
      }
		return RoutingTable;
  }
	// FindPath is used by ROUTERS to find the path (the next router) and the estimated Cost. 
	//The cost (path.Cost) will be the cost from the ROUTER that is using this function to the DES NODE. 
	// int srcRouter is the ID of the router that is using this function, and desNode is the ID of the destination (Node )
	public static PathInfo FindPath (int[][][] RoutingTable, int srcRouter, int desNode, String Mode, int[] CostList)
	{
		PathInfo path = new PathInfo();
		path.SrcRouter=srcRouter;
		path.DesNode=desNode;
		if(Mode=="Dijkstra's Algorithm")
		{
			path.NextRouter=RoutingTable[srcRouter-4][desNode][1];
			path.Cost=RoutingTable[srcRouter-4][desNode][0];
		}
		else
		{
			Random random = new Random();
			if(srcRouter>=6&&desNode<=1){
				path.NextRouter = random.nextInt(2)+4;
				if((path.NextRouter-4.5)*(desNode-0.5)>0)
					path.Cost=RoutingTable[srcRouter-4][path.NextRouter][0] + CostList [2*desNode+4];
				else
					path.Cost=RoutingTable[srcRouter-4][path.NextRouter][0] + CostList [2*desNode+4] + CostList [5];	
			}
				
			else if (srcRouter<=5&&desNode>=2){
				path.NextRouter = random.nextInt(2)+6;
				if((path.NextRouter-6.5)*(desNode-2.5)>0)
					path.Cost=RoutingTable[srcRouter-4][path.NextRouter][0] + CostList [2*desNode+3];
				else
					path.Cost=RoutingTable[srcRouter-4][path.NextRouter][0] + CostList [2*desNode+3] + CostList [8];	
			}
				  
			else 
				System.out.println("Error: The Src and Des are on the same bus");
		}
		return path;
	}
}
