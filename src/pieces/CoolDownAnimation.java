package pieces;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.util.*; //new timer?

//import javax.swing.Timer;
//import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.*;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import game.TimerClock;
import pieces.ChessPiece;

public class CoolDownAnimation extends JPanel {
	
	//CoolDown Variables
    //private BufferedImage background;
	public TimerClock A_Clock = new TimerClock();

    private float progress = 0;
    private long startedAt;
    private int timeout = 3000; //3 seconds
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
		
		
	}
	public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public int getTimeout() {
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
            //applyQualityRenderingHints(g2d);
            //int xloc = x; 
            //int yloc = y;
            //g2d.drawImage(image , (int) (width*0.1+column*width), (int)(height*0.1+row*height), (int)(width*0.8), (int)(height*0.8), null);

            //g2d.setComposite(AlphaComposite.SrcOver.derive(0.5f));
            

            //g2d.fillArc((int) (width*0.1+column*width), (int)(height*0.1+row*height), (int)(width*0.8), (int)(height*0.8), 90, (int) (360f * (1f - progress)));
            if((column + row)%2 == 0)
            {
            	Color myColour = new Color(236,236,236, 255);
            	g2d.setColor(myColour);
            }
            else{
            	Color myColour = new Color(78,78,78, 255);
            	g2d.setColor(myColour);
            }
            	g2d.fillRect(column * 100, row * 100, width, (int) (height * (1f - progress)));
            //repaint();
            //Color myColour = new Color(0,0,255, 127);
            //g2d.setColor(myColour);
            //g2d.fillArc((int) (width*0.1+column*width), (int)(height*0.1+row*height), (int)(width*0.8), (int)(height*0.8), 90, (int) (360f * (1f - progress/2)));

         //   Color myColour = new Color(255,255,255, 127);
          //  applyQualityRenderingHints(g2d);
            
            //g2d.setComposite(AlphaComposite.SrcOver.derive(0.5f));
            
            //g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_ANTIALIAS_ON);
            //g2d.fillArc(x, y, radius, radius, startAngle, arcAngle);
            //g2d.clearRect((int)(width*0.1+column*width), (int)(height*0.1+row*height), (int)(width*0.8), (int)(height*0.8));
            //repaint();
            g2d.dispose();
       
    }
    /*public void CoolDown(Graphics g) {
        //super.CoolDownAnimation(g);
    	startedAt = A_Clock.return_milli_time();
    	final Runnable drawer = new Runnable() {
    	       public void run() {
            Graphics2D g2d = (Graphics2D) g.create();
            //int radius = Math.max((int) (height), (int) (width)) / 2;

            g2d.fillArc((int) (width*0.1+column*width), (int)(height*0.1+row*height), (int)(width*0.8), (int)(height*0.8), 90, (int) (360f * (1f - progress)));
            g2d.dispose();}
    	};
    	final ScheduledFuture<?> drawerHandle = scheduledExecutorService.scheduleAtFixedRate(drawer, 10, 10, TimeUnit.MILLISECONDS);
    	scheduledExecutorService.schedule(new Runnable() {
    		       public void run() { 
    		    	   long diff = A_Clock.return_milli_time() - startedAt;
                       float progress = diff / (float) timeout;
                       //CoolDownAnimation(g);
                       if (diff >= timeout) {
                           progress = 1f;
                           timer.stop();
                       }
    		    	   drawerHandle.cancel(true); setProgress(progress); }
    		     }, 3000, TimeUnit.MILLISECONDS);
    }*/
    
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
