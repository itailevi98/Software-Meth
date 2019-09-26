package SongLib;

import javafx.fxml.FXML;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import javafx.event.*;
import javafx.scene.control.*;
import javafx.beans.value.ChangeListener;
import java.util.Optional;


public class Controller {

    @FXML
    ListView<String> listView;
    
    private ObservableList<String> obsList;
    
    public Label songName = new Label();
    public Label artistName = new Label();
    public Label albumName = new Label();
    public Label songYear = new Label();
    public Button editButton = new Button();
   
  
    public void initialize(Stage primaryStage) {
    	

    	obsList=FXCollections.observableArrayList(
    			"Dead Presidents", 
    			"Thought you wuz nice", 
    			"Blackout", 
    			"Dancing in the Rain",
    			"Thieves in the Night"
    	);
    	listView.setItems(obsList);
    	
    	
    	listView.getSelectionModel().selectedIndexProperty().addListener((obs, oldVal, newVal) -> showItem(primaryStage)); 
    	
    	//select first item by default
    	listView.getSelectionModel().select(0);
    	
    	
    	TextField newSongName = new TextField();
    	editButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				String song = "";
				songName.textProperty().bind(newSongName.textProperty());
				newSongName.setText(song);
				
			}
    	});
    	
    	
    	
    
    }
    
    private void showItem(Stage primaryStage) {
    	
    	String content = listView.getSelectionModel().getSelectedItem();
    	songName.setText(content);
    	//listView.getSelectionModel().getSelectedItem();
    	
    	
    	
    	/*Alert alert = new Alert(AlertType.INFORMATION);
    	alert.initOwner(primaryStage);
    	alert.setTitle("Song Details");
    	alert.setHeaderText("Selected song details: ");
    	
    	String content = "Name: " + listView.getSelectionModel().getSelectedItem();
    	
    	alert.setContentText(content);
    	alert.showAndWait();*/
    }
    
    public void editSong(ActionEvent event){
    	//String originalName = listView.getSelectionModel().getSelectedItem();
    	int index = listView.getSelectionModel().getSelectedIndex();
    	TextField newSongName = new TextField();
    	
    	EventHandler<ActionEvent> edit = new EventHandler<ActionEvent>() {
    			public void handle(ActionEvent e) {
    				songName.setText(newSongName.getText());
    			}
    	};
    	
    	newSongName.setOnAction(edit);
    	
    	
    	/*TextInputDialog dialog = new TextInputDialog(originalName);
    	dialog.initOwner(Main.primaryStage);
    	dialog.setTitle("List Item");
    	dialog.setHeaderText("Selected Item (Index: " + index + ")");
    	dialog.setContentText("Enter New Song Name: ");
    	
    	
    	Optional<String> newSongName = dialog.showAndWait();
    	*/
    	
    	
    		obsList.set(index, songName.getText());

    }
    
}	