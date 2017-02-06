package pieces;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import game.*;
public class King extends ChessPiece{
	
	boolean isChecked;
	
	public King(int row, int column, boolean color){
		super(row, column, color);
		isChecked = false;
		try {
			image = ImageIO.read(new File("King.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
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
		locations.clear();
 		for(int i = row-1; i<=row+1; i++)
 		{
 			for(int j= column-1; j<=column+1; j++)
 			{
 				if( (i < 0 || i > 8 ) && ( j < 0 || j > 8 ) && 
 						!(this.check(i,j,this.getColor())) && 
 						(!(GameBoard.Board[i][j].getCurrentPiece().getColor() == this.getColor()) || GameBoard.Board[i][j].getCurrentPiece() != null ) )
 				locations.add(new Square(i,j));						// Checks boundaries and then checks if location is dangerous, and then checks if a same colored piece is there
 			}
 		}
  	}
	
	public boolean check(int i, int j, boolean color) {
		
		return true;
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
