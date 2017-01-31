
package pieces;

import pieces.ChessPiece;

public class Square {

	private int row, column;
	private ChessPiece currentPiece;
	private boolean whiteDanger;
	private boolean blackDanger;
	private boolean lightOn;
	private boolean movable;
	
	public Square(int row, int colum, boolean wd, boolean bd)
	{
		currentPiece = null;
		whiteDanger = wd;
		blackDanger = bd;
		lightOn = false;
		movable = false;
		
	}
	
	public int getRow()
	{
		return row;	
	}
	
	public int getColumn()
	{
		return column;
	}
	
	public ChessPiece getCurrentPiece()
	{
		return currentPiece;
	}
	
	public boolean checkWhiteDanger()
	{
		return whiteDanger;
	}
	
	public boolean checkBlackDanger()
	{
		return blackDanger;
	}
	
	public boolean checkLight()
	{
		return lightOn;
	}

	public boolean getMovable()
	{
		return movable;
	}
	
	public void setRow(int r)
	{
		row = r;	
	}
	
	public void setColumn(int c)
	{
		column = c;
	}
	
	public void setCurrentPiece(ChessPiece c)
	{
		currentPiece = c;
	}
	
	public void checkWhiteDanger(boolean d)
	{
		whiteDanger = d;
	}

	public void checkBlackDanger(boolean d)
	{
		blackDanger = d;
	}
	
	public void setLight(boolean l)
	{
		lightOn = l;
	}
	
	public void setMovable(boolean m)
	{
		movable = m;
	}
}