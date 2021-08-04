package controller.search_clump;

import java.util.Vector;
import controller.loading.LoadingLoader;
import controller.search_source_inside_clump.SearchSourceInsideClumpLoader;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Pagination;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Support;
import model.database.DatabaseUtility;
import model.database.clump.ClumpDB;
import model.entity.band.Band;
import model.entity.clump.Clump;
import model.entity.ellipse.Ellipse;
import model.entity.flux.Flux;
import model.entity.map.Map;
import model.managers.BandManager;
import model.managers.ClumpManager;
import model.managers.MapManager;

public class SearchClumpController {

	@FXML
	private RadioButton rdbtn_searchClumpByRegion;

	@FXML
	private TextField txt_mean;

	@FXML
	private RadioButton rdbtn_searchClumpById;

	@FXML
	private TextField txtf_idClump;

	@FXML
	private RadioButton rdbtn_searchClumpBydensity;

	@FXML
	private TextField txt_standardDeviation;

	@FXML
	private TextField txt_median;

	@FXML
	private RadioButton rdbtn_searchClumpCircle;

	@FXML
	private TextField txtf_radiusLengthClump;

	@FXML
	private BorderPane root;

	@FXML
	private TextField txtf_startingPointY;

	@FXML
	private ComboBox<String> cmbx_selectMap;

	@FXML
	private TextField txtf_sideLengthClump;

	@FXML
	private TextField txt_median_absolute_deviation;

	@FXML
	private RadioButton rdbtn_searchAllClump;

	@FXML
	private TextField txtf_startingPointX;

	@FXML
	private RadioButton rdbtn_searchClumpSquare;

	private Pagination myPagination;
	private Vector<Clump> data;
	private static final int ROWS_PER_PAGE = 50;
	private static String NO_DATA = "";
	private TableView<Clump> myTable;

	private ClumpManager myClumpManager = new ClumpManager();
	private MapManager myMapManager = new MapManager();
	private Stage myStage;
	private LoadingLoader myLoadingLoader = new LoadingLoader();

	private String mean = NO_DATA;
	private String standardDeviation = NO_DATA;
	private String statisticalMedian = NO_DATA;
	private String medianAbsoluteDeviation = NO_DATA;

	private ObservableList<String> myObservableMapList;
	private Vector<VBox> myVBoxVector = new Vector<>();

	@FXML
	private VBox vbox_density;

	@FXML
	private VBox vbox_region;

	@FXML
	private VBox vbox_all;

	@FXML
	private VBox vbox_id;

	private boolean performed = false;

	public void initialization(Stage arg0) {

		try {
			myLoadingLoader.start();
		} catch (Exception e) {
			Support.getAlert(AlertType.ERROR, "Error", e.getMessage()).show();
		}

		// Add VBox...
		this.myVBoxVector.add(vbox_all);
		this.myVBoxVector.add(vbox_id);
		this.myVBoxVector.add(vbox_density);
		this.myVBoxVector.add(vbox_region);

		Runnable myRunnable = new Runnable() {

			@Override
			public void run() {

				data = new Vector<>();
				myStage = arg0;
				myObservableMapList = FXCollections.observableArrayList();

				// Load all map...
				for (Map obj : myMapManager.getAllMap())
					myObservableMapList.add(obj.getName());

				// Create table...
				createTable();

				// Update view at the Application Thread...
				Platform.runLater(new Runnable() {
					@Override
					public void run() {

						cmbx_selectMap.setItems(myObservableMapList);

						myPagination = new Pagination();
						root.setCenter(myPagination);
						enableOptionEventHandler(null);
						updateViewEventHandler(null);
						myLoadingLoader.close();
					}
				});

			}
		};

		new Thread(myRunnable).start();

		Runnable myRunnable2 = new Runnable() {
			@Override
			public void run() {

				double x;

				ClumpDB myClumpDB = ClumpDB.getInstance();

				x = myClumpDB.getAverage();
				if (x != -1)
					mean = String.valueOf(x);

				x = myClumpDB.getStdDev();
				if (x != -1)
					standardDeviation = String.valueOf(x);

				x = myClumpDB.getStatisticalMedian();
				if (x != -1)
					statisticalMedian = String.valueOf(x);

				x = myClumpDB.getMedianAbsoluteDeviation();
				if (x != -1)
					medianAbsoluteDeviation = String.valueOf(x);

				// Update view at the Application Thread...
				Platform.runLater(new Runnable() {
					@Override
					public void run() {

						txt_mean.setText(mean);
						txt_standardDeviation.setText(standardDeviation);
						txt_median.setText(statisticalMedian);
						txt_median_absolute_deviation.setText(medianAbsoluteDeviation);
					}
				});

			}

		};

		new Thread(myRunnable2).start();

	}

	@FXML
	void CloseEventHandler(ActionEvent event) {
		Support.showExitAlert(myStage);
	}

	@FXML
	void enableOptionEventHandler(ActionEvent event) {

		for (VBox obj : this.myVBoxVector)
			obj.setDisable(true);

		if (rdbtn_searchAllClump.isSelected())
			this.vbox_all.setDisable(false);

		else if (rdbtn_searchClumpById.isSelected())
			this.vbox_id.setDisable(false);

		else if (rdbtn_searchClumpBydensity.isSelected())
			this.vbox_density.setDisable(false);
		else
			this.vbox_region.setDisable(false);
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

					Runnable myRunnableUpdater = new Runnable() {

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
									if (rdbtn_searchAllClump.isSelected())
										data = myClumpManager.getAllClumpsByMap(cmbx_selectMap.getValue(),
												ROWS_PER_PAGE, pageIndex * ROWS_PER_PAGE);

									else if (rdbtn_searchClumpById.isSelected())
										data = myClumpManager.getClumpByID(txtf_idClump.getText(),
												cmbx_selectMap.getValue());

									else if (rdbtn_searchClumpBydensity.isSelected()) {
										data = myClumpManager.getClumpsHostMassiveClump(cmbx_selectMap.getValue(),
												ROWS_PER_PAGE, pageIndex * ROWS_PER_PAGE);

									} else {

										// Get starting point data...
										String x = txtf_startingPointX.getText();
										String y = txtf_startingPointY.getText();

										if (rdbtn_searchClumpCircle.isSelected())
											data = myClumpManager.getClumpsRegionCircle(
													txtf_radiusLengthClump.getText(), x, y, ROWS_PER_PAGE,
													pageIndex * ROWS_PER_PAGE);
										else
											data = myClumpManager.getClumpsRegionSquare(txtf_sideLengthClump.getText(),
													x, y, ROWS_PER_PAGE, pageIndex * ROWS_PER_PAGE);
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

					new Thread(myRunnableUpdater).start();
				}
				return myTable;
			}

		});
	}

	/**
	 * This method is used to create a table.
	 * 
	 * @return A {@code Table} object.
	 */
	private void createTable() {

		// Create TableView...
		this.myTable = new TableView<Clump>();

		// Create TableColumn vector...
		Vector<TableColumn<Clump, String>> myTableColumnVector = new Vector<>();

		// *** ID Column ****
		TableColumn<Clump, String> column_ID = new TableColumn<Clump, String>("ID");
		myTableColumnVector.add(column_ID);
		column_ID.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Clump, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Clump, String> param) {
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
								return String.valueOf(param.getValue().getID());
							}

							@Override
							public void addListener(ChangeListener<? super String> listener) {
							}
						};
					}
				});

		// *** GLON Column ****
		TableColumn<Clump, String> column_Map = new TableColumn<Clump, String>("Map");
		myTableColumnVector.add(column_Map);
		column_Map.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Clump, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Clump, String> param) {
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
								return param.getValue().getMyMap().getName();
							}

							@Override
							public void addListener(ChangeListener<? super String> listener) {
							}
						};
					}
				});

		// *** GLON Column ****
		TableColumn<Clump, String> column_GLON = new TableColumn<Clump, String>("Galactic longitude");
		myTableColumnVector.add(column_GLON);
		column_GLON.setPrefWidth(8 * column_GLON.getText().length());
		column_GLON.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Clump, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Clump, String> param) {
						return new ObservableValue<String>() {

							@Override
							public void removeListener(InvalidationListener arg0) {

							}

							@Override
							public void addListener(InvalidationListener arg0) {

							}

							@Override
							public void removeListener(ChangeListener<? super String> listener) {

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

							}
						};
					}
				});

		// *** GLAT Column ****
		TableColumn<Clump, String> column_GLAT = new TableColumn<Clump, String>("Galactic latitude");
		column_GLAT.setPrefWidth(8 * column_GLAT.getText().length());
		myTableColumnVector.add(column_GLAT);
		column_GLAT.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Clump, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Clump, String> param) {
						return new ObservableValue<String>() {

							@Override
							public void removeListener(InvalidationListener arg0) {

							}

							@Override
							public void addListener(InvalidationListener arg0) {

							}

							@Override
							public void removeListener(ChangeListener<? super String> listener) {

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

							}
						};
					}
				});

		// *** TEMP Column ****
		TableColumn<Clump, String> column_TEMP = new TableColumn<Clump, String>("Temperature");
		myTableColumnVector.add(column_TEMP);
		column_TEMP.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Clump, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Clump, String> param) {
						return new ObservableValue<String>() {

							@Override
							public void removeListener(InvalidationListener arg0) {

							}

							@Override
							public void addListener(InvalidationListener arg0) {

							}

							@Override
							public void removeListener(ChangeListener<? super String> listener) {

							}

							@Override
							public String getValue() {
								Double var0 = param.getValue().getTemperature();

								if (var0 == null)
									return NO_DATA;

								return var0.toString();
							}

							@Override
							public void addListener(ChangeListener<? super String> listener) {

							}
						};
					}
				});

		// *** L_M_RATIO Column ****
		TableColumn<Clump, String> column_L_M_RATIO = new TableColumn<Clump, String>("L/M Ratio");
		myTableColumnVector.add(column_L_M_RATIO);
		column_L_M_RATIO.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Clump, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Clump, String> param) {
						return new ObservableValue<String>() {

							@Override
							public void removeListener(InvalidationListener arg0) {

							}

							@Override
							public void addListener(InvalidationListener arg0) {

							}

							@Override
							public void removeListener(ChangeListener<? super String> listener) {

							}

							@Override
							public String getValue() {
								Double var0 = param.getValue().getBolometricTemperatureMassRatio();

								if (var0 == null)
									return NO_DATA;

								return var0.toString();
							}

							@Override
							public void addListener(ChangeListener<? super String> listener) {

							}
						};
					}
				});

		// *** SURF_DENS Column ****
		TableColumn<Clump, String> column_SURF_DENS = new TableColumn<Clump, String>("Surface density");
		myTableColumnVector.add(column_SURF_DENS);
		column_SURF_DENS.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Clump, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Clump, String> param) {
						return new ObservableValue<String>() {

							@Override
							public void removeListener(InvalidationListener arg0) {

							}

							@Override
							public void addListener(InvalidationListener arg0) {

							}

							@Override
							public void removeListener(ChangeListener<? super String> listener) {

							}

							@Override
							public String getValue() {
								Double var0 = param.getValue().getSurfaceDensity();

								if (var0 == null)
									return NO_DATA;

								return var0.toString();
							}

							@Override
							public void addListener(ChangeListener<? super String> listener) {

							}
						};
					}
				});

		// *** EVOL_FLAG Column ****
		TableColumn<Clump, String> column_EVOL_FLAG = new TableColumn<Clump, String>("Clump type");
		myTableColumnVector.add(column_EVOL_FLAG);
		column_EVOL_FLAG.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Clump, String>, ObservableValue<String>>() {

					public ObservableValue<String> call(CellDataFeatures<Clump, String> param) {

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
								return param.getValue().getType().toString();
							}

							@Override
							public void addListener(ChangeListener<? super String> listener) {
							}
						};

					}
				});

		// *** MASS Column ****
		TableColumn<Clump, String> column_MASS = new TableColumn<Clump, String>("Mass");
		column_MASS.setPrefWidth(200.0);
		myTableColumnVector.add(column_MASS);
		column_MASS.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Clump, String>, ObservableValue<String>>() {

					public ObservableValue<String> call(CellDataFeatures<Clump, String> param) {

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

								Double mass = param.getValue().getMass();

								if (mass == null)
									return NO_DATA;
								else
									return mass.toString();

							}

							@Override
							public void addListener(ChangeListener<? super String> listener) {
							}
						};

					}
				});

		// *** FLUX Column ****
		for (Band myBand : new BandManager().getAllBand()) {

			TableColumn<Clump, String> column = new TableColumn<Clump, String>(
					"Flux at " + myBand.getResolution() + " microns, " + myBand.getWavelength() + " arcsec");
			column.setPrefWidth(8 * column.getText().length());
			column.setCellValueFactory(
					new Callback<TableColumn.CellDataFeatures<Clump, String>, ObservableValue<String>>() {

						public ObservableValue<String> call(CellDataFeatures<Clump, String> param) {

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

											if (obj.getValue() != null)
												myString += "Value: " + obj.getValue();
											else
												myString += "Value: null";

											if (obj.getError() != null)
												myString += "\nError: " + obj.getError();
											else
												myString += "\nError: null";

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

		// *** Ellipse Column ****
		for (Band myBand : new BandManager().getAllBand()) {

			TableColumn<Clump, String> column_major_axis = new TableColumn<Clump, String>(
					"Major Axis at " + myBand.getResolution() + " microns, " + myBand.getWavelength() + " arcsec");

			column_major_axis.setCellValueFactory(
					new Callback<TableColumn.CellDataFeatures<Clump, String>, ObservableValue<String>>() {

						public ObservableValue<String> call(CellDataFeatures<Clump, String> param) {

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

									for (Ellipse obj : param.getValue().getEllipses()) {
										if (myBand.getResolution() == obj.getBand().getResolution()
												&& myBand.getWavelength() == obj.getBand().getWavelength()) {

											if (obj.getMajorAxis() != null)
												return obj.getMajorAxis().toString();
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

			TableColumn<Clump, String> column_minor_axis = new TableColumn<Clump, String>(
					"Minor Axis at " + myBand.getResolution() + " microns, " + myBand.getWavelength() + " arcsec");
			column_minor_axis.setCellValueFactory(
					new Callback<TableColumn.CellDataFeatures<Clump, String>, ObservableValue<String>>() {

						public ObservableValue<String> call(CellDataFeatures<Clump, String> param) {

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

									for (Ellipse obj : param.getValue().getEllipses()) {
										if (myBand.getResolution() == obj.getBand().getResolution()
												&& myBand.getWavelength() == obj.getBand().getWavelength()) {

											if (obj.getMinorAxis() != null)
												return obj.getMinorAxis().toString();
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

			TableColumn<Clump, String> column_angle_rotation = new TableColumn<Clump, String>(
					"Position angle at " + myBand.getResolution() + " microns, " + myBand.getWavelength() + " arcsec");
			column_angle_rotation.setCellValueFactory(
					new Callback<TableColumn.CellDataFeatures<Clump, String>, ObservableValue<String>>() {

						public ObservableValue<String> call(CellDataFeatures<Clump, String> param) {

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

									for (Ellipse obj : param.getValue().getEllipses()) {
										if (myBand.getResolution() == obj.getBand().getResolution()
												&& myBand.getWavelength() == obj.getBand().getWavelength()) {

											if (obj.getAngleOfRotation() != null)
												return obj.getAngleOfRotation().toString();
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

			
			
			// Resize
			column_major_axis.setPrefWidth(8 * column_major_axis.getText().length());
			column_minor_axis.setPrefWidth(8 * column_minor_axis.getText().length());
			column_angle_rotation.setPrefWidth(8 * column_angle_rotation.getText().length());
			
			myTableColumnVector.add(column_major_axis);
			myTableColumnVector.add(column_minor_axis);
			myTableColumnVector.add(column_angle_rotation);
		}

		// Add columns to table..
		for (TableColumn<Clump, String> obj : myTableColumnVector)
			this.myTable.getColumns().add(obj);
		

		// Add context menu...
		ContextMenu myContextMenu = new ContextMenu();
		MenuItem myMenuItem_1 = new MenuItem("Search Source inside this clump.");

		myMenuItem_1.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					SearchSourceInsideClumpLoader x = new SearchSourceInsideClumpLoader();
					x.start(myTable.getSelectionModel().getSelectedItem());
				} catch (Exception e) {
					Support.getAlert(AlertType.ERROR, "Error", e.getMessage()).show();
				}
			}
		});

		myContextMenu.getItems().add(myMenuItem_1);

		this.myTable.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent t) {
				if (t.getButton() == MouseButton.SECONDARY) {
					myContextMenu.show(myTable, t.getScreenX(), t.getScreenY());
				}
			}
		});
	}
}
