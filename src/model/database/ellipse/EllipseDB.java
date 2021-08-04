package model.database.ellipse;

import model.database.AbstractDatabase;

public class EllipseDB extends AbstractDatabase {

	private static EllipseDB myInstance = null;
	private static final String TABLE_NAME = "ellipse";
	public static final String COLUMN_CLUMP_ID = "ellipse_clump_id";
	public static final String COLUMN_CLUMP_MAP_ID = "ellipse_map_id";
	public static final String COLUMN_BAND_ID = "ellipse_band_id";
	public static final String COLUMN_MINOR_AXIS = "ellipse_axis_minor";
	public static final String COLUMN_MAJOR_AXIS = "ellipse_axis_major";
	public static final String COLUMN_ANGLE_OF_ROTATION = "ellipse_angle_rotatio";

	/**
	 * This method is used to get an instance of {@code ClumpDatabase} class.
	 * 
	 * @return An {@code ClumpDatabase} object.
	 */
	public synchronized static EllipseDB getInstance() {
		if (myInstance == null)
			myInstance = new EllipseDB();
		return myInstance;
	}

	@Override
	public void createTable() {
		if (!tableExist(TABLE_NAME))
			executeWriteQuery(importSQL(getClass().getResourceAsStream("SQL_Ellipse.sql")));
	}

	/**
	 * This method is used to insert a new {@code Ellipse} object.
	 * 
	 * @param minorAxis
	 *            - Represents a {@code double}.
	 * @param majorAxis
	 *            - Represents a {@code double}.
	 * @param angleOfRotation
	 *            - Represents a {@code double}.
	 * @return An {@code int}.
	 */
	public void insert(int clumpID, int clumpMapID, double bandID, double majorAxis, double minorAxis,
			double angleOfRotation) {

		// Generating query...
		String SQL = String.format("INSERT INTO %s (%s, %s, %s, %s, %s, %s) VALUES (%s, %s, %s, %s, %s, %s)",
				TABLE_NAME, COLUMN_CLUMP_ID, COLUMN_CLUMP_MAP_ID, COLUMN_BAND_ID, COLUMN_MINOR_AXIS, COLUMN_MAJOR_AXIS,
				COLUMN_ANGLE_OF_ROTATION, clumpID, clumpMapID, bandID, minorAxis, majorAxis, angleOfRotation);

		SQL += String.format("ON CONFLICT (%s, %s, %s) DO UPDATE SET %s = %s, %s = %s, %s = %s", COLUMN_CLUMP_ID,
				COLUMN_CLUMP_MAP_ID, COLUMN_BAND_ID, COLUMN_MINOR_AXIS, minorAxis, COLUMN_MAJOR_AXIS, majorAxis,
				COLUMN_ANGLE_OF_ROTATION, angleOfRotation);

		// Perform query
		executeWriteQuery(SQL);
	}
	
	/**
	 * This method is used to insert a new {@code Ellipse} object.
	 * 
	 * @param minorAxis
	 *            - Represents a {@code double}.
	 * @param majorAxis
	 *            - Represents a {@code double}.
	 * @param angleOfRotation
	 *            - Represents a {@code double}.
	 * @return An {@code int}.
	 */
	public String getInsertQuery(String clumpID, String clumpMapID, int bandID, String majorAxis, String minorAxis,
			String angleOfRotation) {

		// Generating query...
		String SQL = String.format("INSERT INTO %s (%s, %s, %s, %s, %s, %s) VALUES (%s, %s, %s, %s, %s, %s)",
				TABLE_NAME, COLUMN_CLUMP_ID, COLUMN_CLUMP_MAP_ID, COLUMN_BAND_ID, COLUMN_MINOR_AXIS, COLUMN_MAJOR_AXIS,
				COLUMN_ANGLE_OF_ROTATION, clumpID, clumpMapID, bandID, minorAxis, majorAxis, angleOfRotation);

		SQL += String.format("ON CONFLICT (%s, %s, %s) DO UPDATE SET %s = %s, %s = %s, %s = %s", COLUMN_CLUMP_ID,
				COLUMN_CLUMP_MAP_ID, COLUMN_BAND_ID, COLUMN_MINOR_AXIS, minorAxis, COLUMN_MAJOR_AXIS, majorAxis,
				COLUMN_ANGLE_OF_ROTATION, angleOfRotation);

		return SQL;
	}

	
	
	
	
}
