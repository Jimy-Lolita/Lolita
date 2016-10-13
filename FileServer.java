package hei;

import java.io.*; 
import java.net.*;  
 
public class FileServer { 
	
   private ServerSocket server = null; 
   Socket socket = null; 

   public void getData(String savePath,int port)
    { 
	   int progress=0;//程序进度
	   try
	    {
		  //建立socket监听
	       server=new ServerSocket(port);
	        while((socket=server.accept())!=null)
	        {
	           //建立socket输入流
	        	DataInputStream inputStream=new DataInputStream(
	        		new BufferedInputStream(socket.getInputStream()));
	        	//设置缓冲区大小
	        	  int BufferSize=8192;
	        	//缓冲区
	        	 byte[] buf=new byte[BufferSize];
	        	 int passedlen=0;//已接受文件大小
	        	 long len=0;//文件大小
	        	//获取文件名称
	        	  if(!savaPath.contains("."))
	        	  savaPath+=inputStream.readUTF();
	        	  DataOutputStream fileout=new DataOutputStream(
	        		  new BufferedOutputStream(new BufferedOutputStream(
	        		    	new fileOutputStream(savaPath))));
	        	//获取文件长度
	        	  len=inputStream.readLong();
	        		
	        	  System.out.println("文件的长度为"+len+" KB");
	        	  System.out.println("开始接收文件...");
	        		
	        	//获取文件，下面是进度条
	        	 System.out.println("#>>>>>>>>#>>>>>>>>>#>>>>>>>>>#>>>>>>>>>#>>>>>>>>>#");
	        	 System.out.println("#>>>>>>>>#>>>>>>>>>#>>>>>>>>>#>>>>>>>>>#>>>>>>>>>#");	
	        	
	        	 while(true)
	        	 {
	        		 int read=0;
	        		 if(inputStream!=null)
	        		 {
	        			 read=inputStream.read(buf);
	        		 }
	        		 passedlen+=len;
	        		 if(read=-1) break;
	        		 if((int)(passedlen*100.0/len)-progress>0)
	        		 {
	        			 progress=(int)(passedlen*100.0/len);
	        			 System.out.print("文件接收了"+progress+"%");
	        			 System.out.println(">");
	        		 }
	        		 fileOut.write(buf,0,read);
	        	 }
	        	 System.out.println("\n");
	        	 System.out.println("接收完成，文件存为："+savaPath);
	        	 fileOut.close();
	          }		
	       }catch(Exception e){
	    	   System.out.print("FileServer Exception is:"+e);
	    	   e.printStackTrace();
	       }
	}
   public static void main(String[] args){
	   //函数运行之前要指定待传送文件的地址
	   if(args.length!=2)
	   {
		   System.err.println("Usage: FileServer <save path> <port>");
		   System.exit(-1);
	   }
	   new FileServer().getData("D:\\\\", 9600);
	   new FileServer().getData(args[0],Integer.parseInt(args[1]));
   }
}
