package pieces;

import javax.swing.ImageIcon;

public class Queen extends ChessPiece{
	public String name = "Queen";

	public Queen(int row, int column, boolean color){
		super(row, column, color);
		
		if(color)
			image = new ImageIcon("whiteQueen.png").getImage();
		else
			image = new ImageIcon("blackQueen.png").getImage();

		
	}
	
	@Override
	public void move(int row, int column) {
		// TODO Auto-generated method stub
		super.move(row, column);
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
			pinnedPieceMovementHelper();
		}
		
 		promotionImmunity();

		setVisibility(true);
	}

	@Override
	public void highlightLocation() {
		// TODO Auto-generated method stub
		super.highlightLocation();
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
