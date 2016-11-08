package Server;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import Server.ServerProcess.write_Thread;

public class ServerProcess extends ArrayList {
	private SocketManager socketMan = new SocketManager();
	private ServerSocket serverSocket;
	public void open() throws IOException {
		serverSocket = new ServerSocket(7788);
		System.out.println("sssssSocket...");
	}
	public void getServer() {
		try {
			while (true) {
				Socket socket = serverSocket.accept();
				new write_Thread(socket).start();
				socketMan.add(socket);
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
}
