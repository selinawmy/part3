
public class Packet {
	
	/*
	 * This is the Packet structure. ColliTimeMin and BackFlag are used for CSMA_PART2, not in PART1
	 */
	
	int SRC;
	int DST;
	int SrcLoc;
	int DstLoc;
	int TempDstLoc;
	int SrcLabel;     //if the packet is from A or B, label = 1. Otherwise label = 2;
	int DstLabel;
	int DstR = 0;            //NextRouter of the pkt. Initial value is 0, which means the pkt hasn't been received to a router
	int SrcR = 0;            //SrcRouter
	int RLabel = 0;     //NextRouter label
	int GenTime;
	int TransTime;
	int LastTransTime;
	int RcvTime;
	int CurTime;
	int Collision;
	int ColliTimeMin;
	boolean BackFlag;
	int order;
	int InArrTime;
	int Cost;
}
