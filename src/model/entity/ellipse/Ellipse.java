package model.entity.ellipse;

import model.entity.band.Band;

public class Ellipse {
	
	private Double majorAxis;
	private Double minorAxis;
	private Double angleOfRotation;
	private Band band;
	


	public Double getMajorAxis() {
		return majorAxis;
	}

	public void setMajorAxis(Double majorAxis) {
		this.majorAxis = majorAxis;
	}

	public Double getMinorAxis() {
		return minorAxis;
	}

	public void setMinorAxis(Double minorAxis) {
		this.minorAxis = minorAxis;
	}

	public Double getAngleOfRotation() {
		return angleOfRotation;
	}

	public void setAngleOfRotation(Double angleOfRotation) {
		this.angleOfRotation = angleOfRotation;
	}

	public Band getBand() {
		return band;
	}

	public void setBand(Band band) {
		this.band = band;
	}



	
	
	
	

}
