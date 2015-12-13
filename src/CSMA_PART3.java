import java.util.Collections;
import java.util.Random;
import java.util.LinkedList;
import java.util.Vector;


public class CSMA_PART3 {
	
	public static LinkedList<Packet> PktListL = new LinkedList<Packet>();  //for A B R1 R2
	public static LinkedList<Packet> PktListR = new LinkedList<Packet>();  //for C D R3 R4
	public static LinkedList<Packet> PktList = new LinkedList<Packet>();   //for A B C D
	public static Vector<Packet> SimResultList = new Vector<Packet>();
	public static int[] CostList=Routing.UpdateCost();
	public static int[][][] RoutingTable= Routing.Dijkstra(CostList);
	
	public static int clock = 0;
	public static int CostUpdateTime = 2000;
	public static int NumOfNodes = 4;   //4 nodes A B C D 
	public static int TimeCursor[] = new int[4];  // A B C D
	public static int CurTimeCursor[] = new int[2];
	public static int CurTimeLabel[] = new int[2];
	public static int order[] = new int[4];
	
	public static Packet InitialPkt (Packet pkt, int src, int curtime)
	{
		pkt.SRC = src;
		//Mark label and location according to source. Left side: label = 1; right side: label = 2;
		if (src == 0)  
		{
			pkt.SrcLabel = 1;
			pkt.SrcLoc = 0;
		}
		else if (src == 1)
		{
			pkt.SrcLabel = 1;
			pkt.SrcLoc = 3;
		}
		else if (src == 2)
		{
			pkt.SrcLabel = 2;
			pkt.SrcLoc = 0;
		}
		else 
		{
			pkt.SrcLabel = 2;
			pkt.SrcLoc = 3;
		}
		Random random1 = new Random();
		Random random2 = new Random();
		do
		{
			pkt.DST = random1.nextInt(NumOfNodes);
			
		}while( pkt.DST == src);
		//Mark label and location according to destination
		if (pkt.DST == 0)  
		{
			pkt.DstLabel = 1;
			pkt.DstLoc = 0;
		}
		else if (pkt.DST == 1)
		{
			pkt.DstLabel = 1;
			pkt.DstLoc = 3;
		}
		else if (pkt.DST == 2)
		{
			pkt.DstLabel = 2;
			pkt.DstLoc = 0;
		}
		else
		{
			pkt.DstLabel = 2;
			pkt.DstLoc = 3;
		}
		
		if (pkt.SrcLabel != pkt.DstLabel)    //If src and dst are in different sides, the pkt only be received by a nearer router
		{
			if (pkt.SRC == 0 || pkt.SRC == 2)
				pkt.TempDstLoc = 1;
			else
				pkt.TempDstLoc = 2;
		}
		
		float lambda = 0.5f;
		//float lambda = 2.0f;
		float t = (float) (-(1/lambda)*Math.log(random2.nextDouble()));
		int InArrTime = Math.round(t*Constants.frameslot);
		if (InArrTime < 0)
			System.out.println("InArrTime = "+InArrTime);
		TimeCursor[pkt.SRC] = TimeCursor[pkt.SRC] + InArrTime;
		pkt.GenTime = TimeCursor[pkt.SRC];
		pkt.CurTime = TimeCursor[pkt.SRC];
		pkt.TransTime = 0;
		pkt.RcvTime = 0;
		pkt.Collision = 0;
		pkt.ColliTimeMin = (int)Double.POSITIVE_INFINITY;   
		pkt.BackFlag = false;
		if (pkt.CurTime < curtime + Constants.tdelay)
		{
			pkt.CurTime = curtime + Constants.tdelay;
		}
		pkt.InArrTime = InArrTime;
		order[pkt.SRC]++;
		pkt.order = order[pkt.SRC];
		return pkt;
	}
	
	public static void sortPkt ()
	{
		Collections.sort(PktListL, new NodeComparator());
		Collections.sort(PktListR, new NodeComparator());
		Collections.sort(PktList, new NodeComparator());
	}
	
	
	
	public static void updateclock (int delay)
	{
		clock = clock + delay;
		for (int n=0; n<PktList.size(); n++)
		{
			if (PktList.get(n).CurTime < clock)
				PktList.get(n).CurTime = clock;
		}
	}
	
	
	public Vector<Packet> CSMACD ()
	{
		Packet pkt = new Packet();  //Temporary packet
		
		for (int n = 0; n < 2; n++)   //node A B
		{
			Packet pktl = new Packet();
			pktl = InitialPkt(pktl, n, -Constants.tdelay);   // change from -Constants.Td to -Constants.tdelay
			PktListL.add(pktl);
			PktList.add(pktl);
		}
		for (int n = 2; n < 4; n++)   //node C D
		{
			Packet pktr = new Packet();
			pktr = InitialPkt(pktr, n, -Constants.tdelay);   // change from -Constants.Td to -Constants.tdelay
			PktListR.add(pktr);
			PktList.add(pktr);
		}
				
		if (PktList.size()==0)
		{
			System.out.println("No packets to simulate!");
			return SimResultList;
		}
		
		
		while(true)
		{
			sortPkt();  //sort 3 lists simultaneously
			pkt = PktList.getFirst();
			clock = pkt.CurTime;
			
			if (clock >= CostUpdateTime)
			{
				CostList=Routing.UpdateCost();
				RoutingTable= Routing.Dijkstra(CostList);
				CostUpdateTime = CostUpdateTime + 2000;
			}
			
			//the src the pkt is in the left side
			if (((pkt.SrcLabel == 1 && pkt.RLabel == 0) || pkt.RLabel == 1) ) 
			{
				CSMA.csma(PktListL);  //estimate collision 
			}
			//the src the pkt is in the right side
			else if (((pkt.SrcLabel == 2 && pkt.RLabel == 0) || pkt.RLabel == 2))
			{
				CSMA.csma(PktListR);  //estimate collision 
				
			}
			int mincursor = 65000000;                       
			for (int n = 0; n<TimeCursor.length; n++)
			{
				if (TimeCursor[n] < mincursor)
					mincursor = TimeCursor[n];
			}
			if (mincursor > Constants.TotalSimTime)
			{
				Constants.totaltime = TimeCursor[1];
				System.out.println("Simulation Completed!");
				break;
			}
			
		}
		return SimResultList;	
	}
	
}