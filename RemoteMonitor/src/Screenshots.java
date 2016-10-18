package Server;
import java.awt.AWTException;
import java.awt.EventQueue;

import javax.swing.JFrame;

public class Screenshots {
	public Screenshots() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ScreenShotWindow ssw = new ScreenShotWindow();
					ssw.setVisible(true);
				} catch (AWTException e) {
					e.printStackTrace();
				}
			}
		});
	}
}