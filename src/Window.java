package Default;

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
 *5/14/14
 */
public class Window extends JFrame{
	
	public Window()
	{	
		super("Defense of the Oincients");
		//should make game resizable
		setBounds(100, 100, 800, 668);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setVisible(true);
	    //make window go transparent when mouse leaves will look cool
	    addMouseListener( new MouseAdapter() {
	        public void mouseExited( MouseEvent e ) {
	        	//System.out.println("OUT");
	        }
	        
	        public void mouseEntered(MouseEvent e)
	        {
	        	//System.out.println("IN");
	        }
	    } );
       
	}
	
	
	
}
