package pieces;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import game.TimerClock;
import pieces.ChessPiece;
/**
 * 
 * @author David
 *
 */
public class CoolDownAnimation extends JPanel {
	
	//CoolDown Variables
    //private BufferedImage background;
	public TimerClock A_Clock = new TimerClock();

    private float progress = 0;
    private long startedAt;
    private long timeout = 3000; //3 seconds
    private int width, height, column, row;
    private ScheduledExecutorService ex = Executors.newSingleThreadScheduledExecutor();
    ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
    //public Timer timer; //lol
    
    public Timer timer;
     
	public CoolDownAnimation(ChessPiece A_Piece)
	{
		A_Clock = ChessPiece.A_Clock;
		width = A_Piece.width;
		height = A_Piece.height;
		column = A_Piece.column;
		row = A_Piece.row;
		timeout = A_Piece.time_limit;
		
		
	}
	/**
	 * 
	 * @param sets the amount of time the cooldowns take
	 */
	public void setTimeout(int timeout) {
        this.timeout = timeout;
    }
	/**
	 * 
	 * @return returns the amount of time the cooldowns take
	 */
    public long getTimeout() {
        return timeout;
    }
    /**
     * 
     * @param sets progress
     */
    public void setProgress(float progress) {
        this.progress = progress;
        //repaint();
    }
    /**
     * 
     * @return returns progress, which the amount of time that has passed in relation to the time limit
     */
    public float getProgress() {
        return progress;
    }
    
    /**
     * Preconditions: Chess piece has been moved
     * Runs the timer, which then calls CoolDownAnimation to draw the image
     * Postconditions: Timer is stopped and image should be finished drawing
     * @param g Graphics to be passed onto CoolDownAnimation
     */
    public void executeTimeout(Graphics g) {
        if (timer == null) {
            timer = new Timer(0, new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                	boolean wasPaused = false;
                	/*if(A_Clock.get_isPaused())
                	{
                		while(A_Clock.get_isPaused())
                		{
                			//timer.setDelay(1);
                		}
                	}
                	else if(timer != null && !A_Clock.get_isPaused() && wasPaused)
                	{
                		executeTimeout(g);
                		timer.start();
                		wasPaused = false;
                	}*/
                	
                    long diff = A_Clock.return_milli_time() - startedAt;
                    float progress = diff / (float) timeout;
                    CoolDownAnimation(g);
                    if (diff >= timeout) {
                        progress = 1f;
                        timer.stop();
                    }
                    setProgress(progress);
                    repaint();
                    
                }
            });
        } else if (timer.isRunning()) {
            timer.stop();
        }

        startedAt = A_Clock.return_milli_time();
        timer.start();
    }


        //startedAt = A_Clock.return_milli_time();
    
    /**
     * Preconditions: Timer is running
     * Postconditions: Image is drawn based off of progress of the timer
     * @param g The graphics to draw the image
     */
    public void CoolDownAnimation(Graphics g) {
        //super.CoolDownAnimation(g);
        
            Graphics2D g2d = (Graphics2D) g.create();


            //g2d.fillArc((int) (width*0.1+column*width), (int)(height*0.1+row*height), (int)(width*0.8), (int)(height*0.8), 90, (int) (360f * (1f - progress)));
            if((column + row)%2 == 0)
            {
            	Color myColour = new Color(247,246,228, 255);
            	g2d.setColor(myColour);
            }
            else{
            	Color myColour = new Color(36,14,1, 255);
            	g2d.setColor(myColour);
            }
            	g2d.fillRect(column * 100, row * 100, width, (int) (height * (1f - progress)));
           
            g2d.dispose();
       
    }
   
    
}
