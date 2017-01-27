package Default;

import java.awt.BorderLayout;
import java.awt.CardLayout;

import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import menu.StartScreen;

public class RealTimeChess {

    private static JPanel menuContainer;
	private static Window frame;
	//private static Game game;
	
	public static void main(String[] args)
	{
		doUITasks();
		JPanel gamePanel = new JPanel();
		
		frame = new Window();
		//Map map = new Map();			
		//Grid.initialize(map);
		//game = new Game(map);
		//HUD hud = new HUD();
		
		gamePanel.setLayout(new BorderLayout());
		//gamePanel.add(map, BorderLayout.CENTER);
		//gamePanel.add(hud, BorderLayout.SOUTH);
		
		
		
		menuContainer = new JPanel();
		CardLayout cl = new CardLayout();
		menuContainer.setLayout(cl);
		
		StartScreen start = new StartScreen();
		//ServerScreen server = new ServerScreen();
		//ClientScreen client = new ClientScreen();
		//HeroSelection selection = new HeroSelection();
		
		
		
		menuContainer.add(start,"1");
	//	menuContainer.add(selection,"2");
	//	menuContainer.add(gamePanel,"3");
		
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
		
		if(s.equals("3"))
		{
			///new Thread(game).start();
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