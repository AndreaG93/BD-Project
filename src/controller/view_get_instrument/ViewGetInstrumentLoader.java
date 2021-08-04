package controller.view_get_instrument;

import controller.AbstractLoader;
import controller.register_satellite.RegisterSatelliteController;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.managers.AccountManager;

public class ViewGetInstrumentLoader extends AbstractLoader {

	// Controller class
	private ViewGetInstrumentController controller;

	public void start(RegisterSatelliteController arg0, AccountManager arg1) throws Exception {

		this.myStage = new Stage();
		this.FXML_Loader = new FXMLLoader(getClass().getResource("GUI_ViewGetInstrument.fxml"));

		this.root = FXML_Loader.load();
		this.controller = FXML_Loader.getController();
		this.controller.initialization(myStage, arg0, arg1);

		showView("Instrument finder", StageStyle.DECORATED, true, true);
	}
}
