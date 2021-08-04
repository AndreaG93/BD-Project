package model.database.account;

import java.sql.ResultSet;
import model.database.AbstractDatabase;
import model.entity.account.Account;
import model.entity.account.builder.AccountBuilderDirector;

/**
 * The {@code AccountDB} class is used to store and retrieve {@code Account}
 * object from database.
 * 
 * @author Andrea Graziani
 * @see Account
 * @version 1.0
 */
public class AccountDB extends AbstractDatabase {
	private static AccountDB myInstance = null;

	private static final String TABLE_NAME = "account";
	private static final String COLUMN_ID = "account_id";
	private static final String COLUMN_USERNAME = "account_username";
	private static final String COLUMN_PASSWORD = "account_password";
	private static final String COLUMN_TYPE = "account_type";
	private static final String COLUMN_EMAIL = "account_email";
	private static final String COLUMN_NAME = "account_name";
	private static final String COLUMN_SURNAME = "account_surname";

	@Override
	public void createTable() {
		if (!tableExist(TABLE_NAME))
			executeWriteQuery(importSQL(getClass().getResourceAsStream("SQL_Account.sql")));
	}

	/**
	 * This method is used to get an instance of {@code AccountDB} class.
	 * 
	 * @return An {@code AccountDB} object.
	 */
	public synchronized static AccountDB getInstance() {
		if (myInstance == null)
			myInstance = new AccountDB();
		return myInstance;
	}

	/**
	 * This method perform an INSERT operation.
	 * 
	 * @param accountType
	 *            - Represents a {@code String} object.
	 * @param username
	 *            - Represents a {@code String} object.
	 * @param password
	 *            - Represents a {@code String} object.
	 * @param name
	 *            - Represents a {@code String} object.
	 * @param surname
	 *            - Represents a {@code String} object.
	 * @param email
	 *            - Represents a {@code String} object.
	 * @throws Exception
	 */
	public void insert(String accountType, String username, String password, String name, String surname, String email)
			throws Exception {

		// Generating query...
		String SQL = String.format("INSERT INTO %s (%s, %s, %s, %s, %s, %s) VALUES ('%s','%s','%s','%s','%s','%s')",
				TABLE_NAME, COLUMN_TYPE, COLUMN_USERNAME, COLUMN_PASSWORD, COLUMN_NAME, COLUMN_SURNAME, COLUMN_EMAIL,
				accountType, username, password, name, surname, email);

		// Add 'ON CONFLICT' clause...
		SQL += " ";
		SQL += String.format("ON CONFLICT (%s) DO NOTHING", COLUMN_USERNAME);

		// Perform query...
		executeWriteQueryException(SQL);
	}

	/**
	 * This method perform a GET operation with specified {@code Username}.
	 * 
	 * @param arg0
	 *            - Represents a {@code String} object.
	 * @return An {@code Account} object.
	 * @throws Exception
	 */
	public Account getAccountByUsername(String arg0) throws Exception {

		// Generating query...
		String SQL = String.format("SELECT * FROM %s WHERE %s = '%s'", TABLE_NAME, COLUMN_USERNAME, arg0);

		// Perform query...
		return get(SQL);
	}

	/**
	 * This method perform a DELETE operation.
	 * 
	 * @param arg0
	 *            - Represents an {@code Account} object.
	 * @throws Exception
	 */
	public void remove(Account arg0) throws Exception {

		// Generating query...
		String SQL = String.format("DELETE FROM %s WHERE %s = %s", TABLE_NAME, COLUMN_ID, arg0.getID());

		// Perform query...
		executeWriteQueryException(SQL);
	}

	/**
	 * This method perform a GET operation.
	 * 
	 * @param arg0
	 *            - Represents an {@code int}.
	 * @return An {@code Account} object.
	 * @throws Exception
	 */
	public Account get(String SQL) throws Exception {
		Account obj = null;

		try {

			openConnection();
			createStatement();
			ResultSet rs = createResultSet(SQL);

			// Get data
			if (rs.next()) {
				obj = AccountBuilderDirector.createsAccountObject(rs.getInt(COLUMN_ID), rs.getString(COLUMN_NAME),
						rs.getString(COLUMN_SURNAME), rs.getString(COLUMN_EMAIL), rs.getString(COLUMN_PASSWORD),
						rs.getString(COLUMN_TYPE), rs.getString(COLUMN_USERNAME));
			}

		} finally {
			// release JDBC resources
			releaseResources();
		}
		return obj;
	}

}
