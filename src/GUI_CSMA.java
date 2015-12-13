import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Window;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;

import java.awt.Color;

import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;

import java.awt.Toolkit;

import javax.swing.ImageIcon;

import java.awt.Font;

import javax.swing.JFormattedTextField;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;

import java.awt.Component;

import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;
import javax.swing.JPasswordField;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

public class GUI_CSMA extends JFrame {

	private JPanel contentPane;
	private JTextField FrameslotT;
	private JTextField SimuT;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) throws IOException
	{  
		Glogin();
	}
	
	public static void Glogin() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI_CSMA frame = new GUI_CSMA();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public static void GResult(final Vector<ResultStat> resultstat) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI_Result Resultframe = new GUI_Result();
					Resultframe.SetTexts(resultstat);
					Resultframe.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GUI_CSMA() throws IOException
	{
		setBackground(new Color(240, 240, 240));
		setTitle("A Virtual Network with End-to-End Data Delivery");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setIconImage(Toolkit.getDefaultToolkit().getImage(GUI_CSMA.class.getResource("/image/waitingbac.jpg")));
		setSize(700,490);
		setLocationRelativeTo(null);
		
		contentPane = new JPanel();
		contentPane.setToolTipText("");
		contentPane.setForeground(new Color(0, 0, 0));
		contentPane.setBackground(new Color(250, 240, 230));
		contentPane.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel applicant = new JLabel("Frameslot Time");
		applicant.setFont(new Font("Calibri", Font.BOLD, 30));
		applicant.setBackground(new Color(75, 0, 130));
		applicant.setBounds(110, 112, 200, 29);
		contentPane.add(applicant);

		JLabel key = new JLabel("lambda");
		key.setFont(new Font("Calibri", Font.BOLD, 30));
		key.setBackground(new Color(75, 0, 130));
		key.setBounds(150, 174, 200, 29);
		contentPane.add(key);
		
		JLabel Rout = new JLabel("Routing Algorithm");
		Rout.setFont(new Font("Calibri", Font.BOLD, 30));
		Rout.setBackground(new Color(75, 0, 130));
		Rout.setBounds(80, 234, 250, 29);
		contentPane.add(Rout);
		
		JComboBox FrameSlot = new JComboBox();
		FrameSlot.setFont(new Font("Calibri", Font.PLAIN, 25));
		FrameSlot.setBackground(new Color(240, 248, 255));
		FrameSlot.setBounds(311, 112, 139, 36);
		FrameSlot.addItem("50");
		FrameSlot.addItem("150");
		FrameSlot.addItem("250");
		FrameSlot.addItem("350");
		FrameSlot.addItem("500");
		FrameSlot.addItem("2000");
		FrameSlot.setSelectedItem("500");
		FrameSlot.addItemListener(new ItemListener(){
		public void itemStateChanged(ItemEvent evt) {
		if(evt.getStateChange() == ItemEvent.SELECTED){
		 try{
		 String frameslot = evt.getItem().toString();
		Constants.frameslot=Integer.parseInt(frameslot); 
		 System.out.println("You chose " + Constants.frameslot);
		 }catch (Exception e){	      
		   }
		 } 
		}
   });
		contentPane.add(FrameSlot);
		
		JComboBox Lamata = new JComboBox();
		Lamata.setFont(new Font("Calibri", Font.PLAIN, 25));
		Lamata.setBackground(new Color(240, 248, 255));
		Lamata.setBounds(311, 174, 139, 36);
		Lamata.addItem("0.1");
		Lamata.addItem("0.5");
		Lamata.addItem("2");
		Lamata.setSelectedItem("0.5");
		Lamata.addItemListener(new ItemListener(){
		public void itemStateChanged(ItemEvent evt) {
		if(evt.getStateChange() == ItemEvent.SELECTED){
		 try{
		 String Lamatas = evt.getItem().toString();
		 if(Lamatas=="2")
			 Constants.lambda=2;
		 else if (Lamatas=="0.1")
			 Constants.lambda=(float) 0.1;	 
		 System.out.println("You chose " + Lamatas);
		 }catch (Exception e){	      
		   }
		 } 
		}
   });
		contentPane.add(Lamata);
		
		JComboBox dbtype = new JComboBox();
		dbtype.setFont(new Font("Calibri", Font.PLAIN, 25));
		dbtype.setBackground(new Color(240, 248, 255));
		dbtype.setBounds(311, 234, 239, 36);
		dbtype.addItem("Random");
		dbtype.addItem("Dijkstra's Algorithm");
		dbtype.setSelectedItem("Dijkstra's Algorithm");
		dbtype.addItemListener(new ItemListener(){
		public void itemStateChanged(ItemEvent evt) {
		if(evt.getStateChange() == ItemEvent.SELECTED){
		 try{
		 String algorithm = evt.getItem().toString();
		 Constants.RoutingAlgorithm=algorithm;
		 System.out.println("You chose " + algorithm);
		 }catch (Exception e){
		      
		   }
		 } 
		}
   });
		contentPane.add(dbtype);
		
		JButton login = new JButton("Start"); 
		login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			CSMA_PART3 csma = new CSMA_PART3 ();
			System.out.println("Start Simulating");
			Vector<Packet> simresult = new Vector<Packet> ();
			simresult = csma.CSMACD();	
			Vector<ResultStat> resultstatList = new Vector<ResultStat> ();
			
			for (int n=0; n<CSMA_PART3.NumOfNodes; n++)
			{
				ResultStat resultstat = new ResultStat ();
				resultstat = resultstat.statistics(simresult, n);	
				resultstatList.add(resultstat);
				try {
					ResultOutput.output(resultstat, n);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
	
			ResultStat resultstat_same = new ResultStat ();
			resultstat_same = resultstat_same.nodetonode_sameside(simresult);
			ResultStat resultstat_diff = new ResultStat ();
			resultstat_diff = resultstat_diff.nodetonode_diffside(simresult);
			resultstatList.add(resultstat_same);
			resultstatList.add(resultstat_diff);
			GResult(resultstatList);
			//dispose();
			}
		});
		
		login.setFont(new Font("Simplified Arabic", Font.BOLD, 30));
		login.setBackground(Color.ORANGE);
		login.setBounds(277, 288, 143, 52);
		contentPane.add(login);
		
		JLabel background=new JLabel();
		background.setLocation(0, 0);
		background.setIcon(new ImageIcon(GUI_CSMA.class.getResource("/image/nuannuanbac.jpg")));
		background.setSize(700,490);
		contentPane.add(background);   
	}
}
