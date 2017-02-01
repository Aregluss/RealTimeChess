package game;

import game.Player;
import pieces.Pawn;
import pieces.Rook;
import pieces.Bishop;
import pieces.King;
import pieces.Queen;
import pieces.Square;

public class GameBoard 
{
	public Square[][] board;
	
	public GameBoard()
	{
		board = new Square[8][8];
	}
	
	
	public Square[][] getBoard(){
		return board;
	}
}
