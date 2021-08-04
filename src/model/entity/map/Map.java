package model.entity.map;

public class Map {
	
	private final int ID;
	private String name;
	
	
	public Map(int iD) {
		this.ID = iD;
	}
	
	
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public int getID() {
		return ID;
	}


	@Override
	public String toString() {

		String myString = "Map #" + this.ID + "\n";
		myString += "Name: " + this.name;
		return myString;
		
	}
	
	
	

}
