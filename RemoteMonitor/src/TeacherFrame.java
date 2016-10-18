package Server;

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

public class TeacherFrame extends JFrame implements Runnable{

	private BufferedReader reader;
	private PrintWriter writer;
	private Socket socket;
	
	JTextArea Viewtext = new JTextArea(7, 20);
	JTextField Sendtext = new JTextField(20);

	public  TeacherFrame(String name){    
		JPanel[] panel = new JPanel[10];
		JButton[] button = new JButton[20];
		JLabel[] label = new JLabel[10];
		ImageIcon[] icon = new ImageIcon[10];
		
		Container cp = null;
		
		panel[1] = new JPanel(new GridLayout(1, 0));//菜单
		panel[1].setPreferredSize(new Dimension(685, 175));
		panel[2] = new JPanel(new GridLayout(2, 4));//显示桌面
		panel[3] = new JPanel();//文本输入  + time 
		panel[4] = new JPanel();//文本输入
		panel[5] = new JPanel();//time
//		panel[6] = new JPanel(new GridLayout(0, 1));//占位
		
		button[1] = new JButton();
		icon[1] = new ImageIcon("images/005.jpg");
		label[1] = new JLabel(icon[1], SwingConstants.CENTER);
		button[1].add(label[1]);
		
		button[2] = new JButton("远程监控");
		button[3] = new JButton("屏幕截图");
		button[4] = new JButton("传输文件");
		
		button[3].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Screenshots sr = new Screenshots();
			}
		});
		
		button[5] = new JButton("001");
		button[6] = new JButton("hhh");
		button[7] = new JButton("hhh");
		button[8] = new JButton("hhh");
		button[9] = new JButton("hhh");
		button[10] = new JButton("hhh");
		button[11] = new JButton("hhh");
		button[12] = new JButton("hhh");
		
		cp = getContentPane();
		
		cp.add(panel[1],BorderLayout.NORTH);	
		JScrollPane scrollPane = new JScrollPane(panel[2]);
		scrollPane.setBounds(10, 10, 675, 200);
		panel[2].setPreferredSize(new Dimension(985, 550));
		scrollPane.setLocation(0, 153);
		cp.add(scrollPane);
	
		panel[1].add(button[1]);
		panel[1].add(button[2]);
		panel[1].add(button[3]);
		panel[1].add(button[4]);
		
		panel[2].add(button[5]);
		panel[2].add(button[6]);
		panel[2].add(button[7]);
		panel[2].add(button[8]);
		
		panel[2].add(button[9]);
		panel[2].add(button[10]);
		panel[2].add(button[11]);
		panel[2].add(button[12]);		
		
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
	public static void main(String args[]){
		ServerProcess server = new ServerProcess();
		try {
			server.open();
			new TeacherFrame("FarContrl");
			server.getServer();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

