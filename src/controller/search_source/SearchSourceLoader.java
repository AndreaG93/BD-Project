package controller.search_source;

import controller.AbstractLoader;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class SearchSourceLoader extends AbstractLoader {

	// Controller class
	private SearchSourceController controller;

	public void start() throws Exception {
		
		this.myStage = new Stage();
		this.FXML_Loader = new FXMLLoader(getClass().getResource("GUI_SearchSource.fxml"));
		
		this.root = FXML_Loader.load();
		this.controller = FXML_Loader.getController();
		this.controller.initialization(myStage);

		showView("Source finder", StageStyle.DECORATED, true, true);
	}
}
