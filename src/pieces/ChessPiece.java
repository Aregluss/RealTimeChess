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
	public void highightLocation(){};
	
	public Square sendAttackSpot(){
		Square kappa = new Square(0,0,false,false);
		return kappa;};
		
	public boolean getColor(){return status;};
	
	public ArrayList<Square> returnLocations(){return locations;};
	
	public void recursion(int row, int col,int rowIncre, int colIncre) {
		if(GameBoard.Board.getCurrentPiece(row,col).getColor() == color) {
			return;
		}
		locations.add(new Square(row,col,false,false));
		
		if(GameBoard.Board.getCurrentPiece(row,col) != null) {
			return;
		}
		recursion(row+rowIncre,col+colIncre, rowIncre, colIncre);
	}
}
