import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;



/*
 * This is the main function
 */

public class MainFunction {
	public static void main (String[] args) throws IOException 
	{
		/*
		CSMA_PART2 csma = new CSMA_PART2 ();
		@SuppressWarnings("resource")
		Scanner scan = new Scanner (System.in);
		System.out.println("The number of nodes sharing the bus: ");
		CSMA_PART2.NumOfNodes = scan.nextInt();
		CSMA_PART2.cursor(CSMA_PART2.NumOfNodes);
		*/
		CSMA_PART3 csma = new CSMA_PART3();
		Vector<Packet> simresult = new Vector<Packet> ();
		simresult = csma.CSMACD();
		
		
		String filepath = new String();
		@SuppressWarnings("resource")
		Scanner scan2 = new Scanner (System.in);
		System.out.println("Input the path to save the files: ");
		filepath = scan2.nextLine();
		
		
		for (int n=0; n<CSMA_PART3.NumOfNodes; n++)
		{
			ResultStat resultstat = new ResultStat ();
			resultstat = resultstat.statistics(simresult, n);
			
			System.out.println("Total packets of Node " + n + " = " + simresult.size());
			System.out.println("Frameslot of Node " + n + " = " + Constants.frameslot);
			System.out.println("Packets sent from Node " + n + " = " + resultstat.PktNum);
			System.out.println("Mean end to end time at Node " + n + " = " + resultstat.MeanE2ETime);
			System.out.println("Average frame interval at Node " + n + " = " + resultstat.MeanFrame);
			System.out.println("Average access delay at Node " + n + " = " + resultstat.MeanAccess);
			System.out.println("Average queue delay at Node " + n + " = " + resultstat.MeanQueue);
			System.out.println("Throuput" + n + " = " + resultstat.MeanThrouput);
			System.out.println("Simulation time =  "+Constants.totaltime);
			
			
			//File filedata = new File ("/Users/mengyingwang/Documents/eclipse_workspace/data_of_node" + n);
			//File fileresult = new File ("/Users/mengyingwang/Documents/eclipse_workspace/result_of_node" + n);
			
			
			File filedata = new File (filepath + "data_of_node" + n);
			File fileresult = new File (filepath + "result_of_node" + n);
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
		System.out.println("Files are successfully saved!");
	}
}
