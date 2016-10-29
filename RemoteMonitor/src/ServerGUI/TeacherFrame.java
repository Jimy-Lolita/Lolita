package ServerGUI;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.*;

import FileGoooood.FileChooser;
import Server.NowTime;
import Server.Screenshots;
import Server.ServerProcess;

public class TeacherFrame extends JFrame implements Runnable{

	private BufferedReader reader;
	private PrintWriter writer;
	private Socket socket;
	
	JTextArea Viewtext = new JTextArea(7, 20);
	JTextField Sendtext = new JTextField(20);

	public  TeacherFrame(String name){    
		JPanel[] panel = new JPanel[100];
		JButton[] button = new JButton[100];
		JLabel[] label = new JLabel[100];
		ImageIcon[] icon = new ImageIcon[10];
		
		Container cp = null;
		
		panel[1] = new JPanel(new GridLayout(1, 0));//锟剿碉拷
		panel[1].setPreferredSize(new Dimension(685, 175));
		panel[2] = new JPanel(new GridLayout(10, 4));//锟斤拷示锟斤拷锟斤拷
		panel[3] = new JPanel();// 文本输入 + time
		panel[4] = new JPanel();// 文本输入
		panel[5] = new JPanel();// time
		panel[6] = new JPanel();// 第一个
		
		button[1] = new JButton();
		button[1].setIcon(new javax.swing.ImageIcon("images/001.jpg")); 

		button[2] = new JButton(" ");
		button[2].setIcon(new javax.swing.ImageIcon("images/002rm.jpg")); 
		
		button[3] = new JButton(" ");
		button[3].setIcon(new javax.swing.ImageIcon("images/003shots.jpg")); 
		
		button[4] = new JButton(" ");
		button[4].setIcon(new javax.swing.ImageIcon("images/004file.jpg")); 

		button[5] = new JButton(" ");
		button[5].setIcon(new javax.swing.ImageIcon("images/001.jpg")); 
		
		button[3].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Screenshots sr = new Screenshots();
			}
		});

			button[4].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FileChooser csr = new FileChooser();
				csr.setVisible(true);
			}
		});
		
		for(int i = 6; i < 57 ; i ++){
				button[i] = new JButton();
			}

		cp = getContentPane();
		
		cp.add(panel[1],BorderLayout.NORTH);	
		JScrollPane scrollPane = new JScrollPane(panel[2]);
		scrollPane.setBounds(10, 10, 675, 200);
		panel[2].setPreferredSize(new Dimension(985, 2750));
		scrollPane.setLocation(0, 153);
		cp.add(scrollPane);
	
		for(int i = 1; i < 6 ; i ++){
			panel[1].add(button[i]);
		}

		panel[2].add(panel[6]);
		for(int i = 6; i < 55 ; i ++){
			panel[2].add(button[i]);
		}
		
		
		cp.add(panel[3],BorderLayout.SOUTH);
		panel[3].add(panel[4]);
		panel[3].add(panel[5]);
		panel[4].add(Viewtext,BorderLayout.NORTH);
		panel[4].add(Sendtext,BorderLayout.SOUTH);
		Viewtext.setLocation(4,4);
		Sendtext.setLocation(0,4);
		
		Sendtext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				writer.println("Teacher" + Sendtext.getText());
				Sendtext.setText("");
			}
		});
		
		NowTime nowtime = new NowTime();
		panel[5].add(nowtime);
		
		this.setSize(703, 700);
		this.getSocket();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	public static void main(String args[]) throws IOException{
		ServerProcess server = new ServerProcess();
		server.open();
		new TeacherFrame("FarContrl");
		server.getServer();
	}
	public void run() {
		while (true) {
			try {
				Viewtext.append(reader.readLine() + "\n");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void getSocket() {
		Viewtext.append("Try to connect to server\n");
		try {
			socket = new Socket("localhost", 7788);
			Viewtext.append("You can talk with your teacher now\n");
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			writer = new PrintWriter(socket.getOutputStream(), true);
			new Thread(this).start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

