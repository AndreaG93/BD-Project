package model.database.source;

import java.sql.ResultSet;
import java.util.Vector;
import model.database.AbstractDatabase;
import model.database.DatabaseUtility;
import model.database.band.BandDB;
import model.database.flux.FluxDB;
import model.database.map.MapDB;
import model.entity.band.Band;
import model.entity.band.builder.BandBuilderDirector;
import model.entity.flux.Flux;
import model.entity.flux.builder.FluxBuilderDirector;
import model.entity.map.Map;
import model.entity.source.Source;
import model.entity.source.builder.SourceBuilderDirector;

public class SourceDB extends AbstractDatabase {

	private static SourceDB myInstance = null;
	private static final String TABLE_NAME = "source";
	private static final String COLUMN_SOURCE_ID = "source_id";
	private static final String COLUMN_MAP_ID = "source_map_id";
	private static final String COLUMN_GALACTIC_LONGITUDE = "source_galactic_longitude";
	private static final String COLUMN_GALACTIC_LATITUDE = "source_galactic_latitude";

	/**
	 * This method is used to get an instance of {@code SourceDB} class.
	 * 
	 * @return An {@code SourceDB} object.
	 */
	public synchronized static SourceDB getInstance() {
		if (myInstance == null)
			myInstance = new SourceDB();
		return myInstance;
	}

	@Override
	public void createTable() {
		if (!tableExist(TABLE_NAME))
			executeWriteQuery(importSQL(getClass().getResourceAsStream("SQL_Source.sql")));
	}

	/**
	 * This method perform an INSERT operation.
	 * 
	 * @param sourceID
	 *            - Represents an {@code String} object.
	 * @param mapID
	 *            - Represents a {@code double}.
	 * @param gLongitude
	 *            - Represents a {@code double}.
	 * @param gLatitude
	 *            - Represents a {@code double}.
	 * @return - An {@code int}.
	 */
	public String getInsertQuery(String sourceID, int mapID, double gLongitude, double gLatitude) {

		String SQL = "";

		// Generating query...
		SQL += String.format("INSERT INTO %s (%s, %s, %s, %s) VALUES ('%s', %s, %s, %s)\n", TABLE_NAME,
				COLUMN_SOURCE_ID, COLUMN_MAP_ID, COLUMN_GALACTIC_LONGITUDE, COLUMN_GALACTIC_LATITUDE, sourceID, mapID,
				gLongitude, gLatitude);

		SQL += String.format("ON CONFLICT (%s, %s) DO UPDATE SET %s = %s, %s = %s;\n", COLUMN_SOURCE_ID, COLUMN_MAP_ID,
				COLUMN_GALACTIC_LONGITUDE, gLongitude, COLUMN_GALACTIC_LATITUDE, gLatitude);

		// Perform query
		return SQL;
	}

	/**
	 * This method perform an INSERT operation.
	 * 
	 * @param sourceID
	 *            - Represents an {@code String} object.
	 * @param mapID
	 *            - Represents a {@code double}.
	 * @param gLongitude
	 *            - Represents a {@code double}.
	 * @param gLatitude
	 *            - Represents a {@code double}.
	 * @return - An {@code int}.
	 */
	public int insert(String sourceID, int mapID, double gLongitude, double gLatitude) {

		// Generating query...
		String SQL = String.format("INSERT INTO %s (%s, %s, %s, %s) VALUES ('%s', %s, %s, %s)", TABLE_NAME,
				COLUMN_SOURCE_ID, COLUMN_MAP_ID, COLUMN_GALACTIC_LONGITUDE, COLUMN_GALACTIC_LATITUDE, sourceID, mapID,
				gLongitude, gLatitude);

		SQL += String.format("ON CONFLICT (%s) DO UPDATE SET %s = %s, %s = %s", COLUMN_SOURCE_ID,
				COLUMN_GALACTIC_LONGITUDE, gLongitude, COLUMN_GALACTIC_LATITUDE, gLatitude);

		// Perform query
		return executeWriteQueryGettingLastVal(SQL);
	}

	/**
	 * This method is used to delete an existing {@code Source} object.
	 * 
	 * @param arg0
	 *            - Represents an {@code Source} object.
	 */
	public void remove(Source arg0) {

		// Generating query...
		String SQL = String.format("DELETE FROM %s WHERE %s = %s", TABLE_NAME, COLUMN_SOURCE_ID, arg0.getId());

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
	private Vector<Source> get(String selectStatement, int limit, int offset) {

		// Compute total records...
		DatabaseUtility.getInstance().setLastSelectCount(getCount(selectStatement));
		
		String query = importSQL(getClass().getResourceAsStream("SQL_QueryGetSource.sql")) + "\n";

		query += String.format("WHERE %s in (", COLUMN_SOURCE_ID) + selectStatement
				+ String.format(" ORDER BY %s LIMIT %s OFFSET %s", COLUMN_SOURCE_ID, limit, offset) + ")\n";

		// Output vector...
		Vector<Source> obj = new Vector<>();

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

				Source mySource = SourceBuilderDirector.build(rs.getString(COLUMN_SOURCE_ID), myMap,
						(Double) rs.getObject(COLUMN_GALACTIC_LATITUDE),
						(Double) rs.getObject(COLUMN_GALACTIC_LONGITUDE));

				Band myBandFlux = BandBuilderDirector.create(rs.getInt(FluxDB.COLUMN_BAND_ID),
						rs.getDouble(BandDB.COLUMN_RESOLUTION), rs.getDouble(BandDB.COLUMN_WAVELENGTH));

				Flux myFlux = FluxBuilderDirector.create(rs.getInt(FluxDB.COLUMN_ID),
						(Double) rs.getObject(FluxDB.COLUMN_VALUE), (Double) rs.getObject(FluxDB.COLUMN_ERROR),
						myBandFlux);

				// Check existence of a clump...
				boolean exist = false;

				// Check existence of a clump...
				for (Source var : obj)
					if (var.getId().equals(mySource.getId())) {
						// Add flux...
						var.getFluxVector().add(myFlux);
						exist = true;
						break;
					}

				if (!exist) {
					// Add flux...
					mySource.getFluxVector().add(myFlux);
					obj.add(mySource);
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
	 * @return An {@code Agency} object.
	 */
	public Vector<Source> getByPrimaryKey(String arg0, Map arg1) {

		// Generating SELECT statement...
		String SQL = String.format("SELECT %s FROM %s WHERE %s = %s AND %s = %s", COLUMN_SOURCE_ID, TABLE_NAME,
				COLUMN_SOURCE_ID, arg0, COLUMN_MAP_ID, arg1.getID());

		return get(SQL, 1, 0);
	}

	/**
	 * This method is used to search {@code Source} objects presents in a region
	 * (circle version).
	 * 
	 * @param radius
	 *            - Represents a {@code double}.
	 * @param x
	 *            - Represents a {@code double}.
	 * @param y
	 *            - Represents a {@code double}.
	 * @param limit
	 * @param offset
	 * @return A {@code Vector} object.
	 */
	public Vector<Source> searchRegionCircle(double radius, double x, double y, int mapID, int limit, int offset) {

		// Generating SELECT statement...
		String SQL = String.format("SELECT %s FROM %s WHERE (SQRT(POWER(%s - %s, 2)+ POWER(%s - %s, 2)) <= %s) AND %s = %s",
				COLUMN_SOURCE_ID, TABLE_NAME, COLUMN_GALACTIC_LONGITUDE, x, COLUMN_GALACTIC_LATITUDE, y, radius, COLUMN_MAP_ID, mapID);

		// Perform query...
		return get(SQL, limit, offset);
	}

	/**
	 * This method is used to search {@code Source} objects present in a region
	 * (square version).
	 * 
	 * @param side
	 *            - Represents a {@code double}.
	 * @param x
	 *            - Represents a {@code double}.
	 * @param y
	 *            - Represents a {@code double}.
	 * @param limit
	 * @param offset
	 * @return A {@code Vector} object.
	 */
	public Vector<Source> searchRegionSquare(double side, double x, double y, int mapID, int limit, int offset) {

		// Generating query...
		String SQL = String.format(
				"SELECT %s FROM %s WHERE (SQRT(POWER(%s - %s, 2) + POWER(%s - %s, 2)) <= (%s*SQRT(2))/2) AND %s = %s",
				COLUMN_SOURCE_ID, TABLE_NAME, COLUMN_GALACTIC_LONGITUDE, x, COLUMN_GALACTIC_LATITUDE, y, side, COLUMN_MAP_ID, mapID);

		// Perform query...
		return get(SQL, limit, offset);
	}

	/**
	 * This method is used to search {@code Source} objects present in a clump
	 * into Map MISPGAL.
	 * 
	 * @param mipsgalID
	 *            - Represents a {@code int}.
	 * @param clumpX
	 *            - Represents a {@code double}.
	 * @param clumpY
	 *            - Represents a {@code double}.
	 * @param limit 
	 * @param offset 
	 * @param minorAxis
	 *            - Represents a {@code double}.
	 * @return A {@code Vector} object.
	 */
	public Vector<Source> searchSourceInsideClump(int clumpID, int bandID, int limit, int offset) {

		// Generating query...
		String query = importSQL(getClass().getResourceAsStream("GetSourceInsideClump.sql"));
		
		// Add data...
		String SQL = String.format(query, bandID, clumpID);
		
	
		// Perform query...
		return get(SQL, limit, offset);
	}
	
	public Vector<Source> searchSourceInsideClumpYSO(int clumpID, int bandID, int limit, int offset) {

		// Add view...
		this.executeWriteQuery(importSQL(getClass().getResourceAsStream("SQL_FluxView.sql")));
		
		// Generating query...
		String query = importSQL(getClass().getResourceAsStream("GetSourceInsideClumpYSO.sql"));
		
		// Add data...
		String SQL = String.format(query, bandID, clumpID);
		
	
		// Perform query...
		return get(SQL, limit, offset);
	}
	

	/**
	 * This method is used to get data from database by a map ID.
	 * 
	 * @param arg0
	 *            - Represents an {@code int}.
	 * @param limit 
	 * @param offset 
	 * @return A {@code Vector} object.
	 */
	public Vector<Source> getByMapID(int arg0, int limit, int offset) {

		// Generating query...
		String SQL = String.format("SELECT %s FROM %s WHERE %s = %s", COLUMN_SOURCE_ID, TABLE_NAME, COLUMN_MAP_ID, arg0);

		// Perform query...
		return get(SQL, limit, offset);
	}
}
