package controller.home;

import controller.AbstractLoader;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.managers.AccountManager;

public class HomeLoader extends AbstractLoader {

	// Controller class
	private HomeController controller;

	public void start(AccountManager arg1) throws Exception {
		
		this.myStage = new Stage();
		this.FXML_Loader = new FXMLLoader(getClass().getResource("GUI_Home.fxml"));
		
		this.root = FXML_Loader.load();
		this.controller = FXML_Loader.getController();
		this.controller.initialization(myStage, arg1);

		showView("Home", StageStyle.DECORATED, true, true);
	}
}
