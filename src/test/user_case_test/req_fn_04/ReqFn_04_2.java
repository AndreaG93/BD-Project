package test.user_case_test.req_fn_04;

import org.junit.Test;

import model.utility_import.ImportHigalAdditionalInfo;
import model.utility_import.AbstractImportUtility;

public class ReqFn_04_2 {

	private AbstractImportUtility myImportManager;

	/**
	 * This method is used to test {@code higal_additionalinfo.csv} file import.
	 */
	@Test
	public void higalAdditionalInfoImportTest() {

		

		// Load import class...
		this.myImportManager = new ImportHigalAdditionalInfo();

		// Test: IMPORT DATA...
		try {
			this.myImportManager.getData();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} 
	
}
