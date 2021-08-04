package model.database.satellite_instrument;

import model.database.AbstractDatabase;

public class SatelliteInstrumentDB extends AbstractDatabase {

	private static SatelliteInstrumentDB myInstance = null;

	private static final String TABLE_NAME = "satelliteinstrument";
	private static final String COLUMN_SATELLITE_ID = "satelliteinstrument_satellite_id";
	private static final String COLUMN_INSTRUMENT_ID = "satelliteinstrument_instrument_id";

	/**
	 * This method is used to get an instance of {@code SatelliteAgencyDB}
	 * class.
	 * 
	 * @return An {@code SatelliteAgencyDB} object.
	 */
	public synchronized static SatelliteInstrumentDB getInstance() {
		if (myInstance == null)
			myInstance = new SatelliteInstrumentDB();
		return myInstance;
	}

	@Override
	public void createTable() {
		if (!tableExist(TABLE_NAME))
			executeWriteQuery(importSQL(getClass().getResourceAsStream("SQL_SatelliteInstrument.sql")));
	}

	/**
	 * This method is used to insert a new {@code SatelliteAgencyDB} object.
	 * 
	 * @param satelliteID
	 * @param agencyID
	 * 
	 * @param name
	 *            - Represents a {@code String} object.
	 * @return An {@code int}.
	 */
	public void insert(int satelliteID, int agencyID) {

		// Generating query...
		String SQL = String.format("INSERT INTO %s (%s, %s) VALUES (%s, %s)", TABLE_NAME, COLUMN_SATELLITE_ID,
				COLUMN_INSTRUMENT_ID, satelliteID, agencyID);

		// Perform query
		executeWriteQuery(SQL);
	}
}
