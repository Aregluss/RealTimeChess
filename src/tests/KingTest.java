package tests;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import pieces.*;
import game.*;

public class KingTest {
	GameBoard testGameBoard;
	
	ChessPiece BK,WK;
	
	public KingTest() {
		testGameBoard = new GameBoard(0);

		GameBoard.Board[0][0].setCurrentPiece(new Rook(0, 0, false));
		GameBoard.Board[0][7].setCurrentPiece(new Rook(0, 7, false));
		GameBoard.Board[7][0].setCurrentPiece(new Rook(7, 0, true));
		GameBoard.Board[7][7].setCurrentPiece(new Rook(7, 7, true));
		GameBoard.Board[0][4].setCurrentPiece(new King(0, 4, false));
		GameBoard.Board[7][4].setCurrentPiece(new King(7, 4, true));
		
		BK = GameBoard.Board[0][4].getCurrentPiece();
		WK = GameBoard.Board[7][4].getCurrentPiece();
	}
	
	@Test
	public void WKMove() {
		WK.getMoveLocations();
		for(Square movable: WK.locations) {
			System.out.println(movable);
		}
		assertEquals(5,WK.locations.size());
	}
}


