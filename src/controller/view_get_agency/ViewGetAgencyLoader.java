package controller.view_get_agency;

import controller.AbstractLoader;
import controller.register_satellite.RegisterSatelliteController;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ViewGetAgencyLoader extends AbstractLoader {

	// Controller class
	private ViewGetAgencyController controller;

	public void start(RegisterSatelliteController arg0) throws Exception {

		this.myStage = new Stage();
		this.FXML_Loader = new FXMLLoader(getClass().getResource("GUI_ViewGetAgency.fxml"));

		this.root = FXML_Loader.load();
		this.controller = FXML_Loader.getController();
		this.controller.initialization(myStage, arg0);

		showView("Agency finder", StageStyle.DECORATED, true, true);
	}
}
