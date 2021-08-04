package model.managers;

import java.util.Vector;

import model.Support;
import model.database.map.MapDB;
import model.database.source.SourceDB;
import model.entity.band.Band;
import model.entity.clump.Clump;
import model.entity.source.Source;

public class SourceManager {

	private SourceDB mySourceDB = SourceDB.getInstance();
	private MapDB myMapDB = MapDB.getInstance();
	
	
	
	
	
	
	
	public Vector<Source> searchSourceInsideClump(Clump clump, Band band, int limit, int offset)
	{
		return this.mySourceDB.searchSourceInsideClump(clump.getID(), band.getID(), limit, offset);
	}
	
	
	public Vector<Source> searchSourceInsideClumpYSO(Clump clump, Band band, int limit, int offset)
	{
		return this.mySourceDB.searchSourceInsideClumpYSO(clump.getID(), band.getID(), limit, offset);
	}
	
	
	/**
	 * 
	 * @param radiusString
	 * @param xString
	 * @param yString
	 * @param limit 
	 * @param offset 
	 * @return
	 * @throws Exception
	 */
	public Vector<Source> getSourceRegionCircle(String radiusString, String xString, String yString, int limit, int offset, String mapName) throws Exception
	{
		int var0 = myMapDB.getMapByName(mapName).getID();
		double radius = Support.stringToDoubleCheckField(radiusString, "Radius");
		double x = Support.stringToDoubleCheckField(xString, "Starting point X");
		double y = Support.stringToDoubleCheckField(yString, "Starting point Y");
		
		return this.mySourceDB.searchRegionCircle(radius, x, y, var0, limit, offset);		
	}
	
	
	/**
	 * 
	 * @param sideString
	 * @param xString
	 * @param yString
	 * @param limit 
	 * @param offset 
	 * @return
	 * @throws Exception
	 */
	public Vector<Source> getSourceRegionSquare(String sideString, String xString, String yString, int limit, int offset, String mapName) throws Exception
	{
		int var0 = myMapDB.getMapByName(mapName).getID();
		double side = Support.stringToDoubleCheckField(sideString, "Side");
		double x = Support.stringToDoubleCheckField(xString, "Starting point X");
		double y = Support.stringToDoubleCheckField(yString, "Starting point Y");
		
		return this.mySourceDB.searchRegionSquare(side, x, y,var0,limit, offset);
	}

	
	
	public Vector<Source> getAllSourceByMap(String mapName, int limit, int offset) {
		int var0 = myMapDB.getMapByName(mapName).getID();
		return this.mySourceDB.getByMapID(var0, limit, offset);
				
				
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
