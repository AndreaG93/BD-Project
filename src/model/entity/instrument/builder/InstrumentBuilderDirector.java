package model.entity.instrument.builder;

import model.entity.instrument.Instrument;
import model.entity.map.Map;

public class InstrumentBuilderDirector {

	public static Instrument create(int ID, String name, Map map)
	{
		Instrument myInstrument = new Instrument(ID);
		myInstrument.setName(name);
		myInstrument.setMap(map);
	
		return myInstrument;
	}
}
