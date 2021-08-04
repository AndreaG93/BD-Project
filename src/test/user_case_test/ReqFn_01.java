package test.user_case_test;

import static org.junit.Assert.fail;
import org.junit.Test;
import static org.junit.Assert.*;
import model.entity.account.type.Standard;
import model.managers.AccountManager;

/**
 * The {@code ReqFn_01} class is used to test REQ-FN-1
 * 
 * @author Andrea Graziani
 * @see 1.0
 */
public class ReqFn_01 {

	private final String USERNAME = "UserNameTest";
	private final String PASSWORD = "PasswordTest123";
	private final String NAME = "NameTest";
	private final String SURNAME = "SurnameTest";
	private final String ACCOUNT_TYPE = new Standard().toString();
	private final String EMAIL = "@EmailTest";

	AccountManager myAccountManager = new AccountManager();
	int myAccountID;

	@Test
	public void LoginTest() {

		// Insert new account with valid data
		try {
			myAccountManager.registerNewAccount(ACCOUNT_TYPE, USERNAME, PASSWORD, PASSWORD, NAME, SURNAME, EMAIL);
		} catch (Exception e) {
			System.out.println("Message: " + e.getMessage());
			fail();
		}

		// Login with valid data
		try {
			myAccountManager.login(USERNAME, PASSWORD);
		} catch (Exception e) {
			System.out.println("Message: " + e.getMessage());
			fail();
		}

		assertNotEquals("Test...", myAccountManager.getCurrentLoggedAccount(), null);

		// Delete account
		//myAccountManager.removeCurrentLoggedAccount();
	}

	@Test
	public void LoginTestWithInvalidData() {

		// Login with invalid data
		try {
			myAccountManager.login(USERNAME, PASSWORD);
		} catch (Exception e) {
			System.out.println("Message: " + e.getMessage());
		}

		// Login with invalid data
		try {
			myAccountManager.login(null, null);
		} catch (Exception e) {
			System.out.println("Message: " + e.getMessage());
		}

		assertEquals("Test...", myAccountManager.getCurrentLoggedAccount(), null);
	}
}
