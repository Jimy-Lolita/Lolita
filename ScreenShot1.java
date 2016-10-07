import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import javax.swing.*;

//捕获屏幕
public class ScreenShot1 extends JFrame implements ActionListener {

	private ScreenCaptureUtil scrCaptureUtil = null;// 捕获屏幕的工具类
	private PaintCanvas canvas = null;// 画布，用于画捕获到的屏幕图像

	public ScreenShot1() {
		super("Screen Capture");
		init();
	}

	//初始化
	private void init() {

		scrCaptureUtil = new ScreenCaptureUtil();// 创建抓屏工具，建立对象
		canvas = new PaintCanvas(scrCaptureUtil);// 创建画布

		Container cp= this.getContentPane();
		cp.setLayout(new BorderLayout());
		cp.add(canvas, BorderLayout.CENTER);

		JButton capButton = new JButton("抓 屏");
		cp.add(capButton, BorderLayout.SOUTH);
		capButton.addActionListener(this);
		this.setSize(400, 400);
		this.setVisible(true);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
	
// 点击“抓屏”按钮时，在画布上画屏幕图像
	public void actionPerformed(ActionEvent e) {
		canvas.drawScreen();
	}

	public static void main(String[] args) {
		new ScreenShot1();
	}
}


// 抓屏工具类
class ScreenCaptureUtil {
	private Robot robot = null;// 抓屏的主要工具类
	private Rectangle scrRect = null;// 屏幕的矩形图像

	public ScreenCaptureUtil() {
		try {
			robot = new Robot();// 创建一个抓屏工具
		} catch (Exception ex) {
			System.out.println(ex.toString());
		}
		// 获取屏幕的矩形图像
		Dimension scrSize = Toolkit.getDefaultToolkit().getScreenSize();
		scrRect = new Rectangle(0, 0, scrSize.width, scrSize.height);
	}

	//抓屏方法,返回一个图像 
	public BufferedImage captureScreen() {
		BufferedImage scrImg = null;
		try {
			scrImg = robot.createScreenCapture(scrRect);// 抓的是全屏图
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return scrImg;
	}
}

// 画布类，用于显示抓屏得到的图像

class PaintCanvas extends JPanel {
	private ScreenCaptureUtil scrCaptureUtil = null;// 抓屏工具
	private BufferedImage scrImg = null;// 待画的图像　

	public PaintCanvas(ScreenCaptureUtil screenUtil) {
		this.scrCaptureUtil = screenUtil;
	}

	
	// 重载JPanel的paintComponent，用于画背景
	protected void paintComponent(Graphics g) {
		if (scrImg != null) {
			int iWidth = this.getWidth();
			int iHeight = this.getHeight();
			g.drawImage(scrImg, 0, 0, iWidth, iHeight, 0, 0, scrImg.getWidth(),
					scrImg.getHeight(), null);
		}
	}

// 画屏幕图像的方法

	public void drawScreen() {
		Graphics g = this.getGraphics();
		scrImg = scrCaptureUtil.captureScreen();// 抓屏，获取屏幕图像
		if (scrImg != null) {
			this.paintComponent(g);// 画图
		}
		g.dispose();// 释放资源
	}

}

