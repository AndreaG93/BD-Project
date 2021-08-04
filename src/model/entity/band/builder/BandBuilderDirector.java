package model.entity.band.builder;

import model.entity.band.Band;

public class BandBuilderDirector {

	public static Band create(int id, double resolution, double wavelength)
	{
		Band obj = new Band(id);
		obj.setResolution(resolution);
		obj.setWavelength(wavelength);
		
		return obj;
	}
}


