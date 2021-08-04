package test.simple_database_operation_test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import org.junit.Test;


import model.database.clump.ClumpDB;
import model.database.map.MapDB;
import model.entity.clump.Clump;
import model.entity.clump.type.Prestellar;
import model.entity.map.Map;

public class Test_ClumpDB {

	private final String MAP_NAME = "TestMapName";

	private final int CLUMP_ID = 3;
	private final double G_LONGITUDE = 1;
	private final double G_LATITUDE = 2;
	private final double TEMP = 3;
	private final double BOLO_TEMP_MASS_RATIO = 4;
	private final double SUP_DENSITY = 5;
	private final String TYPE = new Prestellar().toString();

	private int myMapID;
	private Map myMap;
	private Clump myClump;
	private MapDB myMapDB;
	private ClumpDB myClumpDB;

	/**
	 * This method is used to initialize test.
	 */
	private void testInitialization() {


		this.myMapDB = MapDB.getInstance();
		this.myClumpDB = ClumpDB.getInstance();
	}

	@Test
	public void test_01() {
		testInitialization();

		// Insert auxiliary object...
		this.myMapID = this.myMapDB.insert(MAP_NAME);

		// Get auxiliary object...
		this.myMap = this.myMapDB.getMapByID(this.myMapID);

		// Test: INSERT operation
		this.myClumpDB.getInsertQuery(String.valueOf(CLUMP_ID), String.valueOf(this.myMapID),
				String.valueOf(G_LONGITUDE), String.valueOf(G_LATITUDE), String.valueOf(TEMP),
				String.valueOf(BOLO_TEMP_MASS_RATIO), String.valueOf(SUP_DENSITY), TYPE, false);

		// -- SubTest: retrieved object test
		this.myClump = this.myClumpDB.getByPrimaryKey(CLUMP_ID, myMapID).firstElement();

		assertNotEquals(this.myClump, null);
		assertEquals(CLUMP_ID, this.myClump.getID(), 0);
		assertEquals(this.myMapID, this.myClump.getMyMap().getID(), 0);
		assertEquals(G_LATITUDE, this.myClump.getGalacticLatitude(), 0);
		assertEquals(G_LONGITUDE, this.myClump.getGalacticLongitude(), 0);
		assertEquals(TEMP, this.myClump.getTemperature(), 0);
		assertEquals(BOLO_TEMP_MASS_RATIO, this.myClump.getBolometricTemperatureMassRatio(), 0);
		assertEquals(SUP_DENSITY, this.myClump.getSurfaceDensity(), 0);
		assertEquals(TYPE, this.myClump.getType().toString());

		// Test: DELETE operation
		this.myClumpDB.remove(this.myClump);
		assertEquals("Testing removing:", this.myClumpDB.getByPrimaryKey(CLUMP_ID, this.myMapID), null);

		// Delete auxiliary object...
		try {
			this.myMapDB.remove(this.myMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
