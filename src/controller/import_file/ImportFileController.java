package controller.import_file;

import controller.loading.LoadingLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Support;
import model.utility_import.ImportHigal;
import model.utility_import.ImportHigalAdditionalInfo;
import model.utility_import.ImportSpitzerIRAC;
import model.utility_import.ImportSpitzerMIPS;

public class ImportFileController {

	@FXML
	private Button btn_submit;

	@FXML
	private BorderPane root;

	private Stage myStage;

	/**
	 * This method is used to initialize current view.
	 * 
	 * @param arg0
	 *            - Represents a {@code Stage} object.
	 */
	public void initialization(Stage arg0) {
		this.myStage = arg0;
	}

	@FXML
	void exitEventHandler(ActionEvent event) {
		Support.showExitAlert(myStage);
	}

	@FXML
	void higalEventHandler(ActionEvent event) {

		// Initialize importing thread...
		new ImportThread(new ImportHigal(), new LoadingLoader()).start();
	}

	@FXML
	void higal_additionalinfoEventHandler(ActionEvent event) {

		// Initialize importing thread...
		new ImportThread(new ImportHigalAdditionalInfo(), new LoadingLoader()).start();

	}

	@FXML
	void ro8EventHandler(ActionEvent event) {

		// Initialize importing thread...
		new ImportThread(new ImportSpitzerIRAC(), new LoadingLoader()).start();

	}

	@FXML
	void mipsEventHandler(ActionEvent event) {

		// Initialize importing thread...
		new ImportThread(new ImportSpitzerMIPS(), new LoadingLoader()).start();

	}
}
