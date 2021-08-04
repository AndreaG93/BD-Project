package model.database;

public class DatabaseUtility {

	private static DatabaseUtility myInstance = null;
	private int lastSelectCount = 1;
	
	private DatabaseUtility() {
	}

	public static DatabaseUtility getInstance() {
		if (myInstance == null)
			myInstance = new DatabaseUtility();
		return myInstance;
	}
	
	public void setLastSelectCount(int arg0)
	{
		lastSelectCount = arg0;
		
	}
	
	public int getLastSelectCount()
	{
		return lastSelectCount;
	}
}
