package tests;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import pieces.*;
import game.*;

public class PawnPromotionTest {

	ChessPiece BlackPawn,WhitePawn;
	GameBoard testgameboard;
	
	public PawnPromotionTest() {
		testgameboard = new GameBoard(0);
		
		GameBoard.Board[1][0].setCurrentPiece(new Pawn(1, 0, true));
		GameBoard.Board[6][0].setCurrentPiece(new Pawn(6, 0, false));
		
		BlackPawn = GameBoard.Board[6][0].getCurrentPiece();
		WhitePawn = GameBoard.Board[1][0].getCurrentPiece();
	}
	
	@Test
	public void BlackPromotion() {
		BlackPawn.hasMoved = true;
		BlackPawn.move(7,0);
		Queen hello = (Queen) GameBoard.Board[7][0].getCurrentPiece();
		assertEquals("queen", hello.check);
	}
}
