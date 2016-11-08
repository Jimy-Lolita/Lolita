package Client;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.imageio.ImageIO;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class RemoteMonitor extends Thread {
	private Dimension screenSize;
	private Rectangle rectangle;
	private Robot robot;
	OutputStream os;
	public RemoteMonitor() {
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		rectangle = new Rectangle(screenSize);// 可以指定捕获屏幕区域
		try {
			robot = new Robot();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
		}
	}

	public void run() {
		os = null;
		Socket socket = null;
		while (true) {
			try {
				socket = new Socket("172.25.51.17", 5001);// 连接远程IP
				BufferedImage image = robot.createScreenCapture(rectangle);// 捕获制定屏幕矩形区域

				os = socket.getOutputStream();
				ImageIO.write(image, "JPEG", os);
				
				os.flush();
				socket.close();
				Thread.sleep(1000);// 每秒20帧
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (os != null) {
					try {
						os.close();
					} catch (Exception ioe) {
					}
				}
				if (socket != null) {
					try {
						socket.close();
					} catch (IOException e) {
					}
				}
			}
		}
	}
}