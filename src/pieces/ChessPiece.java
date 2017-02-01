package pieces;

import game.*;
import pieces.Square;
import java.util.ArrayList;

public class ChessPiece 
{	
	public boolean status = true;
	
	public void move(int row, int column){};
	public void attack(ChessPiece Enemy){};
	public void die(){};
	public ArrayList<Square> getMoveLocations(){
		ArrayList<Square> kek = new ArrayList<Square>(0);
		return kek;
	}; //either returns 2D array of squares, or 2D array of int
	//as positions of nearby square places, or //1D array of loctions 1-64
	public void highightLocation(){};
	
	public Square sendAttackSpot(){
		Square kappa = new Square(0,0,false,false);
		return kappa;};
		
	public boolean getColor(){return status;};
}
