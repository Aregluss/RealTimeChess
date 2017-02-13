package pieces;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import game.*;
public class King extends ChessPiece{
	
	boolean isChecked;
	
	public King(int row, int column, boolean color){
		super(row, column, color);
		isChecked = false;
		canCastleKing = true;
		canCastleQueen = true;
		try {
			image = ImageIO.read(new File("King.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Override
	public void move(int row, int column) {
		// TODO Auto-generated method stub
		
		super.move(row, column);
		if(column == this.column +2)	{
			if(this.getColor() == true)	{
				GameBoard.Board[7][7].getCurrentPiece().move(this.row, this.column+1); // moving rook
			}
			if(this.getColor() == false){
				GameBoard.Board[0][7].getCurrentPiece().move(this.row, this.column+1); // moving rook
			}
		}
		
		if(column == this.column-2)	{
			if(this.getColor() == true)	{
				GameBoard.Board[7][0].getCurrentPiece().move(this.row, this.column-1); // moving rook
			}
			if(this.getColor() == false){
				GameBoard.Board[0][0].getCurrentPiece().move(this.row, this.column-1); // moving rook
			}
		}
		canCastleKing = false;
		canCastleQueen = false;
	}
	
	@Override
	public void attack(ChessPiece Enemy) {
		super.attack(Enemy);
	}

	
	@Override
	public void die() {
		super.die();
	}
	
	@Override
	public void getMoveLocations() {
		locations.clear();
 		
		getmovelocationHelper(1 ,0);
		getmovelocationHelper(0, 1);
		getmovelocationHelper(-1, 0);
		getmovelocationHelper(0,-1);
		getmovelocationHelper(1 ,1);
		getmovelocationHelper(1, -1);
		getmovelocationHelper(-1, 1);
		getmovelocationHelper(-1,-1);
 		
 		if(	(canCastleKing == true) )	{
 			if(	(GameBoard.Board[row][column+1].getCurrentPiece() == null) && (GameBoard.Board[row][column+2].getCurrentPiece() == null))
 				locations.add(new Square(row,column+2));
 			
 		}
 		
 		
 		if( (canCastleQueen == true))	{
 			if(	(GameBoard.Board[row][column-1].getCurrentPiece() == null) && (GameBoard.Board[row][column-2].getCurrentPiece() == null) && (GameBoard.Board[row][column-3].getCurrentPiece() == null))
 				locations.add(new Square(row,column-2));
 		}
  	}
	
	/*
	public void getmovelocationRow(int rowIncre) {
		int newRow = row + rowIncre;
		//Checks boundaries
		if (newRow > 7 || newRow < 0) {
			return;
		}
		//Checks for same team
		if(GameBoard.Board[newRow][column].getCurrentPiece() != null) {
			if(GameBoard.Board[newRow][column].getCurrentPiece().getColor() == color) {		
				return;
			}
		}
		
		//Checks for dangerspot
		if(check(newRow,column,color)) {
			locations.add(new Square(newRow,column));
		}
	}
	
	public void getmovelocationCol(int colIncre) {
		int newCol = column + colIncre;
		//Checks boundaries
		if (newCol > 7 || newCol < 0) {
			return;
		}
		//Checks for same team
		if(GameBoard.Board[row][newCol].getCurrentPiece() != null) {
			if(GameBoard.Board[row][newCol].getCurrentPiece().getColor() == color) {		
				return;
			}
		}
		
		//Checks for dangerspots
		if(check(row,newCol,color)) {
			locations.add(new Square(row,newCol));
		}
	} */
	
	public void getmovelocationHelper(int rowIncre, int colIncre) {
		int newRow = row + rowIncre;
		int newCol = column + colIncre;
		
		//Checks Boundaries
		if( (newRow < 0 || newRow > 7 ) || ( newCol < 0 || newCol > 7 ) ) {
			return;
		}
	
		//Checks for same team
		if(GameBoard.Board[newRow][newCol].getCurrentPiece() != null) {
			if(GameBoard.Board[newRow][newCol].getCurrentPiece().getColor() == color) {		
				return;
			}
		}
		
		//Checks for dangerspots
		if(check(newRow,newCol,color)) {
			System.out.println("IM HERE")	;
			locations.add(new Square(newRow,newCol));
		}
	}
		
	
	
	public boolean check(int i, int j, boolean color) {
		return true;
	}
	
	public boolean checkRecursion() {
		return true;
	}
	
	@Override
	public void highightLocation() {
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
	
	// use the check function, however we do it, to check the locations where it has to move through to see if they are checked
	public void canCastle() { 
		Rook test = new Rook(0,0,true);
		if(hasMoved == true)
			return;
		// calls check to test if its checked
		if(this.getColor() == true) {
			if(GameBoard.Board[7][0].getCurrentPiece().getClass() == test.getClass()) {
				//System.out.println("ISACA" + GameBoard.Board[7][0].getCurrentPiece().getClass() + " "+ test.getClass());
				if(GameBoard.Board[7][0].getCurrentPiece().hasMoved == true)
					canCastleQueen = false;
			}
			if(GameBoard.Board[7][7].getCurrentPiece().getClass() == test.getClass()) {
				if(GameBoard.Board[7][7].getCurrentPiece().hasMoved == true)
					canCastleKing = false;
			}
		}
		
		if(this.getColor() == false) {
			if(GameBoard.Board[0][0].getCurrentPiece().getClass() == test.getClass()) {
				if(GameBoard.Board[0][0].getCurrentPiece().hasMoved == true)
					canCastleQueen = false;
			}
			if(GameBoard.Board[0][7].getCurrentPiece().getClass() == test.getClass()) {
				if(GameBoard.Board[0][7].getCurrentPiece().hasMoved == true)
					canCastleKing = false;
			}
		}
	}
	
}
