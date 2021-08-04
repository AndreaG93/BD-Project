package controller.search_source;

import java.util.Vector;
import controller.loading.LoadingLoader;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Pagination;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Support;
import model.database.DatabaseUtility;
import model.entity.band.Band;
import model.entity.flux.Flux;
import model.entity.map.Map;
import model.entity.source.Source;
import model.managers.BandManager;
import model.managers.MapManager;
import model.managers.SourceManager;

public class SearchSourceController {

	private Pagination myPagination;
	private Vector<Source> data;
	private static final int ROWS_PER_PAGE = 50;
	private static String NO_DATA = "";
	private TableView<Source> myTable;

	private SourceManager mySourceManager = new SourceManager();
	private MapManager myMapManager = new MapManager();
	private Stage myStage;

	@FXML
	private RadioButton rdbtn_searchSourceByRegion;

	@FXML
	private TextField txtf_idClump;

	@FXML
	private RadioButton rdbtn_searchAllSource;

	@FXML
	private RadioButton rdbtn_searchSourceCircle;

	@FXML
	private RadioButton rdbtn_searchSourceSquare;

	@FXML
	private TextField txtf_radiusLength;

	@FXML
	private TextField txtf_sideLength;

	@FXML
	private BorderPane root;

	@FXML
	private ToggleGroup ClumpSearchOption;

	@FXML
	private Pagination pgn_resultList;

	@FXML
	private TextField txtf_startingPointY;

	@FXML
	private ComboBox<String> cmbx_selectMap;

	@FXML
	private TextField txtf_startingPointX;

	

	private boolean performed = false;
	private LoadingLoader myLoadingLoader = new LoadingLoader();
	

	public void initialization(Stage arg0) {
		this.myStage = arg0;
		this.data = new Vector<>();
		createTable();

		// ComboBoxes initialization...
		ObservableList<String> myObservableMapList = FXCollections.observableArrayList();
		for (Map obj : this.myMapManager.getAllMap())
			myObservableMapList.add(obj.getName());

		this.cmbx_selectMap.setItems(myObservableMapList);

		myPagination = new Pagination();
		root.setCenter(myPagination);
		updateViewEventHandler(null);
	}

	@FXML
	void CloseEventHandler(ActionEvent event) {
		Support.showExitAlert(myStage);
	}

	

	@FXML
	void updateViewEventHandler(ActionEvent event) {

		// Sets the page factory callback function...
		myPagination.setPageFactory(new Callback<Integer, Node>() {

			@Override
			public Node call(Integer pageIndex) {

				if (!performed) {

					// Cleaning data vector...
					data.clear();

					// Loading...
					Runnable myRunnable = new Runnable() {

						@Override
						public void run() {

							try {

								// Update view at the Application Thread...
								Platform.runLater(new Runnable() {
									@Override
									public void run() {
										try {
											myLoadingLoader.start();
										} catch (Exception e) {
											Support.getAlert(AlertType.ERROR, "Error", e.getMessage()).show();
										}
									}
								});

								if (cmbx_selectMap.getValue() != null) {
									// Loading data...
									if (rdbtn_searchAllSource.isSelected())
										data = mySourceManager.getAllSourceByMap(cmbx_selectMap.getValue(),
												ROWS_PER_PAGE, pageIndex * ROWS_PER_PAGE);

									else {

										// Get starting point data...
										String x = txtf_startingPointX.getText();
										String y = txtf_startingPointY.getText();

										if (rdbtn_searchSourceCircle.isSelected())
											data = mySourceManager.getSourceRegionCircle(txtf_radiusLength.getText(), x,
													y, ROWS_PER_PAGE, pageIndex * ROWS_PER_PAGE,
													cmbx_selectMap.getValue());
										else
											data = mySourceManager.getSourceRegionSquare(txtf_sideLength.getText(), x,
													y, ROWS_PER_PAGE, pageIndex * ROWS_PER_PAGE,
													cmbx_selectMap.getValue());
									}
								} else
									data.clear();
							} catch (Exception e) {

								// Update view at the Application Thread...
								Platform.runLater(new Runnable() {
									@Override
									public void run() {

										Support.getAlert(AlertType.ERROR, "Error", e.getMessage()).show();
										data.clear();
									}
								});

							} finally {

								// Update pagination...
								Platform.runLater(new Runnable() {
									@Override
									public void run() {

										performed = true;

										if (data.isEmpty())
											myPagination.setPageCount(1);
										else
											myPagination.setPageCount(
													((int) (DatabaseUtility.getInstance().getLastSelectCount()
															/ ROWS_PER_PAGE)) + 1);

										myTable.setItems(FXCollections.observableArrayList(data));
										myLoadingLoader.close();

										performed = false;

									}
								});
							}

						}
					};

					new Thread(myRunnable).start();
				}
				return myTable;

			}

		});
	}

	@SuppressWarnings("unchecked")
	private void createTable() {

		// Create TableView...
		this.myTable = new TableView<Source>();

		// Create TableColumn vector...
		Vector<TableColumn<Source, String>> myTableColumnVector = new Vector<>();

		// *** ID Column ****
		TableColumn<Source, String> column_Source_ID = new TableColumn<Source, String>("ID");
		column_Source_ID.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Source, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Source, String> param) {
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
								return param.getValue().getId();
							}

							@Override
							public void addListener(ChangeListener<? super String> listener) {
								// TODO Auto-generated method stub

							}
						};
					}
				});

		// *** Map Column ****
		TableColumn<Source, String> column_Map = new TableColumn<Source, String>("Map");
		column_Map.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Source, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Source, String> param) {
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

		// *** GLON Column ****
		TableColumn<Source, String> column_GLON = new TableColumn<Source, String>("Galactic longitude");
		column_GLON.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Source, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Source, String> param) {
						return new ObservableValue<String>() {

							@Override
							public void removeListener(InvalidationListener arg0) {
								// TODO Auto-generated method stub

							}

							@Override
							public void addListener(InvalidationListener arg0) {
								// TODO Auto-generated method stub

							}

							@Override
							public void removeListener(ChangeListener<? super String> listener) {
								// TODO Auto-generated method stub

							}

							@Override
							public String getValue() {
								Double var0 = param.getValue().getGalacticLongitude();

								if (var0 == null)
									return NO_DATA;

								return var0.toString();
							}

							@Override
							public void addListener(ChangeListener<? super String> listener) {
								// TODO Auto-generated method stub

							}
						};
					}
				});

		// *** GLAT Column ****
		TableColumn<Source, String> column_GLAT = new TableColumn<Source, String>("Galactic latitude");
		column_GLAT.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Source, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Source, String> param) {
						return new ObservableValue<String>() {

							@Override
							public void removeListener(InvalidationListener arg0) {
								// TODO Auto-generated method stub

							}

							@Override
							public void addListener(InvalidationListener arg0) {
								// TODO Auto-generated method stub

							}

							@Override
							public void removeListener(ChangeListener<? super String> listener) {
								// TODO Auto-generated method stub

							}

							@Override
							public String getValue() {
								Double var0 = param.getValue().getGalacticLatitude();

								if (var0 == null)
									return NO_DATA;

								return var0.toString();
							}

							@Override
							public void addListener(ChangeListener<? super String> listener) {
								// TODO Auto-generated method stub

							}
						};
					}
				});

		// *** FLUX Column ****
		for (Band myBand : new BandManager().getAllBand()) {

			TableColumn<Source, String> column = new TableColumn<Source, String>(
					"Flux at " + myBand.getResolution() + " microns, " + myBand.getWavelength() + " arcsec");
			column.setCellValueFactory(
					new Callback<TableColumn.CellDataFeatures<Source, String>, ObservableValue<String>>() {

						public ObservableValue<String> call(CellDataFeatures<Source, String> param) {

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

									String myString = "";

									for (Flux obj : param.getValue().getFluxVector()) {
										if (myBand.getResolution() == obj.getMyBand().getResolution()
												&& myBand.getWavelength() == obj.getMyBand().getWavelength()) {

											myString += "Value: " + obj.getValue() + "\nError: " + obj.getError();

											return myString;
										}
									}

									return NO_DATA;
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
		this.myTable.getColumns().addAll(column_Source_ID, column_Map, column_GLON, column_GLAT);

		for (TableColumn<Source, String> obj : myTableColumnVector)
			this.myTable.getColumns().add(obj);
	}
}
