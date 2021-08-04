package model.managers;

import model.AbstractManager;
import model.Support;
import model.database.account.AccountDB;
import model.entity.account.Account;
import model.entity.account.type.AccountType;

public class AccountManager extends AbstractManager {
	private Account currentLoggedAccount = null;
	private AccountDB myDB = AccountDB.getInstance();

	/**
	 * This method is used to do login.
	 * 
	 * @param arg0
	 *            - Represents a {@code String} object.
	 * @param arg1
	 *            - Represents a {@code String} object.
	 * @throws Exception
	 */
	public void login(String username, String password) throws Exception {

		Support.nullCheck(username, "UserName");
		Support.nullCheck(password, "Password");
		Support.emptyStringCheck(username, "UserName");
		Support.emptyStringCheck(password, "Password");

		Account myAccount = myDB.getAccountByUsername(username);

		if (myAccount != null) {

			if (myAccount.getPassword().equals(password)) {
				currentLoggedAccount = myAccount;
				notifyToObservers();
			} else
				throw new Exception("Specified password is wrong; please check your password.");
		} else
			throw new Exception("Specified username doesn't exist.");
	}

	/**
	 * This method is used to do logout.
	 */
	public void logout() {
		this.currentLoggedAccount = null;
		notifyToObservers();
	}

	/**
	 * This method is used to get current logged {@code Account}.
	 * 
	 * @return An {@code Account} object;
	 * @see Account
	 */
	public Account getCurrentLoggedAccount() {
		return this.currentLoggedAccount;
	}

	/**
	 * This method is used to register an {@code Account} object.
	 * 
	 * @param accountType
	 *            - Represents a {@code String} object.
	 * @param username
	 *            - Represents a {@code String} object.
	 * @param password
	 *            - Represents a {@code String} object.
	 * @param passwordRepeated
	 *            - Represents a {@code String} object.
	 * @param name
	 *            - Represents a {@code String} object.
	 * @param surname
	 *            - Represents a {@code String} object.
	 * @param email
	 *            - Represents a {@code String} object.
	 * @throws Exception
	 */
	public void registerNewAccount(String accountType, String username, String password, String passwordRepeated,
			String name, String surname, String email) throws Exception {

		this.myDB.insert(accountType, username, password, name, surname, email);
	}

	/**
	 * This method is used to verify if specified name is valid.
	 * 
	 * @param arg0
	 *            - Represents a {@code String} object.
	 * @throws Exception
	 */
	public void nameRegistrationCheck(String arg0) throws Exception {
		Support.validStringOnlyLetterCheck(arg0, "Name");
	}

	/**
	 * This method is used to verify if specified surname is valid.
	 * 
	 * @param arg0
	 *            - Represents a {@code String} object.
	 * @throws Exception
	 */
	public void surnameRegistrationCheck(String arg0) throws Exception {
		Support.validStringOnlyLetterCheck(arg0, "Surname");
	}

	/**
	 * This method is used to verify if specified account type is valid.
	 * 
	 * @param arg0
	 *            - Represents a {@code String} object.
	 * @throws Exception
	 */
	public void typeAccountRegistrationCheck(String arg0) throws Exception {

		Support.nullCheck(arg0, "Account Type");

		try {
			String packagePath = AccountType.class.getPackage().getName();
			Class.forName(packagePath + "." + arg0);
		} catch (ClassNotFoundException e) {
			throw new Exception("Unknown specified type");
		}
	}

	/**
	 * This method is used to verify if specified email is valid.
	 * 
	 * @param arg0
	 *            - Represents a {@code String} object.
	 * @throws Exception
	 */
	public void emailRegistrationCheck(String arg0) throws Exception {

		Support.nullCheck(arg0, "email");
		Support.emptyStringCheck(arg0, "email");
		Support.maxLengthCheck(arg0, "email");

		boolean invalid = true;

		// It verifies if specified email address contains the symbol '@'.
		for (char elem : arg0.toCharArray()) {
			if (elem == '@')
				invalid = false;
		}
		if (invalid)
			throw new Exception("Specifed email address is not valid");
	}

	/**
	 * This method verifies if specified userName is valid.
	 * 
	 * @param arg0
	 *            - Represents a {@code String} object.
	 * @throws Exception
	 */
	public void userNameRegistrationCheck(String arg0) throws Exception {
		Support.nullCheck(arg0, "UserName");
		Support.emptyStringCheck(arg0, "UserName");
		Support.minStringCheck(arg0, "UserName");
		Support.maxLengthCheck(arg0, "UserName");
		
		if(AccountDB.getInstance().getAccountByUsername(arg0) != null)
			throw new Exception("INVALID: Specified 'Username' already exist.");
		
	}

	/**
	 * This method verifies if specified password respects minimal security
	 * requirements; otherwise it raises an exception. Furthermore it verifies
	 * if {@code password} and {@code repeatedPassword} match.
	 * 
	 * @param password
	 *            - Represents a {@code String} object.
	 * @param repeatedPassword
	 *            - Represents a {@code String} object.
	 * @throws Exception
	 */
	public void passwordRegistrationCheck(String password, String repeatedPassword) throws Exception {

		Support.nullCheck(password, "Password");
		Support.emptyStringCheck(password, "Password");
		Support.minStringCheck(password, "Password");
		Support.maxLengthCheck(password, "Password");

		boolean foundDigit = false;
		boolean foundUpperCase = false;
		boolean foundLowerCase = false;
		
		for (char elem : password.toCharArray()) {
			if (Character.isLowerCase(elem))
				foundLowerCase = true;
			else if (Character.isUpperCase(elem))
				foundUpperCase = true;
			else if (Character.isDigit(elem))
				foundDigit = true;	
		}

		if (!(foundDigit && foundUpperCase && foundLowerCase))
			throw new Exception("Is required at least one digit, one upper case and one lower case character");

		if (!password.equals(repeatedPassword))
			throw new Exception("The specified passwords don't match.");
	}
}
