package pieces;

import game.*;
import sound.SoundEffect;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.*;

/** This is the class that every piece inherits from
 * @author Alexander Ngo 60%, -Isaac Fu- 34.5%, Areg Nersisyan 5%, David Sun 0.5%
 *
 */

/*WHAT WE HAVE TO IMPLEMENT
Pawns - promotion selection.
Highlight location with Square
*/


public class ChessPiece// extends JPanel
{	
	public boolean checkMove = true;
	public boolean status = true;
	public boolean hasMoved = false;
	public boolean color;
	public boolean canCastleKing = false;
	public boolean canCastleQueen = false;
	public ArrayList<Square> locations = new ArrayList<Square>();
	public ArrayList<Square> dangerSpots = new ArrayList<Square>();
	public boolean isVisible = true;
	public int row, column;
	Image image;
	public boolean offCoolDown = true;
	public long time, time_limit = 3000;
	public static TimerClock A_Clock = new TimerClock();
	public int width, height;
	public String name;
	
	//Credits to https://www.freesound.org/people/Kastenfrosch/sounds/162465/
    SoundEffect checkSound = new SoundEffect("162465__kastenfrosch__lostitem.wav");
	
	/**
	 * Generic constructor to initialize piece on top on the Graphics and GameBoard 
	 * @param row, row that the piece will be created in
	 * @param column, column that the piece will be created in
	 * @param color, Black (false) or White(true) the team the piece is associated with 
	 */
	
	public ChessPiece(int row, int column, boolean color) {
		this.row = row;
		this.column = column;
		this.color = color;
		width = GraphicsBoard.WIDTH/8;
		height = GraphicsBoard.HEIGHT/8;
		
	}; 
	
	/**  returns if the the piece has moved before, useful for Castling and Pawns
	 * @return boolean, whether the piece has moved or not */
	public boolean gethasMoved() {
		return hasMoved;
	}
	
	/** Called when a piece first moves 
	 * @param setter, set to true when a piece moves for the first time
	 * */
	public void sethasMoved(boolean setter) {
		if(this instanceof Rook || this instanceof King) {
		}
		hasMoved = setter;
	}
	
	public void setoffCoolDown(boolean setter)
	{
		offCoolDown = setter;
	}
	/** Only invoked when the king initiates a Castle, special move function 
	 * @param row row to move the king to 
	 * @param col column to move the king to
	 * */
	//TODO update the player board accordingly after castling
	public void CastleMove(int row, int column){
		if(color){
			//update piece for player
			GameBoard.Player1.pieces.remove(GameBoard.Board[this.row][this.column]);
		}
		else {
			GameBoard.Player2.pieces.remove(GameBoard.Board[this.row][this.column]);
		}
		GameBoard.Board[row][column].setCurrentPiece(this);
		this.row = row;
		this.column = column;
		if(color){
			//add piece for player
			GameBoard.Player1.pieces.add(new Square(row,column,this));
		}
		else {
			GameBoard.Player2.pieces.add(new Square(row,column,this));
		}
		hasMoved = true;
	}
	
	/** Function that actually moves the pieces, children all call this first 
	 *  in their own respective move functions 
	 *  Preconditions : getMoveLocations or a modified version of it (for checking and pinning )has already been run,
	 *  Postconditions: the piece has been moved to the indicated position, the GameBoard + player piece arrays have been updated accordingly 
	 *  @param row row to move the piece
	 *  @param col column to move the piece
	 *  */
	
	public void move(int row, int column){
		boolean canMove = false;
	
		if(GameBoard.gameState == 0) {
			getMoveLocations();
		}
		
		//Search the locations array (created by GetMoveLocations), if a valid move set canMove to true
		for(Square movable: locations)	{
			if((row == movable.getRow()) && (column == movable.getColumn()))	{
				canMove = true;
			}			
		}
    
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
			//Moves the piece then deletes itself from its old position
			GameBoard.Board[row][column].setCurrentPiece(this);
			GameBoard.Board[this.row][this.column].setCurrentPiece(null);
			if(GameBoard.getlastSelected() != null && GameBoard.gameState == 0) {
				GameBoard.getlastSelected().getCurrentPiece().unhighlightLocation(GameBoard.getlastSelected().getCurrentPiece().row, GameBoard.getlastSelected().getCurrentPiece().column);
				GameBoard.getlastSelected().getCurrentPiece().getMoveLocations();
				GameBoard.getlastSelected().getCurrentPiece().highlightLocation();
			}
			//Just added
			if(GameBoard.getlastSelected() != null){
				if(GameBoard.getlastSelected().getCurrentPiece() == GameBoard.Board[row][column].getCurrentPiece() ) {
					GameBoard.clearlastSelected();
				}
			}
			
			this.row = row;
			this.column = column;
			hasMoved = true;
			
			if(color){
				//add piece for player
				GameBoard.Player1.pieces.add(new Square(row,column,this));
			}
			else {
				GameBoard.Player2.pieces.add(new Square(row,column,this));
			}
			
			//Indicates that the game is in checkresolution once a piece moves, sets the state to normal
			//with no bugs, a piece can ONLY move if it resolves the check
			if(GameBoard.gameState == 2) {
				GameBoard.gameState = 1;
				( (King)GameBoard.Board[GameBoard.Bk.getRow()][GameBoard.Bk.getColumn()].getCurrentPiece()).isChecked = false;
				( (King)GameBoard.Board[GameBoard.Wk.getRow()][GameBoard.Wk.getColumn()].getCurrentPiece()).isChecked = false;
			}
			
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
			
			if(GameBoard.gameState == 1) {
				//CHECK RESOLVED
				//Reset the arraylist containing pieces that can save the king
				GameBoard.gameState = 0;
				
				if(GameBoard.getChecked() == 0) {
					GameBoard.Board[GameBoard.Bk.getRow()][GameBoard.Bk.getColumn()].getCurrentPiece().clearcheckhighlightLocation();
				}
				if(GameBoard.getChecked() == 1) {
					GameBoard.Board[GameBoard.Wk.getRow()][GameBoard.Wk.getColumn()].getCurrentPiece().clearcheckhighlightLocation();
				}
				( (King)GameBoard.Board[GameBoard.Bk.getRow()][GameBoard.Bk.getColumn()].getCurrentPiece()).getSaviors().clear();
				( (King)GameBoard.Board[GameBoard.Wk.getRow()][GameBoard.Wk.getColumn()].getCurrentPiece()).getSaviors().clear();
				( (King)GameBoard.Board[GameBoard.Bk.getRow()][GameBoard.Bk.getColumn()].getCurrentPiece()).getcheckAttack().clear();
				( (King)GameBoard.Board[GameBoard.Wk.getRow()][GameBoard.Wk.getColumn()].getCurrentPiece()).getcheckAttack().clear();
				GameBoard.setChecked(2);
				//unfreeze the game + enable enemy pieces 
				System.out.println("CHECK RESOLVED!!");
				
				A_Clock.continueTime();
			}
			
			sethasMoved(true);
			
		}
		else{
			return;
			//invalid movable location... throw an error? idk
		}
	};
	
	/** Unused for now */
	//Maybe break move up a bit and add that bit of code in here, things to look at for 2nd iteration
	public void attack(ChessPiece Enemy){};
	
	/** Called when a piece is successfully attacked */
	//Same comment as above
	public void die(){status = false;};
	
	/** Creates a list of all possible movement locations for that current piece
	 * Each piece has it's own implementation of this. but calls on this parent function
	 * before it does its own 
	 * This parent version does a couple of things 
	 * 1) clears the existing locations arraylist created previously
	 * 2) sets the piece's visiblity to false (useful for checking if the piece is pinned)
	 * 3) if the gamestate is in checkresolution, a new locations arraylist will NOT be created
	 * this effectively prevents enemy pieces + allied pieces that cannot resolve the check from moving*/
	public void getMoveLocations(){

		locations.clear();
		this.setVisibility(false);
		if(GameBoard.gameState == 2) {
			return;
		}
	}; 
	
	
	public void checkhighlightLocation() {
	}
	
	public void clearcheckhighlightLocation() {
		
	}
	
	
	/** visual representation of where a piece can move, differentiates attacking
	 *  and movement */
	public void highlightLocation(){
		GameBoard.Board[row][column].setSquare(1);
		for(Square movable: locations) {
			GameBoard.Board[movable.getRow()][movable.getColumn()].setSquare(0);
			//if(movable.getCurrentPiece().getColor() != this.getColor() && movable.getCurrentPiece() != null) {
			//	GameBoard.Board[movable.getRow()][movable.getColumn()].setSquare("Attack");
			//}	
		}
	};
	
	public void unhighlightLocation(int row, int column) {
		GameBoard.Board[row][column].setSquare(129524512);
		for(Square movable: locations) {
			GameBoard.Board[movable.getRow()][movable.getColumn()].setSquare(51512511);
			//if(movable.getCurrentPiece().getColor() != this.getColor() && movable.getCurrentPiece() != null) {
			//	GameBoard.Board[movable.getRow()][movable.getColumn()].setSquare("Attack");
			//}	
		}
	};
	
	/** @return the piece's associated color
	 * */	
	public boolean getColor(){return color;};
	
	/**
	 * Precondition: getmoveLocations or a variant must be run before this
	 * Postcondition @return arraylist of where a piece can move
	 */
	public ArrayList<Square> returnLocations(){return locations;};
	
	/** getMoveLocation implementation for Rook, Bishop, Queen
	 *  Takes in
	 *  @param row, current row value of piece
	 *  @param col, current column value of piece
	 *  @param rowIncre, how much to increment the row by, e.g. if moving up one, -1
	 *  @param colIncre, how much to increment the col by
	 *  
	 *  Begins to check each spot in the direction indicated by rowIncre and colIncre
	 *  stops when it hits an enemy piece, an allied piece, or hits the border of
	 *  the chessboard
	 *  */
	
	public void recursion(int row, int col,int rowIncre, int colIncre) {
		
		int newRow = row + rowIncre;
		int newCol = col + colIncre;
		if( (newRow < 0 || newRow > 7 ) || ( newCol < 0 || newCol > 7 ) ) {
			return;
		}	
		
		if(GameBoard.Board[newRow][newCol].getCurrentPiece() != null) {
			if(GameBoard.Board[newRow][newCol].getCurrentPiece().getColor() == color) {		
				return;
			}
		}
		
		locations.add(new Square(newRow,newCol,GameBoard.Board[newRow][newCol].getCurrentPiece()));
		
		if(GameBoard.Board[newRow][newCol].getCurrentPiece() != null) {
			return;
		}
	
		recursion(row+rowIncre,col+colIncre, rowIncre, colIncre);
	}
	
	/** draw method draws the image associated with the piece*/
	public void draw(Graphics g)
	{
//		BufferedImage bi = new BufferedImage(400, 300, BufferedImage.TYPE_INT_ARGB);
		
		Graphics2D g2d = (Graphics2D)g;
		
		g2d.drawImage(image, (int) (width*0.1+column*width), (int)(height*0.1+row*height), (int)(width*0.8), (int)(height*0.8), null);
	}


 /*   public Dimension getPreferredSize() {
    	double x = image.getWidth() * 0.1;
    	double y = image.getHeight() * 0.1;
    	
    	int intx = (int) x;
    	int inty = (int) y;
        return image == null ? new Dimension(200, 200) : new Dimension(intx, inty);
    }*/

	
	/**
	 * Precondition an enemy piece has moved and is now capable of attacking the king, gameState has changed in checkResolution
	 * Postcondition all possible moves to resolve the check as been run and pieces have been updated accordingly unless checkmate has occurred 
	 * 
	 * This function examines how many pieces are attacking the king and based off of that creates a list of viable moves to resolve the check
	 * Two different checkResolutions
	 * 1) More than one piece is checking the king
	 * 		-> Run the king's getmovelocation
	 * 2) Only one piece is checking the king
	 * 		-> Run the king's getmovelocations
	 * 		->Iterate through all the allies and check whether or not they can move to save the king (calls a helper function to do so) 
	 */
	public void checkResolution() {
		((King)this).setcheckAttack(((King)this).getAttacking());	

		if(this.getColor()){ 
			((King)GameBoard.Board[GameBoard.Wk.getRow()][GameBoard.Wk.getColumn()].getCurrentPiece()).checkhighlightLocation();
		}
		else {
			((King)GameBoard.Board[GameBoard.Bk.getRow()][GameBoard.Bk.getColumn()].getCurrentPiece()).checkhighlightLocation();
		}
		ChessPiece attacker = ((King)this).getcheckAttack().get(0);
		//reset getMovelocations for both players

		for( Square piece : GameBoard.Player1.pieces) {
			GameBoard.Board[piece.getRow()][piece.getColumn()].getCurrentPiece().locations.clear();
		}
		for( Square piece :GameBoard.Player2.pieces) {
			GameBoard.Board[piece.getRow()][piece.getColumn()].getCurrentPiece().locations.clear();
		}
		

		if( ((King)this).getAttacking().size() > 1 ) {
			GameBoard.gameState = 2;
			this.getMoveLocations();
			if(this.checkmate(0)) {
				//Games over
				GameBoard.gameState = 3;
				GameBoard.setWinner(!this.getColor());
			}
			return;
		}
		
		
		if( ((King)this).getAttacking().size() == 1 ) {			
			//Save king.attacking, run attacking pieces getMoveLocations
			// Compare w/ allied pieces
			//OR King can move

			GameBoard.Board[attacker.row][attacker.column].getCurrentPiece().setVisibility(false);
			if(this.getColor()) {
				for( Square piece : GameBoard.Player1.pieces) {
					if(!(piece.getCurrentPiece() instanceof King)){
						this.checkResolutionAlliedPieces(attacker, piece.getCurrentPiece());
					}
				}
			}
			else {
				for( Square piece :GameBoard.Player2.pieces) {
					if(!(piece.getCurrentPiece() instanceof King)){
						this.checkResolutionAlliedPieces(attacker, piece.getCurrentPiece());
					}
				}
			}
			
		}

		GameBoard.gameState = 2;
		GameBoard.Board[attacker.row][attacker.column].getCurrentPiece().setVisibility(true);
		this.getMoveLocations();
		if(this.checkmate(1)) {
			//Games over
			System.out.println("GAME IS OVER");
			GameBoard.gameState = 3;
			GameBoard.setWinner(!this.getColor());
		}
	}
	
	
	
	/**
	 * Preconditions: The game is in checkResolution, only one piece is checking the king,
	 * Postconditions: all pieces have been evaluated and their locations array has been updated accordingly
	 * 
	 * @param originalAttacker the original piece attacking the king 
	 * @param Ally the ally that locations is being creates for (cannot be a king (the kings getmovelocations function will handle this))
	 * 
	 * What this function does is it determines what is attacking the king and what direction that piece is attacking for
	 * e.g. if it's a pawn/knight then you cannot bodyblock, you must kill the piece outright
	 * 
	 * 
	 */
	public void checkResolutionAlliedPieces(ChessPiece originalAttacker,ChessPiece Ally) {
		Ally.getMoveLocations();

		ArrayList<Square> modifiedLocations = new ArrayList<Square>();
		if(Ally instanceof King) {
			return;
		}

		if(originalAttacker instanceof Pawn) {
			for(Square movable :Ally.locations) {
				if(movable.getRow() == originalAttacker.row && movable.getColumn() == originalAttacker.column) {
					modifiedLocations.add(new Square(movable.getRow(),movable.getColumn(),originalAttacker));
				}
			}
			if(modifiedLocations.size() != 0) {
				((King)this).getSaviors().add(GameBoard.Board[Ally.row][Ally.column].getCurrentPiece());
			}
			Ally.locations.clear();
			GameBoard.Board[Ally.row][Ally.column].getCurrentPiece().locations.clear();
			GameBoard.Board[Ally.row][Ally.column].getCurrentPiece().locations = modifiedLocations;
			return;
		}
		
		//Horizontal Attacking
		if(originalAttacker.row == this.row && originalAttacker.column != this.column) {
			ArrayList<Integer> HoriCols = new ArrayList<Integer>();
			HoriCols.add(originalAttacker.column); HoriCols.add(this.column);
			Collections.sort(HoriCols);
			for(Square movable :Ally.locations) {
				if(movable.getRow() == originalAttacker.row && (HoriCols.get(0)  <= movable.getColumn() && movable.getColumn() <= HoriCols.get(1) ) 
						&& movable.getColumn() != this.column) {
					modifiedLocations.add(new Square(movable.getRow(),movable.getColumn(),GameBoard.Board[movable.getRow()][movable.getColumn()].getCurrentPiece()));
				}
			}
			if(modifiedLocations.size() != 0) {
				((King)this).getSaviors().add(GameBoard.Board[Ally.row][Ally.column].getCurrentPiece());
			}
			Ally.locations.clear();
			GameBoard.Board[Ally.row][Ally.column].getCurrentPiece().locations.clear();
			GameBoard.Board[Ally.row][Ally.column].getCurrentPiece().locations = modifiedLocations;
			return;
		}
		
		//Vertical Attacking
		if(originalAttacker.row != this.row && originalAttacker.column == this.column) {
			ArrayList<Integer> VertiCols = new ArrayList<Integer>();
			VertiCols.add(originalAttacker.row); VertiCols.add(this.row);
			Collections.sort(VertiCols);

			 
			for(Square movable :Ally.locations) {
				if(movable.getColumn() == originalAttacker.column && (VertiCols.get(0)  <= movable.getRow() && movable.getRow() <= VertiCols.get(1) ) 
					&& movable.getRow() != this.row) {
					modifiedLocations.add(new Square(movable.getRow(),movable.getColumn(),GameBoard.Board[movable.getRow()][movable.getColumn()].getCurrentPiece()));
				}
			}
      
			if(modifiedLocations.size() != 0) {
				((King)this).getSaviors().add(GameBoard.Board[Ally.row][Ally.column].getCurrentPiece());
			}
      
			Ally.locations.clear();
			GameBoard.Board[Ally.row][Ally.column].getCurrentPiece().locations.clear();
			GameBoard.Board[Ally.row][Ally.column].getCurrentPiece().locations = modifiedLocations;
			return;
		}
		//Diagonal Attacking (\\\\\\)

		if((originalAttacker.row - this.row) - (originalAttacker.column - this.column) == 0) {
			ArrayList<Integer> rows = new ArrayList<Integer>();
			ArrayList<Integer> cols = new ArrayList<Integer>();
			rows.add(originalAttacker.row); rows.add(this.row);
			cols.add(originalAttacker.column); cols.add(this.column);
			Collections.sort(rows);
			Collections.sort(cols);

			for(Square movable :Ally.locations) {
				if( ((movable.getRow() - this.row) - (movable.getColumn() - this.column) == 0) 
						&& movable.getRow() != this.row 
						&& movable.getColumn() != this.column
						&& (movable.getRow() >= rows.get(0) && movable.getRow() <= rows.get(1)) 
						&& (movable.getColumn() >= cols.get(0) && movable.getColumn() <= cols.get(1))) {
					modifiedLocations.add(new Square(movable.getRow(),movable.getColumn(),GameBoard.Board[movable.getRow()][movable.getColumn()].getCurrentPiece()));
				}
			}
			if(modifiedLocations.size() != 0) {
				((King)this).getSaviors().add(GameBoard.Board[Ally.row][Ally.column].getCurrentPiece());
			}
			Ally.locations.clear();
			GameBoard.Board[Ally.row][Ally.column].getCurrentPiece().locations.clear();
			GameBoard.Board[Ally.row][Ally.column].getCurrentPiece().locations = modifiedLocations;
			return;
		}
		
		//Diagonal Attacking (///////)
		if((originalAttacker.row - this.row) + (originalAttacker.column - this.column) == 0) {
			ArrayList<Integer> rows = new ArrayList<Integer>();
			ArrayList<Integer> cols = new ArrayList<Integer>();
			rows.add(originalAttacker.row); rows.add(this.row);
			cols.add(originalAttacker.column); cols.add(this.column);
			Collections.sort(rows);
			Collections.sort(cols);
			for(Square movable :Ally.locations) {
				if( ((movable.getRow() - this.row) + (movable.getColumn() - this.column) == 0) 
						&& movable.getRow() != this.row 
						&& movable.getColumn() != this.column
						&& (movable.getRow() >= rows.get(0) && movable.getRow() <= rows.get(1)) 
						&& (movable.getColumn() >= cols.get(0) && movable.getColumn() <= cols.get(1))) {
					modifiedLocations.add(new Square(movable.getRow(),movable.getColumn(),GameBoard.Board[movable.getRow()][movable.getColumn()].getCurrentPiece()));
				}
			}
			if(modifiedLocations.size() != 0) {
				((King)this).getSaviors().add(GameBoard.Board[Ally.row][Ally.column].getCurrentPiece());
			}
			Ally.locations.clear();
			GameBoard.Board[Ally.row][Ally.column].getCurrentPiece().locations.clear();
			GameBoard.Board[Ally.row][Ally.column].getCurrentPiece().locations = modifiedLocations;
			return;
		}
		
		//Knight
		if(originalAttacker instanceof Knight) {
			for(Square movable :Ally.locations) {
				if(movable.getRow() == originalAttacker.row && movable.getColumn() == originalAttacker.column) {
					modifiedLocations.add(new Square(movable.getRow(),movable.getColumn(),originalAttacker));
				}
			}
			if(modifiedLocations.size() != 0) {
				((King)this).getSaviors().add(GameBoard.Board[Ally.row][Ally.column].getCurrentPiece());
			}
			Ally.locations.clear();
			GameBoard.Board[Ally.row][Ally.column].getCurrentPiece().locations.clear();
			GameBoard.Board[Ally.row][Ally.column].getCurrentPiece().locations = modifiedLocations;
			return;
		}
	}
	
	/**
	 * 
	 * @param condition, 0 = more than one piece is checking the king , 1 = only one piece is checking the king
	 * @return true if checkmate, false if a piece can resolve
	 * 
	 * Checking for checkmate
	 * 1) if the king is being checked by more than one enemy piece, an allied piece cannot save the king 
	 * if the king cannot move as well, then checkmate has occurred
	 * 2) if the king is only being checked by one enemy piece, then an allied piece may bodyblock for the king or the king can move
	 * 	if neither of these options are valid, checkmate has occurred
	 */
	public boolean checkmate(int condition) {
		if(condition == 0 ) {
			if(locations.size() == 0) {
				return true;
			}
			else {
				return false;
			}
		}
		
		else {
			//AND no pieces can save the king then gg
			if(locations.size() == 0 && ((King)this).getSaviors().size() == 0) {
				return true;
			}
			else {
				return false;
			}
		}
	}
	
	/**
	 * Checks if the king is in check, called after every move function finishes, if the king is checked sets isChecked to true
	 * @param color, true for white king, false for black king
	 */
	public void checkKing(boolean color) {
		//new addition
		GameBoard.graphBoard.resetMousePressed();
		
		for( Square piece :GameBoard.Player2.pieces) {		
			GameBoard.Board[piece.getRow()][piece.getColumn()].getCurrentPiece().unhighlightLocation(piece.getRow(), piece.getColumn());
		}
		for( Square piece :GameBoard.Player1.pieces) {		
			GameBoard.Board[piece.getRow()][piece.getColumn()].getCurrentPiece().unhighlightLocation(piece.getRow(), piece.getColumn());
		}
		
		checkSound();
		
		if(color == true) {
			System.out.println("YO THE WHITE KING IS IN CHECK");
			((King)GameBoard.Board[GameBoard.Wk.getRow()][GameBoard.Wk.getColumn()].getCurrentPiece()).isChecked = true;
			GameBoard.setChecked(1);
		}
		
		else{
			System.out.println("YO THE BLACK KING IS IN CHECK");
			((King)GameBoard.Board[GameBoard.Bk.getRow()][GameBoard.Bk.getColumn()].getCurrentPiece()).isChecked = true;
			GameBoard.setChecked(0);
		}
		
		A_Clock.pause();

		return;
	}
	
	/**
	 * this function checks whether or not a piece is pinned, called in getmovelocations and after the original getmovelocation is created
	 * the piece moving will make itself invisible and calls on the king's check function, if it is check then the piece is considered pinned
	 * 
	 * Precondition: getmovelocations has been called
	 * Postcondition 
	 * @return returns true if a piece is pinned (moving will cause allied king to go into check)
	 */
	public boolean checkpinnedPiece() {
		if(color == true) {

			GameBoard.Board[GameBoard.Wk.getRow()][GameBoard.Wk.getColumn()].getCurrentPiece().setVisibility(false);

			if(checkSquare(GameBoard.Wk.getRow(), GameBoard.Wk.getColumn())) {
				GameBoard.Board[GameBoard.Wk.getRow()][GameBoard.Wk.getColumn()].getCurrentPiece().setVisibility(true);
				return true;
			}	
			else {
				GameBoard.Board[GameBoard.Wk.getRow()][GameBoard.Wk.getColumn()].getCurrentPiece().setVisibility(true);
				return false;
			}
		}
		
		else {
			GameBoard.Board[GameBoard.Bk.getRow()][GameBoard.Bk.getColumn()].getCurrentPiece().setVisibility(false);
			
			if(checkSquare(GameBoard.Bk.getRow(), GameBoard.Bk.getColumn())) {
				GameBoard.Board[GameBoard.Bk.getRow()][GameBoard.Bk.getColumn()].getCurrentPiece().setVisibility(true);
				return true;
			}
			else {
				GameBoard.Board[GameBoard.Bk.getRow()][GameBoard.Bk.getColumn()].getCurrentPiece().setVisibility(true);
				return false;
			}
		}
	}
	
	/**
	 * This function is called when a piece is pinned, it looks at the locations array of the piece and will modify it accordingly
	 * based on what is attacking the king should it move, 
	 * e.g. if a rook is attacking vertically then a pinned piece can move along the same row the enemy rook is
	 * 
	 * Precondition : checkpinnedPiece has been called and has returned true, getMovelocations has been called and a locations array has
	 * been created
	 * Postcondition: locations array has been modified to include only moves that would not check the king 
	 */
	public void pinnedPieceMovementHelper() {
		ArrayList<Square> modifiedLocations = new ArrayList<Square>();

		if(color == true ) {

			for( ChessPiece attack : ((King)GameBoard.Board[GameBoard.Wk.getRow()][GameBoard.Wk.getColumn()].getCurrentPiece()).getAttacking()) {
				attack.getMoveLocations();
				
				if(attack instanceof Knight) {
					for(int i = 0; i < locations.size() ; i++) {
						if(locations.get(i).getRow() == attack.row && attack.column == locations.get(i).getColumn()) {
							modifiedLocations.add(new Square(locations.get(i).getRow(),locations.get(i).getColumn()));														
						}
					}
				}
				
				//Checks if the attacker is a rook
				if(attack instanceof Rook) {
					//Checks the direction the rook is attacking from, Horizontally
					if(attack.row == this.row && attack.column != this.column) {
						for(int i = 0; i < locations.size() ; i++) {
							if(locations.get(i).getRow() == attack.row) {
								modifiedLocations.add(new Square(locations.get(i).getRow(),locations.get(i).getColumn()));														
							}
						}
					}
					//Vertically
					if(attack.row != this.row && attack.column == this.column) {
						for(int i = 0; i < locations.size() ; i++) {
							if(attack.column == locations.get(i).getColumn()) {
								modifiedLocations.add(new Square(locations.get(i).getRow(),locations.get(i).getColumn()));														
							}
						}
					}
						
				}
					
				if(attack instanceof Bishop) {
					// THIS WAY \\\\\\
					if(((attack.row-this.row)-(attack.column-this.column)) == 0) {
						for(int i = 0; i < locations.size() ; i++) {
							if( ( (locations.get(i).getRow()-attack.row) - (locations.get(i).getColumn()-attack.column) ) == 0) {
								modifiedLocations.add(new Square(locations.get(i).getRow(),locations.get(i).getColumn()));														
							}
						}	
					}
					//THIS WAY /////
					if(((attack.row-this.row)+(attack.column-this.column)) ==0) {	
						for(int i = 0; i < locations.size() ; i++) {
								if(((locations.get(i).getRow()-attack.row) + (locations.get(i).getColumn()-attack.column)) == 0) {
									modifiedLocations.add(new Square(locations.get(i).getRow(),locations.get(i).getColumn()));														
								}
							}
						}
					}
					
					//essentially just uses the bishop + rook implementations
					if(attack instanceof Queen) {
						// THIS WAY \\\\\\
						if(((attack.row-this.row)-(attack.column-this.column)) == 0) {
							for(int i = 0; i < locations.size() ; i++) {
								if( ( (locations.get(i).getRow()-attack.row) - (locations.get(i).getColumn()-attack.column) ) == 0) {
									modifiedLocations.add(new Square(locations.get(i).getRow(),locations.get(i).getColumn()));														
								}
							}	
						}
						//THIS WAY /////
						if(((attack.row-this.row)+(attack.column-this.column)) ==0) {	
							for(int i = 0; i < locations.size() ; i++) {
									if(((locations.get(i).getRow()-attack.row) + (locations.get(i).getColumn()-attack.column)) == 0) {
										modifiedLocations.add(new Square(locations.get(i).getRow(),locations.get(i).getColumn()));														
									}
							}
						}
						
						
						if(attack.row == this.row && attack.column != this.column) {
							for(int i = 0; i < locations.size() ; i++) {
								if(locations.get(i).getRow() == attack.row ) {
									modifiedLocations.add(new Square(locations.get(i).getRow(),locations.get(i).getColumn()));														
								}
							}
						}
						if(attack.row != this.row && attack.column == this.column) {
							for(int i = 0; i < locations.size() ; i++) {
								if(locations.get(i).getColumn() == attack.column) {
									modifiedLocations.add(new Square(locations.get(i).getRow(),locations.get(i).getColumn()));														
								}
							}
						}
					}
			}
		}
		//Same thing but for black
		if(color == false ) {
			for( ChessPiece attack : ((King)GameBoard.Board[GameBoard.Bk.getRow()][GameBoard.Bk.getColumn()].getCurrentPiece()).getAttacking()) {
				attack.getMoveLocations();
				
				if(attack instanceof Knight) {
					for(int i = 0; i < locations.size() ; i++) {
						if(locations.get(i).getRow() == attack.row && attack.column == locations.get(i).getColumn()) {
							modifiedLocations.add(new Square(locations.get(i).getRow(),locations.get(i).getColumn()));														
						}
					}
				}
				
				if(attack instanceof Rook) {
					if(attack.row == this.row && attack.column != this.column) {
						for(int i = 0; i < locations.size() ; i++) {
							if(locations.get(i).getRow() == attack.row ) {
								modifiedLocations.add(new Square(locations.get(i).getRow(),locations.get(i).getColumn()));														
							}
						}
					}
					if(attack.row != this.row && attack.column == this.column) {
						for(int i = 0; i < locations.size() ; i++) {
							if( attack.column == locations.get(i).getColumn()) {
								modifiedLocations.add(new Square(locations.get(i).getRow(),locations.get(i).getColumn()));														
							}
						}
					}
						
				}
					
				if(attack instanceof Bishop) {
					// THIS WAY \\\\\\
					if(((attack.row-this.row)-(attack.column-this.column)) == 0) {
						for(int i = 0; i < locations.size() ; i++) {
							if( ( (locations.get(i).getRow()-attack.row) - (locations.get(i).getColumn()-attack.column) ) == 0) {
								modifiedLocations.add(new Square(locations.get(i).getRow(),locations.get(i).getColumn()));														
							}
						}	
					}
					//THIS WAY /////
					if(((attack.row-this.row)+(attack.column-this.column)) ==0) {	
						for(int i = 0; i < locations.size() ; i++) {
								if(((locations.get(i).getRow()-attack.row) + (locations.get(i).getColumn()-attack.column)) == 0) {
									modifiedLocations.add(new Square(locations.get(i).getRow(),locations.get(i).getColumn()));														
								}
							}
						}
					}
					
					if(attack instanceof Queen) {
						// THIS WAY \\\\\\
						if(((attack.row-this.row)-(attack.column-this.column)) == 0) {
							for(int i = 0; i < locations.size() ; i++) {
								if( ( (locations.get(i).getRow()-attack.row) - (locations.get(i).getColumn()-attack.column) ) == 0) {
									modifiedLocations.add(new Square(locations.get(i).getRow(),locations.get(i).getColumn()));														
								}
							}	
						}
						//THIS WAY /////
						if(((attack.row-this.row)+(attack.column-this.column)) ==0) {							
							for(int i = 0; i < locations.size() ; i++) {
									if(((locations.get(i).getRow()-attack.row) + (locations.get(i).getColumn()-attack.column)) == 0) {
										modifiedLocations.add(new Square(locations.get(i).getRow(),locations.get(i).getColumn()));														
									}
							}
						}
						
						
						if(attack.row == this.row && attack.column != this.column) {
							for(int i = 0; i < locations.size() ; i++) {
								if(locations.get(i).getRow() == attack.row) {
									modifiedLocations.add(new Square(locations.get(i).getRow(),locations.get(i).getColumn()));														
								}
							}
						}
						if( attack.row != this.row && attack.column == this.column) {
							for(int i = 0; i < locations.size() ; i++) {
								if(attack.column == locations.get(i).getColumn()) {
									modifiedLocations.add(new Square(locations.get(i).getRow(),locations.get(i).getColumn()));														
								}
							}
						}
					}
			}
		}
		
		locations.clear();
		locations = modifiedLocations;
	}
	
			
	/**
	 * This function checks whether or not a square is under attack by an enemy piece, it does this by checked every direction a piece can attack from
	 *  
	 * 
	 * @param row, row to check
	 * @param column, column to check
	 * @return boolean that indicates whether or not that space is being attacked or not
	 */
			
	public boolean checkSquare(int row, int column) {
		ArrayList<ChessPiece> otherPieces = new ArrayList<ChessPiece>();
		
		otherPieces.clear();
		otherPieces = checkHorseHelper(row, column);
		
		
		checkPieceHorizontal(row, column, 1, column , 0, 0 ,otherPieces);
		checkPieceHorizontal(row, column, -1, column , 0, 0,otherPieces);
		
		checkPieceVertical(row, column, 1, row , 0, 0 ,otherPieces);
		checkPieceVertical(row, column, -1, row , 0, 0 ,otherPieces);

		checkPieceDiagonals(row, column, 1,  1,  row,  column, 0, 0 , otherPieces,null);
		checkPieceDiagonals(row, column, -1,  1,  row,  column, 0, 0 , otherPieces,null);
		checkPieceDiagonals(row, column, 1,  -1,  row,  column, 0, 0 , otherPieces,null);
		checkPieceDiagonals(row, column, -1,  -1,  row,  column, 0, 0 , otherPieces,null);
		
		if(color == true) {
			((King)GameBoard.Board[GameBoard.Wk.getRow()][GameBoard.Wk.getColumn()].getCurrentPiece()).setAttacking(otherPieces);

		}
		
		else {
			((King)GameBoard.Board[GameBoard.Bk.getRow()][GameBoard.Bk.getColumn()].getCurrentPiece()).setAttacking(otherPieces);
		}	
	
		if(otherPieces.size() == 0) {
			return false;
		}
			
		else {
			return true;
		}
		
	}
	
	/**
	 * This is a helper function that is called in checkSquare, recursively finds enemy pieces in the vertical direction
	 * PreCondition: checkSquare is called
	 * Postcondition: any enemy pieces found have been added to arraylist OtherPieces
	 * 
	 * @param row, row of square to check
	 * @param col, col of square to check
	 * @param rowIncre, how much to increment the row by
	 * @param oriRow, the original row
	 * @param piecesFound, how many pieces that have been found 
	 * @param spacesMoved, applicable if the original square was the king
	 * @param otherPieces, arraylist that stores attacking pieces
	 */
	public void checkPieceVertical(int row, int col, int rowIncre, int oriRow, int piecesFound, int spacesMoved ,ArrayList<ChessPiece> otherPieces) {
		if( (row < 0 || row > 7 ) || ( col < 0 || col > 7 ) ) {
			return;
		}
		
		if(GameBoard.Board[row][col].getCurrentPiece() != null) {
			if(GameBoard.Board[row][col].getCurrentPiece().getColor() == color 
					&& GameBoard.Board[row][col].getCurrentPiece().getVisibility() == true 
					&& !GameBoard.Board[row][col].getCurrentPiece().equals(this)) {	
				return;
			}
		}

		if(GameBoard.Board[row][col].getCurrentPiece() != null) {
			if(GameBoard.Board[row][col].getCurrentPiece().getColor() == color 
					&& GameBoard.Board[row][col].getCurrentPiece().equals(this)) {	
				spacesMoved++;
			}
		}
		if(GameBoard.Board[row][col].getCurrentPiece() != null) {
			if(GameBoard.Board[row][col].getCurrentPiece().getColor() != color 
					&& GameBoard.Board[row][col].getCurrentPiece().getVisibility() == true) {
				if( Math.floor(Math.hypot((this.row-row), (this.column-col))) <= 1.0 && piecesFound < 1 && spacesMoved != 1 && oriRow == row) {
					piecesFound++;
					if( (GameBoard.Board[row][col].getCurrentPiece() instanceof Queen || GameBoard.Board[row][col].getCurrentPiece() instanceof Rook)) {
						//King is in check
					}
				}

				else if( Math.abs((oriRow-row)) == 1.0 && piecesFound < 1 ) {
					piecesFound++;
					if( GameBoard.Board[row][col].getCurrentPiece() instanceof King || GameBoard.Board[row][col].getCurrentPiece() instanceof Queen 
							|| GameBoard.Board[row][col].getCurrentPiece() instanceof Rook ) {
						otherPieces.add(GameBoard.Board[row][col].getCurrentPiece());

						return;
					}		
					
					else {
						return;
					}
				}
				
				else {
					if(piecesFound == 0) {
						if( (GameBoard.Board[row][col].getCurrentPiece() instanceof Queen || GameBoard.Board[row][col].getCurrentPiece() instanceof Rook)) {
							otherPieces.add(GameBoard.Board[row][col].getCurrentPiece());
							return;
						}
						
						else {
							return;
						}
						
					}
					if(piecesFound == 1) {

						if( (GameBoard.Board[row][col].getCurrentPiece() instanceof Queen || GameBoard.Board[row][col].getCurrentPiece() instanceof Rook)) {
							otherPieces.add(GameBoard.Board[row][col].getCurrentPiece());
							return;
						}
						else {
							return;
						}
					}
				}
			}
		}

		checkPieceVertical(row+rowIncre, col, rowIncre, oriRow, piecesFound, spacesMoved ,otherPieces);
		return ;
		
		
	}

	
	/**
	 * This is a helper function that is called in checkSquare, recursively finds enemy pieces in the horizontal direction
	 * PreCondition: checkSquare is called
	 * Postcondition: any enemy pieces found have been added to arraylist OtherPieces
	 * 
	 * @param row, row of square to check
	 * @param col, col of square to check
	 * @param colIncre, how much to increment the col by
	 * @param oriRow, the original row
	 * @param piecesFound, how many pieces that have been found 
	 * @param spacesMoved, applicable if the original square was the king
	 * @param otherPieces, arraylist that stores attacking pieces
	 */
	public void checkPieceHorizontal(int row, int col, int colIncre, int oriCol,int piecesFound, int spacesMoved ,ArrayList<ChessPiece> otherPieces) {
		
		
		if( (row < 0 || row > 7 ) || ( col < 0 || col > 7 ) ) {
			return;
		}
		
		if(GameBoard.Board[row][col].getCurrentPiece() != null) {
			if(GameBoard.Board[row][col].getCurrentPiece().getColor() == color 
					&& GameBoard.Board[row][col].getCurrentPiece().getVisibility() == true 
					&& !GameBoard.Board[row][col].getCurrentPiece().equals(this)) {	
				return;
			}
		}
		
		if(GameBoard.Board[row][col].getCurrentPiece() != null) {
			if(GameBoard.Board[row][col].getCurrentPiece().getColor() == color 
					&& GameBoard.Board[row][col].getCurrentPiece().equals(this)) {	
				spacesMoved++;
			}
		}
		
		if(GameBoard.Board[row][col].getCurrentPiece() != null) {
			if(GameBoard.Board[row][col].getCurrentPiece().getColor() != color
					&& GameBoard.Board[row][col].getCurrentPiece().getVisibility() == true) {
				if( Math.floor(Math.hypot((this.row-row), (this.column-col))) <= 1.0 && piecesFound < 1 && oriCol == col) {
					piecesFound++;

					if( (GameBoard.Board[row][col].getCurrentPiece() instanceof Queen || GameBoard.Board[row][col].getCurrentPiece() instanceof Rook)) {
						//King is in check
					}
				}
				
				else if( ( Math.abs(oriCol-col) ) == (1.0) && piecesFound < 1 ) {
					piecesFound++;
					if( GameBoard.Board[row][col].getCurrentPiece() instanceof King || GameBoard.Board[row][col].getCurrentPiece() instanceof Queen 
							|| GameBoard.Board[row][col].getCurrentPiece() instanceof Rook ) {
						otherPieces.add(GameBoard.Board[row][col].getCurrentPiece());
						return;
					}		
					else {
						return;
					}
				}
				
				else {
					if(piecesFound == 0) {
						if( (GameBoard.Board[row][col].getCurrentPiece() instanceof Queen || GameBoard.Board[row][col].getCurrentPiece() instanceof Rook)) {
							otherPieces.add(GameBoard.Board[row][col].getCurrentPiece());
							return;
						}
						
						else {
							return;
						}
						
					}
					if(piecesFound == 1) {
						if( (GameBoard.Board[row][col].getCurrentPiece() instanceof Queen || GameBoard.Board[row][col].getCurrentPiece() instanceof Rook)) {
							otherPieces.add(GameBoard.Board[row][col].getCurrentPiece());
							return;
						}
						else {
							return;
						}
					}
				}
			}
		}
		
		checkPieceHorizontal(row, col+colIncre, colIncre, oriCol, piecesFound, spacesMoved ,otherPieces);
		return ;
		
		
	}
	
	/**
	 * This is a helper function that is called in checkSquare, recursively finds enemy pieces in the diagonal direction
	 * PreCondition: checkSquare is called
	 * Postcondition: any enemy pieces found have been added to arraylist OtherPieces
	 * 
	 * @param row of square that is being checked
	 * @param col of square that is being checked
	 * @param rowIncre how much to increment the row for
	 * @param colIncre how much to increment the col for
	 * @param oriRow the original row that the recursive function started on
	 * @param oriCol the original col that that recursive function started on
	 * @param piecesFound how many pieces the function has found
	 * @param spacesMoved important when the original row and column start on the king
	 * @param otherPieces arraylist that enemy pieces are added to
	 */
	public void checkPieceDiagonals(int row, int col,int rowIncre, int colIncre, int oriRow, int oriCol,int piecesFound,int spacesMoved ,ArrayList<ChessPiece> otherPieces, ChessPiece found) {
		
		if( (row < 0 || row > 7 ) || ( col < 0 || col > 7 ) ) {
			return;
		}
		
		if(GameBoard.Board[row][col].getCurrentPiece() != null) {
			if(GameBoard.Board[row][col].getCurrentPiece().getColor() == color 
					&& GameBoard.Board[row][col].getCurrentPiece().getVisibility() == true
					&& !GameBoard.Board[row][col].getCurrentPiece().equals(this)) {	
				return;
			}
		}
		
		if(GameBoard.Board[row][col].getCurrentPiece() != null) {
			if(GameBoard.Board[row][col].getCurrentPiece().getColor() == color 
					&& GameBoard.Board[row][col].getCurrentPiece().equals(this)) {	
				spacesMoved++;
			}
		}
		
		if(GameBoard.Board[row][col].getCurrentPiece() != null) {
			if(GameBoard.Board[row][col].getCurrentPiece().getColor() != color
					&& GameBoard.Board[row][col].getCurrentPiece().getVisibility() == true) {
				if( (Math.hypot((this.row-row), (this.column-col))) == Math.sqrt(2.0) && piecesFound < 1 && spacesMoved != 1 && oriCol == col && oriRow == row) {
					piecesFound++;
					found = GameBoard.Board[row][col].getCurrentPiece();
					if( (GameBoard.Board[row][col].getCurrentPiece() instanceof Queen || GameBoard.Board[row][col].getCurrentPiece() instanceof Bishop 
							|| GameBoard.Board[row][col].getCurrentPiece() instanceof King)) {
						//King is in check
					}
					if( GameBoard.Board[row][col].getCurrentPiece() instanceof Pawn) {
						if(color == true) {
							if(rowIncre == 1) {
								//King is in check
							}
						}
						
						if (color == false) {
							if(rowIncre == -1) {
								//King is in check
							}
						}
					}
				}
				
				else if((Math.hypot((this.row-row), (this.column-col))) == 1.0 && piecesFound < 1 && spacesMoved != 1 && oriCol == col && oriRow == row) {
					piecesFound++;
					found = GameBoard.Board[row][col].getCurrentPiece();
				}

				
				else if( (Math.hypot((oriRow-row), (oriCol-col))) == Math.sqrt(2.0) && piecesFound < 1 ) {
						piecesFound++;
						found = GameBoard.Board[row][col].getCurrentPiece();
						if( (GameBoard.Board[row][col].getCurrentPiece() instanceof Queen || GameBoard.Board[row][col].getCurrentPiece() instanceof Bishop 
								|| GameBoard.Board[row][col].getCurrentPiece() instanceof King)) {
							otherPieces.add(GameBoard.Board[row][col].getCurrentPiece());
							return;
						}
						
						if( GameBoard.Board[row][col].getCurrentPiece() instanceof Pawn) {
							if(color == true) {
								if( (oriRow - row) > 0) {
									otherPieces.add(GameBoard.Board[row][col].getCurrentPiece());
									return;
								}
							}
							
							if (color == false) {
								if( (oriRow - row) < 0) {
									otherPieces.add(GameBoard.Board[row][col].getCurrentPiece());
									return;
								}
							}
						}
						else {
							return;
						}
				}
				
				else {
					if(piecesFound == 0) {
						if( (GameBoard.Board[row][col].getCurrentPiece() instanceof Queen || GameBoard.Board[row][col].getCurrentPiece() instanceof Bishop)) {
							otherPieces.add(GameBoard.Board[row][col].getCurrentPiece());
						}
						
						else {
							return;
						}		
					}
					if(piecesFound == 1) {
						if( (GameBoard.Board[row][col].getCurrentPiece() instanceof Queen || GameBoard.Board[row][col].getCurrentPiece() instanceof Bishop)) {
							otherPieces.add(GameBoard.Board[row][col].getCurrentPiece());
							return;
						}
						else if(GameBoard.Board[row][col].getCurrentPiece() instanceof Pawn) {
							if(color){
								//row ++
								if( (found.row == row + 1 && found.column == col -1) || (found.row == row + 1 && found.column == col + 1)) {
									otherPieces.add(GameBoard.Board[row][col].getCurrentPiece());
								}
							}
							//row --
							else {
								if( (found.row == row - 1 && found.column == col -1) || (found.row == row - 1 && found.column == col + 1)) {
									otherPieces.add(GameBoard.Board[row][col].getCurrentPiece());
								}
							}
							return;
						}
						else {
							return;
						}
					}
				}
			}
		}
		checkPieceDiagonals(row+rowIncre, col+colIncre, rowIncre ,colIncre,oriRow, oriCol, piecesFound, spacesMoved ,otherPieces, found);
		return; 
	}
		
	/**
	 * This is a helper function that is called in checkSquare,  finds enemy horses
	 * PreCondition: checkSquare is called
	 * Postcondition: any enemy horses found have been added to arraylist OtherPieces 
	 * @param row  to check
	 * @param column to check
	 * @return arraylist full of enemy horses
	 */
	public ArrayList<ChessPiece> checkHorseHelper(int row, int column) {
		ArrayList<ChessPiece> EnemyPieces = new ArrayList<ChessPiece>();
		if( (row +2 >= 0 && row +2 < 8 ) && ( column +1 >= 0 && column +1 < 8 )) {
			if ( GameBoard.Board[row+2][column+1].getCurrentPiece() != null) { 				
				if (!(GameBoard.Board[row+2][column+1].getCurrentPiece().getColor() == color) 
						&& (GameBoard.Board[row+2][column+1].getCurrentPiece() instanceof Knight)) {	
					EnemyPieces.add(GameBoard.Board[row+2][column+1].getCurrentPiece()); 
				}
			}
		}
		
		if( (row +2 >= 0 && row +2 < 8 ) && ( column -1 >= 0 && column -1 < 8 )) {
			if ( GameBoard.Board[row+2][column-1].getCurrentPiece() != null) { 				
				if (!(GameBoard.Board[row+2][column-1].getCurrentPiece().getColor() == color) 
						&& (GameBoard.Board[row+2][column-1].getCurrentPiece() instanceof Knight)) {
					EnemyPieces.add(GameBoard.Board[row+2][column-1].getCurrentPiece());
				}
			}
		}
		
		if( (row -2 >= 0 && row -2 < 8 ) && ( column +1 >= 0 && column +1 < 8 )) {
			if ( GameBoard.Board[row-2][column+1].getCurrentPiece() != null) { 				
				if (!(GameBoard.Board[row-2][column+1].getCurrentPiece().getColor() == color) 
						&& (GameBoard.Board[row-2][column+1].getCurrentPiece() instanceof Knight)) {
					EnemyPieces.add(GameBoard.Board[row-2][column+1].getCurrentPiece());
				}
			}
		}
		
		if( (row -2 >= 0 && row -2 < 8 ) && ( column -1 >= 0 && column -1 < 8 )) {
			if ( GameBoard.Board[row-2][column-1].getCurrentPiece() != null) { 			
				if (!(GameBoard.Board[row-2][column-1].getCurrentPiece().getColor() == color)
						&& ((GameBoard.Board[row-2][column-1].getCurrentPiece() instanceof Knight))) {
					EnemyPieces.add(GameBoard.Board[row-2][column-1].getCurrentPiece());
				}
			}
		}
		
		if( (row +1 >= 0 && row +1 < 8 ) && ( column -2 >= 0 && column -2 < 8 )) {
			if ( GameBoard.Board[row+1][column-2].getCurrentPiece() != null) { 				
				if (!(GameBoard.Board[row+1][column-2].getCurrentPiece().getColor() == color) 
						&& ((GameBoard.Board[row+1][column-2].getCurrentPiece() instanceof Knight))) {
					EnemyPieces.add(GameBoard.Board[row+1][column-2].getCurrentPiece());
				}
			}
		}
		
		if( (row +1 >= 0 && row +1 < 8 ) && ( column +2 >= 0 && column +2 < 8 )) {
			if ( GameBoard.Board[row+1][column+2].getCurrentPiece() != null) { 				
				if (!(GameBoard.Board[row+1][column+2].getCurrentPiece().getColor() == color) 
						&& ((GameBoard.Board[row+1][column+2].getCurrentPiece() instanceof Knight))) {
					EnemyPieces.add(GameBoard.Board[row+1][column+2].getCurrentPiece());
				}
			}
		}

		if( (row -1 >= 0 && row -1 < 8 ) && ( column -2 >= 0 && column -2 < 8 )) {
			if ( GameBoard.Board[row-1][column-2].getCurrentPiece() != null) { 				
				if (!(GameBoard.Board[row-1][column-2].getCurrentPiece().getColor() == color) 
						&& (GameBoard.Board[row-1][column-2].getCurrentPiece() instanceof Knight)) {
					EnemyPieces.add(GameBoard.Board[row-1][column-2].getCurrentPiece());
				}
			}
		}
		
		if( (row -1 >= 0 && row -1 < 8 ) && ( column +2 >= 0 && column +2 < 8 )) {
			if ( GameBoard.Board[row-1][column+2].getCurrentPiece() != null) { 				
				if (!(GameBoard.Board[row-1][column+2].getCurrentPiece().getColor() == color) 
						&& ((GameBoard.Board[row-1][column+2].getCurrentPiece() instanceof Knight))) {
					EnemyPieces.add(GameBoard.Board[row-1][column+2].getCurrentPiece());
				}
			}
		}
		
		return EnemyPieces;
	}
	
	protected void checkSound() {
		checkSound.playClip();   	            
	}

	/**
	 * sets a pieces visibility relative to other pieces
	 * 
	 * @param Visibility, true to set the piece to isVisible, false if the piece is invisible
	 */
	public void setVisibility(boolean Visibility) {
		isVisible = Visibility;
	}
	
	/**
	 * 
	 * @return the visibility of the piece, true if yes, false if no
	 */
	public boolean getVisibility() {
		return isVisible;
	}
	
	@Override
	public String toString() {
		return color + " " + name + " row: " + row + " col: " +column + " ";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (color ? 1231 : 1237);
		result = prime * result + column;
		result = prime * result + (isVisible ? 1231 : 1237);
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + row;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ChessPiece other = (ChessPiece) obj;
		if (color != other.color)
			return false;
		if (column != other.column)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (row != other.row)
			return false;
		return true;
	}
	
}
