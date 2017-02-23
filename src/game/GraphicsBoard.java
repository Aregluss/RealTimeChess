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
import java.awt.image.BufferedImage;

import pieces.ChessPiece;


public class GraphicsBoard extends JPanel implements MouseListener
{

	public static final int WIDTH = 640;
	public static final int HEIGHT = 640;
	
	//public static final int REALWIDTH = 800;
	//public static final int REALHEIGHT = 600;
	
	private Image background;
	private int initalPress = 0;
	private int row, col, row1, col1;
	//BufferedImage bf = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	
	
	public GraphicsBoard()
	{
		
		try
		{
			background = ImageIO.read(new File("board.png"));
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		addMouseListener(this);
		repaint();
		
	}
	
	public void paintComponent(Graphics g)
	{	
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		//g2.setBackground(Color.RED);
		
		g2.drawImage(background, 0, 0, WIDTH, HEIGHT, this);
	}	
	
	
	public Image getImage()
	{
		return background;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
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
					//Graphics g = graphBoard.getGraphics();
					//Graphics2D g2 = (Graphics2D) g;
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
						System.out.println("IM SELECTED");
					}
					else
					{
						System.out.println("R&C:" +row +" " +col);
						row1 = y/80;
						col1 = x/80;
						if(row == row1 && col == col1)
						{
							initalPress = 0;
							System.out.println("DO BETTER U CUC");
						}
						else
						{
							//System.out.println();
							if(initalPress == 1 && GameBoard.Board[row][col].getCurrentPiece().offCoolDown)
							{
								System.out.println("initalpress1");
								GameBoard.Board[row][col].getCurrentPiece().move(row1, col1);
								//GameBoard.Board[row][col].getCurrentPiece().draw(g2);
								//GameBoard.drawPiece(row, col);
								Rectangle a = new Rectangle(col * 80, row * 80, (col+1) * 80, (row+1)*80);
								Rectangle b = new Rectangle(col1 * 80, row1 * 80, (col1+1) * 80, (row1+1)*80);
								repaint(a);
								repaint(b);
								
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
		//animation(bf.getGraphics());
		//g.drawImage(bf,0,0,null);
		 //repaint(); //Redraws board to update images being moved
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
