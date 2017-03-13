package pieces;

import javax.swing.ImageIcon;

public class Bishop extends ChessPiece{

	public String name = "Bishop";

	
	public Bishop(int row, int column, boolean color) {
		super(row, column, color);
		// TODO Auto-generated constructor stub
	
		if(color)
			image = new ImageIcon("whiteBishop.png").getImage();
		else
			image = new ImageIcon("blackBishop.png").getImage();
		
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
		recursion(row, column, 1 ,1);
		recursion(row, column, 1, -1);
		recursion(row, column, -1, 1);
		recursion(row, column, -1,-1);
		if (checkpinnedPiece()) {
			pinnedPieceMovementHelper();
		}
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
