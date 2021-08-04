package model.database.clump;

import java.sql.ResultSet;
import java.util.Vector;

import model.Support;
import model.database.AbstractDatabase;
import model.database.DatabaseUtility;
import model.database.band.BandDB;
import model.database.ellipse.EllipseDB;
import model.database.flux.FluxDB;
import model.database.map.MapDB;
import model.entity.band.Band;
import model.entity.band.builder.BandBuilderDirector;
import model.entity.clump.Clump;
import model.entity.clump.builder.ClumpBuilderDirector;
import model.entity.ellipse.Ellipse;
import model.entity.ellipse.builder.EllipseBuilderDirector;
import model.entity.flux.Flux;
import model.entity.flux.builder.FluxBuilderDirector;
import model.entity.map.Map;

public class ClumpDB extends AbstractDatabase {

	private static ClumpDB myInstance = null;

	private static final String TABLE_NAME = "clump";
	private static final String COLUMN_ID = "clump_id";
	private static final String COLUMN_MAP_ID = "clump_map_id";
	private static final String COLUMN_GALACTIC_LONGITUDE = "clump_galactic_longitude";
	private static final String COLUMN_GALACTIC_LATITUDE = "clump_galactic_latitude";
	private static final String COLUMN_TEMP = "clump_temp";
	private static final String COLUMN_BOLO_TEMP_MASS_RATIO = "clump_bolo_temp_mass_ratio";
	private static final String COLUMN_SUP_DENSITY = "clump_sup_density";
	private static final String COLUMN_MASS = "clump_mass";
	private static final String COLUMN_TYPE = "clump_type";

	/**
	 * This method is used to get an instance of {@code ClumpDB} class.
	 * 
	 * @return An {@code ClumpDB} object.
	 */
	public synchronized static ClumpDB getInstance() {
		if (myInstance == null)
			myInstance = new ClumpDB();
		return myInstance;
	}

	@Override
	public void createTable() {
		if (!tableExist(TABLE_NAME))
			executeWriteQuery(importSQL(getClass().getResourceAsStream("SQL_Clump.sql")));
	}
	

	/**
	 * This method is used to perform an INSERT operation.
	 * 
	 * @param ID
	 * @param mapID
	 * @param gLon
	 * @param gLat
	 * @param temp
	 * @param boloTempBoloRatio
	 * @param supDensity
	 * @param type
	 * @param onConfict
	 * @return
	 */
	public String getInsertQuery(String ID, String mapID, String gLon, String gLat, String temp,
			String boloTempBoloRatio, String supDensity, String type, boolean onConfict) {

		// Elaboration data...
		String var0 = checkNullableObject(gLon).toString();
		String var1 = checkNullableObject(gLat).toString();
		String var2 = checkNullableObject(temp).toString();
		String var3 = checkNullableObject(boloTempBoloRatio).toString();
		String var4 = checkNullableObject(supDensity).toString();
		String var5 = checkNullableObject(type).toString();

		// Generating query...
		String SQL = String.format(
				"INSERT INTO %s (%s, %s, %s, %s, %s, %s, %s, %s) VALUES (%s, %s, %s, %s, %s, %s, %s, '%s') ",
				TABLE_NAME, COLUMN_ID, COLUMN_MAP_ID, COLUMN_GALACTIC_LONGITUDE, COLUMN_GALACTIC_LATITUDE, COLUMN_TEMP,
				COLUMN_BOLO_TEMP_MASS_RATIO, COLUMN_SUP_DENSITY, COLUMN_TYPE, ID, mapID, var0, var1, var2, var3, var4,
				var5);

		if (onConfict)
			SQL += String.format(
					"ON CONFLICT (%s, %s) DO UPDATE SET %s = %s, %s = %s, %s = %s, %s = %s, %s = %s, %s = '%s'",
					COLUMN_ID, COLUMN_MAP_ID, COLUMN_GALACTIC_LONGITUDE, var0, COLUMN_GALACTIC_LATITUDE, var1,
					COLUMN_TEMP, var2, COLUMN_BOLO_TEMP_MASS_RATIO, var3, COLUMN_SUP_DENSITY, var4, COLUMN_TYPE, var5);
		else
			SQL += String.format("ON CONFLICT (%s, %s) DO NOTHING;\n", COLUMN_ID, COLUMN_MAP_ID);

		// Perform query...
		return SQL;
	}

	/**
	 * This method is used to perform a DELETE operation.
	 * 
	 * @param arg0
	 *            - Represents an {@code Clump} object.
	 */
	public void remove(Clump arg0) {

		// Generating query...
		String SQL = String.format("DELETE FROM %s WHERE %s = %s AND %s = %s", TABLE_NAME, COLUMN_ID, arg0.getID(),
				COLUMN_MAP_ID, arg0.getMyMap().getID());

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
	private Vector<Clump> get(String selectStatement, int limit, int offset) {

		// Compute total records...
		DatabaseUtility.getInstance().setLastSelectCount(getCount(selectStatement));
			
		// Get start time...
		long startTime = System.currentTimeMillis();

		// Output vector...
		Vector<Clump> obj = new Vector<>();

		// SQL query
		String query = importSQL(getClass().getResourceAsStream("SQL_QueryGetClump.sql")) + "\n";

		query += String.format("WHERE %s in (", COLUMN_ID) + selectStatement
				+ String.format(" ORDER BY %s LIMIT %s OFFSET %s", COLUMN_ID, limit, offset) + ")\n";

		// Print query...
		System.out.println(Support.getDashedLine(false));
		System.out.println("Performing following SQL query:\n\n" + query + "\n");

		try {

			openConnection();
			createStatement();
			ResultSet rs = createResultSet(query);

			// Get data...
			while (rs.next()) {

				int ID = rs.getInt(COLUMN_ID);

				Band myBandEllipse = BandBuilderDirector.create(rs.getInt("ellipseBandband_id"),
						rs.getDouble("ellipseBandband_resolution"), rs.getDouble("ellipseBandband_ravelength"));

				Ellipse myEllipse = EllipseBuilderDirector.create((Double) rs.getObject(EllipseDB.COLUMN_MAJOR_AXIS),
						(Double) rs.getObject(EllipseDB.COLUMN_MINOR_AXIS),
						(Double) rs.getObject(EllipseDB.COLUMN_ANGLE_OF_ROTATION), myBandEllipse);

				Map myMap = new Map(rs.getInt(MapDB.COLUMN_ID));
				myMap.setName(rs.getString(MapDB.COLUMN_NAME));

				Clump myClump = ClumpBuilderDirector.create(ID, myMap, (Double) rs.getObject(COLUMN_TEMP),
						(Double) rs.getObject(COLUMN_GALACTIC_LONGITUDE),
						(Double) rs.getObject(COLUMN_GALACTIC_LATITUDE), (Double) rs.getObject(COLUMN_SUP_DENSITY),
						(Double) rs.getObject(COLUMN_BOLO_TEMP_MASS_RATIO), rs.getString(COLUMN_TYPE),
						(Double) rs.getObject(COLUMN_MASS));

				Band myBandFlux = BandBuilderDirector.create(rs.getInt(FluxDB.COLUMN_BAND_ID),
						rs.getDouble(BandDB.COLUMN_RESOLUTION), rs.getDouble(BandDB.COLUMN_WAVELENGTH));

				Flux myFlux = FluxBuilderDirector.create(rs.getInt(FluxDB.COLUMN_ID),
						(Double) rs.getObject(FluxDB.COLUMN_VALUE), (Double) rs.getObject(FluxDB.COLUMN_ERROR),
						myBandFlux);

				// Check existence of a clump...
				boolean exist = false;

				// Check existence of a clump...
				for (Clump var : obj)
					if (var.getID() == myClump.getID() && var.getMyMap().getID() == myClump.getMyMap().getID()) {
						// Add flux...
						var.getFluxVector().add(myFlux);

						boolean addEllipse = true;

						for (Ellipse x : var.getEllipses())
							if (x.getBand().getID() == myEllipse.getBand().getID())
								addEllipse = false;

						if (addEllipse)
							var.getEllipses().add(myEllipse);
						exist = true;
						break;
					}

				if (!exist) {
					// Add flux...
					myClump.getFluxVector().add(myFlux);
					myClump.getEllipses().add(myEllipse);
					obj.add(myClump);
				}
			}
		} catch (Exception e) {
			System.out.println("FAILURE: " + e.getMessage());
		} finally {

			// release JDBC resources
			releaseResources();
		}

		// Get end time...
		long endTime = System.currentTimeMillis();
		long duration = (endTime - startTime);

		// Print execution time...
		System.out.println("Query complete! - " + duration + "ms");
		System.out.println(Support.getDashedLine(true));

		return obj;
	}

	/**
	 * This method is used to get data from database by a primary key.
	 * 
	 * @param clumpID
	 *            - Represents an {@code int}.
	 * @param mapID
	 *            - Represents an {@code int}.
	 * @return A {@code Clump} object.
	 */
	public Vector<Clump> getByPrimaryKey(int clumpID, int mapID) {

		// Generating SELECT statement...
		String SQL = String.format("SELECT %s FROM %s WHERE %s = %s AND %s = %s", COLUMN_ID, TABLE_NAME, COLUMN_ID,
				clumpID, COLUMN_MAP_ID, mapID);

		return get(SQL, 1, 0);
	}

	/**
	 * This method is used to get all {@code Clump} objects stored in database
	 * by {@code Map} ID.
	 * 
	 * @param limit
	 * @param offset
	 * 
	 * @return A {@code Vector} object.
	 */
	public Vector<Clump> getAllByMapId(int arg0, int limit, int offset) {

		// Generating SELECT statement...
		String SQL = String.format("SELECT %s FROM %s WHERE %s = %s", COLUMN_ID, TABLE_NAME, COLUMN_MAP_ID, arg0);

		return get(SQL, limit, offset);
	}

	/**
	 * This method is used to search {@code Clump} objects that can host massive
	 * stars.
	 * 
	 * @param limit
	 * @param offset
	 * 
	 * @return A {@code Vector} object.
	 */
	public Vector<Clump> massiveStarHostClumpSerch(int arg0, Integer limit, Integer offset) {

		// Generating SELECT statement...
		String SQL = String.format("SELECT %s FROM %s WHERE (%s > 0.1 AND %s < 1.0) AND (%s = %s)", COLUMN_ID,
				TABLE_NAME, COLUMN_SUP_DENSITY, COLUMN_SUP_DENSITY, COLUMN_MAP_ID, arg0);

		return get(SQL, limit, offset);
	}
	
	/**
	 * This method is used to search {@code Clump} objects presents in a region
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
	public Vector<Clump> searchRegionCircle(double radius, double x, double y, Integer limit, Integer offset) {

		// Generating SELECT statement...
		String SQL = String.format("SELECT %s FROM %s WHERE SQRT(POWER(%s - %s, 2) + POWER(%s - %s, 2)) <= %s",
				COLUMN_ID, TABLE_NAME, COLUMN_GALACTIC_LONGITUDE, x, COLUMN_GALACTIC_LATITUDE, y, radius);
		
		return get(SQL, limit, offset);
	}

	/**
	 * This method is used to search {@code Clump} objects presents in a region
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
	public Vector<Clump> searchRegionSquare(double side, double x, double y, Integer limit, Integer offset) {

		// Generating SELECT statement...
		String SQL = String.format(
				"SELECT %s FROM %s WHERE SQRT(POWER(%s - %s, 2) + POWER(%s - %s, 2)) <= (%s*SQRT(2))/2", COLUMN_ID,
				TABLE_NAME, COLUMN_GALACTIC_LONGITUDE, x, COLUMN_GALACTIC_LATITUDE, y, side);

		return get(SQL, limit, offset);
	}

	/**
	 * This method is used to get the number of records stored into database.
	 * 
	 * @return An {@code Integer} object.
	 */
	public Integer getSumAllClumpMass() {
		Integer obj = null;

		// Generating query...
		String SQL = String.format("SELECT sum(%s) from %s", COLUMN_MASS, TABLE_NAME);

		try {

			openConnection();
			createStatement();
			ResultSet rs = createResultSet(SQL);

			// Get data...
			if (rs.next())
				obj = new Integer(rs.getInt(1));

		} catch (Exception e) {
			System.out.println("FAILURE: " + e.getMessage());
		} finally {
			// release JDBC resources
			releaseResources();
		}
		return obj;
	}

	public double getAverage() {

		// SQL query
		String query = importSQL(getClass().getResourceAsStream("SQL_Calc_avg.sql"));

		return getArithmeticValue(query);
	}

	public double getStdDev() {

		// SQL query
		String query = importSQL(getClass().getResourceAsStream("SQL_Calc_stddev_pop.sql"));

		return getArithmeticValue(query);
	}

	public double getStatisticalMedian() {

		// Add Aggregate function...
		String aggFunction = importSQL(getClass().getResourceAsStream("SQL_AddAggregateFunction.sql"));
		this.executeWriteQuery(aggFunction);

		// SQL query
		String query = importSQL(getClass().getResourceAsStream("SQL_statistical_median.sql"));

		return getArithmeticValue(query);
	}

	public double getMedianAbsoluteDeviation() {

		// SQL query
		String query = importSQL(getClass().getResourceAsStream("SQL_median_absolute_deviation.sql"));

		return getArithmeticValue(query);
	}

	/**
	 * This method is used to calculate masses of stored {@code Clump} object.
	 */
	public void calcClumpMass() {

		// Generating query...
		String SQL = importSQL(getClass().getResourceAsStream("SQL_CalcMass.sql"));

		// Perform query...
		executeWriteQuery(SQL);
	}

	
}
