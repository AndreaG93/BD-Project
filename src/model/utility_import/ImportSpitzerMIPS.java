package model.utility_import;

import java.util.Vector;
import model.database.flux.FluxDB;
import model.database.source.SourceDB;
import model.database.source_flux.SourceFluxDB;
import model.entity.map.Map;
import model.managers.BandManager;
import model.managers.MapManager;

public class ImportSpitzerMIPS extends AbstractImportUtility {

	/**
	 * Constructs a newly allocated object.
	 */
	public ImportSpitzerMIPS() {
		this.myHeader = new String[] { "MIPSGAL", "GLON", "GLAT", "[24]", "e_[24]", "GLIMPSE" };
		this.headerLinePosition = 10;
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

		// Check existence of higal map...
		Map var = myMapManager.getMapByName(MapManager.MIPSGAL_MAP);
		if (var == null) {
			this.myAbstractDatabase.executeWriteQueryException(
					"INSERT INTO map (map_name) VALUES ('MIPS-GAL') ON CONFLICT (map_name) DO NOTHING");
			var = myMapManager.getMapByName(MapManager.MIPSGAL_MAP);
		}

		// Loading data...
		int mipsgalMapID = var.getID();

		// Loading auxiliary data about band...
		int microns24BandID = myBandManager.getBand(24.0, 6.0).getID();

		for (int i = this.headerLinePosition + 1; i < this.recordList.size(); i++) {

			// Get data vector...
			Vector<String> data = readData(i);

			// Generate SQL Transaction...
			String SQL = "BEGIN;\n";

			// Insert source...
			SQL += mySourceDB.getInsertQuery(data.get(0), mipsgalMapID, Double.valueOf(data.get(1)),
					Double.valueOf(data.get(2)));

			// Delete existing flux...
			SQL += "DELETE FROM flux WHERE flux_id in (SELECT sourceflux_flux_id FROM sourceflux WHERE sourceflux_source_id = '"
					+ data.get(0) + "');\n";

			// Flux 24
			SQL += myFluxDB.getInsertQuery(microns24BandID, data.get(3), null);
			SQL += mySourceFluxDB.getInsertQuery(data.get(0), mipsgalMapID);

			SQL += "COMMIT;\n";

			// Execute query...
			this.myAbstractDatabase.executeWriteQuery(SQL);
		}
	}
}
