import java.io.*;
import java.net.*;

public class FileClient {
      public void sendData(String filePath,String IP,int port)
      {
    	  int progress=0;
    	  
    	  //socket输出流
    	  DataOutputStream os=null;
    	  
    	  //文件输入流
    	  DataInputStream is=null;
    	  
    	  //建立socket连接
    	  Socket socket=null;
    	  
    	  try
    	  {
    		  //选择进行传输的文件
    		  File file=new File(filePath);
    		  //建立socket连接
    		  socket=new Socket(IP,port);
    		  os=new DataOutputStream(socket.getOutputStream());
    		  
    		  //将文件名及长度传给服务器端
    		  os.writeUTF(file.getName());
    		  os.flush();
    		  os.writeLong((long)file.length());
    		  os.flush();
    		  is=new DataInputStream(new BufferedInputStream(
    				  new FileInputStream(filePath)));
    		  
    		  //缓冲区大小
    		  int BufferSize=8192;
    		  //缓冲区
    		  byte[] buf=new byte[BufferSize];
    		  
    		  //传输文件
    		  while(true)
    		  {
    			  int read=0;
    			  if(is!=null)
    			  {
    				  read=is.read(buf);
    			  }
    			  progress+=Math.abs(read);
    			  if(read==-1) {break;}
    			  
    			  os.write(buf,0,read);
    			  
    			  //发送进度
    			  System.out.println("文件已发送"+(int)(100*progress/file.length())+"%");
    			  }
    		  os.flush();
    		  System.out.println("文件已上传完毕！");
    	  }catch(IOException e){
    		  e.printStackTrace();
    	  }finally{
    		  //关闭所有连接
    		  try{
    			  if(os!=null)
    				  os.close();
    		  }catch(IOException e){ }
    		  try{
    			  if(is!=null)
    				  is.close();
    		  }catch(IOException e){ }
    		  try{
    			  if(socket!=null)
    				  socket.close();
    		  }catch(IOException e){ }
    	  }
    	 
      }
	
	
	public static void main(String[] args) {
		if(args.length!=3)
		{
			System.err.println("Usage: FileClient: "
					+ "<file path> <server address> <port number>");
			System.exit(-1);
		}
		new FileClient().sendData(args[0],args[1], Integer.parseInt(args[2]));
        new FileClient().sendData("D:\\", "", 9600);//文件地址,主机的IP地址
	}

}
