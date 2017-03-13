package game;

import java.awt.Dimension;
import javax.swing.JFrame;
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
	    //make window go transparent when mouse leaves will look cool
	    setVisible(true);
	   
       
	}
	
	
	
}
