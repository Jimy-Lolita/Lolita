package Server;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class SocketManager extends ArrayList{
	synchronized void add(Socket socket){
		super.add(socket);
	}
	synchronized void remove(Socket socket){
		super.remove(socket);
	}
	synchronized void sendToAll(String str){
		PrintWriter writer =null;
		Socket socket;
		for(int i=0;i<size();i++){
			socket = (Socket)get(i);
			try{
				writer = new PrintWriter(socket.getOutputStream(),true);
				if(writer!=null)
					writer.println(str);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
/*	synchronized void sendClientInfo(){
		String info = "��ǰ��������"+size();
		System.out.println(info);
		sendToAll(info);
	}
	
	synchronized void sendClientCount() {
		String info = "���������仯����ǰΪ" + size() + "�����ߣ�\n";
		System.out.println(info);
		sendToAll(info);

	}		*/
}
