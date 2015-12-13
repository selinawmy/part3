import java.util.LinkedList;
import java.util.Random;


public class CSMA {

	
	public static void csma(LinkedList<Packet> PktList)
	{
		int difftime, bktime;
		Packet pkt0 = new Packet();
		Packet pktn = new Packet();
		Random random = new Random();
		
		
		PathInfo pathinfo = new PathInfo();
		
		
		
		int ColliTimeTemp0 = 0; //Current min collision time for 0 & n
		int ColliTimeTempn = 0;
		boolean BackFlagTemp0 = false;
		boolean BackFlagTempn = false;
		
		for (int n = 1; n<PktList.size(); n++)
		{
			pkt0 = PktList.get(0);
			pktn = PktList.get(n);
			
			/*
			if (pkt0.RLabel != 0)
			{
				if ((CSMA_PART3.CurTimeCursor[pkt0.RLabel-1] + Constants.Td + Constants.Tp*Math.abs(CSMA_PART3.CurTimeLabel[pkt0.RLabel-1] - pkt0.SrcLoc)) > pkt0.CurTime)
					pkt0.CurTime = CSMA_PART3.CurTimeCursor[pkt0.RLabel-1] + Constants.Td + Constants.Tp*Math.abs(CSMA_PART3.CurTimeLabel[pkt0.RLabel-1] - pkt0.SrcLoc);
			}
			*/
			
			difftime = pktn.CurTime - pkt0.CurTime;
			if (difftime < Math.abs(pktn.SrcLoc-pkt0.SrcLoc)*Constants.Tp)  //Collision requirement meets
			{
				System.out.println("Src of pkt0: "+pkt0.SRC);
				System.out.println("Dst of pkt0: "+pkt0.DST);
				System.out.println("CurTime of pkt0: "+pkt0.CurTime);
				System.out.println("Src of pkt1: "+pktn.SRC);
				System.out.println("Dst of pkt1: "+pktn.DST);
				System.out.println("CurTime of pkt1: "+pktn.CurTime);
				ColliTimeTemp0 = pkt0.ColliTimeMin;
				ColliTimeTempn = pktn.ColliTimeMin; //Preserve current min collision time for 0 & n
				if (pkt0.ColliTimeMin > (pktn.CurTime + Math.abs(pktn.SrcLoc-pkt0.SrcLoc)*Constants.Tp))  //backoff based on ColliTimeMin
				{
					pkt0.ColliTimeMin = pktn.CurTime + Math.abs(pktn.SrcLoc-pkt0.SrcLoc)*Constants.Tp;
				}
				if (pktn.ColliTimeMin > pkt0.CurTime + Math.abs(pktn.SrcLoc-pkt0.SrcLoc)*Constants.Tp)
				{
					pktn.ColliTimeMin = pkt0.CurTime + Math.abs(pktn.SrcLoc-pkt0.SrcLoc)*Constants.Tp;
				} // To save the min collision time in ColliTimeMin 
				
				//for pkt 0 
				BackFlagTemp0 = pkt0.BackFlag;
				pkt0.BackFlag = true; //if BackFlag = true collision happened
				if (pkt0.SrcLabel == pkt0.DstLabel || pkt0.RLabel == pkt0.DstLabel)  //if dst of pkt0 is in the same side
				{
					if ((pktn.SrcLoc-pkt0.SrcLoc)*(pktn.SrcLoc-pkt0.DstLoc) > 0) // requirement1 (node n is not between the SrcLoc and des in pkt 0) for fake collision
					{
						if ((Math.abs(pkt0.SrcLoc - pkt0.DstLoc)*Constants.Tp + Constants.Td + pkt0.CurTime) <   //requirement2 (signal from src (of pkt n) 		                                                                                	
								(Math.abs(pktn.SrcLoc - pkt0.DstLoc)*Constants.Tp + pktn.CurTime))//arrives the des (of pkt 0) later than signal from src (of pkt 0)) 
	                                                                                                                     //for fake collision
						{
							pkt0.BackFlag = false||BackFlagTemp0;            // it is fake collision (no collision)
							pkt0.ColliTimeMin = ColliTimeTemp0;// Return to the former value
						}
					}
				}
				else   //dst of pkt0 is in the other side
				{
					if ((Constants.Tp + Constants.Td + pkt0.CurTime) < (Math.abs(pktn.SrcLoc - pkt0.TempDstLoc)*Constants.Tp + pktn.CurTime))//arrives the des (of pkt 0) later than signal from src (of pkt 0)) 
					{
						pkt0.BackFlag = false||BackFlagTemp0;            // it is fake collision (no collision)
						pkt0.ColliTimeMin = ColliTimeTemp0;// Return to the former value
					}
				}
				
				// for pkt n
				BackFlagTempn = pktn.BackFlag;
				pktn.BackFlag = true;
				if (pktn.SrcLabel == pktn.DstLabel || pktn.RLabel == pktn.DstLabel)  //if dst of pktn is in the same side
				{
					if ((pkt0.SrcLoc-pktn.SrcLoc)*(pkt0.SrcLoc-pktn.DstLoc) > 0)
					{
						if ((Math.abs(pktn.SrcLoc - pktn.DstLoc)*Constants.Tp + Constants.Td + pktn.CurTime) <        
								(Math.abs(pkt0.SrcLoc - pktn.DstLoc)*Constants.Tp + pkt0.CurTime))
						{
							pktn.BackFlag = false||BackFlagTempn;
							pktn.ColliTimeMin = ColliTimeTempn;
						}
					}	
				}
				else   //dst of pktn is in the other side
				{
					if ((Constants.Tp + Constants.Td + pktn.CurTime) < (Math.abs(pkt0.SrcLoc - pktn.TempDstLoc)*Constants.Tp + pkt0.CurTime))
					{
						pktn.BackFlag = false||BackFlagTempn;
						pktn.ColliTimeMin = ColliTimeTempn;
					}
				}
				
			}
			else if (pkt0.BackFlag == false)
			{
				if (pktn.BackFlag == false)
					if ((pktn.CurTime < (pkt0.CurTime + Constants.Tp*Math.abs(pkt0.SrcLoc - pktn.SrcLoc) + Constants.Td)))
					{
						pktn.CurTime = pkt0.CurTime + Constants.Tp*Math.abs(pkt0.SrcLoc - pktn.SrcLoc) + Constants.Td;
					}
			}
		}// finish comparing pkt 0 to all the others in PktList: the earliest time that src(pkt 0) recognize collision is ColliTimeMin
		
		if (pkt0.BackFlag == true)  //back off
		{
			if (pkt0.TransTime == 0)
				pkt0.TransTime = pkt0.CurTime;
			pkt0.Collision++;
			if (pkt0.Collision < Constants.maxbackoff)
				bktime = Constants.frameslot*(random.nextInt((int) (Math.pow(2, pkt0.Collision))));
			else
				bktime = Constants.frameslot*(random.nextInt((int) (Math.pow(2, Constants.maxbackoff))));
			//System.out.println("bktime of node " + pkt0.SRC + ": " + bktime);
			pkt0.CurTime = pkt0.ColliTimeMin + bktime;
			System.out.println("CurTime of pkt0 after backoff: "+pkt0.CurTime);
			System.out.println("Collision time: "+pkt0.Collision);
			//if (pkt0.CurTime > 60000000)
				//System.out.println("error");
			pkt0.BackFlag = false;
			pkt0.ColliTimeMin = (int)Double.POSITIVE_INFINITY;  //revised for the same reason
			//PktList.set(0, pkt0);
		}
		else
		{
			if (pkt0.TransTime == 0)
				pkt0.TransTime = pkt0.CurTime;
			
			if (pkt0.SrcLabel == pkt0.DstLabel) //the src and dst of pkt0 are in the same side
			{
				pkt0.LastTransTime = pkt0.CurTime;
				pkt0.RcvTime = pkt0.CurTime + Constants.Td + Math.abs(pkt0.SrcLoc - pkt0.DstLoc)*Constants.Tp;
				if (pkt0.SrcLabel == 1)   //compute the cost
					pkt0.Cost = CSMA_PART3.CostList[4] + CSMA_PART3.CostList[5] + CSMA_PART3.CostList[6];
				else
					pkt0.Cost = CSMA_PART3.CostList[7] + CSMA_PART3.CostList[8] + CSMA_PART3.CostList[9];
				Packet newpkt = new Packet ();
				newpkt = CSMA_PART3.InitialPkt(newpkt, pkt0.SRC, pkt0.CurTime);
				CSMA_PART3.SimResultList.add(pkt0);
				PktList.remove(); 
				CSMA_PART3.PktList.remove();             //Delete the first packet in the PktList, and add it into the Result List (SimResultList)
				PktList.add(newpkt);
				CSMA_PART3.PktList.add(newpkt);
				
			}
			else if (pkt0.RLabel == pkt0.DstLabel)  //pkt0 came from the other side and was successfully transmitted by a router
			{
				pkt0.RcvTime = pkt0.CurTime + Constants.Td + Math.abs(pkt0.SrcLoc - pkt0.DstLoc)*Constants.Tp;
				CSMA_PART3.SimResultList.add(pkt0);
				PktList.remove(); 
				CSMA_PART3.PktList.remove();             //Delete the first packet in the PktList, and add it into the Result List (SimResultList)
			}
			else    //pkt0 is transmitting to the other side, and was successfully received by a router
			{
				pkt0.LastTransTime = pkt0.CurTime;
				pkt0.SrcR = pkt0.SRC + 4;
				pathinfo = Routing.FindPath(CSMA_PART3.RoutingTable, pkt0.SrcR, pkt0.DST, Constants.RoutingAlgorithm, CSMA_PART3.CostList);
				pkt0.DstR = pathinfo.NextRouter;
				if (pkt0.SRC == 0)
				{
					pkt0.RLabel = 2;
					pkt0.Cost = CSMA_PART3.CostList[4];
				}
				else if (pkt0.SRC == 1)
				{
					pkt0.RLabel = 2;
					pkt0.Cost = CSMA_PART3.CostList[6];
				}
				else if (pkt0.SRC == 2)
				{
					pkt0.RLabel = 1;
					pkt0.Cost = CSMA_PART3.CostList[7];
				}
				else
				{
					pkt0.RLabel = 1;
					pkt0.Cost = CSMA_PART3.CostList[9];
				}
				pkt0.Cost = pkt0.Cost + pathinfo.Cost;

				if (pkt0.DstR == 4 || pkt0.DstR == 6)
					pkt0.SrcLoc = 1;
				else
					pkt0.SrcLoc = 2;
				pkt0.CurTime = pkt0.CurTime + 2*Constants.Tp + Constants.TdR;
				if (pkt0.SrcLabel == 1)
					CSMA_PART3.PktListR.add(PktList.pollFirst());
				else
					CSMA_PART3.PktListL.add(PktList.pollFirst());
				Packet newpkt = new Packet ();
				newpkt = CSMA_PART3.InitialPkt(newpkt, pkt0.SRC, pkt0.CurTime);
				PktList.add(newpkt);
				CSMA_PART3.PktList.add(newpkt);
			}
			if (pkt0.RLabel == 0)
			{
				CSMA_PART3.CurTimeCursor[pkt0.SrcLabel-1] = pkt0.CurTime;
				CSMA_PART3.CurTimeLabel[pkt0.SrcLabel-1] = pkt0.SrcLoc;
			}
			else
			{
				CSMA_PART3.CurTimeCursor[pkt0.RLabel-1] = pkt0.CurTime;
				CSMA_PART3.CurTimeLabel[pkt0.RLabel-1] = pkt0.SrcLoc;
			}
				
		}
		
	}
}
