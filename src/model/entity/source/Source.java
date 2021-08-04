package model.entity.source;

import java.util.Vector;

import model.entity.flux.Flux;
import model.entity.map.Map;

public class Source {

	private final String id;
	private final Map map;
	private Double galacticLongitude;
	private Double galacticLatitude;
	private Vector<Flux> fluxVector = new Vector<>();

	/**
	 * Constructs a newly allocated {@code Source} object.
	 * 
	 * @param id
	 *            - Represents an {@code String} object.
	 * @param map
	 *            - Represents an {@code int}.
	 */
	public Source(String id, Map map) {
		super();
		this.id = id;
		this.map = map;
	}

	public Double getGalacticLongitude() {
		return galacticLongitude;
	}

	public void setGalacticLongitude(Double galacticLongitude) {
		this.galacticLongitude = galacticLongitude;
	}

	public Double getGalacticLatitude() {
		return galacticLatitude;
	}

	public void setGalacticLatitude(Double galacticLatitude) {
		this.galacticLatitude = galacticLatitude;
	}

	public Vector<Flux> getFluxVector() {
		return fluxVector;
	}

	public void setFluxVector(Vector<Flux> fluxVector) {
		this.fluxVector = fluxVector;
	}

	public String getId() {
		return id;
	}

	public Map getMap() {
		return map;
	}
}
