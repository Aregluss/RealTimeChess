package pieces;


import pieces.Square;

public interface ChessPiece 
{	
	
	public void move(); 
	public void attack(); 
	public void die(); 
	public void getMoveLocations(); //either returns 2D array of squares, or 2D array of int
	//as positions of nearby square places, or //1D array of loctions 1-64
	public void highightLocation();
	public Square sendAttackSpot();
}
