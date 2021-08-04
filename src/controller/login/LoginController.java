package controller.login;

import controller.home.HomeLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Support;
import model.managers.AccountManager;

public class LoginController {

	private AccountManager myAccountManager;
	private Stage myStage;

	@FXML
	private TextField txtfld_username;

	@FXML
	private Button btn_submit;

	@FXML
	private BorderPane root;

	@FXML
	private PasswordField txtfld_password;

	@FXML
	void loginEventHandler(ActionEvent event) {

		try {
			myAccountManager.login(txtfld_username.getText(), txtfld_password.getText());
		} catch (Exception e) {
			Support.getAlert(AlertType.ERROR, "Error", e.getMessage()).show();
		}
		finally {
			if (myAccountManager.getCurrentLoggedAccount() != null)
			{
				
				try {
					HomeLoader x = new HomeLoader();
					x.start(this.myAccountManager);
					this.myStage.close();	
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}	
		}
	}

	/**
	 * This method is used to initialize the boundary.
	 * 
	 * @param arg0
	 *            - Represents a {@code Stage} object.
	 */
	public void initialization(Stage arg0) {
		this.myStage = arg0;
		this.myAccountManager = new AccountManager();
	}
}
