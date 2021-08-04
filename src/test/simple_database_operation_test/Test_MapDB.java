package test.simple_database_operation_test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import org.junit.Test;
import model.database.map.MapDB;
import model.entity.map.Map;

public class Test_MapDB {

	private final String MAP_NAME = "TestMapName";
	
	private int MapID;
	private Map myMap = null;
	private MapDB myMapDatabase = MapDB.getInstance();

	@Test
	public void Test() {

		// Test: INSERT operation
		this.MapID = this.myMapDatabase.insert(MAP_NAME);

		// -- SubTest: retrieved ID test
		assertNotEquals(this.MapID, -1);

		// -- SubTest: retrieved object test
		
		// By ID
		this.myMap = this.myMapDatabase.getMapByID(MapID);
		assertNotEquals(this.myMap, null);
		
		// By name
		this.myMap = this.myMapDatabase.getMapByName(MAP_NAME);
		assertNotEquals(this.myMap, null);
		
		assertEquals(this.myMap.getName(), MAP_NAME);

		// Test: DELETE operation
		try {
			this.myMapDatabase.remove(this.myMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(this.myMapDatabase.getMapByID(this.MapID), null);
	}
}
