package test.user_case_test.req_fn_04;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import org.junit.Test;

import model.database.clump.ClumpDB;
import model.entity.clump.Clump;
import model.entity.clump.type.Prestellar;
import model.managers.MapManager;
import model.utility_import.ImportHigal;
import model.utility_import.AbstractImportUtility;

public class ReqFn_04_1 {

	private Clump myClump;
	private ClumpDB myClumpDatabase;
	private AbstractImportUtility myImportManager;
	private int higalID;

	// Data taken from "higal.csv" file for test...
	private final int CLUMP_ID = 179761;
	private final double G_LONGITUDE = 42.602398;
	private final double G_LATITUDE = 0.017481;
	private final double TEMP = 12.682;
	private final double BOLO_TEMP_MASS_RATIO = 0.400791;
	private final double SUP_DENSITY = 0.0588188;
	private final String TYPE = new Prestellar().toString();

	/**
	 * This method is used to test {@code higal.csv} file import.
	 */
	@Test
	public void higalImportTest() {

	
		
		this.myClumpDatabase = ClumpDB.getInstance();
		this.higalID = new MapManager().getMapByName(MapManager.HIGAL_MAP).getID();	
		
		// Import data...
		this.myImportManager = new ImportHigal();
		try {
			this.myImportManager.getData();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// -- SubTest: retrieved object test
		this.myClump = this.myClumpDatabase.getByPrimaryKey(CLUMP_ID, higalID).firstElement();

		assertNotEquals("Testing insert...", this.myClump, null);
		assertEquals(CLUMP_ID, this.myClump.getID(), 0);
		assertEquals(higalID, this.myClump.getMyMap().getID(), 0);
		assertEquals(G_LATITUDE, this.myClump.getGalacticLatitude(), 0);
		assertEquals(G_LONGITUDE, this.myClump.getGalacticLongitude(), 0);
		assertEquals(TEMP, this.myClump.getTemperature(), 0);
		assertEquals(BOLO_TEMP_MASS_RATIO, this.myClump.getBolometricTemperatureMassRatio(), 0);
		assertEquals(SUP_DENSITY, this.myClump.getSurfaceDensity(), 0);
		assertEquals(TYPE, this.myClump.getType().toString());
	}
}
