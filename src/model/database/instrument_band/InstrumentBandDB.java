package model.database.instrument_band;

import model.database.AbstractDatabase;

public class InstrumentBandDB extends AbstractDatabase {

	private static InstrumentBandDB myInstance = null;
	private static final String TABLE_NAME = "instrumentband";
	public static final String COLUMN_INSTRUMENT_ID = "instrumentband_instrument_id";
	public static final String COLUMN_BAND_ID = "instrumentband_band_id";

	/**
	 * This method is used to get an instance of {@code InstrumentBandDB} class.
	 * 
	 * @return An {@code InstrumentBandDB} object.
	 */
	public synchronized static InstrumentBandDB getInstance() {
		if (myInstance == null)
			myInstance = new InstrumentBandDB();
		return myInstance;
	}

	@Override
	public void createTable() {
		if (!tableExist(TABLE_NAME))
			executeWriteQuery(importSQL(getClass().getResourceAsStream("SQL_InstrumentBand.sql")));
	}
	

	/**
	 * This method perform an INSERT operation.
	 * 
	 * @param instrumentID
	 *            - Represents an {@code int}.
	 * @param bandID
	 *            - Represents an {@code int}.
	 * @return An {@code int}.
	 */
	public void insert(int instrumentID, int bandID) {

		// Generating query...
		String SQL = String.format("INSERT INTO %s (%s, %s) VALUES (%s, %s)", TABLE_NAME, COLUMN_INSTRUMENT_ID,
				COLUMN_BAND_ID, instrumentID, bandID);

		// Perform query
		executeWriteQuery(SQL);
	}
}
