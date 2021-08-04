package model.entity.clump.builder;

import model.entity.clump.type.Prestellar;

public class PrestellarClumpBuilder extends ClumpBuilder {

	@Override
	public void setClumpType() {
		this.myClump.setType(new Prestellar());
	}
}