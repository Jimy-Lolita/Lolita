
package Server;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.JFrame;

public class NowTime extends  JPanel {
	// ��� ��ʾʱ���JLabel
	public NowTime() {
		JLabel time = new JLabel();
		add(time);
		this.setTimer(time);
	}

	// ����Timer 1000msʵ��һ�ζ��� ʵ����һ���߳�
	private void setTimer(JLabel time) {
		final JLabel varTime = time;
		Timer timeAction = new Timer(1000, new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				long timemillis = System.currentTimeMillis();
				// ת��������ʾ��ʽ
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				varTime.setText(df.format(new Date(timemillis)));
			}
		});
		timeAction.start();
	}

	// ���з���
	public static void main(String[] args) {
		NowTime timeFrame = new NowTime();
		timeFrame.setSize(160, 80);
		timeFrame.setVisible(true);

	}
}
