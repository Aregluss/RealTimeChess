package pieces;

import javax.swing.ImageIcon;

public class Rook extends ChessPiece {

	public String name = "Rook";
	
	public Rook(int row, int column, boolean color) {
		super(row, column, color);
		// TODO Auto-generated constructor stub
		
		if(color)
			image = new ImageIcon("whiteRook.png").getImage();
		else
			image = new ImageIcon("blackRook.png").getImage();
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
	
	
	public void getMoveLocations() {
		super.getMoveLocations();
		recursion(row, column, 1 ,0);
		recursion(row, column, 0, 1);
		recursion(row, column,-1, 0);
		recursion(row, column, 0,-1); 
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
