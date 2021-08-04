package model.database.source_flux;

import model.database.AbstractDatabase;

public class SourceFluxDB extends AbstractDatabase {

	private static SourceFluxDB myInstance = null;
	private static final String TABLE_NAME = "sourceflux";
	private static final String COLUMN_SOURCE_ID = "sourceflux_source_id";
	private static final String COLUMN_SOURCE_MAP_ID = "sourceflux_source_map_id";
	private static final String COLUMN_FLUX_ID = "sourceflux_flux_id";

	/**
	 * This method is used to get an instance of {@code SourceFluxDB} class
	 * 
	 * @return A {@code SourceFluxDB} object.
	 */
	public synchronized static SourceFluxDB getInstance() {
		if (myInstance == null)
			myInstance = new SourceFluxDB();
		return myInstance;
	}

	@Override
	public void createTable() {
		if (!tableExist(TABLE_NAME))
			executeWriteQuery(importSQL(getClass().getResourceAsStream("SQL_SourceFlux.sql")));
	}

	/**
	 * This method perform an INSERT operation.
	 * 
	 * @param fluxID
	 *            - Represents an {@code int}.
	 * @param sourceID
	 *            - Represents an {@code int}.
	 */
	public String getInsertQuery(String sourceId, int mapId) {

		// Generating query...
		String SQL = String.format("INSERT INTO %s (%s, %s, %s) VALUES ('%s', %s, LastVal())", TABLE_NAME,
				COLUMN_SOURCE_ID, COLUMN_SOURCE_MAP_ID, COLUMN_FLUX_ID, sourceId, mapId);

		SQL += String.format(" ON CONFLICT (%s, %s, %s) DO NOTHING;\n", COLUMN_SOURCE_ID, COLUMN_SOURCE_MAP_ID,
				COLUMN_FLUX_ID);

		return SQL;
	}

	/**
	 * This method perform an INSERT operation.
	 * 
	 * @param fluxID
	 *            - Represents an {@code int}.
	 * @param sourceID
	 *            - Represents an {@code int}.
	 */
	public void insert(int fluxID, int sourceID) {

		// Generating query...
		String SQL = String.format("INSERT INTO %s (%s, %s) VALUES (%s, %s)", TABLE_NAME, COLUMN_FLUX_ID,
				COLUMN_SOURCE_ID, fluxID, sourceID);

		// Perform query...
		executeWriteQuery(SQL);
	}
}
