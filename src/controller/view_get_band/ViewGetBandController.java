package controller.view_get_band;

import controller.AbstractControllerObserver;
import controller.register_band.RegisterBandLoader;
import controller.register_instrument.RegisterInstrumentController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Support;
import model.entity.band.Band;
import model.managers.BandManager;

public class ViewGetBandController implements AbstractControllerObserver {

	private Stage myStage;
	private RegisterInstrumentController myRegisterInstrumentController;
	private BandManager myBandManager = new BandManager();

	@FXML
	private Button btn_cancel;

	@FXML
	private ListView<Band> lstv_band;

	@FXML
	private BorderPane root;

	@FXML
	private Button btn_createBand;

	@FXML
	private Button btn_finish;

	@FXML
	void createBandEventHandler(ActionEvent event) {
		try {
			RegisterBandLoader x = new RegisterBandLoader();
			x.start(this);
		} catch (Exception e) {
			Support.getAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
		}
	}

	@FXML
	void cancelEventHandler(ActionEvent event) {
		Support.showExitAlert(this.myStage);
	}

	@FXML
	void finishEventHandler(ActionEvent event) {

		try {
			if (lstv_band.getSelectionModel().getSelectedItem() != null) {
				this.myRegisterInstrumentController.addBandToVector(lstv_band.getSelectionModel().getSelectedItem());
				this.myStage.close();
			}
		} catch (Exception e) {
			Support.getAlert(AlertType.ERROR, "Error", e.getMessage()).show();
		}
		
	}

	public void initialization(Stage arg0, RegisterInstrumentController arg1) {
		this.myStage = arg0;
		this.myRegisterInstrumentController = arg1;
		updateView();
	}

	@Override
	public void updateView() {

		ObservableList<Band> myObservableList = FXCollections.observableArrayList();

		// Populate listView...
		for (Band obj : this.myBandManager.getAllBand())
			myObservableList.add(obj);

		lstv_band.setItems(myObservableList);

	}
}