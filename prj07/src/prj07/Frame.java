package prj07;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;

public class Frame extends JFrame{
	JButton jButton1,jButton2,jButton3,jButton4;
	public Frame(){
		Container cp = getContentPane();
		jButton1 = new JButton("hh");
		jButton2 = new JButton("hh");
		jButton3 = new JButton("hhh");
//		jButton4 = new JButton("h");
		cp.add(jButton1,BorderLayout.NORTH);
		cp.add(jButton2,BorderLayout.CENTER);
		cp.add(jButton3,BorderLayout.EAST);	
//		cp.add(jButton4,BorderLayout.WEST);
		setSize(800,600);
		setTitle("FarContrl");
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public static void main(String args[]){
		Frame window = new Frame();
	}
}
