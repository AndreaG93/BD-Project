package model.entity.clump;

import java.util.Vector;

import model.entity.clump.type.ClumpType;
import model.entity.ellipse.Ellipse;
import model.entity.flux.Flux;
import model.entity.map.Map;

public class Clump {

	private final int ID;
	private Double galacticLongitude;
	private Double galacticLatitude;
	private Double temperature;
	private Double bolometricTemperatureMassRatio;
	private Double surfaceDensity;
	private Double mass;
	private Map myMap;
	private ClumpType type;
	private Vector<Ellipse> ellipses = new Vector<>();
	private Vector<Flux> fluxVector = new Vector<>();

	/**
	 * Constructs a newly allocated {@code Clump} object;
	 * 
	 * @param arg0
	 *            - Represents an {@code int}.
	 * @param arg1 
	 */
	public Clump(int arg0) {
		this.ID = arg0;
	}
	
	public Double getMass() {
		return mass;
	}
	
	public void setMass(Double mass) {
		this.mass = mass;
	}

	public Double getGalacticLongitude() {
		return galacticLongitude;
	}

	public void setGalacticLongitude(Double galacticLongitude) {
		this.galacticLongitude = galacticLongitude;
	}


	public Double getGalacticLatitude() {
		return galacticLatitude;
	}


	public void setGalacticLatitude(Double galacticLatitude) {
		this.galacticLatitude = galacticLatitude;
	}


	public Double getTemperature() {
		return temperature;
	}


	public void setTemperature(Double temperature) {
		this.temperature = temperature;
	}


	public Double getBolometricTemperatureMassRatio() {
		return bolometricTemperatureMassRatio;
	}


	public void setBolometricTemperatureMassRatio(Double bolometricTemperatureMassRatio) {
		this.bolometricTemperatureMassRatio = bolometricTemperatureMassRatio;
	}


	public Double getSurfaceDensity() {
		return surfaceDensity;
	}


	public void setSurfaceDensity(Double surfaceDensity) {
		this.surfaceDensity = surfaceDensity;
	}


	public Map getMyMap() {
		return myMap;
	}


	public void setMyMap(Map myMap) {
		this.myMap = myMap;
	}


	public ClumpType getType() {
		return type;
	}


	public void setType(ClumpType type) {
		this.type = type;
	}


	public Vector<Ellipse> getEllipses() {
		return ellipses;
	}


	public void setEllipses(Vector<Ellipse> ellipses) {
		this.ellipses = ellipses;
	}


	public Vector<Flux> getFluxVector() {
		return fluxVector;
	}


	public void setFluxVector(Vector<Flux> fluxVector) {
		this.fluxVector = fluxVector;
	}


	public int getID() {
		return ID;
	}
	
	

	@Override
	public String toString() {

		String myString = "Clump #" + this.ID;
		myString += "Map: " + this.myMap.getName();
		myString += "Position: GLON:" + this.galacticLongitude + ", GLAT:" + this.galacticLatitude;
		
		for (Flux obj : this.fluxVector)
		{
			myString += "----------------------------------------------------\n";
			myString += "\tFlux Value: " + obj.getValue() + ", Error: " + obj.getError() + "\n";
			myString += "Band: Resolution:" + obj.getMyBand().getResolution() + "Wavelenght: " + obj.getMyBand().getWavelength();
		}
			
		for (Ellipse obj : this.ellipses)
		{
			myString += "----------------------------------------------------\n";
			myString += "Major axis: " + obj.getMajorAxis() + "\n";
			myString += "Minor axis: " + obj.getMinorAxis() + "\n";
			
			myString += "Band: Resolution:" + obj.getBand().getResolution() + "Wavelenght: " + obj.getBand().getWavelength();
		}
		
		for (Ellipse obj : this.ellipses)
		{
			myString += "----------------------------------------------------\n";
			myString += "Angle of rotaion axis: " + obj.getAngleOfRotation() + "\n";
		
			myString += "Band: Resolution:" + obj.getBand().getResolution() + "Wavelenght: " + obj.getBand().getWavelength();
		}
		
		return myString;
	}


	
	
	
	
	
	
	
	
	
	
	
	

}
