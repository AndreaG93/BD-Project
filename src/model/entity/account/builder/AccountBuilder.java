package model.entity.account.builder;

import model.entity.account.Account;

/**
 * The AccountBuilder {@code class} is used to build an account abject.
 * 
 * @author Andrea Graziani
 * @version 1.0
 */
public abstract class AccountBuilder {
	protected Account myAccount;

	
	public void createAccount(int arg0)
	{
		this.myAccount = new Account(arg0);
	}

	/**
	 * Get created {@link Account} object.
	 * 
	 * @return An {@link Account} object.
	 * @see Account
	 */
	public Account getAccount() {
		return myAccount;
	}
	


	/**
	 * 
	 * @param name
	 * @param surname
	 * @param email
	 * @param password
	 * @param username
	 */
	public void setAccount(String name, String surname, String email, String password, String username) {
		this.myAccount.setName(name);
		this.myAccount.setSurname(surname);
		this.myAccount.setEmail(email);
		this.myAccount.setPassword(password);
		this.myAccount.setUsername(username);
	}

	/**
	 * This abstract method is used to set Account's type.
	 */
	public abstract void setAccountType();

}
