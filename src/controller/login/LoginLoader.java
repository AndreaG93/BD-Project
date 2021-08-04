package controller.login;

import controller.AbstractLoader;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LoginLoader extends AbstractLoader {

	// Controller class
	private LoginController controller;

	public void start() throws Exception {

		this.myStage = new Stage();
		this.FXML_Loader = new FXMLLoader(getClass().getResource("GUI_Login.fxml"));

		this.root = FXML_Loader.load();
		this.controller = FXML_Loader.getController();
		this.controller.initialization(myStage);

		showView("Login", StageStyle.DECORATED, false, true);
	}

}
