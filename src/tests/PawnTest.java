package tests;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import pieces.*;
import game.*;

public class PawnTest{
	GameBoard testGameBoard;
	ChessPiece WhitePawnCorner, MovedWhitePawn;
	ChessPiece BlackPawnCorner, MovedBlackPawn;
		
	ChessPiece BlackRightBoundary, WhiteRightBoundary;
	ChessPiece BlackStuck, WhiteStuck;
	
	public PawnTest() {
		
		testGameBoard = new GameBoard(0);
		 GameBoard.Board[7][0].setCurrentPiece(new Pawn(7, 0, true));
		 GameBoard.Board[0][0].setCurrentPiece(new Pawn(0, 0, false));
		 
		 GameBoard.Board[6][1].setCurrentPiece(new Pawn(6, 1, true));
		 GameBoard.Board[1][1].setCurrentPiece(new Pawn(1, 1, false));
		 
		 GameBoard.Board[5][5].setCurrentPiece(new Pawn(5, 5, false));
		 GameBoard.Board[2][5].setCurrentPiece(new Pawn(2, 5, true));
		 
		 GameBoard.Board[0][6].setCurrentPiece(new Pawn(0, 6, false));
		 GameBoard.Board[1][6].setCurrentPiece(new Pawn(1, 6, false));
		 GameBoard.Board[6][6].setCurrentPiece(new Pawn(6, 6, true));
		 GameBoard.Board[7][6].setCurrentPiece(new Pawn(7, 6, true));
		 
		 GameBoard.Board[2][7].setCurrentPiece(new Pawn(2, 7, true));
		 GameBoard.Board[5][7].setCurrentPiece(new Pawn(5, 7, false));
		 
		 
		 WhitePawnCorner = GameBoard.Board[7][0].getCurrentPiece();
		 BlackPawnCorner = GameBoard.Board[0][0].getCurrentPiece();
		 
		 MovedBlackPawn = GameBoard.Board[1][6].getCurrentPiece();
		 MovedWhitePawn = GameBoard.Board[6][6].getCurrentPiece();
		 
		 WhiteStuck = GameBoard.Board[7][6].getCurrentPiece();
		 BlackStuck = GameBoard.Board[0][6].getCurrentPiece();
		 
		 WhiteRightBoundary = GameBoard.Board[2][7].getCurrentPiece();
		 BlackRightBoundary = GameBoard.Board[5][7].getCurrentPiece();
	}
	
	@Test
	public void WhitePawnCornerBoundary() {
		WhitePawnCorner.getMoveLocations();
		assertEquals(2,WhitePawnCorner.locations.size());
	}
	
	@Test
	public void BlackPawnCornerBoundary() {
		BlackPawnCorner.getMoveLocations();
		assertEquals(2,BlackPawnCorner.locations.size());
	}
	
	@Test
	public void WhitePawnStuck() {
		WhiteStuck.getMoveLocations();
		assertEquals(0,WhiteStuck.locations.size());
	}
	
	@Test
	public void BlackPawnStuck() {
		BlackStuck.getMoveLocations();
		assertEquals(0,BlackStuck.locations.size());
	}
	
	@Test
	public void HasMovedWhitePawn() {
		Pawn wtff = (Pawn) MovedWhitePawn;
		wtff.sethasMoved(true);
		MovedWhitePawn.getMoveLocations();
		for(Square movable: MovedWhitePawn.locations) {
			System.out.println(movable);
		}
		assertEquals(3,MovedWhitePawn.locations.size());
	}
	
	@Test
	public void HasMovedBlackPawn() {
		Pawn wtf = (Pawn) MovedBlackPawn;
		wtf.sethasMoved(true);
		MovedBlackPawn.getMoveLocations();
		assertEquals(3,MovedBlackPawn.locations.size());
	}
	
	@Test
	public void RightBoundaryWhite() {
		WhiteRightBoundary.getMoveLocations();
		assertEquals(3,WhiteRightBoundary.locations.size());
	}
	
	@Test
	public void RightBoundaryBlack() {
		BlackRightBoundary.getMoveLocations();
		assertEquals(3,BlackRightBoundary.locations.size());
	}
}

