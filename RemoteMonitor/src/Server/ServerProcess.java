package Server;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerProcess extends ArrayList {
	private SocketManager socketMan = new SocketManager();

	public void getServer() {
		try {
			ServerSocket serverSocket = new ServerSocket(7788);
			System.out.println("�������׽����Ѿ�����");
			while (true) {
				Socket socket = serverSocket.accept();
				new write_Thread(socket).start();
				socketMan.add(socket);
//				socketMan.sendClientCount();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	class write_Thread extends Thread {
		Socket socket = null;
		private BufferedReader reader;
		private PrintWriter writer;

		public write_Thread(Socket socket) {
			this.socket = socket;
		}

		public void run() {
			try {
				reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				writer = new PrintWriter(socket.getOutputStream(), true);
				String msgArea;
				while ((msgArea = reader.readLine()) != null) {
					System.out.println(msgArea);
					socketMan.sendToAll(msgArea);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String args[]) {
		ServerProcess server = new ServerProcess();
		server.getServer();
	}

	public void open() {
		// TODO Auto-generated method stub
		
	}
}
