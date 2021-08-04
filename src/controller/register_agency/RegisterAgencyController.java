package controller.register_agency;

import controller.AbstractControllerObserver;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Support;
import model.managers.AgencyManager;

public class RegisterAgencyController {

	@FXML
	private TextField txtfld_name;

	@FXML
	private Button btn_cancel;

	@FXML
	private BorderPane root;

	@FXML
	private Button btn_finish;

	private Stage myStage;
	private AgencyManager myAgencyManager;
	private AbstractControllerObserver myAbstractControllerObserver ;

	@FXML
	void cancelEventHandler(ActionEvent event) {
		Support.showExitAlert(this.myStage);
	}

	@FXML
	void finishEventHandler(ActionEvent event) {
		try {
			this.myAgencyManager.registerNewAgency(txtfld_name.getText());
			this.myAbstractControllerObserver.updateView();
			this.myStage.close();
		} catch (Exception e) {
			Support.getAlert(AlertType.ERROR, "Error", e.getMessage()).show();
		}
	}
	
	/**
	 * This method is used to initialize current view.
	 * 
	 * @param arg0
	 *            - Represents a {@code Stage} object.
	 */
	public void initialization(Stage arg0, AbstractControllerObserver arg1) {
		this.myStage = arg0;
		this.myAgencyManager = new AgencyManager();
		this.myAbstractControllerObserver = arg1;
	}
}
