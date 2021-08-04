package model.utility_import;

import java.util.Vector;

import model.database.clump.ClumpDB;
import model.entity.clump.type.Prestellar;
import model.entity.clump.type.Protostellar;
import model.entity.clump.type.Unknown;
import model.entity.map.Map;
import model.managers.MapManager;

public class ImportHigal extends AbstractImportUtility {

	/**
	 * Constructs a newly allocated object.
	 */
	public ImportHigal() {
		this.myHeader = new String[] { "ID", "GLON", "GLAT", "TEMP", "L/M", "SURF_DENS", "EVOL_FLAG" };
		this.headerLinePosition = 11;
	}

	@Override
	public void ImportData() throws Exception {

		// Loading managers...
		ClumpDB myClumpDB = ClumpDB.getInstance();
		MapManager myMapManager = new MapManager();

		// Check existence of higal map...
		Map var = myMapManager.getMapByName(MapManager.HIGAL_MAP);
		if (var == null) {
			this.myAbstractDatabase.executeWriteQueryException(
					"INSERT INTO map (map_name) VALUES ('HiGal') ON CONFLICT (map_name) DO NOTHING");
			var = myMapManager.getMapByName(MapManager.HIGAL_MAP);
		}
		// Loading data...
		String higalMapID = String.valueOf(var.getID());

		// Reading data...
		for (int i = this.headerLinePosition + 1; i < this.recordList.size(); i++) {

			// Get data vector...
			Vector<String> data = readData(i);

			String type;

			switch (Integer.valueOf(data.get(6))) {
			case 0: {
				type = new Unknown().toString();
				break;
			}
			case 1: {
				type = new Prestellar().toString();
				break;
			}
			default:
				type = new Protostellar().toString();
				break;
			}

			// Generate SQL query...
			String SQL = myClumpDB.getInsertQuery(data.get(0), higalMapID, data.get(1), data.get(2), data.get(3),
					data.get(4), data.get(5), type, true);

			// Execute query...
			this.myAbstractDatabase.executeWriteQuery(SQL);
		}
	}
}
