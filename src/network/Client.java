package network;
import java.io.*;
import java.util.*;
import pieces.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import game.*;

// Client has a mouse listener... needs to check if its movement is funciton by itself, client needs to know their color
public class Client implements Runnable{
	String IP;
	Socket socket;
	private static int port = 5555;
	public static boolean sent = false;
	public ObjectOutputStream output1;
	public ObjectInputStream input1;
	
	public Client() throws UnknownHostException, IOException{
		System.out.println("Enter IP Address: ");
		Scanner input = new Scanner(System.in);
		IP = input.next();
		socket = new Socket(IP,port);
		System.out.println("Connected!");
		input1 = new ObjectInputStream(socket.getInputStream());
		output1 = new ObjectOutputStream(socket.getOutputStream());
		Thread thread = new Thread(this);
		thread.start();
	}
	public void run(){
		int i =0;
		try{
			while(true){
			 System.out.println("IN CLIENT. ABOUT TO MAKE REALTIMECHESS.");
			 System.out.println("b");
			 GameBoard.Board = (Square[][])input1.readObject();
			 System.out.println(GameBoard.Board[4][4].getCurrentPiece());
			 System.out.println("c");
			 
			 System.out.println("d");
			
			}
			// Implement mouse listener... client sends its mouseclicks to the server. probably store and send it in integers?
			// server will handle movement.
			
			// Another method?? don't know... but client will have to pull object from the server after either every move, or every few seconds because of how the cooldown works.
		}
		catch(Exception e){
			System.out.println("Client messed up");
			System.out.println(e);
		}
		System.out.println("client is done.");
		}

	}