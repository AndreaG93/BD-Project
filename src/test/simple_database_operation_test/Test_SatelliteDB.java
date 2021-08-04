package test.simple_database_operation_test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import java.time.LocalDate;
import org.junit.Test;
import model.database.satellite.SatelliteDB;
import model.entity.agency.Agency;
import model.entity.band.Band;
import model.entity.instrument.Instrument;
import model.entity.satellite.Satellite;

public class Test_SatelliteDB {

	private final String NAME = "NameTest";
	private final LocalDate FIRST_MISSION_DATE = LocalDate.of(2000, 12, 25);
	private final LocalDate LAST_MISSION_DATE = LocalDate.of(1500, 12, 25);
	private int ID;
	private Satellite myTestSatellite = null;
	private SatelliteDB mySatelliteDatabase = SatelliteDB.getInstance();

	public void satelliteDatabaseTest() {

		// Test: INSERT operation
		this.ID = this.mySatelliteDatabase.insert(NAME, FIRST_MISSION_DATE, LAST_MISSION_DATE);

		// -- SubTest: retrieved ID test
		assertNotEquals(this.ID, -1);

		// -- SubTest: retrieved object test
		this.myTestSatellite = this.mySatelliteDatabase.getByKey(ID);

		assertNotEquals(this.myTestSatellite, null);
		assertEquals(this.myTestSatellite.getName(), NAME);
		assertEquals(this.myTestSatellite.getFirstMissionDate(), FIRST_MISSION_DATE);
		assertEquals(this.myTestSatellite.getLastMissionDate(), LAST_MISSION_DATE);

		// Test: DELETE operation
		this.mySatelliteDatabase.remove(myTestSatellite);
		assertEquals(this.mySatelliteDatabase.getByKey(this.ID), null);
	}
	
	
	public void insertTestWithNullLastMission()
	{
		// Insert object
		this.ID = this.mySatelliteDatabase.insert(NAME, FIRST_MISSION_DATE, null);
		this.myTestSatellite = this.mySatelliteDatabase.getByKey(ID);
		
		// Test: Retrieved object test
		assertEquals(this.myTestSatellite.getName(), NAME);
		assertEquals(this.myTestSatellite.getFirstMissionDate(), FIRST_MISSION_DATE);
		assertEquals(this.myTestSatellite.getLastMissionDate(), null);
		
		// Delete object
		this.mySatelliteDatabase.remove(myTestSatellite);
	}
	
	
	@Test
	public void test()
	{
		int x = 0;
		
		for(Satellite obj : this.mySatelliteDatabase.getAll())
		{
			System.out.println("--- Satellite # " + x + " ---");
			System.out.println(obj.getName());
			System.out.println(obj.getFirstMissionDate());
			System.out.println(obj.getLastMissionDate());
			
			for (Agency obj_01 : obj.getAgencies())
			{
				System.out.println(obj_01.getID());
				System.out.println(obj_01.getName());
			}
			
			for (Instrument obj_02 : obj.getInstruments())
			{
				System.out.println(obj_02.getID());
				System.out.println(obj_02.getName());
				
				for (Band obj_03 : obj_02.getSupportedBands())
				{
					System.out.println(obj_03.getID());
					System.out.println(obj_03.getResolution());
					System.out.println(obj_03.getWavelength());
				}
			}
			x++;
		}
	}
}
