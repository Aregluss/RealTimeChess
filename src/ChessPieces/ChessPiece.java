import Square;

public interface ChessPiece 
{
	boolean whiteTeam;
	
	public void move(); 
	public void attack(); 
	public void die(); 
	public void getMoveLocations();
	public void highightLocation();
	public Square sendAttackSpot();
}
