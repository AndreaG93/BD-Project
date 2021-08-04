package model.database._init;

import java.util.Vector;
import model.database.AbstractDatabase;
import model.database.account.AccountDB;
import model.database.agency.AgencyDB;
import model.database.band.BandDB;
import model.database.clump.ClumpDB;
import model.database.clump_flux.ClumpFluxDB;
import model.database.ellipse.EllipseDB;
import model.database.flux.FluxDB;
import model.database.instrument.InstrumentDB;
import model.database.instrument_band.InstrumentBandDB;
import model.database.map.MapDB;
import model.database.satellite.SatelliteDB;
import model.database.satellite_agency.SatelliteAgencyDB;
import model.database.satellite_instrument.SatelliteInstrumentDB;
import model.database.source.SourceDB;
import model.database.source_flux.SourceFluxDB;
import model.database.source_source.SourceSourceDB;

public class InitDB extends AbstractDatabase {

	/**
	 * This method is used to check existence of table in database.
	 */
	public void tableExistenceCheck() {

		// Create a vector object...
		Vector<AbstractDatabase> myAbstractDatabases = new Vector<>();

		// Add database controller classes...
		myAbstractDatabases.add(AccountDB.getInstance());
		myAbstractDatabases.add(AgencyDB.getInstance());
		myAbstractDatabases.add(BandDB.getInstance());

		myAbstractDatabases.add(MapDB.getInstance());
		myAbstractDatabases.add(SatelliteDB.getInstance());
		myAbstractDatabases.add(FluxDB.getInstance());

		myAbstractDatabases.add(ClumpDB.getInstance());
		myAbstractDatabases.add(EllipseDB.getInstance());
		myAbstractDatabases.add(InstrumentDB.getInstance());

		myAbstractDatabases.add(SatelliteAgencyDB.getInstance());
		myAbstractDatabases.add(SatelliteInstrumentDB.getInstance());

		myAbstractDatabases.add(ClumpFluxDB.getInstance());
		myAbstractDatabases.add(InstrumentBandDB.getInstance());
		myAbstractDatabases.add(SourceDB.getInstance());

		myAbstractDatabases.add(SourceFluxDB.getInstance());
		myAbstractDatabases.add(SourceSourceDB.getInstance());

		for (AbstractDatabase obj : myAbstractDatabases)
			obj.createTable();
	}

	/**
	 * This method is used to add demo data into database.
	 */
	public void demoDataCheck() {
		executeWriteQuery(importSQL(getClass().getResourceAsStream("initDB.sql")));
	}

	@Override
	public void createTable() {
	}
}
