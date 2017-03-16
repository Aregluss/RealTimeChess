package pieces;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import game.*;
public class King extends ChessPiece{
	
	public boolean isChecked = false;
	public String name = "King";
	private ArrayList<ChessPiece> saviors = new ArrayList<ChessPiece>();
	private ArrayList<ChessPiece> attacking = new ArrayList<ChessPiece>();
	private ArrayList<ChessPiece> checkAttack = new ArrayList<ChessPiece>();

	
	public King(int row, int column, boolean color){
		super(row, column, color);
		isChecked = false;
		canCastleKing = true;
		canCastleQueen = true;
		if(color)
			image = new ImageIcon("whiteKing.png").getImage();
		else
			image = new ImageIcon("blackKing.png").getImage();
		
		
	}
	
	@Override
	public void move(int row, int column) {
		
		boolean canMove = false;
		System.out.println("king move");
		System.out.println("a "+ GameBoard.Board[row][column].getCurrentPiece());
		if(GameBoard.gameState == 0) {
			getMoveLocations();
		}
		System.out.println("b");

		//Search the locations array (created by GetMoveLocations), if a valid move set canMove to true
		for(Square movable: locations)	{
			if((row == movable.getRow()) && (column == movable.getColumn()))	{
				canMove = true;
			}			
		}
		System.out.println("c");

		//Actually moves the piece, if it's a king moving then update the global king squares stored in GameBoard
		if(canMove)	{
			offCoolDown = false;
			time = A_Clock.return_milli_time();
			if(GameBoard.Board[this.row][this.column].getCurrentPiece() instanceof King) {
				if(this.color == true) {
					GameBoard.Wk.setRow(row);
					GameBoard.Wk.setColumn(column);
				}
	
				if (color == false) {
					GameBoard.Bk.setRow(row);
					GameBoard.Bk.setColumn(column);
				}
			}
			System.out.println("d");

			//checks if moving will kill an enemy piece, if so update the player arraylists accordingly
			if(GameBoard.Board[row][column].getCurrentPiece() != null) {
				if(color){
					//update piece for player
					GameBoard.Player1.pieces.remove(GameBoard.Board[this.row][this.column]);
					GameBoard.Player2.pieces.remove(GameBoard.Board[row][column]);
					GameBoard.Board[row][column].getCurrentPiece().unhighlightLocation(row, column);
				}
				else {
					GameBoard.Player2.pieces.remove(GameBoard.Board[this.row][this.column]);
					GameBoard.Player1.pieces.remove(GameBoard.Board[row][column]);
					GameBoard.Board[row][column].getCurrentPiece().unhighlightLocation(row, column);
				}
				GameBoard.graphBoard.resetMousePressed();

			}
			//for moving to an empty space
			else {
				if(color){
					//update piece for player
					GameBoard.Player1.pieces.remove(GameBoard.Board[this.row][this.column]);
				}
				else {
					GameBoard.Player2.pieces.remove(GameBoard.Board[this.row][this.column]);
				}
			}
			
			System.out.println("e");

			//Moves the piece then deletes itself from its old position
			GameBoard.Board[row][column].setCurrentPiece(this);
			GameBoard.Board[this.row][this.column].setCurrentPiece(null);
			
			System.out.println("f");

			if(GameBoard.getlastSelected() != null && GameBoard.gameState == 0) {
				if(GameBoard.getlastSelected().getCurrentPiece() != GameBoard.Board[row][column].getCurrentPiece()) {
					GameBoard.getlastSelected().getCurrentPiece().unhighlightLocation(GameBoard.getlastSelected().getCurrentPiece().row, GameBoard.getlastSelected().getCurrentPiece().column);
					GameBoard.getlastSelected().getCurrentPiece().getMoveLocations();
					System.out.println(GameBoard.getlastSelected().getCurrentPiece()+" Why am I getting called? " + GameBoard.getlastSelected());
					GameBoard.getlastSelected().getCurrentPiece().highlightLocation();
				}
			}
			
			System.out.println("g");
			
			//Just added
			if(GameBoard.getlastSelected() != null){
				if(GameBoard.getlastSelected().getCurrentPiece() == GameBoard.Board[row][column].getCurrentPiece() ) {
					GameBoard.clearlastSelected();
				}
			}
			
			System.out.println("h");
			
			this.row = row;
			this.column = column;
			//hasMoved = true;
			
			if(color){
				//add piece for player
				GameBoard.Player1.pieces.add(new Square(row,column,this));
			}
			else {
				GameBoard.Player2.pieces.add(new Square(row,column,this));
			}
			
			System.out.println("j");
			
			//Indicates that the game is in checkresolution once a piece moves, sets the state to normal
			//with no bugs, a piece can ONLY move if it resolves the check
			if(GameBoard.gameState == 2) {
				GameBoard.gameState = 1;
				( (King)GameBoard.Board[GameBoard.Bk.getRow()][GameBoard.Bk.getColumn()].getCurrentPiece()).isChecked = false;
				( (King)GameBoard.Board[GameBoard.Wk.getRow()][GameBoard.Wk.getColumn()].getCurrentPiece()).isChecked = false;
			}
			
			System.out.println(GameBoard.Board[GameBoard.Bk.getRow()][GameBoard.Bk.getColumn()].getCurrentPiece()+ " j " +GameBoard.Board[GameBoard.Wk.getRow()][GameBoard.Wk.getColumn()].getCurrentPiece());
			
			//Checks if the piece moving caused a check on the enemy king, if this is true then check resolution occurs
			//FREEZE GAME, disable enemy, Check resolution
			if(color==true) {
				if ( GameBoard.Board[GameBoard.Bk.getRow()][GameBoard.Bk.getColumn()].getCurrentPiece().checkSquare(GameBoard.Bk.getRow(), GameBoard.Bk.getColumn())) {
					checkKing(false);
					if( ( (King)GameBoard.Board[GameBoard.Bk.getRow()][GameBoard.Bk.getColumn()].getCurrentPiece()).isChecked == true) {
						//GameBoard.Board[GameBoard.Bk.getRow()][GameBoard.Bk.getColumn()].getCurrentPiece().setoffCoolDown(true);
						( (King)GameBoard.Board[GameBoard.Bk.getRow()][GameBoard.Bk.getColumn()].getCurrentPiece()).checkResolution();
					}
				}
			}
			else {
				if ( GameBoard.Board[GameBoard.Wk.getRow()][GameBoard.Wk.getColumn()].getCurrentPiece().checkSquare(GameBoard.Wk.getRow(), GameBoard.Wk.getColumn())) {
					checkKing(true);
					if( ( (King)GameBoard.Board[GameBoard.Wk.getRow()][GameBoard.Wk.getColumn()].getCurrentPiece()).isChecked == true) {
						//GameBoard.Board[GameBoard.Wk.getRow()][GameBoard.Wk.getColumn()].getCurrentPiece().setoffCoolDown(true);
						( (King)GameBoard.Board[GameBoard.Wk.getRow()][GameBoard.Wk.getColumn()].getCurrentPiece()).checkResolution();
					}
				}
			}
			
			System.out.println("k");			
			
			if(GameBoard.gameState == 1) {
				//CHECK RESOLVED
				//Reset the arraylist containing pieces that can save the king
				GameBoard.gameState = 0;
				clearcheckhighlightLocation();
				saviors.clear();
				//unfreeze the game + enable enemy pieces 
				System.out.println("CHECK RESOLVED!!");
				A_Clock.continueTime();
			}
			
			System.out.println("l");
			
			if(column == 6 && canCastleKing == true)	{

				if(this.getColor() == true)	{
					GameBoard.Board[7][7].getCurrentPiece().CastleMove(this.row, this.column-1); // moving rook
					GameBoard.Board[7][7].setCurrentPiece(null);
				}
				if(this.getColor() == false){
					GameBoard.Board[0][7].getCurrentPiece().CastleMove(this.row, this.column-1); // moving rook
					GameBoard.Board[0][7].setCurrentPiece(null);
				}
			}
			
			System.out.println("m");
			
			if(column == 2 && canCastleQueen == true)	{
				if(this.getColor() == true)	{
					GameBoard.Board[7][0].getCurrentPiece().CastleMove(this.row, this.column+1); // moving rook
					GameBoard.Board[7][0].setCurrentPiece(null);
				}
				if(this.getColor() == false){
					GameBoard.Board[0][0].getCurrentPiece().CastleMove(this.row, this.column+1); // moving rook
					GameBoard.Board[0][0].setCurrentPiece(null);
				}
			}
			
			System.out.println("n");
			
			if (draw()) {
				GameBoard.gameState = 4; 
			}
			
			sethasMoved(true);
			// Modify BK and WK static pieces.
			canCastleKing = false;
			canCastleQueen = false;
			
		}
		else{
			return;
			//invalid movable location... throw an error? idk
		}
	}

	@Override
	public void die() {
		super.die();
	}
	
	@Override
	public void getMoveLocations() {
		locations.clear();
 		this.canCastle();
 		setVisibility(false);
		getmovelocationHelper(1 ,0);
		getmovelocationHelper(0, 1);
		getmovelocationHelper(-1, 0);
		getmovelocationHelper(0,-1);
		getmovelocationHelper(1 ,1);
		getmovelocationHelper(1, -1);
		getmovelocationHelper(-1, 1);
		getmovelocationHelper(-1,-1);
		
 		if(	(canCastleKing == true && isChecked == false && !checkSquare(row,column+2)  && !checkSquare(row,column+1)) )	{
 			if(GameBoard.Board[row][column+3].getCurrentPiece() != null && GameBoard.Board[row][column+3].getCurrentPiece() instanceof Rook
 					&& GameBoard.Board[row][column+3].getCurrentPiece().gethasMoved() == false) {
	 			if(	(GameBoard.Board[row][column+1].getCurrentPiece() == null) && (GameBoard.Board[row][column+2].getCurrentPiece() == null))
	 				locations.add(new Square(row,column+2));
 			}
 		}
 		
 		
 		if( (canCastleQueen == true && isChecked == false && !checkSquare(row,column-2)) && !checkSquare(row,column-1))	{
 			if(GameBoard.Board[row][column-4].getCurrentPiece() != null && GameBoard.Board[row][column-4].getCurrentPiece() instanceof Rook
 					&& GameBoard.Board[row][column-4].getCurrentPiece().gethasMoved() == false) {
	 			if(	(GameBoard.Board[row][column-1].getCurrentPiece() == null) && (GameBoard.Board[row][column-2].getCurrentPiece() == null) 
	 					&& (GameBoard.Board[row][column-3].getCurrentPiece() == null))
	 				locations.add(new Square(row,column-2));
 			}
 		}
 		promotionImmunity();
 		setVisibility(true);
 		
 		
  	}
	
	
	public void getmovelocationHelper(int rowIncre, int colIncre) {
		int newRow = row + rowIncre;
		int newCol = column + colIncre;

		//Checks Boundaries
		if( (newRow < 0 || newRow > 7 ) || ( newCol < 0 || newCol > 7 ) ) {
			return;
		}
	
		//Checks for same team
		if(GameBoard.Board[newRow][newCol].getCurrentPiece() != null) {
			if(GameBoard.Board[newRow][newCol].getCurrentPiece().getColor() == color) {		
				return;
			}
		}
		
		//Checks for dangerspots
		if(!checkSquare(newRow,newCol)) {
			//System.out.println(newRow + " " + newCol);
				locations.add(new Square(newRow,newCol,GameBoard.Board[newRow][newCol].getCurrentPiece()));
		}
		
		else {
			return;
		}
	}
		
	
	public boolean checkRecursion() {
		return true;
	}
	
	@Override
	public void highlightLocation() {
		super.highlightLocation();
	}
	
	@Override
	public boolean getColor() {
		// TODO Auto-generated method stub
		return super.getColor();
	}
	
	// use the check function, however we do it, to check the locations where it has to move through to see if they are checked
	public void canCastle() { 
		Rook test = new Rook(0,0,true);
		if(hasMoved == true)	{
			canCastleQueen = false;
			canCastleKing = false;
			return;
		}

		// calls check to test if its checked
		if(this.getColor() == true) {
			if(GameBoard.Board[7][0].getCurrentPiece() != null) {
				if(GameBoard.Board[7][0].getCurrentPiece().getClass() == test.getClass()) {
					if(GameBoard.Board[7][0].getCurrentPiece().gethasMoved() == true)
						canCastleQueen = false;
				}
			}
			if(GameBoard.Board[7][7].getCurrentPiece() != null) {
				if(GameBoard.Board[7][7].getCurrentPiece().getClass() == test.getClass()) {
					if(GameBoard.Board[7][7].getCurrentPiece().gethasMoved() == true)
						canCastleKing = false;
				}
			}
		}
		
		if(this.getColor() == false) {
			if(GameBoard.Board[0][0].getCurrentPiece() != null) {
				if(GameBoard.Board[0][0].getCurrentPiece().getClass() == test.getClass()) {
					if(GameBoard.Board[0][0].getCurrentPiece().gethasMoved() == true)
						canCastleQueen = false;
				}
			}
			if(GameBoard.Board[0][7].getCurrentPiece() != null) {
				if(GameBoard.Board[0][7].getCurrentPiece().getClass() == test.getClass()) {
					if(GameBoard.Board[0][7].getCurrentPiece().gethasMoved() == true)
						canCastleKing = false;
				}
			}
		}
	}
	
	/**
	 * This will highlight the king that is being checked and all the pieces that are checking it with a red border
	 * Preconditions : The king is in checked, and his attackers array has been updated
	 * Postconditions : red squares will outline the king and his attackers
	 */
	@Override
	public void checkhighlightLocation() {
		GameBoard.Board[this.row][this.column].setSquare(2);
		for(ChessPiece attacker :checkAttack){
			GameBoard.Board[attacker.row][attacker.column].setSquare(2);
		}
	}
	
	/**
	 * This will unhighlight the king that is being checked and all the pieces that are checking it
	 * Preconditions : The king is now out of check
	 * Postconditions : red squares will be cleared around the king and his attackers
	 */
	@Override
	public void clearcheckhighlightLocation() {
		GameBoard.Board[this.row][this.column].setSquare(412);
		for(ChessPiece attacker :checkAttack){
			GameBoard.Board[attacker.row][attacker.column].setSquare(124);
		}
	}
	
	/**
	 * This function sets the arraylist saviors for the king which holds all the pieces that can resolve a check resolution
	 * @param help, arraylist of pieces that can save the king
	 */
	public void setSaviors(ArrayList<ChessPiece> help) {
		saviors = help;
	}
	
	/**
	 * This function sets the arraylist attacking which holds all pieces attack a certain square
	 * @param attack, arraylist of pieces that can  attack the king's squares or squares around him
	 */
	public void setAttacking(ArrayList<ChessPiece> attack) {
		attacking = attack;
	}
	
	/**
	 * This function sets the arraylist holding pieces checking the king
	 * @param attack, arraylist of pieces checking the king
	 */
	public void setcheckAttack(ArrayList<ChessPiece> attack) {
		checkAttack = attack;
	}
	/**
	 * 
	 * @return saviors arraylist
	 */
	public ArrayList<ChessPiece> getSaviors() {
		return saviors;
	}
	
	/**
	 * 
	 * @return attacking arraylist
	 */
	public ArrayList<ChessPiece> getAttacking() {
		return attacking;
	}
	
	/**
	 * 
	 * @return checkAttack arraylist
	 */
	public ArrayList<ChessPiece> getcheckAttack() {
		return checkAttack;
	}
	
	@Override
	public String toString() {
		return color + " " + name + " row: " + row + " col: " +column + " ";
	}
	
}
