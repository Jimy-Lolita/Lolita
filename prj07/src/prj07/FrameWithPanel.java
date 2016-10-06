package prj07;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.apache.tomcat.jni.File;

public class FrameWithPanel extends JFrame{
	JButton[] buttons = new JButton[15];
	Container cp = null;
	JPanel panel1,panel2,panel3,panel4;
	JLabel label1;
	ImageIcon icon1;
	public FrameWithPanel(){
		super("FarContrl");
		
		icon1 = new ImageIcon("images/001.jpg");
		label1 = new JLabel(icon1,SwingConstants.CENTER);
        label1.setSize(900,900);
        
		panel1=new JPanel();
		panel1.setBackground(Color.GRAY);

		panel2=new JPanel();	
		int i;
		for ( i = 0; i < 9; i++) {
	           buttons[i] = new JButton();
	           panel2.add(buttons[i]);
	           buttons[i].add(label1);
	       }
		
		panel3=new JPanel(new GridLayout(0, 1));
		for ( i = 8; i < 13; i++) {
	           buttons[i] = new JButton("1");
	           panel3.add(buttons[i]);
	       }		
		
		cp=getContentPane();
		cp.add(panel1,BorderLayout.CENTER);
		cp.add(panel2,BorderLayout.NORTH);
		cp.add(panel3,BorderLayout.EAST);	
		setSize(800,600);		
	}
	public static void main(String args[]){
		FrameWithPanel Frame = new FrameWithPanel();
		Frame.setVisible(true);
		Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
