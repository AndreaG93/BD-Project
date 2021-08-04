package controller.register_map;

import controller.AbstractControllerObserver;
import controller.AbstractLoader;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class RegisterMapLoader extends AbstractLoader {

	// Controller class
	private RegisterMapController controller;

	public void start(AbstractControllerObserver arg0) throws Exception {

		this.myStage = new Stage();
		this.FXML_Loader = new FXMLLoader(getClass().getResource("GUI_RegisterMap.fxml"));

		this.root = FXML_Loader.load();
		this.controller = FXML_Loader.getController();
		this.controller.initialization(myStage, arg0);

		showView("Register new map", StageStyle.DECORATED, true, true);
	}
}
