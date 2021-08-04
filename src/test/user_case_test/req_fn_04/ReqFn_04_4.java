package test.user_case_test.req_fn_04;

import org.junit.Test;


import model.utility_import.AbstractImportUtility;
import model.utility_import.ImportSpitzerMIPS;

public class ReqFn_04_4 {

	private AbstractImportUtility myImportManager;

	/**
	 * This method is used to test {@code mips.csv} file import.
	 */
	@Test
	public void spitzerMipsImportTest() {

	

		// Load import class...
		this.myImportManager = new ImportSpitzerMIPS();

		// Test: IMPORT DATA...
		try {
			this.myImportManager.getData();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
