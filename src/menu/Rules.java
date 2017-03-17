package menu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import game.RealTimeChess;

/**It's the screen that game opens with, it contains links to server ,client and playing the game. 
 * 
 * @author Areg
 *
 */
public class Rules extends JPanel
{
	
	JButton back;
	JLabel text;
	
	public Rules()
	{
		  //  setLayout(new BorderLayout());
		    JPanel panel = new JPanel();
		    add(panel);//, BorderLayout.WEST);
		    JLabel jlabel = new JLabel("<html>Welcome to Real Time Chess: <br>"
		    		+ "Here are a basic set of rules to get you started.<br>"
		    		+ "----------------------------------------------------------<br>"
		    		+ "Pieces move just like they do in regular chess.<br>"
		    		+ "Unlike chess, there are no turns, so don't have to wait.<br>"
		    		+ "After moving each piece it cant be moved for 3 seconds.<br>"  
		    		+ "When in check, all piece timers will be paused.<br>"
		    		+ "When checked, it must be resolved in next move<br>"
		    		+ "If you dont respond to a check for 30 seconds you lose.<br>"
		    	//	+ "Copy This and then modify this part for new rules <--.<br>"
		    		+ "Dont play agaisnt devs, they have cheats!</html>", SwingConstants.CENTER);
		    
		    //<br> is html's \n, so use it :)
		    
		    jlabel.setFont(new Font("FUTURA",0,25));
		    panel.add(jlabel);
		    panel.setBorder(new LineBorder(Color.BLACK)); // make it easy to see
		    panel.setSize(400, 400);
		    
		    back = new JButton("Back");
		    add(back, BorderLayout.EAST);
		    back.setPreferredSize(new Dimension(200, 100));
			back.addActionListener(new ButtonListener());
			back.setIcon(new ImageIcon(new ImageIcon("golden.png").getImage().getScaledInstance(115, 80, Image.SCALE_DEFAULT)));
			back.setFont(new Font("FUTURA BOLD", 0, 24));
			back.setRolloverIcon(new ImageIcon(new ImageIcon("goldenH.png").getImage().getScaledInstance(115, 80, Image.SCALE_DEFAULT)));
			back.setVerticalTextPosition(SwingConstants.CENTER);
		    back.setHorizontalTextPosition(SwingConstants.CENTER);

	}
	
	class ButtonListener implements ActionListener
	{

		public void actionPerformed(ActionEvent arg0) 
		{
			if(back.getModel().isArmed())
				RealTimeChess.switchPanel("1");
		}
		
	}
	
}
