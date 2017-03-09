package network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.awt.CardLayout;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.*;
import java.util.HashMap;
import game.*;
import pieces.*;

//server needs to host a gameboard, retreive information from clients and modify gameboard
public class Server implements Runnable{
	public static int port = 5555;
	private static ServerSocket ss;
	private static Socket p1,p2;
	private boolean isRunning = false;
	private static GameBoard ChessGame;
	public static boolean sent = false;
	public ObjectOutputStream output1;
	public ObjectInputStream input1;
	int i =0;
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
	
	// needs to host a gameboard...
	
	  public void run()
	  {
	    System.out.println("listening port: " + port);
	    try
	      {
	        Socket p1 = ss.accept();
	        Thread.sleep(10000);
	        System.out.println("a");
	        RealTimeChess.switchPanel("2");
	        System.out.println("Before writing objects");
	      //input1 = new ObjectInputStream(p1.getInputStream());
	        output1 = new ObjectOutputStream(p1.getOutputStream());
	        System.out.println("After writing objects");
	        // output2.writeObject(GameBoard.Board);
	        // gets input from clients... four integers??? 
	       // BufferedReader reader = new BufferedReader(new InputStreamReader (p2.getInputStream()));
//			String inputLine;
//			while ((inputLine = reader.readLine()) != null ){
//				System.out.println("Message: " + inputLine);
//			}
	        
	      }
	      catch(IOException ex){
	    	  ex.printStackTrace();
	      }
	      catch (InterruptedException e) {
	    	// TODO Auto-generated catch block
	    	  e.printStackTrace();
	    	 }
	    
	    while(true)
	    {
	    	try {
	    		if(GraphicsBoard.moved == true){
				output1.writeObject(GameBoard.Board);
				GraphicsBoard.moved = false;
	    		}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
	    }
	  }
	  
	  public void sendBoard(){
		  
		 
	  }
}