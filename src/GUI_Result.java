import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;

public class GUI_Result extends JFrame{
	private JPanel ResultPane;
	private static Vector<JTextArea> AveTime1List;
	private static Vector<JTextArea> AveTime2List;
	private static Vector<JTextArea> AveTime3List;
	private static Vector<JTextArea> AveTime4List;
	private static Vector<JTextArea> AveTime5List;
	private static Vector<JTextArea> AveTime6List;
	private static Vector<JTextArea> AveCollisionList;
	private static Vector<JTextArea> AveCostList;
	
	public static void SetTexts (Vector<ResultStat> resultstat){
		for(int i=0; i<6; i++){
			AveTime1List.elementAt(i).setText(Double.toString(resultstat.elementAt(i).MeanAccess));
			AveTime2List.elementAt(i).setText(Double.toString(resultstat.elementAt(i).MeanE2ETime));
			AveTime3List.elementAt(i).setText(Double.toString(resultstat.elementAt(i).MeanFrame));
			AveTime4List.elementAt(i).setText(Double.toString(resultstat.elementAt(i).MeanQueue));
			AveTime5List.elementAt(i).setText(Double.toString(resultstat.elementAt(i).MeanThrouput));
			AveTime6List.elementAt(i).setText(Double.toString(resultstat.elementAt(i).PktNum));
			AveCollisionList.elementAt(i).setText(Double.toString(resultstat.elementAt(i).MeanCollision));
			AveCostList.elementAt(i).setText(Double.toString(resultstat.elementAt(i).MeanCost));
		}
	}
	public GUI_Result(){
		
		setBackground(new Color(240, 240, 240));
		setTitle("Simulating Result");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setIconImage(Toolkit.getDefaultToolkit().getImage(GUI_Result.class.getResource("/image/waitingbac.jpg")));
		setSize(1500,700);
		setLocationRelativeTo(null);
		
		ResultPane = new JPanel();
		ResultPane.setToolTipText("");
		ResultPane.setForeground(new Color(0, 0, 0));
		ResultPane.setBackground(new Color(250, 240, 230));
		ResultPane.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		setContentPane(ResultPane);
		ResultPane.setLayout(null);
		
		JLabel NodeA = new JLabel("NodeA");
		NodeA.setFont(new Font("Calibri", Font.BOLD, 30));
		NodeA.setBackground(new Color(75, 0, 130));
		NodeA.setBounds(230, 22, 200, 29);
		ResultPane.add(NodeA);
		
		JLabel NodeB =new JLabel("NodeB");
		NodeB.setFont(new Font("Calibri", Font.BOLD, 30));
		NodeB.setBackground(new Color(75, 0, 130));
		NodeB.setBounds(490, 22, 200, 29);
		ResultPane.add(NodeB);
		
		JLabel NodeC = new JLabel("NodeC");
		NodeC.setFont(new Font("Calibri", Font.BOLD, 30));
		NodeC.setBackground(new Color(75, 0, 130));
		NodeC.setBounds(750, 22, 200, 29);
		ResultPane.add(NodeC);
		
		JLabel NodeD = new JLabel("NodeD");
		NodeD.setFont(new Font("Calibri", Font.BOLD, 30));
		NodeD.setBackground(new Color(75, 0, 130));
		NodeD.setBounds(1010, 22, 200, 29);
		ResultPane.add(NodeD);
		
		JLabel NodeSame = new JLabel("SameSide");
		NodeSame.setFont(new Font("Calibri", Font.BOLD, 30));
		NodeSame.setBackground(new Color(75, 0, 130));
		NodeSame.setBounds(1270, 22, 200, 29);
		ResultPane.add(NodeSame);
		
		JLabel NodeDiff = new JLabel("DiffSide");
		NodeDiff.setFont(new Font("Calibri", Font.BOLD, 30));
		NodeDiff.setBackground(new Color(75, 0, 130));
		NodeDiff.setBounds(1530, 22, 200, 29);
		ResultPane.add(NodeDiff);
		
		JLabel applicant1 = new JLabel("MeanAccess");
		applicant1.setFont(new Font("Calibri", Font.BOLD, 30));
		applicant1.setBackground(new Color(75, 0, 130));
		applicant1.setBounds(30, 100, 200, 29);
		ResultPane.add(applicant1);
		
		JLabel applicant2 = new JLabel("MeanE2ETime");
		applicant2.setFont(new Font("Calibri", Font.BOLD, 30));
		applicant2.setBackground(new Color(75, 0, 130));
		applicant2.setBounds(30, 150, 200, 29);
		ResultPane.add(applicant2);
		
		JLabel applicant3 = new JLabel("MeanFrame");
		applicant3.setFont(new Font("Calibri", Font.BOLD, 30));
		applicant3.setBackground(new Color(75, 0, 130));
		applicant3.setBounds(30, 200, 200, 29);
		ResultPane.add(applicant3);
		
		JLabel applicant4 = new JLabel("MeanQueue");
		applicant4.setFont(new Font("Calibri", Font.BOLD, 30));
		applicant4.setBackground(new Color(75, 0, 130));
		applicant4.setBounds(30, 250, 200, 29);
		ResultPane.add(applicant4);
		
		JLabel applicant5 = new JLabel("MeanThrouput");
		applicant5.setFont(new Font("Calibri", Font.BOLD, 30));
		applicant5.setBackground(new Color(75, 0, 130));
		applicant5.setBounds(30, 300, 200, 29);
		ResultPane.add(applicant5);
		
		JLabel applicant6 = new JLabel("PktNum");
		applicant6.setFont(new Font("Calibri", Font.BOLD, 30));
		applicant6.setBackground(new Color(75, 0, 130));
		applicant6.setBounds(30, 350, 200, 29);
		ResultPane.add(applicant6);
		
		JLabel applicant7 = new JLabel("MeanCollision");
		applicant7.setFont(new Font("Calibri", Font.BOLD, 30));
		applicant7.setBackground(new Color(75, 0, 130));
		applicant7.setBounds(30, 400, 200, 29);
		ResultPane.add(applicant7);
		
		
		JLabel key = new JLabel("Ave Cost");
		key.setFont(new Font("Calibri", Font.BOLD, 30));
		key.setBackground(new Color(75, 0, 130));
		key.setBounds(30, 510, 200, 29);
		ResultPane.add(key);
		
		AveTime1List = new Vector<JTextArea>();
		AveTime2List = new Vector<JTextArea>();
		AveTime3List = new Vector<JTextArea>();
		AveTime4List = new Vector<JTextArea>();
		AveTime5List = new Vector<JTextArea>();
		AveTime6List = new Vector<JTextArea>();
		AveCollisionList = new Vector<JTextArea>();
		AveCostList = new Vector<JTextArea>();
		
		for(int i=0;i<6;i++){
			
			JTextArea AveTime1 = new JTextArea();
			AveTime1.setFont(new Font("Calibri", Font.PLAIN, 25));
			AveTime1.setBackground(new Color(240, 240, 240));
			AveTime1.setBounds(235+250*i, 100, 200, 40);
			ResultPane.add(AveTime1);
			AveTime1List.add(AveTime1);
			
			JTextArea AveTime2 = new JTextArea();
			AveTime2.setFont(new Font("Calibri", Font.PLAIN, 25));
			AveTime2.setBackground(new Color(240, 240, 240));
			AveTime2.setBounds(235+250*i, 150, 200, 40);
			ResultPane.add(AveTime2);
			AveTime2List.add(AveTime2);
			
			JTextArea AveTime3 = new JTextArea();
			AveTime3.setFont(new Font("Calibri", Font.PLAIN, 25));
			AveTime3.setBackground(new Color(240, 240, 240));
			AveTime3.setBounds(235+250*i, 200, 200, 40);
			ResultPane.add(AveTime3);
			AveTime3List.add(AveTime3);
			
			JTextArea AveTime4 = new JTextArea();
			AveTime4.setFont(new Font("Calibri", Font.PLAIN, 25));
			AveTime4.setBackground(new Color(240, 240, 240));
			AveTime4.setBounds(235+250*i, 250, 200, 40);
			ResultPane.add(AveTime4);
			AveTime4List.add(AveTime4);
			
			JTextArea AveTime5 = new JTextArea();
			AveTime5.setFont(new Font("Calibri", Font.PLAIN, 25));
			AveTime5.setBackground(new Color(240, 240, 240));
			AveTime5.setBounds(235+250*i, 300, 200, 40);
			ResultPane.add(AveTime5);
			AveTime5List.add(AveTime5);
			
			JTextArea AveTime6 = new JTextArea();
			AveTime6.setFont(new Font("Calibri", Font.PLAIN, 25));
			AveTime6.setBackground(new Color(240, 240, 240));
			AveTime6.setBounds(235+250*i, 350, 200, 40);
			ResultPane.add(AveTime6);
			AveTime6List.add(AveTime6);
			
			JTextArea AveCollision = new JTextArea();
			AveCollision.setFont(new Font("Calibri", Font.PLAIN, 25));
			AveCollision.setBackground(new Color(240, 240, 240));
			AveCollision.setBounds(235+250*i, 400, 200, 40);
			ResultPane.add(AveCollision);
			AveCollisionList.add(AveCollision);
			
			JTextArea AveCost = new JTextArea();
			AveCost.setFont(new Font("Calibri", Font.PLAIN, 25));
			AveCost.setBackground(new Color(240, 240, 240));
			AveCost.setBounds(235+250*i, 510, 200, 36);
			ResultPane.add(AveCost);
			AveCostList.add(AveCost);
		}
		
	}
}