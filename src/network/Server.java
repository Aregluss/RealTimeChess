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
	public PrintWriter output1;
	public BufferedReader input1;
	int i =0;
	String sending;
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
	      //  RealTimeChess.switchPanel("2");
	       // Thread.sleep(10000);
	        System.out.println("a");
	        System.out.println("Before writing objects");
	        input1 = new BufferedReader(new InputStreamReader (p1.getInputStream()));
	        output1 = new PrintWriter(p1.getOutputStream(), true);
	        GraphicsBoard.x1 = 1;
	        GraphicsBoard.y1 = 2;
	        GraphicsBoard.x2 = 3;
	        GraphicsBoard.y2 = 4;       
	        sending = (Integer.toString(GraphicsBoard.x1) + " ");
	        sending = sending.concat(Integer.toString(GraphicsBoard.y1) + " ");
	        sending = sending.concat(Integer.toString(GraphicsBoard.x2) + " ");
	        sending = sending.concat(Integer.toString(GraphicsBoard.y2) + " ");
	        output1.println(sending);
	        System.out.println("After writing objects");
//			String inputLine;
//			while ((inputLine = reader.readLine()) != null ){
//				System.out.println("Message: " + inputLine);
//			}
	        
	      }
	      catch(IOException ex){
	    	  ex.printStackTrace();
	      }
//	      catch (InterruptedException e) {
//	    	// TODO Auto-generated catch block
//	    	  e.printStackTrace();
//	    	 }
//	    
	    while(true)
	    {

	    	if(GraphicsBoard.moved == true){
	    			// has to print out a string of the 4 coordinates.
	    		//output1.println();
	    		GraphicsBoard.moved = false;
	    	}
	    }
	    	
	  }
	  
	  public void sendStuff(){
		  
		 
	  }
}