
package pieces;

import pieces.ChessPiece;

public class Square {

	private int row, column;
	private ChessPiece currentPiece;
	private boolean whiteDanger;
	private boolean blackDanger;
	private String lightOn;
	private boolean movable;
	
	public Square(int row, int colum, boolean wd, boolean bd)
	{
		currentPiece = null;
		whiteDanger = wd;
		blackDanger = bd;
		lightOn = "none";
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
	
	public String checkLight()
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
	
	public void setLight(String l)
	{
		lightOn = l;
	}
	
	public void setMovable(boolean m)
	{
		movable = m;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + column;
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
		Square other = (Square) obj;
		if (column != other.column)
			return false;
		if (row != other.row)
			return false;
		return true;
	}
	
}