package controller.register_account;

import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.managers.AccountManager;
import controller.AbstractLoader;

public class RegisterAccountLoader extends AbstractLoader {

	// Controller class
	private RegisterAccountController controller;

	public void start(AccountManager arg1) throws Exception {
		
		this.myStage = new Stage();
		this.FXML_Loader = new FXMLLoader(getClass().getResource("GUI_RegisterAccount.fxml"));
		
		this.root = FXML_Loader.load();
		this.controller = FXML_Loader.getController();
		this.controller.initialization(myStage, arg1);

		showView("Register new account", StageStyle.DECORATED, true, true);
	}
}
