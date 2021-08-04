package controller.view_get_instrument;

import java.util.Vector;

import controller.AbstractControllerObserver;
import controller.register_instrument.RegisterInstrumentLoader;
import controller.register_satellite.RegisterSatelliteController;
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
import model.entity.band.Band;
import model.entity.instrument.Instrument;
import model.managers.AccountManager;
import model.managers.BandManager;
import model.managers.InstrumentManager;

public class ViewGetInstrumentController implements AbstractControllerObserver {

	@FXML
	private BorderPane root;

	@FXML
	private Pagination pgnt_instrument;

	@FXML
	private Button btn_add;

	@FXML
	private Button btn_remove;

	@FXML
	private Button btn__registerNewInstrument;
	

	private static int ROWS_PER_PAGE = 50;
	private static String SUPPORTED = "Supported";
	private Stage myStage;
	private RegisterSatelliteController myRegisterSatelliteController;
	private InstrumentManager myInstrumentManager;
	private BandManager myBandManager;
	private TableView<Instrument> myTableView;
	private AccountManager myAccountManager;

	/**
	 * 
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 */
	public void initialization(Stage arg0, RegisterSatelliteController arg1, AccountManager arg2) {
		this.myStage = arg0;
		this.myRegisterSatelliteController = arg1;
		this.myAccountManager = arg2;

		// Loading...
		this.myInstrumentManager = new InstrumentManager();
		this.myBandManager = new BandManager();

		// Button initialization...
		if (this.myRegisterSatelliteController == null)
			this.btn_add.visibleProperty().set(false);

		// Get logged account...
		Account myAccount = this.myAccountManager.getCurrentLoggedAccount();

		// Disable administrative tools if logged Account isn't
		// 'Administrator'...
		if (Administrator.class.isInstance(myAccount.getType())) {
			this.btn__registerNewInstrument.setDisable(false);
		}

		initializationTable();
		updateView();
	}

	/**
	 * This method is used to update view.
	 */
	@Override
	public void updateView() {

		Vector<Instrument> myInstruments = this.myInstrumentManager.getAllInstrumentVector();

		// Set number of pages...
		this.pgnt_instrument.setPageCount((myInstruments.size() / ROWS_PER_PAGE + 1));

		// Sets the page factory callback function...
		this.pgnt_instrument.setPageFactory(new Callback<Integer, Node>() {

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
		this.myTableView = new TableView<Instrument>();

		// Create TableColumnVector...
		Vector<TableColumn<Instrument, String>> myTableColumnVector = new Vector<>();

		// *** ID Column ****
		TableColumn<Instrument, Integer> column_ID = new TableColumn<Instrument, Integer>("ID");
		column_ID.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Instrument, Integer>, ObservableValue<Integer>>() {

					@Override
					public ObservableValue<Integer> call(CellDataFeatures<Instrument, Integer> param) {
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
		TableColumn<Instrument, String> column_name = new TableColumn<Instrument, String>("Instrument's name");
		column_name.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Instrument, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Instrument, String> param) {
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

		// *** Name Column ****
		TableColumn<Instrument, String> column_Map = new TableColumn<Instrument, String>("Associated map");
		column_Map.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Instrument, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Instrument, String> param) {
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
								return param.getValue().getMap().getName();
							}

							@Override
							public void addListener(ChangeListener<? super String> listener) {
								// TODO Auto-generated method stub

							}
						};
					}
				});

		// *** Band Column ****
		for (Band myBand : this.myBandManager.getAllBand()) {

			TableColumn<Instrument, String> column = new TableColumn<Instrument, String>(
					"Band at " + myBand.getResolution() + " microns, " + myBand.getWavelength() + " arcsec");
			column.setCellValueFactory(
					new Callback<TableColumn.CellDataFeatures<Instrument, String>, ObservableValue<String>>() {

						public ObservableValue<String> call(CellDataFeatures<Instrument, String> param) {

							return new ObservableValue<String>() {

								@Override
								public void removeListener(InvalidationListener listener) {
								}

								@Override
								public void addListener(InvalidationListener listener) {
								}

								@Override
								public void removeListener(ChangeListener<? super String> listener) {
								}

								@Override
								public String getValue() {

									for (Band obj : param.getValue().getSupportedBands())
										if (obj.getID() == myBand.getID())
											return SUPPORTED;

									return "";
								}

								@Override
								public void addListener(ChangeListener<? super String> listener) {
								}
							};

						}
					});

			myTableColumnVector.add(column);

		}

		// Add columns to table...
		this.myTableView.getColumns().addAll(column_ID, column_name, column_Map);

		for (TableColumn<Instrument, String> obj : myTableColumnVector)
			this.myTableView.getColumns().add(obj);
	}

	/******************************************************************************************************************
	 * FXML method
	 *****************************************************************************************************************/

	/**
	 * This method is used to add a {@code Instrument} object to a
	 * {@code RegisterSatelliteController} object.
	 * 
	 * @param event
	 *            - Represents a {@code ActionEvent} object.
	 */
	@FXML
	void addEventHandler(ActionEvent event) {

		Instrument var0 = this.myTableView.getSelectionModel().getSelectedItem();

		if (var0 != null) {
			try {
				this.myRegisterSatelliteController.addInstrumentToVector(var0);
			} catch (Exception e) {
				Support.getAlert(AlertType.ERROR, "Error", e.getMessage()).show();
				return;
			}
			this.myStage.close();
		}
	}

	/**
	 * This method is used to remove a selected {@code Instrument} object from
	 * database.
	 * 
	 * @param event
	 *            - Represents a {@code ActionEvent} object.
	 */
	@FXML
	void removeEventHandler(ActionEvent event) {
		Instrument var0 = this.myTableView.getSelectionModel().getSelectedItem();

		try {
			if (var0 != null) {
				if (Support.showConfirmationAlert("You want remove selected instrument?")) {
					this.myInstrumentManager.removeInstrument(var0);
					updateView();
				}
			} else
				throw new Exception("Select an instrument to remove it.");

		} catch (

		Exception e) {
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
	 * This method is used to register a new {@code Instrument} object into
	 * database.
	 * 
	 * @param event
	 *            - Represents a {@code ActionEvent} object.
	 */
	@FXML
	void registerNewInstrumentEventHandler(ActionEvent event) {
		try {
			RegisterInstrumentLoader x = new RegisterInstrumentLoader();
			x.start(this);
		} catch (Exception e) {
			Support.getAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
		}
	}

}
