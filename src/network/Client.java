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
	public String IP;
	public Socket socket;
	private static int port = 5555;
	public static boolean sent = false;
	public PrintWriter output1;
	public BufferedReader input1;
	
	public Client(String IPAddress) throws UnknownHostException, IOException{
		IP = IPAddress;
		socket = new Socket(IP,port);
		System.out.println("Connected!");
		RealTimeChess.switchPanel("2");


		Thread thread = new Thread(this);
		thread.start();
	}
	public void run(){
		int i =0;
		try{
			while(true){
			 System.out.println("IN CLIENT. ABOUT TO MAKE REALTIMECHESS.");
			output1 = new PrintWriter(socket.getOutputStream(), true);
			input1 = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			 String temp_input;
			 if((temp_input = input1.readLine()) != null){
				System.out.println("Client has received: " + temp_input);
			 }
			 System.out.println(" I am now " + temp_input);
			 // parse temp_input into 4 different integers
			 System.out.println("d");
			 Thread.sleep(10000);
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