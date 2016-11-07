package ServerGUI;

/*
 * 服务器主界面
 */

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import Server.FileServer;
import Server.NowTime;
import Server.RemoteMonitored;

import Server.Screenshots;
import Server.ServerProcess;

public class MainFrameS extends JFrame implements Runnable {

	// 单例模式，此类只能被实例化一次
	private static MainFrameS mfs;
	
	private BufferedReader reader;
	private PrintWriter writer;
	private Socket socket;
	 //public static String path =null;
	static Socket s;
	
	// 对应button[6~56]，用作标记，记录button监控的ip
	private String ip1[] = new String[51];
	
	JTextArea Viewtext = new JTextArea(7, 20);
	JTextField Sendtext = new JTextField(20);

	private static final long serialVersionUID = 1L;
	Dimension screenSize;
	
	// button数组变为类的局部变量，让类的所有方法都能引用
	private JButton[] button;
	
	// 单例模式，此类只能被new一次，只有一个实例，其它类只能通过这个方法访问该实例
	public static MainFrameS getInstance(String name) {
		if(mfs == null) {
			//没有实例化过，有实例化过直接返回实例
			mfs = new MainFrameS(name);
		}
		return mfs;
	}
	
	// 单例模式，构造方法私有化，只能通过getInstance方法被调用，维持整个类只有一个实例
	private MainFrameS(String name) {
		
		JPanel[] panel = new JPanel[10];
		button = new JButton[100];  //显示监控的按钮?
		JLabel[] label = new JLabel[100];
		ImageIcon[] icon = new ImageIcon[100];

		Container cp = null;

		panel[1] = new JPanel(new GridLayout(1, 0));// 菜单
		panel[1].setPreferredSize(new Dimension(685, 175));
		panel[2] = new JPanel(new GridLayout(10, 4));// 显示桌面
		panel[3] = new JPanel();// 文本输入 + time
		panel[4] = new JPanel();// 文本输入
		panel[5] = new JPanel();// time
		
		button[1] = new JButton();
		Icon runImg1 = new ImageIcon(this.getClass().getClassLoader().getResource("images/001.jpg"));
		button[1].setIcon(runImg1);
		
		button[2] = new JButton(" ");
		Icon runImg2 = new ImageIcon(this.getClass().getClassLoader().getResource("images/002rm.jpg"));
		button[2].setIcon(runImg2);

		button[3] = new JButton(" ");
		Icon runImg3 = new ImageIcon(this.getClass().getClassLoader().getResource("images/003shots.jpg"));
		button[3].setIcon(runImg3);
		button[3].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Screenshots sr = new Screenshots();
			}
		});

		button[4] = new JButton(" ");
		Icon runImg4 = new ImageIcon(this.getClass().getClassLoader().getResource("images/004file.jpg"));
		button[4].setIcon(runImg4);
		button[4].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FileServer sr = new FileServer(s);
				sr.choose();
				sr.start();
			}
		});

		button[5] = new JButton(" ");
		Icon runImg5 = new ImageIcon(this.getClass().getClassLoader().getResource("images/001.jpg"));
		button[5].setIcon(runImg5);

		for (int i = 6; i < 57; i++) {
			button[i] = new JButton();
		}

		cp = getContentPane();

		cp.add(panel[1], BorderLayout.NORTH);
		JScrollPane scrollPane = new JScrollPane(panel[2]);//放置监控的滚动面板
		scrollPane.setBounds(10, 10, 675, 200);
		panel[2].setPreferredSize(new Dimension(985, 2750));
		scrollPane.setLocation(0, 153);
		cp.add(scrollPane);

		for (int i = 1; i < 6; i++) {
			panel[1].add(button[i]);
		}

		//panel[6].setLayout(null);
		//rmi.setBounds(0, 0, panel[6].getWidth(), panel[6].getHeight());
		//panel[6].add(rmi);

		for (int i = 6; i < 55; i++) {
			panel[2].add(button[i]);
		}

		Thread t = new Thread(new RemoteMonitored());
		t.start();
		
		cp.add(panel[3], BorderLayout.SOUTH);

		panel[3].add(panel[4]);
		panel[3].add(panel[5]);
		panel[4].add(Viewtext, BorderLayout.NORTH);
		panel[4].add(Sendtext, BorderLayout.SOUTH);
		Viewtext.setLocation(4, 4);
		Sendtext.setLocation(0, 4);

		Sendtext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				writer.println("Teacher：" + Sendtext.getText());
				Sendtext.setText("");
			}
		});

		NowTime nowtime = new NowTime();
		panel[5].add(nowtime);

		this.setSize(703, 700);
		this.getSocket();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setVisible(true);

	}
	
	// 这个方法是交给线程来触发监控图片改变的
	public void setButtonWin(String nip, Image img) {
		int winnum = 0; //当前正在监控的窗口的数量
		for(int i = 0; i < ip1.length; i++) {
			if(ip1[i] != null && !ip1[i].equals("")) {
				if(nip.equals(ip1[i])){
					//FIXME 在已有监控窗口的button中找到ip一样的，更新这个窗口
					winnum = i + 6;
					break;
				}
			} else {
				//比较到这里，发现有一个button没有做上已有监控窗口的标志（ip），就用这个button进行监控
				ip1[i] = nip; //button作标记
				winnum = i + 6;
				break;
			}
		}
		//button.add
		//FIXME 改图片
		button[winnum].setIcon(new ImageIcon(img));
		
		System.out.println("窗口号为：" + (winnum - 6));
		System.out.println("lalala服务端识别的ip:" + nip);
	}

	public static void main(String args[]) {
		ServerProcess server = new ServerProcess();
		FileServer sr = new FileServer();
		//**************************
		//RemoteServer rs = new RemoteServer();
		try {
			
			server.open();
			
			//初始化服务端，不带监控窗口
			MainFrameS.getInstance("FarContrl");
			sr.open();
			System.out.println("Communication1");
		
			//rs.setup(9999);
			//System.out.println("RemoteServer1");
			
			s = sr.getsever();
			System.out.println("FileServer");
			
			server.getServer();	
			System.out.println("Communication2");
			
		} catch (IOException e) {
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
			Viewtext.append("You can talk with your student now\n");
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			writer = new PrintWriter(socket.getOutputStream(), true);
			new Thread(this).start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
