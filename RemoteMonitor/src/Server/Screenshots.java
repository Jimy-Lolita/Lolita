import java.awt.AWTException;
import java.awt.EventQueue;

import javax.swing.JFrame;

public class Screenshots{
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			
			public void run() {

				try{
					ScreenShotWindow ssw=new ScreenShotWindow();
					ssw.setVisible(true);
				}catch(AWTException e){
					e.printStackTrace();
				}
			}
		});
	}

	
	
}