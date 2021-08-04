package model.entity.account.builder;

import model.entity.account.Account;

public class AccountBuilderDirector {

	public static Account createsAccountObject(int id, String name, String surname, String email, String password, String type, String username) {
		AccountBuilder myBuilder = null;
		
		try {
			String packagePath = AccountBuilderDirector.class.getPackage().getName();
			myBuilder = (AccountBuilder) Class.forName(packagePath + "." + type + "AccountBuilder").newInstance();
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		myBuilder.createAccount(id);
		myBuilder.setAccountType();
		myBuilder.setAccount(name, surname, email, password, username);

		return myBuilder.getAccount();
	}
}
