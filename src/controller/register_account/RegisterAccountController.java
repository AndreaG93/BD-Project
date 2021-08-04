package controller.register_account;

import java.util.Vector;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import model.Support;
import model.entity.account.type.Administrator;
import model.entity.account.type.Standard;
import model.managers.AccountManager;

public class RegisterAccountController {

	@FXML
	private TextField txtfld_name;

	@FXML
	private TextField txtfld_email;

	@FXML
	private TextField txtfld_username;

	@FXML
	private PasswordField txtfld_password;

	@FXML
	private Button btn_back;

	@FXML
	private Button btn_finish;

	@FXML
	private PasswordField txtfld_password_2;

	@FXML
	private Button btn_cancel;

	@FXML
	private TextField txtfld_surname;

	@FXML
	private ImageView imgv_panel;

	@FXML
	private VBox vbx_page_3;

	@FXML
	private VBox vbx_page_2;

	@FXML
	private VBox vbx_page_1;

	@FXML
	private ComboBox<String> cmbx_accountType;

	@FXML
	private Button btn_next;

	@FXML
	private ProgressBar prgsbr_passwordRobustness;

	@FXML
	private Label lbl_passwordRobustness;

    @FXML
    private Label summary_type;
    
    @FXML
    private Label summary_surname;

    @FXML
    private Label summary_email;
    
    @FXML
    private Label summary_name;
    
    @FXML
    private Label summary_username;
    
    @FXML
    private Label summary_password;
    
    

	private AccountManager myAccountManager;
	private ObservableList<String> typeAccount = FXCollections.observableArrayList();
	private Vector<VBox> vectorPages = new Vector<>();
	private Stage stage;
	private int page = 1;

	// These variables are used to evaluate password...
	private String passwordValuation = "";
	private double passwordRobustness = 0;
	private ColorAdjust colorAdjust;
	private String labelValuetionColor = "";
	private Runnable myRunnable;
	

	/**
	 * This method is used to initialize current view.
	 * 
	 * @param arg0
	 *            - Represents a {@code Stage} object.
	 * @param arg1
	 *            - Represents an {@code AccountManager} object.
	 */
	public void initialization(Stage arg0, AccountManager arg1) {
		this.stage = arg0;
		this.myAccountManager = arg1;

		// Create Runnable to animate ProgressBar...
		this.colorAdjust = new ColorAdjust();
		this.myRunnable = new Runnable() {

			@Override
			public void run() {

				boolean foundDigit = false;
				boolean foundUpperCase = false;
				boolean foundLowerCase = false;
				boolean specialSymbol = false;

				for (char elem : txtfld_password.getText().toCharArray()) {
					if (Character.isLowerCase(elem))
						foundLowerCase = true;
					else if (Character.isUpperCase(elem))
						foundUpperCase = true;
					else if (Character.isDigit(elem))
						foundDigit = true;
					else
						specialSymbol = true;
				}

				if (txtfld_password.getText().length() < 6 || !(foundDigit && foundUpperCase && foundLowerCase)){
					passwordValuation = "Specified password is invalid.";
					labelValuetionColor = "#FF0000";
					passwordRobustness = 0;
				}		
				else {

					// Compute password robustness (stupid algorithm...)
					passwordRobustness = 0;
					passwordRobustness += (((double) txtfld_password.getText().length() * 2) / 100);

					if (specialSymbol)
						passwordRobustness += 0.3;

					if (passwordRobustness <= 0.3) {
						passwordValuation = "Weak";
						colorAdjust.setHue(0.9);
						labelValuetionColor = "#FF0000";
					} else if (passwordRobustness > 0.3 && passwordRobustness < 0.6) {
						passwordValuation = "Good";
						colorAdjust.setHue(-0.8);
						labelValuetionColor = "#FFD700";
						
						
					} else {
						passwordValuation = "Excellent";
						colorAdjust.setHue(-0.4);
						labelValuetionColor = "#32CD32";
					}
				}

				Platform.runLater(new Runnable() {
					@Override
					public void run() {

						prgsbr_passwordRobustness.setProgress(passwordRobustness);
						prgsbr_passwordRobustness.setEffect(colorAdjust);
						
						lbl_passwordRobustness.setText(passwordValuation);
						lbl_passwordRobustness.setTextFill(Paint.valueOf(labelValuetionColor));
					}
				});

			}
		};

		// Set 'OnKeyPressed' event...
		this.txtfld_password.setOnKeyPressed(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {

				new Thread(myRunnable).start();
			}
		});

		// Pages initialization...
		this.vectorPages.add(vbx_page_1);
		this.vectorPages.add(vbx_page_2);
		this.vectorPages.add(vbx_page_3);

		// Image Panel initialization...
		Image image = new Image(getClass().getResourceAsStream("/controller/panel.png"));
		this.imgv_panel.setImage(image);
		this.imgv_panel.fitHeightProperty().bind(this.stage.heightProperty());

		// ComboBoxes initialization...
		typeAccount.add(new Standard().toString());
		typeAccount.add(new Administrator().toString());
		cmbx_accountType.setItems(typeAccount);

		updateView();
	}

	/**
	 * This method is used to register an {@code Account} object into database.
	 * 
	 * @param event
	 *            - Represents an {@code ActionEvent} object.
	 */
	@FXML
	private void finishEventHandler(ActionEvent event) {

		try {
			this.myAccountManager.registerNewAccount(cmbx_accountType.getValue(), txtfld_username.getText(),
					txtfld_password.getText(), txtfld_password_2.getText(), txtfld_name.getText(),
					txtfld_surname.getText(), txtfld_email.getText());
			
			Support.showMessageAlert("Registration complete!");
			this.stage.close();
			
		} catch (Exception e) {
			Support.getAlert(AlertType.ERROR, "Error", e.getMessage()).show();
		}
	}

	/**
	 * This method is used to close current view.
	 * 
	 * @param arg0
	 *            - Represents an {@code ActionEvent} object.
	 */
	@FXML
	public void closeEventHandler(ActionEvent arg0) {
		Support.showExitAlert(this.stage);
	}

	@FXML
	void backEventHandler(ActionEvent event) {
		this.page--;
		updateView();
	}

	@FXML
	void nextEventHandler(ActionEvent event) {
		try {
			switch (this.page) {
			case 1: {

				myAccountManager.userNameRegistrationCheck(txtfld_username.getText());
				myAccountManager.passwordRegistrationCheck(txtfld_password.getText(), txtfld_password_2.getText());
				myAccountManager.typeAccountRegistrationCheck(cmbx_accountType.getValue());

				break;
			}
			case 2: {

				myAccountManager.nameRegistrationCheck(txtfld_name.getText());
				myAccountManager.surnameRegistrationCheck(txtfld_surname.getText());
				myAccountManager.emailRegistrationCheck(txtfld_email.getText());

				break;
			}

			}
		} catch (Exception e) {
			Support.getAlert(AlertType.ERROR, "Error", e.getMessage()).show();
			return;
		}

		this.page++;
		updateView();
	}

	/**
	 * This method is used to update current view.
	 */
	public void updateView() {
		for (VBox x : this.vectorPages)
			x.setVisible(false);

		btn_finish.setDisable(true);

		if (this.page == 1) {
			vbx_page_1.setVisible(true);
			btn_back.setDisable(true);
			btn_next.setDisable(false);

		}
		if (this.page == 2) {
			vbx_page_2.setVisible(true);
			btn_back.setDisable(false);
			btn_next.setDisable(false);

		}
		if (this.page == 3) {
			vbx_page_3.setVisible(true);
			btn_back.setDisable(false);
			btn_next.setDisable(true);
			btn_finish.setDisable(false);

			summary_name.setText(this.txtfld_name.getText());
			summary_surname.setText(this.txtfld_surname.getText());
			summary_email.setText(this.txtfld_email.getText());
			summary_type.setText(this.cmbx_accountType.getValue());
			summary_password.setText(this.txtfld_password.getText());
			summary_username.setText(this.txtfld_username.getText());
		}
	}
}