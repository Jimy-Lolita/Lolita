package Client;

	import java.awt.event.ActionEvent;
	import java.awt.event.ActionListener;
	import java.io.BufferedReader;
	import java.io.InputStreamReader;
	import java.io.PrintWriter;
	import java.net.Socket;

	import javax.swing.JFrame;
	import javax.swing.JLabel;
	import javax.swing.JPanel;
	import javax.swing.JScrollPane;
	import javax.swing.JTextArea;
	import javax.swing.JTextField;


public class MainFrameC extends JFrame implements Runnable {
		private JPanel jpanel = new JPanel();
		private JLabel nameLabel = new JLabel("name:");
		private JTextField nameField = new JTextField();
		private JTextArea msgArea = new JTextArea();
		private JTextField sendField = new JTextField();
		private JButton button1=new JButton(" ");
		private JButton button2=new JButton(" ");
		private JScrollPane jScrollPane1 = new javax.swing.JScrollPane();
		private BufferedReader reader;
		private PrintWriter writer;
		private Socket socket;

		public MainFrameC(String title) {
			super(title);
			this.setSize(360, 500);
			this.add(jpanel);
			jpanel.setLayout(null);
			msgArea.setEditable(false);

			button1.setIcon(new javax.swing.ImageIcon("images/007.jpg"));
			jpanel.add(button1);
		    button1.setBounds(10, 10, 130, 132);
			button2.setIcon(new javax.swing.ImageIcon("images/007.jpg"));
			jpanel.add(button2);
			button2.setBounds(180, 160, 130, 132);

			jpanel.add(nameLabel);
			nameLabel.setBounds(10, 160, 60, 20);
			jpanel.add(nameField);
			nameField.setBounds(60, 10, 270, 21);
			jpanel.add(sendField);
			sendField.setBounds(10, 430, 320,21);
			msgArea.setColumns(20);
			msgArea.setRows(5);
			jScrollPane1.setViewportView(msgArea);
			jpanel.add(jScrollPane1);
			jScrollPane1.setBounds(10, 190, 320, 220);
			sendField.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					writer.println(nameField.getText()+"Student:" + sendField.getText());
					sendField.setText("");
				}
			});
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			while(true){
				try{
					msgArea.append(reader.readLine()+"\n");
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}
		}
		private void getSocket(){
			msgArea.append("Try to connect to server\n");
			try{
				socket = new Socket("localhost",7788);
				msgArea.append("You can talk with your teacher now\n");
				reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				writer = new PrintWriter(socket.getOutputStream(),true);
				new Thread(this).start();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		public static void main (String args[]){
			new RemoteMonitor().start();          //截图线程开始
			MainFrameC clientframe = new MainFrameC("communication");
			clientframe.setVisible(true);
			new FileClient();
			clientframe.getSocket();
		}
	}
