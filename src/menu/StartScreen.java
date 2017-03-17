package menu;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import javax.swing.SwingConstants;

import javax.swing.JTextArea;
import javax.swing.UIManager;
import game.GraphicsBoard;
import game.RealTimeChess;
import network.*;

/**It's the screen that game opens with, it contains links to server ,client and playing the game. 
 * 
 * @author Areg
 *
 */
public class StartScreen extends JPanel
{
	private JButton startGame;
	private JButton host;
	private JButton join;
	private JButton rules;
	private JButton exit;
	
	private String joinIP;
	private String myIP;
	
	public StartScreen()
	{
	//	setLayout(new BoxLayout(this, BoxLayout.));
		setLayout(new BorderLayout());
	    JPanel subPanel = new JPanel();

	    //subPanel.add( new JButton( "1" ));
		
		//	add (save, BorderLayout.EAST);
		ArrayList<JButton> buttons = new ArrayList<JButton>();
		//startGame = new JButton("Start");
		host = new JButton("Host");
		join = new JButton("Join");
		rules = new JButton("Rules");
		exit = new JButton("Exit");
	//	buttons.add(startGame);
		buttons.add(host);
		buttons.add(join);
		buttons.add(rules);
		buttons.add(exit);
		
		for(JButton b: buttons){
			b.setIcon(new ImageIcon(new ImageIcon("golden.png").getImage().getScaledInstance(115, 100, Image.SCALE_DEFAULT)));
			b.setFont(new Font("FUTURA BOLD", 0, 24));
			b.setRolloverIcon(new ImageIcon(new ImageIcon("goldenH.png").getImage().getScaledInstance(115, 100, Image.SCALE_DEFAULT)));
			subPanel.add(b);
			b.addActionListener(new JButtonHandler());
			b.setAlignmentX(Component.RIGHT_ALIGNMENT);
			b.setMinimumSize(new Dimension(200,200));
			b.setMaximumSize(new Dimension(400,300));
			b.setVerticalTextPosition(SwingConstants.CENTER);
		    b.setHorizontalTextPosition(SwingConstants.CENTER);
		}

		add(subPanel, BorderLayout.CENTER);
		subPanel.setSize(new Dimension(250, 1000));
		
		JButton b = new JButton();
		b.setIcon(new ImageIcon(new ImageIcon("whiteQueen.png").getImage().getScaledInstance(300, 600, Image.SCALE_DEFAULT)));
		b.setFont(new Font("FUTURA BOLD", 0, 24));
		b.setRolloverIcon(new ImageIcon(new ImageIcon("blackQueen.png").getImage().getScaledInstance(300, 600, Image.SCALE_DEFAULT)));
		add(b, BorderLayout.EAST);
		b.addActionListener(new JButtonHandler());
		b.setAlignmentX(Component.RIGHT_ALIGNMENT);
		b.setMinimumSize(new Dimension(250,100));
		b.setMaximumSize(new Dimension(200,800));
		
		b.setOpaque(false);
		b.setContentAreaFilled(false);
		b.setBorderPainted(false);
		
		JButton b1 = new JButton();
		b1.setIcon(new ImageIcon(new ImageIcon("whiteKing.png").getImage().getScaledInstance(300, 600, Image.SCALE_DEFAULT)));
		b1.setFont(new Font("FUTURA BOLD", 0, 24));
		b1.setRolloverIcon(new ImageIcon(new ImageIcon("blackKing.png").getImage().getScaledInstance(300, 600, Image.SCALE_DEFAULT)));
		add(b1, BorderLayout.WEST);
		b1.addActionListener(new JButtonHandler());
	//	b1.setAlignmentX(Component.LEFT_ALIGNMENT);
		b1.setMinimumSize(new Dimension(100,100));
		b1.setMaximumSize(new Dimension(400,800));
		
		b1.setOpaque(false);
		b1.setContentAreaFilled(false);
		b1.setBorderPainted(false);
		
		
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
			if(rules.getModel().isArmed()) ///networking and hero selection have to work
				
				RealTimeChess.switchPanel("3");
			else if(host.getModel().isArmed()){
				try {
				//	SchoolServer ss = new SchoolServer();
					System.out.println("hello. I AM IN HOST CLICKED.");
					findIP();
					JTextArea textarea= new JTextArea(myIP);
					textarea.setEditable(false);
					textarea.selectAll();
					UIManager.put("OptionPane.okButtonText", "Copy & Start");
					JOptionPane.showMessageDialog(null, textarea, "Your IP", JOptionPane.INFORMATION_MESSAGE);
					
					textarea.copy();
					
					Server newServer = new Server();
					GraphicsBoard.player = true;
					UIManager.put("OptionPane.okButtonText", "Ok");
				} catch (UnknownHostException e) {
					JOptionPane.showMessageDialog(null, "Unable to find your IP");
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
			else if(join.getModel().isArmed()){
				try{
				joinIP = JOptionPane.showInputDialog("Enter the IP You Wish to Connect To");
				Client newClient = new Client(joinIP);
				GraphicsBoard.player = false;
				}
				catch(IOException e){
					JOptionPane.showMessageDialog(null, "Unable to find your opponent");

				}
				
				
			}
			else if(exit.getModel().isArmed())
				System.exit(0);
		}
		
	}
	
}