package controller.view_map;

import java.util.Vector;
import controller.AbstractControllerObserver;
import controller.register_map.RegisterMapLoader;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
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
import model.entity.map.Map;
import model.managers.AccountManager;
import model.managers.MapManager;


public class ViewMapController implements AbstractControllerObserver {

	@FXML
	private Pagination pgnt_map;

	@FXML
	private Button btn_registerNewMap;

	@FXML
	private BorderPane root;

	@FXML
	private Button btn_remove;

	private static int ROWS_PER_PAGE = 50;
	private Stage myStage;
	private TableView<Map> myTableView;
	private MapManager myMapManager;

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
		this.myMapManager = new MapManager();

		// Get logged account...
		Account myAccount = this.myAccountManager.getCurrentLoggedAccount();

		// Disable administrative tools if logged Account isn't
		// 'Administrator'...
		if (Administrator.class.isInstance(myAccount.getType())) {
			this.btn_registerNewMap.setDisable(false);
		}

		initializationTable();
		updateView();
	}

	/**
	 * This method is used to update view.
	 */
	@Override
	public void updateView() {

		Vector<Map> myMapVector = this.myMapManager.getAllMap();

		// Set number of pages...
		this.pgnt_map.setPageCount((myMapVector.size() / ROWS_PER_PAGE + 1));

		// Sets the page factory callback function...
		this.pgnt_map.setPageFactory(new Callback<Integer, Node>() {

			@Override
			public Node call(Integer pageIndex) {

				int fromIndex = pageIndex * ROWS_PER_PAGE;
				int toIndex = Math.min(fromIndex + ROWS_PER_PAGE, myMapVector.size());
				myTableView.setItems(FXCollections.observableArrayList(myMapVector.subList(fromIndex, toIndex)));

				return myTableView;
			}
		});
	}

	@SuppressWarnings("unchecked")
	private void initializationTable() {

		// Create TableView...
		this.myTableView = new TableView<Map>();

		// *** ID Column ****
		TableColumn<Map, Integer> column_ID = new TableColumn<Map, Integer>("ID");
		column_ID.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Map, Integer>, ObservableValue<Integer>>() {

					@Override
					public ObservableValue<Integer> call(CellDataFeatures<Map, Integer> param) {
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
		TableColumn<Map, String> column_Map = new TableColumn<Map, String>("Map's name");
		column_Map.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Map, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Map, String> param) {
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

	/**
	 * This method is used to remove a selected {@code Instrument} object from
	 * database.
	 * 
	 * @param event
	 *            - Represents a {@code ActionEvent} object.
	 */
	@FXML
	void removeEventHandler(ActionEvent event) {
		Map var0 = this.myTableView.getSelectionModel().getSelectedItem();

		try {
			if (var0 != null) {
				if (Support.showConfirmationAlert("You want remove selected map?")) {
					this.myMapManager.remove(var0);
					updateView();
				}
			} else
				throw new Exception("Select a map to remove it.");

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

	@FXML
	void registerNewMapEventHandler(ActionEvent event) {
		try {
			RegisterMapLoader x = new RegisterMapLoader();
			x.start(this);
		} catch (Exception e) {
			Support.getAlert(AlertType.ERROR, "Error", e.getMessage()).show();
		}
	}

	



}
