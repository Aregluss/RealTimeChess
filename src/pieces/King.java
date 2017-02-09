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
 		for(int i = row-1; i<=row+1; i++)
 		{
 			for(int j= column-1; j<=column+1; j++)
 			{
 				if( (i < 0 || i > 8 ) && ( j < 0 || j > 8 ) && 
 						!(this.check(i,j,this.getColor())) && 
 						(!(GameBoard.Board[i][j].getCurrentPiece().getColor() == this.getColor()) || GameBoard.Board[i][j].getCurrentPiece() != null ) )
 				locations.add(new Square(i,j));						// Checks boundaries and then checks if location is dangerous, and then checks if a same colored piece is there
 			}
 		}
 		
 		if(	(canCastleKing == true) )	{
 			if(	(GameBoard.Board[row][column+1].getCurrentPiece() == null) && (GameBoard.Board[row][column+2].getCurrentPiece() == null))
 				locations.add(new Square(row,column+2));
 			
 		}
 		
 		
 		if( (canCastleQueen == true))	{
 			if(	(GameBoard.Board[row][column-1].getCurrentPiece() == null) && (GameBoard.Board[row][column-2].getCurrentPiece() == null) && (GameBoard.Board[row][column-3].getCurrentPiece() == null))
 				locations.add(new Square(row,column-2));
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
