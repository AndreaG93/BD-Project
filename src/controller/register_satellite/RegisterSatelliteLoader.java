package controller.register_satellite;

import controller.AbstractControllerObserver;
import controller.AbstractLoader;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.managers.AccountManager;

public class RegisterSatelliteLoader extends AbstractLoader {

	// Controller class
	private RegisterSatelliteController controller;

	public void start(AbstractControllerObserver arg0, AccountManager arg1) throws Exception {
		
		this.myStage = new Stage();
		this.FXML_Loader = new FXMLLoader(getClass().getResource("GUI_RegisterSatellite.fxml"));
		
		this.root = FXML_Loader.load();
		this.controller = FXML_Loader.getController();
		this.controller.initialization(myStage, arg0, arg1);

		showView("Register new satellite", StageStyle.DECORATED, true, true);
	}
}