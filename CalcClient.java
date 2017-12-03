package Calculate;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalcClient {
	
    private	BufferedReader in;
    private	PrintWriter out;
	
	JFrame frame = new JFrame("Chatter");
    JTextField textField = new JTextField(40);
    JTextArea messageArea = new JTextArea(8, 40);
    JTextArea namesArea = new JTextArea(8, 10);
    
    public CalcClient()
    {
    	textField.setEditable(false);
    	messageArea.setEditable(false);
        namesArea.setEditable(false);
    	frame.getContentPane().add(textField, "North");
    	frame.getContentPane().add(new JScrollPane(messageArea), "Center");
        frame.getContentPane().add(new JScrollPane(namesArea), "East");
    	frame.setSize(360, 480);
    	
    	textField.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				out.println(textField.getText());
				textField.setText("");
				
			}
		});
    }
    
    private String getServerAdress() 
    {
    	return JOptionPane.showInputDialog(frame,  "Enter IP of the Server", "Welcome to the Client Chatter", JOptionPane.QUESTION_MESSAGE);
    }
    
    private String getName()
    {
    	return JOptionPane.showInputDialog(frame,  "Enter your name", "Welcome to the Client Chatter", JOptionPane.PLAIN_MESSAGE);
    }
    
    private void run() throws IOException
    {
    	String serverAddress = getServerAdress();
    	
    	Socket socket= new Socket(serverAddress, 9040);
    	in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    	out = new PrintWriter(socket.getOutputStream(), true);
    	
    	while(true) 
    	{
    		String line= in.readLine();
    		if(line.startsWith("SUMBITNAME"))
    		{
    			out.println(getName());
    		}
    		
    		else if (line.startsWith("NAMEACCEPTED"))
    		{
    			textField.setEditable(true);
    		}
    		else if (line.startsWith("MESSAGE"))
    		{
    			messageArea.append(line.substring(8)+"\n");
    		}
                else if (line.startsWith("NAMES"))
                {
                    
                        namesArea.append(line.substring(6)+"\n");
                }
    	}
    }
	
	public static void main(String[] argv) throws Exception
	{
		 CalcClient client = new CalcClient();
		 client.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	     client.frame.setVisible(true);
	     client.run();
	     
	}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
}

