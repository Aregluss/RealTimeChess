package pieces;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import game.GameBoard;

public class Knight extends ChessPiece{

	public String name = "Knight";

	
	public Knight(int row, int column, boolean color){
		super(row, column, color);
		
		if(color)
			image = new ImageIcon("whiteKnight.png").getImage();
		else
			image = new ImageIcon("blackKnight.png").getImage();
		

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
		super.getMoveLocations();
		if( (row +2 >= 0 && row +2 < 8 ) && ( column +1 >= 0 && column +1 < 8 )) {
			if ( GameBoard.Board[row+2][column+1].getCurrentPiece() != null) { 				
				if (!(GameBoard.Board[row+2][column+1].getCurrentPiece().getColor() == color)) {
					locations.add(new Square(row+2,column+1,GameBoard.Board[row+2][column+1].getCurrentPiece()));
				}
			}
			if ( GameBoard.Board[row+2][column+1].getCurrentPiece() == null) {
						locations.add(new Square(row+2,column+1));
			}
		}
		
		if( (row +2 >= 0 && row +2 < 8 ) && ( column -1 >= 0 && column -1 < 8 )) {
			if ( GameBoard.Board[row+2][column-1].getCurrentPiece() != null) { 				
				if (!(GameBoard.Board[row+2][column-1].getCurrentPiece().getColor() == color)) {
					locations.add(new Square(row+2,column-1,GameBoard.Board[row+2][column-1].getCurrentPiece()));
				}
			}
			if ( GameBoard.Board[row+2][column-1].getCurrentPiece() == null) {
						locations.add(new Square(row+2,column-1));
			}
		}
		
		if( (row -2 >= 0 && row -2 < 8 ) && ( column +1 >= 0 && column +1 < 8 )) {
			if ( GameBoard.Board[row-2][column+1].getCurrentPiece() != null) { 				
				if (!(GameBoard.Board[row-2][column+1].getCurrentPiece().getColor() == color)) {
					locations.add(new Square(row-2,column+1,GameBoard.Board[row-2][column+1].getCurrentPiece()));
				}
			}
			if ( GameBoard.Board[row-2][column+1].getCurrentPiece() == null) {
						locations.add(new Square(row-2,column+1));
			}
		}
		
		if( (row -2 >= 0 && row -2 < 8 ) && ( column -1 >= 0 && column -1 < 8 )) {
			if ( GameBoard.Board[row-2][column-1].getCurrentPiece() != null) { 				
				if (!(GameBoard.Board[row-2][column-1].getCurrentPiece().getColor() == color)) {
					locations.add(new Square(row-2,column-1,GameBoard.Board[row-2][column-1].getCurrentPiece()));
				}
			}
			if ( GameBoard.Board[row-2][column-1].getCurrentPiece() == null) {
						locations.add(new Square(row-2,column-1));
			}
		}
		
		if( (row +1 >= 0 && row +1 < 8 ) && ( column -2 >= 0 && column -2 < 8 )) {
			if ( GameBoard.Board[row+1][column-2].getCurrentPiece() != null) { 				
				if (!(GameBoard.Board[row+1][column-2].getCurrentPiece().getColor() == color)) {
					locations.add(new Square(row+1,column-2,GameBoard.Board[row+1][column-2].getCurrentPiece()));
				}
			}
			if ( GameBoard.Board[row+1][column-2].getCurrentPiece() == null) {
						locations.add(new Square(row+1,column-2));
			}
		}
		
		if( (row +1 >= 0 && row +1 < 8 ) && ( column +2 >= 0 && column +2 < 8 )) {
			if ( GameBoard.Board[row+1][column+2].getCurrentPiece() != null) { 				
				if (!(GameBoard.Board[row+1][column+2].getCurrentPiece().getColor() == color)) {
					locations.add(new Square(row+1,column+2,GameBoard.Board[row+1][column+2].getCurrentPiece()));
				}
			}
			if ( GameBoard.Board[row+1][column+2].getCurrentPiece() == null) {
						locations.add(new Square(row+1,column+2));
			}
		}

		if( (row -1 >= 0 && row -1 < 8 ) && ( column -2 >= 0 && column -2 < 8 )) {
			if ( GameBoard.Board[row-1][column-2].getCurrentPiece() != null) { 				
				if (!(GameBoard.Board[row-1][column-2].getCurrentPiece().getColor() == color)) {
					locations.add(new Square(row-1,column-2,GameBoard.Board[row-1][column-2].getCurrentPiece()));
				}
			}
			if ( GameBoard.Board[row-1][column-2].getCurrentPiece() == null) {
						locations.add(new Square(row-1,column-2));
			}
		}
		
		if( (row -1 >= 0 && row -1 < 8 ) && ( column +2 >= 0 && column +2 < 8 )) {
			if ( GameBoard.Board[row-1][column+2].getCurrentPiece() != null) { 				
				if (!(GameBoard.Board[row-1][column+2].getCurrentPiece().getColor() == color)) {
					locations.add(new Square(row-1,column+2,GameBoard.Board[row-1][column+2].getCurrentPiece()));
				}
			}
			if ( GameBoard.Board[row-1][column+2].getCurrentPiece() == null) {
						locations.add(new Square(row-1,column+2));
			}
		}
		
		if (checkpinnedPiece()) {
			pinnedPieceMovementHelper();
		}
		setVisibility(true);

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

	@Override
	public String toString() {
		return color + " " + name + " row: " + row + " col: " +column + " ";
	}
	
	
}
