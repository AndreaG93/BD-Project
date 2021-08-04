package model.entity.agency;

public class Agency {
	
	
	
	private final int ID;
	private String name;
	
	public Agency(int iD) {
		super();
		ID = iD;
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
		return "Agency #" + ID + "\nname: " + name;
	}
	
	
	

}
