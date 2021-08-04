package model.managers;

import java.util.Vector;

import model.Support;
import model.database.clump.ClumpDB;
import model.database.map.MapDB;
import model.entity.clump.Clump;

public class ClumpManager {

	private ClumpDB myClumpDB = ClumpDB.getInstance();
	private MapDB myMapDB = MapDB.getInstance();
	
	
	public ClumpManager() {
		computeClumpMass();
	}

	/**
	 * 
	 */
	public void computeClumpMass()
	{
		myClumpDB.calcClumpMass();
	}
	

	public Vector<Clump> getAllClumpsByMap(String mapName, Integer limit, Integer offset) {
		int var0 = myMapDB.getMapByName(mapName).getID();
		return myClumpDB.getAllByMapId(var0, limit, offset);
	}

	/**
	 * 
	 * @param arg0
	 * @param arg1
	 * @return
	 * @throws Exception
	 */
	public Vector<Clump> getClumpByID(String arg0, String mapName) throws Exception {
		int clumpID = Support.stringToIntegerCheckField(arg0, "Clump ID");
		int mapID = myMapDB.getMapByName(mapName).getID();
		return this.myClumpDB.getByPrimaryKey(clumpID, mapID);
	}

	public Vector<Clump> getClumpsHostMassiveClump(String mapName, Integer limit, Integer offset) {
		
		// Retrieve data...
		int var0 = MapDB.getInstance().getMapByName(mapName).getID();
		return this.myClumpDB.massiveStarHostClumpSerch(var0, limit, offset);
		

	}

	/**
	 * This method is used to search {@code Clump} objects presents in a region
	 * (circle version).
	 * @param limit 
	 * @param offset 
	 * 
	 * @param radius
	 *            - Represents a {@code double}.
	 * @param x
	 *            - Represents a {@code double}.
	 * @param y
	 *            - Represents a {@code double}.
	 * @return A {@code Vector} object.
	 */
	public Vector<Clump> getClumpsRegionCircle(String radiusString, String xString, String yString, Integer limit, Integer offset) throws Exception {
		double radius = Support.stringToDoubleCheckField(radiusString, "Radius");
		double x = Support.stringToDoubleCheckField(xString, "X");
		double y = Support.stringToDoubleCheckField(yString, "Y");

		return this.myClumpDB.searchRegionCircle(radius, x, y, limit, offset);
				
	}

	public Vector<Clump> getClumpsRegionSquare(String sideString, String xString, String yString, Integer limit, Integer offset) throws Exception {
		double side = Support.stringToDoubleCheckField(sideString, "Side");
		double x = Support.stringToDoubleCheckField(xString, "X");
		double y = Support.stringToDoubleCheckField(yString, "Y");

		return this.myClumpDB.searchRegionSquare(side, x, y, limit, offset);
	}


	
	
	

	

}
