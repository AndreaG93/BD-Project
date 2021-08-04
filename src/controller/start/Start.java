package controller.start;

import controller.loading.LoadingLoader;
import controller.login.LoginLoader;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.Support;
import model.database._init.InitDB;

public class Start extends Application {

	// Loading screen...
	private LoadingLoader myLoadingLoader = new LoadingLoader();

	public static void main(String[] arg0) {
		launch(arg0);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		try {
			myLoadingLoader.start();
		} catch (Exception e) {
			Support.getAlert(AlertType.ERROR, "Error", e.getMessage()).show();
		}

		// thread...
		Runnable myRunnable = new Runnable() {

			@Override
			public void run() {

				// Application initialization...
				InitDB var = new InitDB();
				var.tableExistenceCheck();
				var.demoDataCheck();

				// Update view at the Application Thread...
				Platform.runLater(new Runnable() {
					@Override
					public void run() {

						myLoadingLoader.close();

						try {

							LoginLoader x = new LoginLoader();
							x.start();

						} catch (Exception e) {
							Support.getAlert(AlertType.ERROR, "Error", e.getMessage()).show();
						}

					}
				});

			}
		};

		
		new Thread(myRunnable).start();
		
	}

}
