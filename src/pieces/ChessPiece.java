package pieces;

import game.*;
import pieces.*;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.math.*;



/** This is the class that every piece inherits from
 * @author Alexander Ngo, Isaac Fu
 *
 */

/*WHAT WE HAVE TO IMPLEMENT
Pawns - promotion.
King- Castling, Checking, Checkmate(if another piece can stop the checkmate)
Move - mouse Listener + updating gameboard
Highlight location with Square
*/


public class ChessPiece
{	
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
	private int width, height;
	public String name;
	
	/**
	 * Generic constructor to initialize piece on top on the Graphics and GameBoard 
	 * @param row, row that the piece will be created in
	 * @param column, column that the piece will be created in
	 * @param color, Black or White the team the piece is associated with 
	 */
	
	public ChessPiece(int row, int column, boolean color) {
		this.row = row;
		this.column = column;
		this.color = color;
		width = GraphicsBoard.WIDTH/8;
		height = GraphicsBoard.HEIGHT/8;
		 
	}; 
	
	/**  returns if the the piece has moved before useful for Castling and Pawns */
	public boolean gethasMoved() {
		return hasMoved;
	}
	
	/** Called when a piece first moves */
	
	public void sethasMoved(boolean setter) {
		hasMoved = setter;
	}
	
	/** Only invoked when the king initiates a Castle, special move function */
	public void CastleMove(int row, int column){
		GameBoard.Board[row][column].setCurrentPiece(this);
		this.row = row;
		this.column = column;
		hasMoved = true;
	}
	
	/** Function that actually moves the pieces, children all call this first 
	 *  in their own respective move functions */
	
	public void move(int row, int column){
		locations.clear();

		getMoveLocations();
		boolean canMove = false;
		for(Square movable: locations)	{
			if(	(row == movable.getRow()) && (column == movable.getColumn()))	{
				canMove = true;
			}			
		}
		if(canMove)	{
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
			GameBoard.Board[row][column].setCurrentPiece(this);
			GameBoard.Board[this.row][this.column].setCurrentPiece(null);
			this.row = row;
			this.column = column;
			this.sethasMoved(true);

			//			check()
		}
		else{
			//invalid movable location... throw an error? idk
		}
	};
	
	/** Unused for now */
	public void attack(ChessPiece Enemy){};
	
	/** Called when a piece is successfully attacked */
	public void die(){status = false;};
	
	/** Creates a list of all possible movement locations for that current piece*/
	public void getMoveLocations(){}; 
	
	/** visual representation of where a piece can move, differentiates attacking
	 *  and movement */
	public void highightLocation(){
		getMoveLocations();
		for(Square movable: locations) {
			GameBoard.Board[movable.getRow()][movable.getColumn()].setLight("Move");
			if(movable.getCurrentPiece().getColor() != this.getColor() && movable.getCurrentPiece() != null) {
				GameBoard.Board[movable.getRow()][movable.getColumn()].setLight("Attack");
			}	
		
	}
	};
	
	//I have no clue
	public Square sendAttackSpot(){
		getMoveLocations();
		Square kappa = new Square();
		for(Square movable: locations) {
			if(movable.getCurrentPiece().getColor() != this.getColor() && movable.getCurrentPiece() != null) {
			}
			return movable;
		}
		return kappa;
		};
	
	/** returns the piece's associated color*/	
	public boolean getColor(){return color;};
	
	// ????
	public ArrayList<Square> returnLocations(){return locations;};
	
	/** getMoveLocation implementation for Rook, Bishop, Queen
	 *  Takes in
	 *  @param row, current row value of piece
	 *  @param col, current column value of piece
	 *  @param rowIncre, how much to increment the row by, e.g. if moving up one, -1
	 *  @param colIncre, how much to increment the col by
	 *  
	 *  Begins to check each spot in the direction indicated by rowIncre and colIncre
	 *  stops when it hits an enemy piece, an allied piece, or hit the border of
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
		
		locations.add(new Square(newRow,newCol));
		
		if(GameBoard.Board[newRow][newCol].getCurrentPiece() != null) {
			return;
		}
	
		recursion(row+rowIncre,col+colIncre, rowIncre, colIncre);
	}
	
	/** draw method draws the image associated with the piece*/
	public void draw(Graphics g)
	{
		g.drawImage(image, (int) (width*0.1+column*width), (int)(height*0.1+row*height), (int)(width*0.8), (int)(height*0.8), null);
	}
	
	//Checks if the king is in check, called after every move function finishes
	public boolean checkKing(boolean color) {
		if(color == true) {
			System.out.println("YO THE WHITE KING IS IN CHECK");
		}
		
		else{
			System.out.println("YO THE BLACK KING IS IN CHECK");

		}
		return false;
	}
	
	
	//For counting purposes
	/*checkHelper(row, column, 1 ,0) + checkHelper(row, column, 0, 1) + checkHelper(row, column,-1, 0) + checkHelper(row, column, 0,-1) + checkHelper(row, column, 1 ,1) 
	+ checkHelper(row, column, 1, -1) + checkHelper(row, column, -1, 1) + checkHelper(row, column, -1,-1); */
	
	public boolean checkSquare(int row, int column) {
	
		ArrayList<ChessPiece> otherPieces = new ArrayList<ChessPiece>();
		ArrayList<ChessPiece> knight = checkHorseHelper(row, column);
		
		checkPieceHorizontal(row, column, 1, column , 0, 0 ,otherPieces);
		checkPieceHorizontal(row, column, -1, column , 0, 0,otherPieces);
		checkPieceVertical(row, column, 1, row , 0, 0 ,otherPieces);
		checkPieceVertical(row, column, -1, row , 0, 0 ,otherPieces);
		checkPieceDiagonals(row, column, 1,  1,  row,  column, 0, 0 , otherPieces);
		checkPieceDiagonals(row, column, -1,  1,  row,  column, 0, 0 , otherPieces);
		checkPieceDiagonals(row, column, 1,  -1,  row,  column, 0, 0 , otherPieces);
		checkPieceDiagonals(row, column, -1,  -1,  row,  column, 0, 0 , otherPieces);
		
		if(otherPieces.size() + knight.size() == 0) {
			return false;
		}
			
		else {
			return true;
		}
		
	}
	
	public void checkPieceVertical(int row, int col, int rowIncre, int oriRow, int piecesFound, int spacesMoved ,ArrayList<ChessPiece> otherPieces) {
		
		
		if( (row < 0 || row > 7 ) || ( col < 0 || col > 7 ) ) {
			return;
		}
		
		if(GameBoard.Board[row][col].getCurrentPiece() != null) {
			if(GameBoard.Board[row][col].getCurrentPiece().getColor() == color 
					&& GameBoard.Board[row][col].getCurrentPiece().getVisibility() == true) {	
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
			if(GameBoard.Board[row][col].getCurrentPiece().getColor() != color) {
				if( Math.floor(Math.hypot((this.row-row), (this.column-col))) <= 1.0 && piecesFound < 1 && spacesMoved != 1 && oriRow == row) {
					piecesFound++;
					if( (GameBoard.Board[row][col].getCurrentPiece() instanceof Queen || GameBoard.Board[row][col].getCurrentPiece() instanceof Rook)) {
						//King is in check
					}
				}
				
				else {
					if(piecesFound == 0) {
						if( (GameBoard.Board[row][col].getCurrentPiece() instanceof Queen || GameBoard.Board[row][col].getCurrentPiece() instanceof Rook)) {
							otherPieces.add(GameBoard.Board[row][col].getCurrentPiece());
						}
						
						else {
							return;
						}
						
					}
					if(piecesFound == 1) {

						if( (GameBoard.Board[row][col].getCurrentPiece() instanceof Queen || GameBoard.Board[row][col].getCurrentPiece() instanceof Rook)) {
							otherPieces.add(GameBoard.Board[row][col].getCurrentPiece());
						}
					}
				}
			}
		}
		
		checkPieceVertical(row+rowIncre, col, rowIncre, oriRow, piecesFound, spacesMoved ,otherPieces);
		return ;
		
		
	}

	
	
	public void checkPieceHorizontal(int row, int col, int colIncre, int oriCol,int piecesFound, int spacesMoved ,ArrayList<ChessPiece> otherPieces) {
		
		
		if( (row < 0 || row > 7 ) || ( col < 0 || col > 7 ) ) {
			return;
		}
		
		if(GameBoard.Board[row][col].getCurrentPiece() != null) {
			if(GameBoard.Board[row][col].getCurrentPiece().getColor() == color 
					&& GameBoard.Board[row][col].getCurrentPiece().getVisibility() == true) {	
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
			if(GameBoard.Board[row][col].getCurrentPiece().getColor() != color) {
				if( Math.floor(Math.hypot((this.row-row), (this.column-col))) <= 1.0 && piecesFound < 1 && spacesMoved != 1 && oriCol == col) {
					piecesFound++;

					if( (GameBoard.Board[row][col].getCurrentPiece() instanceof Queen || GameBoard.Board[row][col].getCurrentPiece() instanceof Rook)) {
						//King is in check
					}
				}
				
				else {
					if(piecesFound == 0) {
						if( (GameBoard.Board[row][col].getCurrentPiece() instanceof Queen || GameBoard.Board[row][col].getCurrentPiece() instanceof Rook)) {
							otherPieces.add(GameBoard.Board[row][col].getCurrentPiece());
						}
						
						else {
							return;
						}
						
					}
					if(piecesFound == 1) {
						if( (GameBoard.Board[row][col].getCurrentPiece() instanceof Queen || GameBoard.Board[row][col].getCurrentPiece() instanceof Rook)) {
							otherPieces.add(GameBoard.Board[row][col].getCurrentPiece());

						}
					}
				}
			}
		}
		
		checkPieceHorizontal(row, col+colIncre, colIncre, oriCol, piecesFound, spacesMoved ,otherPieces);
		return ;
		
		
	}
	
	
	public void checkPieceDiagonals(int row, int col,int rowIncre, int colIncre, int oriRow, int oriCol,int piecesFound,int spacesMoved ,ArrayList<ChessPiece> otherPieces) {
		
		if( (row < 0 || row > 7 ) || ( col < 0 || col > 7 ) ) {
			return;
		}
		
		if(GameBoard.Board[row][col].getCurrentPiece() != null) {
			if(GameBoard.Board[row][col].getCurrentPiece().getColor() == color 
					&& GameBoard.Board[row][col].getCurrentPiece().getVisibility() == true) {	
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
			if(GameBoard.Board[row][col].getCurrentPiece().getColor() != color) {
				if( (Math.hypot((this.row-row), (this.column-col))) == Math.sqrt(2.0) && piecesFound < 1 && spacesMoved != 1 && oriCol == col && oriRow == row) {
					piecesFound++;
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
				
				else if( (Math.hypot((oriRow-row), (oriCol-col))) == Math.sqrt(2.0) && piecesFound < 1 ) {
						piecesFound++;
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

						}
					}
				}
			}
		}
		checkPieceDiagonals(row+rowIncre, col+colIncre, rowIncre ,colIncre,oriRow, oriCol, piecesFound, spacesMoved ,otherPieces);
		return; 
	}
		
	//FUCK YEAH IT WORKS
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
	
	public void setVisibility(boolean Visibility) {
		isVisible = Visibility;
	}
	
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
		if (isVisible != other.isVisible)
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
	
//** and overridden implementation of toString to check the ChessPiece
	/*@Override
	public String toString() {
		return "ChessPiece [status=" + status + ", color=" + color + ", canCastleKing=" + canCastleKing
				+ ", canCastleQueen=" + canCastleQueen + ", locations=" + locations + ", row=" + row + ", column="
				+ column + ", image=" + image + ", width=" + width + ", height=" + height + "]";
	}
    */
	
}
