package pieces;

import game.*;
import pieces.Square;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;


/*WHAT WE HAVE TO IMPLEMENT
Pawns - promotion.
King- Castling, Checking, Checkmate(if another piece can stop the checkmate)
Move - mouse Listener + updating gameboard
Highlight location with Square
*/


public class ChessPiece 
{	
	public boolean status = true;
	public boolean hasMoved = false;
	public boolean color;
	public boolean canCastleKing = false;
	public boolean canCastleQueen = false;
	public ArrayList<Square> locations = new ArrayList<Square>();
	public int row, column;
	Image image;
	private int width, height;
	public ChessPiece(int row, int column, boolean color) {
		this.row = row;
		this.column = column;
		this.color = color;
		width = 60;
		height = 60;
	}; 
	public void move(int row, int column){
		getMoveLocations();
		boolean canMove = false;
		for(Square movable: locations)	{
			if(	(row == movable.getRow()) && (column == movable.getColumn()))	{
				canMove = true;
			}			
		}
		if(canMove)	{
			GameBoard.Board[row][column].setCurrentPiece(this);
			GameBoard.Board[this.row][this.column].setCurrentPiece(null);
			this.row = row;
			this.column = column;
			hasMoved = true;
//			check()
		}
		else{
			//invalid movable location... throw an error? idk
		}
	};
	public void attack(ChessPiece Enemy){};
	public void die(){status = false;};
	//either returns 2D array of squares, or 2D array of int
	//as positions of nearby square places, or //1D array of loctions 1-64
	public void getMoveLocations(){}; 
	public void highightLocation(){
		getMoveLocations();
		for(Square movable: locations) {
			GameBoard.Board[movable.getRow()][movable.getColumn()].setLight("Move");
			if(movable.getCurrentPiece().getColor() != this.getColor() && movable.getCurrentPiece() != null) {
				GameBoard.Board[movable.getRow()][movable.getColumn()].setLight("Attack");
			}	
		
	}
};
	
	public Square sendAttackSpot(){
		getMoveLocations();
		Square kappa = new Square();
		for(Square movable: locations) {
			if(movable.getCurrentPiece().getColor() != this.getColor() && movable.getCurrentPiece() != null) {
			}
			return movable;
		}
		return kappa;
		};
		
	public boolean getColor(){return color;};
	
	public ArrayList<Square> returnLocations(){return locations;};
	
	public void recursion(int row, int col,int rowIncre, int colIncre) {
		
		int newRow = row + rowIncre;
		int newCol = col + colIncre;
		
		if( (newRow < 0 || newRow > 7 ) || ( newCol < 0 || newCol > 7 ) ) {
			return;
		}
		
		if(GameBoard.Board[newRow][newCol].getCurrentPiece() != null) {
			if(GameBoard.Board[newRow][newCol].getCurrentPiece().getColor() == color) {		
				return;
			}
		}
		
		locations.add(new Square(newRow,newCol));
		
		if(GameBoard.Board[newRow][newCol].getCurrentPiece() != null) {
			return;
		}
	
		recursion(row+rowIncre,col+colIncre, rowIncre, colIncre);
	}
	
	public void draw(Graphics g)
	{
		g.drawImage(image, 10+column*80, 10+row*80, width, height, null);
	}
	
	@Override
	public String toString() {
		return "ChessPiece [status=" + status + ", color=" + color + ", canCastleKing=" + canCastleKing
				+ ", canCastleQueen=" + canCastleQueen + ", locations=" + locations + ", row=" + row + ", column="
				+ column + ", image=" + image + ", width=" + width + ", height=" + height + "]";
	}
}
