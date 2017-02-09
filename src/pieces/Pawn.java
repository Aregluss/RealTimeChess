package pieces;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import game.GameBoard;
import pieces.Square;


public class Pawn extends ChessPiece{

	public boolean hasMoved;
	
	
	public Pawn(int row, int column, boolean color){
		super(row, column, color);
		hasMoved = false;
		
		try {
			image = ImageIO.read(new File("Pawn.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		//Haven't considered pawn reaching end of board (promotion)
		if(this.getColor() == true) {
			if( GameBoard.Board[row-1][column].getCurrentPiece() == null ) {
				locations.add(new Square(row - 1,column));	
			}
			System.out.println(GameBoard.Board[row-1][column].getCurrentPiece());
			if((GameBoard.Board[row-1][column].getCurrentPiece() == null && GameBoard.Board[row-2][column].getCurrentPiece() == null) && hasMoved != true) {
				locations.add(new Square(row - 2,column));	
			}
			
			if(column-1 >= 0 && GameBoard.Board[row-1][column-1].getCurrentPiece() != null)  
				if(GameBoard.Board[row-1][column-1].getCurrentPiece().getColor() != getColor()) {
					locations.add(new Square(row - 1,column - 1));	
			}
		
			if(column+1 < 8 && GameBoard.Board[row-1][column+1].getCurrentPiece() != null)  
				if(GameBoard.Board[row-1][column+1].getCurrentPiece().getColor() != getColor()) {
					locations.add(new Square(row - 1,column + 1));	
			}
		}
		
		if(this.getColor() == false) {
			
			if( GameBoard.Board[row+1][column].getCurrentPiece() == null ) {
				locations.add(new Square(row + 1,column));	
			}
			
			if(GameBoard.Board[row+1][column].getCurrentPiece() == null && GameBoard.Board[row+2][column].getCurrentPiece() == null && hasMoved != true) {
				locations.add(new Square(row + 2,column));	
			}
			
			if(column-1 >= 0 && GameBoard.Board[row+1][column-1].getCurrentPiece() != null)  
				if(GameBoard.Board[row+1][column-1].getCurrentPiece().getColor() != getColor()) {
					locations.add(new Square(row + 1,column - 1));	
			}
		
			if(column+1 < 8 && GameBoard.Board[row+1][column+1].getCurrentPiece() != null)  
				if(GameBoard.Board[row+1][column+1].getCurrentPiece().getColor() != getColor()) {
					locations.add(new Square(row + 1,column + 1));	
			}
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
