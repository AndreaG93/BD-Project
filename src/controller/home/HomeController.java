package controller.home;


import controller.import_file.ImportFileLoader;
import controller.login.LoginLoader;
import controller.register_account.RegisterAccountLoader;
import controller.search_clump.SearchClumpLoader;
import controller.search_source.SearchSourceLoader;
import controller.view_get_instrument.ViewGetInstrumentLoader;
import controller.view_map.ViewMapLoader;
import controller.view_satellite.ViewSatelliteLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.Support;
import model.entity.account.Account;
import model.entity.account.type.Administrator;
import model.managers.AccountManager;

public class HomeController {

	private AccountManager myAccountManager;
	private Stage myStage;

	@FXML
	private Label lbl_privilegeLevel;

	@FXML
	private Label lbl_accountNameSurname;

	@FXML
	private Button btn_registerAccount;

	@FXML
	private BorderPane root;

	@FXML
	private ImageView imgv_panel;
	
	@FXML
	private Button btn_importData;

	/**
	 * This method is used to initialize current view.
	 * 
	 * @param arg0
	 *            - Represents a {@code Stage} object.
	 * @param arg1
	 *            - Represents a {@code AccountManager} object.
	 */
	public void initialization(Stage arg0, AccountManager arg1) {

		this.myStage = arg0;
		this.myAccountManager = arg1;

		// Get logged account...
		Account myAccount = this.myAccountManager.getCurrentLoggedAccount();

		// Disable administrative tools if logged Account isn't
		// 'Administrator'...
		if (Administrator.class.isInstance(myAccount.getType())) {
			this.btn_importData.setDisable(false);
			this.btn_registerAccount.setDisable(false);
		}
		
		/**
		 * Image Panel initialization...
		 */

		Image image = new Image(getClass().getResourceAsStream("/controller/panel.png"));
		this.imgv_panel.setImage(image);
		this.imgv_panel.fitHeightProperty().bind(this.myStage.heightProperty());
		

		// Other initialization...
		this.lbl_accountNameSurname.setText(myAccount.getSurname() + " " + myAccount.getName());
		this.lbl_privilegeLevel.setText(myAccount.getType().toString());
	}

	/******************************************************************************************************************
	 * FXML method
	 *****************************************************************************************************************/

	@FXML
    void ViewMapEventHandler(ActionEvent event) {
		try {
			ViewMapLoader x = new ViewMapLoader();
			x.start(this.myAccountManager);
		} catch (Exception e) {
			Support.getAlert(AlertType.ERROR, "Error", e.getMessage()).show();
		}
    }
	
	
	@FXML
	void ViewSatelliteEventHandler(ActionEvent event) {
		try {
			ViewSatelliteLoader x = new ViewSatelliteLoader();
			x.start(this.myAccountManager);
		} catch (Exception e) {
			Support.getAlert(AlertType.ERROR, "Error", e.getMessage()).show();
		}
	}

	@FXML
	void ViewInstrumentEventHandler(ActionEvent event) {
		try {
			ViewGetInstrumentLoader x = new ViewGetInstrumentLoader();
			x.start(null, this.myAccountManager);
		} catch (Exception e) {
			Support.getAlert(AlertType.ERROR, "Error", e.getMessage()).show();
		}
	}

	@FXML
	void ViewClumpEventHandler(ActionEvent event) {
		try {
			SearchClumpLoader x = new SearchClumpLoader();
			x.start();
		} catch (Exception e) {
			Support.getAlert(AlertType.ERROR, "Error", e.getMessage()).show();
		}
	}

	@FXML
	void ViewSourceEventHandler(ActionEvent event) {

		try {
			SearchSourceLoader x = new SearchSourceLoader();
			x.start();
		} catch (Exception e) {
			Support.getAlert(AlertType.ERROR, "Error", e.getMessage()).show();
		}
	}

	@FXML
	void ImportDataEventHandler(ActionEvent event) {
		
		try {
			ImportFileLoader x = new ImportFileLoader();
			x.start();
		} catch (Exception e) {
			Support.getAlert(AlertType.ERROR, "Error", e.getMessage()).show();
		}
		
		
		

	}

	@FXML
	void RegisterAccountEventHandler(ActionEvent event) {

		try {
			RegisterAccountLoader x = new RegisterAccountLoader();
			x.start(this.myAccountManager);
		} catch (Exception e) {
			Support.getAlert(AlertType.ERROR, "Error", e.getMessage()).show();
		}

	}

	/**
	 * This method is used to do logout.
	 * 
	 * @param event
	 *            - Represents a {@code ActionEvent} object.
	 */
	@FXML
	void LogoutEventHandler(ActionEvent event) {

		try {
			LoginLoader x = new LoginLoader();
			x.start();

			this.myAccountManager.logout();
			this.myStage.close();

		} catch (Exception e) {
			Support.getAlert(AlertType.ERROR, "Error", e.getMessage()).show();
		}

	}
}
