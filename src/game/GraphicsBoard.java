package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import pieces.Square; //remove later


public class GraphicsBoard extends JPanel implements MouseListener
{

	public static final int WIDTH = 640;
	public static final int HEIGHT = 640;
	
	//public static final int REALWIDTH = 800;
	//public static final int REALHEIGHT = 600;
	
	private Image background;
	private int initalPress = 0;
	private int row, col, row1, col1;
	
	public GraphicsBoard()
	{
		
		try
		{
			background = ImageIO.read(new File("Board.jpg"));
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		addMouseListener(this);
		repaint();
		
	}
	/**Draws the chessboard backround
	 * @param g, Graphics object for drawing
	 */
	public void paintComponent(Graphics g)
	{	
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		//g2.setBackground(Color.RED);
		g2.drawImage(background, 0, 0, WIDTH, HEIGHT, this);
	}	
	
	/**Getter for the backround image
	 * 
	 * @return the background
	 */
	public Image getImage()
	{
		return background;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	/**This is where moving chess pieces happens
	 * Right click selects the piece, records time where piece was selected
	 * moves when pressed again on an empty space
	 * Left click cancels a current move
	 * Keep track of if its first click to select or second click to move
	 */
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		int x = e.getX();
		int y = e.getY();
		//int initalPress = 0;
		
		//double dx = x * (Map.REALWIDTH + 0.0) / map.getWidth();
		//double dy = y * (Map.REALHEIGHT + 0.0) / map.getHeight();
		
		System.out.println("x & y = " +x +" & "+y);
		if(x <= WIDTH && y <= HEIGHT)
		{
			if(e.getButton() == MouseEvent.BUTTON3)
			{
				System.out.println("LEFT");
				initalPress = 0;
			}
			else if(e.getButton() == MouseEvent.BUTTON1)
			{

				if(GameBoard.Board[y/80][x/80].getCurrentPiece() != null || initalPress == 1)
				{
					System.out.print("inital press: ");
					System.out.println(initalPress);
					boolean offCoolDown = (initalPress == 0 && GameBoard.Board[y/80][x/80].getCurrentPiece().offCoolDown);
					System.out.print("OffCoolDown: ");
					System.out.println(offCoolDown);
					if (GameBoard.Board[y/80][x/80].getCurrentPiece() != null)
					{
						GameBoard.Board[y/80][x/80].getCurrentPiece().offCoolDown = (GameBoard.Board[y/80][x/80].getCurrentPiece().time_limit < GameBoard.Board[y/80][x/80].getCurrentPiece().A_Clock.return_milli_time()-GameBoard.Board[y/80][x/80].getCurrentPiece().time);
					}
					if(initalPress == 0 && offCoolDown)
					{	
						row = y/80;
						col = x/80;
						initalPress++;
						System.out.println("IM SELECTED " + GameBoard.Board[row][col].getCurrentPiece());
						if(GameBoard.gameState == 2) {
							//lol
							System.out.println("THIS IS CHECKED BOYS");
							/*for(Square movable : GameBoard.Board[row][col].getCurrentPiece().locations){
								System.out.println(movable);
							}*/
						}
						else {
							System.out.println("normality");
							GameBoard.Board[row][col].getCurrentPiece().getMoveLocations();							
							/*for(Square movable : GameBoard.Board[row][col].getCurrentPiece().locations){
								System.out.println(movable);
							}*/
						}
					}
					else
					{
						
						System.out.println("R&C:" +row +" " +col);
						row1 = y/80;
						col1 = x/80;
						if(row == row1 && col == col1)
						{
							initalPress = 0;
							System.out.println("Moving me to same spot?");
						}
						else
						{
							if(initalPress == 1 && GameBoard.Board[row][col].getCurrentPiece().offCoolDown)
 							{
 								System.out.println("initalpress1");
 								GameBoard.Board[row][col].getCurrentPiece().move(row1, col1);
 							}
 							if(GameBoard.Board[row][col].getCurrentPiece() == null)
  							{
  								initalPress = 0;
 								System.out.println("MOVE ME DADDY");
 							}
						}
					}
				}
			}
		}
		 repaint(); //Redraws board to update images being moved
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
