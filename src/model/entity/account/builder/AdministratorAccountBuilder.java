package model.entity.account.builder;

import model.entity.account.type.Administrator;

public class AdministratorAccountBuilder extends AccountBuilder {

	@Override
	public void setAccountType() {
		this.myAccount.setType(new Administrator());
	}
}
