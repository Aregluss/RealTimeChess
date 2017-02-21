package tests.Rook;
//Some notes rook needs to have canCastle field updated on constructor
import static org.junit.Assert.assertEquals;
import org.junit.Test;

import pieces.*;
import game.*;

public class RookTest {
	GameBoard testGameBoard;
	ChessPiece LeftWhite;
	ChessPiece RightWhite;
	ChessPiece LeftBlack;
	ChessPiece RightBlack;
	
	public RookTest() {
		testGameBoard = new GameBoard(0);
		GameBoard.Board[0][0].setCurrentPiece(new Rook(0, 0, false));
		GameBoard.Board[0][7].setCurrentPiece(new Rook(0, 7, false));
		GameBoard.Board[7][0].setCurrentPiece(new Rook(7, 0, true));
		GameBoard.Board[7][7].setCurrentPiece(new Rook(7, 7, true));
		GameBoard.Board[0][4].setCurrentPiece(new King(0, 4, false));
		GameBoard.Board[7][4].setCurrentPiece(new King(7, 4, true));
		
		LeftBlack = GameBoard.Board[0][0].getCurrentPiece();
		RightBlack = GameBoard.Board[0][7].getCurrentPiece();
		LeftWhite = GameBoard.Board[7][0].getCurrentPiece();
		RightWhite = GameBoard.Board[7][7].getCurrentPiece();
	}
	
	@Test 
	public void BlackLeftRookMoveLoc() {
		LeftBlack.getMoveLocations();
		assertEquals(10,LeftBlack.locations.size());
	}
	
	@Test 
	public void BlackRightRookMoveLoc() {
		RightBlack.getMoveLocations();
		assertEquals(9,RightBlack.locations.size());
	}
	
	@Test 
	public void WhiteLeftRookMoveLoc() {
		LeftWhite.getMoveLocations();
		assertEquals(10,LeftWhite.locations.size());
	}
	
	@Test 
	public void WhiteRightRookMoveLoc() {
		RightWhite.getMoveLocations();
		assertEquals(9,RightWhite.locations.size());
	}
}
