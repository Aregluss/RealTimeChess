package network;

import java.io.*;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import pieces.*;
import java.net.Socket;
import java.net.UnknownHostException;
import game.*;

// Client has a mouse listener... needs to check if its movement is funciton by itself, client needs to know their color
public class Client implements Runnable{
	public String IP;
	public Socket socket;
	private static int port = 5555;
	public static boolean sent = false;
	public static PrintWriter output1;
	public BufferedReader input1;
	public volatile boolean truth = true;
	public static String sending;
	
	/**
	* Constructor for the client. Connects to the server, and creates an output stream and an input stream
	* Also, it switches client-side to the game.
	* Precondition: A valid IPAddress
	* Postcondition: creates a socket
	* @param an IPAddress in the form of a string
	*/
	public Client(String IPAddress) throws UnknownHostException, IOException{
		IP = IPAddress;
		socket = new Socket(IP,port);
		System.out.println("Connected!");
		output1 = new PrintWriter(socket.getOutputStream(), true);
		input1 = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		RealTimeChess.switchPanel("2");
		Thread thread = new Thread(this);
		thread.start();
	}
	
	/**
	* Part of the thread of client. All it does is it calls on receive, which receives the coordinates that the other player is sending.
	* Because it is on a thread, it will continually wait to receive information.
	* Precondition: A connection with the server.
	* 
	*/
	
	public void run(){
		try{
			while(truth){
				System.out.println("wasting");
			 receive();
		//	 send();
			// Thread.sleep(1000);
			}
		}
		catch(Exception e){
			UIManager.put("OptionPane.okButtonText", "Disconnect");
			JOptionPane.showMessageDialog(null, "Other player has disconnected");
			System.exit(0);
			try{
			socket.close();
			input1.close();
			output1.close();
			}
			catch(IOException kappa){
				System.out.println("SHOULD NOT GET HERE EVER....");
			}
	    	 
		}
		System.out.println("client is done.");
		}
	
	/**
	* Send function sends the move to the other player. Sends it through and output stream. Gets called right before the piece is moved on own board.
	* 
	* Precondition: A connection with the server, two (x,y) coordinates to send.
	* Postcondition: Sends coordinates to other player.
	*/
	
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
	/**
	* sendPromotion sends what piece you are promoting to the other player. Sends it through and output stream. Gets called right before the pawn is promoted on your own board.
	* 
	* Precondition: A connection with the server, a string to send.
	* Postcondition: Sends promotion string to other player.
	* @param a promotion piece in the form of a string. ex. Queen,Rook,Bishop
	*/
	
	public static void sendPromotion(String promotion){
		  sending = promotion;
		  output1.println(sending);
	  }
	
	/**
	* sendgameState sends the condition of the game to the other player. Sends it through and output stream. Gets called when the gamestate is changing, so when the game is in check or ending or stalemating.
	* 
	* Precondition: A connection with the server, a string to send.
	* Postcondition: Sends gamestate string to other player.
	* @param a game state in the form of a string. ex. end(game end),draw(stalemate)
	*/
	public static void sendgameState(String gamestate) {
		  sending = gamestate;
		  output1.println(sending);
	  }
	  
	
	/**
	* Receives the data that other players send through the input stream. Depending on the output, it will do certain things.
	* It can receive the game state, or promotion pieces. It can also receive the coordinates. Once it receives the coordinates it parses the string into and array of integers.
	* It then uses the array of integers to move the piece on its own gameboard.
	* Precondition: An output from the other player.
	* Postcondition: Moves piece on own game board.
	*/
	  public void receive() throws IOException{

			 String temp_input;
				temp_input = input1.readLine();
				System.out.println(temp_input);
				System.out.println("Beg server receive");	
				
			if(GameBoard.gameState == 3|| temp_input.equals("end")) {
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
			 
			 if(GameBoard.gameState == 4 || temp_input.equals("draw")) {
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
				 GraphicsBoard.y3 = results[0];
				 GraphicsBoard.x3 = results[1];
				 GraphicsBoard.y4 = results[2];
				 GraphicsBoard.x4 = results[3];
				 if(GameBoard.Board[results[0]][results[1]].getCurrentPiece().getClass() == test.getClass() && results[2] == 0){
					 GraphicsBoard.y3 = results[0];
					 GraphicsBoard.x3 = results[1];
					 GraphicsBoard.y4 = results[2];
					 GraphicsBoard.x4 = results[3];
					 ((Pawn)GameBoard.Board[results[0]][results[1]].getCurrentPiece()).move(results[2], results[3], true);
				 }
				 else{
					 GameBoard.Board[results[0]][results[1]].getCurrentPiece().getMoveLocations();
					 GameBoard.Board[results[0]][results[1]].getCurrentPiece().move(results[2], results[3]);
				 } 
			 }
			 if((results[2] == GraphicsBoard.y1) && (results[3] == GraphicsBoard.x1) && GameBoard.Board[results[2]][results[3]] != null ){
				 if( (GameBoard.Board[results[2]][results[3]].getCurrentPiece()) instanceof Pawn ) {
					 if( (GameBoard.Board[results[2]][results[3]].getCurrentPiece().row == 0 && GameBoard.Board[results[2]][results[3]].getCurrentPiece().getColor()) ||
							 (GameBoard.Board[results[2]][results[3]].getCurrentPiece().row == 7 && !GameBoard.Board[results[2]][results[3]].getCurrentPiece().getColor())){
						 //Do absolutely nothing LOL
					 }
						 
				 }
				 else {
					 GameBoard.clearHighlights();
				 }
				 
			 }
			 }
			 System.out.println("end receive client");
			 GameBoard.graphBoard.repaint();
			 
			 
			 
			 // hopefully someway we can get it to repaint automatically... or else the client has to click to do something
			 temp_input = null;
		}

		}