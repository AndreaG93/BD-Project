package model.entity.band;

public class Band {

	private final int ID;
	private double resolution;
	private double wavelength;

	public Band(int iD) {
		ID = iD;
	}

	@Override
	public String toString() {

		String myString = "Band #" + this.getID() + "\n";
		myString += "\t Resolution: " + this.resolution + "\n";
		myString += "\t Wavelength: " + this.wavelength + "\n";
		
		return myString;
	}

	public double getResolution() {
		return resolution;
	}

	public void setResolution(double resolution) {
		this.resolution = resolution;
	}

	public double getWavelength() {
		return wavelength;
	}

	public void setWavelength(double wavelength) {
		this.wavelength = wavelength;
	}

	public int getID() {
		return ID;
	}
}
