package controller;

import java.util.Optional;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import model.Support;

public abstract class AbstractLoader {

	// The JavaFX FXMLLoader class is used to loading FXML files at runtime.
	protected FXMLLoader FXML_Loader;

	// The JavaFX Stage class is the container for any JavaFX program
	protected Stage myStage;

	// The JavaFX Scene class is the container for all content in a scene
	protected Scene myScene;

	// The base class for all nodes that have children in the scene graph
	protected Parent root;

	/**
	 * This method is used to display new {@code Stage} object.
	 * 
	 * @param Title
	 *            - Represents a {@code String} object.
	 * @param stageStyle
	 *            - Represents a {@code StageStyle} object.
	 * @param isResizable
	 *            - Represents a {@code boolean}.
	 * @param isClosable
	 *            - Represents a {@code boolean}.
	 * @param isAlwaysOnTop
	 *            - Represents a {@code boolean}.
	 */
	protected void showView(String Title, StageStyle stageStyle, boolean isResizable, boolean isClosable) {
		this.myScene = new Scene(root);
		this.myStage.setTitle(Title);
		this.myStage.setScene(myScene);
		this.myStage.setResizable(isResizable);
		this.myStage.initModality(Modality.APPLICATION_MODAL);
		this.myStage.initStyle(stageStyle);
		this.myStage.show();

		if (isClosable)
			this.myStage.setOnCloseRequest(new EventHandler<WindowEvent>() {

				@Override
				public void handle(WindowEvent event) {

					Alert myDialog = Support.getAlert(Alert.AlertType.CONFIRMATION, "Message", "Do you want exit?");
					myDialog.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);

					Optional<ButtonType> result = myDialog.showAndWait();
					if (result.get() == ButtonType.YES)
						myStage.close();
					else
						event.consume();
				}
			});
		else
			this.myStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				
				@Override
				public void handle(WindowEvent event) {
					event.consume();
					
				}
			});
	}
}
