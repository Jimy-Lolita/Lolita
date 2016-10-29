import java.awt.event.*;
import java.awt.*;

import javax.swing.*;

import java.io.*;

public  class Talking extends JFrame implements ActionListener{
	
    JButton button1=new JButton("发送");
    JButton button2=new JButton("清空");
    Container cp=getContentPane(); 
	JTextArea text1=new JTextArea(10,20);
	JTextArea text2=new JTextArea(10,20);
	
    public Talking(){
    	
    	super("与管理者对话中...");
    	cp.setLayout(null);
    	
    	//放置text1
    	text1.setSize(300,200);
    	text1.setLocation(50,20);
    	cp.add(text1);
    	
    	//放置text2
    	text2.setSize(300,200);
    	text2.setLocation(50,240);
    	cp.add(text2);
    	
    	//两个按钮
    	button1.setSize(60,20);
    	button2.setSize(60,20);
    	button1.setLocation(100,460);
    	button2.setLocation(200,460);
    	cp.add(button1);
    	cp.add(button2);
    	
    	//监听器
    	button1.addActionListener(this);
    	button2.addActionListener(this);
    
    	
         setSize(450,600);
         setVisible(true);
         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
	
    public void actionPerformed(ActionEvent e){
    	String command=e.getActionCommand();
    	if(command.equals("发送"))
    		showTalking();
    	else if(command.equals("清空"))
    		DeleteAll();    		
    }
    
    private void DeleteAll(){
         text2.setText(null);
    }
    
    private void showTalking(){
    	text1.setText("我说：\n"+text2.getText());
    }
    
	public static void main(String[] args) {
		new Talking();			
	}
}
