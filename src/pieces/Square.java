
package pieces;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import pieces.ChessPiece;

public class Square extends JPanel{// implements MouseListener{

	private int row, column;
	public ChessPiece currentPiece;
	private String lightOn;
	private boolean movable;
	private Image light;
	
	public Square(int row, int column)
	{
		
		try
		{
			light = ImageIO.read(new File("Rook.jpg"));
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		
		currentPiece = null;
		lightOn = "none";
		movable = false;


		this.row = row;
		this.column = column;
	}
	
	public Square(int row, int column, ChessPiece currentPiece) {
		this.currentPiece = currentPiece;
		lightOn = "none";
		movable = false;
		this.row = row;
		this.column = column;
	}
	
	public Square() {
		
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


	/*public void turnItUp(Graphics g) ///????
	{	
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		
		g2.drawImage(light, 10, 10, 20, 20, this);
	}	*/
	@Override
	public String toString() {
		return "Square [row=" + row + ", column=" + column + ", currentPiece=" + currentPiece + "]";
	}
	
}