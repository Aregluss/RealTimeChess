package game;
import java.util.*;
import pieces.*;
import game.*;
public class Player{ 
	private boolean color;
	public ArrayList<ChessPiece> pieces = new ArrayList<ChessPiece>();
	public Player(boolean color) {
		if(color){
			for(int i=6; i<8; i++){
				for(int j=0; j<8; j++){
					pieces.add(GameBoard.Board[i][j].getCurrentPiece());
				}
			}
		}
		else{
			for(int i=0; i<2; i++){
				for(int j=0; j<8; j++){
					pieces.add(GameBoard.Board[i][j].getCurrentPiece());
				}
			}
		}
	}
	
}
