package model.entity.source.builder;

import model.entity.map.Map;
import model.entity.source.Source;

public class SourceBuilderDirector {

	/**
	 * This method is used to build a new {@code Source} object.
	 * 
	 * @param id
	 *            - Represents an {@code String} object.
	 * @param map
	 *            - Represents an {@code Map} object.
	 * @param galacticLatitude
	 *            - Represents an {@code Double} object.
	 * @param galacticLongitude
	 *            - Represents an {@code Double} object.
	 * @return A {@code Source} object.
	 */
	public static Source build(String id, Map map, Double galacticLatitude, Double galacticLongitude) {
		Source obj = new Source(id, map);
		obj.setGalacticLatitude(galacticLatitude);
		obj.setGalacticLongitude(galacticLongitude);

		return obj;
	}
}
