
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
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import game.GraphicsBoard;
import pieces.ChessPiece;

public class Square extends JPanel{// implements MouseListener{

	private int row, column;
	public ChessPiece currentPiece;
	private String lightOn;
	private boolean movable;
	private Image light;
	private int width = GraphicsBoard.WIDTH/8;
	private int height = GraphicsBoard.HEIGHT/8;
	
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
	
	
	public void setSquare(int lightStatus) {
		if (lightStatus == 0) {
			light = new ImageIcon("green.png").getImage();
		}
		
		else if (lightStatus == 1) { 
			light = new ImageIcon("golden.png").getImage();
		} 
		
		else{
			light = null;
		}
	}
	
	public Image getSquare() {
		return light;
	}
	
	public void setMovable(boolean m)
	{
		movable = m;
	}
	
	/** draw method draws the image associated with the piece*/
	public void draw(Graphics g)
	{
//		BufferedImage bi = new BufferedImage(400, 300, BufferedImage.TYPE_INT_ARGB);
		
		Graphics2D g2d = (Graphics2D)g;
		
		g2d.drawImage(light, (int) (width*0.1+column*width), (int)(height*0.1+row*height), (int)(width*0.8), (int)(height*0.8), null);
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