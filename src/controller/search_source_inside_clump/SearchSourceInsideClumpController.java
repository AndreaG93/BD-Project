package controller.search_source_inside_clump;

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
import javafx.scene.control.ListView;
import javafx.scene.control.Pagination;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Support;
import model.database.DatabaseUtility;
import model.entity.band.Band;
import model.entity.clump.Clump;
import model.entity.flux.Flux;
import model.entity.source.Source;
import model.managers.BandManager;
import model.managers.SourceManager;

public class SearchSourceInsideClumpController {

	private Pagination myPagination;
	private Vector<Source> data;
	private static final int ROWS_PER_PAGE = 50;
	private static String NO_DATA = "";
	private TableView<Source> myTable;

	private SourceManager mySourceManager = new SourceManager();
	private BandManager myBandManager = new BandManager();
	private Stage myStage;
	private Clump myClump;

	@FXML
	private ListView<Band> lst_bandList;

	@FXML
	private BorderPane root;

	@FXML
	private Pagination pgn_resultList;

	@FXML
	private RadioButton rdbtn_searchYSO;

	@FXML
	private RadioButton rdbtn_searchSourceInside;
	
	private boolean performed = false;

	public void initialization(Stage arg0, Clump arg1) {
		this.myStage = arg0;
		this.myClump = arg1;
		this.data = new Vector<>();
		createTable();

		// ListView initialization...
		ObservableList<Band> myObservableBandList = FXCollections.observableArrayList();
		for (Band obj : this.myBandManager.getAllBand())
			myObservableBandList.add(obj);

		this.lst_bandList.setItems(myObservableBandList);
		
		myPagination = new Pagination();
		root.setCenter(myPagination);
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

					LoadingLoader myLoadingLoader = new LoadingLoader();

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

								// Loading data...
								if (rdbtn_searchSourceInside.isSelected()) {
									if (lst_bandList.getSelectionModel().getSelectedItem() == null)
										throw new Exception("No band selected");

									data = mySourceManager.searchSourceInsideClump(myClump,
											lst_bandList.getSelectionModel().getSelectedItem(), ROWS_PER_PAGE,
											pageIndex * ROWS_PER_PAGE);
								} else {

									if (lst_bandList.getSelectionModel().getSelectedItem() == null)
										throw new Exception("No band selected");

									data = mySourceManager.searchSourceInsideClumpYSO(myClump,
											lst_bandList.getSelectionModel().getSelectedItem(), ROWS_PER_PAGE,
											pageIndex * ROWS_PER_PAGE);
								}

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
