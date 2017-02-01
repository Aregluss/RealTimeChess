package pieces;
import java.util.ArrayList;
import game.*;
public class King extends ChessPiece{
	int row, column;
	boolean color;
	@Override
	public void move(int row, int column) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void attack() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void die() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<Square> getMoveLocations() {
		// TODO Auto-generated method stub
		ArrayList<Square> locations = new ArrayList<Square>(0);
		for(int i = row-1; i<=row+1; i++)
		{
			for(int j= column-1; j<=column+1; j++)
			{
				if( (i < 0 || i > 8 ) && ( j < 0 || j > 8 ) && !(GameBoard.Board.checkDanger(color,i,j)) && !(GameBoard.Board.getCurrentPiece(i,j).getColor() == color) ) 
					locations.add(Square(i,j,false,false));						// Checks boundaries and then checks if location is dangerous, and then checks if a same colored piece is there
			}
		}
		return locations;
	}

	@Override
	public void highightLocation() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Square sendAttackSpot() {
		// TODO Auto-generated method stub
		return null;
	}

}
