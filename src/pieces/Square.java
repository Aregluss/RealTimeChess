
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
	
	/**
	 * Standard constructor for a square,
	 * @param row, row of the square
	 * @param column, column of the square
	 */
	public Square(int row, int column)
	{
		//light
		
		currentPiece = null;
		lightOn = "none";
		movable = false;


		this.row = row;
		this.column = column;
	}
	/**
	 * Constructor for square, useful for adding pieces to that square
	 * @param row, of the square
	 * @param column of the square
	 * @param currentPiece the current piece on that square
	 */
	public Square(int row, int column, ChessPiece currentPiece) {
		this.currentPiece = currentPiece;
		lightOn = "none";
		movable = false;
		this.row = row;
		this.column = column;
	}
	
	/**
	 * default constructor
	 */
	public Square() {
		
	}
	
	/**
	 * 
	 * @return row of square
	 */
	public int getRow()
	{
		return row;	
	}
	
	/**
	 * 
	 * @return column of square
	 */
	public int getColumn()
	{
		return column;
	}
	
	/**
	 * 
	 * @return chesspiece on square or null if empty
	 */
	public ChessPiece getCurrentPiece()
	{
		return currentPiece;
	}
	
	/**
	 * 
	 * @return if the light is on or not (highlighting the square)
	 */
	public String checkLight()
	{
		return lightOn;
	}

	/**Get movable status of piece
	 * 
	 * @return movable status
	 */
	public boolean getMovable()
	{
		return movable;
	}
	
	/**
	 * 
	 * @param r, row to set the square's row to
	 */
	public void setRow(int r)
	{
		row = r;	
	}
	
	/**
	 * 
	 * @param c, row to set the square's column to
	 */
	public void setColumn(int c)
	{
		column = c;
	}
	
	/**
	 * 
	 * @param c, chesspiece to set to square
	 */
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