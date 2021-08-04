package controller.view_get_agency;

import java.util.Vector;

import controller.AbstractControllerObserver;
import controller.register_agency.RegisterAgencyLoader;
import controller.register_satellite.RegisterSatelliteController;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Support;
import model.entity.agency.Agency;
import model.managers.AgencyManager;

public class ViewGetAgencyController implements AbstractControllerObserver {

	@FXML
	private BorderPane root;

	@FXML
	private Pagination pgnt_agency;

	private static int ROWS_PER_PAGE = 50;
	private Stage myStage;
	private RegisterSatelliteController myRegisterSatelliteController;
	private AgencyManager myAgencyManager = new AgencyManager();
	private TableView<Agency> myTableView;
	private Vector<Agency> myInstruments;

	public void initialization(Stage arg0, RegisterSatelliteController arg1) {
		this.myStage = arg0;
		this.myRegisterSatelliteController = arg1;

		initializationTable();
		updateView();
	}

	/**
	 * This method is used to update view.
	 */
	@Override
	public void updateView() {

		myInstruments = new Vector<>();
		
		try {
			myInstruments = this.myAgencyManager.getAllAgencyVector();
		} catch (Exception e) {
			Support.getAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
		}

		// Set number of pages...
		this.pgnt_agency.setPageCount((myInstruments.size() / ROWS_PER_PAGE + 1));

		// Sets the page factory callback function...
		this.pgnt_agency.setPageFactory(new Callback<Integer, Node>() {

			@Override
			public Node call(Integer pageIndex) {

				int fromIndex = pageIndex * ROWS_PER_PAGE;
				int toIndex = Math.min(fromIndex + ROWS_PER_PAGE, myInstruments.size());
				myTableView.setItems(FXCollections.observableArrayList(myInstruments.subList(fromIndex, toIndex)));

				return myTableView;
			}
		});
	}

	@SuppressWarnings("unchecked")
	private void initializationTable() {

		// Create TableView...
		this.myTableView = new TableView<Agency>();

		// *** ID Column ****
		TableColumn<Agency, Integer> column_ID = new TableColumn<Agency, Integer>("ID");
		column_ID.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Agency, Integer>, ObservableValue<Integer>>() {

					@Override
					public ObservableValue<Integer> call(CellDataFeatures<Agency, Integer> param) {
						// TODO Auto-generated method stub
						return new ObservableValue<Integer>() {

							@Override
							public void removeListener(InvalidationListener arg0) {
								// TODO Auto-generated method stub

							}

							@Override
							public void addListener(InvalidationListener arg0) {
								// TODO Auto-generated method stub

							}

							@Override
							public void removeListener(ChangeListener<? super Integer> listener) {
								// TODO Auto-generated method stub

							}

							@Override
							public Integer getValue() {
								return param.getValue().getID();
							}

							@Override
							public void addListener(ChangeListener<? super Integer> listener) {
								// TODO Auto-generated method stub

							}
						};
					}
				});

		// *** Name Column ****
		TableColumn<Agency, String> column_Map = new TableColumn<Agency, String>("Agency's name");
		column_Map.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Agency, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Agency, String> param) {
						return new ObservableValue<String>() {

							@Override
							public void removeListener(InvalidationListener listener) {
								// TODO Auto-generated method stub

							}

							@Override
							public void addListener(InvalidationListener listener) {
								// TODO Auto-generated method stub

							}

							@Override
							public void removeListener(ChangeListener<? super String> listener) {
								// TODO Auto-generated method stub

							}

							@Override
							public String getValue() {
								return param.getValue().getName();
							}

							@Override
							public void addListener(ChangeListener<? super String> listener) {
								// TODO Auto-generated method stub

							}
						};
					}
				});

		// Add columns to table...
		this.myTableView.getColumns().addAll(column_ID, column_Map);
	}

	/******************************************************************************************************************
	 * FXML method
	 *****************************************************************************************************************/

	@FXML
	void createAgencyEventHandler(ActionEvent event) {

		try {
			RegisterAgencyLoader x = new RegisterAgencyLoader();
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

		Agency var0 = this.myTableView.getSelectionModel().getSelectedItem();

		if (var0 != null) {
			try {
				this.myRegisterSatelliteController.addAgencyToVector(var0);
			} catch (Exception e) {
				Support.getAlert(AlertType.ERROR, "Error", e.getMessage()).show();
				return;
			}
			this.myStage.close();
		}
	}

}
