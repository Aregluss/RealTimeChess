package Pieces;


import Pieces.Square;

public interface ChessPiece 
{	
	
	public void move(); 
	public void attack(); 
	public void die(); 
	public void getMoveLocations();
	public void highightLocation();
	public Square sendAttackSpot();
}
