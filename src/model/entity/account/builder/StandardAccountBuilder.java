package model.entity.account.builder;

import model.entity.account.type.Standard;

public class StandardAccountBuilder extends AccountBuilder {

	@Override
	public void setAccountType() {
		this.myAccount.setType(new Standard());
	}
}
