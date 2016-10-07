import java.awt.Container;
import java.awt.event.*;

import javax.swing.*;


public class Exit extends JFrame implements ActionListener{
	
      JButton button1=new JButton("ÍË³ö");
       Container cp=getContentPane();
       
      public Exit(){
    	  button1.addActionListener(this);
    	  cp.add(button1);
    	 
    	  setSize(450,600);
          setVisible(true);
          setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      }
      
      public void actionPerformed(ActionEvent e){
    	  String command=e.getActionCommand();
      	if(command.equals("ÍË³ö"))
      		exit();
      }
      
      private void exit(){
    	  System.exit(0);
      }
      
	public static void main(String[] args) {
		new Exit();

	}

}
