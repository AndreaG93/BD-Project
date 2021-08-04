package model.entity.clump.builder;

import model.entity.clump.Clump;
import model.entity.map.Map;

public class ClumpBuilderDirector {

	public static Clump create(int id, Map map, Double temperature, Double galacticLongitude, Double galacticLatitude,
			Double surfaceDensity, Double bolometricTemperatureMassRatio, String type, Double mass) {
		ClumpBuilder builder = null;

		try {
			String packagePath = ClumpBuilderDirector.class.getPackage().getName();
			builder = (ClumpBuilder) Class.forName(packagePath + "." + type + "ClumpBuilder").newInstance();
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			builder = new UnknownClumpBuilder();
		}

		builder.createClump(id);
		builder.setClumpData(temperature, galacticLongitude, galacticLatitude, surfaceDensity,
				bolometricTemperatureMassRatio, map, mass);
		builder.setClumpType();

		return builder.getClump();
	}
}
