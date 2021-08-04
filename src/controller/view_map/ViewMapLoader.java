package controller.view_map;

import controller.AbstractLoader;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.managers.AccountManager;

public class ViewMapLoader extends AbstractLoader {

	// Controller class
	private ViewMapController controller;

	public void start(AccountManager arg0) throws Exception {

		this.myStage = new Stage();
		this.FXML_Loader = new FXMLLoader(getClass().getResource("GUI_ViewMap.fxml"));

		this.root = FXML_Loader.load();
		this.controller = FXML_Loader.getController();
		this.controller.initialization(myStage, arg0);

		showView("Map finder", StageStyle.DECORATED, true, true);
	}
}
