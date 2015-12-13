import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class ResultOutput {
	public static void output(ResultStat resultstat, int n) throws IOException
	{
		File filedata = new File ("/Users/mengyingwang/Documents/eclipse_workspace/" + "data_of_node" + n);       //File path
		File fileresult = new File ("/Users/mengyingwang/Documents/eclipse_workspace/" + "result_of_node" + n);
		FileWriter outdata = new FileWriter (filedata);
		FileWriter outresult = new FileWriter (fileresult);
		
		//output data into txt files, and then input txt into MATLAB to plot
		int i;
		for (i=0; i < resultstat.PktNum-1; i++)
		{
			outdata.write(resultstat.queuedelay.get(i)+"\t"+resultstat.accessdelay.get(i)+"\t"+resultstat.frameinteval.get(i)+"\r\n");
		}
		outdata.write(resultstat.queuedelay.get(i)+"\t"+resultstat.accessdelay.get(i)+"\t"+0);
		outdata.close();
		
		String s1 = "Simulation Results of Node " + n;
		String s2 = "Packets sent from Node " + n + " = " + resultstat.PktNum;
		String s3 = "Mean end to end time at Node " + n + " = " + resultstat.MeanE2ETime;
		String s4 = "Average frame interval at Node " + n + " = " + resultstat.MeanFrame;
		String s5 = "Average access delay at Node " + n + " = " + resultstat.MeanAccess;
		String s6 = "Average queue delay at Node " + n + " = " + resultstat.MeanQueue;
		String s7 = "Throughput of Node " + n + " = " + resultstat.MeanThrouput;
		outresult.write(s1 + "\r\n" + s2 + "\r\n" + s3 + "\r\n" + s4 + "\r\n" + s5 + "\r\n" + s6 + "\r\n" + s7 + "\r\n");
		outresult.close();
	}
}
