
public class Constants {
	
	
	/*
	 * The main constants and parameters used in the project.
	 */
	
	public static int frameslot = 500;
	public static int totaltime; 
	public static int TotalSimTime = (int) (60*Math.pow(10, 5));
	public static int Td = 80;  //Transmission delay
	public static int Tp = 10;  //Propagation delay
	public static int TdR = 8;
	public static int tdelay = Td + Tp;
	public static float lambda = 0.5f;
	public static int maxbackoff = 3;
	public static String RoutingAlgorithm = "Dijkstra's Algorithm";
	public static int A = 0, B = 1, C = 2, D = 3, R1 = 4, R2 = 5, R3 = 7, R4 = 6;
	public static int A_R1=4, R1_R2=5, R2_B=6, R1_R4=2, R1_R3=3, R2_R4=0, R2_R3=1, C_R4=7, R4_R3=8, R3_D=9;

}
