package model.managers;

import java.util.Vector;

import model.Support;
import model.database.band.BandDB;
import model.entity.band.Band;

public class BandManager {

	private BandDB myBandDB = BandDB.getInstance();

	/**
	 * This method is used to register an {@code Account} object.
	 * 
	 * @param resolution
	 *            - Represents a {@code String} object.
	 * @param wavelength
	 *            - Represents a {@code String} object.
	 * @throws Exception
	 */
	public void registerNewBand(String resolution, String wavelength) throws Exception {

		// Data control...
		double res = resolutionRegistrationCheck(resolution);
		double wav = wavelengthRegistrationCheck(wavelength);

		// Check existence...
		if (myBandDB.getByResolutionWavelenght(res, wav) != null)
			throw new Exception("Band already exist!");

		// Insert to DB...
		this.myBandDB.insert(res, wav);
	}

	/**
	 * This method is used to verify if specified argument is a valid.
	 * 
	 * @param arg0
	 *            - Represents a {@code String} object.
	 * @return A {@code double}.
	 * @throws Exception
	 */
	public double resolutionRegistrationCheck(String resolution) throws Exception {
		return Support.stringToDoubleCheckField(resolution, "Resolution");
	}

	/**
	 * This method is used to verify if specified argument is a valid.
	 * 
	 * @param arg0
	 *            - Represents a {@code String} object.
	 * @return A {@code double}.
	 * @throws Exception
	 */
	public double wavelengthRegistrationCheck(String wavelength) throws Exception {
		return Support.stringToDoubleCheckField(wavelength, "Wavelength");
	}

	/**
	 * This method is used to get a {@code Band} object.
	 * 
	 * @param resolution
	 *            - Represents a {@code double}.
	 * @param wavelength
	 *            - Represents a {@code double}.
	 * @return A {@code Band} object.
	 */
	public Band getBand(double resolution, double wavelength) {
		return this.myBandDB.getByResolutionWavelenght(resolution, wavelength);
	}

	/**
	 * This method is used to get a {@code Vector} object that contains all
	 * {@code Band} object stored into database.
	 * 
	 * @return A {@code Vector} object.
	 */
	public Vector<Band> getAllBand() {
		return this.myBandDB.getAll();
	}

	/**
	 * This method is used to get a {@code Band} object by ID
	 * 
	 * @param ID
	 *            - Represents an {@code int}.
	 * @return A {@code Band} object.
	 */
	public Band getBandByID(int ID) {
		return this.myBandDB.getByKey(ID);
	}
}
