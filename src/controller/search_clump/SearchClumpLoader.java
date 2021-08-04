package controller.search_clump;

import controller.AbstractLoader;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class SearchClumpLoader extends AbstractLoader {

	// Controller class
	private SearchClumpController controller;

	public void start() throws Exception {
		
		this.myStage = new Stage();
		this.FXML_Loader = new FXMLLoader(getClass().getResource("GUI_SearchClump.fxml"));
		
		this.root = FXML_Loader.load();
		this.controller = FXML_Loader.getController();
		this.controller.initialization(myStage);

		showView("Clump finder", StageStyle.DECORATED, true, true);
	}
}
