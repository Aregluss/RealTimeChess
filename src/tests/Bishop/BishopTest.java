package tests.Bishop;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import pieces.*;
import game.*;

public class BishopTest {
	GameBoard testGameBoard;
	ChessPiece LeftWhite, RightWhite;
	ChessPiece LeftBlack, RightBlack;
	ChessPiece WhiteBish, BlackBish;
	
	ChessPiece WhitePawn;
	
	public BishopTest() {
		testGameBoard = new GameBoard(0);
		GameBoard.Board[0][0].setCurrentPiece(new Bishop(0, 0, false));
		GameBoard.Board[0][7].setCurrentPiece(new Bishop(0, 7, false));
		GameBoard.Board[7][0].setCurrentPiece(new Bishop(7, 0, true));
		GameBoard.Board[7][7].setCurrentPiece(new Bishop(7, 7, true));
		
		GameBoard.Board[0][2].setCurrentPiece(new Bishop(0, 2, false));
		GameBoard.Board[7][2].setCurrentPiece(new Bishop(7, 2, true));
		
		GameBoard.Board[4][5].setCurrentPiece(new Pawn(4, 5, true));
				
		LeftBlack = GameBoard.Board[0][0].getCurrentPiece();
		RightBlack = GameBoard.Board[0][7].getCurrentPiece();
		LeftWhite = GameBoard.Board[7][0].getCurrentPiece();
		RightWhite = GameBoard.Board[7][7].getCurrentPiece();
		WhiteBish = GameBoard.Board[7][2].getCurrentPiece();
		BlackBish = GameBoard.Board[0][2].getCurrentPiece();
	}
	
	@Test 
	public void BlackLeftBishopMoveLoc() {
		LeftBlack.getMoveLocations();
		assertEquals(7,LeftBlack.locations.size());
	}
	
	@Test 
	public void BlackRightBishopMoveLoc() {
		RightBlack.getMoveLocations();
		assertEquals(7,RightBlack.locations.size());
	}
	
	@Test 
	public void WhiteLeftBishopMoveLoc() {
		LeftWhite.getMoveLocations();
		assertEquals(7,LeftWhite.locations.size());
	}
	
	@Test 
	public void WhiteRightBishopMoveLoc() {
		RightWhite.getMoveLocations();
		assertEquals(7,RightWhite.locations.size());
	}
	
	@Test
	public void WhiteBishopAllyTest() {
		WhiteBish.getMoveLocations();
		assertEquals(4,WhiteBish.locations.size());
	}
	
	@Test
	public void BlackBishopBoundaryTest() {
		BlackBish.getMoveLocations();
		assertEquals(7,BlackBish.locations.size());
	}
}

