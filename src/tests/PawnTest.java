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
		Pawn wtf = (Pawn) WhitePawnCorner;
		wtf.getMoveLocations();
		assertEquals(2,wtf.locations.size());
	}
	
	@Test
	public void BlackPawnCornerBoundary() {
		Pawn wtf = (Pawn) BlackPawnCorner;
		wtf.getMoveLocations();
		assertEquals(2,wtf.locations.size());
	}
	
	@Test
	public void WhitePawnStuck() {
		Pawn wtf = (Pawn) WhiteStuck;
		wtf.getMoveLocations();
		assertEquals(0,wtf.locations.size());
	}
	
	@Test
	public void BlackPawnStuck() {
		Pawn wtf = (Pawn) BlackStuck;
		wtf.getMoveLocations();
		assertEquals(0,wtf.locations.size());
	}
	
	@Test
	public void HasMovedWhitePawn() {
		Pawn wtf = (Pawn) MovedWhitePawn;
		MovedWhitePawn.hasMoved = true;
		wtf.getMoveLocations();
		assertEquals(3,wtf.locations.size());
	}
	
	@Test
	public void HasMovedBlackPawn() {
		Pawn wtf = (Pawn) MovedBlackPawn;
		wtf.hasMoved = true;
		wtf.getMoveLocations();
		assertEquals(3,wtf.locations.size());
	}
	
	@Test
	public void RightBoundaryWhite() {
		Pawn wtf = (Pawn) WhiteRightBoundary;
		wtf.getMoveLocations();
		assertEquals(3,wtf.locations.size());
	}
	
	@Test
	public void RightBoundaryBlack() {
		Pawn wtf = (Pawn) BlackRightBoundary;
		wtf.getMoveLocations();
		assertEquals(3,wtf.locations.size());
	}
}
