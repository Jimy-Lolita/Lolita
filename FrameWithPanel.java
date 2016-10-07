
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class FrameWithPanel extends JFrame {

	public FrameWithPanel(String name) {
		super("FarContrl");
		JButton button1, button2, button3, button4, button5;
		JButton buttons1, buttons2, buttons3, buttons4;
		Container cp = null;
		JPanel[] panel = new JPanel[4];
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

		button3 = new JButton();
		icon3 = new ImageIcon("images/003.jpg");
		label3 = new JLabel(icon3, SwingConstants.CENTER);
		button3.add(label3);

		button4 = new JButton();
		icon4 = new ImageIcon("images/004.jpg");
		label4 = new JLabel(icon4, SwingConstants.CENTER);
		button4.add(label4);

		panel[1] = new JPanel();
		panel[1].setBackground(Color.WHITE);

		panel[2] = new JPanel(new GridLayout(1, 4));
		panel[3] = new JPanel(new GridLayout(0, 1));

		/*
		 * panel[3].setOpaque(false); panel[3].setSize(33,33);
		 */
		panel[3].add(button1);
		button1.add(label1);

		panel[3].add(button2);
		button2.add(label2);

		panel[3].add(button3);
		button3.add(label3);

		panel[3].add(button4);
		button4.add(label4);

		// panel[2].setLayout(GridLayout(45,55));
		buttons1 = new JButton("远程监控");
		buttons2 = new JButton("传输文件");
		buttons3 = new JButton("控制鼠标");
		buttons4 = new JButton("显示时间");
		panel[2].add(buttons1);
		panel[2].add(buttons2);
		panel[2].add(buttons3);
		panel[2].add(buttons4);

//		NowTime nowtime = new NowTime();
//		buttons4.add(nowtime);
		
		cp = getContentPane();
		cp.add(panel[1], BorderLayout.CENTER);
		cp.add(panel[2], BorderLayout.NORTH);
		cp.add(panel[3], BorderLayout.EAST);
		setSize(700, 700);
	}

	public static void main(String args[]) {
		FrameWithPanel Frame = new FrameWithPanel("FarContrl");

		Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Frame.setVisible(true);
		Frame.setResizable(false);
		Frame.setLocationRelativeTo(null);
	}
}
