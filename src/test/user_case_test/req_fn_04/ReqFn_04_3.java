package test.user_case_test.req_fn_04;

import org.junit.Test;


import model.utility_import.AbstractImportUtility;
import model.utility_import.ImportSpitzerIRAC;

public class ReqFn_04_3 {

	private AbstractImportUtility myImportManager;

	/**
	 * This method is used to test {@code r08.csv} file import.
	 */
	@Test
	public void spitzerIracImportTest() {

	

		// Load import class...
		this.myImportManager = new ImportSpitzerIRAC();

		// Test: IMPORT DATA...
		try {
			this.myImportManager.getData();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
