package network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class Server implements Runnable{
	public static int port = 5555;
	private static ServerSocket ss;
	private boolean isRunning = false;
	
	public Server()
	  {
	    if (!isRunning)
	    {
	      runServer();
	      
	      Thread thread = new Thread(this);
	      thread.start();
	      
	      isRunning = true;
	    }
	  }
	
	private static void runServer(){
	    try
	    {
	      ss = new ServerSocket(port);
	      System.out.println("running");
	    }
	    catch (IOException ex)
	    {
	      ex.printStackTrace();
	    }
	 }

	
	  public void run()
	  {
	    System.out.println("listening port: " + port);
	    for (;;)
	    {
	      try
	      {
	        Socket s = ss.accept();
	        ObjectInputStream input = new ObjectInputStream(s.getInputStream());
	        ObjectOutputStream output = new ObjectOutputStream(s.getOutputStream());
	        
	        System.out.println("new connection");
	        
	        
	      }
	      catch(IOException ex){
	    	  ex.printStackTrace();
	      }
	    }
}
}
