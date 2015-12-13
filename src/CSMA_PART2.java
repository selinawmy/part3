import java.util.Collections;
import java.util.Random;
import java.util.LinkedList;
import java.util.Vector;


public class CSMA_PART2 {
	
	private static LinkedList<Packet> PktList = new LinkedList<Packet>();
	private static Vector<Packet> SimResultList = new Vector<Packet>();
	public static Constants constant = new Constants ();

	private static int clock = 0;
	public static int NumOfNodes = 0;   //number of nodes
	public static int TimeCursor[];
	
	public static void cursor (int n)
	{
		TimeCursor = new int[n];

	}
	
	public static Packet InitialPkt (Packet pkt, int src, int curtime)
	{
		pkt.SRC = src;
		Random random = new Random();
		do
		{
			pkt.DST = random.nextInt(NumOfNodes);
			
		}while( pkt.DST == src);	
				
		float lambda = 0.5f;
		//float lambda = 2.0f;
		float t = (float) (-(1/lambda)*Math.log(Math.random()));
		int InArrTime = Math.round(t*constant.frameslot);
		TimeCursor[pkt.SRC] = TimeCursor[pkt.SRC] + InArrTime;
		pkt.GenTime = TimeCursor[pkt.SRC];
		pkt.CurTime = TimeCursor[pkt.SRC];
		pkt.TransTime = 0;
		pkt.RcvTime = 0;
		pkt.Collision = 0;
		pkt.ColliTimeMin = 1000000000;   //revised from 70000000 to 1000000000. Increase it to make sure it is big enough
		pkt.BackFlag = false;
		if (pkt.CurTime < curtime + constant.Td + constant.Tp)
		{
			pkt.CurTime = curtime + constant.Td + constant.Tp;
		}
		return pkt;
	}
	
	public static void sortPkt ()
	{
		/*
		int n=0;
		while(n<PktList.size() && pkt.CurTime > PktList.get(n).CurTime)
		{
			n++;
		}
		PktList.add(n, pkt);
		*/
		Collections.sort(PktList, new NodeComparator());
		
	}
	
	public static void deletePkt ()
	{
		SimResultList.add(PktList.getFirst());
		PktList.remove();
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
		
		int src, dst, difftime;
		
		
		int bktime = 0;
		int tframeslot = constant.frameslot;
		
		for (int n = 0; n < NumOfNodes; n++)
		{
			Packet pkt = new Packet();
			pkt = InitialPkt(pkt, n, -constant.Td);
			PktList.add(pkt);
		}
		
		
		Random random1 = new Random();
		//Random random2 = new Random();
		//Packet temppkt1 = new Packet();
		//Packet temppkt2 = new Packet();
		
		if (PktList.size()==0)
		{
			System.out.println("No packets to simulate!");
			return SimResultList;
		}
		
		Packet pkt0 = new Packet ();
		Packet pktn = new Packet ();
		
		while(true)
		{
			sortPkt();
			clock = PktList.get(0).CurTime;
			
			int ColliTimeTemp0 = 0; //Current min collision time for 0 & n
			int ColliTimeTempn = 0;
			boolean BackFlagTemp0 = false;
			boolean BackFlagTempn = false;
			
			
			for (int n = 1; n<PktList.size(); n++)
			{
				pkt0 = PktList.get(0);
				pktn = PktList.get(n);
				
 				difftime = pktn.CurTime - pkt0.CurTime;
				if (difftime < Math.abs(pktn.SRC-pkt0.SRC)*constant.Tp)  //Collision requirement meets
				{
					ColliTimeTemp0 = pkt0.ColliTimeMin;
					ColliTimeTempn = pktn.ColliTimeMin; //Preserve current min collision time for 0 & n
					if (pkt0.ColliTimeMin > (pktn.CurTime + Math.abs(pktn.SRC-pkt0.SRC)*constant.Tp))  //backoff based on ColliTimeMin
					{
						pkt0.ColliTimeMin = pktn.CurTime + Math.abs(pktn.SRC-pkt0.SRC)*constant.Tp;
					}
					if (pktn.ColliTimeMin > pkt0.CurTime + Math.abs(pktn.SRC-pkt0.SRC)*constant.Tp)
					{
						pktn.ColliTimeMin = pkt0.CurTime + Math.abs(pktn.SRC-pkt0.SRC)*constant.Tp;
					} // To save the min collision time in ColliTimeMin 
					
					//for pkt 0 
					BackFlagTemp0 = pkt0.BackFlag;
					pkt0.BackFlag = true; //if BackFlag = true collision happened
					if ((pktn.SRC-pkt0.SRC)*(pktn.SRC-pkt0.DST) > 0) // requirement1 (node n is not between the src and des in pkt 0) for fake collision
					{
						if ((Math.abs(pkt0.SRC - pkt0.DST)*constant.Tp + constant.Td + pkt0.CurTime) <   //requirement2 (signal from src (of pkt n) 		                                                                                	
								(Math.abs(pktn.SRC - pkt0.DST)*constant.Tp + pktn.CurTime))//arrives the des (of pkt 0) later than signal from src (of pkt 0)) 
                                                                                                                         //for fake collision
						{
							pkt0.BackFlag = false||BackFlagTemp0;            // it is fake collision (no collision)
							pkt0.ColliTimeMin = ColliTimeTemp0;// Return to the former value
						}
					}
					// for pkt n
					BackFlagTempn = pktn.BackFlag;
					pktn.BackFlag = true;
					if ((pkt0.SRC-pktn.SRC)*(pkt0.SRC-pktn.DST) > 0)
					{
						if ((Math.abs(pktn.SRC - pktn.DST)*constant.Tp + constant.Td + pktn.CurTime) <        
								(Math.abs(pkt0.SRC - pktn.DST)*constant.Tp + pkt0.CurTime))
						{
							pktn.BackFlag = false||BackFlagTempn;
							pktn.ColliTimeMin = ColliTimeTempn;
						}
					}	
				}
				else if (pkt0.BackFlag == false)
				{
					if (pktn.BackFlag == false)
						if ((pktn.CurTime < (pkt0.CurTime + constant.Tp*Math.abs(pkt0.SRC - pktn.SRC) + constant.Td)))
						{
							pktn.CurTime = pkt0.CurTime + constant.Tp*Math.abs(pkt0.SRC - pktn.SRC) + constant.Td;
						}
				}
			}// finish comparing pkt 0 to all the others in PktList: the earliest time that src(pkt 0) recognize collision is ColliTimeMin
			
			if (pkt0.BackFlag == true)  //backoff
			{
				if (pkt0.TransTime == 0)
					pkt0.TransTime = pkt0.CurTime;
				pkt0.Collision++;
				if (pkt0.Collision < constant.maxbackoff)
					bktime = tframeslot*(random1.nextInt((int) (Math.pow(2, pkt0.Collision))));
				else
					bktime = tframeslot*(random1.nextInt((int) (Math.pow(2, constant.maxbackoff))));
				//System.out.println("bktime of node " + pkt0.SRC + ": " + bktime);
				pkt0.CurTime = pkt0.ColliTimeMin + bktime;
				//if (pkt0.CurTime > 60000000)
					//System.out.println("error");
				pkt0.BackFlag = false;
				pkt0.ColliTimeMin = 1000000000;  //revised for the same reason
				//PktList.set(0, pkt0);
			}
			else
			{
				if (pkt0.TransTime == 0)
					pkt0.TransTime = pkt0.CurTime;
				pkt0.RcvTime = pkt0.CurTime + constant.Td + Math.abs(pkt0.SRC - pkt0.DST)*constant.Tp;
				if (pkt0.RcvTime - pkt0.TransTime < 0)
					System.out.println("nectige!");
				Packet newpkt = new Packet ();
				newpkt = InitialPkt(newpkt, pkt0.SRC, pkt0.CurTime);
				deletePkt();  //Delete the first packet in the PktList, and add it into the Result List (SimResultList)
				PktList.add(newpkt);
			}
			
			
			int mincursor = 65000000;                        // revised the requirement to break out of the "while" loop
			for (int n = 0; n<TimeCursor.length; n++)
			{
				if (TimeCursor[n] < mincursor)
					mincursor = TimeCursor[n];
			}
			if (mincursor > constant.TotalSimTime)
			{
				constant.totaltime = TimeCursor[1];
				System.out.println("Simulation Completed!");
				break;
			}
			/*
			if (TimeCursor[0] > TimeCursor[1])
			{
				if (TimeCursor[1] > constant.TotalSimTime)
				{
					constant.totaltime = TimeCursor[1];
					System.out.println("Simulation Completed!");
					break;
				}
			}
			else
			{
				if (TimeCursor[0] > constant.TotalSimTime)
				{
					constant.totaltime = TimeCursor[0];
					System.out.println("Simulation Completed!");
					break;
				}
			}
			/*if (clock > TotalSimTime)
			{
				System.out.println("Simulation Completed!");
				break;
			}*/
				
				
		}
		return SimResultList;
			
	}
	
}