package model.entity.instrument;

import java.util.Vector;

import model.entity.band.Band;
import model.entity.map.Map;

public class Instrument {
	
	private final int ID;
	private String name;
	private Map map;
	


	private Vector<Band> supportedBands;
	
	public Instrument(int iD) {
		ID = iD;
		this.supportedBands = new Vector<>();
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

		String myString = "Instrument #" + this.ID + "\n";
		myString += this.name + "\nSupported Bands:\n";
		for (Band obj : this.supportedBands)
			myString += "\tResolution: " + obj.getResolution() + ", Wavelength: " + obj.getWavelength() + "\n";
		
		return myString;
	}
	
	public Map getMap() {
		return map;
	}


	public void setMap(Map map) {
		this.map = map;
	}

	public Vector<Band> getSupportedBands() {
		return supportedBands;
	}


	public void setSupportedBands(Vector<Band> supportedBands) {
		this.supportedBands = supportedBands;
	}
}
