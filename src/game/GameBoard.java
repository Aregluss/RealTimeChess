package game;


import game.Player;
import pieces.Pawn;
import pieces.Rook;
import pieces.Bishop;
import pieces.King;
import pieces.Knight;
import pieces.Queen;
import pieces.Square;

import java.awt.Graphics;

import game.GraphicsBoard;

public class GameBoard implements Runnable
{
	public static Square[][] Board;
	public Player Player1, Player2;
	public GraphicsBoard graphBoard;
	public static Square Bk; // Holds information on black king, notably location
	public static Square Wk; // Holds information on white king, notably location
	
	
	

	
	
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
	}
	
	
	
	
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
					King bob = new King(7,4,true);
					bob.canCastle();
					
					//Pawns
					for(int j=0; j < 8; j++)
					{
						Board[1][j].setCurrentPiece(new Pawn(1, j, false));
						Board[6][j].setCurrentPiece(new Pawn(6, j, true));
					}
					
		}

	@Override
	public void run() 
	{
		while(true)
		{
			if(Board[0][3].getCurrentPiece() == null)
				System.out.println("No Queen");
			
			Graphics g = graphBoard.getGraphics();
			if(g != null)
			{
				for(int i = 0; i < Board.length; i++)
					for(int j = 0; j < Board[i].length; j++)
					{
						if(Board[i][j].getCurrentPiece() != null)
						{
							Board[i][j].getCurrentPiece().draw(g);
							
						}
						
					}
					
				
			}
			
		}
	}
	
	
}
