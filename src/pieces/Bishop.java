package pieces;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import game.GameBoard;

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
