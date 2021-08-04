package controller.register_map;

import controller.AbstractControllerObserver;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Support;
import model.managers.MapManager;

public class RegisterMapController {

	@FXML
    private TextField txtfld_name;

    @FXML
    private BorderPane root;

    private Stage myStage;
	private MapManager myMapManager;
	private AbstractControllerObserver myAbstractControllerObserver;

	@FXML
	void cancelEventHandler(ActionEvent event) {
		Support.showExitAlert(this.myStage);
	}

	@FXML
	void finishEventHandler(ActionEvent event) {
		try {
			this.myMapManager.registerNewMap(txtfld_name.getText());
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
		this.myMapManager = new MapManager();
		this.myAbstractControllerObserver = arg1;
	}
}
