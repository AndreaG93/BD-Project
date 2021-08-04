package model.database.instrument;

import java.sql.ResultSet;
import java.util.Vector;
import model.database.AbstractDatabase;
import model.database.band.BandDB;
import model.database.map.MapDB;
import model.entity.band.Band;
import model.entity.band.builder.BandBuilderDirector;
import model.entity.instrument.Instrument;
import model.entity.instrument.builder.InstrumentBuilderDirector;
import model.entity.map.Map;

public class InstrumentDB extends AbstractDatabase {

	private static InstrumentDB myInstance = null;
	private static final String TABLE_NAME = "instrument";
	public static final String COLUMN_MAP_ID = "instrument_map_id";
	public static final String COLUMN_ID = "instrument_id";
	public static final String COLUMN_NAME = "instrument_name";

	/**
	 * This method is used to get an instance of {@code InstrumentDatabase}
	 * class.
	 * 
	 * @return An {@code InstrumentDatabase} object.
	 */
	public synchronized static InstrumentDB getInstance() {
		if (myInstance == null)
			myInstance = new InstrumentDB();
		return myInstance;
	}

	@Override
	public void createTable() {
		if (!tableExist(TABLE_NAME))
			executeWriteQuery(importSQL(getClass().getResourceAsStream("SQL_Instrument.sql")));
	}

	/**
	 * This method perform an INSERT operation.
	 * 
	 * @param name
	 *            - Represents a {@code String} object.
	 * @return An {@code int}.
	 */
	public int insert(String name, int mapid) {

		// Generating query...
		String SQL = String.format("INSERT INTO %s (%s, %s) VALUES ('%s', %S)", TABLE_NAME, COLUMN_NAME, COLUMN_MAP_ID, name, mapid);

		// Perform query
		return executeWriteQueryGettingLastVal(SQL);
	}

	/**
	 * This method perform a DELETE operation.
	 * 
	 * @param arg0
	 *            - Represents an {@code Instrument} object.
	 */
	public void remove(Instrument arg0) {

		// Generating query...
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
	private Vector<Instrument> get(String SQL) {

		String query = importSQL(getClass().getResourceAsStream("SQL_QueryGetInstrument.sql")) + "\n";
		query += SQL + "\nORDER BY " + COLUMN_ID;

		// Output vector...
		Vector<Instrument> obj = new Vector<>();

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

				Instrument myInstrument = InstrumentBuilderDirector.create(rs.getInt(COLUMN_ID),
						rs.getString(COLUMN_NAME), myMap);

				Band myBandIntrument = BandBuilderDirector.create(rs.getInt(BandDB.COLUMN_ID),
						rs.getDouble(BandDB.COLUMN_RESOLUTION), rs.getDouble(BandDB.COLUMN_WAVELENGTH));

				// Check existence of a clump...
				boolean exist = false;

				// Check existence of a clump...
				for (Instrument var : obj)
					if (var.getID() == myInstrument.getID()) {
						// Add band...
						var.getSupportedBands().add(myBandIntrument);
						exist = true;
						break;
					}

				if (!exist) {
					// Add band...
					myInstrument.getSupportedBands().add(myBandIntrument);
					obj.add(myInstrument);
				}

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
	 * @return An {@code Instrument} object.
	 */
	public Instrument getByKey(int arg0) {
		Instrument obj = null;

		// Generating query...
		String SQL = String.format("WHERE %s = %s", COLUMN_ID, arg0);

		try {
			obj = this.get(SQL).firstElement();
		} catch (Exception e) {
			obj = null;
		}

		return obj;
	}

	/**
	 * This method is used to get data from database by name.
	 * 
	 * @param arg0
	 *            - Represents an {@code int}.
	 * @return An {@code Instrument} object.
	 */
	public Instrument getByName(String arg0) {
		Instrument obj = null;

		// Generating query...
		String SQL = String.format("WHERE %s = '%s'", COLUMN_NAME, arg0);

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
	public Vector<Instrument> getAll() {
		return this.get("");
	}

}
