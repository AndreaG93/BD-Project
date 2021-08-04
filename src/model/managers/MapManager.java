package model.managers;

import java.util.Vector;

import model.Support;
import model.database.map.MapDB;
import model.entity.map.Map;

public class MapManager {

	public static final String HIGAL_MAP = "HiGal";
	public static final String GLIMPSE_MAP = "Glimpse";
	public static final String MIPSGAL_MAP = "MIPS-GAL";

	private MapDB myMapDB = MapDB.getInstance();

	/**
	 * This method is used to register an {@code Map} object.
	 * 
	 * @param name
	 *            - Represents a {@code String} object.
	 * @throws Exception
	 */
	public void registerNewMap(String name) throws Exception {

		// Data control...
		registrationNameCheck(name);
		
		if (this.myMapDB.getMapByName(name) != null)
			throw new Exception("Specified map already exists.");

		// Insert...
		this.myMapDB.insert(name);
	}
	
	/**
	 * This method is used to verify if specified argument is a valid.
	 * 
	 * @param arg0
	 *            - Represents a {@code String} object.
	 * @throws Exception
	 */
	public void registrationNameCheck(String arg0) throws Exception {
		Support.nullCheck(arg0, "Name");
		Support.emptyStringCheck(arg0, "Name");
		Support.maxLengthCheck(arg0, "Name");
	}
	
	
	
	
	public Map getMapByName(String name) {
		return this.myMapDB.getMapByName(name);
	}

	public Vector<Map> getAllMap() {
		return this.myMapDB.getAll();
	}
	
	
	public void remove(Map arg0) throws Exception
	{
		this.myMapDB.remove(arg0);
	}
}
