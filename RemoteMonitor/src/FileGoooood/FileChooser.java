package FileGoooood;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

import javax.swing.*;

public class FileChooser extends JFrame implements ActionListener{  
   JButton open=null;  
   public static void main(String[] args) {  
       new FileChooser();  
   }  
   public FileChooser(){  
       open=new JButton("open");  
       this.add(open);  
       this.setBounds(400, 200, 100, 100);  
       this.setVisible(true);  
       this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
       open.addActionListener(this);  
   }  
   @Override  
   public void actionPerformed(ActionEvent e) {  
       // TODO Auto-generated method stub  
       JFileChooser jfc=new JFileChooser();  
       jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES );  
       jfc.showDialog(new JLabel(), "ѡ��");  
       File file=jfc.getSelectedFile();  
       
       if(file.isDirectory()){  
           System.out.println("�ļ���:"+file.getAbsolutePath());  
       }else if(file.isFile()){  
           System.out.println("�ļ�:"+file.getAbsolutePath());  
       }  
       System.out.println(jfc.getSelectedFile().getName());  
         
   }  
 
}  