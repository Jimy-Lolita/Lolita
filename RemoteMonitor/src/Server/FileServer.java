
package Server;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFileChooser;
import javax.swing.JLabel;

import ServerGUI.MainFrameS;

public class FileServer {
	private ServerSocket ss;
	public static String path = null;
	Socket st;
	DataInputStream fis;
	DataOutputStream ps;

	public FileServer() {
	}

	public FileServer(Socket s) {
		this.st = s;
	}

	public void open() throws IOException {
		ss = new ServerSocket(8821);
		System.out.println("sssssSocket...");
	}

	public void choose() {
		JFileChooser jfc = new JFileChooser();
		jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		jfc.showDialog(new JLabel(), "Choose");
		File file = jfc.getSelectedFile();
		if (file != null) {
			path = file.getAbsolutePath();
			System.out.println(jfc.getSelectedFile().getName());

		} else {
			jfc.setVisible(false);
		}
	}

	public Socket getsever() {
		Socket s = null;
		try {
			s = ss.accept();// 等待客户端 连接8821端口
			System.out.println("建立socket链接");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return s;
	}

	public void start() {
		
		try {
			while (true) {
				// 选择进行传输的文件
				String filePath = path;
				File fi = new File(filePath);
				System.out.println("st:" + st);
				ps = new DataOutputStream(st.getOutputStream());
				System.out.println("文件长度:" + (int) fi.length());
				System.out.println("文件路径:" + path);

				// public Socket accept() throws
				// IOException侦听并接受到此套接字的连接。此方法在进行连接之前一直阻塞。

				// DataInputStream dis = new DataInputStream(new
				// BufferedInputStream(s.getInputStream()));
				// dis.readByte();

				fis = new DataInputStream(new BufferedInputStream(
						new FileInputStream(fi/* new File(filePath) */)));

				// 将文件名及长度传给客户端。这里要真正适用所有平台，例如中文名的处理，还需要加工，具体可以参见Think In Java
				// 4th里有现成的代码。
				ps.writeUTF(fi.getName());
				ps.flush();
				ps.writeLong((long) fi.length());
				ps.flush();

				int bufferSize = 8192;
				byte[] buf = new byte[bufferSize];

				 while (true) {
	                    int read = 0;
	                    if (fis != null) {
	                        read = fis.read(buf);
	                    }

	                    if (read == -1) {
	                        break;
	                    }
	                    ps.write(buf, 0, read);
	                }
	                ps.flush();
	                // 注意关闭socket链接哦，不然客户端会等待server的数据过来，
	                // 直到socket超时，导致数据不完整。                
	                fis.close();
	                try{
	                st.close();    
	                }catch(Exception exx){
	                	 exx.printStackTrace();
	                }/***********/
	                System.out.println("文件传输完成");
	            }

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
}

// public static void main(String arg[]) {
// new FileServer().start();
// }
