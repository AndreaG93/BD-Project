package model.database.clump_flux;

import model.database.AbstractDatabase;

public class ClumpFluxDB extends AbstractDatabase {
	
	private static ClumpFluxDB myInstance = null;
	private static final String TABLE_NAME = "clumpflux";
	private static final String COLUMN_CLUMP_ID = "clumpflux_clump_id";
	private static final String COLUMN_MAP_ID = "clumpflux_map_id";
	private static final String COLUMN_FLUX_ID = "clumpflux_flux_id";

	/**
	 * This method is used to get an instance of {@code ClumpFluxDB} class.
	 * 
	 * @return An {@code ClumpFluxDB} object.
	 */
	public synchronized static ClumpFluxDB getInstance() {
		if (myInstance == null)
			myInstance = new ClumpFluxDB();
		return myInstance;
	}

	@Override
	public void createTable() {
		if (!tableExist(TABLE_NAME))
			executeWriteQuery(importSQL(getClass().getResourceAsStream("SQL_ClumpFlux.sql")));
	}
	
	/**
	 * This method is used to perform an INSERT operation.
	 * 
	 * @param clumpID
	 *            - Represents an {@code int}.
	 * @param mapID
	 *            - Represents an {@code int}.
	 * @param fluxID
	 *            - Represents an {@code int}.
	 */
	public void insert(int clumpID, int mapID, int fluxID) {

		// Generating query...
		String SQL = String.format("INSERT INTO %s (%s, %s, %s) VALUES (%s, %s, %s) ", TABLE_NAME, COLUMN_CLUMP_ID,
				COLUMN_MAP_ID, COLUMN_FLUX_ID, clumpID, mapID, fluxID);
		
		SQL += String.format("ON CONFLICT (%s, %s, %s) DO NOTHING ", COLUMN_CLUMP_ID, COLUMN_MAP_ID, COLUMN_FLUX_ID);
		
		// Perform query
		executeWriteQuery(SQL);
	}
	
	
	
	public String getInsertQuery(String clumpID, String mapID) {

		// Generating query...
		String SQL = String.format("INSERT INTO %s (%s, %s, %s) VALUES (%s, %s, LastVal()) ", TABLE_NAME, COLUMN_CLUMP_ID,
				COLUMN_MAP_ID, COLUMN_FLUX_ID, clumpID, mapID);
		
		SQL += String.format("ON CONFLICT (%s, %s, %s) DO NOTHING; ", COLUMN_CLUMP_ID, COLUMN_MAP_ID, COLUMN_FLUX_ID);
		
		return SQL;
	}
	
}
