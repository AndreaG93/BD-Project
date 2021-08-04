package model.utility_import;

import java.util.Vector;
import model.database.flux.FluxDB;
import model.database.source.SourceDB;
import model.database.source_flux.SourceFluxDB;
import model.entity.map.Map;
import model.managers.BandManager;
import model.managers.MapManager;

public class ImportSpitzerIRAC extends AbstractImportUtility {

	/**
	 * Constructs a newly allocated object.
	 */
	public ImportSpitzerIRAC() {
		this.myHeader = new String[] { "SSTGLMC", "GLon", "GLat", "[3.6]G", "[4.5]G", "[5.8]G", "[8.0]G" };
		this.headerLinePosition = 11;
	}

	@Override
	protected void ImportData() throws Exception {

		// Loading managers...
		BandManager myBandManager = new BandManager();
		MapManager myMapManager = new MapManager();

		// Loading database...
		SourceDB mySourceDB = SourceDB.getInstance();
		SourceFluxDB mySourceFluxDB = SourceFluxDB.getInstance();
		FluxDB myFluxDB = FluxDB.getInstance();

		// Loading data...
		int microns3_6BandID = myBandManager.getBand(3.6, 1.7).getID();
		int microns4_5BandID = myBandManager.getBand(4.5, 1.8).getID();
		int microns5_8BandID = myBandManager.getBand(5.8, 1.9).getID();
		int microns8_0BandID = myBandManager.getBand(8.0, 2.0).getID();

		// Check existence of higal map...
		Map var = myMapManager.getMapByName(MapManager.GLIMPSE_MAP);
		if (var == null){
			this.myAbstractDatabase.executeWriteQueryException(
					"INSERT INTO map (map_name) VALUES ('Glimpse') ON CONFLICT (map_name) DO NOTHING");
			var = myMapManager.getMapByName(MapManager.GLIMPSE_MAP);
		}
		// Loading data...
		int glimpseMapID = var.getID();

		// Reading data...
		for (int i = this.headerLinePosition + 1; i < this.recordList.size(); i++) {

			// Get data vector...
			Vector<String> data = readData(i);

			// Generate SQL Transaction...
			String SQL = "BEGIN;\n";

			// Insert source...
			SQL += mySourceDB.getInsertQuery(data.get(0), glimpseMapID, Double.valueOf(data.get(1)),
					Double.valueOf(data.get(2)));

			// Delete existing flux...
			SQL += "DELETE FROM flux WHERE flux_id in (SELECT sourceflux_flux_id FROM sourceflux WHERE sourceflux_source_id = '"
					+ data.get(0) + "');\n";

			// Flux 3_6
			SQL += myFluxDB.getInsertQuery(microns3_6BandID, data.get(3), null);
			SQL += mySourceFluxDB.getInsertQuery(data.get(0), glimpseMapID);

			// Flux 4_5
			SQL += myFluxDB.getInsertQuery(microns4_5BandID, data.get(4), null);
			SQL += mySourceFluxDB.getInsertQuery(data.get(0), glimpseMapID);

			// Flux 5_8
			SQL += myFluxDB.getInsertQuery(microns5_8BandID, data.get(5), null);
			SQL += mySourceFluxDB.getInsertQuery(data.get(0), glimpseMapID);

			// Flux 8_0
			SQL += myFluxDB.getInsertQuery(microns8_0BandID, data.get(6), null);
			SQL += mySourceFluxDB.getInsertQuery(data.get(0), glimpseMapID);

			SQL += "COMMIT;\n";

			// Execute query...
			this.myAbstractDatabase.executeWriteQuery(SQL);
		}
	}
}
