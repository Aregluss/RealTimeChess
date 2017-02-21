package pieces;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import game.GameBoard;
import pieces.Square;


public class Pawn extends ChessPiece{

	public boolean hasMoved = false;
	public String name = "Pawn";
	
	
	public Pawn(int row, int column, boolean color){
		super(row, column, color);
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
		sethasMoved(true);
		
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
		getmovelocationLeft(color);
		getmovelocationRight(color);
		getmovelocationOne(color);
		getmovelocationTwo(color);
	}
	
	public void getmovelocationLeft(boolean color) {
		if(column-1 < 0) {
			return;
		}
		
		if(color == true) {
			if(row-1 < 0){
				return;
			}
			if(GameBoard.Board[row-1][column-1].getCurrentPiece() != null) {
				if(GameBoard.Board[row-1][column-1].getCurrentPiece().getColor() != color) {
					locations.add(new Square(row-1,column-1));
				}
			}
		}
		
		if(color == false) {
			if(row+1 > 7) {
				return;
			}
			if(GameBoard.Board[row+1][column-1].getCurrentPiece() != null)
				if(GameBoard.Board[row+1][column-1].getCurrentPiece().getColor() != color) {
				locations.add(new Square(row+1,column-1));
				}
		}
	}
	
	public void getmovelocationRight(boolean color) {
		if(column+1 > 7) {
			return;
		}
		
		
		if(color == true) {
			if(row-1 < 0){
				return;
			}
			if(GameBoard.Board[row-1][column+1].getCurrentPiece() != null) {
				if(GameBoard.Board[row-1][column+1].getCurrentPiece().getColor() != color) {
					locations.add(new Square(row-1,column+1));
				}
			}
		}
		if(color == false) {
			if(row+1 > 7) {
				return;
			}
			if(GameBoard.Board[row+1][column+1].getCurrentPiece() != null) {
				if(GameBoard.Board[row+1][column+1].getCurrentPiece().getColor() != color) {
					locations.add(new Square(row+1,column+1));
				}
			}
		}
	}
	
	public void getmovelocationOne(boolean color) {
		
		if(color == true) {
			if(row-1 < 0 || GameBoard.Board[row-1][column].getCurrentPiece() != null){
				return;
			}		
			
			locations.add(new Square(row-1,column));
		}
		
		if(color == false) {
			if(row+1 > 7 || GameBoard.Board[row+1][column].getCurrentPiece() != null) {
				return;
			}
			
			locations.add(new Square(row+1,column));
		}
	}

	public void getmovelocationTwo(boolean color) {
		if(hasMoved == true){
			return;
		}
		if(color == true) {
			if(row-2 < 0 || row-1 < 0 || GameBoard.Board[row-1][column].getCurrentPiece() != null || 
					GameBoard.Board[row-2][column].getCurrentPiece() != null){
				return;
			}
		
			locations.add(new Square(row-2,column));
		}
		
		if(color == false) {
			if(row+2 > 7 || row+1 > 7 || GameBoard.Board[row+1][column].getCurrentPiece() != null || 
					GameBoard.Board[row+2][column].getCurrentPiece() != null) {
				
				return;
			}
			
			locations.add(new Square(row+2,column));
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
	public String toString() {
		return color + " " + name + " row: " + row + " col: " +column + " ";
	}
	
}
