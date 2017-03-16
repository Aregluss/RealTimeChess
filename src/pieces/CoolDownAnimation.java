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
	public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public long getTimeout() {
        return timeout;
    }

    public void setProgress(float progress) {
        this.progress = progress;
        //repaint();
    }

    public float getProgress() {
        return progress;
    }

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
   
    
    public void applyQualityRenderingHints(Graphics2D g2d) {
        g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
    }

}
