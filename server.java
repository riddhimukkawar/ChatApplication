package chatapplication;
import java .net.*;
import java.io.*;
import javax.swing.*;
import java.awt.Font;
import java.awt.BorderLayout;  
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.lang.annotation.*;


public class server extends JFrame {

 
	ServerSocket serverSocket;
	Socket socket;
	BufferedReader br;
	PrintWriter out;
	JLabel heading=new JLabel("Server Area");
 JTextArea messageArea=new JTextArea();
 JTextField messageInput=new JTextField();

 Font font=new Font("Roboto",Font.PLAIN,20);
//constructor
	public server()
	
	{
		try
		{
			serverSocket = new ServerSocket(7777);
			System.out.println("server is start");
		
			socket = serverSocket.accept();
			br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out=new PrintWriter(socket.getOutputStream());

			createGUI();
            handleEvents();
			Startreading();
		//	Startwriting();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}

	private void handleEvents(){
        messageInput.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
			public void keyReleased(KeyEvent e) {
               // System.out.println("key released: " + e.getKeyCode());
                if (e.getKeyCode() == 10)
				{
					//System.out.println("you have pressed enter button");
					String contentToSend=messageInput.getText();
					messageArea.append("Me :"+ contentToSend +"\n");
					out.println(contentToSend);
					out.flush();
					messageInput.setText("");
				
				}
            }
        });
	
		
	}
	private  void createGUI()
	{

         this.setTitle("Server Messager[END]");
		 this.setSize(600,600);
		 this.setLocationRelativeTo(null);
		 this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 //coding for component
		 heading.setFont(font);
		 messageArea.setFont(font);
		 messageInput.setFont(font);
		 
		 heading.setHorizontalAlignment(SwingConstants.CENTER);
		 heading.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
		 messageArea.setEditable(false); 
		 // heading.setIcon(new ImageIcon("Cchatlogo.jpg"));
		  
 
 
		  ///frame layout
		  this.setLayout(new BorderLayout());
 
		  this.add(heading,BorderLayout.NORTH);
		  JScrollPane jScrollPane=new JScrollPane(messageArea);
		  this.add(jScrollPane,BorderLayout.CENTER);
		  this.add(messageInput,BorderLayout.SOUTH);
 
 
 
		  this.setVisible(true);
	 }
 


	public void Startreading() {
	
		//For Thread reading
		Runnable r1=()->{
			System.out.println("Readable..");
			try {
			while(true)
			{
				
				String msg=br.readLine();
				if(msg.equals("end"))
				{
			
					JOptionPane.showMessageDialog(null, "Client terminated the chat");
					messageInput.setEnabled(false);
					socket.close();
					break;
				}
				messageArea.append("Client :" +msg+"\n");
				//System.out.println("client:"+msg);}
			}
			}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			
			
		};
		new Thread(r1).start();
	}
	public void Startwriting()
	{
		Runnable r2=()->{
			try {
		         	while(true)
			      {
			     	
				
					BufferedReader br2=new BufferedReader(new InputStreamReader(System.in));
					String content=br2.readLine();
					out.println(content);
					out.flush();
					
				}
			}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			
			};
		
		new Thread(r2).start();
	}
	
	public static void main(String[] args) {
		System.out.println("this is sever side");
		new server();

	}

}
