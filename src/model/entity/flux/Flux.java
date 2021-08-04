package model.entity.flux;

import model.entity.band.Band;

public class Flux {

	private final int ID;
	public Band getMyBand() {
		return myBand;
	}

	private Band myBand;
	private Double error;
	private Double value;
	
	public Flux(int arg0) {
		ID = arg0;
	}
	
	public void setMyBand(Band myBand) {
		this.myBand = myBand;
	}

	public Double getError() {
		return error;
	}

	public void setError(Double error) {
		this.error = error;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public int getID() {
		return ID;
	}

	



	
	
}
