package SongLib;

import javafx.fxml.FXML;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import javafx.event.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
//import javafx.beans.value.ChangeListener;
//import java.util.Optional;


public class Controller {

    @FXML
    ListView<String> listView;
    
    private ObservableList<String> obsList;
    
    public Label songName = new Label();
    public Label artistName = new Label();
    public Label albumName = new Label();
    public Label songYear = new Label();
    public Button editButton = new Button();
    public TextField newSongName = new TextField();
    public TextField newArtistName = new TextField();
    public TextField newAlbumName = new TextField();
    public TextField newYearDate = new TextField();
    public Button saveSong = new Button();
    public Button deleteSong = new Button();
    public Button cancelButton = new Button();
   
  
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
    	
    	
    	
    	editButton.setOnAction(this::editSongDetails);
    	
    	
    	
    
    }
    
    private void showItem(Stage primaryStage) {
    	
    	String content = listView.getSelectionModel().getSelectedItem();
    	songName.setText(content);
    	
    	newSongName.setVisible(false);
		newAlbumName.setVisible(false);
		newArtistName.setVisible(false);
		newYearDate.setVisible(false);
		
		saveSong.setVisible(false);
    	
    	//listView.getSelectionModel().getSelectedItem();
    	
    	
    	
    	/*Alert alert = new Alert(AlertType.INFORMATION);
    	alert.initOwner(primaryStage);
    	alert.setTitle("Song Details");
    	alert.setHeaderText("Selected song details: ");
    	
    	String content = "Name: " + listView.getSelectionModel().getSelectedItem();
    	
    	alert.setContentText(content);
    	alert.showAndWait();*/
    }
    
    public void editSongDetails(ActionEvent event){
    	int index = listView.getSelectionModel().getSelectedIndex();
    	newSongName.setVisible(true);
    	newSongName.setText(songName.getText());
    	
    	newArtistName.setVisible(true);
    	newArtistName.setText(artistName.getText());
    	
    	newAlbumName.setVisible(true);
    	newAlbumName.setText(albumName.getText());
    	
    	newYearDate.setVisible(true);
    	newYearDate.setText(songYear.getText());
    	
    	
    	saveSong.setVisible(true);
    	cancelButton.setVisible(true);
    	
    	
    	
    	saveSong.setOnAction(new EventHandler<ActionEvent>() {
    		public void handle(ActionEvent e) {
    			if(newSongName.getText().isEmpty() || newArtistName.getText().isEmpty()) {
    				Alert alert = new Alert(AlertType.INFORMATION);
    				alert.initOwner(Main.primaryStage);
    				alert.setTitle("Empty Song/Artist");
    				alert.setHeaderText("You cannot leave a song or artist name empty. Please input a name for the song/artist.");
    				alert.showAndWait();
    				editSongDetails(event);
    				return;
    			}
    			songName.setText(newSongName.getText());
    			artistName.setText(newArtistName.getText());
    			albumName.setText(newAlbumName.getText());
    			songYear.setText(newYearDate.getText());
    			
    			
    			obsList.set(index, songName.getText());
    			
    			
    			newSongName.setVisible(false);
    			newAlbumName.setVisible(false);
    			newArtistName.setVisible(false);
    			newYearDate.setVisible(false);
    			
    			
    			
    			saveSong.setVisible(false);
    			cancelButton.setVisible(false);
    			
    		}
    		
    		
    	});
    	
    	
    	cancelButton.setOnAction(new EventHandler<ActionEvent>() {
    		public void handle(ActionEvent e) {
    			newSongName.setVisible(false);
    			newArtistName.setVisible(false);
    			newAlbumName.setVisible(false);
    			newYearDate.setVisible(false);
    			
    			saveSong.setVisible(false);
    			cancelButton.setVisible(false);
    		}
    	});
    	
    	
    	
    	

    }
    
    
    public void deleteSong(ActionEvent event) {
    	
    	
    	
    }
    
   
    
}	