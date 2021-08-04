package model.managers;

import java.time.LocalDate;
import java.util.Vector;
import model.Support;
import model.database.satellite.SatelliteDB;
import model.database.satellite_agency.SatelliteAgencyDB;
import model.database.satellite_instrument.SatelliteInstrumentDB;
import model.entity.agency.Agency;
import model.entity.instrument.Instrument;
import model.entity.satellite.Satellite;

public class SatelliteManager {

	private SatelliteAgencyDB mySatelliteAgencyDB = SatelliteAgencyDB.getInstance();
	private SatelliteInstrumentDB mySatelliteInstrumentDB = SatelliteInstrumentDB.getInstance();
	private SatelliteDB mySatelliteDB = SatelliteDB.getInstance();

	/**
	 * This method is used to register an {@code Satellite} object.
	 * 
	 * @param name
	 *            - Represents a {@code String} object.
	 * @param firstMissionDate
	 *            - Represents a {@code LocalDate} object.
	 * @param lastMissionDate
	 *            - Represents a {@code LocalDate} object.
	 * @param myAgencies
	 *            - Represents a {@code Vector<Agency>} object.
	 * @param myInstruments
	 *            - Represents a {@code Vector<Instrument>} object.
	 * @throws Exception
	 */
	public void registerNewSatellite(String name, LocalDate firstMissionDate, LocalDate lastMissionDate,
			Vector<Agency> myAgencies, Vector<Instrument> myInstruments) throws Exception {

		nameRegistrationCheck(name);
		firstMissionDateRegistrationCheck(firstMissionDate);
		
		if (this.mySatelliteDB.getByName(name) != null)
			throw new Exception("Specified satellite's name already exists.");
		
		if (myAgencies.isEmpty())
			throw new Exception("List of agencies is empty.");
		
		if (myInstruments.isEmpty())
			throw new Exception("List of instruments is empty.");
		
	
		int ID = this.mySatelliteDB.insert(name, firstMissionDate, lastMissionDate);

		// Associates agencies
		for (Agency obj : myAgencies)
			this.mySatelliteAgencyDB.insert(ID, obj.getID());

		// Associates Instruments
		for (Instrument obj : myInstruments)
			this.mySatelliteInstrumentDB.insert(ID, obj.getID());
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
	 * This method is used to verify if specified FirstMissionDate is valid.
	 * 
	 * @param arg0
	 *            - Represents a {@code LocalDate} object.
	 * @throws Exception
	 */
	public void firstMissionDateRegistrationCheck(LocalDate arg0) throws Exception {
		Support.nullCheck(arg0, "First Mission Date");
	}
	
	
	public void removeSatellite(Satellite arg0)
	{
		this.mySatelliteDB.remove(arg0);
	}
	
	
	public Vector<Satellite> getAllSatellites()
	{
		return this.mySatelliteDB.getAll();
	}
	
	
	
	

}
