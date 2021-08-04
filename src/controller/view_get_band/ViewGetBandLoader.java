package controller.view_get_band;

import controller.AbstractLoader;
import controller.register_instrument.RegisterInstrumentController;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ViewGetBandLoader extends AbstractLoader {

	// Controller class
	private ViewGetBandController controller;

	public void start(RegisterInstrumentController arg0) throws Exception {

		this.myStage = new Stage();
		this.FXML_Loader = new FXMLLoader(getClass().getResource("GUI_ViewGetBand.fxml"));

		this.root = FXML_Loader.load();
		this.controller = FXML_Loader.getController();
		this.controller.initialization(myStage, arg0);

		showView("Band finder", StageStyle.DECORATED, true, true);
	}

}
