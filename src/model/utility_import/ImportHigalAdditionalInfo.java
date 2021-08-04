package model.utility_import;

import java.util.Vector;
import model.database.clump.ClumpDB;
import model.database.clump_flux.ClumpFluxDB;
import model.database.ellipse.EllipseDB;
import model.database.flux.FluxDB;
import model.entity.map.Map;
import model.managers.BandManager;
import model.managers.MapManager;

public class ImportHigalAdditionalInfo extends AbstractImportUtility {

	/**
	 * Constructs a newly allocated object.
	 */
	public ImportHigalAdditionalInfo() {
		this.myHeader = new String[] { "SOURCE_ID", "S70", "S160", "S250", "S350", "S500", "FW70_1", "FW70_2",
				"FW160_1", "FW160_2", "FW250_1", "FW250_2", "FW350_1", "FW350_2", "FW500_1", "FW500_2", "PA70", "PA160",
				"PA250", "PA350", "PA500" };
		this.headerLinePosition = 26;
	}

	@Override
	protected void ImportData() throws Exception {

		// Loading database...
		ClumpDB myClumpDB = ClumpDB.getInstance();
		EllipseDB myEllipseDB = EllipseDB.getInstance();
		FluxDB myFluxDB = FluxDB.getInstance();
		ClumpFluxDB myClumpFluxDB = ClumpFluxDB.getInstance();

		// Loading managers...
		MapManager myMapManager = new MapManager();
		BandManager myBandManager = new BandManager();

		// Check existence of higal map...
		Map var = myMapManager.getMapByName(MapManager.HIGAL_MAP);
		if (var == null) {
			this.myAbstractDatabase.executeWriteQueryException(
					"INSERT INTO map (map_name) VALUES ('HiGal') ON CONFLICT (map_name) DO NOTHING");
			var = myMapManager.getMapByName(MapManager.HIGAL_MAP);
		}
		// Loading data...
		String higalMapID = String.valueOf(var.getID());

		int microns70BandID = myBandManager.getBand(70.0, 5.2).getID();
		int microns160BandID = myBandManager.getBand(160, 12.0).getID();
		int microns250BandID = myBandManager.getBand(250.0, 18.0).getID();
		int microns350BandID = myBandManager.getBand(350.0, 24.0).getID();
		int microns500BandID = myBandManager.getBand(500.0, 35.0).getID();

		for (int i = this.headerLinePosition + 1; i < this.recordList.size(); i++) {

			// Get data vector...
			Vector<String> x = readData(i);

			String mySQL = "BEGIN; ";

			// Insert...
			mySQL += myClumpDB.getInsertQuery(x.get(0), higalMapID, null, null, null, null, null, null, false);

			// INSERT / UPDATE FLUX...
			mySQL += "DELETE FROM flux WHERE flux_id in " + "(SELECT clumpflux_flux_id " + "FROM clumpflux "
					+ "WHERE clumpflux_clump_id = " + x.get(0) + " AND clumpflux_map_id = " + higalMapID + ");";

			mySQL += "DELETE FROM clumpflux WHERE clumpflux_clump_id = " + x.get(0) + " AND clumpflux_map_id = "
					+ higalMapID + ";";

			// Flux 70
			mySQL += myFluxDB.getInsertQuery(microns70BandID, x.get(1), null);
			mySQL += myClumpFluxDB.getInsertQuery(x.get(0), String.valueOf(higalMapID));

			// Flux 160
			mySQL += myFluxDB.getInsertQuery(microns160BandID, x.get(2), null);
			mySQL += myClumpFluxDB.getInsertQuery(x.get(0), String.valueOf(higalMapID));

			// Flux 250
			mySQL += myFluxDB.getInsertQuery(microns250BandID, x.get(3), null);
			mySQL += myClumpFluxDB.getInsertQuery(x.get(0), String.valueOf(higalMapID));

			// Flux 350
			mySQL += myFluxDB.getInsertQuery(microns350BandID, x.get(4), null);
			mySQL += myClumpFluxDB.getInsertQuery(x.get(0), String.valueOf(higalMapID));

			// Flux 500
			mySQL += myFluxDB.getInsertQuery(microns500BandID, x.get(5), null);
			mySQL += myClumpFluxDB.getInsertQuery(x.get(0), String.valueOf(higalMapID));

			// INSERT / UPDATE ELLIPSE...

			mySQL += myEllipseDB.getInsertQuery(x.get(0), higalMapID, microns70BandID, x.get(6), x.get(7), x.get(16))
					+ ";";
			mySQL += myEllipseDB.getInsertQuery(x.get(0), higalMapID, microns160BandID, x.get(8), x.get(9), x.get(17))
					+ ";";
			mySQL += myEllipseDB.getInsertQuery(x.get(0), higalMapID, microns250BandID, x.get(10), x.get(11), x.get(18))
					+ ";";
			mySQL += myEllipseDB.getInsertQuery(x.get(0), higalMapID, microns350BandID, x.get(12), x.get(13), x.get(19))
					+ ";";
			mySQL += myEllipseDB.getInsertQuery(x.get(0), higalMapID, microns500BandID, x.get(14), x.get(15), x.get(20))
					+ ";";

			mySQL += "COMMIT; ";

			this.myAbstractDatabase.executeWriteQuery(mySQL);
		}
	}
}