package model.database.agency;

import java.sql.ResultSet;
import java.util.Vector;
import model.database.AbstractDatabase;
import model.entity.agency.Agency;

public class AgencyDB extends AbstractDatabase {

	private static AgencyDB myInstance = null;

	private static final String TABLE_NAME = "agency";
	public static final String COLUMN_ID = "agency_id";
	public static final String COLUMN_NAME = "agency_name";

	/**
	 * This method is used to get an instance of {@code AgencyDB} class.
	 * 
	 * @return An {@code AgencyDB} object.
	 */
	public synchronized static AgencyDB getInstance() {
		if (myInstance == null)
			myInstance = new AgencyDB();
		return myInstance;
	}

	@Override
	public void createTable() {
		if (!tableExist(TABLE_NAME))
			executeWriteQuery(importSQL(getClass().getResourceAsStream("SQL_Agency.sql")));
	}

	/**
	 * This method perform an INSERT operation.
	 * 
	 * @param name
	 *            - Represents a {@code String} object.
	 * @throws Exception
	 */
	public void insert(String name) throws Exception {

		// Generating query...
		String SQL = String.format("INSERT INTO %s (%s) VALUES ('%s')", TABLE_NAME, COLUMN_NAME, name);

		// Add 'ON CONFLICT' clause...
		SQL += " ";
		SQL += String.format("ON CONFLICT (%s) DO NOTHING", COLUMN_NAME);

		// Perform query...
		executeWriteQueryException(SQL);
	}

	/**
	 * This method perform a DELETE operation.
	 * 
	 * @param arg0
	 *            - Represents an {@code Agency} object.
	 * @throws Exception
	 */
	public void remove(Agency arg0) throws Exception {

		// Generating query...
		String SQL = String.format("DELETE FROM %s WHERE %s = %s", TABLE_NAME, COLUMN_ID, arg0.getID());

		// Perform query...
		executeWriteQueryException(SQL);
	}


	/**
	 * This method perform a GET operation.
	 * 
	 * @param SQL
	 *            - Represents a {@code String} object.
	 * @return A {@code Vector} object.
	 * @throws Exception
	 */
	private Vector<Agency> get(String SQL) throws Exception {

		Vector<Agency> obj = new Vector<>();
		Agency auxiliaryObject;

		try {

			openConnection();
			createStatement();
			ResultSet rs = createResultSet(SQL);

			// Get data
			while (rs.next()) {

				auxiliaryObject = new Agency(rs.getInt(COLUMN_ID));
				auxiliaryObject.setName(rs.getString(COLUMN_NAME));
				obj.add(auxiliaryObject);
			}
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
	 * @return An {@code Agency} object.
	 */
	public Agency getByKey(int arg0) {
		Agency obj;

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
	 * This method is used to get data from database by a name.
	 * 
	 * @param arg0
	 *            - Represents a {@code String} object.
	 * @return An {@code Agency} object.
	 */
	public Agency getByName(String arg0) {
		Agency obj;

		// Generating query...
		String SQL = String.format("SELECT * FROM %s WHERE %s = '%s'", TABLE_NAME, COLUMN_NAME, arg0);

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
	 * @throws Exception 
	 */
	public Vector<Agency> getAll() throws Exception {

		// Generating query...
		String SQL = String.format("SELECT * FROM %s", TABLE_NAME);

		// Perform query...
		return this.get(SQL);
	}

}