package pieces;

import game.*;
import pieces.Square;
import java.util.ArrayList;

public class ChessPiece 
{	
	public boolean status = true;
	boolean color;
	public ArrayList<Square> locations = new ArrayList<Square>();
	int row, column;
	
	public void move(int row, int column){};
	public void attack(ChessPiece Enemy){};
	public void die(){status = false;};
	//either returns 2D array of squares, or 2D array of int
	//as positions of nearby square places, or //1D array of loctions 1-64
	public void getMoveLocations(){}; 
	public void highightLocation(){
		getMoveLocations();
		for(Square movable: locations) {
			GameBoard.Board[movable.getRow()][movable.getColumn()].setLight("Move");
			if(movable.getCurrentPiece().getColor() != getColor() && movable.getCurrentPiece() != null) {
				GameBoard.Board[movable.getRow()][movable.getColumn()].setLight("Attack");
			}	
		
	}
};
	
	public Square sendAttackSpot(){
		getMoveLocations();
		for(Square movable: locations) {
			if(movable.getCurrentPiece().getColor() != getColor() && movable.getCurrentPiece() != null) {
			}
			return movable;
		}};
		
	public boolean getColor(){return status;};
	
	public ArrayList<Square> returnLocations(){return locations;};
	
	public void recursion(int row, int col,int rowIncre, int colIncre) {
		if(GameBoard.Board[row][col].getCurrentPiece().getColor() == color) {
			return;
		}
		locations.add(new Square(row,col,false,false));
		
		if(GameBoard.Board[row][col].getCurrentPiece() != null) {
			return;
		}
		recursion(row+rowIncre,col+colIncre, rowIncre, colIncre);
	}
}
