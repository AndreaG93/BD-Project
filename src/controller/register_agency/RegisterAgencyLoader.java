package controller.register_agency;

import controller.AbstractControllerObserver;
import controller.AbstractLoader;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class RegisterAgencyLoader extends AbstractLoader {

	// Controller class
	private RegisterAgencyController controller;

	public void start(AbstractControllerObserver arg0) throws Exception {

		this.myStage = new Stage();
		this.FXML_Loader = new FXMLLoader(getClass().getResource("GUI_RegisterAgency.fxml"));

		this.root = FXML_Loader.load();
		this.controller = FXML_Loader.getController();
		this.controller.initialization(myStage, arg0);

		showView("Register new agency", StageStyle.DECORATED, true, true);
	}
}
