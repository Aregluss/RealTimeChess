package pieces;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import game.GameBoard;
import pieces.Square;


public class Pawn extends ChessPiece{

	public boolean hasMoved = false;
	public String name = "Pawn";
	
	
	public Pawn(int row, int column, boolean color){
		super(row, column, color);
	
		
		if(color)
			image = new ImageIcon("whitePawn.png").getImage();
		else
			image = new ImageIcon("blackPawn.png").getImage();

	}


	@Override
	public void move(int row, int column) {
		// TODO Auto-generated method stub
		super.move(row, column);
		
		if(color == true) {
			if(row == 0 && this.row == 0) {
				promote(choosePromotion());
				
			}
		}
		
		if(color == false) {
			if(row == 7 && this.row == 7) {
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
		super.getMoveLocations();
		getmovelocationLeft(color);
		getmovelocationRight(color);
		getmovelocationOne(color);
		getmovelocationTwo(color);
		if (checkpinnedPiece()) {
			pinnedPieceMovementHelper();
		}
		setVisibility(true);
	}
	/** Precondition: getMovelocations for a pawn is called
	 *  Postcondition: locations for pawn is modified accordingly
	 *  checks if a pawn can attack to the left diagonal
	 *  if so adds it to pawn's location
	 * @param color of pawn
	 */
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
					locations.add(new Square(row-1,column-1,GameBoard.Board[row-1][column-1].getCurrentPiece()));
				}
			}
		}
		
		if(color == false) {
			if(row+1 > 7) {
				return;
			}
			if(GameBoard.Board[row+1][column-1].getCurrentPiece() != null)
				if(GameBoard.Board[row+1][column-1].getCurrentPiece().getColor() != color) {
				locations.add(new Square(row+1,column-1,GameBoard.Board[row+1][column-1].getCurrentPiece()));
				}
		}
	}
	
	/**
	 * Precondition: getMovelocations for a pawn is called
	 *  Postcondition: locations for pawn is modified accordingly
	 *  checks if a pawn can attack to the right diagonal
	 *  if so adds it to pawn's location
	 * @param color of pawn
	 */
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
					locations.add(new Square(row-1,column+1,GameBoard.Board[row-1][column+1].getCurrentPiece()));
				}
			}
		}
		if(color == false) {
			if(row+1 > 7) {
				return;
			}
			if(GameBoard.Board[row+1][column+1].getCurrentPiece() != null) {
				if(GameBoard.Board[row+1][column+1].getCurrentPiece().getColor() != color) {
					locations.add(new Square(row+1,column+1,GameBoard.Board[row+1][column+1].getCurrentPiece()));
				}
			}
		}
	}
	
	/**
	 * Precondition: getMovelocations for a pawn is called
	 *  Postcondition: locations for pawn is modified accordingly
	 *  checks if a pawn can move one space forward
	 *  if so adds it to pawn's location
	 * @param color of pawn
	 */
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

	/**
	 * Precondition: getMovelocations for a pawn is called
	 *  Postcondition: locations for pawn is modified accordingly
	 *  checks if a pawn can move two spaces, checks if a pawn has moved before
	 *  if so adds it to pawn's location
	 * @param color of pawn
	 */
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
			
	/**
	 * Precondition called when the pawn hits the end of the board
	 * Postcondition promotes the pawn to the selected promotion
	 * 
	 * @param promotionPiece, which piece the pawn wants to promote to
	 */
	//TODO let the player choose + update player arrays accordingly
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
		if(color){
			//update piece for player
			GameBoard.Player1.pieces.remove(GameBoard.Board[this.row][this.column]);
			GameBoard.Player1.pieces.add(new Square(row,column,this));
		}
		else {
			GameBoard.Player2.pieces.remove(GameBoard.Board[this.row][this.column]);
			GameBoard.Player2.pieces.add(new Square(row,column,this));
		}
	}
	
	/**
	 * Precondition: called when the pawn hits the end of the board
	 * Postcondition
	 * @return String that reflect what the player has chosen for their pawn
	 */
	
	public String choosePromotion() {
		//UI STUFF HERE
		return "queen";
	}

	@Override
	public void highlightLocation() {
		// TODO Auto-generated method stub
		super.highlightLocation();
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
