package controller.view_satellite;

import controller.AbstractLoader;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.managers.AccountManager;

public class ViewSatelliteLoader extends AbstractLoader {

	// Controller class
	private ViewSatelliteController controller;

	public void start(AccountManager arg0) throws Exception {

		this.myStage = new Stage();
		this.FXML_Loader = new FXMLLoader(getClass().getResource("GUI_ViewSatellite.fxml"));

		this.root = FXML_Loader.load();
		this.controller = FXML_Loader.getController();
		this.controller.initialization(myStage, arg0);

		showView("Satellite finder", StageStyle.DECORATED, true, true);
	}
}
