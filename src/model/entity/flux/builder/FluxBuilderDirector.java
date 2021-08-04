package model.entity.flux.builder;

import model.entity.band.Band;
import model.entity.flux.Flux;

public class FluxBuilderDirector {
		
	public static Flux create(int ID, Double value, Double error, Band myBand)
	{
		Flux obj = new Flux(ID);
		
		obj.setValue(value);
		obj.setError(error);
		obj.setMyBand(myBand);
		
		return obj;
	}
}
