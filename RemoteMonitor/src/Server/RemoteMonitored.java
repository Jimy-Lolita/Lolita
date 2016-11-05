package Server;

/*其中client为客户端（控制端），servlet为服务端（被控制端），
 * 直接运行两个jar文件即可。（运行jar文件必须在电脑安装jdk。）
 * 接受截图
*/
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.zip.ZipInputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
//屏幕监控的显示框用JButton,直接用一个setImageIcon(imaegIcon)就可以显示图片，就不用重画JFrame的背景
public class  RemoteMonitored  extends JButton implements Runnable{  
    private static final long serialVersionUID = 1L;  
    Dimension screenSize;  
    private ServerSocket imgss;
    private Socket imgs;
    public  RemoteMonitored () {  
    	//this.setSize(width, height);//设置按钮的大小
    	try {
			imgss = new ServerSocket(5001);
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
        //super();  
        //screenSize = Toolkit.getDefaultToolkit().getScreenSize();  
        //this.setSize(800, 640);  
        //Screen p = new Screen();  
        //Container c = this.getContentPane();  
        //c.setLayout(new BorderLayout());  
        //c.add(p, SwingConstants.CENTER);  
        //new Thread(p).start();  
        //SwingUtilities.invokeLater(new Runnable(){  
            //public void run() {  
            //    setVisible(true);  
          //  }});  
    }  
    /*public static void main(String[] args) {  
        new  RemoteMonitored ();  
    }  
  
    class Screen extends JPanel implements Runnable {  
  
        private static final long serialVersionUID = 1L;  
        private Image cimage;  
  
        public void run() {  
            ServerSocket ss = null;  
            try {  
                ss = new ServerSocket(5001);// 探听5001端口的连接  
                while (true) {  
                    Socket s = null;  
                    try {  
                        s = ss.accept();  
                        ZipInputStream zis = new ZipInputStream(s  
                                .getInputStream());  
                        zis.getNextEntry();  
                        cimage = ImageIO.read(zis);// 把ZIP流转换为图片  
                        repaint();  
                    } catch (Exception e) {  
                        e.printStackTrace();  
                    } finally {  
                        if (s != null) {  
                            try {  
                                s.close();  
                            } catch (IOException e) {  
                                e.printStackTrace();  
                            }  
                        }  
                    }  
                }  
            } catch (Exception e) {  
            } finally {  
                if (ss != null) {  
                    try {  
                        ss.close();  
                    } catch (IOException e) {  
                        e.printStackTrace();  
                    }  
                }  
            }  
        }  
  
        public Screen() {  
            super();  
            this.setLayout(null);  
        }  
  
        public void paint(Graphics g) {  
            super.paint(g);  
            Graphics2D g2 = (Graphics2D) g;  
            g2.drawImage(cimage, 0, 0, null);  
        }  
    }  */
//在线程中不断获取图片
	@Override
	public void run() {
		// TODO 自动生成的方法存根
		while(true){
			try {
				while((imgs = imgss.accept()) != null){
					System.out.println("端口5001有连接");
					InputStream is = imgs.getInputStream();
					Image i = ImageIO.read(is);
					if(i != null){
						System.out.println("图片接受成功");
						//对图像进行压缩，使得图片符合JButton大小
						i = i.getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH);
						this.setIcon(new ImageIcon(i));
					}
				}
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
	}
}  