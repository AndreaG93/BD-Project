package model.database.satellite_agency;

import model.database.AbstractDatabase;

public class SatelliteAgencyDB extends AbstractDatabase {

	private static SatelliteAgencyDB myInstance = null;
	private static final String TABLE_NAME = "satelliteagency";
	private static final String COLUMN_SATELLITE_ID = "satelliteagency_satellite_id";
	private static final String COLUMN_AGENCY_ID = "satelliteagency_agency_id";

	/**
	 * This method is used to get an instance of {@code SatelliteAgencyDB}
	 * class.
	 * 
	 * @return An {@code SatelliteAgencyDB} object.
	 */
	public synchronized static SatelliteAgencyDB getInstance() {
		if (myInstance == null)
			myInstance = new SatelliteAgencyDB();
		return myInstance;
	}

	@Override
	public void createTable() {
		if (!tableExist(TABLE_NAME)) 
			executeWriteQuery(importSQL(getClass().getResourceAsStream("SQL_SatelliteAgency.sql")));
	}
	
	/**
	 * 
	 * @param satelliteID
	 *            - Represents an {@code int}.
	 * @param agencyID
	 *            - Represents an {@code int}.
	 * @return
	 */
	public int insert(int satelliteID, int agencyID) {

		// Generating query...
		String SQL = String.format("INSERT INTO %s (%s, %s) VALUES (%s, %s)", TABLE_NAME, COLUMN_SATELLITE_ID,
				COLUMN_AGENCY_ID, satelliteID, agencyID);
		

		// Perform query...
		return executeWriteQueryGettingLastVal(SQL);
	}
}
