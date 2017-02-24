package game;

import java.awt.BorderLayout;
import java.awt.Color;
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
		setBounds(100, 100, 800, 700);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setVisible(true);
	    //make window go transparent when mouse leaves will look cool
	    
	   
       
	}
	
	
	
}
