package game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
/**
 * 
 * @author Areg
 *
 */
public class Window extends JFrame{
	
	public Window()
	{	
		super("RTC");
		//should make game resizable
		setBounds(200, 200, 800, 800);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMinimumSize(new Dimension(800, 800));
		setVisible(true);
	    //make window go transparent when mouse leaves will look cool
	    
	   
       
	}
	
	
	
}
