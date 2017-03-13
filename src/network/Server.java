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

import javax.swing.JOptionPane;

import game.*;
import pieces.*;

//server needs to host a gameboard, retreive information from clients and modify gameboard
public class Server implements Runnable{
	public static int port = 5555;
	private static ServerSocket ss;
	static final int SO_TIMEOUT = 5000;
	private static Socket p1,p2;
	private boolean isRunning = false;
	private static GameBoard ChessGame;
	public static boolean sent = false;
	public volatile boolean truth = true;
	public static PrintWriter output1;
	public BufferedReader input1;
	int i =0;
	public static String sending;
	public static boolean connected = false;;
	public Server() throws IOException
	  {
	      runServer();
	      if(connected){
	    	  System.out.println("GOT THROUGH CONNECTED");
	       input1 = new BufferedReader(new InputStreamReader (p1.getInputStream()));
	       output1 = new PrintWriter(p1.getOutputStream(), true);  
	      Thread thread = new Thread(this);
	      thread.start();
	      }
	    }
	 
	private static void runServer(){
	    try
	    {
	      ss = new ServerSocket(port);
	      ss.setSoTimeout(5000);
	      System.out.println("listening port: " + port);
	      p1 = ss.accept();
	       RealTimeChess.switchPanel("2");
	      System.out.println("running");
	      connected = true;
	    }
	    catch (IOException ex)
	    {
	    	System.out.println("RUN SERVER FAILED");
	      //ex.printStackTrace();
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
	    	 JOptionPane.showMessageDialog(null, "Other player has disconnected");
	    	 RealTimeChess.switchPanel("1");
	    	 try{
	    	 ss.close();
	    	 input1.close();
	    	 output1.close();
	    	 }
	    	 catch( IOException e){
	    		 System.out.println("IF THIS HAPPENED... I HAVE NO IDEA...");
	    	 }
	    	 
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
		    	if(GameBoard.gameState == 3 && GameBoard.getWinner() == true) {
					JOptionPane.showMessageDialog(null, "You've Won!", "Victory", JOptionPane.INFORMATION_MESSAGE);
					RealTimeChess.switchPanel("1");
				}
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
			 if( ((King)GameBoard.Board[GameBoard.Bk.getRow()][GameBoard.Bk.getColumn()].getCurrentPiece()).isChecked) {
				 ((King)GameBoard.Board[GameBoard.Bk.getRow()][GameBoard.Bk.getColumn()].getCurrentPiece()).checkSquare(GameBoard.Bk.getRow(), GameBoard.Bk.getColumn());
				 ((King)GameBoard.Board[GameBoard.Bk.getRow()][GameBoard.Bk.getColumn()].getCurrentPiece()).checkResolution();
				 GameBoard.Board[results[0]][results[1]].getCurrentPiece().move(results[2], results[3]);
			 }
			 if(( (King)GameBoard.Board[GameBoard.Wk.getRow()][GameBoard.Wk.getColumn()].getCurrentPiece()).isChecked) {
				 ((King)GameBoard.Board[GameBoard.Wk.getRow()][GameBoard.Wk.getColumn()].getCurrentPiece()).checkSquare(GameBoard.Wk.getRow(), GameBoard.Wk.getColumn());
				 ((King)GameBoard.Board[GameBoard.Wk.getRow()][GameBoard.Wk.getColumn()].getCurrentPiece()).checkResolution();
				 GameBoard.Board[results[0]][results[1]].getCurrentPiece().move(results[2], results[3]);
			 }
		 }
		 else{
		 GameBoard.Board[results[0]][results[1]].getCurrentPiece().getMoveLocations();
		 GameBoard.Board[results[0]][results[1]].getCurrentPiece().move(results[2], results[3]);
		 }
		 
		
		 
		 GameBoard.graphBoard.repaint();
		 
		 if(GameBoard.gameState == 3 && GameBoard.getWinner() == false) {
				JOptionPane.showMessageDialog(null, "You lost!", "DEFEAT", JOptionPane.INFORMATION_MESSAGE);
				RealTimeChess.switchPanel("1");
		 }
		 
		 // hopefully someway we can get it to repaint automatically... or else the client has to click to do something
		 temp_input = null;
	}

	}