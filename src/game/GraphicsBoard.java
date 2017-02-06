package game;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;


public class GraphicsBoard extends JPanel
{

	public static final int WIDTH = 640;
	public static final int HEIGHT = 640;
	//public static final int REALWIDTH = 800;
	//public static final int REALHEIGHT = 600;
	
	private Image background;
	
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
		
		
		repaint();
		
	}
	
	public void paintComponent(Graphics g)
	{	
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		
		
		g2.drawImage(background, 0, 0, WIDTH, HEIGHT, this);
	}	
	
	
	public Image getImage()
	{
		return background;
	}
	
}
