package network;

import java.io.IOException;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import game.*;
import pieces.*;


//server needs to host a gameboard, retreive information from clients and modify gameboard
public class Server implements Runnable{
	public static int port = 5555;
	private static ServerSocket ss;
	static final int SO_TIMEOUT = 10000;
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
	 
	private static void runServer() throws IOException{
	    try
	    {
	    	System.out.println("FIRST LINE");
	      ss = new ServerSocket(port);
	      System.out.println("SECOND LINE");
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
	    	ss.close();
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
	    	 UIManager.put("OptionPane.okButtonText", "Disconnect");
	    	 JOptionPane.showMessageDialog(null, "Other player has disconnected");
	    	 System.exit(0);
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
	  }
	  
	  public static void sendPromotion(String promotion){
		  sending = promotion;
		  output1.println(sending);
	  }
	  
	  public static void sendgameState(String gamestate) {
		  sending = gamestate;
		  output1.println(sending);
	  }
	  
	  public void receive() throws IOException{

			String temp_input;
			temp_input = input1.readLine();
			System.out.println(temp_input);
				
				
			
				
			if(GameBoard.gameState == 3|| temp_input == "end") {
				sendgameState("end");
				UIManager.put("OptionPane.okButtonText", "Exit");
				if (GameBoard.getWinner() == false ) {
					JOptionPane.showMessageDialog(null, "You won!", "VICTORY", JOptionPane.INFORMATION_MESSAGE);
					GameBoard.clearHighlights();
							 
				}
				else {
					JOptionPane.showMessageDialog(null, "You lost!", "DEFEAT", JOptionPane.INFORMATION_MESSAGE);
					GameBoard.clearHighlights();
				}
					System.exit(0);
			}
				 
			if(GameBoard.gameState == 4 || temp_input == "draw") {
				System.out.println("SENDIasdfsdfNG ");

				sendgameState("draw");
				System.out.println("SENDIasasdfsdaffafdfsdfNG ");

				UIManager.put("OptionPane.okButtonText", "Exit");
				JOptionPane.showMessageDialog(null, "It's a draw!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
				GameBoard.clearHighlights();	 	
				System.exit(0);
			}
				 
			 if((temp_input.equals("Queen")) ||(temp_input.equals("Rook"))  || (temp_input.equals("Knight"))  || (temp_input.equals("Bishop")) ){
				 System.out.println("Inside the pawn function of network promotion");
				 System.out.println(GraphicsBoard.y3 + " " + GraphicsBoard.x3 + " " + GraphicsBoard.y4 + " " + GraphicsBoard.x4);
				 /*GameBoard.Board[GraphicsBoard.y3][GraphicsBoard.x3].getCurrentPiece().getMoveLocations();
				 ((Pawn)GameBoard.Board[GraphicsBoard.y3][GraphicsBoard.x3].getCurrentPiece()).move(GraphicsBoard.y4, GraphicsBoard.x4,temp_input);*/
				 
				 ((Pawn)GameBoard.Board[GraphicsBoard.y4][GraphicsBoard.x4].getCurrentPiece()).promote(temp_input);
			 }
			 else{
				 System.out.println("Inside the normal move function");
			 String[] items = temp_input.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\\s", "").split(",");
			 int[] results = new int[7];
			 System.out.println("b");
			 for (int i = 0; i < items.length; i++) {
			     try {
			         results[i] = Integer.parseInt(items[i]);
			     } catch (NumberFormatException nfe) {
			         //NOTE: write something here if you need to recover from formatting errors
			     };
			 }
			 Pawn test = new Pawn(0,0,true);
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
				 if(GameBoard.Board[results[0]][results[1]].getCurrentPiece().getClass() == test.getClass() && results[2] == 7){
					 GraphicsBoard.y3 = results[0];
					 GraphicsBoard.x3 = results[1];
					 GraphicsBoard.y4 = results[2];
					 GraphicsBoard.x4 = results[3];
					 GameBoard.Board[results[0]][results[1]].getCurrentPiece().getMoveLocations();
					 ((Pawn)GameBoard.Board[results[0]][results[1]].getCurrentPiece()).move(results[2], results[3], true);
				 }
				 else{
					 GameBoard.Board[results[0]][results[1]].getCurrentPiece().getMoveLocations();
					 GameBoard.Board[results[0]][results[1]].getCurrentPiece().move(results[2], results[3]);
				 } 
			 }
			 if((results[2] == GraphicsBoard.y1) && (results[3] == GraphicsBoard.x1)){
				 GameBoard.clearHighlights();
			 }
			 }
			 System.out.println("end receive");
			 GameBoard.graphBoard.repaint();
			 
			 // hopefully someway we can get it to repaint automatically... or else the client has to click to do something
			 temp_input = null;
		}

		}