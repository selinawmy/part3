
import java.util.Collections;
import java.util.Random;
import java.util.LinkedList;
import java.util.Vector;


public class CSMA_PART1 {
	private static LinkedList<Packet> PktList = new LinkedList<Packet>();
	private static Vector<Packet> SimResultList = new Vector<Packet>();
	public static Constants constant = new Constants ();
	
	private static int clock = 0;
	//private static int NumOfNodes = 0;   //number of nodes
	private static int TimeCursor[] = new int[2];  //corresponds to GENTIMECURSOR in sample code

	public static Packet InitialPkt (Packet pkt, int src)
	{
		pkt.SRC = src;
		pkt.DST = src % 2+ 1;
			
		float t = (float) (-(1/constant.lambda)*Math.log(Math.random()));
		int InArrTime = Math.round(t*constant.frameslot);
		TimeCursor[pkt.SRC - 1] = TimeCursor[pkt.SRC - 1] + InArrTime;
		pkt.GenTime = TimeCursor[pkt.SRC - 1];
		pkt.CurTime = TimeCursor[pkt.SRC - 1];
		pkt.TransTime = 0;
		pkt.RcvTime = 0;
		pkt.Collision = 0;
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
	
	
	public Vector<Packet> CSMACD (int A, int B)
	{
		
		int src, dst, difftime;
		
		int bktime[] = new int[2];
		int tframeslot = constant.frameslot;
		
		Packet pktA = new Packet();
		pktA = InitialPkt(pktA, A);
		Packet pktB = new Packet();
		pktB = InitialPkt(pktB, B);
		PktList.add(pktA);
		PktList.add(pktB);
		sortPkt();
		
		
		Random random1 = new Random();
		Random random2 = new Random();
		//Packet temppkt1 = new Packet();
		//Packet temppkt2 = new Packet();
		
		if (PktList.size()==0)
		{
			System.out.println("No packets to simulate!");
			return SimResultList;
		}
		
		while(true)
		{
			clock = PktList.get(0).CurTime;
			src = PktList.get(0).SRC;
			dst = PktList.get(0).DST;
			difftime = PktList.get(1).CurTime - PktList.get(0).CurTime;
			
			if (difftime > constant.Tp)  //no collision happens
			{
				if (PktList.get(0).TransTime == 0)
					PktList.get(0).TransTime = PktList.get(0).CurTime;
				PktList.get(0).RcvTime = PktList.get(0).CurTime + constant.tdelay;
				deletePkt();
				Packet pktsrc = new Packet();
				pktsrc = InitialPkt(pktsrc, src);
				PktList.add(pktsrc);
				updateclock(constant.tdelay);
				sortPkt();
				
				
			}
			else
			{
				if (PktList.get(0).TransTime == 0)
					PktList.get(0).TransTime = PktList.get(0).CurTime;
				if (PktList.get(1).TransTime == 0)
					PktList.get(1).TransTime = PktList.get(1).CurTime;
				PktList.get(0).Collision++;
				PktList.get(1).Collision++;
				if (PktList.get(0).Collision < constant.maxbackoff)
					bktime[0] = tframeslot*(random1.nextInt((int) (Math.pow(2, PktList.get(0).Collision))));
				else
					bktime[0] = tframeslot*(random1.nextInt((int) (Math.pow(2, constant.maxbackoff))));
				if (PktList.get(1).Collision < constant.maxbackoff)
					bktime[1] = tframeslot*(random2.nextInt((int) (Math.pow(2, PktList.get(1).Collision))));
				else
					bktime[1] = tframeslot*(random2.nextInt((int) (Math.pow(2, constant.maxbackoff))));
				//System.out.println(bktime[0]);
				//System.out.println(bktime[1]);
				PktList.get(0).CurTime = PktList.get(0).CurTime + constant.Tp + difftime + bktime[0];
				PktList.get(1).CurTime = PktList.get(1).CurTime + constant.Tp - difftime + bktime[1];
				sortPkt();
			}
			
			System.out.println("T0 "+TimeCursor[0]);
			System.out.println("T1 "+TimeCursor[1]);
			System.out.println("Clock "+clock);
			
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
