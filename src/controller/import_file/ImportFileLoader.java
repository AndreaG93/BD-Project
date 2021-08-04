package controller.import_file;

import controller.AbstractLoader;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ImportFileLoader extends AbstractLoader {

	// Controller class
	private ImportFileController controller;

	public void start() throws Exception {

		this.myStage = new Stage();
		this.FXML_Loader = new FXMLLoader(getClass().getResource("GUI_Import_file.fxml"));
	
		this.root = FXML_Loader.load();
		this.controller = FXML_Loader.getController();
		this.controller.initialization(myStage);

		showView("Import data", StageStyle.DECORATED, false, true);
	}
}
