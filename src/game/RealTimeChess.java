package game;

import java.awt.BorderLayout;
import java.awt.CardLayout;

import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import menu.Rules;
import menu.StartScreen;
import pieces.Square;
import game.GameBoard;

public class RealTimeChess {

    private static JPanel menuContainer;
	private static Window frame;
	private static GameBoard ChessGame;
	
	public static void main(String[] args)
	{
		doUITasks();
		JPanel gamePanel = new JPanel();
		
		frame = new Window();
		GraphicsBoard grBoard = new GraphicsBoard();
		ChessGame = new GameBoard(1, grBoard);
		
		
		
		gamePanel.setLayout(new BorderLayout());
		gamePanel.add(grBoard, BorderLayout.CENTER);
		//gamePanel.add(hud, BorderLayout.SOUTH);
		
		
		
		menuContainer = new JPanel();
		CardLayout cl = new CardLayout();
		menuContainer.setLayout(cl);
		
		StartScreen start = new StartScreen();
		Rules rules = new Rules();

		
		
		menuContainer.add(start,"1");
		menuContainer.add(gamePanel,"2");
		menuContainer.add(rules,"3");
		
		frame.add(menuContainer);

		
		frame.setVisible(true);
	}
	
	//we need to make main constucton like mario that shely had insead of this
	/**Changes the visible panel in menuContainer to the panel that was added with the specified name s
	 * @param s the name specified when the panel was added to menuContainer
	 */
	public static void switchPanel(String s)
	{
		((CardLayout)menuContainer.getLayout()).show(menuContainer, s);
		frame.requestFocus();
		
		if(s.equals("2"))
		{
			new Thread(ChessGame).start();
		}
	}
	
	public static void doUITasks(){
		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception e) {
		    // If Nimbus is not available, you can set the GUI to another look and feel.
			//change javadoc above
		}
		
	}
}