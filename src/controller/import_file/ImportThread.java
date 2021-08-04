package controller.import_file;

import controller.loading.LoadingLoader;
import javafx.application.Platform;
import javafx.scene.control.Alert.AlertType;
import model.Support;
import model.utility_import.AbstractImportUtility;

public class ImportThread extends Thread implements Runnable {

	private AbstractImportUtility myAbstractImportUtility;
	private LoadingLoader myLoadingLoader;

	public ImportThread(AbstractImportUtility arg0, LoadingLoader arg1) {
		this.myAbstractImportUtility = arg0;
		this.myLoadingLoader = arg1;
	}

	@Override
	public void run() {

		try {

			// Update view at the Application Thread...
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					try {
						myLoadingLoader.start();
					} catch (Exception e) {
						Support.getAlert(AlertType.ERROR, "Error", e.getMessage()).show();
					}
				}
			});

			// Importing data and computes time...
			long startTime = this.myAbstractImportUtility.getData();
			long endTime = System.currentTimeMillis();
			long durationSec = (endTime - startTime)/1000;
			
			
			
			// Update view at the Application Thread...
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					Support.showMessageAlert("Operation complete in " + durationSec + " s!");
				}
			});

		} catch (Exception e) {

			// Update view at the Application Thread...
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					Support.getAlert(AlertType.ERROR, "Error", e.getMessage()).show();
				}
			});

		} finally {

			// Update view at the Application Thread...
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					myLoadingLoader.close();
				}
			});
		}
	}
}
