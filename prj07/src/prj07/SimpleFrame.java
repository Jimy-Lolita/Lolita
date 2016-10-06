package prj07;

import javax.swing.*;

public class SimpleFrame {
	public static void main(String args[]){
		JFrame frame = new JFrame("Simple Frame");
		frame.setSize(350, 240);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
