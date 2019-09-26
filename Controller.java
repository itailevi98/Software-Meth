package SongLib;

import javafx.fxml.FXML;
import javafx.collections.FXCollections;
import javafx.scene.control.ListView;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.beans.value.ChangeListener;

public class Controller {

    @FXML
    ListView<String> listView;
    
    private ObservableList<String> obsList;
    
  
    public void initialize() {

    	obsList=FXCollections.observableArrayList(
    			"Dead Presidents", 
    			"Thought you wuz nice", 
    			"Blackout", 
    			"Dancing in the Rain",
    			"Thieves in the Night"
    	);
    	listView.setItems(obsList);
    	
    	//select first item by default
    	listView.getSelectionModel().select(0);
    	
    	
    	//listView.getSelectionModel().selectedIndexProperty().addListener(obsList, oldVal, newVal)->showItem(primaryStage);
    
    
    }
    
    private void showItem(Stage primaryStage) {
    	Alert alert = new Alert(AlertType.INFORMATION);
    	alert.initOwner(primaryStage);
    	alert.setTitle("Song Details");
    	alert.setHeaderText("Selected song details: ");
    	
    	String content = "Name: " + listView.getSelectionModel().getSelectedItem();
    	
    	alert.setContentText(content);
    	alert.showAndWait();
    }
    
}	