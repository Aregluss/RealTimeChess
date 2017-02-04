package pieces;

import java.util.ArrayList;

import game.GameBoard;

public class Knight extends ChessPiece{

	public Knight(int row, int column, boolean color){
		super(row, column, color);
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
		if( (row +2 < 0 || row +2 > 8 ) && ( column +1 < 0 || column +1 > 8 ) && 
				(!(GameBoard.Board[row+2][column+1].getCurrentPiece().getColor() == color) || GameBoard.Board[row+2][column+1].getCurrentPiece() != null) ) {
			locations.add(new Square(row+2,column+1,false,false));
		}
		
		if( (row +2< 0 || row+2 > 8 ) && ( column -1 < 0 || column -1 > 8 ) && 
				(!(GameBoard.Board[row+2][column-1].getCurrentPiece().getColor() == color) || GameBoard.Board[row+2][column-1].getCurrentPiece() != null) ) {
			locations.add(new Square(row+2,column-1,false,false));
		}

		if( (row +1 < 0 || row+1 > 8 ) && ( column +2< 0 || column+2 > 8 ) && 
				(!(GameBoard.Board[row+1][column+2].getCurrentPiece().getColor() == color) || GameBoard.Board[row+1][column+2].getCurrentPiece() != null) ) {
			locations.add(new Square(row+1,column+2,false,false));
		}

		if( (row +1 < 0 || row +1 > 8 ) && ( column -2 < 0 || column - 2> 8 ) && 
				(!(GameBoard.Board[row+1][column-2].getCurrentPiece().getColor() == color) || GameBoard.Board[row+1][column-2].getCurrentPiece() != null) ) {
			locations.add(new Square(row+1,column-2,false,false));
		}

		if( (row -1 < 0 || row-1 > 8 ) && ( column +2< 0 || column+2 > 8 ) && 
				(!(GameBoard.Board[row-1][column+2].getCurrentPiece().getColor() == color) || GameBoard.Board[row-1][column+2].getCurrentPiece() != null) ) {
			locations.add(new Square(row-1,column+2,false,false));
		}

		if( (row -1 < 0 || row -1 > 8 ) && ( column -2 < 0 || column - 2> 8 ) && 
				(!(GameBoard.Board[row-1][column-2].getCurrentPiece().getColor() == color) || GameBoard.Board[row-1][column-2].getCurrentPiece() != null) ) {
			locations.add(new Square(row-1,column-2,false,false));
		}

		if( (row -2 < 0 || row -2 > 8 ) && ( column +1 < 0 || column +1 > 8 ) && 
				(!(GameBoard.Board[row-2][column+1].getCurrentPiece().getColor() == color) || GameBoard.Board[row-2][column+1].getCurrentPiece() != null) ) {
			locations.add(new Square(row-2,column+1,false,false));
		}
		
		if( (row -2< 0 || row-2 > 8 ) && ( column -1 < 0 || column -1 > 8 ) && 
				(!(GameBoard.Board[row-2][column-1].getCurrentPiece().getColor() == color) || GameBoard.Board[row-2][column-1].getCurrentPiece() != null) ) {
			locations.add(new Square(row-2,column-1,false,false));
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


}
