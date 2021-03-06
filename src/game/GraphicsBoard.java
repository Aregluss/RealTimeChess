package game;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import pieces.ChessPiece;
import pieces.CoolDownAnimation;
import network.*;
import pieces.King;

public class GraphicsBoard extends JPanel implements MouseListener
{

	public static final int WIDTH = 800;
	public static final int HEIGHT = 800;
	
	//public static final int REALWIDTH = 800;
	//public static final int REALHEIGHT = 600;
	
	private Image background;
	private int initalPress = 0;
	private int row, col, row1, col1;
	public static int x1,y1,x2,y2;
	public static int x3,y3,x4,y4;
	public static boolean player = false;
	public static boolean moved = false;
	/**Constructor
	 * Reads the background image, adds mouse listener.
	 */
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
	/**Returns Player
	 * @return returns player
	 */
	public boolean returnPlayer(){
		return player;
	}
	/**Sets the player (true = white, false = black)
	 * 
	 * @param x, what the player boolean value should be set to.
	 */
	public void setPlayer(boolean x){
		player = x;
	}
	/**Checks if a piece has been moved.
	 * 
	 * @return boolean value if a piece has been moved.
	 */
	public static boolean isMoved(){
		return moved;
	}
	
	/**Sets the boards moved value to param.
	 * 
	 * @param x Boolean value to set boards moved status.
	 */
	public static void setMoved(boolean x){
		moved = x;
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
	
	public int getW()
	{
		return getWidth();
	}

	public int getH()
	{
		return getHeight();
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
	/**Calls the mouseHelper function, passing
	 * its own MouseEvent e, and boards player value.
	 * 
	 */
	public void mousePressed(MouseEvent e) {
		
		mouseHelper(e,player);
	}
	/**This is where moving chess pieces happens
	 * Right click selects the piece, records time where piece was selected
	 * moves when pressed again on an empty space
	 * Left click cancels a current move
	 * Keep track of if its first click to select or second click to move
	 *
	 * 
	 * @param e MouseEvent e passed by mousePressed
	 * @param color player value of the board that calls it.
	 */
	public void mouseHelper(MouseEvent e, boolean color){
		// TODO Auto-generated method stub
				// TODO Auto-generated method stub
				int x = e.getX();
				int y = e.getY();
				//int initalPress = 0;
				
				//double dx = x * (Map.REALWIDTH + 0.0) / map.getWidth();
				//double dy = y * (Map.REALHEIGHT + 0.0) / map.getHeight();
				System.out.println("x & y = " +x +" & "+y);
				if(x < WIDTH && y < HEIGHT)
				{
					int yx = y/(HEIGHT/8);
					int xx = x/(WIDTH/8);
					if(initalPress ==0){
						x1 = xx;
						y1 = yx;
					}
					if(initalPress ==1){
						x2 = xx;
						y2 = yx;

					}
					if(e.getButton() == MouseEvent.BUTTON3)
					{
						System.out.println("LEFT");
						initalPress = 0;
						GameBoard.clearlastSelected();
						if(GameBoard.Board[row][col].getCurrentPiece() != null && GameBoard.Board[row][col].getLight() != new ImageIcon("blue.png").getImage()) {
							GameBoard.Board[row][col].getCurrentPiece().unhighlightLocation(row, col);
						}
						if(GameBoard.Board[row1][col1].getCurrentPiece() != null && GameBoard.Board[row1][col1].getLight() != new ImageIcon("blue.png").getImage()) {
							GameBoard.Board[row1][col1].getCurrentPiece().unhighlightLocation(row1, col1);
						}
												
						if(GameBoard.gameState == 2) {
							if(GameBoard.getChecked() == 0) {
								
								((King)GameBoard.Board[GameBoard.Bk.getRow()][GameBoard.Bk.getColumn()].getCurrentPiece()).checkhighlightLocation();
							}
							if(GameBoard.getChecked() == 1) {
								((King)GameBoard.Board[GameBoard.Wk.getRow()][GameBoard.Wk.getColumn()].getCurrentPiece()).checkhighlightLocation();
							}
						}
					}
					else if(e.getButton() == MouseEvent.BUTTON1)
					{
						
						if(GameBoard.Board[yx][xx].getCurrentPiece() != null || initalPress == 1)
						{
							System.out.print("inital press: ");
							System.out.println(initalPress);
							boolean offCoolDown = (initalPress == 0 && GameBoard.Board[yx][xx].getCurrentPiece().offCoolDown);
							System.out.print("OffCoolDown: ");
							System.out.println(offCoolDown);
							if (GameBoard.Board[yx][xx].getCurrentPiece() != null)
							{
								GameBoard.Board[yx][xx].getCurrentPiece().offCoolDown = (GameBoard.Board[yx][xx].getCurrentPiece().time_limit < GameBoard.Board[yx][xx].getCurrentPiece().A_Clock.return_milli_time()-GameBoard.Board[yx][xx].getCurrentPiece().time);
								GameBoard.Board[yx][xx].getCurrentPiece();
								offCoolDown = (initalPress == 0 && (GameBoard.Board[yx][xx].getCurrentPiece().offCoolDown) || ChessPiece.A_Clock.get_isPaused());
								if(GameBoard.Board[yx][xx].getCurrentPiece().hasMoved == false)
								{
									offCoolDown = (initalPress == 0);
								}
							}
							
							if(initalPress == 0 && offCoolDown)
							{	
								row = yx;
								col = xx;
								if(GameBoard.Board[row][col].getCurrentPiece().color == color){
								initalPress++;
								System.out.println("IM SELECTED " + GameBoard.Board[row][col].getCurrentPiece());
								//new addition
								GameBoard.setlastSelected(row,col,GameBoard.Board[row][col].getCurrentPiece());
								if(GameBoard.gameState == 2) {
									//lol
									GameBoard.Board[row][col].getCurrentPiece().highlightLocation();
									System.out.println("THIS IS CHECKED BOYS");
									/*for(Square movable : GameBoard.Board[row][col].getCurrentPiece().locations){
										System.out.println(movable);
									}*/
								}
								else {
									System.out.println("normality");
									GameBoard.Board[row][col].getCurrentPiece().getMoveLocations();			
									GameBoard.Board[row][col].getCurrentPiece().highlightLocation();
									/*for(Square movable : GameBoard.Board[row][col].getCurrentPiece().locations){
										System.out.println(movable);
									}*/
								}
								}
								else{
									System.out.println("Wrong color BOYYYYYYYYYYYYYY");
								}
							}
							else
							{
								
								System.out.println("R&C:" +row +" " +col);
								row1 = yx;
								col1 = xx;
								//System.out.println(GameBoard.Board[row1][col1].getCurrentPiece().time);
								//System.out.println(GameBoard.Board[row][col].getCurrentPiece().hasMoved);
								if(row == row1 && col == col1)
								{
									initalPress = 0;
									System.out.println("Moving me to same spot?");
									if(GameBoard.Board[row][col].getLight() != new ImageIcon("blue.png").getImage()) {
										GameBoard.Board[row][col].getCurrentPiece().unhighlightLocation(row,col);
									}
									
									GameBoard.clearlastSelected();
					
									if(GameBoard.gameState == 2) {
										if(GameBoard.getChecked() == 0) {
											((King)GameBoard.Board[GameBoard.Bk.getRow()][GameBoard.Bk.getColumn()].getCurrentPiece()).checkhighlightLocation();
										}
										if(GameBoard.getChecked() == 1) {
											((King)GameBoard.Board[GameBoard.Wk.getRow()][GameBoard.Wk.getColumn()].getCurrentPiece()).checkhighlightLocation();
										}
									}
								}
								else
								{
									boolean paused = ChessPiece.A_Clock.get_isPaused();
									System.out.println(paused);
									if(initalPress == 1 && (GameBoard.Board[row][col].getCurrentPiece().offCoolDown || !GameBoard.Board[row][col].getCurrentPiece().hasMoved || ChessPiece.A_Clock.get_isPaused()))
		 							{
		 								System.out.println("initalpress1");
		 								if(color==true)
		 									Server.send();
		 								if(color == false)
		 									Client.send();
		 								GameBoard.Board[row][col].getCurrentPiece().move(row1, col1);

		 								//GameBoard.Board[row1][col1].getCurrentPiece().hasMoved = true;
		 								//GameBoard.Board[row1][col1].getCurrentPiece().executeTimeout();
		 								//GameBoard.Board[row1][col1].getCurrentPiece().
		 								//Rectangle a = new Rectangle(col * 80, row * 80, (col+1) * 80, (row+1)*80);
										//Rectangle b = new Rectangle(col1 * 80, row1 * 80, (col1+1) * 80, (row1+1)*80);
										Graphics g = getGraphics();
										if(GameBoard.Board[row1][col1].getCurrentPiece() != null && GameBoard.Board[row][col].getCurrentPiece() == null)
										{
											new CoolDownAnimation(GameBoard.Board[row1][col1].getCurrentPiece()).executeTimeout(g);
										}
										//executeTimeout(g, GameBoard.Board[row1][col1].getCurrentPiece(), row1, col1);
										//GameBoard.Board[row1][col1].getCurrentPiece().CoolDownAnimation(g, row1, col1);
										//repaint(a);
										//repaint(b);
		 							}
		 								
		 							if(GameBoard.Board[row][col].getCurrentPiece() == null)
		  							{
		  								initalPress = 0;
		 								System.out.println("This is moved");
		 								GameBoard.Board[row1][col1].getCurrentPiece().unhighlightLocation(row, col);
		 								//System.out.println(GameBoard.Board[row1][col1].getCurrentPiece().time_limit < GameBoard.Board[row1][col1].getCurrentPiece().A_Clock.return_milli_time()-GameBoard.Board[row1][col1].getCurrentPiece().time)
		 								//System.out.println(GameBoard.Board[row1][col1].getCurrentPiece().time);
		 								System.out.println(GameBoard.Board[row1][col1].getCurrentPiece().offCoolDown);
		 								
		 								if(GameBoard.gameState == 2) {
		 									if(GameBoard.getChecked() == 0) {
		 										((King)GameBoard.Board[GameBoard.Bk.getRow()][GameBoard.Bk.getColumn()].getCurrentPiece()).checkhighlightLocation();
		 									}
		 									if(GameBoard.getChecked() == 1) {
		 										((King)GameBoard.Board[GameBoard.Wk.getRow()][GameBoard.Wk.getColumn()].getCurrentPiece()).checkhighlightLocation();
		 									}
		 								}
		 								
		  							}
								}
							}
						}
					}
				}
				repaint();
				
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
	/**Resets mousePressed initial value to 0
	 * This puts mouse clicks on select mode.
	 */
	public void resetMousePressed() {
		initalPress = 0;
	}


	
    /*public void executeTimeout(Graphics g, ChessPiece piece, int row1, int col1) {
        if (piece.timer == null) {
            piece.timer = new Timer(0, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    long diff =  System.currentTimeMillis() - piece.startedAt;
                    float progress = diff / (float) piece.timeout;
                    piece.CoolDownAnimation(g);
                    if (diff >= piece.timeout) {
                        progress = 1f;
                        piece.timer.stop();
                    }
                    piece.setProgress(progress);
                    Rectangle b = new Rectangle(col1 * 80, row1 * 80, ((col1)+1 * 80), ((row1)+1*80));
                    repaint(b);
                    
                }
            });
        } else if (piece.timer.isRunning()) {
            piece.timer.stop();
        }

        piece.startedAt = System.currentTimeMillis();
        piece.timer.start();
    }*/
	
}
