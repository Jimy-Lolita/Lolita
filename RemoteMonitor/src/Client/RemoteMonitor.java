package Client;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.Socket;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class RemoteMonitor extends Thread {
	private Dimension screenSize;
	private Rectangle rectangle;
	private Robot robot;

	public RemoteMonitor() {
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		rectangle = new Rectangle(screenSize);// 鍙互鎸囧畾鎹曡幏灞忓箷鍖哄煙
		try {
			robot = new Robot();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
		}
	}

	public void run() {
		ZipOutputStream os = null;
		Socket socket = null;
		while (true) {
			try {
				socket = new Socket("172.22.9.119", 5001);// 杩炴帴杩滅▼IP
				BufferedImage image = robot.createScreenCapture(rectangle);// 鎹曡幏鍒跺畾灞忓箷鐭╁舰鍖哄煙
				os = new ZipOutputStream(socket.getOutputStream());// 鍔犲叆鍘嬬缉娴�

				os.setLevel(9);
				os.putNextEntry(new ZipEntry("test.jpg"));
				JPEGCodec.createJPEGEncoder(os).encode(image);// 鍥惧儚缂栫爜鎴怞PEG
				os.flush();
				System.out.println("write");
				os.close();
				Thread.sleep(1000);// 姣忕20甯е�
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