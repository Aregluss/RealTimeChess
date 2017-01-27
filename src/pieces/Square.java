package pieces;

public class Square {

	boolean isEmpty;
	boolean isDangerous;
	boolean lightOn;
	
	public Square(boolean empty, boolean danger)
	{
		isEmpty = empty;
		isDangerous = danger;
		lightOn = false;
	}
	
}
