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
*/
// WHAT WE HAVE TO IMPLEMENT
// Pawns - promotion.
// King- Castling, Checking, Checkmate(if another piece can stop the checkmate)
// Move - mouse Listener + updating gameboard
// 


public class ChessPiece 
{	
	public boolean status = true;
	boolean color;
	public ArrayList<Square> locations = new ArrayList<Square>();
	int row, column;
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
		check()
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
		
	public boolean getColor(){return status;};
	
	public ArrayList<Square> returnLocations(){return locations;};
	
	public void recursion(int row, int col,int rowIncre, int colIncre) {
		if(GameBoard.Board[row][col].getCurrentPiece().getColor() == color) {
			return;
		}
		locations.add(new Square(row,col));
		
		if(GameBoard.Board[row][col].getCurrentPiece() != null) {
			return;
		}
		recursion(row+rowIncre,col+colIncre, rowIncre, colIncre);
	}
	

	public void draw(Graphics g)
	{
		g.drawImage(image, 10+column*80, 10+row*80, width, height, null);
	}
}
