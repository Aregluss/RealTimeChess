package pieces;
import java.util.ArrayList;
import game.*;
public class King extends ChessPiece{
	
	@Override
	public void move(int row, int column) {
		// TODO Auto-generated method stub
		super.move(row, column);
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
		
 		for(int i = row-1; i<=row+1; i++)
 		{
 			for(int j= column-1; j<=column+1; j++)
 			{
 				if( (i < 0 || i > 8 ) && ( j < 0 || j > 8 ) && 
 						!(GameBoard.Board[i][j].checkDanger(color,i,j)) && 
 						(!(GameBoard.Board[i][j].getCurrentPiece().getColor() == color) || GameBoard.Board[i][j].getCurrentPiece() != null ) )
 				locations.add(new Square(i,j,false,false));						// Checks boundaries and then checks if location is dangerous, and then checks if a same colored piece is there
 			}
 		}
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
	
	

}
