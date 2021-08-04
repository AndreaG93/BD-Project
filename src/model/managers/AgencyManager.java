package model.managers;

import java.util.Vector;

import model.Support;
import model.database.agency.AgencyDB;
import model.entity.agency.Agency;

public class AgencyManager {

	private AgencyDB myAgencyDB = AgencyDB.getInstance();
	
	/**
	 * This method is used to register an {@code Agency} object.
	 * 
	 * @param name
	 *            - Represents a {@code String} object.
	 * @throws Exception
	 */
	public void registerNewAgency(String name) throws Exception {

		// Data control...
		registrationNameCheck(name);
		
		if (this.myAgencyDB.getByName(name) != null)
			throw new Exception("Specified agency already exists.");

		// Insert...
		this.myAgencyDB.insert(name);
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

	/**
	 * This method is used to get a {@code Vector} object that contains all
	 * {@code Agency} object stored into database.
	 * 
	 * @return A {@code Vector} object.
	 * @throws Exception 
	 */
	public Vector<Agency> getAllAgencyVector() throws Exception {
		return this.myAgencyDB.getAll();
	}
}
