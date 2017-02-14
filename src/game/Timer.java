package game;

public class Timer 
	private static Clock clock = Clock.systemDefaultZone();
	private long startTime = clock.millis();
	
	public long return_time() 
	{
		return (clock.millis() - startTime)/1000; //returns seconds
	}
	public long return_milli_time()
	{
		return (clock.millis() - startTime); //return milliseconds
	}
}
