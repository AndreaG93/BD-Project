package model.database.source_source;

import model.database.AbstractDatabase;

public class SourceSourceDB extends AbstractDatabase {
	
	private static SourceSourceDB myInstance = null;

	private static final String TABLE_NAME = "SourceSource";
	private static final String COLUMN_MIPSGAL_ID = "MipsgalSourceID";
	private static final String COLUMN_GLIMPSE_ID = "GlimpseSourceID";

	/**
	 * This method is used to get an instance of {@code SourceSourceDB}
	 * class.
	 * 
	 * @return An {@code SourceSourceDB} object.
	 */
	public synchronized static SourceSourceDB getInstance() {
		if (myInstance == null)
			myInstance = new SourceSourceDB();
		return myInstance;
	}

	@Override
	public void createTable() {
		if (!tableExist(TABLE_NAME))
			executeWriteQuery(importSQL(getClass().getResourceAsStream("SQL_SourceSource.sql")));
	}
	
	/**
	 * 
	 * @param mipsgalID
	 * @param glimpseID
	 * @return
	 */
	public int insert(int mipsgalID, int glimpseID) {

		String SQL = String.format("INSERT INTO %s (%s, %s) VALUES (%s, %s)", TABLE_NAME, COLUMN_MIPSGAL_ID,
				COLUMN_GLIMPSE_ID, mipsgalID, glimpseID);
	
		// Perform query
		return executeWriteQueryGettingLastVal(SQL);
	}

	

}
