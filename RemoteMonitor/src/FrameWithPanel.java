
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class FrameWithPanel extends JFrame implements Runnable {

	JTextArea text = new JTextArea(20, 46);
	
	JTextArea text1 = new JTextArea(10, 46);	
	JTextField text2 = new JTextField(46);

	private BufferedReader reader;
	private PrintWriter writer;
	private Socket socket;

	public FrameWithPanel(String name) {
		super("FarContrl");
		JButton button1, button2, button3, button4, button5;
		JButton buttons1, buttons2, buttons3, buttons4;
		Container cp = null;
		JPanel[] panel = new JPanel[10];
		JLabel label1, label2, label3, label4, label5;
		ImageIcon icon1, icon2, icon3, icon4, icon5;

		button1 = new JButton();
		icon1 = new ImageIcon("images/001.jpg");
		label1 = new JLabel(icon1, SwingConstants.CENTER);
		button1.add(label1);

		button2 = new JButton();
		icon2 = new ImageIcon("images/002.jpg");
		label2 = new JLabel(icon2);
		button2.add(label2);

		button4 = new JButton();
		icon4 = new ImageIcon("images/004.jpg");
		label4 = new JLabel(icon4, SwingConstants.CENTER);
		button4.add(label4);

		button4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);// 隐藏窗体
				System.exit(0);// 退出程序
			}
		});

		text2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				writer.println("老师:" + text2.getText());
				text2.setText("");
			}
		});

		panel[2] = new JPanel(new GridLayout(1, 4));
		panel[3] = new JPanel(new GridLayout(0, 1));
		panel[0] = new JPanel(new GridLayout(0, 1));

		panel[3].add(button1);
		button1.add(label1);

		panel[3].add(button2);
		button2.add(label2);

		panel[3].add(button4);
		button4.add(label4);

		buttons1 = new JButton("远程监控");
		buttons2 = new JButton("传输文件");
		buttons3 = new JButton("截取屏幕");

//		buttons3.onClick.AddListener((new Screenshots());
		
		panel[2].add(buttons1);
		panel[2].add(buttons2);
		panel[2].add(buttons3);

		NowTime nowtime = new NowTime();
		panel[0].add(nowtime);

		// Talking talking =new Talking();
		// panel[4].add(talking);

		panel[1] = new JPanel();
		panel[1].setLayout(new FlowLayout(10));
		panel[4] = new JPanel();
		panel[4].setSize(30, 30);
		panel[4].setBackground(Color.WHITE);

		cp = getContentPane();
		cp.add(panel[1], BorderLayout.CENTER);
//		cp.add(panel[4], BorderLayout.CENTER);
		cp.add(panel[2], BorderLayout.NORTH);
		cp.add(panel[3], BorderLayout.EAST);
		panel[2].add(panel[0], BorderLayout.EAST);

	//	panel[1].add(panel[4]);
		panel[1].add(text);
		panel[1].add(text1);
		panel[1].add(text2);

		setSize(700, 700);
	}

	public void run() {
		while (true) {
			try {
				text1.append(reader.readLine() + "\n");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void getSocket() {
		text1.append("尝试与服务器连接\n");
		try {
			socket = new Socket("localhost", 7788);
			text1.append("信息传输准备完毕\n");
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			writer = new PrintWriter(socket.getOutputStream(), true);
			new Thread(this).start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String args[]) {

		FrameWithPanel Frame = new FrameWithPanel("FarContrl");
		Frame.getSocket();
		Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Frame.setResizable(false);
		Frame.setLocationRelativeTo(null);
		Frame.setVisible(true);
	}
}
