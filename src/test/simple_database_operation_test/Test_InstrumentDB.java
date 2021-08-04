package test.simple_database_operation_test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import org.junit.Test;


import model.database.band.BandDB;
import model.database.instrument.InstrumentDB;
import model.database.instrument_band.InstrumentBandDB;
import model.database.map.MapDB;
import model.entity.band.Band;
import model.entity.instrument.Instrument;

public class Test_InstrumentDB {

	private final String NAME = "NameTest";
	private final double RESOLUTION = 26.7;
	private final double WAVELENGTH = 86.9;
	
	String GLIMPSE_MAP = "Glimpse";
	private BandDB myBandDB;
	private InstrumentBandDB myInstrumentBandDB;
	private InstrumentDB myInstrumentDB;
	private Instrument myInstrument;
	private int ID;
	
	
	@Test
	public void test() {
		
		// *** Initialization ***
		
	
		this.myInstrumentBandDB = InstrumentBandDB.getInstance();
		this.myInstrumentDB = InstrumentDB.getInstance();
		this.myBandDB = BandDB.getInstance();
		MapDB myMapDB = MapDB.getInstance();
		
		// *** *** *** *** *** ***
		
		// Test: INSERT operation
		this.ID = this.myInstrumentDB.insert(NAME, myMapDB.getMapByName(GLIMPSE_MAP).getID());
			
		int BandID = this.myBandDB.insert(RESOLUTION, WAVELENGTH);
		
		this.myInstrumentBandDB.insert(ID, BandID);
		
		// *** Get auxiliary object...
		Band myBand = this.myBandDB.getByKey(BandID);
		
		// Test: GET operation
		this.myInstrument = this.myInstrumentDB.getByKey(this.ID);
		
		// -- SubTest: retrieved object test
		assertNotEquals(null, this.myInstrument);
		assertEquals(myInstrument.getName(), NAME);
		assertEquals(myInstrument.getSupportedBands().firstElement().getResolution(), myBand.getResolution(), 0);
		assertEquals(myInstrument.getSupportedBands().firstElement().getWavelength(), myBand.getWavelength(), 0);
		
		// Test: DELETE operation
		this.myInstrumentDB.remove(myInstrument);
		assertEquals(this.myInstrumentDB.getByKey(this.ID), null);
		
		// *** Delete auxiliary object...
		this.myBandDB.remove(myBand);
	}
}
