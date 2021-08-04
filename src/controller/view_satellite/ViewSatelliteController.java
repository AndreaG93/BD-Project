package controller.view_satellite;

import java.time.LocalDate;
import java.util.Vector;
import controller.AbstractControllerObserver;
import controller.register_satellite.RegisterSatelliteLoader;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Support;
import model.entity.account.Account;
import model.entity.account.type.Administrator;
import model.entity.agency.Agency;
import model.entity.instrument.Instrument;
import model.entity.satellite.Satellite;
import model.managers.AccountManager;
import model.managers.SatelliteManager;

public class ViewSatelliteController implements AbstractControllerObserver {

	@FXML
	private BorderPane root;

	@FXML
	private Button btn_remove;

	@FXML
	private Pagination pgnt_satellite;

	@FXML
	private Button btn_registerNewSatellite;

	private static int ROWS_PER_PAGE = 50;
	private Stage myStage;
	private TableView<Satellite> myTableView;
	private SatelliteManager mySatelliteManager;
	private AccountManager myAccountManager;

	/**
	 * 
	 * @param arg0
	 * @param arg1
	 */
	public void initialization(Stage arg0, AccountManager arg1) {
		this.myStage = arg0;
		this.myAccountManager = arg1;

		// Loading...
		this.mySatelliteManager = new SatelliteManager();

		// Get logged account...
		Account myAccount = this.myAccountManager.getCurrentLoggedAccount();

		// Disable administrative tools if logged Account isn't
		// 'Administrator'...
		if (Administrator.class.isInstance(myAccount.getType())) {
			this.btn_registerNewSatellite.setDisable(false);
		}

		initializationTable();
		updateView();
	}

	/**
	 * This method is used to update view.
	 */
	@Override
	public void updateView() {

		Vector<Satellite> mySatellites = this.mySatelliteManager.getAllSatellites();

		// Set number of pages...
		this.pgnt_satellite.setPageCount((mySatellites.size() / ROWS_PER_PAGE + 1));

		// Sets the page factory callback function...
		this.pgnt_satellite.setPageFactory(new Callback<Integer, Node>() {

			@Override
			public Node call(Integer pageIndex) {

				int fromIndex = pageIndex * ROWS_PER_PAGE;
				int toIndex = Math.min(fromIndex + ROWS_PER_PAGE, mySatellites.size());
				myTableView.setItems(FXCollections.observableArrayList(mySatellites.subList(fromIndex, toIndex)));

				return myTableView;
			}
		});
	}

	@SuppressWarnings("unchecked")
	private void initializationTable() {

		// Create TableView...
		this.myTableView = new TableView<Satellite>();

		// *** ID Column ****
		TableColumn<Satellite, Integer> column_ID = new TableColumn<Satellite, Integer>("ID");
		column_ID.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Satellite, Integer>, ObservableValue<Integer>>() {

					@Override
					public ObservableValue<Integer> call(CellDataFeatures<Satellite, Integer> param) {
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
		TableColumn<Satellite, String> column_Map = new TableColumn<Satellite, String>("Satellite's name");
		column_Map.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Satellite, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Satellite, String> param) {
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

		// *** FIRTS MISSION Column ****
		TableColumn<Satellite, String> column_FIRTS_MISSION = new TableColumn<Satellite, String>("First mission date");
		column_FIRTS_MISSION.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Satellite, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Satellite, String> param) {
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
								return param.getValue().getFirstMissionDate().toString();
							}

							@Override
							public void addListener(ChangeListener<? super String> listener) {
								// TODO Auto-generated method stub

							}
						};
					}
				});

		// *** LAST MISSION Column ****
		TableColumn<Satellite, String> column_LAST_MISSION = new TableColumn<Satellite, String>("Last mission date");
		column_LAST_MISSION.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Satellite, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Satellite, String> param) {
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

								LocalDate var0 = param.getValue().getLastMissionDate();

								if (var0 == null)
									return "";
								else
									return var0.toString();
							}

							@Override
							public void addListener(ChangeListener<? super String> listener) {
								// TODO Auto-generated method stub

							}
						};
					}
				});

		// *** AGENCY Column ****
		TableColumn<Satellite, String> column_AGENCY = new TableColumn<Satellite, String>("Agency");
		column_AGENCY.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Satellite, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Satellite, String> param) {
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

								String var0 = "";

								for (Agency obj : param.getValue().getAgencies())
									var0 += obj.getName() + "\n";

								return var0;
							}

							@Override
							public void addListener(ChangeListener<? super String> listener) {
								// TODO Auto-generated method stub

							}
						};
					}
				});

		// *** Instrument name Column ****
		TableColumn<Satellite, String> column_InstrumentName = new TableColumn<Satellite, String>("Instrument's name");
		column_InstrumentName.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Satellite, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Satellite, String> param) {
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

								String var0 = "";

								for (Instrument obj : param.getValue().getInstruments())
									var0 += obj.getName() + "\n";

								return var0;
							}

							@Override
							public void addListener(ChangeListener<? super String> listener) {
								// TODO Auto-generated method stub

							}
						};
					}
				});

		// Add columns to table...
		this.myTableView.getColumns().addAll(column_ID, column_Map, column_FIRTS_MISSION, column_LAST_MISSION,
				column_AGENCY, column_InstrumentName);

	}

	/******************************************************************************************************************
	 * FXML method
	 *****************************************************************************************************************/

	/**
	 * This method is used to remove a selected {@code Instrument} object from
	 * database.
	 * 
	 * @param event
	 *            - Represents a {@code ActionEvent} object.
	 */
	@FXML
	void removeEventHandler(ActionEvent event) {
		Satellite var0 = this.myTableView.getSelectionModel().getSelectedItem();

		try {
			if (var0 != null) {
				if (Support.showConfirmationAlert("You want remove selected satellite?")) {
					this.mySatelliteManager.removeSatellite(var0);
					updateView();
				}
			} else
				throw new Exception("Select a satellite to remove it.");

		} catch (Exception e) {
			Support.getAlert(AlertType.ERROR, "Error", e.getMessage()).show();
		}
	}

	/**
	 * This method is used to close current {@code Stage} object.
	 * 
	 * @param event
	 *            - Represents a {@code ActionEvent} object.
	 */
	@FXML
	void exitEventHandler(ActionEvent event) {
		Support.showExitAlert(this.myStage);
	}

	/**
	 * This method is used to register a new {@code Satellite} object into
	 * database.
	 * 
	 * @param event
	 *            - Represents a {@code ActionEvent} object.
	 */
	@FXML
	void registerNewSatelliteEventHandler(ActionEvent event) {
		try {
			RegisterSatelliteLoader x = new RegisterSatelliteLoader();
			x.start(this, this.myAccountManager);
		} catch (Exception e) {
			Support.getAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
		}
	}

}
