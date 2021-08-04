package controller.register_instrument;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import java.util.Vector;
import controller.AbstractControllerObserver;
import controller.view_get_band.ViewGetBandLoader;
import javafx.stage.Stage;
import model.Support;
import model.entity.band.Band;
import model.entity.map.Map;
import model.managers.InstrumentManager;
import model.managers.MapManager;

public class RegisterInstrumentController {

	private Stage myStage;
	private InstrumentManager myInstrumentManager = new InstrumentManager();
	private AbstractControllerObserver myAbstractControllerObserver;
	private Vector<Band> myBandVector = new Vector<>();
	private MapManager myMapManager = new MapManager();

	@FXML
	private TextField txtfld_name;

	@FXML
	private ListView<Band> lstv_band;

	@FXML
	private VBox vbx_page_1;

	@FXML
	private ComboBox<String> cmb_map;

	public void initialization(Stage arg0, AbstractControllerObserver arg1) {
		this.myStage = arg0;
		this.myAbstractControllerObserver = arg1;

		// ComboBoxes Initialization...
		ObservableList<String> myObservableMapList = FXCollections.observableArrayList();
		for (Map obj : this.myMapManager.getAllMap())
			myObservableMapList.add(obj.getName());

		this.cmb_map.setItems(myObservableMapList);
	}

	@FXML
	void closeEventHandler(ActionEvent event) {
		Support.showExitAlert(this.myStage);
	}

	@FXML
	void finishEventHandler(ActionEvent event) {
		try {
			this.myInstrumentManager.registerNewInstrument(txtfld_name.getText(), myBandVector, cmb_map.getValue());
			this.myAbstractControllerObserver.updateView();
			this.myStage.close();
		} catch (Exception e) {
			Support.getAlert(AlertType.ERROR, "Error", e.getMessage()).show();
		}
	}

	@FXML
	void addBandEventHandler(ActionEvent event) {
		try {
			ViewGetBandLoader x = new ViewGetBandLoader();
			x.start(this);
		} catch (Exception e) {
			Support.getAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
		}
	}

	/**
	 * This method is used to update view.
	 */
	public void updateView() {

		// Populate listView...
		ObservableList<Band> myObservableList_Band = FXCollections.observableArrayList();

		for (Band obj : this.myBandVector)
			myObservableList_Band.add(obj);

		lstv_band.setItems(myObservableList_Band);
	}

	/**
	 * This method is used to add a new {@code Band} object to list.
	 * 
	 * @param arg0
	 *            - Represents a {@code Band} object.
	 * @throws Exception 
	 */
	public void addBandToVector(Band arg0) throws Exception {
		
		for(Band obj : this.myBandVector)
			if(obj.getID() == arg0.getID())
				throw new Exception("Specified band already inserted.");
		
		
		this.myBandVector.add(arg0);
		updateView();
	}

	/**
	 * This method is used to delete a selected {@code Band} object.
	 * 
	 * @param event
	 *            - Represents a {@code ActionEvent} object.
	 */
	@FXML
	void removeBandEventHandler(ActionEvent event) {
		if (lstv_band.getSelectionModel().getSelectedItem() != null) {
			this.myBandVector.remove(lstv_band.getSelectionModel().getSelectedItem());
			updateView();
		}
	}

}
