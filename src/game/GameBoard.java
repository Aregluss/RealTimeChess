package game;


import game.Player;
import pieces.Pawn;
import pieces.Rook;
import pieces.Bishop;
import pieces.ChessPiece;
import pieces.King;
import pieces.Knight;
import pieces.Queen;
import pieces.Square;

import java.awt.Graphics;
import java.awt.Graphics2D;
import game.GraphicsBoard;

public class GameBoard implements Runnable
{
	public static Square[][] Board;
	public static Player Player1;
	public static Player Player2;
	public static GraphicsBoard graphBoard;
	public static Square Bk = new Square(0,4); // Holds information on black king, notably location
	public static Square Wk = new Square(7,4); // Holds information on white king, notably location
	public static int gameState = 0;// 0 if normal, 1 if check is being resolved, 2 if a king is in check, 3 if the game is over, 4 if the game is in a draw
	private static Square lastSelected; // holds the last clicked piece
	private static boolean winner; // true if white wins, false if black wins
	private static int checked; //1 if white is checked, 0 if black is checked, 2 if none are checked
	public volatile boolean truth = true;
	
	/** Constructor which sets up pieces, board, graphicsBoard, and Players
	 * 
	 * @param x Specifies game setup, 1 being normal chess setup
	 * @param gb A graphics board used for drawing
	 */
	public GameBoard(int x, GraphicsBoard gb)
	{	
		graphBoard = gb;
		
		Board = new Square[8][8];
		
		for(int i = 0; i < Board.length; i++)
			for(int j = 0; j < Board[i].length; j++)
			{
				Board[i][j] = new Square(i,j);
			}
				
		if(x == 1)
			standardSetup();
		Player1 = new Player(true);
		Player2 = new Player(false);
		
	}	
	
	// For testing
	public GameBoard(int x) {
		
		Board = new Square[8][8];
		
		for(int i = 0; i < Board.length; i++)
			for(int j = 0; j < Board[i].length; j++)
			{
				Board[i][j] = new Square(i,j);
			}
		
		if(x == 1)
			standardSetup();
	}
	
	/**
	 * Sets up the pieces in standard positions
	 */
	public void standardSetup()
	{
					//Rooks
					Board[0][0].setCurrentPiece(new Rook(0, 0, false));
					Board[0][7].setCurrentPiece(new Rook(0, 7, false));
					Board[7][0].setCurrentPiece(new Rook(7, 0, true));
					Board[7][7].setCurrentPiece(new Rook(7, 7, true));
					//Knights
					Board[0][1].setCurrentPiece(new Knight(0, 1, false));
					Board[0][6].setCurrentPiece(new Knight(0, 6, false));
					Board[7][1].setCurrentPiece(new Knight(7, 1, true));
					Board[7][6].setCurrentPiece(new Knight(7, 6, true));
					//Bishops
					Board[0][2].setCurrentPiece(new Bishop(0, 2, false));
					Board[0][5].setCurrentPiece(new Bishop(0, 5, false));
					Board[7][2].setCurrentPiece(new Bishop(7, 2, true));
					Board[7][5].setCurrentPiece(new Bishop(7, 5, true));
					//King&Queen
					Board[0][4].setCurrentPiece(new King(0, 4, false));
					Board[0][3].setCurrentPiece(new Queen(0, 3, false));
					Board[7][3].setCurrentPiece(new Queen(7, 3, true));
					Board[7][4].setCurrentPiece(new King(7, 4, true));
					
					//Pawns
					for(int j=0; j < 8; j++)
					{
						Board[1][j].setCurrentPiece(new Pawn(1, j, false));
						Board[6][j].setCurrentPiece(new Pawn(6, j, true));
					}
		}
	
	/**
	 * sets the last piece clicked, useful for dynamic highlighting
	 * @param row. row of piece
	 * @param col, column of piece
	 * @param chessPiece, chesspiece, selected
	 */
	public static void setlastSelected(int row, int col, ChessPiece chessPiece) {
		lastSelected = new Square(row,col,chessPiece);
	}
	
	/**
	 * clear the last piece clicked
	 */
	public static void clearlastSelected(){
		lastSelected = null;
	}
	
	/**
	 * 
	 * @return the last selected piece
	 */
	public static Square getlastSelected() {
		return lastSelected;
	}
	
	/**
	 * sets the game's winner
	 * @param win, true if the white team won, false if the black team won
	 */
	public static void setWinner(boolean win) {
		winner = win;
	}
	
	/**
	 * 
	 * @return winner of the game
	 */
	public static boolean getWinner() {
		return winner;
	}
	/**
	 * 
	 * @param check, which king isin check, 0 for black, 1 for white, 2 if neither
	 */
	public static void setChecked(int check) {
		checked = check;
	}
	
	/**
	 * 
	 * @return which king is in check or if neither are checked
	 */
	public static int getChecked() {
		return checked;
	}

	/**
	 * clears highlights 
	 */
	public static void clearHighlights(){
		for(int i =0; i<8; i++){
			for(int j=0; j<8; j++){
				Board[i][j].setSquare(7);
			}
		}
	}
	@Override
	/**Thread which handles drawing pieces in right places
	 * 
	 */
	public void run() 
	{
		while(truth)
		{
			Graphics g = graphBoard.getGraphics();
			Graphics2D g2 = (Graphics2D) g;
			if(g2 != null)
			{
				for(int i = 0; i < Board.length; i++)
					for(int j = 0; j < Board[i].length; j++)
					{
						if(Board[i][j].getCurrentPiece() != null)
						{
							Board[i][j].getCurrentPiece().draw(g2);
						}
						
						if(Board[i][j].getSquare() != null)
						{
							Board[i][j].draw(g2);
						}
						
					}
			}
			
			
			
			
		}
	}
	
	
}
