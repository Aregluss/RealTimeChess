package tests;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import pieces.*;
import game.*;

public class KnightTest {
	GameBoard testGameBoard;
	
	ChessPiece BKLeftCorner, BKRightCorner;
	ChessPiece WKLeftCorner, WKRightCorner;
	ChessPiece WK42,WK54;
	ChessPiece BK23,BK35;
	
	public KnightTest(){
		testGameBoard = new GameBoard(0);
		
		GameBoard.Board[0][0].setCurrentPiece(new Knight(0, 0, false));
		GameBoard.Board[0][7].setCurrentPiece(new Knight(0, 7, false));
		GameBoard.Board[7][0].setCurrentPiece(new Knight(7, 0, true));
		GameBoard.Board[7][7].setCurrentPiece(new Knight(7, 7, true));
		
		GameBoard.Board[4][2].setCurrentPiece(new Knight(4, 2, true));
		GameBoard.Board[5][4].setCurrentPiece(new Knight(5, 4, true));
		GameBoard.Board[2][3].setCurrentPiece(new Knight(2, 3, false));
		GameBoard.Board[3][5].setCurrentPiece(new Knight(3, 5, false));

		BKLeftCorner = GameBoard.Board[0][0].getCurrentPiece();
		BKRightCorner = GameBoard.Board[0][7].getCurrentPiece();
		WKLeftCorner = GameBoard.Board[7][0].getCurrentPiece();
		WKRightCorner = GameBoard.Board[7][7].getCurrentPiece();
		
		WK42 = GameBoard.Board[4][2].getCurrentPiece();
		WK54 = GameBoard.Board[5][4].getCurrentPiece();
		BK23 = GameBoard.Board[2][3].getCurrentPiece();
		BK35 = GameBoard.Board[3][5].getCurrentPiece();
	}
	
	@Test
	public void BKLeftCorner() {
		BKLeftCorner.getMoveLocations();
		assertEquals(2,BKLeftCorner.locations.size());
	}
	
	@Test
	public void WKLeftCorner() {
		WKLeftCorner.getMoveLocations();
		assertEquals(2,WKLeftCorner.locations.size());
	}
	
	@Test
	public void BKRightCorner() {
		BKRightCorner.getMoveLocations();
		assertEquals(2,BKRightCorner.locations.size());
	}
	
	@Test
	public void WKRightCorner() {
		WKRightCorner.getMoveLocations();
		assertEquals(2,WKRightCorner.locations.size());
	}

	
	@Test
	public void WK42() {
		WK42.getMoveLocations();
		for(Square movable: WK42.locations) {
			System.out.println(movable);
		}
		assertEquals(7,WK42.locations.size());
	}
	
	@Test
	public void WK54() {
		WK54.getMoveLocations();
		assertEquals(7,WK54.locations.size());
	}
	
	@Test
	public void BK23() {
		BK23.getMoveLocations();
		assertEquals(7,BK23.locations.size());
	}
	
	@Test
	public void BK35() {
		BK35.getMoveLocations();
		assertEquals(7,BK35.locations.size());
	}

}
