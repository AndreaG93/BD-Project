package model.entity.clump.builder;

import model.entity.clump.type.Unknown;

public class StarlessUnboundClumpBuilder extends ClumpBuilder {

	@Override
	public void setClumpType() {
		this.myClump.setType(new Unknown());
	}
}