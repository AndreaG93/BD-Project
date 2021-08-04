package model.database.map;

import java.sql.ResultSet;
import java.util.Vector;

import model.database.AbstractDatabase;
import model.entity.map.Map;

public class MapDB extends AbstractDatabase {

	private static MapDB myInstance = null;
	private static final String TABLE_NAME = "map";
	public static final String COLUMN_ID = "map_id";
	public static final String COLUMN_NAME = "map_name";

	/**
	 * This method is used to get an instance of {@code MapDatabase} class.
	 * 
	 * @return An {@code MapDatabase} object.
	 */
	public synchronized static MapDB getInstance() {
		if (myInstance == null)
			myInstance = new MapDB();
		return myInstance;
	}

	@Override
	public void createTable() {
		if (!tableExist(TABLE_NAME)) 
			executeWriteQuery(importSQL(getClass().getResourceAsStream("SQL_Map.sql")));
	}
	
	
	
	/**
	 * This method is used to insert a new {@code Map} object.
	 * 
	 * @param name
	 *            - Represents a {@code String} object.
	 * @return An {@code int}.
	 * @throws Exception 
	 */
	public int insert(String name) {
		
		// Generating query...
		String SQL = String.format("INSERT INTO %s (%s) VALUES ('%s')", TABLE_NAME, COLUMN_NAME, name);
		
		SQL += String.format(" ON CONFLICT (%s) DO NOTHING", COLUMN_NAME);

		// Perform query
		return executeWriteQueryGettingLastVal(SQL);
	}

	/**
	 * This method perform a DELETE operation.
	 * 
	 * @param arg0
	 *            - Represents an {@code Map} object.
	 * @throws Exception 
	 */
	public void remove(Map arg0) throws Exception {

		// Generating query...
		String SQL = String.format("DELETE FROM %s WHERE %s = %s", TABLE_NAME, COLUMN_ID, arg0.getID());

		// Perform query...
		executeWriteQueryException(SQL);
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
	private Vector<Map> get(String SQL) {
		Vector<Map> obj = new Vector<>();

		try {

			System.out.println("Query:\n" + SQL);

			openConnection();
			createStatement();
			ResultSet rs = createResultSet(SQL);

			// Get data
			while (rs.next()) {
				Map myMap = new Map(rs.getInt(COLUMN_ID));
				myMap.setName(rs.getString(COLUMN_NAME));
				obj.add(myMap);
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
	 * This method is used to get a {@code Map} object by ID.
	 * 
	 * @param arg0
	 *            - Represents an {@code int}.
	 * @return A {@code Map} object.
	 */
	public Map getMapByID(int arg0) {

		Map obj;

		// Generating query...
		String SQL = String.format("SELECT * FROM %s WHERE %s = %s", TABLE_NAME, COLUMN_ID, arg0);

		try {
			obj = get(SQL).firstElement();
		} catch (Exception e) {
			obj = null;
		}

		return obj;
	}

	/**
	 * This method is used to get a {@code Map} object by name.
	 * 
	 * @param mapName
	 *            - Represents a {@code String} object.
	 * @return A {@code Map} object.
	 */
	public Map getMapByName(String mapName) {
		Map obj;

		// Generating query...
		String SQL = String.format("SELECT * FROM %s WHERE %s = '%s'", TABLE_NAME, COLUMN_NAME, mapName);

		try {
			obj = get(SQL).firstElement();
		} catch (Exception e) {
			obj = null;
		}

		return obj;
	}

	public Vector<Map> getAll() {
		
		// Generating query...
		String SQL = String.format("SELECT * FROM %s", TABLE_NAME, COLUMN_NAME);

		// Perform query...
		return get(SQL);
	}

	

}
