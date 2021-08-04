package test.simple_database_operation_test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import org.junit.Test;
import model.database.band.BandDB;
import model.entity.band.Band;

public class Test_BandDB {

	private final double RESOLUTION = 15;
	private final double WAVELENGTH = 16.8;

	private BandDB myBandDB = BandDB.getInstance();
	private Band myBand = null;
	private int myBandID;

	@Test
	public void test() {

		// Test: INSERT operation
		this.myBandID = this.myBandDB.insert(RESOLUTION, WAVELENGTH);

		// -- SubTest: retrieved ID test
		assertNotEquals(this.myBandID, -1);

		// -- SubTest: retrieved object test
		this.myBand = this.myBandDB.getByKey(this.myBandID);
		
		assertNotEquals(this.myBand, null);
		assertEquals(RESOLUTION, this.myBand.getResolution(), 0);
		assertEquals(WAVELENGTH, this.myBand.getWavelength(), 0);

		// Test: DELETE operation
		this.myBandDB.remove(this.myBand);
		assertEquals(this.myBandDB.getByKey(this.myBandID), null);
	}
}
