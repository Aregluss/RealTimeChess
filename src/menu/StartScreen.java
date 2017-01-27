package menu;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

import game.RealTimeChess;

/**It's the screen that game opens with, it contains links to server ,client and playing the game. 
 * 
 * @author Areg & Leon
 *
 */
public class StartScreen extends JPanel
{
	private JButton startGame;
	private JButton host;
	private JButton join;
	private JButton option;
	private JButton exit;
	
	private String joinIP;
	private String myIP;
	
	public StartScreen()
	{
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		ArrayList<JButton> buttons = new ArrayList<JButton>();
		startGame = new JButton("Start");
		host = new JButton("Host");
		join = new JButton("Join");
		option = new JButton("Options");
		exit = new JButton("Exit");
		buttons.add(startGame);
		buttons.add(host);
		buttons.add(join);
		buttons.add(option);
		buttons.add(exit);
		
		for(JButton b: buttons){
			b.setIcon(new ImageIcon("Logo.png"));
			b.setFont(new Font("Britannic Bold", 0, 24));
			b.setRolloverIcon(new ImageIcon("Logo2.png"));
			add(b);
			b.addActionListener(new JButtonHandler());
			b.setAlignmentX(Component.CENTER_ALIGNMENT);
			b.setMinimumSize(new Dimension(500,500));
			b.setMaximumSize(new Dimension(500,500));
			b.setVerticalTextPosition(SwingConstants.BOTTOM);
		    b.setHorizontalTextPosition(SwingConstants.CENTER);
		}
		
		//setIcon(newjavax.swing.ImageIcon(getClass().getResource ("white.png")));
		
	//	CODE FOR TEXT UNDERNEATH ICON
	 
	 	//startGame.setBorderPainted(false);
		//startGame.setContentAreaFilled(false);
	//	startGame.setVerticalTextPosition(SwingConstants.BOTTOM);
		//startGame.setHorizontalTextPosition(SwingConstants.CENTER);

	}
	
	private void findIP() throws UnknownHostException{
		 InetAddress thisIp = InetAddress.getLocalHost();
		 myIP = thisIp.getHostAddress();
	}

	class JButtonHandler implements ActionListener
	{

		public void actionPerformed(ActionEvent arg0) 
		{
			if(startGame.getModel().isArmed()) ///networking and hero selection have to work
				
				RealTimeChess.switchPanel("2");
			else if(host.getModel().isArmed()){
				try {
				//	SchoolServer ss = new SchoolServer();
					findIP();
					JOptionPane.showMessageDialog(null, "Your IP is " +  myIP);
					RealTimeChess.switchPanel("2");
				} catch (UnknownHostException e) {
					JOptionPane.showMessageDialog(null, "Unable to find your IP");
				}
			}
			else if(join.getModel().isArmed())
				joinIP = JOptionPane.showInputDialog("Enter the IP You Wish to Connect To");
			else if(exit.getModel().isArmed())
				System.exit(0);
		}
		
	}
	
}
