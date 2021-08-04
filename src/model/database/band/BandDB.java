package model.database.band;

import java.sql.ResultSet;
import java.util.Vector;
import model.database.AbstractDatabase;
import model.entity.band.Band;

public class BandDB extends AbstractDatabase {

	private static BandDB myInstance = null;
	
	private static final String TABLE_NAME = "band";
	public static final String COLUMN_ID = "band_id";
	public static final String COLUMN_RESOLUTION = "band_resolution";
	public static final String COLUMN_WAVELENGTH = "band_ravelength";

	/**
	 * This method is used to get an instance of {@code BandDatabase} class.
	 * 
	 * @return An {@code BandDatabase} object.
	 */
	public synchronized static BandDB getInstance() {
		if (myInstance == null)
			myInstance = new BandDB();
		return myInstance;
	}

	@Override
	public void createTable() {
		if (!tableExist(TABLE_NAME))
			executeWriteQuery(importSQL(getClass().getResourceAsStream("SQL_Band.sql")));
	}


	/**
	 * This method perform an INSERT operation.
	 * 
	 * @param resolution
	 *            - Represents a {@code double}.
	 * @param wavelength
	 *            - Represents a {@code double}.
	 * @return An {@code int}.
	 */
	public int insert(double resolution, double wavelength) {

		// Generating query...
		String SQL = String.format("INSERT INTO %s (%s, %s) VALUES (%s, %s) ", TABLE_NAME, COLUMN_RESOLUTION,
				COLUMN_WAVELENGTH, resolution, wavelength);
		
		SQL += String.format(" ON CONFLICT (%s, %s) DO NOTHING", COLUMN_RESOLUTION, COLUMN_WAVELENGTH);

		// Perform query
		return executeWriteQueryGettingLastVal(SQL);
	}

	/**
	 * This method perform a DELETE operation.
	 * 
	 * @param arg0
	 *            - Represents an {@code Band} object.
	 */
	public void remove(Band arg0) {

		// Generating query...
		String SQL = String.format("DELETE FROM %s WHERE %s = %s", TABLE_NAME, COLUMN_ID, arg0.getID());

		// Perform query
		executeWriteQuery(SQL);
	}

	/******************************************************************************************************************
	 * Getter method
	 *****************************************************************************************************************/

	/**
	 * This method is used to get an existing {@code Band} object.
	 * 
	 * @param arg0
	 *            - Represents an {@code String} object.
	 * @return A {@code Band} object.
	 */
	private Vector<Band> get(String SQL) {

		Vector<Band> obj = new Vector<>();
		Band auxiliaryObject;

		try {

			openConnection();
			createStatement();
			ResultSet rs = createResultSet(SQL);

			// Get data
			while (rs.next()) {

				auxiliaryObject = new Band(rs.getInt(COLUMN_ID));
				auxiliaryObject.setResolution(rs.getDouble(COLUMN_RESOLUTION));
				auxiliaryObject.setWavelength(rs.getDouble(COLUMN_WAVELENGTH));
				obj.add(auxiliaryObject);

			}
		} catch (Exception e) {
			System.out.println("FAILURE: " + e.getMessage());
		} finally {

			// release JDBC resources
			releaseResources();
		}
		return obj;
	}

	/**
	 * This method is used to get data from database by a primary key.
	 * 
	 * @param arg0
	 *            - Represents an {@code int}.
	 * @return A {@code Band} object.
	 */
	public Band getByKey(int arg0) {
		Band obj = null;

		// Generating query...
		String SQL = String.format("SELECT * FROM %s WHERE %s = %s", TABLE_NAME, COLUMN_ID, arg0);

		try {
			obj = this.get(SQL).firstElement();
		} catch (Exception e) {
			obj = null;
		}

		return obj;
	}

	/**
	 * This method is used to get a {@code Band} object by resolution and
	 * wavelength
	 * 
	 * @param resolution
	 *            - Represents an {@code double}.
	 * @param wavelength
	 *            - Represents an {@code double}.
	 * @return A {@code Band} object.
	 */
	public Band getByResolutionWavelenght(double resolution, double wavelength) {
		Band obj = null;

		// Generating query...
		String SQL = String.format("SELECT * FROM %s WHERE %s = %s AND %s = %s", TABLE_NAME, COLUMN_RESOLUTION,
				resolution, COLUMN_WAVELENGTH, wavelength);

		try {
			obj = this.get(SQL).firstElement();
		} catch (Exception e) {
			obj = null;
		}

		return obj;
	}

	/**
	 * This method is used to get all data from database.
	 * 
	 * @return A {@code Vector} object.
	 */
	public Vector<Band> getAll() {

		// Generating query...
		String SQL = String.format("SELECT * FROM %s", TABLE_NAME);

		return this.get(SQL);
	}

	
}
