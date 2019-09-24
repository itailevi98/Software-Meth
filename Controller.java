package SongLib;

import javafx.fxml.FXML;
import javafx.collections.FXCollections;
import javafx.scene.control.ListView;
import javafx.collections.ObservableList;

public class Controller {

    @FXML
    ListView<String> listView;
    
    private ObservableList<String> obsList;
    
  
    public void initialize() {

    obsList=FXCollections.observableArrayList("Dead Presidents", "Thought you wuz nice", "Blackout", "Dancing in the Rain","Thieves in the Night");
    listView.setItems(obsList);
    
    
    }
}	