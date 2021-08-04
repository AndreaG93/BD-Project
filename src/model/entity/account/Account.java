package model.entity.account;

import model.entity.account.type.AccountType;

public class Account {

	private final int ID;

	private String username;
	private String password;
	private AccountType type;
	private String name;
	private String surname;
	private String email;

	/**
	 * Constructs a newly allocated {@code Account} object.
	 * 
	 * @param arg0
	 *            - Represents an {@code int}
	 */
	public Account(int arg0) {
		this.ID = arg0;
	}
	
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public AccountType getType() {
		return type;
	}
	public void setType(AccountType type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getID() {
		return ID;
	}
	
}
