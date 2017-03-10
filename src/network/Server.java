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
	public volatile boolean truth = true;
	public static PrintWriter output1;
	public BufferedReader input1;
	int i =0;
	public static String sending;
	public Server() throws IOException
	  {
	    if (!isRunning)
	    {
	      runServer();
	       input1 = new BufferedReader(new InputStreamReader (p1.getInputStream()));
	       output1 = new PrintWriter(p1.getOutputStream(), true);  
	      Thread thread = new Thread(this);
	      thread.start();
	      
	      isRunning = true;
	    }
	  }
	private static void runServer(){
	    try
	    {
	      ss = new ServerSocket(port);
	      System.out.println("listening port: " + port);
	      p1 = ss.accept();
	       RealTimeChess.switchPanel("2");
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
	    try
	      { 
	    	while(truth){
	    		receive();	
		    }

	       }
	      catch(Exception ex){
	    	  ex.printStackTrace();
	      }
	   
	  }
	  
	  public static void send(){
		 // System.out.println("In send stuff: " +);
			  	System.out.println("Before sending output!!!!!!");
			  	sending = ("[" + Integer.toString(GraphicsBoard.y1) + ",");
		        sending = sending.concat(Integer.toString(GraphicsBoard.x1) + ",");
		        sending = sending.concat(Integer.toString(GraphicsBoard.y2) + ",");
		        sending = sending.concat(Integer.toString(GraphicsBoard.x2) + "]");
		        output1.println(sending);
		    	System.out.println("After sending output!!!!"); 
	  }
	  public void receive() throws IOException{

			String temp_input;
		 if((temp_input = input1.readLine()) != null){
			//System.out.println("Client has received: " + temp_input);
		 }
		 String[] items = temp_input.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\\s", "").split(",");
		 int[] results = new int[items.length];
		 System.out.println("b");
		 for (int i = 0; i < items.length; i++) {
		     try {
		         results[i] = Integer.parseInt(items[i]);
		     } catch (NumberFormatException nfe) {
		         //NOTE: write something here if you need to recover from formatting errors
		     };
		 }
		 System.out.println("c");
		 System.out.println(results[0] + ", " + results[1] + ", " + results[2] + ", " + results[3] + ".");
		 if(GameBoard.gameState == 2){
			 if(( (King)GameBoard.Bk.getCurrentPiece()).isChecked) {
				 GameBoard.Bk.getCurrentPiece().checkResolution();
				 GameBoard.Board[results[0]][results[1]].getCurrentPiece().move(results[2], results[3]);
			 }
			 if(( (King)GameBoard.Wk.getCurrentPiece()).isChecked) {
				 GameBoard.Wk.getCurrentPiece().checkResolution();
				 GameBoard.Board[results[0]][results[1]].getCurrentPiece().move(results[2], results[3]);
			 }
		 }
		 else{
		 GameBoard.Board[results[0]][results[1]].getCurrentPiece().getMoveLocations();
		 GameBoard.Board[results[0]][results[1]].getCurrentPiece().move(results[2], results[3]);
		 }
		 GameBoard.graphBoard.repaint();
		 // hopefully someway we can get it to repaint automatically... or else the client has to click to do something
		 temp_input = null;
	}

	}