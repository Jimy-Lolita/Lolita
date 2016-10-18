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
		String info = "当前聊天人数"+size();
		System.out.println(info);
		sendToAll(info);
	}
	
	synchronized void sendClientCount() {
		String info = "聊天人数变化，当前为" + size() + "人在线！\n";
		System.out.println(info);
		sendToAll(info);

	}		*/
}
