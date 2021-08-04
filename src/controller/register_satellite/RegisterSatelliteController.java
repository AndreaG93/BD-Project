package controller.register_satellite;

import java.awt.Button;
import java.util.Vector;

import controller.AbstractControllerObserver;
import controller.view_get_agency.ViewGetAgencyLoader;
import controller.view_get_instrument.ViewGetInstrumentLoader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Support;
import model.entity.agency.Agency;
import model.entity.instrument.Instrument;
import model.managers.AccountManager;
import model.managers.SatelliteManager;

public class RegisterSatelliteController {

	private Stage myStage;
	private Vector<Instrument> myInstrumentVector = new Vector<>();
	private Vector<Agency> myAgencyVector = new Vector<>();
	private SatelliteManager mySatelliteManager = new SatelliteManager();
	private AbstractControllerObserver myAbstractControllerObserver;
	private AccountManager myAccountManager;

	@FXML
	private TextField txtfld_name;

	@FXML
	private DatePicker dtpkr_firstMissionDate;

	@FXML
	private DatePicker dtpkr_lastMissionDate;

	@FXML
	private VBox vbx_page_1;

	@FXML
	private ListView<Instrument> lstv_instruments;

	@FXML
	private ListView<Agency> lstv_agency;

	@FXML
	private Button btn_cancel;

	@FXML
	private ImageView imgv_sidePanel;

	@FXML
	void cancelEventHandler(ActionEvent event) {
		Support.showExitAlert(this.myStage);
	}

	public void initialization(Stage arg0, AbstractControllerObserver arg1, AccountManager arg2) {
		this.myStage = arg0;
		this.myAbstractControllerObserver = arg1;
		this.myAccountManager = arg2;
		
		// Image Panel initialization...
		Image image = new Image(getClass().getResourceAsStream("/controller/panel.png"));
		this.imgv_sidePanel.setImage(image);
		this.imgv_sidePanel.fitHeightProperty().bind(this.myStage.heightProperty());
	}

	/**
	 * This method is used to complete registration.
	 * 
	 * @param event
	 */
	@FXML
	void finishEventHandler(ActionEvent event) {

		try {
			this.mySatelliteManager.registerNewSatellite(txtfld_name.getText(), dtpkr_firstMissionDate.getValue(),
					dtpkr_lastMissionDate.getValue(), this.myAgencyVector, this.myInstrumentVector);
			this.myAbstractControllerObserver.updateView();
			Support.showMessageAlert("Registration complete!");
			this.myStage.close();
		} catch (Exception e) {
			Support.getAlert(AlertType.ERROR, "Error", e.getMessage()).show();
		}
	}
	
	@FXML
	void insertInstrumentEventHandler(ActionEvent event) {
		try {
			ViewGetInstrumentLoader x = new ViewGetInstrumentLoader();
			x.start(this, this.myAccountManager);
		} catch (Exception e) {
			Support.getAlert(AlertType.ERROR, "Error", e.getMessage()).show();
		}

	}

	/**
	 * This method is used to open "View and Get Agency" wizard.
	 * 
	 * @param event
	 *            - Represents a {@code ActionEvent} object.
	 */
	@FXML
	void insertAgencyEventHandler(ActionEvent event) {

		try {
			ViewGetAgencyLoader x = new ViewGetAgencyLoader();
			x.start(this);
		} catch (Exception e) {
			Support.getAlert(AlertType.ERROR, "Error", e.getMessage()).show();
		}

	}

	/**
	 * This method is used to update view.
	 */
	public void updateView() {

		// Populate listView...
		ObservableList<Agency> myObservableList_Agency = FXCollections.observableArrayList();
		ObservableList<Instrument> myObservableList_Instrument = FXCollections.observableArrayList();

		for (Agency obj : this.myAgencyVector)
			myObservableList_Agency.add(obj);

		for (Instrument obj : this.myInstrumentVector)
			myObservableList_Instrument.add(obj);

		lstv_agency.setItems(myObservableList_Agency);
		lstv_instruments.setItems(myObservableList_Instrument);
	}

	/**
	 * This method is used to add a new {@code Agency} object to list.
	 * 
	 * @param arg0
	 *            - Represents a {@code Agency} object.
	 * @throws Exception 
	 */
	public void addAgencyToVector(Agency arg0) throws Exception {
		
		for(Agency obj : this.myAgencyVector)
			if(obj.getID() == arg0.getID())
				throw new Exception("Specified agency already inserted.");
			
		this.myAgencyVector.add(arg0);
		updateView();
	}

	/**
	 * This method is used to add a new {@code Instrument} object to list.
	 * 
	 * @param arg0
	 *            - Represents a {@code Instrument} object.
	 * @throws Exception 
	 */
	public void addInstrumentToVector(Instrument arg0) throws Exception {
		
		for(Instrument obj : this.myInstrumentVector)
			if(obj.getID() == arg0.getID())
				throw new Exception("Specified instrument already inserted.");
		
		this.myInstrumentVector.add(arg0);
		updateView();
	}

	/**
	 * This method is used to delete a selected {@code Instrument} object.
	 * 
	 * @param event
	 *            - Represents a {@code ActionEvent} object.
	 */
	@FXML
	void removeInstrumentEventHandler(ActionEvent event) {

		if (lstv_instruments.getSelectionModel().getSelectedItem() != null) {
			this.myInstrumentVector.remove(lstv_instruments.getSelectionModel().getSelectedItem());
			updateView();
		}
	}

	/**
	 * This method is used to delete a selected {@code Agency} object.
	 * 
	 * @param event
	 *            - Represents a {@code ActionEvent} object.
	 */
	@FXML
	void removeAgencyEventHandler(ActionEvent event) {
		if (lstv_agency.getSelectionModel().getSelectedItem() != null) {
			this.myAgencyVector.remove(lstv_agency.getSelectionModel().getSelectedItem());
			updateView();
		}
	}
}
