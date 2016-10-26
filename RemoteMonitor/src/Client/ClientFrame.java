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


public class ClientFrame extends JFrame implements Runnable {
		private JPanel jpanel = new JPanel();
		private JLabel nameLabel = new JLabel("name:");
		private JTextField nameField = new JTextField();
		private JTextArea msgArea = new JTextArea();
		private JTextField sendField = new JTextField();
		private JScrollPane jScrollPane1 = new javax.swing.JScrollPane();
		private BufferedReader reader;
		private PrintWriter writer;
		private Socket socket;

		public ClientFrame(String title) {
			super(title);
			this.setSize(360, 340);
			this.add(jpanel);
			jpanel.setLayout(null);
			msgArea.setEditable(false);
			jpanel.add(nameLabel);
			nameLabel.setBounds(10, 10, 60, 20);
			jpanel.add(nameField);
			nameField.setBounds(60, 10, 270, 21);
			jpanel.add(sendField);
			sendField.setBounds(10, 270, 320,21);
			msgArea.setColumns(20);
			msgArea.setRows(5);
			jScrollPane1.setViewportView(msgArea);
			jpanel.add(jScrollPane1);
			jScrollPane1.setBounds(10, 40, 320, 220);
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
			ClientFrame clientframe = new ClientFrame("communication");
			clientframe.setVisible(true);
			clientframe.getSocket();
		}
	}
