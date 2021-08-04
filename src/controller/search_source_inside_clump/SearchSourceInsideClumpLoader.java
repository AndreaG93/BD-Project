package controller.search_source_inside_clump;

import controller.AbstractLoader;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.entity.clump.Clump;

public class SearchSourceInsideClumpLoader extends AbstractLoader {

	// Controller class
	private SearchSourceInsideClumpController controller;

	public void start(Clump arg0) throws Exception {
		
		this.myStage = new Stage();
		this.FXML_Loader = new FXMLLoader(getClass().getResource("GUI_SearchSourceInsideClump.fxml"));
		
		this.root = FXML_Loader.load();
		this.controller = FXML_Loader.getController();
		this.controller.initialization(myStage, arg0);

		showView("Source finder - Selected Clump #" + arg0.getID(), StageStyle.DECORATED, true, true);
	}
}
