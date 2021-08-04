package model.entity.clump.builder;

import model.entity.clump.Clump;
import model.entity.map.Map;

public abstract class ClumpBuilder {

	protected Clump myClump;

	public void createClump(int arg0) {
		this.myClump = new Clump(arg0);
	}

	public Clump getClump() {
		return this.myClump;
	}

	public abstract void setClumpType();

	public void setClumpData(Double temperature, Double galacticLongitude, Double galacticLatitude,
			Double surfaceDensity, Double bolometricTemperatureMassRatio, Map mapObject, Double mass) {
		this.myClump.setMyMap(mapObject);
		this.myClump.setTemperature(temperature);
		this.myClump.setGalacticLongitude(galacticLongitude);
		this.myClump.setGalacticLatitude(galacticLatitude);
		this.myClump.setSurfaceDensity(surfaceDensity);
		this.myClump.setBolometricTemperatureMassRatio(bolometricTemperatureMassRatio);
		this.myClump.setMass(mass);
	}
}

