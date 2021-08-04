package model.database.satellite;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.Vector;
import model.database.AbstractDatabase;
import model.database.agency.AgencyDB;
import model.database.band.BandDB;
import model.database.instrument.InstrumentDB;
import model.database.instrument_band.InstrumentBandDB;
import model.database.map.MapDB;
import model.entity.agency.Agency;
import model.entity.band.Band;
import model.entity.band.builder.BandBuilderDirector;
import model.entity.instrument.Instrument;
import model.entity.instrument.builder.InstrumentBuilderDirector;
import model.entity.map.Map;
import model.entity.satellite.Satellite;

public class SatelliteDB extends AbstractDatabase {

	private static SatelliteDB myInstance = null;

	private static final String TABLE_NAME = "Satellite";
	private static final String COLUMN_ID = "satellite_id";
	private static final String COLUMN_NAME = "satellite_name";
	private static final String COLUMN_FIRST_MISSION_DATE = "satellite_firts_mission_date";
	private static final String COLUMN_LAST_MISSION_DATE = "satellite_last_mission_date";

	/**
	 * This method is used to get an instance of {@code SatelliteDB} class
	 * 
	 * @return A {@code SatelliteDB} object.
	 */
	public synchronized static SatelliteDB getInstance() {
		if (myInstance == null)
			myInstance = new SatelliteDB();
		return myInstance;
	}

	@Override
	public void createTable() {
		if (!tableExist(TABLE_NAME))
			executeWriteQuery(importSQL(getClass().getResourceAsStream("SQL_Satellite.sql")));
	}

	/**
	 * This method perform an INSERT operation.
	 * 
	 * @param name
	 *            - Represents a {@code String} object.
	 * @param firstMissionDate
	 *            - Represents a {@code LocalDate} object.
	 * @param lastMissionDate
	 *            - Represents a {@code LocalDate} object.
	 * @return An {@code int}.
	 */
	public int insert(String name, LocalDate firstMissionDate, LocalDate lastMissionDate) {

		// Generating query...
		String SQL = String.format("INSERT INTO %s (%s, %s, %s) VALUES ('%s','%s', %s)", TABLE_NAME, COLUMN_NAME,
				COLUMN_FIRST_MISSION_DATE, COLUMN_LAST_MISSION_DATE, name, firstMissionDate, convertToStringCheckNullableWithQuote(lastMissionDate));

		// Perform query...
		return executeWriteQueryGettingLastVal(SQL);
	}

	/**
	 * This method perform a DELETE operation.
	 * 
	 * @param arg0
	 *            - Represents an {@code Satellite} object.
	 */
	public void remove(Satellite arg0) {

		String SQL = String.format("DELETE FROM %s WHERE %s = %s", TABLE_NAME, COLUMN_ID, arg0.getID());

		// Perform query...
		executeWriteQuery(SQL);
	}

	/**
	 * This method is used to get data from database.
	 * 
	 * @param SQL
	 *            - Represents a {@code String} object.
	 * @return A {@code Vector} object.
	 */
	private Vector<Satellite> get(String SQL) {

		String query = importSQL(getClass().getResourceAsStream("SQL_QueryGetSatellite.sql")) + "\n";
		query += SQL + "\nORDER BY " + COLUMN_ID;

		// Output vector...
		Vector<Satellite> obj = new Vector<>();

		try {

			// Print query...
			System.out.println("Query:\n" + query);

			openConnection();
			createStatement();
			ResultSet rs = createResultSet(query);

			// Get data
			while (rs.next()) {

				Map myMap = new Map(rs.getInt(MapDB.COLUMN_ID));
				myMap.setName(rs.getString(MapDB.COLUMN_NAME));
				
				Satellite mySatellite = new Satellite(rs.getInt(COLUMN_ID),
						rs.getDate(COLUMN_FIRST_MISSION_DATE).toLocalDate());
				mySatellite.setName(rs.getString(COLUMN_NAME));
				
				if (rs.getDate(COLUMN_LAST_MISSION_DATE) != null)
					mySatellite.setLastMissionDate(rs.getDate(COLUMN_LAST_MISSION_DATE).toLocalDate());

				Agency myAgency = new Agency(rs.getInt(AgencyDB.COLUMN_ID));
				myAgency.setName(rs.getString(AgencyDB.COLUMN_NAME));

				Band myBand = BandBuilderDirector.create(rs.getInt(InstrumentBandDB.COLUMN_BAND_ID),
						rs.getDouble(BandDB.COLUMN_RESOLUTION), rs.getDouble(BandDB.COLUMN_WAVELENGTH));

				Instrument myInstrument = InstrumentBuilderDirector.create(rs.getInt(InstrumentDB.COLUMN_ID),
						rs.getString(InstrumentDB.COLUMN_NAME), myMap);

				// Current satellite...
				boolean satelliteExistence = false;

				// Check existence...
				for (Satellite var0 : obj) {

					if (var0.getID() == mySatellite.getID()) {

						satelliteExistence = true;
						
						Instrument currentInstrument = null;
						boolean instrumentExistence = false;
						boolean agencyExistence = false;
						
						
						// Checks agency existence...
						for (Agency x : var0.getAgencies()) {

							if (x.getID() == myAgency.getID()) {
								agencyExistence = true;
								break;
							}
						}

						// Checks instrument existence...
						for (Instrument x : var0.getInstruments()) {

							if (x.getID() == myInstrument.getID()) {
								currentInstrument = x;
								instrumentExistence = true;
								break;
							}
						}
						
				
						if(!agencyExistence)
						{
							var0.getAgencies().add(myAgency);
						}
						
						
						if (!instrumentExistence) {
							myInstrument.getSupportedBands().add(myBand);
							var0.getInstruments().add(myInstrument);
							break;
						}
						else
						{
							currentInstrument.getSupportedBands().add(myBand);
						}

						break;
					}
				}

				if (!satelliteExistence) {
					myInstrument.getSupportedBands().add(myBand);
					mySatellite.getAgencies().add(myAgency);
					mySatellite.getInstruments().add(myInstrument);
					obj.add(mySatellite);
				}

			}

		} catch (

		Exception e) {
			System.out.println("FAILURE: " + e.getMessage());
			e.printStackTrace();
		} finally {

			// release JDBC resources
			releaseResources();
		}
		return obj;
	}

	public Satellite getByKey(int arg0) {
		Satellite obj = null;

		// Generating query...
		String SQL = String.format("WHERE %s = %s", COLUMN_ID, arg0);

		try {
			obj = get(SQL).firstElement();
		} catch (Exception e) {
			obj = null;
		}

		return obj;
	}

	public Satellite getByName(String arg0) {
		Satellite obj = null;

		// Generating query...
		String SQL = String.format("WHERE %s = '%s'", COLUMN_NAME, arg0);

		try {
			obj = get(SQL).firstElement();
		} catch (Exception e) {
			obj = null;
		}

		return obj;
	}

	public Vector<Satellite> getAll() {

		// Perform query...
		return get("");
	}

}
