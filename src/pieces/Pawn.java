package pieces;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import game.GameBoard;
import pieces.Square;
import network.*;


public class Pawn extends ChessPiece{

	public boolean hasMoved = false;
	public String name = "Pawn";
	public static String promotioner;
	public boolean promotion = false;
	public int beforePromotionRow = -1;
	public int beforePromotionCol = -1;
	
	public Pawn(int row, int column, boolean color){
		super(row, column, color);
	
		
		if(color)
			image = new ImageIcon("whitePawn.png").getImage();
		else
			image = new ImageIcon("blackPawn.png").getImage();

	}


	@Override
	public void move(int row, int column) {
		// TODO Auto-generated method stub
		super.move(row, column);
		
		System.out.println(" hey!");
		if(beforePromotionRow != -1 && beforePromotionCol != -1) {
			System.out.println(beforePromotionRow+" this is my old spot "+beforePromotionCol);
			unhighlightLocation(beforePromotionRow,beforePromotionCol);
		}
		
		
		if(color == true) {
			if(row == 0 && this.row == 0) {
				promote(choosePromotion());
				Server.sendPromotion(promotioner);
			}
		}
		
		if(color == false) {
			if(row == 7 && this.row == 7) {
				promote(choosePromotion());
				Client.sendPromotion(promotioner);
			}
		}

	}
	
	public void move(int row, int column, boolean readyforPromotion) {
		super.move(row, column);
	}
	
	public void move(int row, int column, String promotion){
		super.move(row, column);
		
		if(color == true) {
			if(row == 0 && this.row == 0) {
				promote(promotion);
				
			}
		}
		
		if(color == false) {
			if(row == 7 && this.row == 7) {
				promote(promotion);
			}
		}
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
		getmovelocationLeft(color);
		getmovelocationRight(color);
		getmovelocationOne(color);
		getmovelocationTwo(color);
		if (checkpinnedPiece()) {
			pinnedPieceMovementHelper();
		}
 		promotionImmunity();

		setVisibility(true);
	}
	/** Precondition: getMovelocations for a pawn is called
	 *  Postcondition: locations for pawn is modified accordingly
	 *  checks if a pawn can attack to the left diagonal
	 *  if so adds it to pawn's location
	 * @param color of pawn
	 */
	public void getmovelocationLeft(boolean color) {
		if(column-1 < 0) {
			return;
		}
		
		if(color == true) {
			if(row-1 < 0){
				return;
			}
			if(GameBoard.Board[row-1][column-1].getCurrentPiece() != null) {
				if(GameBoard.Board[row-1][column-1].getCurrentPiece().getColor() != color) {
					locations.add(new Square(row-1,column-1,GameBoard.Board[row-1][column-1].getCurrentPiece()));
				}
			}
		}
		
		if(color == false) {
			if(row+1 > 7) {
				return;
			}
			if(GameBoard.Board[row+1][column-1].getCurrentPiece() != null)
				if(GameBoard.Board[row+1][column-1].getCurrentPiece().getColor() != color) {
				locations.add(new Square(row+1,column-1,GameBoard.Board[row+1][column-1].getCurrentPiece()));
				}
		}
	}
	
	/**
	 * Precondition: getMovelocations for a pawn is called
	 *  Postcondition: locations for pawn is modified accordingly
	 *  checks if a pawn can attack to the right diagonal
	 *  if so adds it to pawn's location
	 * @param color of pawn
	 */
	public void getmovelocationRight(boolean color) {
		if(column+1 > 7) {
			return;
		}
		
		
		if(color == true) {
			if(row-1 < 0){
				return;
			}
			if(GameBoard.Board[row-1][column+1].getCurrentPiece() != null) {
				if(GameBoard.Board[row-1][column+1].getCurrentPiece().getColor() != color) {
					locations.add(new Square(row-1,column+1,GameBoard.Board[row-1][column+1].getCurrentPiece()));
				}
			}
		}
		if(color == false) {
			if(row+1 > 7) {
				return;
			}
			if(GameBoard.Board[row+1][column+1].getCurrentPiece() != null) {
				if(GameBoard.Board[row+1][column+1].getCurrentPiece().getColor() != color) {
					locations.add(new Square(row+1,column+1,GameBoard.Board[row+1][column+1].getCurrentPiece()));
				}
			}
		}
	}
	
	/**
	 * Precondition: getMovelocations for a pawn is called
	 *  Postcondition: locations for pawn is modified accordingly
	 *  checks if a pawn can move one space forward
	 *  if so adds it to pawn's location
	 * @param color of pawn
	 */
	public void getmovelocationOne(boolean color) {
		
		if(color == true) {
			if(row-1 < 0 || GameBoard.Board[row-1][column].getCurrentPiece() != null){
				return;
			}		
			
			locations.add(new Square(row-1,column));
		}
		
		if(color == false) {
			if(row+1 > 7 || GameBoard.Board[row+1][column].getCurrentPiece() != null) {
				return;
			}
			
			locations.add(new Square(row+1,column));
		}
	}

	/**
	 * Precondition: getMovelocations for a pawn is called
	 *  Postcondition: locations for pawn is modified accordingly
	 *  checks if a pawn can move two spaces, checks if a pawn has moved before
	 *  if so adds it to pawn's location
	 * @param color of pawn
	 */
	public void getmovelocationTwo(boolean color) {
		if(hasMoved == true){
			return;
		}
		if(color == true) {
			if(row-2 < 0 || row-1 < 0 || GameBoard.Board[row-1][column].getCurrentPiece() != null || 
					GameBoard.Board[row-2][column].getCurrentPiece() != null){
				return;
			}
		
			locations.add(new Square(row-2,column));
		}
		
		if(color == false) {
			if(row+2 > 7 || row+1 > 7 || GameBoard.Board[row+1][column].getCurrentPiece() != null || 
					GameBoard.Board[row+2][column].getCurrentPiece() != null) {
				
				return;
			}
			
			locations.add(new Square(row+2,column));
		}
	}
			
	/**
	 * Precondition called when the pawn hits the end of the boar
	 * Postcondition promotes the pawn to the selected promotion
	 * 
	 * @param promotionPiece, which piece the pawn wants to promote to
	 */
	//TODO let the player choose + update player arrays accordingly
	public void promote(String promotionPiece) {
		promotioner = promotionPiece;
		// light pops, ui to ask player what they want the pawn to be promoted to
		if(promotionPiece.equals("Queen")) {
			GameBoard.Board[row][column].setCurrentPiece(new Queen(row,column,color));
		}
		if(promotionPiece.equals("Bishop")) {
			GameBoard.Board[row][column].setCurrentPiece(new Bishop(row,column,color));
		}
		if(promotionPiece.equals("Knight")) {
			GameBoard.Board[row][column].setCurrentPiece(new Knight(row,column,color));
		}
		if(promotionPiece.equals("rook")) {
			GameBoard.Board[row][column].setCurrentPiece(new Rook(row,column,color));
		}
		if(color){
			//update piece for player
			GameBoard.Player1.pieces.remove(GameBoard.Board[this.row][this.column]);
			GameBoard.Player1.pieces.add(new Square(row,column,this));
			if(GameBoard.Board[GameBoard.Bk.getRow()][GameBoard.Bk.getColumn()].getCurrentPiece().checkSquare(GameBoard.Bk.getRow(),GameBoard.Bk.getColumn())) {
				checkKing(false);
				( (King)GameBoard.Board[GameBoard.Bk.getRow()][GameBoard.Bk.getColumn()].getCurrentPiece()).checkResolution();
			}
		}
		else {
			GameBoard.Player2.pieces.remove(GameBoard.Board[this.row][this.column]);
			GameBoard.Player2.pieces.add(new Square(row,column,this));
			if(GameBoard.Board[GameBoard.Wk.getRow()][GameBoard.Wk.getColumn()].getCurrentPiece().checkSquare(GameBoard.Wk.getRow(),GameBoard.Wk.getColumn())) {
				checkKing(true);
				( (King)GameBoard.Board[GameBoard.Wk.getRow()][GameBoard.Wk.getColumn()].getCurrentPiece()).checkResolution();
			}
		}
		
		
	}
	
	/**
	 * Precondition: called when the pawn hits the end of the board
	 * Postcondition
	 * @return String that reflect what the player has chosen for their pawn
	 */
	
	public String choosePromotion() {
		String[] options = new String[] {"Queen", "Rook", "Bishop", "Knight"};
		int response = JOptionPane.showOptionDialog(null, "Message", "Title",
		JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
		    // Where response == 0 for Yes, 1 for No, 2 for Maybe and -1 or 3 for Escape/Cancel.
		return options[response];
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
		
	public boolean gethasMoved() {
		return hasMoved;
	}
	
	public void sethasMoved(boolean setter) {
		hasMoved = setter;
	}
	
	@Override
	public String toString() {
		return color + " " + name + " row: " + row + " col: " +column + " ";
	}
	
}
