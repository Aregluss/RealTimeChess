package pieces;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Queen extends ChessPiece{
	public String name = "Queen";

	public Queen(int row, int column, boolean color){
		super(row, column, color);
		try {
			image = ImageIO.read(new File("queen.png"));
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
		// TODO Auto-generated method stub
		super.attack(Enemy);
	}

	@Override
	public void die() {
		// TODO Auto-generated method stub
		super.die();
	}

	@Override
	public void getMoveLocations() {
		super.getMoveLocations();
		
		recursion(row, column, 1 ,0);
		recursion(row, column, 0, 1);
		recursion(row, column,-1, 0);
		recursion(row, column, 0,-1);
		recursion(row, column, 1 ,1);
		recursion(row, column, 1, -1);
		recursion(row, column, -1, 1);
		recursion(row, column, -1,-1);
		
		if (checkpinnedPiece()) {
			System.out.println("im pinned!");
			pinnedPieceMovementHelper();
		}
		setVisibility(true);
	}

	@Override
	public void highightLocation() {
		// TODO Auto-generated method stub
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

	@Override
	public String toString() {
		return color + " " + name + " row: " + row + " col: " +column + " ";
	}

}
