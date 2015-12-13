import java.util.Vector;

public class ResultStat {
	
	/*This class is for data analysis. Compute the average delay, access time, total packet number, etc.
	 * Function statistics is for computation.
	 */
	
	
	int PktNum = 0;
	Vector <Integer> queuedelay = new Vector <Integer> ();
	Vector <Integer> accessdelay = new Vector <Integer> ();
	Vector <Integer> frameinteval = new Vector <Integer> ();
	Vector <Integer> cost = new Vector <Integer> ();
	
	long SumE2ETime = 0;
	long SumQueue = 0;
	long SumAccess = 0;
	long SumFrame = 0;
	double SumCost = 0;
	double SumCollision = 0;
	double MeanE2ETime = 0.0;
	double MeanQueue = 0.0;
	double MeanAccess = 0.0;
	double MeanFrame = 0.0;
	double MeanCost = 0.0;
	double MeanCollision=0.0;
	int MeanThrouput = 0;
	
	public ResultStat statistics (Vector<Packet> simresult, int src)
	{
		ResultStat resultstat = new ResultStat ();
		int temp = 0;
		int temp2 = 0;
		Packet pkt = null;
		for (int n=0; n<simresult.size(); n++)
		{
			if (simresult.get(n).SRC == src)
			{
				resultstat.PktNum++;
				pkt = simresult.get(n);
				temp = pkt.RcvTime - pkt.GenTime;
				temp2 = pkt.LastTransTime - pkt.GenTime;
				resultstat.queuedelay.add(temp2);
				resultstat.SumQueue = resultstat.SumQueue + temp2;
				resultstat.SumE2ETime = resultstat.SumE2ETime + temp;
				temp = pkt.RcvTime - pkt.TransTime;
				resultstat.accessdelay.add(temp);
				resultstat.SumAccess = resultstat.SumAccess + temp;
				resultstat.frameinteval.add(pkt.InArrTime);
				resultstat.SumFrame = resultstat.SumFrame + pkt.InArrTime;
				resultstat.cost.add(pkt.Cost);
				resultstat.SumCost= resultstat.SumCost + pkt.Cost;
				resultstat.SumCollision= resultstat.SumCollision + pkt.Collision;
			}
			
		}
		resultstat.MeanE2ETime = resultstat.SumE2ETime / resultstat.PktNum;
		resultstat.MeanThrouput = (int) ((8000 / resultstat.MeanE2ETime) * Math.pow(10, 6));
		resultstat.MeanAccess = resultstat.SumAccess / resultstat.PktNum;
		resultstat.MeanQueue = resultstat.SumQueue / resultstat.PktNum;
		resultstat.MeanFrame = resultstat.SumFrame / resultstat.PktNum;
		resultstat.MeanCost = (double)Math.round(resultstat.SumCost / resultstat.PktNum*10000)/10000;
		resultstat.MeanCollision = (double)Math.round(resultstat.SumCollision / resultstat.PktNum*10000)/10000;
		return resultstat;
	}
	
	public ResultStat nodetonode_sameside (Vector<Packet> simresult)
	{
		ResultStat resultstat = new ResultStat ();
		int temp = 0;
		int temp2 = 0;
		Packet pkt = null;
		for (int n=0; n<simresult.size(); n++)
		{
			if (simresult.get(n).SrcLabel == simresult.get(n).DstLabel)
			{
				resultstat.PktNum++;
				pkt = simresult.get(n);
				temp = pkt.RcvTime - pkt.GenTime;
				temp2 = pkt.LastTransTime - pkt.GenTime;
				resultstat.queuedelay.add(temp2);
				resultstat.SumQueue = resultstat.SumQueue + temp2;
				resultstat.SumE2ETime = resultstat.SumE2ETime + temp;
				temp = pkt.RcvTime - pkt.TransTime;
				resultstat.accessdelay.add(temp);
				resultstat.SumAccess = resultstat.SumAccess + temp;
				resultstat.frameinteval.add(pkt.InArrTime);
				resultstat.SumFrame = resultstat.SumFrame + pkt.InArrTime;
				resultstat.cost.add(pkt.Cost);
				resultstat.SumCost= resultstat.SumCost + pkt.Cost;
				resultstat.SumCollision= resultstat.SumCollision + pkt.Collision;
			}
			
		}
		resultstat.MeanE2ETime = resultstat.SumE2ETime / resultstat.PktNum;
		resultstat.MeanThrouput = (int) ((8000 / resultstat.MeanE2ETime) * Math.pow(10, 6));
		resultstat.MeanAccess = resultstat.SumAccess / resultstat.PktNum;
		resultstat.MeanQueue = resultstat.SumQueue / resultstat.PktNum;
		resultstat.MeanFrame = resultstat.SumFrame / resultstat.PktNum;
		resultstat.MeanCost = (double)Math.round(resultstat.SumCost / resultstat.PktNum*10000)/10000;
		resultstat.MeanCollision = (double)Math.round(resultstat.SumCollision / resultstat.PktNum*10000)/10000;
		return resultstat;
	}
	
	public ResultStat nodetonode_diffside (Vector<Packet> simresult)
	{
		ResultStat resultstat = new ResultStat ();
		int temp = 0;
		int temp2 = 0;
		Packet pkt = null;
		for (int n=0; n<simresult.size(); n++)
		{
			if (simresult.get(n).SrcLabel != simresult.get(n).DstLabel)
			{
				resultstat.PktNum++;
				pkt = simresult.get(n);
				temp = pkt.RcvTime - pkt.GenTime;
				temp2 = pkt.LastTransTime - pkt.GenTime;
				resultstat.queuedelay.add(temp2);
				resultstat.SumQueue = resultstat.SumQueue + temp2;
				resultstat.SumE2ETime = resultstat.SumE2ETime + temp;
				temp = pkt.RcvTime - pkt.TransTime;
				resultstat.accessdelay.add(temp);
				resultstat.SumAccess = resultstat.SumAccess + temp;
				resultstat.frameinteval.add(pkt.InArrTime);
				resultstat.SumFrame = resultstat.SumFrame + pkt.InArrTime;
				resultstat.cost.add(pkt.Cost);
				resultstat.SumCost= resultstat.SumCost + pkt.Cost;
				resultstat.SumCollision= resultstat.SumCollision + pkt.Collision;
			}
			
		}
		resultstat.MeanE2ETime = resultstat.SumE2ETime / resultstat.PktNum;
		resultstat.MeanThrouput = (int) ((8000 / resultstat.MeanE2ETime) * Math.pow(10, 6));
		resultstat.MeanAccess = resultstat.SumAccess / resultstat.PktNum;
		resultstat.MeanQueue = resultstat.SumQueue / resultstat.PktNum;
		resultstat.MeanFrame = resultstat.SumFrame / resultstat.PktNum;
		resultstat.MeanCost = (double)Math.round(resultstat.SumCost / resultstat.PktNum*10000)/10000;
		resultstat.MeanCollision = (double)Math.round(resultstat.SumCollision / resultstat.PktNum*10000)/10000;
		return resultstat;
	}
}
