package network;

import java.awt.Graphics;
import java.awt.event.InputEvent;
import java.io.*;
import java.util.*;

import javax.swing.JOptionPane;

import pieces.*;
import java.net.ServerSocket;
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
			System.out.println("Client messed up");
			System.out.println(e);
		}
		System.out.println("client is done.");
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
		 
		 if(GameBoard.gameState == 3) {
				JOptionPane.showMessageDialog(null, "You've Won!", "Victory", JOptionPane.INFORMATION_MESSAGE);
				System.exit(0);
			}
		 
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
		 // hopefully someway we can get it to repaint automatically... or else the client has to click to do something
		 temp_input = null;
	}

	}