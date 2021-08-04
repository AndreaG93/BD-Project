package model.database;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.concurrent.locks.ReentrantLock;

import model.Support;

public abstract class AbstractDatabase {

	// JDBC driver name and database URL
	private static final String JDBC_DRIVER = "org.postgresql.Driver";
	private static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";

	// Database credentials
	private static final String USER = "postgres";
	private static final String PASS = "andrea";

	// ReentrantLock
	protected static ReentrantLock myReentrantLock = new ReentrantLock();

	private Connection myConnection = null;
	private Statement myStatement = null;

	/**
	 * This method is used to create table if it doesn't exists.
	 */
	public abstract void createTable();

	/**
	 * This method is used to establish a connection to database.
	 * 
	 * @throws Exception
	 */
	public void openConnection() throws Exception {

		// Register JDBC driver
		Class.forName(JDBC_DRIVER);

		// Open a connection
		this.myConnection = DriverManager.getConnection(DB_URL, USER, PASS);
	}

	/**
	 * This method is used to create a new {@code Statement} object.
	 * 
	 * @throws Exception
	 */
	public void createStatement() throws Exception {
		this.myStatement = this.myConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
	}

	/**
	 * This method is used to create a new {@code ResultSet} object.
	 * 
	 * @param SQL
	 *            - Represents a {@code String} object.
	 * @return A {@code ResultSet} object.
	 * @throws Exception
	 */
	public ResultSet createResultSet(String SQL) throws Exception {
		
		// Print query...
		System.out.println(Support.getDashedLine(false));
		System.out.println("Performing following SQL query:\n\n" + SQL + "\n");
		
		return myStatement.executeQuery(SQL);
	}

	/**
	 * This method is used to release JDBC resources.
	 */
	public void releaseResources() {
		try {

			// Releases Statement object
			if (this.myStatement != null && !this.myStatement.isClosed())
				this.myStatement.close();

			// Releases Connection object
			if (this.myConnection != null && !this.myConnection.isClosed())
				this.myConnection.close();

		} catch (SQLException e) {
			System.out.println("FAILURE RELEASE RESOURCE: " + e.getMessage());
		}
	}

	/**
	 * This method is used to check existence of a specified table
	 * 
	 * @param tableName
	 *            - Represents a {@code String} object.
	 * @return A {@code boolean}.
	 */
	public boolean tableExist(String tableName) {

		boolean tExists = false;

		try {
			openConnection();

			DatabaseMetaData md = this.myConnection.getMetaData();
			ResultSet rs = md.getTables(null, null, tableName.toLowerCase(), null);
			while (rs.next()) {

				if (!rs.wasNull()) {
					tExists = true;
					break;
				}
			}
		} catch (Exception e) {
			System.out.println("FAILURE CHECK EXISTENCE TABLE " + tableName + ": " + e.getMessage());
		}
		return tExists;
	}

	/**
	 * This method is used to perform an insert query and it return last value.
	 * 
	 * @param SQL
	 *            - Represents a {@code String} object.
	 * @return An {@code int}.
	 */
	public int executeWriteQueryGettingLastVal(String SQL) {

		// Print query...
		System.out.println("Performing query:\n" + SQL);

		int var = -1;

		// Acquires the lock
		myReentrantLock.lock();

		try {

			openConnection();
			createStatement();
			this.myStatement.executeUpdate(SQL);

			ResultSet rs = createResultSet("SELECT LASTVAL();");

			// Get data
			if (rs.next())
				var = rs.getInt(1);

		} catch (Exception e) {
			System.out.println("FAILURE EXECUTE QUERY: " + e.getMessage());
		} finally {

			// release JDBC resources
			releaseResources();

			// Release lock
			myReentrantLock.unlock();
		}

		return var;
	}

	/**
	 * This method is used to perform an insert, update or delete query. Thread
	 * safe version.
	 * 
	 * @param SQL
	 *            - Represents a {@code String} object.
	 * @throws Exception
	 */
	public void executeWriteQuery(String SQL) {

		// Print query...
		System.out.println("-----------------------------------------------");
		System.out.println("Performing query:\n" + SQL);

		// Acquires the lock
		myReentrantLock.lock();

		try {

			openConnection();
			createStatement();
			this.myStatement.executeUpdate(SQL);

		} catch (Exception e) {
			System.out.println("FAILURE EXECUTE QUERY: " + e.getMessage());
		} finally {

			// release JDBC resources
			releaseResources();

			// Release lock
			myReentrantLock.unlock();

			// Print query...
			System.out.println("Query complete!");
		}
	}

	/**
	 * This method is used to perform an insert, update or delete query. 
	 * 
	 * @param SQL
	 *            - Represents a {@code String} object.
	 * @throws Exception
	 */
	public void executeWriteQueryException(String SQL) throws Exception {

		// Print query...
		System.out.println(Support.getDashedLine(false));
		System.out.println("Performing following SQL query:\n\n" + SQL + "\n");

		// Acquires the lock
		myReentrantLock.lock();

		try {

			openConnection();
			createStatement();
			this.myStatement.executeUpdate(SQL);

			System.out.println("Operation complete!");
			System.out.println(Support.getDashedLine(false));
			
		} catch (Exception e) {
			throw new Exception("Impossible to perform required operation\n\nTechnical details:\n" + e.getMessage());
		} finally {

			// release JDBC resources
			releaseResources();

			// Release lock
			myReentrantLock.unlock();
		}
	}

	public String convertToStringCheckNullable(Object arg0) {
		String var = "NULL";
		if (arg0 != null)
			var = arg0.toString();
		return var;
	}

	/**
	 * This method returns specified object if it isn't null; otherwise return "NULL"
	 * @param arg0
	 * @return
	 */
	public Object checkNullableObject(Object arg0) {
		if (arg0 == null)
			return "NULL";
		return arg0;
	}

	public String convertToStringCheckNullableWithQuote(Object arg0) {
		String var = "NULL";
		if (arg0 != null)
			var = "'" + arg0.toString() + "'";
		return var;
	}

	/**
	 * This method is used to convert a {@code SQL} file's content to
	 * {@code String}.
	 * 
	 * @param arg0
	 *            - Represents a {@code InputStream} object.
	 * @return A {@code String} object.
	 */
	@SuppressWarnings("resource")
	public String importSQL(InputStream arg0) {
		String myString = "";

		Scanner myScanner = new Scanner(arg0);

		// myScanner.useDelimiter("(;(\r)?\n)|(--\n)");
		myScanner.useDelimiter("\r|" + System.lineSeparator());

		while (myScanner.hasNext()) {
			myString = myString + myScanner.next();
		}

		return myString;
	}

	/**
	 * This method is used to retrieve a result from an arithmetical SQL query.
	 * 
	 * @param SQL
	 *            - Represents a {@code String} object.
	 * @return
	 */
	public double getArithmeticValue(String SQL) {
		Double obj = null;

		// Print query...
		System.out.println(Support.getDashedLine(false));
		System.out.println("Performing following SQL query:\n\n" + SQL + "\n");

		try {

			openConnection();
			createStatement();
			ResultSet rs = createResultSet(SQL);

			// Get data...
			if (rs.next())
				obj = new Double(rs.getDouble(1));

		} catch (Exception e) {
			System.out.println("FAILURE: " + e.getMessage());
		} finally {
			// release JDBC resources
			releaseResources();
		}

		// Checking result...
		if (obj == null)
			return -1;
		else
			return obj;
	}

	public int getCount(String SQL) {
		return (int) getArithmeticValue("SELECT COUNT(*) FROM (" + SQL + ") AS MYSELECT");
	}

}
