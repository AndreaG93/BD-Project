package controller.register_band;

import controller.AbstractControllerObserver;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Support;
import model.managers.BandManager;

public class RegisterBandController {

	private AbstractControllerObserver myAbstractControllerObserver;
	
	@FXML
	private TextField txtfld_resolution;

	@FXML
	private Button btn_cancel;

	@FXML
	private BorderPane root;

	@FXML
	private Button btn_finish;

	@FXML
	private TextField txtfld_wavelength;

	private Stage myStage;
	private BandManager myBandManager;

	@FXML
	void cancelEventHandler(ActionEvent event) {
		Support.showExitAlert(this.myStage);
	}

	@FXML
	void finishEventHandler(ActionEvent event) {
		try {
			this.myBandManager.registerNewBand(txtfld_resolution.getText(), txtfld_wavelength.getText());
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
	 * @param arg02 
	 */
	public void initialization(Stage arg0, AbstractControllerObserver arg1) {
		this.myStage = arg0;
		this.myBandManager = new BandManager();
		this.myAbstractControllerObserver = arg1;
	}
}
