package pieces;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import game.*;
public class King extends ChessPiece{
	
	boolean isChecked = false;
	public String name = "King";
	public ArrayList<ChessPiece> saviors = new ArrayList<ChessPiece>();
	public ArrayList<ChessPiece> attacking = new ArrayList<ChessPiece>();

	
	public King(int row, int column, boolean color){
		super(row, column, color);
		isChecked = false;
		canCastleKing = true;
		canCastleQueen = true;
		
		if(color)
			try {
				image = ImageIO.read(new File("whiteKing.png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		else
		{
			try {
				image = ImageIO.read(new File("blackKing.png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	@Override
	public void move(int row, int column) {
		
		super.move(row, column);

		if(column == 6 && canCastleKing == true)	{

			if(this.getColor() == true)	{
				GameBoard.Board[7][7].getCurrentPiece().CastleMove(this.row, this.column-1); // moving rook
				GameBoard.Board[7][7].setCurrentPiece(null);
			}
			if(this.getColor() == false){
				GameBoard.Board[0][7].getCurrentPiece().CastleMove(this.row, this.column-1); // moving rook
				GameBoard.Board[0][7].setCurrentPiece(null);
			}
		}
		
		if(column == 2 && canCastleQueen == true)	{
			if(this.getColor() == true)	{
				GameBoard.Board[7][0].getCurrentPiece().CastleMove(this.row, this.column+1); // moving rook
				GameBoard.Board[7][0].setCurrentPiece(null);
			}
			if(this.getColor() == false){
				GameBoard.Board[0][0].getCurrentPiece().CastleMove(this.row, this.column+1); // moving rook
				GameBoard.Board[0][0].setCurrentPiece(null);
			}
		}
		// Modify BK and WK static pieces.
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
 		this.canCastle();
 		setVisibility(false);
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
 			if(	(GameBoard.Board[row][column-1].getCurrentPiece() == null) && (GameBoard.Board[row][column-2].getCurrentPiece() == null) 
 					&& (GameBoard.Board[row][column-3].getCurrentPiece() == null))
 				locations.add(new Square(row,column-2));
 		}
 		
 		setVisibility(true);
 		
 		
  	}
	
	
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
		if(!checkSquare(newRow,newCol)) {
			//System.out.println(newRow + " " + newCol);
				locations.add(new Square(newRow,newCol,GameBoard.Board[newRow][newCol].getCurrentPiece()));
		}
		
		else {
			return;
		}
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
		if(hasMoved == true)	{
			canCastleQueen = false;
			canCastleKing = false;
			return;
		}

		// calls check to test if its checked
		if(this.getColor() == true) {
			if(GameBoard.Board[7][0].getCurrentPiece().getClass() == test.getClass()) {
				//System.out.println("ISACA" + GameBoard.Board[7][0].getCurrentPiece().getClass() + " "+ test.getClass());
				if(GameBoard.Board[7][0].getCurrentPiece().gethasMoved() == true)
					canCastleQueen = false;
			}
			if(GameBoard.Board[7][7].getCurrentPiece().getClass() == test.getClass()) {
				if(GameBoard.Board[7][7].getCurrentPiece().gethasMoved() == true)
					canCastleKing = false;
			}
		}
		
		if(this.getColor() == false) {
			if(GameBoard.Board[0][0].getCurrentPiece().getClass() == test.getClass()) {
				if(GameBoard.Board[0][0].getCurrentPiece().gethasMoved() == true)
					canCastleQueen = false;
			}
			if(GameBoard.Board[0][7].getCurrentPiece().getClass() == test.getClass()) {
				if(GameBoard.Board[0][7].getCurrentPiece().gethasMoved() == true)
					canCastleKing = false;
			}
		}
	}
	@Override
	public String toString() {
		return color + " " + name + " row: " + row + " col: " +column + " ";
	}
	
}
