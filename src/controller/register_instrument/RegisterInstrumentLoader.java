package controller.register_instrument;

import controller.AbstractControllerObserver;
import controller.AbstractLoader;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class RegisterInstrumentLoader extends AbstractLoader {

	// Controller class
	private RegisterInstrumentController controller;

	public void start(AbstractControllerObserver arg0) throws Exception {

		this.myStage = new Stage();
		this.FXML_Loader = new FXMLLoader(getClass().getResource("GUI_RegisterInstrument.fxml"));

		this.root = FXML_Loader.load();
		this.controller = FXML_Loader.getController();
		this.controller.initialization(myStage, arg0);

		showView("Register new instrument", StageStyle.DECORATED, true, true);
	}
}