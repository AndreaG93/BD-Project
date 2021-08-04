package model.database.flux;

import java.sql.ResultSet;
import java.util.Vector;
import model.database.AbstractDatabase;
import model.database.band.BandDB;
import model.entity.band.Band;
import model.entity.band.builder.BandBuilderDirector;
import model.entity.flux.Flux;
import model.entity.flux.builder.FluxBuilderDirector;

public class FluxDB extends AbstractDatabase {

	private static FluxDB myInstance = null;
	public static final String TABLE_NAME = "flux";
	public static final String COLUMN_ID = "flux_id";
	public static final String COLUMN_BAND_ID = "flux_band_id";
	public static final String COLUMN_ERROR = "flux_error";
	public static final String COLUMN_VALUE = "flux_value";

	/**
	 * This method is used to get an instance of {@code FluxDB} class.
	 * 
	 * @return An {@code FluxDB} object.
	 */
	public synchronized static FluxDB getInstance() {
		if (myInstance == null)
			myInstance = new FluxDB();
		return myInstance;
	}

	@Override
	public void createTable() {
		if (!tableExist(TABLE_NAME))
			executeWriteQuery(importSQL(getClass().getResourceAsStream("SQL_Flux.sql")));
	}


	/**
	 * 
	 * @param bandID
	 * @param value
	 * @param error
	 * @return
	 */
	public String getInsertQuery(int bandID, String value, String error) {

		// Elaboration data...
		String var0 = convertToStringCheckNullable(error);
		String var1 = convertToStringCheckNullable(value);

		// Generating query...
		String SQL = String.format("INSERT INTO %s (%s, %s, %s) VALUES (%s, %s, %s);\n", TABLE_NAME, COLUMN_BAND_ID,
				COLUMN_VALUE, COLUMN_ERROR, bandID, var1, var0);

		return SQL;
	}
	
	/**
	 * This method is used to perform a DELETE operation.
	 * 
	 * @param arg0
	 *            - Represents an {@code Flux} object.
	 */
	public void remove(Flux arg0) {

		// Perform query...
		String SQL = String.format("DELETE FROM %s WHERE %s = %s", TABLE_NAME, COLUMN_ID, arg0.getID());

		// Perform query
		executeWriteQuery(SQL);
	}

	/******************************************************************************************************************
	 * Getter method
	 *****************************************************************************************************************/

	/**
	 * This method is used to get data from database.
	 * 
	 * @param SQL
	 *            - Represents a {@code String} object.
	 * @return A {@code Vector} object.
	 */
	private Vector<Flux> get(String SQL) {

		String query = importSQL(getClass().getResourceAsStream("SQL_QueryGetFlux.sql")) + "\n";
		query += SQL + "\nORDER BY " + COLUMN_ID;

		// Output vector...
		Vector<Flux> obj = new Vector<>();

		try {

			// Print query...
			System.out.println("Query:\n" + query);

			openConnection();
			createStatement();
			ResultSet rs = createResultSet(query);

			// Get data
			while (rs.next()) {

				Band myBand = BandBuilderDirector.create(rs.getInt(BandDB.COLUMN_ID),
						rs.getDouble(BandDB.COLUMN_RESOLUTION), rs.getDouble(BandDB.COLUMN_WAVELENGTH));
				Flux myFlux = FluxBuilderDirector.create(rs.getInt(COLUMN_ID), rs.getDouble(COLUMN_VALUE),
						rs.getDouble(COLUMN_ERROR), myBand);

				obj.add(myFlux);
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
	 * @return A {@code Flux} object.
	 */
	public Flux getByKey(int arg0) {
		Flux obj;

		// Generating query...
		String SQL = String.format(" WHERE %s = %s", COLUMN_ID, arg0);

		try {
			obj = get(SQL).firstElement();
		} catch (Exception e) {
			obj = null;
		}

		return obj;
	}

	/******************************************************************************************************************
	 * Useful method
	 *****************************************************************************************************************/

	public void update(int ID, Double value, Double error) {

		// Elaboration data...
		String var0 = convertToStringCheckNullable(error);
		String var1 = convertToStringCheckNullable(value);

		// Generating query...
		String SQL = String.format("UPDATE %s SET %s = %s, %s = %s WHERE %s = %s", TABLE_NAME, COLUMN_VALUE, var1,
				COLUMN_ERROR, var0, COLUMN_ID, ID);

		// Perform query...
		executeWriteQuery(SQL);
	}
}
