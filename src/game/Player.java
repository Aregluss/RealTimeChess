package game;
import java.util.*;
import pieces.*;
import game.*;
public class Player{ 
	private boolean color;
	public ArrayList<Square> pieces = new ArrayList<Square>();
	public Player(boolean color) {
		if(color){
			for(int i=6; i<8; i++){
				for(int j=0; j<8; j++){
					pieces.add(new Square(i,j,GameBoard.Board[i][j].getCurrentPiece()));
				}
			}
			this.color = color;
		}
		else{
			for(int i=0; i<2; i++){
				for(int j=0; j<8; j++){
					pieces.add(new Square(i,j,GameBoard.Board[i][j].getCurrentPiece()));
				}
			}
			this.color = color;
		}
	}
	
}
