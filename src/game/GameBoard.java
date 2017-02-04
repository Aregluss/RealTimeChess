package game;

import game.Player;
import pieces.Pawn;
import pieces.Rook;
import pieces.Bishop;
import pieces.King;
import pieces.Queen;
import pieces.Square;

public final class GameBoard 
{
	public static Square[][] Board;
	
	private GameBoard()
	{
		Board = new Square[8][8];
	}
	
	public static Square[][] getBoard(){
		return Board;
	}
}
