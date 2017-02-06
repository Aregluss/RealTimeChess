
package pieces;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import pieces.ChessPiece;

public class Square extends JPanel implements MouseListener{

	private int row, column;
	private ChessPiece currentPiece;
	private boolean whiteDanger;
	private boolean blackDanger;
	private String lightOn;
	private boolean movable;
	
	public Square(int row, int colum)
	{
		currentPiece = null;
		lightOn = "none";
		movable = false;
		
		addMouseListener(this); //???????????????
	}
	
	public Square()
	{
		
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

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		int x = e.getX();
		int y = e.getY();
		
		//double dx = x * (Map.REALWIDTH + 0.0) / map.getWidth();
		//double dy = y * (Map.REALHEIGHT + 0.0) / map.getHeight();
		
		System.out.println("SEX");
		
		if(e.getButton() == MouseEvent.BUTTON3)
		{
			System.out.println("LEFT");
		}
		else if(e.getButton() == MouseEvent.BUTTON1)
		{
			System.out.println("ROIGHT");
		}
		//System.out.println(x + ", " + y);
		
	//	map.repaint();
		
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		System.out.println("YEET BOI");
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}