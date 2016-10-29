package Server;
import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JWindow;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

//Jwindow Ҳ���Ĵ󶥼����֮һ����λ��ͬ��JFrame����һ���ޱ������Ĵ���
public class ScreenShotWindow extends JWindow {

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int orgx,orgy,endx,endy;
	
	/**image������:
	 * 1.��ȡ������Ļ�Ľ�ͼ*/
	private BufferedImage image=null;
	private BufferedImage tempImage=null;
	private BufferedImage saveImage=null;
	
	private ToolsWindow tools=null;
	
	
	public ScreenShotWindow() throws AWTException {
		
		//��ȡĬ����Ļ�豸
		GraphicsEnvironment environment=GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice screen=environment.getDefaultScreenDevice();
		
        //��ȡ��Ļ�ߴ�
		Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
		this.setBounds(0, 0, d.width, d.height);
		//��ȡ��Ļ��ͼ
		Robot robot=new Robot(screen);
//		Robot robot=new Robot();
		
//		image=new BufferedImage((int)d.getWidth(),(int)d.getHeight(),BufferedImage.TYPE_4BYTE_ABGR);
		image=robot.createScreenCapture(new Rectangle(0, 0, d.width, d.height));
		
		//��������û���ʱ�����
		this.addMouseListener(new MouseAdapter() {
			
			//��갴�µ��¼�����
			@Override
			public void mousePressed(MouseEvent e) {
				
				//
				orgx=e.getX();
				orgy=e.getY();
				
				if(tools!=null){
					tools.setVisible(false);
				}
			}
			
			//���̧����¼�����
			@Override
			public void mouseReleased(MouseEvent e) {

				if(tools==null){
					tools=new ToolsWindow(ScreenShotWindow.this,e.getX(),e.getY());
					
				}else{
					tools.setLocation(e.getX(), e.getY());
				}
				tools.setVisible(true);
				tools.toFront();
				
			}
			
		});
		
		//��������ƶ��ļ���
		this.addMouseMotionListener(new MouseMotionAdapter() {
			
			//��껬���ļ���
			//�ڻ��������лᱻ��������
			@Override
			public void mouseDragged(MouseEvent e) {

				endx=e.getX();
				endy=e.getY();
				
				//��ʱͼ�����ڻ�����Ļ���������Ļ��˸
				Image tempImage2=createImage(ScreenShotWindow.this.getWidth(),ScreenShotWindow.this.getHeight());
				//���ڻ�ͼ
				Graphics g=tempImage2.getGraphics();
				g.drawImage(tempImage, 0, 0,null);
				
				int x=Math.min(orgx, endx);
				int y=Math.min(orgy, endy);
				
				int width=Math.abs(endx-orgx)+1;
				int height=Math.abs(endy-orgy)+1;
				
				g.setColor(Color.RED);
				//��֤ͼƬ���β����߿򸲸�
				g.drawRect(x-1, y-1, width+1, height+1);
				
				//getSubimage(int x,int y,int w,int h)���ڷ��ع涨λ���еľ���ͼ��BufferedImag������
				saveImage=image.getSubimage(x, y, width, height);
				//���ڻ���ǰͼ���еĿ���ͼ��
				g.drawImage(saveImage, x, y, null);
				
				ScreenShotWindow.this.getGraphics().drawImage(tempImage2,
						0, 0,ScreenShotWindow.this);
	
			}
			
			
		});
		
	}
	
	//��д�˻滭�ķ���
	@Override
	public void paint(Graphics g) {

		//new RescaleOp(float[] scaleFactors, float[] offsets, RenderingHints hints)
		//����һ��������ϣ�����������Ӻ�ƫ�������� RescaleOp��
		//RescaleOp ���й�ͼ�����ŵ���
		//RescaleOp.filter(BufferedImage src,BufferedImage dest)
		//���ڶ�Դͼ��src��������
		RescaleOp ro=new RescaleOp(0.8f,0, null);
		tempImage=ro.filter(image, null);
		g.drawImage(tempImage, 0, 0, this);
			
	}
	//����ͼ���ļ�
	public void saveImage() throws IOException{
		JFileChooser jfc=new JFileChooser();
		jfc.setDialogTitle("����");
		
		//�ļ����������û����˿�ѡ����ļ�
		FileNameExtensionFilter filter=new FileNameExtensionFilter("JPG", "jpg");
		jfc.setFileFilter(filter);
		
		//��ʼ��һ��Ĭ���ļ�(���ļ�������������)
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyymmddHHmmss");
		String filename=sdf.format(new Date());
		
		File filePath=FileSystemView.getFileSystemView().getHomeDirectory();
		File defaultFile=new File(filePath+File.separator+filename+".jpg");
		jfc.setSelectedFile(defaultFile);
		
		int flag=jfc.showSaveDialog(this);
		if(flag==JFileChooser.APPROVE_OPTION){
			File file=jfc.getSelectedFile();
			String path=file.getPath();
			//����ļ���׺�������û����������׺�����벻��ȷ�ĺ�׺
			if(!(path.endsWith(".jpg")||path.endsWith("JPG"))){
				path+=".jpg";
			}
			//д���ļ�
			ImageIO.write(saveImage, "jpg", new File(path));
			System.exit(0);
		}
	}

}