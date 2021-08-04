package model.entity.clump.builder;

import model.entity.clump.type.Protostellar;

public class ProtostellarClumpBuilder extends ClumpBuilder {

	@Override
	public void setClumpType() {
		this.myClump.setType(new Protostellar());
	}
}