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
		setBounds(200, 200, 825, 850);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMinimumSize(new Dimension(825, 850));
	    //make window go transparent when mouse leaves will look cool
	    setVisible(true);
	   
       
	}
	
	
	
}
