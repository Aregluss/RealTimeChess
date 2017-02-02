package pieces;

import java.util.ArrayList;

import game.GameBoard;
import pieces.Square;


public class Pawn extends ChessPiece{

	boolean hasMoved;
	
	
	public Pawn()
	{
		hasMoved = false;
	}


	@Override
	public void move(int row, int column) {
		// TODO Auto-generated method stub
		super.move(row, column);
	}


	@Override
	public void attack(ChessPiece Enemy) {
		// TODO Auto-generated method stub
		super.attack(Enemy);
	}


	@Override
	public void die() {
		// TODO Auto-generated method stub
		super.die();
	}


	@Override
	public void getMoveLocations() {
		if( GameBoard.Board.getCurrentPiece(row + 1,column) != null ) {
			locations.add(new Square(row + 1,column,false,false));	
		}
		
		if(GameBoard.Board.getCurrentPiece(row + 2,column) != null && hasMoved != true) {
			locations.add(new Square(row + 2,column,false,false));	
		}
		
		if(GameBoard.Board.getCurrentPiece(row + 1,column + 1).getColor() != getColor()) {
			locations.add(new Square(row + 1,column + 1,false,false));	
		}
		
		if(GameBoard.Board.getCurrentPiece(row + 1,column - 1).getColor() != getColor()) {
			locations.add(new Square(row + 1,column - 1,false,false));	
		}
		
	}


	@Override
	public void highightLocation() {
		// TODO Auto-generated method stub
		super.highightLocation();
	}


	@Override
	public Square sendAttackSpot() {
		// TODO Auto-generated method stub
		return super.sendAttackSpot();
	}


	@Override
	public boolean getColor() {
		// TODO Auto-generated method stub
		return super.getColor();
	}
	
	public void promote() {
		
	}

	
}
