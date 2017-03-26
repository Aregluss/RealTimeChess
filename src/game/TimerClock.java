package game;

import java.time.Clock;

public class TimerClock{ 
	private static Clock clock = Clock.systemDefaultZone();
	private long startTime = clock.millis();
	private long pauseStart = 0;
	private long pauseEnd;
	private boolean isPaused = false;
	
	public long return_time() 
	{
		if(isPaused)
		{
			return (pauseStart - startTime)/1000;
		}
		return (clock.millis() - startTime)/1000; //returns seconds
	}
	
	public long return_milli_time()
	{
		if(isPaused)
		{
			return (pauseStart - startTime);
		}
		return (clock.millis() - startTime); //return milliseconds
	}
	
	public void pause()
	{
		if(!isPaused)
		{
			pauseStart = clock.millis();
			isPaused = true;
		}
	}
	
	public void continueTime()
	{
		if(isPaused)
		{
			pauseEnd = clock.millis();
			long difference = pauseEnd - pauseStart;
			startTime += difference;
			isPaused = false;
		}	
	}
	
	public boolean get_isPaused()
	{
		return isPaused;
	}
}
