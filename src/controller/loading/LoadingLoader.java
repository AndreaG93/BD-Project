package controller.loading;

import java.util.concurrent.ThreadLocalRandom;

import controller.AbstractLoader;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ProgressBar;
import javafx.scene.effect.ColorAdjust;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LoadingLoader extends AbstractLoader {

	@FXML
	private ProgressBar progressbar;

	private boolean updateProgressBarColor = true;

	public void start() throws Exception {

		this.myStage = new Stage();
		this.FXML_Loader = new FXMLLoader(getClass().getResource("GUI_Loading.fxml"));
		this.FXML_Loader.setController(this);

		this.root = FXML_Loader.load();
		showView("Please wait...", StageStyle.UNDECORATED, false, false);

		// Create and start a thread to update progress bar color...
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {

					while (updateProgressBarColor) {
						Thread.sleep(2000);
						Platform.runLater(new Runnable() {
							@Override
							public void run() {

								ColorAdjust colorAdjust = new ColorAdjust();
								colorAdjust.setHue(ThreadLocalRandom.current().nextDouble(-1, 1));
								progressbar.setEffect(colorAdjust);

							}
						});
					}

				} catch (InterruptedException e) {
					return;
				}
			}
		}).start();
	}

	public void close() {
		this.updateProgressBarColor = false;
		this.myStage.close();
	}
}
