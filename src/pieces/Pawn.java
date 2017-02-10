package pieces;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import game.GameBoard;
import pieces.Square;


public class Pawn extends ChessPiece{

	private boolean hasMoved = false;
	public String check;
	
	
	public Pawn(int row, int column, boolean color){
		super(row, column, color);
		check = "pawn";
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
		
		if(color == true) {
			if(row == 0) {
				promote(choosePromotion());
			}
		}
		
		if(color == false) {
			if(row == 7) {
				promote(choosePromotion());
			}
		}
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
		System.out.println("DUMBO" + this.hasMoved);

		if(this.getColor() == true) {
			if( GameBoard.Board[row-1][column].getCurrentPiece() == null ) {
				locations.add(new Square(row - 1,column));	
			}
			if(hasMoved != true) {
			if((GameBoard.Board[row-1][column].getCurrentPiece() == null && GameBoard.Board[row-2][column].getCurrentPiece() == null)) {
				locations.add(new Square(row - 2,column));	
			}
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
			
			if (hasMoved != true) {
			if(GameBoard.Board[row+1][column].getCurrentPiece() == null && GameBoard.Board[row+2][column].getCurrentPiece() == null) {
			
				locations.add(new Square(row + 2,column));	
			}
			}
			
			if(column-1 >= 0 && GameBoard.Board[row+1][column-1].getCurrentPiece() != null) {  
				if(GameBoard.Board[row+1][column-1].getCurrentPiece().getColor() != getColor()) {
					locations.add(new Square(row + 1,column - 1));	
				}
			}
		
			if(column+1 < 8 && GameBoard.Board[row+1][column+1].getCurrentPiece() != null) {  
				if(GameBoard.Board[row+1][column+1].getCurrentPiece().getColor() != getColor()) {
					locations.add(new Square(row + 1,column + 1));	
				}
			}
		}
	}
			
	public void promote(String promotionPiece) {
		// light pops, ui to ask player what they want the pawn to be promoted to
		if(promotionPiece.equals("queen")) {
			GameBoard.Board[row][column].setCurrentPiece(new Queen(row,column,color));
		}
		if(promotionPiece.equals("bishop")) {
			GameBoard.Board[row][column].setCurrentPiece(new Bishop(row,column,color));
		}
		if(promotionPiece.equals("knight")) {
			GameBoard.Board[row][column].setCurrentPiece(new Knight(row,column,color));
		}
		if(promotionPiece.equals("rook")) {
			GameBoard.Board[row][column].setCurrentPiece(new Rook(row,column,color));
		}
	}
	
	public String choosePromotion() {
		//UI STUFF HERE
		return "queen";
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
		
	public boolean gethasMoved() {
		return hasMoved;
	}
	
	public void sethasMoved(boolean setter) {
		hasMoved = setter;
	}
	@Override
	public String toString()
	{
		return check;
	}
}
