package Calculate;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;

public class Calculate {
	
	private static final int PORT= 9040;
	
	private static HashSet<String> names= new HashSet<String>();
	
	private static HashSet<PrintWriter> writers = new HashSet<PrintWriter>();
	
	
	
	public static void main(String[] argv) throws Exception
	{
		System.out.println("The server is on");
		
		ServerSocket listener = new ServerSocket(PORT);
		try
		{
			while (true)
			{
				new Handler(listener.accept()).start();
			}
		}
		finally
		{
			listener.close();
		}
		
	}
	
	private static class Handler extends Thread 
	{
		private String name;
		private Socket socket;
		private BufferedReader in;
		private PrintWriter out;
		
		public Handler(Socket socket)
		{
			this.socket= socket;
		}
	
	
            public void run() 
            {
		try
		{
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			out = new PrintWriter(socket.getOutputStream(), true);
			
			while (true) //sprobowac zamienic na wait i notify
			{
				out.println("SUMBITNAME");
				name= in.readLine();
				if (name== null) 
				{
					return;
				}
				synchronized (names) 
				{
					if (!names.contains(name))
					{
						names.add(name);
						break;   //sprobowac zamienic na break i notify
					}
				}
			}
			
			out.println("NAMEACCEPTED");
			writers.add(out);
			
			while(true)
			{
				String input=in.readLine();
                                int temp = Integer.parseInt(input);
				if(input==null)
				{
					return;
				}
                                sleep(2000);
                                temp*=2;
				for (PrintWriter writer : writers)
				{
                                    
					writer.println("MESSAGE "+name+": "+temp);
				}
			}
			
		}
		catch (IOException|InterruptedException e)
		{
			System.out.println(e);
		}
		finally
		{
			if (name != null)
			{
				names.remove(name);
			}
			if (out != null)
			{
				writers.remove(out);
			}
			try
			{
				socket.close();
			}
			catch(IOException e)
			{}
		}
            }
		
		
        }
}
	
