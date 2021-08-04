package model.managers;

import java.util.Vector;
import model.Support;
import model.database.instrument.InstrumentDB;
import model.database.instrument_band.InstrumentBandDB;
import model.database.map.MapDB;
import model.entity.band.Band;
import model.entity.instrument.Instrument;

public class InstrumentManager {

	private InstrumentBandDB myInstrumentBandDB = InstrumentBandDB.getInstance();
	private InstrumentDB myInstrumentDB = InstrumentDB.getInstance();

	/**
	 * This method is used to register an {@code Instrument} object.
	 * 
	 * @param name
	 *            - Represents a {@code String} object.
	 * @param bands
	 *            - Represents a {@code Vector<Band>} object.
	 * @throws Exception
	 */
	public void registerNewInstrument(String name, Vector<Band> bands, String nameName) throws Exception {
		nameRegistrationCheck(name);
		Support.nullCheck(nameName, "Map");

		int ID = this.myInstrumentDB.insert(name, MapDB.getInstance().getMapByName(nameName).getID());

		// Associates agencies
		for (Band obj : bands)
			this.myInstrumentBandDB.insert(ID, obj.getID());
	}

	/**
	 * This method is used to verify if specified name is valid.
	 * 
	 * @param arg0
	 *            - Represents a {@code String} object.
	 * @throws Exception
	 */
	public void nameRegistrationCheck(String arg0) throws Exception {
		Support.nullCheck(arg0, "Name");
		Support.emptyStringCheck(arg0, "Name");
		Support.maxLengthCheck(arg0, "Name");
	}

	/**
	 * This method is used to get a {@code Vector} object that contains all
	 * {@code Instrument} object stored into database.
	 * 
	 * @return A {@code Vector} object.
	 */
	public Vector<Instrument> getAllInstrumentVector() {
		return this.myInstrumentDB.getAll();
	}
	
	
	public void removeInstrument(Instrument arg0)
	{
		this.myInstrumentDB.remove(arg0);
	}
	
	
}
