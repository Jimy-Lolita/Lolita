
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class UPLOAD extends JFrame {
	JButton button1=new JButton("文件传输");
	JTextArea text1=new JTextArea(10,20);
    Container cp=getContentPane(); 
	public UPLOAD() {
	    super("文件传输");
    	cp.setLayout(null);
    	
    	text1.setSize(230,25);
    	text1.setLocation(50,15);
    	cp.add(text1);
    	
    	button1.setSize(60,20);
    	button1.setLocation(300,15);
    	cp.add(button1);
    	
    	button1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			//文件上传
			}
			});
    	
    	
        setSize(400,100);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void main(String args[]){
		new UPLOAD();
	}
}
